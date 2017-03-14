(ns new-hope.week1
  (:require [midje.sweet :refer :all]
            [criterium.core :as crit]))

;; After launching the REPL always run (refresh) at least once

;; `comment` always returns nil and does not evaluate its arguments
;; Might be handy to type expressions here and send them to REPL
;;  instead of typing them in REPL directly
(comment
  (- 10 (* 2 3))
  (.toUpperCase "hello world")
  (conj '(2 3 4) 1)
  (conj '(3 4) 2 1)
  (conj [1 2 3] 100)
  (println "Hello")
  )

;; For Week 1 days 1-3 just play around a bit, use comment form to write down expressions

;; Unit test is just an expression that declares expectations using a simple DSL
;; The whole form returns true or false when evaluated
(facts "some facts"
       (second [2 3 4]) => 3)

(facts "some facts"
       (first [2 3 4]) => 2)

;; Day 4

(def second-to-last
  (fn [coll]
    (second (reverse coll))))

(comment
  (= __ (set '(:a :a :b :c :c :c :c :d :d)))
  (= __ (let [x 5] (+ 2 x)))
  )

(comment
  ;(= (__ [1 2 3]) '(1 1 2 2 3 3))

  (second-to-last [4 5 6 7])
  (reverse [1 2 3 4])
  (second [4 3 2 1])
  (first (next [4 3 2 1]))
  (butlast [1 2 3 4])   

  (last [1 2 3])
  (filter odd? [1 2 3])
  )

(facts "about second-to-last"
       (second-to-last [1 2 3 4]) => 3
       (second-to-last [1]) => nil
       (second-to-last nil) => nil
       (second-to-last []) => nil)

(facts "vectors"
       ['(:a :b :c) [:a :b :c] [:a :b :c]]
       => [(list :a :b :c) (vec '(:a :b :c)) (vector :a :b :c)])

(facts nil false)

(comment
  ;; Simple time measurement (run once)
  (let [coll (vec (range 10000))]
    (time (second (reverse coll)))
    (time (last (butlast coll))))
  ;; More elaborate benchmark taking 10 seconds
  (let [coll (vec (range 10000))]
    (crit/quick-bench (second (reverse coll)))
    (crit/quick-bench (last (butlast coll))))
  )

(defn sum-it-all-up
  [col]
  (reduce + col))

(defn find-the-odd
  [col]
  (filter odd? col))

(defn is-palindrome
  [col]
  (let [reverse-fun (if (string? col) clojure.string/reverse reverse)]
    (= (reverse-fun col) col)))

(defn duplicate
  [col]
  (reverse
    (reduce (fn [acc element]
              (conj acc element element)) '() col)))

(defn deduplicate
  [col]
  (let [reverse-fun (if (string? col) #(reverse (apply str %)) reverse)]
    (reverse-fun
      (reduce
        (fn
          [acc element]
          (if (not= (first acc) element)
            (conj acc element)
            acc))
        '() col))))
