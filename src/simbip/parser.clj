(ns simbip.parser
  (import java.lang.String)
  (:import
    (ast ExprLexer ExprBuildTree ExprParser Expr)
    (java.io ByteArrayInputStream)
    (org.antlr.v4.runtime CommonTokenStream ANTLRInputStream)))
    

(defn get-raw-AST
  [action-string]
  (let [ string-stream (ByteArrayInputStream. (.getBytes action-string))
         input (ANTLRInputStream. string-stream)
         lexer (ExprLexer. input)
         tokens (CommonTokenStream. lexer)
         parser (ExprParser. tokens)
         ast (.visitDo_action (ExprBuildTree.)
               (.do_action parser))]
    ast))

(defn validate-AST?
  [action-string]
  (Expr/validateAST action-string))

(defn build-AST
  [action-string]
  (let [ raw-ast (get-raw-AST action-string)
         builder (fn rec-build [node]
                     (case (get node "tag")
                       "OP"
                       (cons
                         {:type "OP"
                          :name (get node "value")}
                         (map #(rec-build %) (get node "arg-list")))
                       ("Keyword", "keyword")
                       (cons
                         {:type "Keyword"
                          :name (get node "value")}
                         (map #(rec-build %) (get node "arg-list")))
                       ("FunctionCall")
                       (cons
                         {:type "FunctionCall"
                          :name (get node "value")}
                         (map #(rec-build %) (get node "arg-list")))
                       "Identifier"
                       (list
                         {:type "VAR"
                          :name (get node "value")})
                       ("Integer", "Boolean", "String")
                       (list
                         {:type "VALUE"
                          :value (get node "value")})))]
    (builder raw-ast)))


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

(defn merge-environment
  [atomic-map extern-map]
  (swap! atomic-map into extern-map))

(defn operator-table
  [op-name]
  (case op-name
    "." (fn [keyword-head & keyword-postfix]
          (apply str
            (cons (name keyword-head)
              (map #(str "-" (name %)) keyword-postfix)))
          #_(str (name keyword1) "-" (name keyword2)))
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
    "&&" (fn [& args]
           (if (every? true? args)
             true
             false))
    "||" (fn [& args]
           (if (some true? args)
             true
             false))
    "=" (fn [setter name value]
          (setter name value))))
    

(defn keyword-table
  [k]
  (case k
    "if"
    (fn [condition then-value else-value]
      (if condition
        then-value
        else-value))
    "do"
    (fn [& stmt-values]
      (if (empty? stmt-values)
        nil
        (last stmt-values)))))

(defn function-table
  [fun]
  (case fun
    "max"
    (fn [& vl]
      (apply max vl))
    "printf"
    (fn [& vl]
      (do
        (apply printf vl)
        (println)))
    "randomSelect"
    (fn [& vl]
      (rand-nth vl))
    "randnth"
    (fn [v-list]
      (rand-nth v-list))
    "range"
    (fn [start end]
      {:pre [(integer? start)
             (integer? end)]}
      (range start end))

    "boxInt"
    (fn [int-value]
      {:pre [(integer? int-value)]}
      {:type 'boxed-int
       :value int-value})

    "unboxInt"
    (fn [boxed-int-value]
      {:pre [(= 'boxed-int (:type boxed-int-value))]}
      (:value boxed-int-value))

    "makeStack"
    (fn []
      '())

    "pushStack"
    (fn [s item]
      (conj s item))
    "peekStack"
    (fn [s]
      (peek s))
    "popStack"
    (fn [s]
      (pop s))
    "isEmptyStack"
    (fn [s]
      (= '() s))

    "makeQueue"
    (fn []
      '())

    "enQueue"
    (fn [q item]
      (reverse
        (conj
          (reverse q)
          item)))
    "firstQueue"
    (fn [q]
      (peek q))
    "deQueue"
    (fn [q]
      (pop q))
    "isEmptyQueue"
    (fn [q]
      (= '() q))))

    

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
;            ("!")
;            { :operate
;              (fn [t]
;                {:value
;                 (apply op [(:value t)])})}
            ;; 运算操作符们
            ( "*", "/", "%", "+", "-", ">", "<",
              "<=", ">=", "==", "!=", "&", "^", "|",
              "&&", "||", "!")
            { :operate
              (fn [& args]
                (do
                  #_(println v " applys to " args)
                  {:value
                   (apply op
                     (map
                       #(let [value (:value %)]
                        ;; 处理变量没有绑定值的情况
                          (if (nil? value)
                            (if (contains? % :name)
                              (throw (Exception.
                                       (str "No value is bind to " (:name %))))
                              (do
                                (println %)
                                (throw (Exception.
                                         (str "No value found for operator " v)))))
                            value))
                       args))}))}
            (".")
            { :operate
              (fn [& args]
                (let [ complete-name (keyword (apply op (map :name args)))
                       complete-value (getter complete-name)]
                  { :name complete-name
                    :value complete-value}))}
            ;;
            ("=")
            { :operate
              (fn [lv rv]
                (do
                  (apply op [setter (:name lv) (:value rv)])  ;; 一开始错把第一个 t1 的选择属性写成了 :value。
                  {:value (:value rv)}))}))

        "Keyword"

        (let [action (keyword-table (:name token))]
          { :operate (fn [& args]
                       {:value (apply action (map :value args))})})
            

        "FunctionCall"
        (let [action (function-table (:name token))]
          { :operate (fn [& args]
                       {:value (apply action (map :value args))})})


        ;; 表示返回值可以提供的接口有 name 和 value 两个, name 表示可以作为右值使用。
        "VAR"
        (if (or
              (= (:name token) "true")
              (= (:name token) "false"))
          (if (= (:name token) "true")
            { :value true}
            { :value false})
          (let [keyword-name (keyword (:name token))]
            { :name keyword-name
              :value (getter keyword-name)}))

        "VALUE"
        { :value (:value token)}))))


(defn exec-ast
  [trans ast]
  (if (empty? ast)
    false
    (let [ root (first ast)
           tr-root (trans root)
           leafs (rest ast)]
      (if (= "if" (:name root))
        ;;"An ugly hack."
        (let [condition (exec-ast trans (first leafs))]
          (if (true? (:value condition))
            (apply
              (:operate tr-root)
              (list
                {:value true}
                (exec-ast trans (nth leafs 1))
                nil))
            (apply
              (:operate tr-root)
              (list
                {:value false}
                nil
                (exec-ast trans (nth leafs 2))))))
        (if (contains? tr-root :operate)
          (apply
            (:operate tr-root)
            (doall
              (map #(exec-ast trans %) leafs)))
          tr-root)))))

(defn environment-synchronize
  [val-map env2-map key-map]
  "for key :k in key-map, merge {(:k key-map) (:k val-map)} into env2-map"
  (merge-environment
    env2-map
    (reduce
      into
      {}
      (map
        (fn [k]
          {(get key-map k) (get val-map k)})
        (keys key-map)))))

(defn compute-action!
  [ast env]
  (if (empty? ast)
    true
    (exec-ast
      (get-trans-interface env)
      ast)))


(do
  (def env1 (atom {}))
  (def env (set-environment env1))
  (def tr (get-trans-interface env))
  (def ast  (build-AST "a = 2;b=3;c=4;d=a+b*c; e=max(1,2,3,4,5);"))



  (def tt (exec-ast tr ast)))

(defn generate-code
  [ast]
  (if (empty? ast)
    ""
    (let [ token (first ast)
           args (rest ast)]
      (case (:type token)
        "OP"
        ;; 运算操作符们
        (if (= 1 (count args))
          (str  (:name token) (generate-code (first args)))
          (str "(" (generate-code (first args))
                   (apply str
                     (map
                       #(str " " (:name token) " " (generate-code %))
                       (rest args)))
                   ")"))

        "Keyword"
        (case (:name token)
          "do"
          (do
            (apply str
              (map
                #(do
                   (str (generate-code %) ";\n"))
                args)))
          "if"
          (str
            "if (" (generate-code (nth args 0)) ")\n"
                   "{\n" (generate-code (nth args 1)) "\n}\nelse\n{\n"
                   (generate-code (nth args 2)) "\n}\n"))

        ;; 表示返回值可以提供的接口有 name 和 value 两个, name 表示可以作为右值使用。
        "VAR"
        (:name token)

        "VALUE"
        (str (:value token))))))

