(ns string-calculator.core
  (:require [string-calculator.numbers-parser :as numbers-parser]))

(def any-negative?
  (partial not-every? #(>= % 0)))

(defn throw-negative-numbers-exception [numbers]
  (throw
    (Exception.
      (str "Detected negative numbers: "
           (clojure.string/join ", " (filter neg? numbers))))))

(defn validate [numbers]
  (if (any-negative? numbers)
    (throw-negative-numbers-exception numbers)
    numbers))

(defn remove-too-big-numbers [numbers]
  (remove #(> % 1000) numbers))

(def sum (partial apply +))

(defn add [input-str]
  (->
    input-str
    numbers-parser/parse
    validate
    remove-too-big-numbers
    sum))