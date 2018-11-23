(ns luminus-cats-gallery.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [luminus-cats-gallery.core-test]))

(doo-tests 'luminus-cats-gallery.core-test)

