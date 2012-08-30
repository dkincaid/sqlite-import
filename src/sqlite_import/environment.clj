(ns sqlite-import.environment)

(def settings
  ^{:doc "Default and individual profiles settings set from the clojure.app.profile system property."}
  (delay
   (let [default { }
         settings {:dev { }

                   :test { }
                   
                   :prod { }}
         
         profile (keyword (System/getProperty "clojure.app.profile"))]
     (merge default (settings profile)))))
