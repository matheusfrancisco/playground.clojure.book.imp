(ns jclj.chapter-4)
;;Data types
;; Understanding precision
;; Trying to be rational
;; When to use keywords
;; Symbolic resolution
;; Regular expressions


;;So far, we’ve covered a somewhat eclectic mix of fun and practical
;; concerns. This  brings us to a point where we can dive
;; deeper into a fundamental topic: how Cljoure deals with
;; scalar values, including numeric, symbolic, and regular expression.

;; Understanding precision
;;
;; Numbers in Clojure are by default as precise
;; as they need to be. Given enough memory...


;;Truncation

(let [imadeuapi 3.14159265358979323846264338327950288419716939937M] 
  (println (class imadeuapi)) 
  imadeuapi)
; conjure/out | java.math.BigDecimal
;3.14159265358979323846264338327950288419716939937M

(let [butieatedit 3.14159265358979323846264338327950288419716939937]
  (println (class butieatedit))
  butieatedit)
; conjure/out | java.lang.Double
; conjure/ret ⤸
;3.141592653589793

;;Promotion
;;Overflow
;;Underflow
;;Rounding errors

(let [approx-interval (/ 209715 2097152)
      actual-interval (/ 1 10)
      hours (* 3600 100 10)
      actual-total (double (* hours actual-interval))
      approx-total (double (* hours approx-interval))]
  (- actual-total approx-total))
; => 0.34332275390625


; Trying to be rational
;2/3
;; why be rational?

;; how to be rational
;; ratio? rational? rationalize
(def a (rationalize 1.0e50))
(def b (rationalize -1.0e50))
(def c (rationalize 17.0e00))
(+ (+ a b) c) ;=> 17N

;You can use rational? to check whether a given number is a rational
;and then use rationalize to convert it to one. There are a few rules of
;thumb to remember if you want to maintain perfect accuracy in your
;computations:

;Never use Java math libraries unless they return results of

;BigDecimal, and even then be suspicious.

;Don’t rationalize values that are Java float or double primitives.

;If you must write your own high-precision calculations, do so with  rationals.

;Only convert to a floating-point representation as a last resort.

(numerator (/ 123 10)) ; => 123
(denominator (/ 123 10)) ; => 10

;;
;;When to use Keyowrds;

