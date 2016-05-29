(ns new-hope.week2
  (:require [midje.sweet :refer :all]
            [criterium.core :as crit]))

;; DAY 1

;; Fibonacci Sequence
(defn fibonacci [x]
  (cond
    (= x 1) [1]
    (= x 2) [1 1]
    :else (loop [n 3 r [1 1]]
            (if (= n x)
              (conj r (+ (last (butlast r)) (last r)))
              (recur (inc n) (conj r (+ (last (butlast r)) (last r))))))))

(facts "about fibonacci"
       (fibonacci 3) => '(1 1 2)
       (fibonacci 6) => '(1 1 2 3 5 8)
       (fibonacci 8) => '(1 1 2 3 5 8 13 21))

;; Get the Caps
(comment
  (defn get-caps [s]
    (apply str (filter #(Character/isUpperCase %) s)))
  )

(defn get-caps [s]
  (let [s-seq (seq s)]
    (loop [s-seq s-seq r ""]
      (if (empty? s-seq)
        r
        (if (Character/isUpperCase (first s-seq))
          (recur (rest s-seq) (str r (first s-seq)))
          (recur (rest s-seq) r))))))

(facts "about get-caps"
       (get-caps "HeLlO, WoRlD!") => "HLOWRD"
       (empty? (get-caps "nothing")) => true
       (get-caps "$#A(*&987Zf") => "AZ")

;; Intro to some
(comment
  (= 6 (some #{2 7 6} [5 6 7 8]))
  (= 6 (some #(when (even? %) %) [5 6 7 8]))
  )

;; Factorial Fun
(defn factorial [n]
  {:pre  [(>= n 0)] }
  (loop [n n r 1]
    (if (zero? n)
      r
      (recur (dec n) (*' r n)))))

(facts "about factorial"
       (factorial -1) => (throws AssertionError "Assert failed: (>= n 0)")
       (factorial 0) => 1
       (factorial 1) => 1
       (factorial 3) => 6
       (factorial 5) => 120
       (factorial 8) => 40320)

;; Intro to Destructuring
(comment
  (= [2 4] (let [[a b c d e f g] (range)] [c e]))
  )

;; DAY 2

;; Advanced Destructuring
(comment
  (= [1 2 [3 4 5] [1 2 3 4 5]] (let [[a b & c :as d] [1 2 3 4 5]] [a b c d]))
  )

;; A Half-Truth
(defn half-truth [& vals]
  (true? (and (some identity vals) (not (every? identity vals)))))

(facts "about half-truth"
       (half-truth false false) => false
       (half-truth true false) => true
       (half-truth true) => false
       (half-truth false true false) => true
       (half-truth true true true) => false
       (half-truth true true true false) => true)

;; Greatest Common Divisor
(defn gcd [x y]
  (cond
    ;;(zero? x) y
    (zero? y) x
    (< x y) (recur x (rem y x))
    (> x y) (recur y (rem x y))))

(facts "about gcd"
       (gcd 2 4) => 2
       (gcd 10 5) 5
       (gcd 5 7) 1
       (gcd 1023 858) 33)

;; DAY 3

;; Simple closures
(defn get-xn [n]
  (fn [x] (int (Math/pow x n))))

(facts "about get-xn"
       ((get-xn 2) 16) => 256
       ((get-xn 8) 2) => 256
       (map (get-xn 3) [1 2 3 4]) => [1 8 27 64]
       (map #((get-xn %) 2) [0 1 2 3 4]) => [1 2 4 8 16])

;; Cartesian Product
(defn cart-prod [set-1 set-2]
  (set (for [x set-1 y set-2] [x y])))

(facts "about cart-prod"
       (cart-prod #{"ace" "king" "queen"} #{"spade" "heart" "diamond" "club"}) =>
       #{["ace" "spade"] ["ace" "heart"] ["ace" "diamond"] ["ace" "club"]
         ["king" "spade"] ["king" "heart"] ["king" "diamond"] ["king" "club"]
         ["queen" "spade"] ["queen" "heart"] ["queen" "diamond"] ["queen" "club"]}
       (cart-prod #{1 2 3} #{4 5}) => #{[1 4] [2 4] [3 4] [1 5] [2 5] [3 5]}
       (count (cart-prod (into #{} (range 10))
                  (into #{} (range 30)))) => 300)

;; DAY 4

;; Symmetric Difference
(defn symmetric-diff [set-1 set-2]
  (let [union-set #{}
        union-set (apply conj union-set set-1)
        union-set (apply conj union-set set-2)]
    (loop [s union-set r #{}]
      (if (empty? s)
        r
        (if (not (and (contains? set-1 (first s)) (contains? set-2 (first s))))
          (recur (rest s) (conj r (first s)))
          (recur (rest s) r))))))

(facts "about symmetric-diff"
       (symmetric-diff #{1 2 3 4 5 6} #{1 3 5 7}) => #{2 4 6 7}
       (symmetric-diff #{:a :b :c} #{}) => #{:a :b :c}
       (symmetric-diff #{} #{4 5 6}) => #{4 5 6}
       (symmetric-diff #{[1 2] [2 3]} #{[2 3] [3 4]}) => #{[1 2] [3 4]})

;; Least Common Multiple
(defn lcm
  ([a] a)
  ([a b] (/ (* a b) (gcd a b)))
  ([a b & more]
   (loop [more more r (/ (* a b) (gcd a b))]
     (if (empty? more)
       r
       (recur (rest more) (/ (* r (first more)) (gcd r (first more))))))))

(facts "about lcm"
       (lcm 2 3) => 6
       (lcm 5 3 7) => 105
       (lcm 1/3 2/5) => 2
       (lcm 3/4 1/6) => 3/2
       (lcm 7 5/7 2 3/5) => 210)

;; DAY 5

;; Pascal’s Triangle
(defn nth-row [n]
  (cond
    (= n 1) [1]
    (= n 2) [1 1]
    :else (loop [row 2 r [1 1]]
            (if (= row n)
              r
              (recur
                (inc row)
                (loop [v r r [1]]
                  (if (< (count v) 2)
                    (conj r 1)
                    (recur (rest v) (conj r (+ (first v) (second v)))))))))))

(facts "about nth-row"
       (nth-row 1) = > [1]
       (map nth-row (range 1 6)) =>
       [[1]
        [1 1]
        [1 2 1]
        [1 3 3 1]
        [1 4 6 4 1]]
       (nth-row 11) => [1 10 45 120 210 252 210 120 45 10 1])