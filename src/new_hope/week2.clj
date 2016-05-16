(ns new-hope.week2
  (:require [midje.sweet :refer :all]
            [criterium.core :as crit]))

(comment
  )

;;Day 1

(defn fiboseq [count]
  (case count
    0 []
    1 [1]
    2 [1 1]
    (loop [coll [1 1] remains (- count 2)]
      (if (= 0 remains)
        coll
        (recur (conj coll (reduce +' (take-last 2 coll))) (dec remains))))))

;TODO: assess lazy-seq for lazy implementation

(facts "about fiboseq"
       (fiboseq 2) => [1 1]
       (fiboseq 3) => [1 1 2]
       (fiboseq 6) => [1 1 2 3 5 8]
       (fiboseq 8) => [1 1 2 3 5 8 13 21]
       ;Edge cases
       (fiboseq 0) => []
       (fiboseq 1) => [1])

