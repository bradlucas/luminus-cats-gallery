(ns luminus-cats-gallery.app
  (:require [luminus-cats-gallery.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
