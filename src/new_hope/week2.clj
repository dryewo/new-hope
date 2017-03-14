(ns new-hope.week1
  (:require [midje.sweet :refer :all]
            [criterium.core :as crit]))



(defn filter-caps [s]
  (apply str (filter #(Character/isUpperCase %) s)))

(facts "it should filter the capital letters"
       (filter-caps "HeLlO, WoRlD!") => "HLOWRD"
       (filter-caps "nothing") => ""
       (filter-caps "$#A(*&987Zf") => "AZ")

(comment
  (some #{2 7 6} [5 6 7 8]))


(defn factorial
  "calculates the factorial of a number"
  [n]
  (loop [result 1
         index 1]
    (if (= index n)
      (* result index)
      (recur (* result index) (inc index)))))

(facts "it should calculate factorial"
       (factorial 1) => 1
       (factorial 2) => 2
       (factorial 8) => 40320)

(= [2 4] (let [[a b c d e f g] (range)] [2 4]))

(= [1 2 [3 4 5] [1 2 3 4 5]] (let [[a b & c :as d] [1 2 3 4 5]] [a b c d]))

(defn- some-bool [b list]
  (true? (some #(= % b) list)))

(defn the-predicate [& args]
  (and (some-bool true args) (some-bool false args)))

(facts "it should return true or false"
       (the-predicate false false) => false
       (the-predicate true false) => true
       (the-predicate true) => false
       (the-predicate false true false) => true
       (the-predicate true true true) => false
       (the-predicate true true true false) => true)

(defn gcd [a b]
  (if (zero? b)
    a
    (recur b (mod a b))))

(facts "it should calculate the gcd"
       (gcd 2 4) => 2
       (gcd 10 5) => 5
       (gcd 5 7) => 1
       (gcd 1023 858) => 33)

(defn cartesian-product [set-1 set-2]
  (set (for [x set-1
             y set-2]
         [x y])))

(fact "should do the cartesian product"
      (cartesian-product #{1 2 3} #{4 5}) => #{[1 4] [2 4] [3 4] [1 5] [2 5] [3 5]})

(defn- some-to-bool [pred list]
  (true? (some pred list)))

(defn symmetric-difference [set-1 set-2]
  (clojure.set/union
    (clojure.set/difference set-1 set-2)
    (clojure.set/difference set-2 set-1)))

(facts "should return the difference"
       (symmetric-difference #{1 2 3 4 5 6} #{1 3 5 7}) => #{2 4 6 7})

(defn add-adiacent [xs]
  (if (= 1 (count xs))
    []
    (map #(apply + %) (partition 2 1 xs))))

(facts "is should add adiacent numbers"
       (add-adiacent [1 2 3 4]) => [3 5 7]
       (add-adiacent [1 1]) => [2]
       (add-adiacent [1 3 3 1]) => [4 6 4])

(defn pascal [n]
  (loop [result [1]
         index   1]
    (if (= index n)
      result
      (recur (concat [1] (add-adiacent result) [1]) (inc index)))))

(facts "should calculate pascal"
       (pascal 1) => [1]
       (pascal 11) => [1 10 45 120 210 252 210 120 45 10 1])