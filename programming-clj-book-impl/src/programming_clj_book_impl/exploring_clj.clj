(ns programming-clj-book-impl.exploring-clj.clj)

(+ 1 2 3)
;-> 6
(+ 0 1)
;-> 1
(+)
;-> 0
(- 10 5)
;-> 5
(* 3 10 10)
;-> 300
(> 5 2)
;-> true
(>= 5 5)
;-> true
(< 5 2)
;-> false
(= 5 2)
;-> false
(/ 22 7)
; => 22/7
(/ 22.0 7)
; => 3.142857142857143
(quot 22.0 7)
;=> 3.0
(rem 22 7)
;=> 1


;;Collections
(prn [1 2 3])
; conjure/out | [1 2 3]
;;linked list
(quote (1 2 3)) ; => (1 2 3)
(prn '(1 2 3))
; conjure/out | (1 2 3)

(println "another\nmultiline\nstring")
; conjure/out | another
; conjure/out | multiline
; conjure/out | string

(str 1 2 nil 3) ; => "123"
(str \h \e \y \space \y \o \u) ;=> "hey you"


;; Booleans and nil

;true is true, and false is false.
;In addition to false, nil evaluates to false when used in a Boolean
;context.
;Other than false and nil, everything else evaluates to true in a
;Boolean context.

(true? true)
(true? "foo")
(zero? 0.0)
(zero? (/ 22 7))

;; Functions
(string? "hello")
(keyword? :hello)
(symbol? 'hello)


;to define your function use defn:
; the name is an symbol naming the function (implicitly defined within the current namespace)
; the doc-string is an optional string describing the function. The attr-ma associates metadata
; with the function's var. It's covered separately in Metadata.
; the prepost-map? can be used to define preconditions and postconditions that are atutomatically checked on invocatoin,
; and the body contains any number of expressions. The result of the final expression is the return value of the function.

;(defn name doc-string? attr-map? [params*] prepost-map? body)


(defn example
  "Returns a example of string"
  [string]
  (str "Example of " string))

(example "String") ;=> "Example of String"
(doc example)

(defn greeting
  "Returns gretting of the from Hello
  Dfault username is world"
  ([] (greeting "world"))
  ([user] (str "Hello, " user)))

(greeting "Xico") ;=> "Hello Xico"
(defn date [person-1 person-2 & chaperones]
  (println person-1 "and" person-2
           "went out with" (count chaperones) "chaperones"))

(date "Xico" "Mayara" "Grill" "Sabado")
; conjure/out | Xico and Mayara went out with 2 chaperones


;;Anonymous Functions
;In addition to named functions with defn,
;you can also create anonymous function with fn.
;At least three reasons exist to
;create an anonymous function:

; the function is so brief and self-explanatory that giving it a name
; makes the code harder to read,  not easir.

; the function is being used only from inside another function and
; needs a local name, not a top-level binding

; the function is created inside another function for the purpose of
; capturing the values of parameters or local bindings


;Functions used as predicates when filtering data are often brief
;and self-explanatory. For example, imagine that you want to
;create an index for a sequence of words, and you don’t care
;about words shorter than three characters. You can write an
;indexable-word? function like this:


(defn indexable-word? [word]
  (> (count word) 2))

(require '[clojure.string :as str])
(filter indexable-word? (str/split "A fine day it is" #"\W+"))
;=>("fine" "day")

;anonymous functions let you do the same thing in a single line.
;the simplest anonymous fn form is the following:
;(fn [params*] body)
(filter (fn [w] (> (count w) 2))(str/split "A fine day" #"\W+"))
;=>("fine" "day")


;There’s an even shorter reader macro syntax for anonymous
;functions, using implicit parameter names. The parameters are
;named %1, %2, and optionally, a final %& to collect the rest of a
;variable number of arguments. You can also use just % for the
;first parameter, preferred for single-argument functions. This
;syntax looks like this:


; #(body)

(filter #(> (count %) 2) (str/split "A fine day" #"\W+")) ;=> ("fine" "day")

;;A second motivation for anonymous function is when you
;; want to use a named function but only inside the scope of another function. Continuing with the indexable-word? example,
;; you could write this:

(defn indexable-word [text]
  (let [indexable-word? (fn [w] (> (count w) 2))]
    (filter indexable-word? (str/split text #"\W+"))))

(indexable-word "a fine day it is" ) ;=> ("fine" "day")

;A third reason to use anonymous functions is when you create
;a function dynamically at runtime. Earlier, you implemented a
;simple greeting function. Extending this idea, you can create a
;make-greeter function that creates greeting functions. make-greeter
;will take a greeting-prefix and return a new function that composes
;greetings from the greeting-prefix and a name.

(defn make-greeter [greeting-prefix]
  (fn [username] (str greeting-prefix ", " username)))

;It makes no sense to name the fn, because it’s creating a
;different function each time make-greeter is called. However, you
;may want to name the results of specific calls to make-greeter. You
;can use def to name functions created by make-greeter:

(def hello-greeting (make-greeter "Hello"))
(def aloha-greeting (make-greeter "Aloha"))

(hello-greeting "world")
;"Hello, world"
(aloha-greeting "world")
;"Aloha, world"


;WHEN TO USE ANONYMOUS FUNCTIONS
;Anonymous functions have a terse syntax—sometimes too
;terse. You may actually prefer to be explicit, creating named
;functions such as indexable-word?. That’s perfectly fine and will
;certainly be the right choice if indexable-word? needs to be called
;from more than one place.
;Anonymous functions are an option, not a requirement. Use
;the anonymous forms only when you find that they make your
;code more readable. They take a little getting used to, so don’t
;be surprised if you gradually use them more and more.


;; VARS , BINDINGS AND NAMESPACES
