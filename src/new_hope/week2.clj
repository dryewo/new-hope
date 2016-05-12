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

