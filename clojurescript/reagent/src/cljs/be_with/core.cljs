(ns be-with.core
  (:require
   [reagent.core :as reagent :refer [atom]]
   [reagent.session :as session]
   [reitit.frontend :as reitit]
   [clerk.core :as clerk]
   [accountant.core :as accountant]
   [cljs-time.core :as time]
   [cljs-time.local :as time-local]
   [goog.string :as gstring]
   [goog.string.format]))

;; -------------------------
;; Routes


(def router
  (reitit/router
   [["/" :index]]))


;; -------------------------
;; My components

(defn floor-number [x y] (.floor js/Math (/ x y)))
(defn render-days [x] (gstring/format "%s Days" x))

(def now (atom (time-local/local-now)))
(def from-date (time/date-time 2013 7 6))
(def optimize-render (memoize render-days))
(defn timer []
  (fn []
    [:span (optimize-render (floor-number (time/in-seconds (time/interval from-date @now)) 86400))]))

(js/setInterval #(swap! now time-local/local-now) 100)


;; -------------------------
;; Page components


(defn home-page []
  (fn []
    [:div.container [timer]]))


;; -------------------------
;; Translate routes -> page components


(defn page-for [route]
  (case route
    :index #'home-page))


;; -------------------------
;; Page mounting component


(defn current-page []
  (fn []
    (let [page (:current-page (session/get :route))]
      [:div
       [page]])))


;; -------------------------
;; Initialize app


(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (clerk/initialize!)
  (accountant/configure-navigation!
   {:nav-handler
    (fn [path]
      (let [match (reitit/match-by-path router path)
            current-page (:name (:data  match))
            route-params (:path-params match)]
        (reagent/after-render clerk/after-render!)
        (session/put! :route {:current-page (page-for current-page)
                              :route-params route-params})
        (clerk/navigate-page! path)))
    :path-exists?
    (fn [path]
      (boolean (reitit/match-by-path router path)))})
  (accountant/dispatch-current!)
  (mount-root))
