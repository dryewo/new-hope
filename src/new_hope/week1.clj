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



(def sum-it-up #(reduce + %))

(facts "about sum-it-up"
       (sum-it-up [1 2 3]) => 6
       (sum-it-up (list 0 -2 5 5)) => 8
       (sum-it-up #{4 2 1}) => 7,
       (sum-it-up '(0 0 -1)) => -1
       (sum-it-up '(1 10 3)) => 14
       ;Edge cases
       (sum-it-up '(1)) => 1
       (sum-it-up '()) => 0
       )



(def find-odd #(filter odd? %))

(facts "about find-odd"
       (find-odd #{1 2 3 4 5}) => '(1 3 5)
       ;Edge cases
       (find-odd '(1)) => '(1)
       (find-odd '(2)) => '()
       (find-odd '()) => '()
       )



(defn palindrom? [p]
  (if (empty? p)
    true
    (= (seq p) (reverse p))))

(facts "about palindrom"
       (palindrom? '(1 2 3 4 5)) => false
       (palindrom? "racecar") => true
       (palindrom? "lezunasanuzel") => true
       (palindrom? "clojure") => false
       (palindrom? '(1 1 3 3 1 1)) => true
       (palindrom? '(:a :b :c)) => false
       (palindrom? '(1 10 3)) => false
       ;Edge cases
       (palindrom? '(1)) => true
       (palindrom? '()) => true
       )



(defn duplicate-a-sequence
  [coll]
  (mapcat #(list % %) coll))
  ;  (reduce #(conj %1 %2 %2) [] coll))

(facts "about duplicate-a-sequence"
       (duplicate-a-sequence [1 2 3]) => '(1 1 2 2 3 3)
       (duplicate-a-sequence '(:a :a :b :b)) => '(:a :a :a :a :b :b :b :b)
       (duplicate-a-sequence [[1 2] [3 4]]) => '([1 2] [1 2] [3 4] [3 4])
       ;Lazyness test
       (take 6 (duplicate-a-sequence (range))) => '(0 0 1 1 2 2)
       ;Edge cases
       (duplicate-a-sequence '()) => '()
       )



;; Day 5

(defn compress [coll]
  (loop [input (seq coll) result []]
    (if (empty? input)
      result
      (if (= (first input) (last result))
        (recur (rest input) result)
        (recur (rest input) (conj result (first input)))))))

(facts "about compress"
       (apply str (compress "Leeeeeerrroyyy")) => "Leroy"
       (compress [1 1 2 3 3 2 2 3]) => '(1 2 3 2 3)
       (compress [[1 2] [1 2] [3 4] [1 2]]) => '([1 2] [3 4] [1 2])
       ;Edge cases
       (compress []) => '())