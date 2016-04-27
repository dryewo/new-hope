(ns new-hope.fizz-buzz
  (:require [midje.sweet :refer :all]))

(def fizz? #(= (mod % 3) 0))

(def buzz? #(= (mod % 5) 0))

(defn fizz-buzz [n]
  (if (and (fizz? n) (buzz? n))
    "FizzBuzz"
    (cond (fizz? n) "Fizz"
          (buzz? n) "Buzz"
          :else     (str n))))

(map fizz-buzz (range 1 31))


(facts "fizz is working"
  (fizz? 3) => true)

(facts "fizz is working"
       (fizz? 4) => false)

(facts "buzz is working"
       (buzz? 10) => true)

(facts "buzz is working"
       (buzz? 12) => false)