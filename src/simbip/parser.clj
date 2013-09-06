#_(ns simbip.parser
  (import 'java.lang.Character)
  (import 'java.lang))

#_(defn remove-whitespace
  [expression-string]
  "Remove all whitespaces from a expression string."
  (clojure.string/join
    (remove #(Character/isWhitespace %) expression-string)))

#_(defn single-assignments
  [expression-string]
  "Split a BIP action string seperated by ';' (a=1;b=2;c=a+b;) into a list of single assignments."
  (remove clojure.string/blank?
    (clojure.string/split
      expression-string #";")))

#_(defn tokenize
  [assignment-string]
  "Assignment-string is a pure expression without whitespaces and looks like 'a=b' or 'a=3'.
   Types of tokens: var-name, fun-name, operators, numbers.
   Comments is not expected at present.
   Return value is a list of {:value :type}"
  (if (clojure.string/blank? assignment-string)
    []
    (let [ch (first assignment-string)
          left (.substring assignment-string  1)]
      (cond
      ;; parentheses
        (= ch \()
        (cons {:value "(" :type "LP"}
          (tokenize left))
        (= ch \))
        (cons {:value ")" :type "RP"}
          (tokenize left))

      ;; operator
        (or
          (= ch \+)
          (= ch \-)
          (= ch \*)
          (= ch \/)
          (= ch \%)
          (= ch \^))
        (cons {:value (str ch) :type "OP"}
          (tokenize left))

      ;; variable
        (Character/isAlphabetic (int ch))
        (let [var-name (re-find #"\w+" assignment-string)
              left (clojure.string/replace-first
                     assignment-string
                     var-name
                     "")]
          (cons {:value var-name :type "VAR"}
            (tokenize left)))

      ;; number
        (Character/isDigit ch)
        (let [num-str (re-find #"\d+" assignment-string)
              left (clojure.string/replace-first
                     assignment-string
                     num-str
                     "")]
          (cons {:value num-str :type "NUM"}
            (tokenize left)))

      ;; operator of length-2 (maybe)
        (or
          (= ch \!)
          (= ch \=)
          (= ch \&)
          (= ch \|)
          (= ch \>)
          (= ch \<))
        (let [ch2 (first left)]
          (if (or
                (Character/isDigit ch2)
                (Character/isAlphabetic (int ch2))
                (= ch2 \())
            (cons {:value (str ch) :type "OP"}
              (tokenize left))
            (cons {:value (str ch ch2 ) :type "OP"}
              (tokenize (.substring left 1)))))))))

#_(defn op-level
  [op-name]
  ;; Reference: http://en.cppreference.com/w/cpp/language/operator_precedence
  (case op-name
    ("(", ")") 1
    ("!") 2
    ("*", "/", "%") 4
    ("+", "-") 5
    ("<", ">", "<=", ">=") 7
    ("==", "!=") 8
    ("&") 9
    ("^") 10
    ("|") 11
    ("&&") 12
    ("||") 13
    ("=") 15))

#_(defn build-AST-rec
  [ast]
  ())

#_(defn build-AST
  [token-list]
  (if (= 0 (count token-list))
    {:node? true :level 0 :left [] :right [] :value ()}
    (let [head (first token-list)
          tail (rest token-list)]
      (if (= "LP" (:type head))
        (do
          ())))))

#_(defn rewrite-AST)
