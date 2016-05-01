(ns new-hope.week1.day2
  (:require [midje.sweet :refer :all]
            [criterium.core :as crit]))


;The	rest	function	will	return	all	the	items	of	a	sequence	except	the	first.
(facts "rest func"
       (rest  [10  20  30  40]) => [20 30 40])


;Intro	to	Functions
;Clojure	has	many	different	ways	to	create	functions.

(facts "func 1"
       8 =>  ((fn  add-five  [x]  (+  x  5))  3))
(facts "func 2"
       8 => ((fn  [x]  (+  x  5))  3))
(facts "func 3"
       8 => (#(+ %  5)  3))
(facts "func 4"
       8 =>((partial  +  5)  3))

;Write	a	function	which	doubles	a	number.
(defn num-double [x] (* x 2))
(facts "func 5"
       (num-double  2)  4)
(facts "func 6"
       (num-double  3)  6)
(facts "func 7"
       (num-double  11)  22)
(facts "func 8"
       (num-double  7)  14)

;Write	a	function	which	returns	a	personalized	greeting.
(defn hello [x] str "Hello, " x)
(facts   (hello  "Dave")  "Hello,	Dave!")
(facts   (hello  "Jenn")  "Hello,	Jenn!")
(facts   (hello  "Rhea")  "Hello,	Rhea!")

;The	map	function	takes	two	arguments:	a	function	(f)	and	a	sequence	(s).	Map	returns	a
;new	sequence	consisting	of	the	result	of	applying	f	to	each	item	of	s.	Do	not	confuse	the
;map	function	with	the	map	data	structure.
(facts   '(6 7 8)  (map  #(+  %  5)  '(1  2  3)))

;Sequences:	filter
;Lesson	URL:	https://www.4clojure.com/problem/18
;The	filter	function	takes	two	arguments:	a	predicate	function	(f)	and	a	sequence	(s).	Filter
;returns	a	new	sequence	consisting	of	all	the	items	of	s	for	which	(f	item)	returns	true.
(facts   '(6 7)  (filter  #(>  %  5)  '(3  4  5  6  7)))

;Clojure	lets	you	give	local	names	to	values	using	the	special	let-form.
(facts   7  (let  [x  5]  (+  2  x)))
(facts   7  (let  [x  3,  y  10]  (-  y  x)))
(facts   7  (let  [x  21]  (let  [y  3]  (/  x  y))))

;Can	you	bind	x,	y,	and	z	so	that	these	are	all	true?
(facts   10  (let  [x 6 y 4]  (+  x  y)))
(facts   4  (let  [y 2 z 2]  (+  y  z)))
(facts   1  (let  [z 1]  z))