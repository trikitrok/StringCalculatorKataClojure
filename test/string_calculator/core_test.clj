(ns string-calculator.core-test
  (:use midje.sweet)
  (:use [string-calculator.core]))

(facts 
  "about string-calculator"
  (fact 
    "It returns 0 for an empty string"
    (add "") => 0))
