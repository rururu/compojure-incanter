(defproject compo1 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [hiccup "1.0.4"]
                 [incanter "1.5.2"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler compo1.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]]}})
