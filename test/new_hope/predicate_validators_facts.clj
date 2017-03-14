(ns new-hope.predicate-validators-facts
  (:require [new-hope.predicate-validators :refer :all]
            [midje.sweet :refer :all]))

(facts "validate the predicates"
       (fact "for a valid path predicate"
             (let [valid-path-predicate {:name "path" :args ["/hello"]}]
               (count (validate-predicate valid-path-predicate))) => 0)
       (fact "for an invalid path predicate"
             (let [invalid-path-predicate {:name "path" :args ["/hello", "/hello1"]}]
               (count (validate-predicate invalid-path-predicate))) => 1)
       (fact "for a valid header predicate"
             (let [valid-header-predicate {:name "header" :args ["X-Name" "value"]}]
               (count (validate-predicate valid-header-predicate))) => 0)
       (fact "for an invalid header predicate"
             (let [invalid-header-predicate {:name "header" :args ["X-Name"]}]
               (count (validate-predicate invalid-header-predicate))) => 1))
