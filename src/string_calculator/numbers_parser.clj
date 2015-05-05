(ns string-calculator.numbers-parser)

(defn- get-matches [pattern string]
  (mapcat (partial drop 1) (re-seq pattern string)))

(defn str->int [str-num]
  (Integer/parseInt str-num))

(defn parse [str-cmd]
  (let [nums-str (get-matches #"([-\d]+)" str-cmd)]
    (if (empty? nums-str)
      [0]
      (map str->int nums-str))))