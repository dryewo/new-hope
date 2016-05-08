(ns new-hope.week2.day1
  (:require [midje.sweet :refer :all]
    [criterium.core :as crit]))


;Fibonacci  Sequence
(facts "fibs"
   (fibs	3)	=> '(1	1	2)
   (fibs	6)	=> '(1	1	2	3	5	8)
   (fibs	8)	=> '(1	1	2	3	5	8	13	21)
       )

(def fibs
  (fn fib
    ([] (fib 1 1))
    ([n] (take n (fib)))
    ([n0 n1] (cons n0 (lazy-seq (fib n1 (+ n1 n0))) ))
    )
  )
