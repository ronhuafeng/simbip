(ns simbip.constructor
  (use simbip.structure)
  (use simbip.protocol)
  (use simbip.parser)
  (:gen-class
   :methods [^:static [createToken [clojure.lang.PersistentArrayMap Integer] clojure.lang.PersistentArrayMap]
             ^:static [createPlace [String] simbip.structure.Place]
             ^:static [createPort [String Boolean] simbip.structure.Port]
             ^:static [createPort [String Boolean clojure.lang.PersistentArrayMap] simbip.structure.Port]
             ^:static [createPort [String Boolean clojure.lang.PersistentArrayMap clojure.lang.Symbol]
                       simbip.structure.Port]
             ^:static [createTransition [String simbip.structure.Place simbip.structure.Place simbip.structure.Port]
                       simbip.structure.Transition]
             ^:static [createTransition [String
                                         simbip.structure.Place
                                         simbip.structure.Place
                                         simbip.structure.Port
                                         Integer]
                       simbip.structure.Transition]
             ^:static [createTransition [String
                                         simbip.structure.Place
                                         simbip.structure.Place
                                         simbip.structure.Port
                                         Integer
                                         String
                                         String]
                       simbip.structure.Transition]
             ^:static [createAtomic [String
                                     clojure.lang.PersistentVector
                                     clojure.lang.PersistentVector
                                     simbip.structure.Place
                                     clojure.lang.PersistentVector
                                     Integer]
                       simbip.structure.Atomic]
             ^:static [createAtomic [String
                                     clojure.lang.PersistentVector
                                     clojure.lang.PersistentVector
                                     simbip.structure.Place
                                     clojure.lang.PersistentVector
                                     Integer
                                     clojure.lang.PersistentArrayMap]
                       simbip.structure.Atomic]
             ^:static [createInteraction [String
                                          simbip.structure.Port
                                          clojure.lang.PersistentVector
                                          Integer]
                       simbip.structure.Interaction]
             ^:static [createInteraction [String
                                          simbip.structure.Port
                                          clojure.lang.PersistentVector
                                          Integer
                                          clojure.lang.PersistentArrayMap  ;; action-string-map
                                          clojure.lang.PersistentArrayMap  ;; var-list map
                                          ]
                       simbip.structure.Interaction]
             ^:static [createCompound [String
                                       clojure.lang.PersistentVector
                                       clojure.lang.PersistentVector]
                       simbip.structure.Compound]
             ^:static [createCompound [String
                                       clojure.lang.PersistentVector
                                       clojure.lang.PersistentVector
                                       clojure.lang.PersistentVector]
                       simbip.structure.Compound]
             ^:static [fire [java.lang.Object] void]
             ^:static [createExport [simbip.structure.Port
                                     simbip.structure.Port
                                     simbip.structure.Atomic]
                       clojure.lang.PersistentArrayMap]
             ^:static [createExport [simbip.structure.Port
                                     simbip.structure.Port
                                     simbip.structure.Compound]
                       clojure.lang.PersistentArrayMap]
             ^:static [createExport [simbip.structure.Port
                                     simbip.structure.Port
                                     simbip.structure.Interaction]
                       clojure.lang.PersistentArrayMap]
             ^:static [createPriority [java.lang.Object
                                       java.lang.Object
                                       String]
                       clojure.lang.PersistentArrayMap]
             ^:static [createConnection [simbip.structure.Atomic
                                         simbip.structure.Port]
                       clojure.lang.PersistentArrayMap]
             ^:static [createConnection [simbip.structure.Compound
                                         simbip.structure.Port]
                       clojure.lang.PersistentArrayMap]
             ^:static [allSubComponents [simbip.structure.Compound]
                       clojure.lang.PersistentVector]
             ^:static [allSubComponents [simbip.structure.Atomic]
                       clojure.lang.PersistentVector]
             ^:static [allEnabledSubComponents [Object]
                       clojure.lang.PersistentVector]
             ^:static [currentPlace [simbip.structure.Atomic]
                       simbip.structure.Place]
             ^:static [getVariableStrings [simbip.structure.Atomic]
                       clojure.lang.LazySeq]
             ^:static [isEnable [Object]
                       Boolean]
             ^:static [getKeyword [String]
                       clojure.lang.Keyword]
             ^:static [getSnapshot [simbip.structure.Compound]
                       clojure.lang.PersistentArrayMap]
             ^:static [restoreSnapshot [simbip.structure.Compound
                                        clojure.lang.PersistentArrayMap]
                       void]
             ^:static [getAST [String]
                       Object]
             ^:static [isValidateAST
                       [String]
                       Boolean]
             ^:static [convertCode [String]
                       String]]))

(defn -createToken
  [value timestamp]
  (create-token value timestamp))
(defn -isEnable
  [c]
  (if (true? (enable? c))
    true
    false))
(defn -getKeyword
  [str]
  (keyword str))

(defn -createExport
  [^simbip.structure.Port target ^simbip.structure.Port source source-component]
  {:target target :source source :source-component source-component})
(defn -createPriority
  [low high guard-string]
  (make-priority low high guard-string))

(defn -allEnabledSubComponents
  [component]
  (case (:type component)
    Compound
    (apply vector
      (enabled-subcomponents component))
    Atomic
    (apply vector
      (enabled-internal-transitions component))))

(defn -fire
  [component]
  (fire! component))

(defn -getSnapshot
  [this]
  (get-snapshot this))
(defn -restoreSnapshot
  [this snapshot]
  (restore-snapshot!
    this
    snapshot))

(defn -isEnable
  [component]
  (enable? component))

(defn -createPlace
  [name]
  (create-place name))

(defn -createPort
  ([name isExport]
   (create-port name isExport))
  ([name isExport varList]
   (create-port name isExport varList))
  ([name isExport varList direction]
   (create-port name isExport varList direction)))

(defn -createTransition
  ([name source target port]
   (create-transition name source target port))
  ([name source target port timestamp]
   (create-transition name source target port timestamp))
  ([name source target port timestamp guard action]
   (create-transition name source target port timestamp guard action)))

(defn -createAtomic
  ([name ports places init transitions timestamp]
   (create-atomic name ports places init transitions timestamp))
  ([name ports places init transitions timestamp variables]
   (create-atomic name ports places init transitions timestamp variables)))

(defn -createConnection
  [component port]
  {:component component :port port})

(defn -createInteraction
  ([name port connections timestamp]
   (create-interaction name port connections timestamp))
  ([name port connections timestamp action-string-map var-list]
   (create-interaction name port connections timestamp action-string-map var-list)))


(defn -createCompound
  ([name subcomponents exports]
    (create-compound name subcomponents exports))
  ( [name subcomponents exports priorities]
    (create-compound name subcomponents exports priorities)))

(defn -allSubComponents
  [component]
  (case (:type component)
    Compound (reduce
               into
               [component]
               (map -allSubComponents
                 (:subcomponents component)))
    Atomic (into [component] (:places component))
    Interaction [component]
    []))

(defn -currentPlace
  [component]
  (current-place component))
(defn -getVariableStrings
  [component]
  (if (contains? component :variables)
    (let [varList (deref (:variables component))]
      (map
        (fn [v]
          {
           "name" (str (:name component) "." (name v))
           "value" (str (get varList v))
            })
        (keys varList)))
    []))

(defn -getAST
  [action-string]
  (get-raw-AST action-string))

(defn -isValidateAST
  [action-string]
  (validate-AST? action-string))

(defn -convertCode
  [action-string]
  (generate-code
    (build-AST action-string)))
