(ns luminus-cats-gallery.core
  (:require [baking-soda.core :as b]
            [reagent.core :as r]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [markdown.core :refer [md->html]]
            [luminus-cats-gallery.ajax :as ajax]
            [ajax.core :refer [GET POST]]
            [secretary.core :as secretary :include-macros true])
  (:import goog.History))

(defonce session (r/atom {:page :home}))

; the navbar components are implemented via baking-soda [1]
; library that provides a ClojureScript interface for Reactstrap [2]
; Bootstrap 4 components.
; [1] https://github.com/gadfly361/baking-soda
; [2] http://reactstrap.github.io/

(defn nav-link [uri title page]
  [b/NavItem
   [b/NavLink
    {:href   uri
     :active (when (= page (:page @session)) "active")}
    title]])

(defn navbar []
  (r/with-let [expanded? (r/atom true)]
    [b/Navbar {:light true
               :class-name "navbar-dark bg-primary"
               :expand "md"}
     [b/NavbarBrand {:href "/"} "luminus-cats-gallery"]
     [b/NavbarToggler {:on-click #(swap! expanded? not)}]
     [b/Collapse {:is-open @expanded? :navbar true}
      [b/Nav {:class-name "mr-auto" :navbar true}
       [nav-link "#/" "Home" :home]
       [nav-link "#/about" "About" :about]]]]))

(defn about-page []
  [:div.container
   [:div.row
    [:div.col-md-12
     [:img {:src "/img/warning_clojure.png"}]]]])

;; (defn home-page []
;;   [:div.container
;;    (when-let [docs (:docs @session)]
;;      [:div.row>div.col-sm-12
;;       [:div {:dangerouslySetInnerHTML
;;              {:__html (md->html docs)}}]])])

;; Version 1 - Show list of Cats
;; (defn fetch-links! [links cnt] 
;;   (GET "/api/cats" {:params {:cnt cnt}
;;                     :handler #(reset! links %)}))
;;
;; (defn home-page [] 
;;   (let [links (r/atom nil)] 
;;     (fetch-links! links 20) 
;;     (fn []
;;       (if (not-empty @links)
;;         [:div
;;          [:h2 "Cats"]
;;          [:ul {:class "list-group"}]
;;          (for [link @links]
;;            [:li {:class "list-group-item"} [:img {:src link}]])]
;;         [:div "Standby for cats!"]))))

;; Version 2 - Pagenation
(defn fetch-links! [links cnt] 
  ;; partition links into groups of six
  (GET "/api/cats" {:params {:cnt cnt}
                    :handler #(reset! links (vec (partition-all 6 %)))}))

(defn images [links]
  [:div.text-xs-center
   (for [row (partition-all 3 links)]
     ^{:key row}
     [:div.row
      (for [link row]
        ^{:key link}
        [:div.col-sm-4 [:img {:width 400 :src link}]])])])

(defn forward [i pages]
  (if (< i (dec pages)) (inc i) i))

(defn back [i]
  (if (pos? i) (dec i) i))

(defn page-nav-link [page i]
  [:li.page.item>a.page-link.btn.btn-primary
   {:on-click #(reset! page i)
    :class (when (= i @page) "active")}
   [:span i]])

(defn pager [pages page]
  (when (> pages 1)
    (into
     [:div.text-xs-center>ul.pagination.pagination-lg]
     (concat
      [[:li.page-item>a.page-link.btn
        {:on-click #(swap! page back pages)
         :class (when (= @page 0) "disabled")}
        [:span "<<"]]]
      (map (partial page-nav-link page) (range pages))
      [[:li.page-item>a.page-link.btn
        {:on-click #(swap! page forward pages)
         :class (when (= @page (dec pages)) "disabled")}
        [:span ">>"]]]))))

(defn home-page [] 
  (let [links (r/atom nil)
        page (r/atom 0)] 
    (fetch-links! links 50) 
    (fn []
      (when @links
        [:div.container->div.row>div.col-md-12
         [pager (count @links) page]
         [images (@links @page)]]))))

(def pages
  {:home #'home-page
   :about #'about-page})

(defn page []
  [(pages (:page @session))])

;; -------------------------
;; Routes

(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
  (swap! session assoc :page :home))

(secretary/defroute "/about" []
  (swap! session assoc :page :about))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
        (events/listen
          HistoryEventType/NAVIGATE
          (fn [event]
            (secretary/dispatch! (.-token event))))
        (.setEnabled true)))

;; -------------------------
;; Initialize app
(defn fetch-docs! []
  (GET "/docs" {:handler #(swap! session assoc :docs %)}))

(defn mount-components []
  (r/render [#'navbar] (.getElementById js/document "navbar"))
  (r/render [#'page] (.getElementById js/document "app")))

(defn init! []
  (ajax/load-interceptors!)
  (fetch-docs!)
  (hook-browser-navigation!)
  (mount-components))
