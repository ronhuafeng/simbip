(ns simbip.parser
  (import java.lang.String))

(defn is-alpha?
  [ch]
  (or
    (and (<= (int ch) (int \Z)) (>= (int ch) (int \A)))
    (and (<= (int ch) (int \z)) (>= (int ch) (int \a)))))
(defn is-digit?
  [ch]
  (and (<= (int ch) (int \9)) (>= (int ch) (int \0))))
(defn is-whitespace?
  [ch]
  (or
    (= ch \space)
    (= ch \newline)
    (= ch \tab)
    (= ch \return)))

(defn remove-whitespace
  [expression-string]
  "Remove all whitespaces from a expression string."
  (clojure.string/join
    (remove is-whitespace? expression-string)))

(defn single-statements
  [expression-string]
  "Split a BIP action string seperated by ';' (a=1;b=2;c=a+b;) into a list of single statements."
  (remove clojure.string/blank?
    (clojure.string/split
      expression-string #";")))

(defn tokenize
  [statement]
  "Statement is a pure expression without whitespaces and looks like 'a=b' or 'a=3'.
   Types of tokens: var-name, fun-name, operators, numbers.
   Comments is not expected at present.
   Return value is a list of {:name :type}"
  (if (clojure.string/blank? statement)
    '()
    (let [ch (first statement)
          left (.substring statement 1)]
      (cond
      ;; parentheses
        (= ch \()
        (cons {:name "(" :type "LP"}
          (tokenize left))
        (= ch \))
        (cons {:name ")" :type "RP"}
          (tokenize left))

      ;; operator
        (or
          (= ch \+)
          (= ch \-)
          (= ch \*)
          (= ch \/)
          (= ch \%)
          (= ch \^))
        (cons {:name (str ch) :type "OP"}
          (tokenize left))

      ;; variable
        (is-alpha? ch)
        (let [var-name (re-find #"\w+" statement)
              left (clojure.string/replace-first
                     statement
                     var-name
                     "")]
          (cons {:name var-name :type "VAR"}
            (tokenize left)))

      ;; number
        (is-digit? ch)
        (let [num-str (re-find #"\d+" statement)
              left (clojure.string/replace-first
                     statement
                     num-str
                     "")]
          (cons {:name num-str :type "NUM"}
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
                (is-digit? ch2)
                (is-alpha? ch2)
                (= ch2 \())
            (cons {:name (str ch)
                   :type "OP"}
              (tokenize left))
            (cons {:name (str ch ch2 ) :type "OP"}
              (tokenize (.substring left 1)))))))))

(defn op-level
  [op-name]
  ;; Reference: http://en.cppreference.com/w/cpp/language/operator_precedence
  (case op-name
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

(defn op-diff
  [current top]
  "Return if current-operator has higher priority than operator on top of stack, negtive result means higher."
  (if (or
        (= top "(")
        (= current "("))
    -1
    (- (op-level current)
      (op-level top))))



(defn build-AST-rec
  [op-stack sym-stack tokens]
  (if (= 0 (count tokens))
    ;; check if terms left
    (if (not-empty sym-stack)
      ;; check if operators left
      (if (not-empty op-stack)
        ;; use operators to combine the  terms in sym-stack
        (build-AST-rec
          (rest op-stack)
          (if (or
                (= (:name (first op-stack)) "!"))  ;; Uni-operator only pops one sym-token
            (conj
              (rest sym-stack)
              (list (first op-stack)
                (first sym-stack)))
            (conj
              (rest (rest sym-stack))
              (list (first op-stack)
                (second sym-stack)
                (first sym-stack))))
          (rest tokens))
        ;; only orphan term left
        (first sym-stack))
      '())
    (let [t (first tokens)]
      (case (:type t)
        ;; If the first token is a variable or a number, then we push it into the stack of symbols.
        ("VAR", "NUM")
        (build-AST-rec
          op-stack
          (conj sym-stack (list t))
          (rest tokens))

        ;; If the first token is an operator, then we compare its priority with the top of the stack of
        ;; operators. We only push the operator into the stack when its priority is higher than the top
        ;; element.
        "OP"
        (let [top (first op-stack)]
          (if (nil? top)
            (build-AST-rec
              (conj op-stack t)
              sym-stack
              (rest tokens))
            (let [d (op-diff (:name t) (:name top))]
              (cond
                ;; Current operator with higher priority. Push current operator into the stack.
                (neg? d)
                (build-AST-rec
                  (conj op-stack t)
                  sym-stack
                  (rest tokens))
                ;; Meets an operator with equal (assume the operator is left-associative) or lower priority.
                ;; To make a new expression using the operator at top of op-stack
                ;; and two term at top of sys-stack.
                (or
                  (zero? d)
                  (pos? d))
                (build-AST-rec
                  (rest op-stack)
                  (if (or
                        (= (:name top) "!"))  ;; Uni-operator only pops one sym-token
                    (conj
                      (rest sym-stack)
                      (list top
                        (first sym-stack)))
                    (conj
                      (rest (rest sym-stack))
                      (list (first op-stack)
                        (second sym-stack)
                        (first sym-stack))))
                  tokens)))))
        ;; If meets left parenthesis, push it into stack.
        "LP"
        (build-AST-rec
          (conj op-stack t)
          sym-stack
          (rest tokens))

        "RP"
        (if (= "(" (:name (first op-stack)))
          (build-AST-rec
            (rest op-stack)
            sym-stack
            (rest tokens))
          (build-AST-rec
            (rest op-stack)
            (conj
              (rest (rest sym-stack))
              (list (first op-stack)
                (second sym-stack)
                (first sym-stack))
              )
            tokens))
        ))))


(defn build-AST
  [tokens]
  (build-AST-rec '() '() tokens))

(defn set-environment
  [atomic-map]
  (fn [action]
    (case action
      get
      (fn [bind-name]
        (get (deref atomic-map) bind-name))
      set
      (fn [bind-name value]
        (swap! atomic-map assoc bind-name value)))))

(defn operator-table
  [op-name]
  (case op-name
    "!" not
    "*" *
    "/" /
    "%" mod
    "+" +
    "-" -
    ">" >
    "<" <
    "<=" <=
    ">=" >=
    "==" =
    "!=" not=
    "&" bit-and
    "^" bit-xor
    "|" bit-or
    "&&" (fn [a b] (and a b))
    "||" (fn [a b] (or a b))
    "=" (fn [setter name value ]
          (setter name value))
    ))

(defn get-trans-interface
  [env]
  (let [setter (env 'set)
        getter (env 'get)]
    (fn [token]
      (case (:type token)
        "OP"
        (let [v (:name token)
              op (operator-table v)]
          ;; 在接下来的构建中，假设 t 是代表实现了 name value operate 等的 token， 实际上应该遇不到实现了 operate 的 token。
          (case v
            ("!")
            { :operate
              (fn [t]
                {:value
                 (apply op [(:value t)])})}
            ;; 二元操作符们
            ( "*", "/", "%", "+", "-", ">", "<",
              "<=", ">=", "==", "!=", "&", "^", "|",
              "&&", "||")
            { :operate
              (fn [t1 t2]
                {:value
                 (apply op [(:value t1) (:value t2)])})}
            ;;
            ("=")
            { :operate
              (fn [t1 t2]
                (do
                  (apply op [setter (:name t1) (:value t2)])  ;; 一开始错把第一个 t1 的选择属性写成了 :value。
                  {:value (:value t2)}))}
            ))
        ;; 表示返回值可以提供的接口有 name 和 value 两个, name 表示可以作为右值使用。
        "VAR"
        (let [keyword-name (keyword (:name token))]
          { :name keyword-name
            :value (getter keyword-name)})

        "NUM"
        { :value (read-string (:name token))}
        ))))

(defn build-exec
  [trans ast]
  (if (empty? ast)
    false
    (let [tr-root (trans (first ast))
          leafs (rest ast)]
      (if (empty? leafs)
        tr-root
        ( apply
          (:operate tr-root)
          (map #(build-exec trans %) leafs))))))


(tokenize "(a+b+c)")
(build-AST (tokenize "e==(a+(b+(c*d)))"))
(do
  (def env1 (atom {}))
  (def env (set-environment env1))
  (def tr (get-trans-interface env))
  (def ast (build-AST (tokenize "7==(a=7)")))
  (def tt (build-exec tr ast)))
(build-AST (tokenize "a=5"))