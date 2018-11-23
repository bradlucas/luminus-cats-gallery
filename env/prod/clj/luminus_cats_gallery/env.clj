(ns luminus-cats-gallery.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[luminus-cats-gallery started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[luminus-cats-gallery has shut down successfully]=-"))
   :middleware identity})
