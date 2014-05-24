(ns advance.application
  (:require [advance.core :as core]))

(defn apply-queue
  "Given a queue, applies all functions in that queue to target, until queue is exhausted"
  [queue target]
  (loop [accum target]
    (if (core/exhausted? queue)
      accum
      (recur (core/dequeue! queue accum)))))
