(ns string-calculator.numbers-parser)

(def ^:private default-delimiters ["," "\n"])

(def ^:private escaped-chars-by-matachar
  (let [esc-chars "()*&^%$#!"]
    (zipmap esc-chars
            (map #(str "\\" %) esc-chars))))

(defn- escape-meta-characters [delimiters-str]
  (reduce str (map #(get escaped-chars-by-matachar % %)
                   delimiters-str)))

(def ^:private get-matches (partial drop 1))

(defn- extract-delimiters [delimiters-str]
  (if-let [delimiters (re-seq #"\[(.*?)\]" delimiters-str)]
    (mapcat get-matches delimiters)
    delimiters-str))

(defn- create-delimiters-pattern [delimiters-str]
  (re-pattern
    (escape-meta-characters
      (clojure.string/join "|"
                           (concat default-delimiters
                                   (extract-delimiters delimiters-str))))))

(defn- extract-delimiters-and-numbers [input]
  (if-let [delimiters-and-numbers (re-seq #"//(.+)\n(.*)" input)]
    (mapcat get-matches delimiters-and-numbers)
    ["" input]))

(defn- extract-nums-str [input-str]
  (let [[delimiters-str nums-str] (extract-delimiters-and-numbers input-str)]
    (clojure.string/split
      nums-str
      (create-delimiters-pattern delimiters-str))))

(defn parse [input-str]
  (if (empty? input-str)
    [0]
    (map #(Integer/parseInt %)
         (extract-nums-str input-str))))