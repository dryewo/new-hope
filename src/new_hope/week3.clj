(ns new-hope.week3
  (:require [midje.sweet :refer :all]
            [criterium.core :as crit]))

;; DAY 1

;; To Tree, or not to Tree
(defn binary-tree? [s]
  (and
    (= (count s) 3)
    (every?
      #(or
        (nil? %)
        (and
          (sequential? %)
          (binary-tree? %)))
      (rest s))))

(facts "about binary-tree?"
       (binary-tree? '(:a (:b nil nil) nil)) => true
       (binary-tree? '(:a (:b nil nil))) => false
       (binary-tree? [1 nil [2 [3 nil nil] [4 nil nil]]]) => true
       (binary-tree? [1 [2 nil nil] [3 nil nil] [4 nil nil]]) => false
       (binary-tree? [1 [2 [3 [4 nil nil] nil] nil] nil]) => true
       (binary-tree? [1 [2 [3 [4 false nil] nil] nil] nil]) => false
       (binary-tree? '(:a nil ())) => false)

;; Beauty is Symmetry
(defn sym?
  ([t]
   (let [l (second t)
         r (last t)]
     (sym? l r)))
  ([tl tr]
   (let [[lv ll lr :as tl] tl
         [rv rl rr :as tr] tr]
     (or
       (and
         (nil? tl)
         (nil? tr))
       (and
         (sequential? tl)
         (sequential? tr)
         (= lv rv)
         (sym? ll rr)
         (sym? lr rl))))))

(facts "about sym?"
       (sym? '(:a (:b nil nil) (:b nil nil))) => true
       (sym? '(:a (:b nil nil) nil)) => false
       (sym? '(:a (:b nil nil) (:c nil nil))) => false
       (sym? [1 [2 nil [3 [4 [5 nil nil] [6 nil nil]] nil]]
            [2 [3 nil [4 [6 nil nil] [5 nil nil]]] nil]]) => true
       (sym? [1 [2 nil [3 [4 [5 nil nil] [6 nil nil]] nil]]
            [2 [3 nil [4 [5 nil nil] [6 nil nil]]] nil]]) => false
       (sym? [1 [2 nil [3 [4 [5 nil nil] [6 nil nil]] nil]]
            [2 [3 nil [4 [6 nil nil] nil]] nil]]) => false
       (sym? '(:a (nil nil nil) nil)) => false)

;; DAY 2

;; Flipping out
(defn flip [func] (fn [& args] (apply func (reverse args))))

(facts "about flip"
       ((flip nth) 2 [1 2 3 4 5]) => 3
       ((flip >) 7 8) => true
       ((flip quot) 2 8) => 4
       ((flip take) [1 2 3 4 5] 3) => [1 2 3])

;; Rotate a sequence
(defn rotate [n coll]
  (if (empty? coll)
    coll
    (let [total (count coll)
          n (if (< n 0)
              (+ total (rem n total))
              (rem n total))]
      (concat (drop n coll) (take n coll)))))

(facts "about rotate"
       (rotate 2 [1 2 3 4 5]) => '(3 4 5 1 2)
       (rotate -2 [1 2 3 4 5]) => '(4 5 1 2 3)
       (rotate 6 [1 2 3 4 5]) => '(2 3 4 5 1)
       (rotate 1 '(:a :b :c)) => '(:b :c :a)
       (rotate -4 '(:a :b :c)) => '(:c :a :b))

;; DAY 3

;; Reverse Interleave
(defn rev-interleave [coll n]
  (for [x (range n)] (take-nth n (drop x coll))))

(facts "about rev-interleave"
       (rev-interleave [1 2 3 4 5 6] 2) => '((1 3 5) (2 4 6))
       (rev-interleave (range 9) 3) => '((0 3 6) (1 4 7) (2 5 8))
       (rev-interleave (range 10) 5) => '((0 5) (1 6) (2 7) (3 8) (4 9)))

;; Split by Type
(defn split-by-type [s]
  (vals
    (group-by #(type %) s)))

(facts "about split-by-type"
       (set (split-by-type [1 :a 2 :b 3 :c])) => #{[1 2 3] [:a :b :c]}
       (set (split-by-type [:a "foo" "bar" :b])) => #{[:a :b] ["foo" "bar"]}
       (set (split-by-type [[1 2] :a [3 4] 5 6 :b])) => #{[[1 2] [3 4]] [:a :b] [5 6]})

;; DAY 4

;; Prime Numbers
(defn first-primes [n]
  (let [prime? (fn [x] (not-any? #(= (rem x %) 0)(range 2 x)))]
    (take n (filter prime? (iterate inc 2)))))

(facts "about first-primes"
       (first-primes 2) => [2 3]
       (first-primes 5) => [2 3 5 7 11]
       (last (first-primes 100)) => 541)

;; DAY 5

;; Anagram Finder
(defn find-anograms [v]
  (set
    (filter
      #(> (count %) 1)
      (map
        set
        (vals
          (group-by
            #(set %)
            v))))))

(facts "about find-anograms"
       (find-anograms ["meat" "mat" "team" "mate" "eat"]) => #{#{"meat" "team" "mate"}}
       (find-anograms ["veer" "lake" "item" "kale" "mite" "ever"]) => #{#{"veer" "ever"} #{"lake" "kale"} #{"mite" "item"}})