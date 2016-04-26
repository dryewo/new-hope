(ns new-hope.week2
  (:require [midje.sweet :refer :all]))

;; Fibonacci Sequence

(defn fib [x]
  (loop [prev 0
         current 1
         acc []]
    (if (= x (count acc))
        acc
        (recur current
               (+ prev current)
               (conj acc current)))))

(def fibonacci-sequence
  ((fn fib [prev next] (lazy-seq (cons prev (fib next (+ prev next))))) 1 1))


(comment
  (fib 0)
  (fib 1)
  (fib 2)
  (fib 7)
  (take 8 fibonacci-sequence)
  )

(facts "problem 26"
       (fib 0) => '()
       (fib 1) => '(1)
       (fib 2) => '(1 1)
       (fib 3) => '(1 1 2)
       (fib 8) => '(1 1 2 3 5 8 13 21))

;; Get the Caps

(defn capitals [text]
  (apply str (filter #(Character/isUpperCase %) (seq text))))

(comment
  (capitals "kOisa")
  (capitals "HeLlO, WoRlD!")
  )

(facts "problem 29"
       (capitals "HeLlO, WoRlD!") => "HLOWRD")

;; Factorial Fun

(defn factorial-fun [x]
  (apply * (range 1 (+ 1 x))))

(factorial-fun 8)

(facts "factorial fun"
       (factorial-fun 1) => 1
       (factorial-fun 3) => 6
       (factorial-fun 8) => 40320
       )

;; Intro to Destructuring

(= [2 4] (let [[a b c d e] (range)] [c e]))


;; A Half-Truth

(defn half-truth [& bools]
  (not= (reduce #(and %1 %2) bools)
        (reduce #(or %1 %2) bools)))

(= false (half-truth false false))

(= false (half-truth true))

(= false (half-truth true true true))

(= true (half-truth false true false))

(= true (half-truth true true true false))


;; Greatest Common Divisor

(defn divisors [n]
  (filter #(= 0 (rem n %)) (range 1 n)))


(defn common-divisors [x y]
  (set (concat (divisors x) (divisors y))))

(apply max (common-divisors 1023 858))

(defn gcd [a b]
  (loop [a a
         b b]
    (if (= 0 b)
      a
      (recur b (rem a b)))))

(facts "Greatest Common Divisor"
       (gcd 1023 858) => 33)

;; Symmetric Difference

(defn difference [a b]
  (filter #(not (some #{%} b)) a))


(defn sym_diff [a b]
  (set (concat (difference a b) (difference b a))))


(comment
  (difference [0 1 2 3 6] [ 2 3 4 5])
  (sym_diff [0 2 3 4] [1 5 3 4])
  )

(facts "Symmetric Difference"
       (sym_diff #{1 2 3 4 5 6} #{1 3 5 7}) => #{2 4 6 7})
