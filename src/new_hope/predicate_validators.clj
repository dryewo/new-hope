(ns new-hope.predicate-validators
  (require :require [midje.sweet :refer :all]))

(defmulti validate-predicate
          "Validates a predicate and returns a list of validation errors"
          :name)

(defmethod validate-predicate "path" [predicate]
  (let [validation-errors (transient [])
        args-size (count (predicate :args))]
    (if (not= 1 args-size)
      (conj! validation-errors "Wrong number of arguments"))
    (persistent! validation-errors)))

(defmethod validate-predicate "header" [predicate]
  (let [validation-errors (transient [])
        args-size (count (predicate :args))]
    (if (not= 2 args-size)
      (conj! validation-errors "Wrong number of arguments"))
    (persistent! validation-errors)))
