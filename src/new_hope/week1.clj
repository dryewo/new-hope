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

;; Day 4

(def second-to-last
  (fn [coll]
    (second (reverse coll))))

(comment
  (= __ (set '(:a :a :b :c :c :c :c :d :d)))
  (= __ (let [x 5] (+ 2 x)))
  )

(comment
  (second-to-last [4 5 6 7])
  (reverse [1 2 3 4])
  (second [4 3 2 1])
  (first (next [4 3 2 1]))
  (butlast [1 2 3 4])
  (last [1 2 3])
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
;; sum it up
(defn sum-it-all [xs] (reduce + xs))
(facts "sum it all test"
  (sum-it-all [1 2 3]) => 6
  (sum-it-all (list 0 -2 5 5)) => 8
  (sum-it-all #{4 2 1}) => 7
  (sum-it-all '(0 0 -1)) => -1
  (sum-it-all '(1 10 3)) => 14
)
;; odds only
(defn odds-only [xs] (filter odd? xs))

(facts "odds only test"
       (odds-only #{1 2 3 4 5}) => '(1 3 5)
       (odds-only [4 2 1 6]) => '(1)
       (odds-only [2 2 4 6]) => '()
       (odds-only [1 1 1 3]) => '(1 1 1 3)

       )
;; palindrome detector
(defn palindrome? [xs] (= (seq xs) (reverse (seq xs))))
(facts "palindrome"
       (palindrome? '(1 2 3 4 5)) => false
       (palindrome? "racecar") => true
       (palindrome? [:foo :bar :foo]) => true
       (palindrome? '(1 1 3 3 1 1)) => true
       (palindrome? '(:a :b :c)) => false
)
;; dublicate sequence
(defn duplicate [xs]
  (reduce concat (map #(take 2 (repeat %)) xs)))
(facts "duplicate test"
       (duplicate [1 2 3]) => '(1 1 2 2 3 3)
       (duplicate [:a :a :b :b]) => '(:a :a :a :a :b :b :b :b)
       (duplicate [[1 2] [3 4]]) => '([1 2] [1 2] [3 4] [3 4])
       (duplicate [[1 2] [3 4]]) => '([1 2] [1 2] [3 4] [3 4])
       )
