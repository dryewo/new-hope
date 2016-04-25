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
  )

;; For Week 1 days 1-3 just play around a bit, use comment form to write down expressions

;; Unit test is just an expression that declares expectations using a simple DSL
;; The whole form returns true or false when evaluated
(facts "some facts"
  (second [2 3 4]) => 3)

;; Day 1
(facts "about data structures"
  ; Strings
  (.toUpperCase "hello world") => "HELLO WORLD"
  ; Lists
  '(:a :b :c) => (list :a :b :c)
  (conj '(2 3 4) 1) => '(1 2 3 4)
  '(1 2 3 4) => (conj '(3 4) 2 1)
  ; Vectors
  (= [:a :b :c] (list :a :b :c)) (vec '(:a :b :c)) (vector :a :b :c)
  (conj [1 2 3] 4) => [1 2 3 4]
  (conj [1 2] 3 4) => [1 2 3 4]
  ; Sets
  (set '(:a :a :b :c :c :c :c :d :d)) => #{:a :b :c :d}
  (clojure.set/union #{:a :b :c} #{:b :c :d}) => #{:a :b :c :d}
  (conj #{1 4 3} 2) => #{1 2 3 4}
  ; Maps
  ((hash-map :a 10, :b 20, :c 30) :b) => 20
  (:b {:a 10, :b 20, :c 30}) => 20
  (conj {:a 1} {:b 2} [:c 3]) => {:a 1, :b 2, :c 3}
  ; Sequences
  (first '(3 2 1)) => 3
  (second [2 3 4]) => 3
  (last (list 1 2 3)) => 3)

;; Day 4

(def second-to-last
  (fn [coll]
    (second (reverse coll))))

(comment
  (reverse [1 2 3 4])
  (second [4 3 2 1])
  (first (next [4 3 2 1]))
  (butlast [1 2 3 4])
  (last [1 2 3])
  )

(facts "about second-to-last"
  (second-to-last [1 2 3 4]) => 3
  (second-to-last [1]) => nil
  (second-to-last nil) => nil)

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
