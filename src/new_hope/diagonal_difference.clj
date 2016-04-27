(ns new-hope.diagonal-difference
  (:require [midje.sweet :refer :all]
            [criterium.core :as crit]))

;https://www.hackerrank.com/challenges/diagonal-difference

(def matrix [[11 2 4] [4 5 6] [10 8 -12]])

(defn element [m i j] (nth (nth m i) j))

(defn first-sum [m, n]
  (loop [sum 0
         line 0]
    (if (= n line)
      sum
      (recur (+ sum (element m line line)) (inc line)))))

(defn second-sum [m n]
  (loop [sum 0
         line 0]
    (if (= n line)
      sum
      (recur (+ sum (element m line (- n (+ line 1)))) (inc line)))))

(defn abs-of-diff [x y] (Math/abs (- x y)))

(defn diff-of-diags [m n]
  (abs-of-diff (first-sum m n) (second-sum m n)))

(facts "diff of diags"
       (diff-of-diags matrix 3) => 15)

(facts "should diff"
       (abs-of-diff 9 1) => 8
       (abs-of-diff -9 1) => 10
       (abs-of-diff 9 10) => 1)

(facts "first sum"
       (first-sum matrix 3) => 4)

(facts "second sum"
       (second-sum matrix 3) => 19)



(facts "write element"
       (element matrix 1 1) => 5
       (element matrix 0 0) => 11
       (element matrix 0 2) => 4
       (element matrix 2 1) => 8)