(ns advance.core)

(defn queue
  []
  (ref {:call-history         []
        :transformation-queue []}
       :meta {:is-queue true}))

(defmacro defqueue
  [queue-name]
  `(def ~queue-name (~queue)))

(defn queue?
  [thing]
  (= (:is-queue (meta thing)) true))

(defn exhausted?
  [queue]
  (-> queue
      deref
      :transformation-queue
      empty?))

(defn enqueue!
  [queue function]
  (if-not (queue? queue)
    (throw (java.lang.Exception. (format "%s is not a queue." queue)))
    (dosync
     (alter queue update-in [:transformation-queue] conj function))))

(defn dequeue!
  [queue target]
  (if-not (queue? queue)
    (throw (java.lang.Exception. (format "%s is not a queue." queue)))
    (if-let [function (first (:transformation-queue @queue))]
      (let [result (function target)]
        (dosync (alter queue update-in [:transformation-queue] #(drop 1 %))
                (alter queue update-in [:call-history] conj target)
                result))
      (dosync (alter queue assoc-in [:transformation-queue] [])
              target))))
