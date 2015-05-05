(ns string-calculator.core)

(defn add [nums_str]  
  (if (empty? nums_str)
    0
    (Integer/parseInt nums_str)))