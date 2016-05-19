(ns new-hope.insertionsort2
  (:require [midje.sweet :refer :all]))

; https://www.hackerrank.com/challenges/insertionsort2

(def arr [1 4 3 5 6 2])


(defn insert-elem [arr size]
  (if (< size 2)
    arr
    (let [elem-to-insert (nth arr (dec size))] 
      (loop [index (dec size)
             prev-index (dec index)
             arr arr]
        (if (< prev-index 0)
          (let [new-arr (assoc arr index elem-to-insert)]
            new-arr)
          (if (> (nth arr prev-index) elem-to-insert)
            (let [new-arr (assoc arr index (nth arr prev-index))]
              (recur (dec index) (dec prev-index) new-arr))
            (let [new-arr (assoc arr index elem-to-insert)]
              new-arr)
            ))))))

(defn insert-sort [arr]
  (loop [arr arr
         size 2]
    (if (< (count arr) size)
      arr
      (let [new-arr (insert-elem arr size)]
        (do
          (apply println new-arr)
          (recur new-arr (inc size)))))))

(fact "insert works"
      (insert-elem [] 0) => []
      (insert-elem [3] 1) => [3]
      (insert-elem [1 3] 2) => [1 3]
      (insert-elem [1 2 3] 3) => [1 2 3]
      (insert-elem [5 3] 2) => [3 5]
      (insert-elem [5 6 9 7] 4) => [5 6 7 9]
      (insert-elem [5 6 2 1] 3) => [2 5 6 1]
      )

(fact "sort works"
      (insert-sort arr) => [1 2 3 4 5 6]
      (insert-sort [1]) => [1]
      (insert-sort []) => []
      (insert-sort [2 1]) => [1 2])