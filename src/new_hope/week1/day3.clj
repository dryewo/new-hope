(ns new-hope.week1.day3
  (:require [midje.sweet :refer :all]
            [criterium.core :as crit]))

;Regular	Expressions
(re-seq #"jam" "I like jam in my jam " )
(apply str [1 2 3])
(apply str (re-seq #"[A-Z]+" "bA1B3Ce"))

;Simple	Recursion
((fn foo [x] (when (> x 0) (conj (foo (dec x)) x))) 5)

(loop [x 5
       result []]
  (if (> x 0)
    (recur (dec x) (conj result (+ 2 x)) )
    result))

;Playing around with examples
(def adjs ["small" "big" "extra big"])

(defn alice [in out]
  (if (empty? in)
    out
    (recur
      (rest in)
      (conj out (str "alice is " (first in))))))

(alice adjs [])

(defn countdown [x]
  (if (= x 0) 0
   (recur (- x 1))))

(countdown 10000)

(def  animals  [:mouse  :duck  :dodo  :lory  :eaglet])

(take 5 (map #(str %) (range)))

(map #(println %) adjs)

(take 15 (cycle ["adfa" "sadfsa"]))

(reduce + [1 2 3])

(reduce (fn [r x] (+ r x)) [1 2 3])
