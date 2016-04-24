(ns new-hope.week2
  (:require [midje.sweet :refer :all])
  (:require [clojure.test :refer :all])
  (:require [clojure.set :refer :all])
  )


(defn fibonacci
  "calculates fibonacci sequence up to the given argument"
  [length]
  (take length (map first (iterate (fn [[x y]] [y (+ x y)]) [1 1]))))

;Day 1
(deftest test-fibonacci-sequence
  (is (= (fibonacci 3) '(1 1 2)))
  (is (= (fibonacci 6) '(1 1 2 3 5 8)))
  (is (= (fibonacci 8) '(1 1 2 3 5 8 13 21)))
)

(defn get-the-caps
  "returns only uppercase characters of the argument sequence"
  [x]
  (filter #(Character/isUpperCase %) x)
)

(deftest test-only-uppercase
  (is (get-the-caps "HeLlO, WoRlD!") "HLOWRD")
  (is (empty? (get-the-caps "nothing")) true)
  (is (get-the-caps "$#A(*&987Zf") "AZ")
)

(deftest test-some
  (is 6 (some #{2 7 6} [5 6 7 8]))
  (is 6 (some #(when (even? %) %) [5 6 7 8]))
)

(defn factorial-fun
  "calculates factorial of the argument"
  [x]
  (apply * (range x))
)

(deftest test-factorial-fun
  (is 1 (factorial-fun 1))
  (is 6 (factorial-fun 3))
  (is 120 (factorial-fun 5))
  (is 40320 (factorial-fun 8))
)

(deftest intro-to-destructuring
  (is [2 4] (let [[a b c d e f g]	(range)] [b d]))
)

;Day 2

(deftest advanced-destructuring
  (is [1 2 [3 4 5] [1 2 3 4 5]] (let [[a b & c :as d] [1 2 3 4 5]] [a b c d]))
)

(defn a-half-truth
  "performs the xor of the arguments"
  [& args]
  (and (not-every? true? args) (not (nil? (some true? args))))
)

; why (= nil (and true nil))?
(deftest test-a-half-truth
  (is (= false (a-half-truth false false)))
  (is (= true (a-half-truth true false)))
  (is (= false (a-half-truth true)))
  (is (= true (a-half-truth false true false)))
  (is (= false (a-half-truth true true true)))
  (is (= true (a-half-truth true true true false)))
)
(defn great-common-divisor
  "docstring"
  [x y]
  (if (zero? y)
    x
    (recur y (mod x y))
  )
)

(deftest test-great-common-divisor
  (is (= (great-common-divisor 2 4) 2))
  (is (= (great-common-divisor 10 5) 5))
  (is (= (great-common-divisor 5 7) 1))
  (is (= (great-common-divisor 1023 858) 33))
)

; Day 3

(defn simple-closure
  "returns function that calculates power of the argument"
  [power]
  (fn [base]
    (loop [inputx 1 count 1]
      (if (> count power)
        inputx
        (recur (* inputx base) (inc count))
      )
    )
  )
)

(deftest test-simple-closure
  (is (= 256 ((simple-closure 2) 16) ((simple-closure 8) 2)))
  (is (= [1 8 27 64] (map (simple-closure 3) [1 2 3 4])))
  (is (= [1 2 4 8 16] (map #((simple-closure %) 2) [0 1 2 3 4])))
)

(defn cartesian-product
  "calculates the cartesian product of two sets"
  [a b]
  (set (apply concat (for [element b] (set (map #(conj [%] element) a)))))
)

(deftest test-cartesian-product
  (is (=  #{[1 4] [2 4] [3 4] [1 5] [2 5] [3 5]} (cartesian-product #{1 2 3} #{4 5})))
  (is (= 300 (count (cartesian-product (into #{} (range	10)) (into #{} (range 30)) ))))
  (is (=	(cartesian-product #{"ace" "king" "queen"} #{"spade" "heart" "diamond" "club"})
      #{["ace" "spade"] ["ace" "heart"] ["ace" "diamond"] ["ace" "club"] ["king" "spade"] ["king" "heart"]
        ["king" "diamond"] ["king" "club"] ["queen" "spade"] ["queen" "heart"] ["queen" "diamond"] ["queen" "club"]}))
)

; Day 4

(defn symmetric-difference
  "calculates the symmetric difference of two sets"
  [a b]
  (set (concat (clojure.set/difference a b) (clojure.set/difference b a)))
)

(deftest test-symmetric-difference
  (is (= #{2 4 6 7} (symmetric-difference #{1 2 3 4 5 6} #{1 3 5 7}) ))
  (is (= (symmetric-difference	#{:a :b :c} #{}) #{:a :b :c}))
  (is (= (symmetric-difference #{} #{4 5 6}) #{4 5 6}))
  (is (= (symmetric-difference #{[1 2] [2 3]} #{[2 3] [3 4]}) #{[1 2] [3 4]}))
)

(defn least-common-multiple-two-arguments
  "calculates the least common multiple of the numeric arguments"
  [a b]
  (/ (* a b) (great-common-divisor a b))
  )

(defn least-common-multiple
  "calculates the least common multiple of the numeric arguments"
  [& args]
  (reduce least-common-multiple-two-arguments args)
)

(deftest test-least-common-multiple
  (is (= (least-common-multiple 2 3) 6))
  (is (= (least-common-multiple 5 3 7)) 105)
  (is (= (least-common-multiple 1/3 2/5)	2))
  (is (= (least-common-multiple 3/4 1/6)	3/2))
  (is (= (least-common-multiple 7 5/7 2 3/5) 210))
)

; Day 5

(defn pascals-triangle
  "calculates pascal triangle sequence on a any number of arguments"
  [n]
  (let [line [1]]
    (loop [input (range n) count 1] (conj line ())))
)

; def pascal(n):
; line = [1]
; for k in range(n):
;   line.append(line[k] * (n-k) / (k+1))
; return line

(deftest test-pascals-triangle
  (is (= (pascals-triangle 1) [1]))
  (is (= (pascals-triangle 11) [1 10 45 120 210 252 210 120 45 10 1]))
  (is (= (map pascals-triangle (range	1	6)) [      [1]
                                                  [1 1]
                                                 [1 2 1]
                                                [1 3 3 1]
                                               [1 4 6 4 1]])
  )
)