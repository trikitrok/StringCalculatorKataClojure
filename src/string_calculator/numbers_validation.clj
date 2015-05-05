(ns string-calculator.numbers-validation)

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
