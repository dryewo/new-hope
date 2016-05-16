(ns new-hope.week3
  (:require [midje.sweet :refer :all])
  (:require [clojure.test :refer :all])
  (:require [clojure.set :refer :all])
  )

;Day 1
(defn is-binary-tree
  "determines whether the argument sequence is a binary tree or not"
  [x]
  (if (not (coll? x))
    (if (= x false) false true)
    (if (= (count x) 3)
      (and (is-binary-tree (first (rest x))) (is-binary-tree (last x)))
      false))
  )

(deftest test-is-binary-tree
  (is (= (is-binary-tree '(:a (:b nil nil) nil)) true))
  (is (= (is-binary-tree '(:a (:b nil nil))) false))
  (is (= (is-binary-tree [1 nil [2 [3 nil nil] [4 nil nil]]]) true))
  (is (= (is-binary-tree [1 [2 nil nil] [3 nil nil] [4 nil nil]]) false))
  (is (= (is-binary-tree [1 [2 [3 [4 nil nil] nil] nil] nil]) true))
  (is (= (is-binary-tree [1 [2 [3 [4 false nil] nil] nil] nil]) false))
  (is (= (is-binary-tree '(:a nil ())) false))
  )

(defn is-symmetric-tree
  "determines whether the argument sequence is a binary tree or not"
  [x y]
  (if (and (coll? x) (coll? y))
    (and (= (first x) (first y))
         (and
           (is-symmetric-tree (first (rest x)) (last y))
           (is-symmetric-tree (last x) (first (rest y)))
           ))
    (= x y))
  )

(defn is-symetric-tree-self
  [x]
  (if (coll? x)
    (is-symmetric-tree (first (rest x)) (last x))
    true
    )
  )

(deftest test-is-symmetric-tree
  (is (= (is-symetric-tree-self '(:a (:b nil nil) (:b nil nil))) true))
  (is (= (is-symetric-tree-self '(:a (:b nil nil) nil)) false))
  (is (= (is-symetric-tree-self '(:a (:b nil nil) (:c nil nil))) false))
  (is (= (is-symetric-tree-self [1 [2 nil [3 [4 [5 nil nil] [6 nil nil]] nil]]
                                 [2 [3 nil [4 [6 nil nil] [5 nil nil]]] nil]]) true))
  (is (= (is-symetric-tree-self [1 [2 nil [3 [4 [5 nil nil] [6 nil nil]] nil]]
                                 [2 [3 nil [4 [5 nil nil] [6 nil nil]]] nil]]) false))
  (is (= (is-symetric-tree-self [1 [2 nil [3 [4 [5 nil nil] [6 nil nil]] nil]]
                                 [2 [3 nil [4 [6 nil nil] nil]] nil]]) false))
  )

;Day	2

(defn flip-order
  [x]
  (fn [a b] (x b a))
  )

(deftest test-flip-order
  (is (= 3 ((flip-order nth) 2 [1 2 3 4 5])))
  (is (= true ((flip-order >) 7 8)))
  (is (= 4 ((flip-order quot) 2 8)))
  (is (= [1 2 3] ((flip-order take) [1 2 3 4 5] 3)))
  )

(defn rotate-a-sequence
  [rotation collection]
  (let [size (count collection) absrot (Math/abs rotation)]
    (take-last size (take (if (pos? rotation) (+ size absrot) (+ size absrot 1)) (cycle collection))))
  )

(deftest test-rotate-a-sequence
  (is (= (rotate-a-sequence 2 [1 2 3 4 5]) '(3 4 5 1 2)))
  (is (= (rotate-a-sequence -2 [1 2 3 4 5]) '(4 5 1 2 3)))
  (is (= (rotate-a-sequence 6 [1 2 3 4 5]) '(2 3 4 5 1)))
  (is (= (rotate-a-sequence 1 '(:a :b :c)) '(:b :c :a)))
  (is (= (rotate-a-sequence -4 '(:a :b :c)) '(:c :a :b)))
  )

;Day	3

(defn reverse-interleave
  "reverses the operation of collection interleave"
  [col size]
  (apply map list (partition size col))
  )

(deftest test-reverse-interleave
  (is (= (reverse-interleave [1 2 3 4 5 6] 2) '((1 3 5) (2 4 6))))
  (is (= (reverse-interleave (range 9) 3) '((0 3 6) (1 4 7) (2 5 8))))
  (is (= (reverse-interleave (range 10) 5) '((0 5) (1 6) (2 7) (3 8) (4 9))))
  )

(defn split-by-type
  "docstring"
  [col]
  (for [fun (set (map type col))]
    (filter (partial instance? fun) col))
  )

(deftest test-split-by-type
  (is (= (set (split-by-type [1 :a 2 :b 3 :c])) #{[1 2 3] [:a :b :c]}))
  (is (= (set (split-by-type [:a "foo" "bar" :b])) #{[:a :b] ["foo" "bar"]}))
  (is (= (set (split-by-type [[1 2] :a [3 4] 5 6 :b])) #{[[1 2] [3 4]] [:a :b] [5 6]}))
  )

; Day	4
(defn is-prime
  "true if the provided argument is prime"
  [n]
  (if (< n 2)
    false
    (if (= n 2)
      true
      (not (reduce #(or %1 %2)
                   (for [x (range 2 n)] (zero? (mod n x)))))))
  )

(defn prime-numbers
  "gets the first n prime numbers"
  [n]
  (take n (filter #(is-prime %) (range)))
  )

(deftest test-prime-numbers
  (is (= (prime-numbers 2) [2 3]))
  (is (= (prime-numbers 5) [2 3 5 7 11]))
  (is (= (last (prime-numbers 100)) 541))
  )

;Day	5
(defn anagram
  "todo"
  [x]
  )

(deftest test-anagram
  (is (= (anagram ["meat" "mat" "team" "mate" "eat"]) #{#{"meat" "team" "mate"}}))
  (is (= (anagram ["veer" "lake" "item" "kale" "mite" "ever"]) #{#{"veer" "ever"} #{"lake" "kale"} #{"mite" "item"}}))
  )