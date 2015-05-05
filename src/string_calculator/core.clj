(ns string-calculator.core
  (:require [string-calculator.numbers-parser :as numbers-parser]
            [string-calculator.numbers-validation :as numbers-validation]))

(defn remove-too-big-numbers [numbers]
  (remove #(> % 1000) numbers))

(def sum (partial apply +))

(defn add [input-str]
  (->
    input-str
    numbers-parser/parse
    numbers-validation/validate
    remove-too-big-numbers
    sum))