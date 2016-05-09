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

;HOMEWORK /m\
;intro to strings
(= "HELLO WORLD" (.toUpperCase "hello world"))

;intro to lists
(= (list ':a ':b ':c) '(:a :b :c))

;Lists:conj
(= '(1 2 3 4) (conj '(2 3 4) 1))
(= (list '1 '2 '3 '4) (conj '(3 4) 2 1))

;Vectors
(= [:a :b :c]
   (list :a :b :c)
   (vec '(:a :b :c))
   (vector :a :b :c))

;Vectors: conj
(= [1 2 3 4] (conj [1 2 3] 4))
(= (vector 1 2 3 4) (conj [1 2] 3 4))

;Sets (collections of unique values)
(= #{:c :b :d :a} (set '(:a :a :b :c :c :c :c :d :d)))
(= #{:c :b :d :a} (clojure.set/union #{:a :b :c} #{:b :c :d}))

;Sets: conj
(= #{1 4 #{1 4 3 2} 3} (conj #{1 4 3} #{1 2 3 4}))

;intro to maps (key-value pairs)
(= 20 ((hash-map :a 10, :b 20, :c 30) :b))
(= 20 (:b {:a 10, :b 20, :c 30}))

;Maps: conj
(= {:a 1, :b 2, :c 3} (conj {:a 1} (hash-map :b 2) [:c 3]))

;intro to sequences
(= 3 (first '(3 2 1)))
(= 3 (second [2 3 4]))
(= 3 (last (list 1 2 3)))

;sequences: rest
(= '(20 30 40) (rest [10 20 30 40]))

;intro to functions
(= 8 ((fn add-five [x] (+ x 5)) 3))
(= 8 ((fn [x] (+ x 5)) 3))
(= 8 (#(+ % 5) 3))
(= 8 ((partial + 5) 3))

;double down: write function which doubles a number
(= (#(* % 2) 2) 4)
(= ((fn [x] (* x 2)) 3) 6)
(= ((partial * 2) 11) 22)
(= ((fn doble-down [x] (* x 2)) 7) 14)

;hello world - personalized greeting
(= (#(str "Hello, " % "!") "Dave") "Hello, Dave!")
(= ((fn [x] (str "Hello, " x "!")) "Jenn") "Hello, Jenn!")
(= ((fn hello [x] (str "Hello, " x "!")) "Rhea") "Hello, Rhea!")

;map function
(= '(6 7 8) (map #(+ % 5) '(1 2 3)))

;filter function
(= '(6 7) (filter #(> % 5) '(3 4 5 6 7)))

;let
(= 7 (let [x 5] (+ 2 x)))
(= 7 (let [x 3, y 10] (- y x)))
(= 7 (let [x 21] (let [y 3] (/ x y))))

;let it be
(= 10 (let [x 7, y 3] (+ x y)))
(= 4 (let [y 3, z 1] (+ y z)))
(= 1 (let [z 1] z))

;regex
(= "ABC" (apply str (re-seq #"[A-Z]+" "bA1B3Ce ")))

;recursive function
(= '(5 4 3 2 1) ((fn foo [x] (when (> x 0) (conj (foo (dec x)) x))) 5))

;tail recursion
(= [7 6 5 4 3]
   (loop [x 5
          result []]
     (if (> x 0)
       (recur (dec x) (conj result (+ 2 x)))
       result)))

;thread-first macro
(= (last (sort (rest (reverse [2 5 4 1 3 6]))))
   (-> [2 5 4 1 3 6] (reverse) (rest) (sort) (last))
   5)

;thread-last macro
(= (apply + (map inc (take 3 (drop 2 [2 5 4 1 3 6]))))
   (->> [2 5 4 1 3 6] (drop 2) (take 3) (map inc) (apply +))
   11)

;for
(= '(1 5 9 13 17 21 25 29 33 37) (for [x (range 40)
                                       :when (= 1 (rem x 4))]
                                   x))
(= '(1 5 9 13 17 21 25 29 33 37) (for [x (iterate #(+ 4 %) 0)
                                       :let [z (inc x)]
                                       :while (< z 40)]
                                   z))
(= '(1 5 9 13 17 21 25 29 33 37) (for [[x y] (partition 2 (range 20))]
                                   (+ x y)))

;sum it up
(def sum-it-up
  (fn [coll]
    (reduce + coll)))

(= (sum-it-up [1 2 3]) 6)
(= (sum-it-up (list 0 -2 5 5)) 8)
(= (sum-it-up #{4 2 1}) 7)
(= (sum-it-up '(0 0 -1)) -1)
(= (sum-it-up '(1 10 3)) 14)

;find odd numbers
(def find-odds
  (fn [coll]
    (filter odd? coll)))

(= (find-odds #{1 2 3 4 5}) '(1 3 5))
(= (find-odds [4 2 1 6]) '(1))
(= (find-odds [2 2 4 6]) '())
(= (find-odds [1 1 1 3]) '(1 1 1 3))

;palindrome
(def palindrome?
  (fn [x]
    (= (seq x) (reverse x))))

(false? (palindrome? '(1 2 3 4 5)))
(true? (palindrome? "racecar"))
(true? (palindrome? [:foo :bar :foo]))
(true? (palindrome? '(1 1 3 3 1 1)))
(false? (palindrome? '(:a :b :c)))

;duplicate each element in sequence
(def duplicate
  (fn [coll]
    (reverse
      (reduce #(cons %2 (cons %2 %1)) '() coll))))

(= (duplicate [1 2 3]) '(1 1 2 2 3 3))
(= (duplicate [:a :a :b :b]) '(:a :a :a :a :b :b :b :b))
(= (duplicate [[1 2] [3 4]]) '([1 2] [1 2] [3 4] [3 4]))

;compress a sequence (remove duplicates)
(def compress-seq
  (fn [coll]
    (dedupe coll)))

(= (apply str (compress-seq "Leeeeeerrroyyy")) "Leroy")
(= (compress-seq [1 1 2 3 3 2 2 3]) '(1 2 3 2 3))
(= (compress-seq [[1 2] [1 2] [3 4] [1 2]]) '([1 2] [3 4] [1 2]))

;pack sequence
(def pack-seq
  (fn [coll]
    (partition-by identity coll)))

(= (pack-seq [1 1 2 1 1 1 3 3]) '((1 1) (2) (1 1 1) (3 3)))
(= (pack-seq [:a :a :b :b :c]) '((:a :a) (:b :b) (:c)))
(= (pack-seq [[1 2] [1 2] [3 4]]) '(([1 2] [1 2]) ([3 4])))

;find every Nth item
(def drop-nth
  (fn [coll n]
    (keep-indexed
      (fn [i elem]
        (if (not= 0 (mod (inc i) n)) elem nil)) coll)))

(= (drop-nth [1 2 3 4 5 6 7 8] 3) [1 2 4 5 7 8])
(= (drop-nth [:a :b :c :d :e :f] 2) [:a :c :e])
(= (drop-nth [1 2 3 4 5 6] 4) [1 2 3 5 6])

;iterate - replecate a sequence
(def replicate-seq
  (fn [coll n]
    (mapcat #(repeat n %) coll)))

(= (replicate-seq [1 2 3] 2) '(1 1 2 2 3 3))
(= (replicate-seq [:a :b] 4) '(:a :a :a :a :b :b :b :b))
(= (replicate-seq [4 5 6] 1) '(4 5 6))
(= (replicate-seq [[1 2] [3 4]] 2) '([1 2] [1 2] [3 4] [3 4]))
(= (replicate-seq [44 33] 2) [44 44 33 33])