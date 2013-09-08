(ns compo1.views
(:use [hiccup core page]
        [incanter core stats charts]
        [compojure response])
(:import (java.io ByteArrayOutputStream
                    ByteArrayInputStream)))
                    
(defn make-form [header button & fields]
	(letfn [(row [field]
		(str "<tr><td>" field ":</td><td><input type=\"text\" name=\"" field "\" value=\"\" /></td></tr>"))]
	  (str "<form method=\"post\">"
	      "<h1>" header "</h1>"
	      "<table>"
     	  (apply str (map row fields))
		  "<tr><td></td><td><input type=\"submit\" name=\"button\" value=\"" button "\"></td></tr>"
  		  "</table>"
	      "</form>")))

(defn index-page []
(html5
    [:head
      [:title "Hello Ru"]
      (include-css "/css/style.css")]
    [:body
     [:h1 "Incanter Plots"]
     [:ol
      [:li [:a {:href "/normal"} "Normal Plot"]]
      [:li [:a {:href "/func"} "Function Plot"]]]]))  
      
(defn to-in-stream [chart]
	(let [out-stream (ByteArrayOutputStream.)]
		(save chart out-stream)
        (ByteArrayInputStream.
        	(.toByteArray out-stream))))
                       
(defn gen-samp-hist-png [size_s mean_s sd_s]
(let [size (if (empty? size_s) 1000 (Integer. size_s))
          m (if (empty? mean_s) 1 (Integer. mean_s))
          s (if (empty? sd_s) 1 (Integer. sd_s))
          samp (sample-normal size
                    :mean m
                    :sd s)
          chart (histogram
                 samp
                 :title "Normal Sample"
                 :x-label (str "sample-size = " size
                               ", mean = " m
                               ", sd = " s))
          in-stream (to-in-stream chart)]
      {:status 200
       :headers {"Content-Type" "image/png"}
       :body in-stream}))
       
(defn gen-func-plot-png [func from to]
	(let [fu1 (if (empty? func) "sin" func)
	      fun (symbol "incanter.core" fu1)
		  left (if (empty? from) -3.14 (Double. from))
		  right (if (empty? to) 3.14 (Double. to))
		  call `(function-plot ~fun ~left ~right :title "Function Plot")
		  chart (eval call)
          in-stream (to-in-stream chart)]
      {:status 200
       :headers {"Content-Type" "image/png"}
       :body in-stream}))
