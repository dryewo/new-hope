(ns user
  (:require [clojure.repl :refer [apropos dir doc find-doc pst source]]
            [clojure.tools.namespace.repl :refer [refresh refresh-all]]
            [schema.core :as s]
            [midje.repl :as midje]
            [criterium.core :as crit]))
