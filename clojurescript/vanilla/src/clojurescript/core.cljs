(ns clojurescript.core
  (:require ;; [clojure.browser.repl :as repl]
   [goog.string :as gstring]
   [goog.string.format]
   [cljs-time.core :as time]
   [cljs-time.local :as time-local]
   [garden.core :refer [css]]))

;; (defonce conn
;;   (repl/connect "http://localhost:9000/repl"))

(enable-console-print!)

(def app-name "container")
(def from-date (time/date-time 2013 7 6))
(defn apply-style [style] (.appendChild (.-head js/document) (doto (.createElement js/document "style") (.appendChild (.createTextNode js/document style)))))
(defn create-app [] (doto (.createElement js/document "div") (.setAttribute "id" app-name)))
(defn floor-number [x y] (.floor js/Math (/ x y)))
(defn render [x] (set! (.-innerHTML (.getElementById js/document app-name)) x))
(def optimize-render (memoize render))
(defn time-flies [] (let [now (time-local/local-now)
                          duration-in-seconds (time/in-seconds (time/interval from-date now))
                          days (floor-number duration-in-seconds 86400)] (optimize-render (gstring/format "<span>%s Days</span>" days))))


(apply-style (css [:body {:text-align "center" :font-size "2rem"}]))
(.appendChild (.-body js/document) (create-app))
(.setInterval js/window time-flies 100)
