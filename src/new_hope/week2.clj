(ns new-hope.week2
  (:require [midje.sweet :refer :all])
  (:require [clojure.test :refer :all]))


(defn fibonacci
  "docstring"
  [length]
  (take length (map first (iterate (fn [[x y]] [y (+ x y)]) [1 1]))))

;Day 1
(deftest test-fibonacci-sequence
  (is (= (fibonacci 3) '(1 1 2)))
  (is (= (fibonacci 6) '(1 1 2 3 5 8)))
  (is (= (fibonacci 8) '(1 1 2 3 5 8 13 21)))
)