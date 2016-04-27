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


;; Day 3

(re-seq #"jam" "I like jam in my jam")

(apply str [1 2 3])

(apply str (re-seq #"A-Z+" "AadfBdfasZaa"))

(.toUpperCase (str (first [:cat :dog :fish])))

(-> [:cat :dog :fish]
    first
    str
    .toUpperCase)

(->> [1 2 3 4 5 6 7 8]
     (filter odd?)
     (take 2))

(for [x (range 40)
        :when (= 1 (rem x 4))]
  x)

(for [x (iterate #(+ 4 %) 0)
      :let [z (inc x)]
      :while (< z 40)]
  x)

(for [[x y] (partition 2 (range 20))]
  [x y])

;; Day 4

(defn second-to-last [col]
  (if (< (count col) 2)
    nil
    (nth col (- (count col) 2))))

(facts "should return the second to last"
       (second-to-last (list 1 2 3 4 5)) => 4
       (second-to-last ["a" "b" "c"]) => "b"
       (second-to-last [[1 2] [2 3]]) => [1 2]
       (second-to-last []) => nil
       (second-to-last [1]) => nil)

(defn sum [col]
  (reduce + col))

(facts "should return the sum"
       (sum [1 2 3]) => 6)

(defn odd-numbers [col]
  (filter odd? col))

(facts "should return the odd numbers"
       (odd-numbers #{1 2 3 4 5}) => '(1 3 5))

(defn palindrome [col]
  (let [size (count col)
        half-count (quot size 2)
        nr-of-dropped (if (even? size) half-count (inc half-count))
        first-half (take half-count col)
        second-half (drop nr-of-dropped col)]
    (= first-half (reverse second-half))))

(facts "should return true for palindrome"
       (palindrome "racecar") => true
       (palindrome [1 2 3 4 5]) => false
       (palindrome [1 2 3 2 1]) => true)

(defn dupl [col]
  (flatten (map (fn [x] [x x]) col)))

(facts "should duplicate elements"
       (dupl [1 2 3]) => '(1 1 2 2 3 3))

(palindrome [1 2 4 5])

;; Day 5

(defn compress [col]
  (if (empty? col)
    col
    (loop [elem (first col)
          rest-col (rest col)
          result []]
     (if (empty? rest-col)
       (conj result elem)
       (let [new-elem (first rest-col)
             new-result (if (= elem new-elem)
                          result
                          (conj result elem))]
         (recur new-elem (rest rest-col) new-result))))))

(facts "should compress a seq"
       (compress [1]) => [1]
       (compress []) => []
       (compress [1 1 2 2 3 3 3 4 4 5 3]) => [1 2 3 4 5 3])

(defn drop-nth [col n]
  (filter #(not= 0 (rem % n)) col))

(facts "should drop every nth element"
       (drop-nth [1 2 3 4 5 6 7 8] 3) => [1 2 4 5 7 8])


(take 5 (iterate inc 1))

(defn replic [col n]
  (flatten (map #(take n (iterate identity %)) col)))

(facts "should replace each element a number of times"
       (replic [1 2 3] 2) => [1 1 2 2 3 3])

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
