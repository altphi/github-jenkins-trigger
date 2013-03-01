(ns github-jenkins-trigger.core
  (:require [cheshire.core :refer :all])
  (:use ring.middleware.params
        ring.util.response
        ring.util.codec
        ring.adapter.jetty
        [clojure.java.shell :only [sh]])
   (:gen-class)
  )

(def the-url (atom nil))

(defn handler [{myparams :params}]
  (def github_map (parse-string (myparams "payload") true))
  (def repo (get-in github_map [:repository :name]))
  (def branch (clojure.string/replace
                (clojure.string/replace (get github_map :ref) #"^refs/heads/" "")
                #"/" "-"))
  (println (str "attempting to build " repo "-" branch " @ jenkins host " @the-url))
  (sh "/usr/bin/java" "-jar" "/service/jenkins/bin/jenkins-cli.jar" "-s" @the-url "build" (str repo "-" branch))
  {:status 200
  :headers {"Content-Type" "text/html"}
  :body (str repo "\n" branch) })

(def app
   (wrap-params handler))


(defn -main [ arg ]
  (reset! the-url arg)
  (run-jetty app {:port 3838})
)
