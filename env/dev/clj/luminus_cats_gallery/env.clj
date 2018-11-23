(ns luminus-cats-gallery.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [luminus-cats-gallery.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[luminus-cats-gallery started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[luminus-cats-gallery has shut down successfully]=-"))
   :middleware wrap-dev})
