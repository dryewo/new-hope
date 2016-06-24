(ns new-hope.week2
  (:require [midje.sweet :refer :all]
            [clojure.string :as str]
            [clojure.data :as data]
            [clojure.set :as cljset]))

;fibonacci sequence
(defn get-n-fibonacci
  ([n]
   (get-n-fibonacci [1 1] n))
  ([x, n]
   (if (< (count x) n)
     (get-n-fibonacci (conj x (+ (last x) (nth x (- (count x) 2)))) n)
     x)))

(facts "about get-n-fibonacci"
       (get-n-fibonacci 3) => '(1 1 2)
       (get-n-fibonacci 6) => '(1 1 2 3 5 8)
       (get-n-fibonacci 8) => '(1 1 2 3 5 8 13 21))

;get the caps
(def get-the-caps
  (fn [in]
    (str/join (re-seq  #"[A-Z]" in))))

(facts "about get-the-caps"
       (get-the-caps "HeLlO, WoRlD!") => "HLOWRD"
       (empty? (get-the-caps "nothing")) => true
       (get-the-caps "$#A(*&987Zf)") => "AZ")

;intro to some
(facts "about some"
       (some #{2 7 6} [5 6 7 8]) => 6
       (some #(when (even? %) %) [5 6 7 8]) => 6)

;factorial fun
(def factorial-fun
  (fn [n]
    (loop [x n f 1]
      (if (= x 1)
        f
        (recur (dec x) (* f x))))))

(facts "about factorial-fun"
       (factorial-fun 1) => 1
       (factorial-fun 3) => 6
       (factorial-fun 5) => 120
       (factorial-fun 8) => 40320)

;intro to destructuring & advanced destructuring
(facts "about destructuring"
       (let [[a b c d e f g] (range)] [c e]) => [2 4]
       (let [[a b & c :as d] [1 2 3 4 5]] [a b c d]) => [1 2 [3 4 5] [1 2 3 4 5]])

;A Half-Truth
(defn a-half-truth [& args]
  (let [[a & b] args] [a b]
                      (if (and (true? a) (some false? (vec b))) true
                                (if (and (false? a) (some true? (vec b))) true false))))

(facts "about a-half-truth"
       (a-half-truth true false) => true
       (a-half-truth true) => false
       (a-half-truth false true false) => true
       (a-half-truth true true true) => false
       (a-half-truth true true true false) => true)

;Greatest common divisor
(defn gcd [a b]
  (if (zero? b) a
                (recur b (mod a b))))

(facts "about gcd"
       (gcd 2 4) => 2
       (gcd 10 5) => 5
       (gcd 5 7) => 1
       (gcd 1023 858) => 33)

;simple clojures
(def simple-clojure
  (fn [x]
    (fn [y]
      (apply * (repeat x y)))))

(facts "about simple-clojure"
       ((simple-clojure 2) 16) ((simple-clojure 8) 2) => 256
       (map (simple-clojure 3) [1 2 3 4]) => [1 8 27 64]
       (map #((simple-clojure %) 2) [0 1 2 3 4]) => [1 2 4 8 16])

;cartesian product
(defn cartesian-product [set1 set2]
  (into #{} (for [x set1 y set2] [x y])))

(facts "about cartesian-product"
       (cartesian-product
         #{"ace", "king", "queen"} #{"spade", "heart", "diamond", "club"}) => #{["ace", "spade"] ["ace", "heart"] ["ace", "diamond"] ["ace" "club"]
                                                      ["king", "spade"] ["king", "heart"] ["king", "diamond"] ["king" "club"]
                                                      ["queen", "spade"] ["queen", "heart"] ["queen", "diamond"] ["queen" "club"]}
       (cartesian-product #{1 2 3} #{4 5}) => #{[1 4] [2 4] [3 4] [1 5] [2 5] [3 5]}
       (count (cartesian-product (into #{} (range 10)) (into #{} (range 30)))))

;symmetric difference
(defn symmetric-difference [set1 set2]
  (apply sorted-set
         (cljset/union
           (first (data/diff set1 set2))
           (second (data/diff set1 set2)))))

(facts "about symmetric-difference"
       (symmetric-difference #{1 2 3 4 5 6} #{1 3 5 7}) => #{2 4 6 7}
       (symmetric-difference #{:a :b :c} #{}) => #{:a :b :c}
       (symmetric-difference #{} #{4 5 6}) => #{4 5 6}
       (symmetric-difference #{[1 2] [2 3]} #{[2 3] [3 4]}) => #{[1 2] [3 4]})

;last common multiple
(defn lcm [& args]
  (->> args
       (reduce
         (fn [a b]
           (/ (* a b) (gcd a b))))))

(facts "about lcm"
       (lcm 2 3) => 6
       (lcm 5 3 7) => 105
       (lcm 1/3 2/5) => 2
       (lcm 3/4 1/6) => 3/2
       (lcm 7 5/7 2 3/5) => 210)

;pascals triangle
(defn pascals-triangle [n]
  (if (= 1 n) [1]
              (into [] (nth
                         (iterate #(map + (lazy-cat [0] %1) (lazy-cat %1 [0])) '(1)) (- n 1)))))

(facts "about pascals-triangle"
       (pascals-triangle 1) => [1]
       (map pascals-triangle (range 1 6)) => '([1] [1 1] [1 2 1] [1 3 3 1] [1 4 6 4 1])
       (pascals-triangle 11) => [1 10 45 120 210 252 210 120 45 10 1])


