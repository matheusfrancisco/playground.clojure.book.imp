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
; => :a-keyword
; => ::also-a-keyword

;; Applications of keywords

(def population {:zombies 2700, :humans 9})
(get population :zombies) ; => 2700
(get population :test) ; => nil
(:test population) ; => nil

(prn (/ (get population :zombies)
        (get population :humans))
     "zombies per capita")
; conjure/out | 300 "zombies per capita"

;; As a functions
(:zombies population) ; => 2700
(prn (/ (:zombies population)
        (:humans population))
     "zombies per capita")
; conjure/out | 300 "zombies per capita"

(defn pour [lb ub]
  (cond
    (= ub :toujours) (iterate inc lb)
    :else (range lb ub)))
(pour 1 10)
;(1 2 3 4 5 6 7 8 9)
;(pour 1 :toujours)
defn (do-blowfish [directive]
  case (directive
    :aquarium/blowfish (println "feed the fish")
    :crypto/blowfish (println "encode the message")
    :blowfish (println "not sure what to do")))

(ns crypto)
(user/do-blowfish :blowfish)
; not sure what to do
(user/do-blowfish ::blowfish)
; encode the message
(ns aquarium)
(user/do-blowfish :blowfish)
; not sure what to do
(user/do-blowfish ::blowfish)
; feed the fish


;;Symbolic resolution


(identical? 'goat 'goat)
;=>false

(= 'goat 'goat)
; => true;

(name 'goat); => "goat"

;The identical? function in Clojure only ever returns true when the
;symbols are the same object:
(let [x 'goat, y x]
  (identical? x y)) ; => true

;;
;;Metadata

;;the with-meta function takes an object
;; and a map an returns another object of the same type
;; with the metadata attached. Equally named symbol often aren't
;; the same instance
;; because each can have its own unique metada:

(let [x (with-meta 'goat {:ornery true})
      y (with-meta 'goat {:ornery false})]
  [(= x y)
   (identical? x y)
   (meta x)
   (meta y)])
;[true false {:ornery true} {:ornery false}]
;The two locals x and y both hold an equal symbol 'goat, but they’re
;different instances, each containing separate metadata maps obtained with
;the meta function. So you see, symbol equality depends on neither
;metadata nor identity. This equality semantic isn’t limited to symbols but is
;pervasive in Clojure, as we’ll demonstrate throughout this book. You’ll find
;that keywords can’t hold metadata[7] because any equally named keyword is
;the same object.

;;

;;
;For example,
;the best function highlights this perfectly in the way that it takes the
;greater-than function > and calls it in its body as f:


(defn best [f xs]
  (reduce #(if (f % %2) % %2) xs))

(best > [1 2 3 4 5 6 7 8]) ;=> 8
(best > [15 2 3 4 5 6 7 8]) ;=> 15


; Regular expressions the second problem

;;Regular expressions are a powerful and compact way to find specific
;;patterns in text strings. Although we sympathize with Zawinski’s attitude
;;and appreciate his wit, sometimes regular expressions are a useful tool to
;;have on hand. The full capabilities of regular expressions (or regexes) are
;;well beyond the scope of this section (Friedl 1997), but we’ll look at some
;;of the ways Clojure uses Java’s regex capabilities.


;A literal regular expression in Clojure[10] looks like this:
#"an example pattern"

(class #"example")
; => java.util.regex.Pattern

;Although the pattern is surrounded with double quotes like string literals,
;the way things are escaped within the quotes isn’t the same. If you’ve
;written regexes in Java, you know that any backslashes intended for
;consumption by the regex compiler must be doubled, as shown in the
;following compile call. This isn’t necessary in Clojure regex literals, as
;shown by the undoubled return value:
;(java.util.regex.Pattern/compile "\\d")
;=> #"\d"


;d UNIX_LINES ., ^, and $ match only the Unix line terminator '\n'.

;i CASE_INSENSITIVE ASCII characters are matched without regard to uppercase or
;lowercase.
;
;x COMMENTS Whitespace and comments in the pattern are ignored.
;
;m MULTILINE ^ and $ match near line terminators instead of only at the
;beginning or end of the entire input string.
;
;s DOTALL . matches any character including the line terminator.
;
;u UNICODE_CASE Causes the i flag to use Unicode case insensitivity instead of
;ASC

;;
;; Regular-expression functions

;; Clojure regex workhorse. It returns a lazy seq of all matches in a string

(re-seq #"\w+" "one-two/three")
; => ("one" "two" "three")
