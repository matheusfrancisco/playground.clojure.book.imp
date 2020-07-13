(ns programming-clj-book-impl.specifications
  (:require [clojure.spec.alpha :as s]))

(s/def ::ingredient (s/key :req [::name ::quantity ::unit]))
(s/def ::name string?)
(s/def ::quantity number?)
(s/def ::unit keyword?)


(s/fdef scale-ingredient
  ::agrs (s/cat :ingredient ::ingredient :factor number?)
  :ret ::ingredient)

;;VALIDATING DATA
;;PREDICATES

;;boolean?, string?, keyword?, rational?, pos?, zero?, empty?, any? some?

(s/def :my.app/company-name string?)
(s/valid? :my.app/company-name "Xico Labs")
(s/valid? :my.app/company-name 23)


;;ENUMERATED VALUES

(s/def :marble/color #{:red :green :blue})
(s/valid? :marble/color :red)
(s/valid? :marble/color :pink)


(s/def :bowling/roll #{0 1 2 3 4})
(s/valid? :bowling/roll 3)

;;RANGE SPECS

(s/def :ranged-roll (s/int-in 0 11))
(s/valid? :ranged-roll 10)


(s/def :name (s/nilable string?))


(s/def :odd-int (s/and int? odd?))
