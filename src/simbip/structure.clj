(ns simbip.structure
  (use simbip.protocol)
  (use simbip.parser))


(defn create-token
  [value timestamp]
   {:timestamp timestamp :value value})

(defrecord Place
  [type name active?])

(defn create-place
  [name]
  (->Place 'Place name (atom false)))


(extend-type Place
  Queryable
  (equal-name?
    [this that]
    (and (= 'Place
           (:type this)
           (:type that))
      (= (:name this)
        (:name that))))
  (enable? [this]
    (deref (:active? this)))

  Accessible
  (get-snapshot
    [this]
    { :active? (deref (:active? this))})
  (restore-snapshot!
    [this snapshot]
    (let [{:keys [active?]} snapshot]
      (do
        (reset!
          (:active? this)
          active?))))

  (clear! [this]
    (compare-and-set! (:active? this)
      (deref (:active? this))
      false))
  (enable! [this]
    (compare-and-set! (:active? this)
      (deref (:active? this))
      true)))



(defrecord Port
  [type name export? var-list tokens direction])
;; var-list is a map of {:var-name-in-component :var-name-in-port's-type, ...}
(defn create-port
  ([name export?]
   (->Port 'Port
     name
     export?
     []
     (atom [])
     'both
     ))
  ([name export? var-list]
   (->Port 'Port
     name
     export?
     var-list
     (atom [])
     'both
     ))
  ([name export? var-list direction]
   (->Port 'Port
     name
     export?
     var-list
     (atom [])
     direction
     )))

(defn project-value
  ;; value:  {:x 1 :y 2 :z 3}   val-keys {:x :y}
  ;; result: {:x 1 :y 2}
  [value val-keys]
  (reduce
    into
    {}
    (map
      (fn [attr]
        {attr (get value attr)})
      val-keys)))

;; 把 {:a 1 :b 2} {:a :x :b :y} 转化成 {:x 1 :y 2}
(defn convert-value
  [key-value key-map]
  (reduce
    into
    {}
    (map
      (fn [k]
        {(get key-map k) (get key-value k)})
      (keys key-value))))

(defn value-through-port
  [value-map port]
  (convert-value
    (project-value
      value-map
      (keys (:var-list port)))
    (:var-list port)))
(defn value-back-port
  [value-map port]
  (let [var-list (:var-list port)]
    (convert-value
      (project-value
        value-map
        (vals var-list))
      (zipmap (vals var-list) (keys var-list)))))


(extend-type Port

  Accessible
  (get-snapshot
    [this]
    { :tokens (deref (:tokens this))})
  (restore-snapshot!
    [this snapshot]
    (let [{:keys [tokens]} snapshot]
      (do
        (println (:name this) ": tokens -> " tokens)
        (reset!
          (:tokens this)
          tokens))))

  (add-token!
    [port token]
    (swap! (:tokens port)
      conj
      token))

  (retrieve-port
    [this]
    (deref (:tokens this)))

  (clear!
    [this]
    (compare-and-set! (:tokens this)
      (retrieve-port this)
      []))

  Queryable

  (export?
    [port]
    (:export? port))


  (equal-name?
    [this that]
    (and (= 'Port
           (:type this)
           (:type that))
      (= (:name this)
        (:name that))))
  (enable?
    ([this]
     (if (= 'input (:direction this))
       true
       (> (count (retrieve-port this))
         0)))))

(defrecord Transition
  [type name source target port timestamp guard? action! atomic-parent])

(defn create-transition
  ([name source target port]
   (->Transition 'Transition
     name
     source
     target
     port
     0
     (build-AST "true;") ;; guard
     (build-AST "") ;; action
     (atom nil)))
  ([name source target port timestamp]
   (->Transition 'Transition
     name
     source
     target
     port
     timestamp
     (build-AST "true;") ;; guard
     (build-AST "") ;; action
     (atom nil)))
  ([name source target port timestamp guard-string action-string]
    (let [ action! (build-AST action-string)
           guard? (build-AST (str guard-string ";"))]
      (->Transition 'Transition
        name
        source
        target
        port
        timestamp
        guard? ;; guard
        action! ;; action
        (atom nil)))))











(defrecord Atomic
  [type name
   ports places transitions
   timestamp
   variables
   environment])
;; variable: {:x v-x :y v-y}

(defn current-place
  [component]
  (first
    (filter enable?
      (:places component))))

(defn current-transitions
  [component]
  (let [current (current-place component)]
    (filter
      #(= current (:source %))
      (:transitions component))))

(defn- add-port-tokens
  [component]
  (doseq [t (current-transitions component)]
    (if (:value (compute-action!
                  (:guard? t)
                  (:environment component)))
      (add-token!
        (:port t)
        (create-token
          (value-through-port
            (deref (:variables component))
            (:port t))
          (get-timestamp component))))))
(defn- clear-port-tokens
  [component]
  (doseq [t (current-transitions component)]
    (clear! (:port t))))

(defn create-atomic
  ([name ports places init transitions timestamp]
   (let [ var-map (atom {})
          environment (set-environment var-map)
          c (->Atomic 'Atomic
              name
              ports
              places
              transitions
              (atom timestamp)
              var-map
              environment)]
     (do
       (doseq [s (:places c)]
         (clear! s))

       (doseq [t (:transitions c)]
         (reset! (:atomic-parent t) c))

       (enable! init)

       (add-port-tokens c))

     c))
  ([name ports places init transitions timestamp variables]
   (let [ var-map (atom variables)
          environment (set-environment var-map)
          c (->Atomic 'Atomic
              name
              ports
              places
              transitions
              (atom timestamp)
              var-map
              environment)]
     (do
       (doseq [s (:places c)]
         (clear! s))

       (doseq [t (:transitions c)]
         (reset! (:atomic-parent t) c))

       (enable! init)

       (add-port-tokens c))
     c)))


(defn enabled-internal-transitions
  [component]
  (let [current (current-place component)]
    (filter
      (fn internal-transition-enable?
        [t]
        (let [p (:port t)]
          (and
            (enable? t current p)
            (not (export? p))
            (enable? p)
            (:value (compute-action!
                      (:guard? t)
                      (:environment component))))))
      (:transitions component))))



(defn- fire-transition
  [component t]
  (do
    (clear-port-tokens component)

    (clear! (:source t))

    ;; action stuff
    (let [trans (get-trans-interface (:environment component))]
      (exec-ast trans (:action! t)))

    ;;some timestamp stuff
    (set-timestamp
      component
      (+
        (:timestamp t)
        (get-timestamp component)))

    (enable! (:target t))

    (add-port-tokens component)))


(extend-type Transition
  Queryable

  (enable? [this place port]
    (if (and
          (equal-name? place (:source this))
          (equal-name? port (:port this)))
      true
      false))
  Fireable
  (fire!
    [this]
    {:pre [(not (nil? @(:atomic-parent this)))]}
    (fire-transition
      @(:atomic-parent this)
      this))
  )

(extend-type Atomic
  Queryable
  (enable?
    ([this]
     (if (not-empty
           (enabled-internal-transitions this))
       true
       false))
    ([this port] #_ ("Note: may return nil.")
     (if (and
           (enable? port)
           (export? port)
           (contain-port? this port))
       true
       false)))
  (contain-port?
    [this port]
    (if (not-empty
          (filter
            #(= % port)
            (:ports this)))
      true
      false))

  Fireable
  (fire!
    [this]
    (let [tl (enabled-internal-transitions this)
          t (top-priority this nil tl)]
      (fire-transition this t)))

  Accessible
  (get-snapshot
    [this]
    { :ports (apply
               merge
               (map
                 (fn [p]
                   {(:name p) (get-snapshot p)})
                 (:ports this)))
      :places (apply
                merge
                (map
                  (fn [p]
                    {(:name p) (get-snapshot p)})
                  (:places this)))
      :variables (deref (:variables this))
      :timestamp (deref (:timestamp this))})
  (restore-snapshot!
    [this snapshot]
    (let [{:keys [ports places variables timestamp]} snapshot]
      (do
        ;; restore ports
        (doseq [p (:ports this)]
          (restore-snapshot!
            p
            (get ports (:name p))))
        ;; restore places
        (doseq [p (:places this)]
          (restore-snapshot!
            p
            (get places (:name p))))
        ;; restore variables
        (reset!
          (:variables this)
          variables)
        ;; restore timestamp
        (reset!
          (:timestamp this)
          timestamp))))

  (get-timestamp
    ([this]
     (deref (:timestamp this)))
    ([this port]
     (get-timestamp this)))
  (set-timestamp
    ([this new-value]
     (compare-and-set!
       (:timestamp this)
       (get-timestamp this)
       new-value))
    ([this port new-value]
     (set-timestamp this new-value)))

  (get-variable
    ([this attr]
     (attr (deref (:variables this))))
    ([this port attr]
     (attr (deref (:variables this)))))
  (set-variable
    ([this attr new-value]
     (swap! (:variables this)
       assoc
       attr new-value)))

  (retrieve-port
    [this port]
    (if (contain-port? this port)
      (retrieve-port port)
      []))
  (top-priority
    [this rules selections]
    {:pre [(pos? (count selections))
           "The count of enabled transitions is more than one."]}
    (rand-nth selections))
  (assign-port!
    [this port token]
    (let [current (current-place this)
          ;; get the transition to fire.
          tl (filter
               #(and
                 (enable? % current port)
                 ;;增加了处理一个 place 上发出的 transition 上有相同 port 的情况
                 (:value (compute-action!
                           (:guard? %)
                           (:environment this))))
               (:transitions this))
          t (if (= 1 (count tl))
              (first tl)
              (throw (Exception. (str
                                   "Enabled transition is not equal to 1, actually "
                                   (count tl)
                                   ", component is: " (:name this) ""
                                   ", port is: " (:name port) ""
                                   ", \n and transitions are: \n"
                                   (apply str
                                     (map #(str (:name %) ", ")
                                       tl))))))]
      (do
        (if (nil? (:timestamp token))
          ()
          (set-timestamp this (:timestamp token)))

      ;; 如果 token 中的 key 是 port 的 var-list 中的 val 中的值，说明这个变量是被 port 映射出来的
      ;; 在外面的名字是 var-list 中 key 对应的 val 的值
        #_(let [assigned-value (value-back-port
                               (:value token)
                               port)]
          (doseq [attr (keys assigned-value)]
            (if (contains?
                  (deref (:variables this))
                  attr)
              (set-variable
                this
                attr
                (get assigned-value attr)))))
        (doseq [attr (keys (:value token))]
          (let [t (some
                    #(if (= attr (val %)) %)
                    (:var-list port))]     ;; 筛选到上面提到的 key－val 对
            (if t
              (set-variable
                this
                (key t)
                (get (:value token) attr)))))    ;; 其实 attr 就等于 (val t)

        (fire-transition this t)))))



(defrecord Interaction
  [type name
   port connections ;; connection {:component :port}
   timestamp action variables environment var-list])  ;; var-list， 类似于在 port 中的作用，完成参数代换




(defn create-interaction
  ([^String name port connections ^Integer timestamp]
    (let [var-map (atom {})
          environment (set-environment var-map)
          up-action (build-AST "")
          down-action (build-AST "")
          guard-action (build-AST "true;")]
      (->Interaction 'Interaction
        name
        port
        connections
        timestamp
        { :up-action up-action
          :down-action down-action
          :guard-action guard-action}
        var-map
        environment
        {} ;; var list
        )))
  ([name port connections timestamp action-string-map var-list]
    (let [ var-map (atom {})
           environment (set-environment var-map)
           up-action (build-AST (:up-action action-string-map))
           down-action (build-AST (:down-action action-string-map))
           guard-action (build-AST (str (:guard-action action-string-map) ";"))
           ;;_
           #_(do
               (println up-action)
               (println "up-action: " (:up-action action-string-map))
               (println down-action)
               (println "down-action: " (:down-action action-string-map))
               (println down-action)
               (println "guard-action: " (:guard-action action-string-map)))
          ]
      (->Interaction 'Interaction
        name
        port
        connections
        timestamp
        { :up-action up-action
          :down-action down-action
          :guard-action guard-action}
        var-map
        environment
        var-list
        ))))


;; 是否 interaction 连接的所有 port 都是激活的
(defn- all-enable?
  [connections]
  (apply
    (every-pred (fn [c]
                  (enable?
                    (:component c)
                    (:port c))))
    connections))

;; 把每个 port 中token中的变量都合并一个 map 中，用来进行后续计算
(defn get-ports-map
  [interaction]
  (apply merge
    (map (fn [conn]
           (:value (first (retrieve-port
                            (:component conn)
                            (:port conn)))))
      (:connections interaction))))
(defn- extract-ports-to-env!
  [interaction]
  (do
    ;; 重置环境
    (reset! (:variables interaction) {})

    ;; 把 port 中收集上来的变量的值换个名字放到 Interaction 的变量中
    (environment-synchronize
      (get-ports-map interaction)
      (:variables interaction)
      (:var-list interaction))))

(defn- do-up-action!
  [interaction]
  (do
    (extract-ports-to-env! interaction)

    ;; 按照 Interaction 的 UP action 进行动作
    (compute-action!
      (:up-action (:action interaction))
      (:environment interaction))))

(defn- do-down-action!
  [interaction timestamp]
  ;; 给每个 connection 中的 port 和 component 构造一个 正确的 token，然后通过 assign-port!
  ;; 过程触发下面的 fire! 过程。
  (let [ ports-env (atom {})
         re-var-list (zipmap
                       (vals (:var-list interaction))
                       (keys (:var-list interaction)))]
    (do
      ;; 按照 Interaction 的 DOWN action 进行动作
      (compute-action!
        (:down-action (:action interaction))
        (:environment interaction))

      (environment-synchronize
        (deref (:variables interaction))
        ports-env
        re-var-list)

      (doseq [conn (:connections interaction)]
        (assign-port!
          (:component conn)
          (:port conn)
          ;; 最最关键的构造 token 的过程
          {:timestamp (+
                   (:timestamp interaction)
                   timestamp)
           :value (project-value
                    (deref ports-env)
                    (vals (:var-list (:port conn))))})))))

(defn- fire-interaction!
  [this token]
  (do
    (do-up-action! this)

    ;; Attention! 下面开始进行 Down Action 的动作
    ;; 从一个token中提取值，合并到当前 interaction 的环境中
    (merge-environment
      (:variables this)
      (:value token))

    (do-down-action! this (:timestamp token))))

(extend-type Interaction

  Queryable

  (enable?
    ([this]
     (if (not (nil? (:port this)))
       false
       (if (all-enable? (:connections this))
         (do
           (extract-ports-to-env! this)
           (:value (compute-action!
                     (:guard-action (:action this))
                     (:environment this))))
         false)
       ))
    ([this port]
     (if (or
           (nil? port) ;; if port is nil or internal port return false.
           (not (export? port)))
       false
       (if (all-enable? (:connections this))
         (do
           (extract-ports-to-env! this)
           (:value (compute-action!
                     (:guard-action (:action this))
                     (:environment this))))
         false)
       )))

  Accessible

  (get-timestamp
    ([this]
     (apply max
       (map #(get-timestamp (:component %) (:port %))
         (:connections this))))
    ([this port]
     (get-timestamp this)))

  (get-variable
    [this index attr]
    (let [connection (get (:connections this) index)
          c (:component connection)
          p (:port connection)]
      (get-variable c p attr)))
  (retrieve-port
    [this port]
    (if (enable? this port)
      ;; only use the up-action result of the values of ports
      [(create-token
         (do
           (do-up-action! this)
           ;; 生成一个 token 中使用的 value 用于向上传递
           (value-through-port
             (deref (:variables this))
             port)
           )
         (get-timestamp this))]
      []))
  #_("assign-port! : Get a token from export, means the interaction is
      involved in a larger interaction outside. We assume every interaction
      having one export atmost, so the parameter 'port' is ignored in the
      following call of fire-interaction!.")
  (assign-port!
    [this port token]
    ;; 最初这里的 token 中变量名字没有做修改，所以会发生错误
    (let [assigned-value (value-back-port
                           (:value token)
                           port)]
      (fire-interaction!
        this
        { :value assigned-value
          :timestamp (:timestamp token)})))

  Fireable

  (fire!
    [this]
    (fire-interaction!
      this
      (create-token
        {}
        (get-timestamp this))))
  )


(defrecord Compound
  [type name subcomponents exports ports priorities])

;; exports >> {:target :source :source-component}

(defn create-compound
  ([name subcomponents exports]
    (let [ports (map :target exports)]
      (->Compound 'Compound
        name
        subcomponents
        exports
        ports
        [])))
  ([name subcomponents exports priorities]
    (let [ports (map :target exports)]
      (->Compound 'Compound
        name
        subcomponents
        exports
        ports
        priorities))))


(defn- some-enable?
  [components]
  (some
    #(enable? %)
    components))



(defn get-export
  [compound port]
  (some
    #(if (= port (:target %)) %)
    (:exports compound)))

(defn make-priority
  [low high guard-string]
  { :low low
    :high high
    :guard? (build-AST (str guard-string ";"))})

(defn compute-priority-table
  [priority-list]
  "Assume all guards to be true, i.e. the priority-list is a filtered result of guards."
  (let [ get-table-item (fn [table x y]
                          (get
                            (get table x)
                            y))
         assoc-table-item (fn [table x y value]
                            (assoc
                              table
                              x
                              (assoc
                                (get table x)
                                y
                                value)))
         init-table (reduce
                      (fn [table priority-item]
                        (assoc-table-item
                          table
                          (:low priority-item)
                          (:high priority-item)
                          true))
                      {}
                      priority-list)
         index-set (set (into
                          (map :low priority-list)
                          (map :high priority-list)))
         ;; build a lazy seq to serve reduce method.
         update-list (for [ m index-set
                            x index-set]
                       [m x])]


    ;; algorithm: Improved-Transitive-Closure (A)
    ;;
    ;; input: A
    ;; output: updated A
    ;;
    ;; begin
    ;;   for m := 1 to n do
    ;;     for x := 1 to n do
    ;;       if A[x, m] then
    ;;         for y := 1 to n do
    ;;           if A[m, y] then A[x, y] := true
    ;; end
    (reduce
      (fn [table update-item]
        (let [[m x] update-item]
          (if (true?
                (get-table-item table x m))
            (reduce
              (fn [t y]
                (if (true?
                      (get-table-item t m y))
                  (assoc-table-item t x y true)
                  t))
              table
              index-set)
            table)))
      init-table
      update-list)))

(defn top-priorities
  [candidates priority-table]
  (let [get-table-item (fn [table x y]
                         (get
                           (get table x)
                           y))]
    (if (empty? priority-table)
      candidates
      (filter
        (fn [x]
          (not
            (some
              (fn [y]
                (true?
                  (get-table-item priority-table x y)))
              candidates)))
        candidates))))

(defn enabled-subcomponents
  [compound]
  (let [component-list (filter
                         #(enable? %)
                         (:subcomponents compound))]
    (top-priorities
      component-list
      (compute-priority-table
        (:priorities compound)))))

(extend-type Compound
  Queryable

  (enable?
    ([this]
     (if (some-enable?
           (:subcomponents this))
       true
       false))
    ([this port]
     (if (let [export (get-export this port)]
           (enable?
             (:source-component export)
             (:source export)))
       true
       false)))

  Accessible
  (get-snapshot
    [this]
    { :subcomponents (apply
                       merge
                       (map
                         (fn [c]
                           {(:name c) (get-snapshot c)})
                         (filter
                           #(not= 'Interaction
                              (:type %))
                           (:subcomponents this))))})
  (restore-snapshot!
    [this snapshot]
    (let [{:keys [subcomponents]} snapshot]
      (do
        ;; restore sub components
        (doseq [c (filter
                    #(not= 'Interaction
                       (:type %))
                    (:subcomponents this))]
          (restore-snapshot!
            c
            (get subcomponents (:name c)))))))

  (get-variable
    [this port attr]
    (let [token (first (retrieve-port this port))]
      (get (:value token) attr)))

  (assign-port!
    [this port token]
    (let [ export (get-export this port)
           assigned-value (value-back-port
                            (:value token)
                            port)]
      (assign-port!
        (:source-component export)
        (:source export)
        {
         :value assigned-value
         :timestamp (:timestamp token)
          })))
  (retrieve-port
    [this port]
    (let [export (get-export this port)
          inner-port (retrieve-port
                       (:source-component export)
                       (:source export))
          inner-t (first inner-port)]
      [{ :timestamp (:timestamp inner-t)
         :value (value-through-port
                          (:value inner-t)
                          port)}]))

  (get-timestamp
    ([this]
     (apply min
       (map get-timestamp
         (filter enable?
           (:subcomponents this)))))
    ([this port]
     (let [e (get-export this port)]
       (get-timestamp (:source-component e) (:source e)))))

  (set-timestamp
    ([this port new-value]
     (let [e (get-export this port)]
       (set-timestamp (:source-component e) (:source e)))))

  (top-priority
    [this rules selections]
    {:pre [(pos? (count selections))
           "The count of enabled components is more than one."]}
    (let [min-timestamp (apply min
                     (map get-timestamp selections))]
      (rand-nth (filter
                  #(= min-timestamp (get-timestamp %))
                  selections))))

  Fireable

  (fire!
    [this]
    (let [c (top-priority
              this
              []
              (enabled-subcomponents this))]
      (fire! c))))


