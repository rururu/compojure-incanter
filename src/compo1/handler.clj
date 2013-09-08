(ns compo1.handler
  (:use compojure.core
        compo1.views
        [hiccup.middleware :only (wrap-base-url)])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [compojure.response :as response]))

(def rts
(defroutes app-routes
  (GET "/" [] (index-page))
  (GET "/func" [] (make-form "Function Plot" "plot" "function" "from" "to"))
  (POST "/func" [function from to] (gen-func-plot-png function from to))  
  (GET "/normal" [] (make-form "Normal Plot" "plot" "size" "mean" "sd"))
  (POST "/normal" [size mean sd] (gen-samp-hist-png size mean sd))  
  (GET "/sample-normal" [] (gen-samp-hist-png nil nil nil))
  (route/resources "/")
  (route/not-found "Not Found")))

(def app
  (-> (handler/site app-routes)
      (wrap-base-url)))
      
