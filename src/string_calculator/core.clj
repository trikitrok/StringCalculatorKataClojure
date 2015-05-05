(ns string-calculator.core)

(def default-delimiters ["," "\\n"])

(defn create-delimiters-pattern [given-delimiters]
  (re-pattern
    (clojure.string/join
      "|"
      (concat default-delimiters given-delimiters))))

(defn extract-delimiters-and-numbers [input]
  (if-let [delimiters-and-numbers (first (re-seq #"//(.+)\n(.*)" input))]
    (drop 1 delimiters-and-numbers)
    ["" input]))

(defn extract-nums-str [input-str]
  (let [[given-delimiters nums-str] (extract-delimiters-and-numbers input-str)]
    (clojure.string/split
      nums-str
      (create-delimiters-pattern given-delimiters))))

(defn parse [input-str]
  (if (empty? input-str)
    [0]
    (map #(Integer/parseInt %)
         (extract-nums-str input-str))))

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
    parse
    validate
    remove-too-big-numbers
    sum))