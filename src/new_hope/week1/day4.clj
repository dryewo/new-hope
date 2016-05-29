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
(def sumItUp
  #(reduce + 0 %))

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

(facts "" (isOdd 2) => false)
(facts "" (isOdd 3) => true)
(facts "" (my-odd #{1 2 3 4 5}) => '(1 3 5))
(facts "" (my-odd [4 2 1 6]) => '(1))
(facts "" (my-odd [2 2 4 6]) => '())
(facts "" (my-odd [1 1 1 3]) => '(1 1 1 3))

;Write	a	function	which	returns	true	if	the	given	sequence	is	a	palindrome.	Hint:	“racecar”
;does	not	equal	‘(\r	\a	\c	\e	\c	\a	\r)
(defn palin
  ([in rev]
   (if (empty? in)
     true
     (if (= (first in) (first rev))
       (recur (rest in) (rest rev))
       false)
     )
    )
  )
(palin "stss" "stss")
;(defn isPalindrome [st]
;
;  (palin (st) (reverse st))
;  )
;(false?	(__	'(1	2	3	4	5)))(true?	(__	"racecar"))
;(true?	(__	[:foo	:bar	:foo]))
;(true?	(__	'(1	1	3	3	1	1)))
;(false?	(__	'(:a	:b	:c)))