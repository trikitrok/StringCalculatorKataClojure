(ns string-calculator.core)

(defn extract-delimiters-and-numbers [input]
  (if-let [delimiters-and-numbers (first (re-seq #"//(.+)\n(.*)" input))]
    (drop 1 delimiters-and-numbers)
    ["" input]))

(defn parse-numbers [nums-str]
  (let [[given-delimiters nums-str] (extract-delimiters-and-numbers nums-str)
        delimiters (concat ["," "\\n"] given-delimiters)
        delimiters-pattern (clojure.string/join "|" delimiters)]
    (clojure.string/split nums-str (re-pattern delimiters-pattern))))

(defn extract-numbers [nums-str]
  (if (empty? nums-str)
    [0]
    (map #(Integer/parseInt %)
         (parse-numbers nums-str))))

(defn add [nums-str]
  (apply + (extract-numbers nums-str)))