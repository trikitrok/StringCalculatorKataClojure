(ns string-calculator.core-test
  (:use midje.sweet)
  (:use [string-calculator.core]))

(facts 
  "about string-calculator"
  (fact 
    "It returns 0 for an empty string"
    (add "") => 0)

  (fact
    "It returns the number itself when the string contains only a number"
    (add "1") => 1
    (add "2") => 2)

  (fact
    "It adds strings containing several numbers separated by commas"
    (add "1,2") => 3
    (add "1,2,3") => 6)

  (fact
    "It adds numbers separated by new lines and/or commas"
    (add "1\n2,3") => 6)

  (fact
    "It can also use any given delimiter"
    (add "//;\n1;2,3") => 6)

  (fact
    "It throws and exception when trying to add negative numbers"
    (add "1,-2,3,-4") => (throws Exception "Detected negative numbers: -2, -4"))

  (fact
    "It ignores any number greater than 1000"
    (add "4,5,1001,3") => 12)

  (fact
    "It can use delimiters of any length"
    (add "//[***]\n1***2***3") => 6))
