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

;; Day 2
(facts "about functions"
  (rest [10 20 30 40]) => '(20 30 40)
  ;; Add-five
  ((fn add-five [x] (+ x 5)) 3) => 8
  ((fn [x] (+ x 5)) 3) => 8
  (#(+ % 5) 3) => 8
  ((partial + 5) 3) => 8
  ;; Double down
  ((fn [x] (* 2 x)) 2) => 4
  ((fn [x] (* 2 x)) 3) => 6
  (#(* % 2) 11) => 22
  ((partial * 2) 7) => 14
  ;; Hello World
  ((fn [x] (format "Hello, %s!" x)) "Dave") => "Hello, Dave!"
  (#(format "Hello, %s!" %) "Jenn") => "Hello, Jenn!"
  ((partial format "Hello, %s!") "Rhea") => "Hello, Rhea!"
  ;; Map, filter
  (map #(+ % 5) '(1 2 3)) => '(6 7 8)
  (filter #(> % 5) '(3 4 5 6 7)) => '(6 7)
  ;; Local bindings
  (let [x 5] (+ 2 x)) => 7
  (let [x 3, y 10] (- y x)) => 7
  (let [x 21] (let [y 3] (/ x y))) => 7
  ;; Let it be
  (let [x 4 y 6] (+ x y)) => 10
  (let [y 1 z 3] (+ y z)) => 4
  (let [z 1] z) => 1)

;; Day 3
(facts "about recursion and transformations"
  ;; Regex
  (re-seq #"jam" "I like jam in my jam") => '("jam" "jam")
  (apply str [1 2 3]) => "123"
  (apply str (re-seq #"[A-Z]+" "bA1B3C2")) => "ABC"
  ;; Recursion
  ((fn foo [x] (when (> x 0) (conj (foo (dec x)) x))) 5) => '(5 4 3 2 1)
  (loop [x 5
         result []]
    (if (> x 0)
      (recur (dec x) (conj result (+ 2 x)))
      result)) => '(7 6 5 4 3)
  ;; Transforming code
  (.toUpperCase (str (first [:cat :dog :fish]))) => ":CAT"
  (-> [:cat :dog :fish] first str .toUpperCase) => ":CAT"
  (= (last (sort (rest (reverse [2 5 4 1 3 6])))) => (-> [2 5 4 1 3 6] (reverse) (rest) (sort) (last)) 5)
  (->> [1 2 3 4 5 6 7 8] (filter even?) (take 3)) => '(2 4 6)
  (= (reduce + (map inc (take 3 (drop 2 [2 5 4 1 3 6])))) (->> [2 5 4 1 3 6] (drop 2) (take 3) (map inc) (reduce +)) 11)
  (for [x (range 40)
        :when (= 1 (rem x 4))] x) => '(1 5 9 13 17 21 25 29 33 37)
  (for [x (iterate #(+ 4 %) 0)
        :let [z (inc x)]
        :while (< z 40)]
    z) => '(1 5 9 13 17 21 25 29 33 37)
  (for [[x y] (partition 2 (range 20))]
    (+ x y)) => '(1 5 9 13 17 21 25 29 33 37))

;; Day 4
(facts "about logic flows"
  ((fn [xs] (second (reverse xs))) (list 1 2 3 4 5)) => 4)

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

;; Sum it all up
(def sumseq (fn [xs]
              (reduce + xs)))

(facts "about sumseq"
  (sumseq [1 2 3]) => 6
  (sumseq (list 0 -2 5 5)) => 8
  (sumseq #{4 2 1}) => 7
  (sumseq '(0 0 -1)) => -1
  (sumseq '(1 10 3)) => 14)

;; Find the odd numbers
(def onlyodd (fn [xs]
               (filter odd? xs)))

(facts "about onlyodd"
  (onlyodd #{1 2 3 4 5}) => '(1 3 5)
  (onlyodd [4 2 1 6]) => '(1)
  (onlyodd [2 2 4 6]) => '()
  (onlyodd [1 1 1 3]) => '(1 1 1 3))

;; Palindrome Detector
(def palindrome? (fn [s]
                   (= (seq s) (reverse s))))

(fact "about palindrom?"
  (palindrome? '(1 2 3 4 5)) => false
  (palindrome? "racecar") => true
  (palindrome? [:foo :bar :foo]) => true
  (palindrome? '(1 1 3 3 1 1)) => true
  (palindrome? '(:a :b :c)) => false)

;; Duplicate a Sequence
(def dupe (fn [xs]
            (reduce concat (map #(repeat 2 %) xs))))

(facts "about dupe"
  (dupe [1 2 3]) => '(1 1 2 2 3 3)
  (dupe [:a :a :b :b]) => '(:a :a :a :a :b :b :b :b)
  (dupe [[1 2] [3 4]]) => '([1 2] [1 2] [3 4] [3 4]))

;; Day 5
;; Compress a Sequence
(defn removedupes [xs]
  (reduce (fn [xs x]
            (if (not= (last xs) x) (conj xs x) xs))
    [] xs))

(facts "about removedupes"
  (apply str (removedupes "Leeeeeerrroyyy")) => "Leroy"
  (removedupes [1 1 2 3 3 2 2 3]) => '(1 2 3 2 3)
  (removedupes [[1 2] [1 2] [3 4] [1 2]]) => '([1 2] [3 4] [1 2]))

;; Pack a Sequence
(defn packdupes [xs]
  (reduce (fn [xs x]
            (if (= (last (last xs)) x)
              (conj (pop xs) (conj (last xs) x))
              (conj xs [x])))
    [] xs))

(facts "about packdupes"
  (packdupes [1 1 2 1 1 1 3 3]) => '((1 1) (2) (1 1 1) (3 3))
  (packdupes [:a :a :b :b :c]) => '((:a :a) (:b :b) (:c))
  (packdupes [[1 2] [1 2] [3 4]]) => '(([1 2] [1 2]) ([3 4])))

;; Drop Every Nth Item
(defn with-index [xs]
  (map vector (range 1 (inc (count xs))) xs))

(defn dropnth [xs n]
  (reduce (fn [xs [i x]]
            (if (not= (mod i n) 0) (conj xs x) xs))
    [] (with-index xs)))

(facts "about dropnth"
  (dropnth [1 2 3 4 5 6 7 8] 3) => [1 2 4 5 7 8]
  (dropnth [:a :b :c :d :e :f] 2) => [:a :c :e]
  (dropnth [1 2 3 4 5 6] 4) => [1 2 3 5 6])

;; Intro to Iterate
(facts "about iterate"
  (take 5 (iterate #(+ 3 %) 1)) => '(1 4 7 10 13))

;; Replicate a Sequence
(defn replseq [xs n]
  (reduce (fn [xs x]
            (concat xs (take n (iterate identity x))))
    [] xs))

(facts "about replseq"
  (replseq [1 2 3] 2) => '(1 1 2 2 3 3)
  (replseq [:a :b] 4) => '(:a :a :a :a :b :b :b :b)
  (replseq [4 5 6] 1) => '(4 5 6)
  (replseq [[1 2] [3 4]] 2) => '([1 2] [1 2] [3 4] [3 4])
  (replseq [44 33] 2) => [44 44 33 33])
