(ns advance.core)

(defmacro defqueue
  [queue-name]
  `(def ~queue-name (atom {:call-history []         
                           :transformation-queue []}
                          :meta {:is-queue true})))

(defn queue?
  [thing]
  (= (:is-queue (meta thing)) true))

(defn enqueue!
  [queue function]
  (if-not (queue? queue)
    (throw (java.lang.Exception. (format "%s is not a queue." queue)))
    (swap! queue update-in [:transformation-queue] conj function)))

(defn dequeue!
  [queue target]
  (if-not (queue? queue)
    (throw (java.lang.Exception. (format "%s is not a queue." queue)))
    (if-let [function (first (:transformation-queue @queue))]
      (let [result (function target)]
        (do (swap! queue update-in [:transformation-queue] #(drop 1 %))
            (swap! queue update-in [:call-history] conj result)
            result))
      (do (swap! queue assoc-in [:transformation-queue] [])
          target))))
