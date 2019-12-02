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
(defn apply-style [style] (.appendChild (.-head js/document) (doto (.createElement js/document "style") (.appendChild (.createTextNode js/document style)))))
(defn create-app [] (doto (.createElement js/document "div") (.setAttribute "id" app-name)))
(defn render [x] (set! (.-innerHTML (.getElementById js/document app-name)) x))
(def optimize-render (memoize render))
(defn time-flies [] (let [now (time-local/local-now)
                          year (time/year now)
                          month (time/month now)
                          day (time/day now)
                          hour (time/hour now)
                          minute (time/minute now)
                          second (time/second now)] (optimize-render (gstring/format "%sY %sM %sd %sH %sm %ss" year month day hour minute second))))


(apply-style (css [:body {:font-size "2rem"}]))
(.appendChild (.-body js/document) (create-app))
(.setInterval js/window time-flies 100)
