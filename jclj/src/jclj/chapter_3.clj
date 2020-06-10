(ns jclj.chapter-3)

;; Truthiness
(if true :truthy :falsey) ; => :truthy
(if [] :truthy :falsey) ;=> :truthy
(if nil :truthy :falsey) ;=> :falsey
(if false :truthy :falsey) ;=> :falsey

; Don't create Boolean objects
;; it's possible to create an object that looks a lot like,
;; but isn't actually, false
;Never do this
(def evil-false (Boolean. "false"))

;evil-false looks like false
(prn evil-false)
; conjure/out | false
(= false evil-false) ; => true
;;
;;But once it gains your trust, it'll show you just how wicked it is by acting
;;like true
(if evil-false :truthy :falsey) ;=> :truthy

;;This is the right way
(if (Boolean/valueOf "false") :truthy :falsey) ; => :falsey



;; nill vs. false
;; Rarely do you need to differentiate between the two non-truthy valuse, but if
;; you do, you can use nil? and false?

(when (nil? nil) "Actually nil, not false") ; => "Actually nil, not false"

;;Nil pun with care
;;Because empty collections act like true in Boolean contexts, you need an
;;idiom for testing whether there's anything in a collection to process.
;;Thankfully, Clojure provides such a technique:
(seq [1 2 3]) ;=> (1 2 3)
(seq []) ; => nil


(defn print-seq [s]
  (when (seq s)
    (prn (first s))
    (recur (rest s))))


(print-seq [1 2 3 4])
; conjure/eval | (print-seq [1 2 3 4])
; conjure/out | 1
; conjure/out | 2
; conjure/out | 3
; conjure/out | 4
; conjure/ret ⤸
(print-seq []) ; => nil

;In fact, print-seq is a template for most functions in Clojure: it shows
;that you generally shouldn’t assume seq has been called on your collection
;arguments, but instead call seq in the function itself and process based on
;its result.

;; Destructuring

(def guys ["Guy" "Lewis" "Steve"])
(str (nth guys 2) "," (nth guys 0) "," (nth guys 1))

(let [[f-name m-name l-name] guys]
  (str l-name ", " f-name " " m-name))

;; destructure the result of re-find rem
(def date-regex #"(\d{1,2})\/(\d{1,2})\/(\d{4})")
(let [rem (re-matcher date-regex "12/02/1994")]
  (when (.find rem)
    (let [[_ m d] rem]
      {:month m :day d})))

; => {:month "12", :day "02"}

(let [[a b c & more] (range 10)]
  (prn "a b c are:" a b c)
  (prn "more is :" more))
; conjure/out | "a b c are:" 0 1 2
; conjure/out | "more is :" (3 4 5 6 7 8 9)

(let [range-vec (vec (range 10))
      [a b c & more :as all] range-vec]
  (prn "a b c are:" a b c)
  (prn "more is :" more)
  (prn "all is:" all))

; conjure/out | "a b c are:" 0 1 2
; conjure/out | "more is :" (3 4 5 6 7 8 9)
; conjure/out | "all is:" [0 1 2 3 4 5 6 7 8 9]


;;Destructuring with a  map

(def guy-name
  {:f-name "Guy" :m-name "Lewis" :l-name "Steele"})

(let [{f-name :f-name, m-name :m-name, l-name :l-name} guy-name]
  (str l-name ", " f-name " " m-name))
; => "Steele, Guy Lewis"

;;nicely handles
(let [{:keys [f-name m-name l-name]} guy-name]
  (str l-name ", " f-name " " m-name))

;; So by using :keys instead of a binding form, you're telling Clojure that
;; the next form will be a vector of names that it should convert to keywords
; such as :f-name in order to look up their values in the input map.

;;Similarly, if you'd use :strs, Clojure would be looking for items in the map
;; with string keys such as "f-name", and :syms would indicate symbol keys.

; The directives :keys :strs ;syms and regular named bindings can appear
; in any combination and in any order. But sometimes you'll want to
; get at the original map in other words, the keys you didn't name
; individually by any of the methods just described. For that, you want :as
; which works just like it does with vector destructuring:

(let [{f-name :f-name, :as whole-name} guy-name]
  (prn "First name" :f-name)
  (prn "Whole name is ")
  whole-name)
;=> {:f-name "Guy", :m-name "Lewis", :l-name "Steele"}

;;If the destructuring map looks up a key that's not in the source map, it's
;; normally bound to nil, but you can provide different defaults with or

(let [{:keys [title f-name m-name l-name],
       :or {title "Mr."}} guy-name]
  (prn title f-name m-name l-name))
; conjure/out | "Mr." "Guy" "Lewis" "Steele"

;;All of these map destructuring features also work on lists, a feature that's
;;primarily used by functions so as to accept keyword arguments

(defn whole-name [& args]
  (let [{:keys [f-name m-name l-name]} args]
    (str l-name ", " f-name " " m-name)))

(whole-name :f-name "Xico" :m-name "FF" :l-name "Matheus")
; = > "Matheus, Xico FF"


;;Associative destructuring
(let [{first-thing 0, last-thing 3} [1 2 3 4]]
  [first-thing last-thing])
; => [1 4]

;; Destructuring in function parameters

(defn print-last-name [{:keys [l-name]}]
  (prn l-name))

(print-last-name guy-name)
; conjure/out | "Steele"



;;; USING THE REPL to Experiment

