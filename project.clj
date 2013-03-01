(defproject github-jenkins-trigger "0.1.0"
  :description "this webserver listens for github hooks, parses the JSON and fires up jenkins"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/math.numeric-tower "0.0.2"]
                 [compojure "1.1.5"]
                 [cheshire "5.0.2"]
                 [ring/ring-core "1.1.6"]
                 [ring/ring-jetty-adapter "1.1.6"]]
  :main github-jenkins-trigger.core)
