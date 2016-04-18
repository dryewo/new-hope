(ns new-hope.week2
  (:require [midje.sweet :refer :all])
  (:require [clojure.test :refer :all])
  )


(defn fibonacci
  "calculates fibonacci sequence up to the given argument"
  [length]
  (take length (map first (iterate (fn [[x y]] [y (+ x y)]) [1 1]))))

;Day 1
(deftest test-fibonacci-sequence
  (is (= (fibonacci 3) '(1 1 2)))
  (is (= (fibonacci 6) '(1 1 2 3 5 8)))
  (is (= (fibonacci 8) '(1 1 2 3 5 8 13 21)))
)

(defn get-the-caps
  "returns only uppercase characters of the argument sequence"
  [x]
  (filter #(Character/isUpperCase %) x)
)

(deftest test-only-uppercase
  (is (get-the-caps "HeLlO, WoRlD!") "HLOWRD")
  (is (empty? (get-the-caps "nothing")) true)
  (is (get-the-caps "$#A(*&987Zf") "AZ")
)

(deftest test-some
  (is 6 (some #{2 7 6} [5 6 7 8]))
  (is 6 (some #(when (even? %) %) [5 6 7 8]))
)

(defn factorial-fun
  "calculates factorial of the argument"
  [x]
  (apply * (range x))
)

(deftest test-factorial-fun
  (is 1 (factorial-fun 1))
  (is 6 (factorial-fun 3))
  (is 120 (factorial-fun 5))
  (is 40320 (factorial-fun 8))
)

(deftest intro-to-destructuring
  (is [2 4] (let [[a b c d e f g]	(range)] [b d]))
)

;Day 2

(deftest advanced-destructuring
  (is [1 2 [3 4 5] [1 2 3 4 5]] (let [[a b & c :as d] [1 2 3 4 5]] [a b c d]))
)

(defn a-half-truth
  "performs the xor of the arguments"
  [& args]
  (and (not-every? true? args) (not (nil? (some true? args))))
)

; why (= nil (and true nil))?
(deftest test-a-half-truth
  (is (= false (a-half-truth false false)))
  (is (= true (a-half-truth true false)))
  (is (= false (a-half-truth true)))
  (is (= true (a-half-truth false true false)))
  (is (= false (a-half-truth true true true)))
  (is (= true (a-half-truth true true true false)))
)
(defn great-common-divisor
  "docstring"
  [x y]
  (if (zero? y)
    x
    (recur y (mod x y))
  )
)

(deftest test-great-common-divisor
  (is (= (great-common-divisor 2 4) 2))
  (is (= (great-common-divisor 10 5) 5))
  (is (= (great-common-divisor 5 7) 1))
  (is (= (great-common-divisor 1023 858) 33))
)

; Day 3

(defn simple-closure
  "returns function that calculates power of the argument"
  [x]
  (fn [base]
    (loop [inputx base count 1]
      (if (>= count x)
        inputx
        (recur (* inputx base) (inc count))
      )
    )
  )
)

(deftest test-simple-closure
  (is (= 256 ((simple-closure 2) 16) ((simple-closure 8) 2)))
  (is (= [1 8 27 64] (map (simple-closure 3) [1 2 3 4])))
  (is (= [1 2 4 8 16] (map #((simple-closure %) 2) [0 1 2 3 4])))
)



