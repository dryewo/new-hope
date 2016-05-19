(ns new-hope.week1
  (:require [midje.sweet :refer :all]
            [criterium.core :as crit]))

;; After launching the REPL always run (refresh) at least once

;; `comment` always returns nil and does not evaluate its arguments
;; Might be handy to type expressions here and send them to REPL
;;  instead of typing them in REPL directly
(comment
  (- 10 (* 2 3))
  (.toUpperCase "hello world")
  (conj '(2 3 4) 1)
  (conj '(3 4) 2 1)
  (conj [1 2 3] 100)
  (println "Hello"))

(facts "some facts"
  (second [2 3 4]) => 3)

;; For Week 1 days 1-3 just play around a bit, use comment form to write down expressions

;; Unit test is just an expression that declares expectations using a simple DSL
;; The whole form returns true or false when evaluated
;; Day 1

;; https://www.4clojure.com/problem/3
;; (= __ (.toUpperCase "hello world"))
(facts "about Strings"
  (.toUpperCase "hello world")=>"HELLO WORLD")

;; https://www.4clojure.com/problem/4
;; (= (list __) '(:a :b :c))
(facts "about Lists"
  (list? '(:a :b :c))
  (list? (list :a :b :c)))

(comment
 (list? (list :a :b :c)))
;; https://www.4clojure.com/problem/5
;; (= __ (conj '(2 3 4) 1))
;; (= __ (conj '(3 4) 2 1))
(facts "about Lists conj"
  (conj '(2 3 4) 1)=>(list 1 2 3 4)
  (conj '(3 4) 2 1)=>(list 1 2 3 4))


;; https://www.4clojure.com/problem/6
;; (= [__] (list :a :b :c) (vec '(:a :b :c)) (vector :a :b :c))
(facts "about Vectors"
  (vec '(:a :b :c))=> (vector :a :b :c)
  (list :a :b :c)=> [:a :b :c])

(comment)
(= [:a :b :c] (list :a :b :c) (vec '(:a :b :c)) (vector :a :b :c))

;; https://www.4clojure.com/problem/7
;; (= __ (conj [1 2 3] 4))
;; (= __ (conj [1 2] 3 4))
(facts "about Vectors conj"
  (conj[1 2 3]4)=>[1 2 3 4]
  (conj[1 2 ]3 4)=>[1 2 3 4])

;; TODO: exception if compare it with {:c :b :d :a}
;; https://www.4clojure.com/problem/8
;; (= __ (set '(:a :a :b :c :c :c :c :d :d)))
;; (= __ (clojure.set/union #{:a :b :c} #{:b :c :d}))
(facts "about Sets"
  (set '(:a :a :b :c :c :c :c :d :d))=> #{:a :b :c :d}
  (clojure.set/union #{:a :b :c} #{:b :c :d}) => #{:a :b :c :d})

(comment
 (clojure.set/union #{:a :b :c} #{:b :c :d}))

;; https://www.4clojure.com/problem/9
;; (= #{1 2 3 4} (conj #{1 4 3} __))
(facts "about Sets conj"
  (conj #{1 4 3} 2)=> #{1 2 3 4})

;; https://www.4clojure.com/problem/10

;; (= __ ((hash-map :a 10, :b 20, :c 30) :b))
;; (= __ (:b {:a 10, :b 20, :c 30}))
(facts "about hash-map"
  ((hash-map :a 10, :b 20, :c 30) :b) => 20)

;; https://www.4clojure.com/problem/11
;; (= {:a 1, :b 2, :c 3} (conj {:a 1} __ [:c 3]))
(facts "about maps conj"
  ({:a 1, :b 2, :c 3}(conj {:a 1} {:b 2} [:c 3])))

(comment
 ({:a 1, :b 2, :c 3}(conj {:a 1} {:b 2} [:c 3])))

;; https://www.4clojure.com/problem/12
;; (= __ (first '(3 2 1)))
;; (= __ (second [2 3 4]))
;; (= __ (last (list 1 2 3)))
(facts "about Seq"
  (first '(3 2 1)) => 3
  (second [2 3 4]) => 3
  (last (list 1 2 3)) => 3)



;;
;; Day 4
;;

(def second-to-last
  (fn [coll]
    (second (reverse coll))))

(comment
  (= #{:a :b :c :d} (set '(:a :a :b :c :c :c :c :d :d)))
  (= __ (let [x 5] (+ 2 x))))


(comment
  (second-to-last [4 5 6 7])
  (reverse [1 2 3 4])
  (second [4 3 2 1])
  (first (next [4 3 2 1]))
  (butlast [1 2 3 4])
  (last [1 2 3]))


(facts "about second-to-last"
  (second-to-last [1 2 3 4]) => 3
  (second-to-last [1]) => nil
  (second-to-last nil) => nil
  (second-to-last []) => nil)

(facts "vectors"
  ['(:a :b :c) [:a :b :c] [:a :b :c]]
  => [(list :a :b :c) (vec '(:a :b :c)) (vector :a :b :c)])

(facts nil false)

(comment
  ;; Simple time measurement (run once)
  (let [coll (vec (range 10000))]
    (time (second (reverse coll)))
    (time (last (butlast coll))))
  ;; More elaborate benchmark taking 10 seconds
  (let [coll (vec (range 10000))]
    (crit/quick-bench (second (reverse coll)))
    (crit/quick-bench (last (butlast coll)))))
