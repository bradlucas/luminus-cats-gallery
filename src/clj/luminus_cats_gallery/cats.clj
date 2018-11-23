(ns luminus-cats-gallery.cats
  (:require [clj-http.client :as client]
            [cheshire.core :as c]
            [luminus-cats-gallery.config :as config]))


;; First version
;;
(defn get-cnt-links [cnt]
  (let [url (str "https://api.thecatapi.com/v1/images/search?format=json&limit=" cnt)
        headers {:headers {"Content-Type" "application/json" "x-api-key" (config/env :api-key)}}]
    (:body (client/get url headers))))

(defn parse-urls [json]
  ;; use optional second parameter of true to get keywords instead of strings
  (map :url (c/parse-string json true)))

(defn get-links0 [cnt]
  (parse-urls (get-cnt-links cnt)))


;; Second version.
;; @see The 'Thread-first` macro
;; https://clojuredocs.org/clojure.core/-%3E
;;
(defn get-links [cnt]
  (-> "https://api.thecatapi.com/v1/images/search?format=json&limit="
      (str cnt)
     (client/get {:headers {"Content-Type" "application/json" "x-api-key" (config/env :api-key)}})
     :body
     parse-urls))
