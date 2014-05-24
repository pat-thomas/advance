# advance

Clojure library for functional queues.
## Usage

```clj
(ns my-app.core
  (:require [advance.core :as advance]))

(advance/defqueue chicken)                     ;; defines an (empty) functional queue.

(advance/enqueue! chicken reverse)             ;; adds the function reverse onto the end of the queue.
(advance/enqueue! chicken #(map keyword %))    ;; adds the function #(map keyword %) onto the end of the queue.

(advance/dequeue! chicken ["foo" "bar" "baz"]) ;; applies the first function on the queue to the target passed in
                                               ;; results in ["baz" "bar" "foo"]
                                               ;; and removes that function from the queue.
 
(advance/dequeue! chicken ["foo" "bar" "baz"]) ;; applies the first function on the queue to the target passed in
                                               ;; results in [:foo :bar :baz]
                                               ;; and removes that function from the queue.
 
(advance/dequeue! chicken ["foo" "bar" "baz"]) ;; applies the first function on the queue to the target passed in
                                               ;; the queue is "exhausted", so the target passed in is just returned.
```				

## License

Copyright © 2014 Pat Thomas

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
