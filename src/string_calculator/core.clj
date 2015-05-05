(ns string-calculator.core)

(defn parse-numbers [nums-str]
  (clojure.string/split nums-str #","))

(defn extract-numbers [nums-str]
  (if (empty? nums-str)
    [0]
    (map #(Integer/parseInt %)
         (parse-numbers nums-str))))

(defn add [nums-str]
  (apply + (extract-numbers nums-str)))