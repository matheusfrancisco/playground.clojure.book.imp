(ns programming-clj-book-impl.getting-started)


(defn blank? [str]
  (every? #(Character/isWhitespace %) str))


(comment
  " The clojure version is shorter. But even more important
  , it's simpler: it has no variables, no mutable state, and no
  branches. This is possible thanks to higher-oreder functions")



(blank? "Matheus Francisco") ;=> false
(blank? " ") ; =>true

(comment
  """
      public class Person {
        private String firstName;
        private String lastName;
        public Person(String firstName, String lastName) {
          this.firstName = firstName;
          this.lastName = lastName;
        }
        public String getFirstName() {
          return firstName;
        }
        public void setFirstName(String firstName) {
          this.firstName = firstName;
        }
        public String getLastName() {
          return lastName;
        }
        public void setLastName(String lastName) {
          this.lastName = lastName;
        }
      }
  """)


(defrecord Person [first-name last-name])
(def xico (->Person "Matheus" "Francisco"))
(:first-name xico) ;=> Matheus
(:last-name xico) ;=> Francisco


(cond (= 1 1) "equal"
    (> 2 1) "more")

(for [c compositions :when (= (:name c) "Requiem")] (:composer c))
;-> ("W. A. Mozart" "Giuseppe Verdi")


;;atom
(def visitors (atom #{}))
(swap! visitors conj "Xico") ;=> {Xico}
(prn visitors)
; conjure/out | #object[clojure.lang.Atom 0x213f09bd {:status :ready, :val #{"Xico"}}]
(deref visitors) ; => #{"Xico"}
(prn @visitors) ; => #{"Xico"}


(defn hello
  "Writes hello message to *out*. Calls you by username.
  Knows if you have been here before."
  [username]
  (swap! visitors conj username)
  (str "Hello, " username))

(hello "Matheux")

(prn @visitors)
; conjure/out | #{"Matheux" "Xico"}


(doc str)
(find-doc "reduce")



