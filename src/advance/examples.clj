(ns advance.examples
  (:require [advance.core        :as core]
            [advance.application :as application]))

(comment
  (core/defqueue chicken)
  
  (dosync (core/enqueue! chicken reverse)
          (core/enqueue! chicken #(map keyword %)))

  (application/apply-queue chicken ["foof" "barf" "bazf"])
  )

