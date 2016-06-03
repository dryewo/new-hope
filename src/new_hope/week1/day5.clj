(ns new-hope.week1.day5
  (:require [midje.sweet :refer :all]
            [criterium.core :as crit]))

;Write	a	function	which	removes	consecutive	duplicates	from	a	sequence.
(defn de-dups-helper [st out]
  (let [h (first st)                                        ;; is there more ideomatic solution? such as h :: t => bla-bla
        t (rest st)]
    (cond
      (= h nil) out
      (= h (first out)) (recur t out)
      :else (recur (rest st) (cons h out)))))

(defn de-dups [st]
  (reverse (de-dups-helper st [])))

(facts "de dups "
       (apply str (de-dups "Leeeeeerrroyyy")) => "Leroy"
       (de-dups [1 1 2 3 3 2 2 3]) => '(1 2 3 2 3)
       (de-dups [[1 2] [1 2] [3 4] [1 2]]) => '([1 2] [3 4] [1 2]))

(comment
  (de-dups "aaaaabbb"))

;Write	a	function	which	packs	consecutive	duplicates	into	sub-lists.

(defn pack-seq [xs]
  (reverse (reduce (fn [r x]
                     (cond
                       (empty? r) [[x]]
                       (= (first (first r)) x) (cons (cons x (first r)) (rest r))
                       :else (cons [x] r)
                       )) [] xs)))

(facts "seq pack"
       (pack-seq [1 1 2 1 1 1 3 3]) => '((1 1) (2) (1 1 1) (3 3))
       (pack-seq [:a :a :b :b :c]) => '((:a :a) (:b :b) (:c))
       (pack-seq [[1 2] [1 2] [3 4]]) => '(([1 2] [1 2]) ([3 4])))


;Write	a	function	which	drops	every	Nth	item	from	a	sequence.
(defn drop-nth [in cnt]
  (defn inner [lst idx out]
    (cond
      (empty? lst) out
      (= (rem idx cnt) 0) (recur (rest lst) (inc idx) out)
      :else (recur (rest lst) (inc idx) (conj out (first lst)))))
  (inner in 1 []))

(facts "drop nth"
       (drop-nth [1 2 3 4 5 6 7 8] 3) => [1 2 4 5 7 8]
       (drop-nth [:a :b :c :d :e :f] 2) => [:a :c :e]
       (drop-nth [1 2 3 4 5 6] 4) => [1 2 3 5 6])


;The	 iterate 	function	is	one	that	makes	an	infinite	lazy	sequence.	Remember	to	be	careful
;with	infinite	sequences	and	use	them	with	a	 take .	The	 iterate 	function	applies	a	function
;to	an	initial	argument	and	then	applies	the	same	function	to	the	result,	and	does	it	again,
;and	again:
;(take	5	(iterate	inc	1))
;;	->	(1	2	3	4	5)
;The	iterate	function	can	be	used	to	produce	an	infinite	lazy	sequence.
(= [1 4 7 10 13] (take 5 (iterate #(+ 3 %) 1)))


;Write	a	function	which	replicates	each	element	of	a	sequence	a	variable	number	of	times.

(defn dupl-ntimes [el num]
  (take num (repeat el)))

(defn repliction [xs num]
  (defn repl-recur [in out]
    (if (empty? in) out
                    (concat out (repl-recur (rest in) (dupl-ntimes (first in) num)))))
  (repl-recur xs []))

(repliction [1 2 3] 2)


(facts "replicate seq"
       (repliction [1 2 3] 2) => '(1 1 2 2 3 3)
       (repliction [:a :b] 4) => '(:a :a :a :a :b :b :b :b)
       (repliction [4 5 6] 1) => '(4 5 6)
       (repliction [[1 2] [3 4]] 2) => '([1 2] [1 2] [3 4] [3 4])
       (repliction [44 33] 2) => [44 44 33 33])