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

(defn parse-numbers [input-str]
  (if (empty? input-str)
    [0]
    (map #(Integer/parseInt %)
         (extract-nums-str input-str))))

(defn add [input-str]
  (let [numbers (parse-numbers input-str)]
    (if (every? #(>= % 0) numbers)
      (apply + (parse-numbers input-str))
      (throw (Exception. (str "Detected negative numbers: "
                            (clojure.string/join ", " (filter neg? numbers))))))))