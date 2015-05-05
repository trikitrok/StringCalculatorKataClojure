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
    (add "1,2") => 3))
