(ns new-hope.week1
  (:require [midje.sweet :refer :all]
            [clojure.set :as set]
            [clojure.string :as string]
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

; Day 1
(facts (.toUpperCase "hello world") => "HELLO WORLD")
(facts (list :a :b :c) => '(:a :b :c))
(facts (conj '(2 3 4) 1) => '(1 2 3 4))
(facts (conj '(3 4) 2 1) => '(1 2 3 4))
(facts (list :a :b :c) => '(:a :b :c)
       (vec '(:a :b :c)) => '(:a :b :c)
       (vector :a :b :c) => '(:a :b :c))
(facts (conj [1 2 3] 4) => [1 2 3 4])
(facts (conj [1 2] 3 4) => [1 2 3 4])
(facts (set '(:a :a :b :c :c :c :c :d :d)) => #{:a :b :c :d})
(facts (set/union #{:a :b :c} #{:b :c :d}) => #{:a :b :c :d})
(facts (conj #{1 4 3} 2) => #{1 2 3 4})
(facts ((hash-map :a 10 :b 20 :c 30) :b) => 20)
(facts (:b {:a 10, :b 20, :c 30}) => 20)
(facts (first '(3 2 1)) => 3)
(facts (second [2 3 4]) => 3)
(facts (last (list 1 2 3)) => 3)

; Day 2
(facts (rest [10 20 30 40]) => [20 30 40])
(facts ((fn add-five [x] (+ x 5)) 3) => 8)
(facts ((fn [x] (+ x 5)) 3) => 8)
(facts (#(+ % 5) 3) => 8)
(facts ((partial	+	5) 3) => 8)
(facts (#(* % 2)	2)	=> 4)
(facts (#(* % 2)	3) => 6)
(facts (#(* % 2) 11) => 22)
(facts (#(* % 2)	7) =>	14)
(facts (#(string/join ["Hello, " % "!"]) "Dave") => "Hello, Dave!")
(facts (#(string/join ["Hello, " % "!"]) "Jenn") => "Hello, Jenn!")
(facts (#(string/join ["Hello, " % "!"]) "Rhea") => "Hello, Rhea!")
(facts (map #(+ % 5) '(1 2 3)) => '(6 7 8))
(facts (filter #(> % 5) '(3 4 5 6 7)) => '(6 7))
(facts (let [x 5] (+ 2 x)) => 7)
(facts (let [x 3, y 10] (- y x)) => 7)
(facts (let [x 21] (let [y 3] (/ x y))) => 7)
(facts (let [x 5 y 5] (+ x y)) => 10)
(facts (let [y 2 z 2] (+	y	z)) => 4)
(facts (let [z 1] z) => 1)

; Day 3
(facts (apply str (re-seq #"[A-Z]+" "bA1B3Ce ")) => "ABC")
(facts ((fn foo [x] (when (> x 0) (conj (foo (dec x)) x))) 5) => '(5 4 3 2 1))
(facts (loop [x 5 result []]
        (if (> x 0)
          (recur (dec x) (conj result (+ 2 x)))
          result))
       => [7 6 5 4 3])
(facts
  (last (sort (rest (reverse	[2 5 4 1 3 6]))))
     (->	[2 5 4 1 3 6] (reverse) (rest) (sort) (last))
     => 5)
(facts
  (apply + (map inc (take 3 (drop 2 [2 5 4 1 3 6]))))
  => (->> [2 5 4 1 3 6] (drop 2) (take 3) (map inc) (reduce +))
  => 11)

(facts (for [x (range 40)
             :when (= 1 (rem x 4))]
         x)
       => '(1 5 9 13 17 21 25 29 33 37) )

(facts (for [x (iterate #(+ 4 %) 0)
             :let [z (inc x)]
             :while (< z 40)]
         z)
       => '(1 5 9 13 17 21 25 29 33 37))

(facts (for [[x y] (partition 2 (range 20))]
         (+ x y))
       => '(1 5 9 13 17 21 25 29 33 37))

;; Day 4

(def second-to-last
  (fn [coll]
    (second (reverse coll))))

(facts "about second-to-last"
  (second-to-last [1 2 3 4]) => 3
  (second-to-last [1]) => nil
  (second-to-last nil) => nil)

(defn sum-it-all-up
  "returns the sum of a sequence of numbers"
  [x]
   (apply + x))

(facts "sums all the elements in the coll"
       (sum-it-all-up	[1 2 3]) => 6
       (sum-it-all-up	(list	0	-2 5 5)) => 8
       (sum-it-all-up	#{4	2 1}) => 7
       (sum-it-all-up	'(0 0 -1)) => -1
       (sum-it-all-up	'(1 10 3)) => 14
)

(defn find-the-odd-numbers
  "Returns a coll with the odd numbers of the argument coll"
  [x]
   (filter odd? x))

(facts "finds the odd numbers in coll"
        (find-the-odd-numbers #{1 2 3 4 5})	=> '(1 3 5)
        (find-the-odd-numbers [4 2 1 6])	=> '(1)
        (find-the-odd-numbers [2 2 4 6])	=> '()
        (find-the-odd-numbers [1 1 1 3])	=> '(1 1 1 3))

(defn palindrome-detector
  "returns true if the argument is a palindrome"
  [x]
  (= (reverse x) (vec x)))

(facts "determines palindrome colls"
       (palindrome-detector '(1 2 3 4 5)) => false
       (palindrome-detector "racecar") => true
       (palindrome-detector [:foo :bar :foo]) => true
       (palindrome-detector '(1	1	3	3	1	1)) => true
       (palindrome-detector '(:a :b :c)) => false)

(defn duplicate-a-sequence
  "returns the argument sequence with each element duplicated"
  [x]
  (sort(concat x x)))

(facts "returns colls of duplicated elements"
       (duplicate-a-sequence [1 2 3]) => '(1 1 2 2 3 3)
       (duplicate-a-sequence [:a :a :b :b]) => '(:a :a :a :a :b :b :b :b)
       (duplicate-a-sequence [[1 2] [3 4]]) => '([1 2] [1 2] [3 4] [3 4]))

;; Day 5

(defn compress-a-sequence
  "eliminates all repeated elements from a sequence"
  [x]
  (loop [input x result []]
    (if (not (empty? input))
      (if (= (last result) (first input))
        (recur (rest input) result)
        (recur (rest input) (conj result (first input)))
       )
      result)
  )
)

(facts  (apply str (compress-a-sequence "Leeeeeerrroyyy")) => "Leroy"
        (compress-a-sequence [1 1 2 3 3 2 2 3]) => '(1 2 3 2 3)
        (compress-a-sequence [[1 2] [1 2] [3 4] [1 2]]) => '([1 2] [3 4] [1 2]))

(defn pack-a-sequence
  "packs consecutive duplicates into sublist"
  [x]
  (loop [input x result '()]
    (if (not (empty? input))
      (recur (rest input) (if (= (last (last result)) (first input))
                            (concat (butlast result) (list (conj (last result) (first input))))
                            (concat result (list (list (first input))))
                            )
             )
      result)
    )
  )

(facts (pack-a-sequence [1 1 2 1 1 1 3 3]) => '((1 1) (2) (1 1 1) (3 3))
  (pack-a-sequence [:a :a :b :b :c]) => '((:a :a) (:b :b) (:c))
       (pack-a-sequence [[1 2] [1 2] [3 4]]) => '(([1	2] [1 2]) ([3 4])))

(defn drop-every-nth-item
  "Drops every n-th item of a coll"
  [x nth]
  (loop [input x result []]
    (if (not (empty? input))
      (let [nthpack (take nth input)]
        (if (= (count nthpack) nth)
          (recur (drop nth input) (concat result (butlast nthpack)))
          (recur (drop nth input) (concat result nthpack)))
      )
      result
    )
  )
)

(facts (drop-every-nth-item [1 2 3 4 5 6 7 8]	3) => [1 2 4 5 7 8]
        (drop-every-nth-item [:a :b :c :d :e :f] 2) => [:a :c :e]
        (drop-every-nth-item [1	2	3	4	5	6] 4) => [1 2 3 5 6])

(defn replicate-a-sequence
  "replicates each element of a sequence a variable number of times"
  [x y]
  ())

(facts (replicate-a-sequence [1 2 3] 2)	=> '(1 1 2 2 3 3)
        (replicate-a-sequence[:a :b] 4) => '(:a :a :a :a :b :b :b :b)
        (replicate-a-sequence [4 5 6] 1) => '(4 5 6)
        (replicate-a-sequence [[1 2] [3 4]] 2) =>  '([1 2] [1 2] [3 4] [3 4])
        (replicate-a-sequence [44 33] 2) => [44 44 33 33])