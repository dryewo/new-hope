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
  (conj '(3 4) 2 1))


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
  (last [1 2 3]))


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
    (crit/quick-bench (last (butlast coll)))))


;; http://www.4clojure.com/problem/24
(def sum-it-all-up
  (fn [coll]
    (apply + coll)))

(facts "about sum-it-all-up"
       (sum-it-all-up [1 2 3]) => 6
       (sum-it-all-up (list 0 -2 5 5)) => 8
       (sum-it-all-up #{4 2 1}) => 7
       (sum-it-all-up '(0 0 -1)) => -1
       (sum-it-all-up '(1 10 3)) => 14)

;;http://www.4clojure.com/problem/25
(def filter-odd
  (fn [coll]
    (filter odd? coll)))

(facts "about filter-odd"
       (filter-odd #{1 2 3 4 5}) => '(1 3 5)
       (filter-odd [4 2 1 6]) => '(1)
       (filter-odd [2 2 4 6]) => '()
       (filter-odd [1 1 1 3]) => '(1 1 1 3))

;;http://www.4clojure.com/problem/27
(def palindrome?
  (fn [coll]
    (= (apply str (reverse coll)) (apply str coll))))

(facts "about palindrome-detect"
       (palindrome? '(1 2 3 4 5)) => false
       (palindrome? "racecar") => true
       (palindrome? [:foo :bar :foo]) => true
       (palindrome? '(1 1 3 3 1 1)) => true
       (palindrome? '(:a :b :c)) => false)

;;http://www.4clojure.com/problem/32
(def duplicate-a-seq
  (fn [coll] (sort (apply conj coll coll))))

(facts "about duplicate-a-seq"
       (duplicate-a-seq [1 2 3]) => '(1 1 2 2 3 3)
       (duplicate-a-seq [:a :a :b :b]) => '(:a :a :a :a :b :b :b :b)
       (duplicate-a-seq [[1 2] [3 4]]) => '([1 2] [1 2] [3 4] [3 4])
       (duplicate-a-seq [[1 2] [3 4]]) => '([1 2] [1 2] [3 4] [3 4]))

;; Day 5
;;http://www.4clojure.com/problem/30
(def compress-seq
  (fn [coll]
    (loop [in coll
           out []]
      (if (not (seq in))
        out
        (if (= (first in) (first (rest in)))
          (recur (rest in) out)
          (recur (rest in) (conj out (first in))))))))

(facts "about compress-seq"
       (apply str (compress-seq "Leeeeeerrroyyy")) => "Leroy"
       (compress-seq [1 1 2 3 3 2 2 3]) => '(1 2 3 2 3)
       (compress-seq [[1 2] [1 2] [3 4] [1 2]]) => '([1 2] [3 4] [1 2]))

;; http://www.4clojure.com/problem/31
(def pack-seq
  (fn [x y]
    (if (not (seq? x))
      (if (= x y)
        (list x y))
      (if (= (first (last x)) y)
        (let [last_element (last x)]
          (conj (drop-last x) (conj last_element y)))
        (conj x (list y))))))

(defn reduce-coll [coll]
  (reductions pack-seq coll))


(facts "about reduce-coll"
       (reduce-coll [1 1 2 1 1 1 3 3]) => '((1 1) (2) (1 1 1) (3 3)))