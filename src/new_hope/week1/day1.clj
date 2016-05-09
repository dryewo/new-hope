(ns new-hope.week1.day1
  (:require [midje.sweet :refer :all]
            [criterium.core :as crit]))


;Intro to Strings

(defn upper [str]
  (.toUpperCase str))


(facts "string check"
       (upper "hello") => "HELLO")



;Lists can be constructed with either a function or a quoted form.
(facts "list creating"
       (list :a :b :c) => '(:a :b :c))

;When operating on a list, the conj function will return a new list with one or more items "added" to the front.
;
;Note that there are two test cases, but you are expected to supply only one answer, which will cause all the tests to pass.

(facts "conj"
       (conj '(2 3 4) 1) => '(1 2 3 4))

(facts "conj2"
       (conj '(3 4) 2 1) => '(1 2 3 4))

;Vectors can be constructed several ways. You can compare them with lists.

;Issue
;(facts "vectors"
;       [(:a :b :c
;         [:a :b :c]
;         [:a :b :c]  => (list :a :b :c) (vec '(:a :b :c)) (vector :a :b :c))])


;When operating on a Vector, the conj function will return a new vector with one or more items "added" to the end.

(facts "vectors 1"
       (conj [1 2 3] 4) => [1 2 3 4])

(facts "vectors 2"
       (conj [1 2] 3 4) => [1 2 3 4])


(facts "set 1"
       #{:c :b :d :a}) =>  (set '(:a :a :b :c :c :c :c :d :d))

(facts "set 2"
       (clojure.set/union #{:a :b :c} #{:b :c :d}) => #{:a :b :c :d})



;When operating on a set, the conj function returns a new set with one or more keys "added".
(facts "set 3"
       (conj #{1 4 3} 2) =>  #{1 2 3 4})

;Maps  store  key-value  pairs.  Both  maps  and  keywords  can  be  used  as  lookup  functions.
;Commas  can  be  used  to  make  maps  more  readable,  but  they  are  not  required.
(facts "map 1"
       ((hash-map  :a  10,  :b  20,  :c  30)  :b) => 20)
(facts "map 2"
       (:b  {:a  10,  :b  20,  :c  30}) => 20)

;Maps:  conj
;When  operating  on  a  map,  the  conj  function  returns  a  new  map  with  one  or  more  key-
;value  pairs  “added”.

(facts "maps conj"
       (conj  {:a  1} [:b 2] [:c  3]) => {:a  1,  :b  2,  :c  3})

;Intro  to  Sequences
;All  Clojure  collections  support  sequencing.  You  can  operate  on  sequences  with  functions
;like  first,  second,  and  last.
(facts "seq intro"
       (first  '(3  2  1)) => 3)
(facts "seq second"
       (second  [2  3  4]) => 3)
(facts "seq last"
       (last (list  1  2  3)) => 3)