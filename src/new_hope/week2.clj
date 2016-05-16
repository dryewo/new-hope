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

;; Day 4
;; Symmetric Difference
(defn sym-diff [xs ys]
  (clojure.set/union
    (clojure.set/difference xs ys)
    (clojure.set/difference ys xs)))

(facts "about sym-diff"
  (sym-diff #{1 2 3 4 5 6} #{1 3 5 7}) => #{2 4 6 7}
  (sym-diff #{:a :b :c} #{}) => #{:a :b :c}
  (sym-diff #{} #{4 5 6}) => #{4 5 6}
  (sym-diff #{[1 2] [2 3]} #{[2 3] [3 4]}) => #{[1 2] [3 4]})

;; Least Common Multiple
(defn lcm [x & xs]
  (if (empty? xs)
    x
    (let [y (first xs)]
      (recur (/ (* x y) (gcd x y)) (rest xs)))))

(facts "about lcm"
  (lcm 2 3) => 6
  (lcm 5 3 7) => 105
  (lcm 1/3 2/5) => 2
  (lcm 3/4 1/6) => 3/2
  (lcm 7 5/7 2 3/5) => 210)

;; Day 5
;; Pascal's Triangle
(defn pt-row [n]
  (loop [xs [1] k 1]
    (if (= n (dec k))
      xs
      (recur (conj xs (* (peek xs) (/ (- (inc n) k) k))) (inc k)))))

(defn pt-row' [n] (pt-row (dec n)))

(facts "about pt-row"
  (pt-row 0) => [1]
  (pt-row' 1) => [1]
  (map pt-row' (range 1 6)) => [[1]
                                [1 1]
                                [1 2 1]
                                [1 3 3 1]
                                [1 4 6 4 1]]
  (pt-row' 11) => [1 10 45 120 210 252 210 120 45 10 1])
