(ns programming-clj-book-impl.calling-structures)

(keys {:name "xico" :lastname "matheus"}) ;=> (:name :lastname)
(vals {:name "xico" :lastname "matheus"}) ;=> ("xico" "matheus")
;(get map key value-if-not-found?)

(get {:name "xico" :lastname "matheus"} :name) ;=>  "xico"
({:name "xico" :lastname "matheus"} :name) ;=> "xico"
(:name {:name "xico" :lastname "matheus"}) ;=> "xico"

(def p {:name "xico" :age "24"})
(:name p)
(contains? p :name) ;=> true

(get p :lastname :not-found) ;=> :not-found

(assoc p :lastname "francisco")
; => {:name "xico", :age "24", :lastname "francisco"}

(dissoc p :name)
;{:age "24"}

(let [new-p (assoc p :lastname "francisco")]
  (select-keys new-p [:name :lastname]))
;{:name "xico", :lastname "francisco"}

(let [new-p (assoc p :lastname "francisco")]
  (merge p {:mname "matheus" :cel "33333"}))
;{:name "xico", :age "24", :mname "matheus", :cel "33333"}

;(merge-with merge-fn & maps)

(merge-with
  concat
  {:rr ["Xico"], :ll ["Matheus"]}
  {:rr ["Xico 2"], :ll ["Matheus 2"]}
  {:rr ["Xico 3"], :ll ["Matheus 3"]})
;{:rr ("Xico" "Xico 2" "Xico 3"),
; :ll ("Matheus" "Matheus 2" "Matheus 3")}


;; Functions ON Sets
(def languages #{"java" "c" "d" "clojure"})
(def beverages #{"java" "chai" "pop"})
(require '[clojure.set :refer :all])
(union languages beverages)
;#{"d" "clojure" "pop" "java" "chai" "c"}
(difference languages beverages)
;#{"d" "clojure" "c"}
(intersection languages beverages)
;#{"java"}
(select #(= 1 (count %)) languages)
;#{"d" "c"}


(def compositions
  #{{:name "The Art of the Fugue" :composer "J. S. Bach"}
    {:name "Musical Offering" :composer "J. S. Bach"}
    {:name "Requiem" :composer "Giuseppe Verdi"}
    {:name "Requiem" :composer "W. A. Mozart"}})
(def composers
  #{{:composer "J. S. Bach" :country "Germany"}
    {:composer "W. A. Mozart" :country "Austria"}
    {:composer "Giuseppe Verdi" :country "Italy"}})
(def nations
  #{{:nation "Germany" :language "German"}
    {:nation "Austria" :language "German"}
    {:nation "Italy" :language "Italian"}})


(rename compositions {:name :title})
;#{{:composer "Giuseppe Verdi", :title "Requiem"}
;  {:composer "W. A. Mozart", :title "Requiem"}
;  {:composer "J. S. Bach", :title "The Art of the Fugue"}
;  {:composer "J. S. Bach", :title "Musical Offering"}}
(select #(= (:name %) "Requiem") compositions)
;-> #{{:name "Requiem", :composer "W. A. Mozart"}
;{:name "Requiem", :composer "Giuseppe Verdi"}}
