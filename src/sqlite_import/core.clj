(ns sqlite-import.core
  (:use [sqlite-import.environment :only [settings]])
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.data.csv :as csv])
  (:gen-class))

(def db
  {:classname "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname "/home/davek/database.db"})

(defn create-db
  "Create a new database table."
  [table-name columns]
  (try (jdbc/with-connection db
         (apply jdbc/create-table table-name columns))
       (catch Exception e (println e))))

(defn insert
  "Insert the given data into the provided table."
  [table-name data]
  (try (jdbc/with-connection db
         (jdbc/insert-records table-name data))
       (catch Exception e (println e))))

(defn clojurize-keyword
  "Turn a string into an idiomatic Clojure keyword (lower case)"
  [s]
  (keyword (string/replace (string/lower-case s) "_" "_")))

(defn read-csv
  "Read the given csv file, create a new database table using the names found in the header as columns
 the write the rest of the records into the newly created table."
  [filename tablename]
  (with-open [infile (io/reader filename)]
     (let [lines (csv/read-csv infile)
           columns (map clojurize-keyword (first lines))
           data (map #(zipmap columns %) (rest lines))]
       (create-db tablename (map vector columns))
       (doseq [record data]
        (insert tablename record)))))

(defn -main
  "Read the provided csv file, create the database, create the table and write the records"
  [filename tablename]
  (System/setProperty "clojure.app.profile" "dev")
  (let [settings @settings]
    (println "Writing to database" filename)
    (read-csv filename tablename)))
