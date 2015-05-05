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

(defn- extract-delimiters-and-numbers [input]
  (let [delimiters-and-numbers (get-matches #"//(.+)\n(.*)" input)]
    (if (empty? delimiters-and-numbers)
      ["" input]
      delimiters-and-numbers)))

(defn- extract-nums-str [input-str]
  (let
    [[delimiters-str nums-str] (extract-delimiters-and-numbers input-str)]
    (clojure.string/split
      nums-str
      (create-delimiters-pattern delimiters-str))))

(defn parse [input-str]
  (if (empty? input-str)
    [0]
    (map #(Integer/parseInt %)
         (extract-nums-str input-str))))