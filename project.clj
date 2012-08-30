(defproject sqlite-import "0.1.0-SNAPSHOT"
  :min-lein-version "2.0.0"
  :description "FIXME: write description"
  :main "sqlite-import.core"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/data.csv "0.1.2"]
                 [org.clojure/java.jdbc "0.2.3"]
                 [org.xerial/sqlite-jdbc "3.7.2"]]
  :profiles {:dev { :dependencies [[midje "1.4.0"]]}})
