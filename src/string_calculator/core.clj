(ns string-calculator.core
  (:require [string-calculator.numbers-parser :as numbers-parser]
            [string-calculator.numbers-validation :as numbers-validation]
            [string-calculator.numbers-filter :as numbers-filter]))

(def ^:private sum (partial apply +))

(defn add [input-str]
  (-> input-str
      numbers-parser/parse
      numbers-validation/validate
      numbers-filter/remove-too-big-numbers
      sum))