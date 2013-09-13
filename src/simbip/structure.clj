(ns simbip.structure
  (use simbip.protocol)
  (use simbip.parser))


(defn create-token
  [value time]
   {:time time :value value})

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
  [type name source target port time guard? action!])

(defn create-transition
  ([name source target port]
   (->Transition 'Transition
     name
     source
     target
     port
     0
     "" ;; guard
     '() ;; action
     ))
  ([name source target port time]
   (->Transition 'Transition
     name
     source
     target
     port
     time
     "" ;; guard
     '() ;; action
     ))
  ([name source target port time guard? action-string]
    (let [action! (build-ASTs-from-string action-string)]
      (->Transition 'Transition
        name
        source
        target
        port
        time
        "" ;; guard
        action! ;; action
        ))))

(extend-type Transition
  Queryable

  (enable? [this place port]
    (if (and
          (equal-name? place (:source this))
          (equal-name? port (:port this)))
      true
      false))
  )









(defrecord Atomic
  [type name
   ports places transitions
   time
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
    (add-token!
      (:port t)
      (create-token
        (value-through-port
          (deref (:variables component))
          (:port t))
        (get-time component)))))
(defn- clear-port-tokens
  [component]
  (doseq [t (current-transitions component)]
    (clear! (:port t))))

(defn create-atomic
  ([name ports places init transitions time]
   (let [ var-map (atom {})
          environment (set-environment var-map)
          c (->Atomic 'Atomic
              name
              ports
              places
              transitions
              (atom time)
              var-map
              environment)]
     (do
       (doseq [s (:places c)]
         (clear! s))

       (enable! init)

       (add-port-tokens c))

     c))
  ([name ports places init transitions time variables]
   (let [ var-map (atom variables)
          environment (set-environment var-map)
          c (->Atomic 'Atomic
              name
              ports
              places
              transitions
              (atom time)
              var-map
              environment)]
     (do
       (doseq [s (:places c)]
         (clear! s))

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
            (enable? p))))
      (:transitions component))))



(defn- fire-transition
  [component t]
  (do
    (clear-port-tokens component)

    (clear! (:source t))

    ;; action stuff
    (let [trans (get-trans-interface (:environment component))]
      (build-exec-list trans (:action! t)))

    ;;some time stuff
    (set-time
      component
      (+
        (:time t)
        (get-time component)))

    (enable! (:target t))

    (add-port-tokens component)))

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
  (get-time
    ([this]
     (deref (:time this)))
    ([this port]
     (get-time this)))
  (set-time
    ([this new-value]
     (compare-and-set!
       (:time this)
       (get-time this)
       new-value))
    ([this port new-value]
     (set-time this new-value)))

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
          t (some
              #(if (enable? % current port) %)
              (:transitions this))]
      (do
        (if (nil? (:time token))
          ()
          (set-time this (:time token)))

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
   time action variables environment var-list])  ;; var-list， 类似于在 port 中的作用，完成参数代换




(defn create-interaction
  ([^String name port connections ^Integer time]
    (let [var-map (atom {})
          environment (set-environment var-map)
          up-action (build-ASTs-from-string "")
          down-action (build-ASTs-from-string "")]
      (->Interaction 'Interaction
        name
        port
        connections
        time
        { :up-action up-action
          :down-action down-action}
        var-map
        environment
        {} ;; var list
        )))
  ([name port connections time action-string-map var-list]
    (let [var-map (atom {})
          environment (set-environment var-map)
          up-action (build-ASTs-from-string (:up-action action-string-map))
          down-action (build-ASTs-from-string (:down-action action-string-map))
          ;; _ (println up-action)
          ;; _ (println "up-action: " (:up-action action-string-map))
          ;; _ (println down-action)
          ]
      (->Interaction 'Interaction
        name
        port
        connections
        time
        { :up-action up-action
          :down-action down-action}
        var-map
        environment
        var-list
        ))))



(defn- all-enable?
  [connections]
  (apply
    (every-pred (fn [c]
                  (enable?
                    (:component c)
                    (:port c))))
    connections))

(defn- fire-interaction!
  [this token]
  (let [ ports-env (atom {})
         trans (get-trans-interface (:environment this))
        ]
    (do

      ;; 重置环境
      (reset! (:variables this) {})

      ;; 把每个 port 中token中的变量都合并到辅助环境中，用来进行后续计算
      (doseq [conn (:connections this)]
        (do
          (merge-environment
            ports-env
            (:value (first (retrieve-port
                             (:component conn)
                             (:port conn)))))
          ))


      ;; 把 port 中收集上来的变量的值换个名字放到 Interaction 的变量中
      (environment-synchronize
        (deref ports-env)
        (:variables this)
        (:var-list this))

      ;; 按照 Interaction 的 UP action 进行动作
      (build-exec-list trans (:up-action (:action this)))



      ;; 从一个token中提取值，合并到当前环境中
      (merge-environment
        (:variables this)
        (:value token))

      ;; 按照 Interaction 的 DOWN action 进行动作
      (build-exec-list trans (:down-action (:action this)))


      ;; 给每个 connection 中的 port 和 component 构造一个 正确的 token，然后通过 assign-port!
      ;; 过程触发下面的 fire! 过程。

      (let [re-var-list (zipmap
                          (vals (:var-list this))
                          (keys (:var-list this)))]
        (do
          (environment-synchronize
            (deref (:variables this))
            ports-env
            re-var-list)
          (doseq [conn (:connections this)]
            (assign-port!
              (:component conn)
              (:port conn)
              ;; 最最关键的构造 token 的过程
              {:time (+
                       (:time this)
                       (:time token))
               :value (project-value
                        (deref ports-env)
                        (vals (:var-list (:port conn))))}))))

      )))

(extend-type Interaction

  Queryable

  (enable?
    ([this]
     (if (not (nil? (:port this)))
       false
       (if (all-enable? (:connections this))
         true
         false) #_ ("The guard part is ignored.")
       ))
    ([this port]
     (if (or
           (nil? port) ;; if port is nil or internal port return false.
           (not (export? port)))
       false
       (if (all-enable? (:connections this))
         true
         false) #_ ("The guard part is ignored.")
       )))

  Accessible

  (get-time
    ([this]
     (apply max
       (map #(get-time (:component %) (:port %))
         (:connections this))))
    ([this port]
     (get-time this)))

  (get-variable
    [this index attr]
    (let [connection (get (:connections this) index)
          c (:component connection)
          p (:port connection)]
      (get-variable c p attr)))
  (retrieve-port
    [this port]
    (if (all-enable? (:connections this))
      ;; only use the up-action result of the values of ports
      [(create-token
         (let [ ports-env (atom {})
                trans (get-trans-interface (:environment this))
                ]
           (do
             (reset! (:variables this) {})
             ;; 把每个 port 中token中的变量都合并到辅助环境中，用来进行后续计算
             (doseq [conn (:connections this)]
               (merge-environment
                 ports-env
                 (:value (first (retrieve-port
                                  (:component conn)
                                  (:port conn)))))
               )

             ;; 把 port 中收集上来的变量的值换个名字放到 Interaction 的变量中
             (environment-synchronize
               (deref ports-env)
               (:variables this)
               (:var-list this))


             ;; 按照 Interaction 的 UP action 进行动作
             (build-exec-list trans (:up-action (:action this)))
             ;; 生成一个 token 中使用的 value 用于向上传递
             (value-through-port
               (deref (:variables this))
               port)
             ))
         (get-time this))]
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
          :time (:time token)})))

  Fireable

  (fire!
    [this]
    (fire-interaction!
      this
      (create-token
        {}
        (get-time this))))
  )


(defrecord Compound
  [type name subcomponents exports ports])

;; exports >> {:target :source :source-component}

(defn create-compound
  [name subcomponents exports]
  (let [ports (map :target exports)]
    (->Compound 'Compound name subcomponents exports ports)))


(defn- some-enable?
  [components]
  (some
    #(enable? %)
    components))

(defn- enabled-subcomponents
  [compound]
  (filter
    #(enable? %)
    (:subcomponents compound)))

(defn get-export
  [compound port]
  (some
    #(if (= port (:target %)) %)
    (:exports compound)))


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
         :time (:time token)
          })))
  (retrieve-port
    [this port]
    (let [export (get-export this port)
          inner-port (retrieve-port
                       (:source-component export)
                       (:source export))
          inner-t (first inner-port)]
      [{ :time (:time inner-t)
         :value (value-through-port
                          (:value inner-t)
                          port)}]))

  (get-time
    ([this]
     (apply min
       (map get-time
         (filter enable?
           (:subcomponents this)))))
    ([this port]
     (let [e (get-export this port)]
       (get-time (:source-component e) (:source e)))))

  (set-time
    ([this port new-value]
     (let [e (get-export this port)]
       (set-time (:source-component e) (:source e)))))

  (top-priority
    [this rules selections]
    {:pre [(pos? (count selections))
           "The count of enabled components is more than one."]}
    (let [min-time (apply min
                     (map get-time selections))]
      (rand-nth (filter
                  #(= min-time (get-time %))
                  selections))))

  Fireable

  (fire!
    [this]
    (let [c (top-priority
              this
              []
              (enabled-subcomponents this))]
      (fire! c))))


