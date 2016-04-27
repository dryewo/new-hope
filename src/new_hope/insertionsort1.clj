(ns new-hope.insertionsort1
  (:require [midje.sweet :refer :all]))

; https://www.hackerrank.com/challenges/insertionsort1

(def arr [2 4 6 8 3])

(def arr-size (count arr))
(def elem (last arr))


(defn insert-elem [arr]
  (if (< (count arr) 2)
    (do (apply println arr)
        arr)
    (let [elem-to-insert (last arr)] 
      (loop [index (dec (count arr))
             prev-index (dec index)
             arr arr]
        (if (< prev-index 0)
          (let [new-arr (assoc arr index elem-to-insert)]
            (apply println new-arr)
            new-arr)
          (if (> (nth arr prev-index) elem-to-insert)
            (let [new-arr (assoc arr index (nth arr prev-index))]
              (apply println new-arr)
              (recur (dec index) (dec prev-index) new-arr))
            (let [new-arr (assoc arr index elem-to-insert)]
              (apply println new-arr)
              new-arr)
            ))))))



(fact "the size is correct"
      arr-size => 5)

(fact "elem is the correct one"
      elem => 3)

(fact "insert works"
      (insert-elem []) => []
      (insert-elem [3]) => [3]
      (insert-elem [1 3]) => [1 3]
      (insert-elem [1 2 3]) => [1 2 3]
      (insert-elem [5 3]) => [3 5]
      (insert-elem [5 6 9 7]) => [5 6 7 9]
      (insert-elem arr) => [2 3 4 6 8]
      )