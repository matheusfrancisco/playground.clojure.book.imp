(ns programming-clj-book-impl.unifying_data_w_sequences)


;you can get the first item in a sequence
(first '(1 2 3))

;; you can get everything after the first item  the rest
;; of a sequence
(rest '(1 2 3))

;; You can construct a new sequence by adding an item to the front of
;; an existing sequence. This is called consing:
(cons 0 '(1 2 3))

;; the seq function will return a seq on any seq-able collection:
;; (seq coll)

(first [1 2 3]) ; => 1
(rest [1 2 3]) ; => (2 3)
(cons 0 [1 2 3]) ; => (0 1 2 3)


;When you apply rest or cons to a vector, the result is a seq, not a
;vector. In the REPL, seqs print just like lists, as you can see in
;the earlier output. You can check the actual returned type by
;using the seq? predicate:


(seq? (rest [1 2 3])) ;=> true

(first {:fname "Xico" :lname "Lino"}) ;=> [:fname "Xico"]
(rest {:fname "Matheus" :lname "Francisco"}) ;=> ([:lname "Francisco"])

(cons [:mname "NN"] {:fname "Matheus" :lname "Francisco"})
; => ([:mname "NN"] [:fname "Matheus"] [:lname "Francisco"])


(first #{:the :quick :brown :fox})
(rest #{:the :quick :browm :fox})

(sorted-set :the :quick :browm)
(sorted-map :c 3 :a 2 :b 1)
;{:a 2, :b 1, :c 3}


;; In addition to the core capabilities of seq, two other capabilities are worth meeting
;; immediately conj and into

;(conj coll element & elemenst)
;(into to-coll from-coll)

(conj '(1 2 3) :a) ; =>(:a 1 2 3)
(into '(1 2 3) '(:a :b :c)) ; => (:c :b :a 1 2 3)

(conj [1 2 3] :a) ; => [1 2 3 :a]
(into [1 2 3] [:a :b :c]) ; => [1 2 3 :a :b :c]

(list? (rest [1 2 3]))
(seq? (rest [1 2 3]))




;(range start? end? step?)

(1 3 5 7 9 11 13 15 17 19 21 23)
(range 10);end
;(0 1 2 3 4 5 6 7 8 9)
; conjure/eval | (range 10 20)
(range 10 20);start + end
;(10 11 12 13 14 15 16 17 18 19)
; conjure/ret ⤸
(range 1 25 2)
;(1 3 5 7 9 11 13 15 17 19 21 23)


;(repeat n x)

(repeat 5 1)
;(1 1 1 1 1)
(repeat 10 "x")
;("x" "x" "x" "x" "x" "x" "x" "x" "x" "x")

;;
;;iterate begins with a value x and continues forever,
;;applying a function f to each value to calculate the next

;(iterate f x)
(take 10 (iterate inc 1))

;(1 2 3 4 5 6 7 8 9 10)
;since the sequence is infinite, you need
;another new function to help you view the sequence from the repl
;and returns a lazy sequence of the first n items from a collection
;;and provides one way to create a finitie view onto an infinite collection

;(take n sequence)

;;the whole number are a pretty useful sequence to have around
;so let's def them for future use:
(def whole-numbers (iterate inc 1))

;when called with a single argument, repeat returns a lazy infinite sequence:
; (repeat x)
(take 20 (repeat 1))
;(1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1)

;; the cycle functions takes a collection and cycles it infinitely
;(cycle coll)
(take 10 (cycle (range 3)))
;(0 1 2 0 1 2 0 1 2 0)
(take 10 (cycle (range 4)))
;(0 1 2 3 0 1 2 3 0 1)

; the interleave funciton takes multiple collections and produces a new collection that interleaves values from each
;;collection until one of the collections is exhausted

(interleave whole-numbers ["A" "B" "C" "D"])

(interpose "," ["a", "bab", "grapes"])
;("a" "," "bab" "," "grapes")

(apply str (interpose "," ["apples" "bananas" "grapes"]))
;"apples,bananas,grapes"

(require '[clojure.string :refer [join]])
(join \, ["Apples" "banana"])
;"Apples,banana"

;;;
;;
;;For each collection type in clojure, there is a function that
;;takes an arbitrary number of arguments and creates a collection Of that type:
;;
;;(list & elements)
;(vector & elements)
;(hash-set & elements)
;(hash-map key1 val1 ...)

;;hash-set has a cousin set that works a little differently set
;expects a collection as its first argument:

(set [1 2 3])
;#{1 3 2}

;;hash-set takes a variable list of aruments
(hash-set 1 2 3)
;#{1 3 2}

(vec (range 3))
;[0 1 2]

;;;
;; Filtering sequences
;;clojure provides a number of functions that filter a sequence,
;;return a subsequence of the original sequence. the most basic
;;of these is filter

;(filter pred coll)

;;filter takes a predicate and a collection and returns a sequence of
; objects for which the filter returns true (when, interpreted in a boolean context).
;you can filter the whole-numbers from the previous section to get the odd numeber or the even numbers:

(take 10 (filter even? whole-numbers))
;(2 4 6 8 10 12 14 16 18 20)
(take 10 (filter odd? whole-numbers))
;(1 3 5 7 9 11 13 15 17 19)

;;you can take from a sequence while a predicate remains true with
;take-while:

;;(take-while pred coll)

;;For example, to take all the characters in a string up to the first vowel,
;;we can define some useful helper functions:

(def vowel? #{\a\e\i\o\u})
(def consonant? (complement vowel?))

;;the use those predicates to take the characters from the string
;up to the first vowel
(take-while consonant? "the-quick-brow-fox")
;(\t \h)

;;A couple of interesting things are happening here:
;; sets act as functions that look up a value in the set and return
;; either the value or nil if not found. So, you can read #{\a \e \i \o \u} as
;; the function that test to see wheter its argument is a vowel

;;complement reverses the behavior of another funciton. Here we create
;consonant? by defining it as the function that is the complement of vowel?

;;the opposite of take-wilhe is drop-while
;;(drop-while pred coll)

;;drop-while drops elements from the beginning of a sequence
;whie a predicate is true and the returns the rest. you could use drop-while to drop all leading non-vowels from a string
(drop-while consonant? "the-quick-brown-fox")
;(\e \- \q \u \i \c \k \- \b \r \o \w \n \- \f \o \x)



;split-at and split-with will split a collection into two collections:
(split-at 5 (range 10))
;[(0 1 2 3 4) (5 6 7 8 9)]

(split-with #(<= % 10) (range 0 20 2))
;[(0 2 4 6 8 10) (12 14 16 18)]

;;all take-, spli-, drop- return lazy sequences


;;;SEQUENCE PREDICATES
;; Filter functions take a predicate and return a sequence.
;;Closely related are the sequence predicates.
;;A sequence predicate asks how some other predicate applies to 
;;every item in a sequence.
;;For example, the every? predicate asks whether some other predicate is true for every element of a sequence.

;(every? pred coll)

(every? odd? [1 3 5]) ;true
(every? odd? [1 2 5]) ;false

;;(some pred coll)
;;some return the first non-false value for its predicate or returns nill
;if no element mateched:
(some even? [1 2 3])
(some even? [1 3 5])

;Notice that some does not end with a question mark. some is not a
;predicate, although it’s often used like one. some returns the
;actual value of the first match instead of true. The distinction is
;invisible when you pair some with even?, since even? is itself a
;predicate. To see a non-true match, try using some with identity to
;find the first logically true value in a sequence

(some identity [nil false 1 nil 2]);=>1

;;a common use of some is to perfom a linear search
;;to see if a sequence contains a matching element, which is typically
;;written as a set of a single element. For example to sse if a 
;sequence contains the value 3:

(some #{199} (range 200)) ;=>199

(not-every? even? whole-numbers)
(not-any? even? whole-numbers)


;;Transforming sequences

;(map f coll)

(map #(format "<p>%s</p>" %) ["the" "quick" "brown" "fox"])
;("<p>the</p>" "<p>quick</p>" "<p>brown</p>" "<p>fox</p>")
(map #(format "<%s>%s</%s>" %1 %2 %1) ["h1" "h2" "h3" "h1"] ["the" "quick" "brown" "fox"])
("<h1>the</h1>" "<h2>quick</h2>" "<h3>brown</h3>" "<h1>fox</h1>")


;;another transformation is reduce:
;;(reduce f coll)
;;
;;f is a function of two arguments reduce applies f on the first two elements
;; in coll and then applies f to the result and the third
;;element, and so on. reduce is useful for functions that "totatl up"
; a sequence in some way. You can use reduce to add items:

(reduce + (range 1 11)) ;=> 55
(reduce * (range 1 11)) ;=>  3628800

;;
;;you can sort a collection with sort or sort-by

;;(sort comp? coll)
;;(sort-by a-fin comp? coll)

(sort [42 1 7 11]); =>(1 7 11 42)
(sort-by #(.toString %) [42 1 7 11]);=>(1 11 42 7)

(sort-by :grade > [{:grade 83} {:grade 90} {:grade 77}])
;({:grade 90} {:grade 83} {:grade 77})

;(for [binding-form coll-expr filter-expr? ...] expr)

(for [word ["the" "quick" "brown" "fox"]]
  (format "<p> %s </p>" word))
;("<p> the </p>" "<p> quick </p>" "<p> brown </p>" "<p> fox </p>")

;This reads almost like English: “For [each] word in [a sequence
;of words], format [according to format instructions].”


;Comprehensions can emulate filter using a :when clause. You can
;pass even? to :when to filter the even numbers:

(take 10 (for [n whole-numbers :when (even? n)] n))
;(2 4 6 8 10 12 14 16 18 20)

; A :while clause continues the evaluation only while its expression holds true:

(for [n whole-numbers :while (even? n)] n)
;()

;The real power of for comes when you work with more than one
;binding expression. For example, you can express all possible
;positions on a chessboard in algebraic notation by binding both
;rank and file:

(for [file "ABCDEFGH"
      rank (range 1 9)]
  (format "%c%d" file rank))
;("A1" "A2" "A3" "A4" "A5" "A6" "A7" "A8" ... "H1" "H2" "H3" "H4" "H5" "H6" "H7" "H8")



