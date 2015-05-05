(ns string-calculator.numbers-parser)

(def ^:private default-delimiters ["," "\n"])

(def ^:private escaped-chars-by-matachar
  (let [esc-chars "()*&^%$#!"]
    (zipmap esc-chars
            (map #(str "\\" %) esc-chars))))

(defn- escape-meta-characters [delimiters-str]
  (reduce str (map #(get escaped-chars-by-matachar % %)
                   delimiters-str)))

(defn- get-matches [pattern string]
  (if-let [delimiters (re-seq pattern string)]
    (mapcat (partial drop 1) delimiters)
    []))

(defn- extract-delimiters [delimiters-str]
  (let [delimiters (get-matches #"\[(.*?)\]" delimiters-str)]
    (if (empty? delimiters)
      delimiters-str
      delimiters)))

(defn- create-delimiters-pattern [delimiters-str]
  (re-pattern
    (escape-meta-characters
      (clojure.string/join
        "|"
        (concat default-delimiters
                (extract-delimiters delimiters-str))))))

(defn- numbers-and-delimiters-pattern [input]
  (let [delimiters-and-numbers (get-matches #"//(.+)\n(.*)" input)]
      [(or (second delimiters-and-numbers) input)
       (create-delimiters-pattern
         (or (first delimiters-and-numbers) ""))]))

(defn- extract-nums-str [input-str]
  (apply clojure.string/split
         (numbers-and-delimiters-pattern input-str)))

(defn parse [input-str]
  (if (clojure.string/blank? input-str)
    [0]
    (map #(Integer/parseInt %)
         (extract-nums-str input-str))))