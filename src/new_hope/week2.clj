(ns new-hope.week2
  (:require [midje.sweet :refer :all]
            [criterium.core :as crit]))

;; Day 1
;; Fibonacci
(defn fibonacci [n]
  (take n (map first (iterate (fn [[x y]]
                                [y (+ x y)]) [1 1]))))

(facts "about fiboncacci"
  (fibonacci 3) => '(1 1 2)
  (fibonacci 6) => '(1 1 2 3 5 8)
  (fibonacci 8) => '(1 1 2 3 5 8 13 21))

;; Get the Caps
(defn filter-caps [s]
  (apply str (filter #(Character/isUpperCase %) s)))

(facts "about filter-caps"
  (filter-caps "HeLlO, WoRlD!") => "HLOWRD"
  (filter-caps "nothing") => ""
  (filter-caps "$#A(*&987Zf") => "AZ")

;; Intro to some
(facts "about some"
  (some #{2 7 6} [5 6 7 8]) => 6
  (some #(when (even? %) %) [5 6 7 8]) => 6)

;; Factorial Fun
(defn factorial [n]
  (reduce * (range 1 (inc n))))

(facts "about factorial"
  (factorial 1) => 1
  (factorial 3) => 6
  (factorial 5) => 120
  (factorial 8) => 40320)

;; Intro to Destructuring
(facts "about destructuring"
  (let [[a b c d e f g] (range)] [c e]) => [2 4])

;; Day 2
;; Advanced Destructuring
(facts "about advanced destructuring"
  (let [[a b & c] ["cat" "dog" "bird" "fish"]] [a b]) => '("cat" "dog")
  (let [[a b & c] ["cat" "dog" "bird" "fish"]] c) => '("bird" "fish")
  (let [[a b & c :as d] (range 1 6)] [a b c d]) => [1 2 [3 4 5] [1 2 3 4 5]])

;; A Half-Truth
(defn half-truth [& bools]
  (loop [t false f false [x & xs] bools]
    (if (nil? x)
      (and t f)
      (recur (or t x) (or f (not x)) xs))))

(facts "about half-truth"
  (half-truth true false) => true
  (half-truth true) => false
  (half-truth false true false) => true
  (half-truth true true true) => false
  (half-truth true true true false) => true
  (half-truth) => false)

;; Greatest common divisor
(defn gcd [a b]
  (if (= b 0)
    a
    (recur b (mod a b))))

(facts "about gcd"
  (gcd 2 4) => 2
  (gcd 10 5) => 5
  (gcd 5 7) => 1
  (gcd 1023 858) => 33)

;; Day 3
;; Simple closures
(defn pow [x y] (int (Math/pow x y)))
(defn pow' [y] (fn [x] (pow x y)))

(facts "about simple closures"
  ((pow' 2) 16) => 256
  ((pow' 8) 2) => 256
  (map (pow' 3) [1 2 3 4]) => [1 8 27 64]
  (map #((pow' %) 2) [0 1 2 3 4]))

;; Cartesian Product
(defn cart-prod [xs ys]
  (reduce (fn [prod x]
            (into prod (map (fn [y]
                              [x y]) ys))) #{} xs))

(facts "about cart-prod"
  (cart-prod #{"ace" "king" "queen"} #{"spade" "heart" "diamond" "club"}) =>
  #{["ace" "spade"] ["ace" "heart"] ["ace" "diamond"] ["ace" "club"]
    ["king" "spade"] ["king" "heart"] ["king" "diamond"] ["king" "club"]
    ["queen" "spade"] ["queen" "heart"] ["queen" "diamond"] ["queen" "club"]}
  (count (cart-prod
           (into #{} (range 10))
           (into #{} (range 30)))) => 300)
