(ns simbip.structure
  (use simbip.protocol))


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

(defn create-port
  ([name export?]
   (->Port 'Port name export? [] (atom []) 'both))
  ([name export? var-list]
   (->Port 'Port name export? var-list (atom []) 'both))
  ([name export? var-list direction]
   (->Port 'Port name export? var-list (atom []) direction)))

(defn project-value
  ;; value:  {:x 1 :y 2 :z 3}
  ;; result: {:x 1 :y 2}
  [this value]
  (reduce
    into
    {}
    (map
      (fn [attr]
        {attr (attr value)})
      (:var-list this))))

;; TODO: Token and retrieve-port is incompatible.

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
     (fn [c]
       true)
     (fn [c]
       ())))
  ([name source target port time]
   (->Transition 'Transition
     name
     source
     target
     port
     time
     (fn [c]
       true)
     (fn [c]
       ())))
  ([name source target port time guard? action!]
   (->Transition 'Transition
     name
     source
     target
     port
     time
     (fn [variables]
       true)
     action!)))



#_ (fn t-action
     [c]
     (do
       (set-variable
         c
         :x (+ 1
              (get-variable c :x )))))

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
   variables])
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
        (project-value
          (:port t)
          (deref (:variables component)))
        (get-time component)))))
(defn- clear-port-tokens
  [component]
  (doseq [t (current-transitions component)]
    (clear! (:port t))))

(defn create-atomic
  ([name ports places init transitions time]
   (let [c (->Atomic 'Atomic name
                             ports
                             places
                             transitions
                             (atom time)
                             (atom {}))]
     (do
       (doseq [s (:places c)]
         (clear! s))

       (enable! init)

       (add-port-tokens c))
     c))
  ([name ports places init transitions time variables]
   (let [c (->Atomic 'Atomic name
                             ports
                             places
                             transitions
                             (atom time)
                             (atom variables))]
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
    ((:action! t) component)

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

        (doseq [attr (keys (:value token))]
          (if (contains?
                (deref (:variables this))
                attr)
            (set-variable
              this
              attr
              (get (:value token) attr))))

        (fire-transition this t)))))



(defrecord Interaction
  [type name
   port connections ;; connection {:component :port}
   time action!])

;; Demo of action!
;; connections
;; 1 and 2 are the indexes of connection in connections
;; :x1 1 :x
;; :x2 2 :y
;; up: tokens -> token
;; down: token -> tokens
#_ (fn action!
     [I direction]
     (cond
       (= direction 'up)
       (create-token
         {:x1 (get-variable I 0 :x )
          :x2 (get-variable I 1 :x )}
         (get-time I))

       (= direction 'down)
       (fn [token]
         ;; v is result of up-action
         (let [v (:value token)]
           {p1 (create-token
                 {:x (+ (:x v) (:y v))}
                 (+ (:time I) (:time token)))
            p2 (create-token
                 {:y (+ (:x v) (:y v))}
                 (+ (:time I) (:time token)))}))))



(defn create-interaction
  ([^String name port connections ^Integer time]
   (->Interaction 'Interaction name port connections time
                               (fn [I direction]
                                 (cond
                                   (= direction 'up) {:value {} :time 0}
                                                     (= direction 'down) (fn [token] {})))))
  ([name port connections time action!]
   (->Interaction 'Interaction name port connections time action!)))



(defn- all-enable?
  [connections]
  (apply
    (every-pred (fn [c]
                  (enable?
                    (:component c)
                    (:port c))))
    connections))

(defn- fire-interaction!
  [t token]
  #_("Actually, the action part should be refactored. Because the task
      the action part completed is so heavy.")
  (let [up-token (create-token
                   (into
                     #_("up-action extracts values of variabled from each connected port.")
                       (:value ((:action! t) t 'up))
                       #_("??? why not into {}, (:value token) is correct???
                         This merge combines all new variables from up-action's computation
                         and new results from variables of outside interaction's computation.
                         Then these values can be used in the computation of values of tokens
                         which will be dispatched to corresponding ports.")
                       (:value token))
                   (:time token))
        value (:value up-token)
        down-tokens (((:action! t) t 'down) up-token)
        time-token (+ (:time up-token)
                     (:time t))]
    ;;TODO: move time addition to 'action!' function
    (doseq [c (:connections t)]
      #_("assign-port! generates a token for every sub-components. The token
          contains new values of variables and new timestamps.")
      (assign-port!
        (:component c)
        (:port c)
        ;; generate a proper token for each port
        (if (contains? down-tokens (:port c))
          (assoc
            (get down-tokens (:port c))
            :time time-token)
          (create-token
            {}
            time-token))))))

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
         (project-value
           port
           (:value ((:action! this) this 'up)))
         (get-time this))]
      []))
  #_("assign-port! : Get a token from export, means the interaction is
      involved in a larger interaction outside. We assume every interaction
      having one export atmost, so the parameter 'port' is ignored in the
      following call of fire-interaction!.")
  (assign-port!
    [this port token]
    (fire-interaction! this token))

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
    (let [export (get-export this port)]
      (assign-port!
        (:source-component export)
        (:source export)
        token)))
  (retrieve-port
    [this port]
    (let [export (get-export this port)]
      (retrieve-port
        (:source-component export)
        (:source export))))

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
      (println (:name c))
      (fire! c))))


