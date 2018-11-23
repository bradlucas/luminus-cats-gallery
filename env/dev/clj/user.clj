(ns user
  (:require [luminus-cats-gallery.config :refer [env]]
            [clojure.spec.alpha :as s]
            [expound.alpha :as expound]
            [mount.core :as mount]
            [luminus-cats-gallery.figwheel :refer [start-fw stop-fw cljs]]
            [luminus-cats-gallery.core :refer [start-app]]))

(alter-var-root #'s/*explain-out* (constantly expound/printer))

(defn start []
  (mount/start-without #'luminus-cats-gallery.core/repl-server))

(defn stop []
  (mount/stop-except #'luminus-cats-gallery.core/repl-server))

(defn restart []
  (stop)
  (start))


