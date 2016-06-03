(ns new-hope.week1.day4
  (:require [midje.sweet :refer :all]
            [criterium.core :as crit]))

;Penultimate	Element
(def penultimate
  #(last (take 2 (reverse %))))

(facts "second to last1"
       (penultimate (list 1 2 3 4 5)) => 4)
(facts "second to last2"
       (penultimate ["a" "b" "c"]) => "b")
(facts "second to last3"
       (penultimate [[1 2] [3 4]]) => [1 2])

;Write	a	function	which	returns	the	sum	of	a	sequence	of	numbers.
(defn sumItUp [coll]
  (reduce + 0 coll))

(facts "" (sumItUp [1 2 3]) => 6)
(facts "" (sumItUp #{4 2 1}) => 7)
(facts "" (sumItUp (list 0 -2 5 5)) => 8)
(facts "" (sumItUp '(0 0 -1)) => -1)
(facts "" (sumItUp '(1 10 3)) => 14)


;Write	a	function	which	returns	only	the	odd	numbers	from	a	sequence.
(defn isOdd [x]
  (not= 0 (rem x 2)))

(defn my-odd [x]
  (filter isOdd x))

(facts ""
       (isOdd 2) => false
       (isOdd 3) => true
       (my-odd #{1 2 3 4 5}) => '(1 3 5)
       (my-odd [4 2 1 6]) => '(1)
       (my-odd [2 2 4 6]) => '()
       (my-odd [1 1 1 3]) => '(1 1 1 3))

;Write	a	function	which	returns	true	if	the	given	sequence	is	a	palindrome.	Hint:	“racecar”
;does	not	equal	‘(\r	\a	\c	\e	\c	\a	\r)
; cond

(defn isPalindrome [st]
  (defn palin [in rev]
    (if (= in rev) true false))
  (palin (seq st) (reverse st)))

(facts "is Palindrome"
       (isPalindrome '(1 2 3 4 5)) => false
       (isPalindrome "racecar") => true
       (isPalindrome [:foo :bar :foo]) => true
       (isPalindrome '(1 1 3 3 1 1)) => true
       (isPalindrome '(:a :b :c)) => false)

;Write	a	function	which	duplicates	each	element	of	a	sequence.
(defn dups [[x & more]]
  (if (= x nil) [] (cons x (cons x (dups more)))))

(facts "dups"
       (dups [1 2 3]) => '(1 1 2 2 3 3)
       (dups [:a :a :b :b]) => '(:a :a :a :a :b :b :b :b)
       (dups [[1 2] [3 4]]) => '([1 2] [1 2] [3 4] [3 4])
       (dups [[1 2] [3 4]]) => '([1 2] [1 2] [3 4] [3 4]))