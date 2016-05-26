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
  (= #{:a :b :c :d} (set '(:a :a :b :c :c :c :c :d :d)))
  (= 7 (let [x 5] (+ 2 x)))
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

;; Homework

;; Sum It All Up
(comment
  (defn sum [coll]
    (if (empty? coll)
      0
      (+ (first coll) (sum (rest coll)))))
  )

(defn sum [coll]
  (loop [c coll total 0]
    (if (empty? c)
      total
      (recur (rest c) (+' (first c) total)))))


(facts "about sum"
       (sum [1 2 3]) => 6
       (sum (list 0 -2 5 5)) => 8
       (sum #{4 2 1}) => 7
       (sum '(0 0 -1)) => -1
       (sum '(1 10 3)) => 14)

;; Find the odd numbers
(defn odd-nums [coll] (filter odd? coll))

(facts "about odd-nums"
       (odd-nums #{1 2 3 4 5}) => '(1 3 5)
       (odd-nums [4 2 1 6]) => '(1)
       (odd-nums [2 2 4 6]) => '()
       (odd-nums [1 1 1 3]) => '(1 1 1 3))

;; Palindrome Detector
(defn palindrom? [x] (= (seq x) (reverse (seq x))))

(facts "about palindrom?"
       (palindrom? '(1 2 3 4 5)) => false
       (palindrom? "racecar") => true
       (palindrom? [:foo :bar :foo]) => true
       (palindrom? '(1 1 3 3 1 1)) => true
       (palindrom? '(:a :b :c)) => false)

;; Duplicate a Sequence
(comment
  (defn dup-seq [coll]
    (if (empty? coll)
      '()
      (conj (dup-seq (rest coll)) (first coll) (first coll))))
  )

(defn dup-seq [coll]
  (loop [c coll r []]
    (if (empty? c)
      r
      (recur (rest c) (conj r (first c) (first c))))))

(facts "about dup-seq"
       (dup-seq [1 2 3]) => '(1 1 2 2 3 3)
       (dup-seq [:a :a :b :b]) => '(:a :a :a :a :b :b :b :b)
       (dup-seq [[1 2] [3 4]]) => '([1 2] [1 2] [3 4] [3 4])
       (dup-seq [[1 2] [3 4]]) => '([1 2] [1 2] [3 4] [3 4]))

;; Day 5

;; Compress a Sequence
(defn compress-seq [coll] (map first (partition-by identity coll)))

(facts "about compress-seq"
       (apply str (compress-seq "Leeeeeerrroyyy")) => "Leroy"
       (compress-seq [1 1 2 3 3 2 2 3]) => '(1 2 3 2 3)
       (compress-seq [[1 2] [1 2] [3 4] [1 2]]) => '([1 2] [3 4] [1 2]))

;; Pack a Sequence
(comment
  (defn pack-seq [coll] (partition-by identity coll))
  )

(defn pack-seq [coll]
  (loop [c coll r [] group [] last (first c)]
    (if (empty? c)
      (if (empty? group) r (conj r group))
      (if (= last (first c))
        (recur (rest c) r (conj group (first c)) (first c))
        (recur (rest c) (conj r group) [(first c)] (first c))
        )
      )))

(facts "about pack-seq"
       (pack-seq [1 1 2 1 1 1 3 3]) => '((1 1) (2) (1 1 1) (3 3))
       (pack-seq [:a :a :b :b :c]) => '((:a :a) (:b :b) (:c))
       (pack-seq [[1 2] [1 2] [3 4]]) => '(([1 2] [1 2]) ([3 4]))
       (pack-seq [1]) => '((1))
       (pack-seq []) => '())

;; Drop Every Nth Item
(comment
  (defn drop-nth [coll n]
    (flatten (map #(if (= (count %) n) (butlast %) %) (partition-all n coll))))
  )
(comment
  (defn drop-nth [coll n]
    (apply concat (map #(if (= (count %) n) (butlast %) %) (partition-all n coll))))
  )

(defn drop-nth [coll n]
  (loop [c coll r [] cnt 1]
    (if (empty? c)
      r
      (if (= cnt n)
        (recur (rest c) r 1)
        (recur (rest c) (conj r (first c)) (inc cnt))))))

(facts "about drop-nth"
       (drop-nth [1 2 3 4 5 6 7 8] 3) => [1 2 4 5 7 8]
       (drop-nth [:a :b :c :d :e :f] 2) [:a :c :e]
       (drop-nth [1 2 3 4 5 6] 4) [1 2 3 5 6])

;; Intro to Iterate
(comment
  (take 5 (iterate inc 1))
  (= '(1 4 7 10 13) (take 5 (iterate #(+ 3 %) 1)))
  )

;; Replicate a Sequence
(defn repl-seq [coll n] (flatten (map #(take n (repeat %)) coll)))

(facts "about repl-seq"
       (repl-seq [1 2 3] 2) '(1 1 2 2 3 3)
       (repl-seq [:a :b] 4) '(:a :a :a :a :b :b :b :b)
       (repl-seq [4 5 6] 1) '(4 5 6)
       (repl-seq [[1 2] [3 4]] 2) '([1 2] [1 2] [3 4] [3 4])
       (repl-seq [44 33] 2) [44 44 33 33])