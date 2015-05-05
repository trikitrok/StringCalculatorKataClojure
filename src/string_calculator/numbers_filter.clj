(ns string-calculator.numbers-filter)

(defn remove-too-big-numbers [numbers]
  (remove #(> % 1000) numbers))