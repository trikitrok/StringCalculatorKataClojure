(ns string-calculator.core)

(defn add [nums_str]  
  (if (empty? nums_str)
    0
    (apply +
           (map #(Integer/parseInt %)
                (clojure.string/split nums_str #",")))))