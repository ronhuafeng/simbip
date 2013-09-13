(ns simbip.constructor
  (use simbip.structure)
  (use simbip.protocol)
  (:gen-class
   :methods [^:static [createToken [clojure.lang.PersistentArrayMap Integer] clojure.lang.PersistentArrayMap]
             ^:static [createPlace [String] simbip.structure.Place]
             ^:static [createPort [String Boolean] simbip.structure.Port]
             ^:static [createPort [String Boolean clojure.lang.PersistentVector] simbip.structure.Port]
             ^:static [createPort [String Boolean clojure.lang.PersistentVector clojure.lang.Symbol]
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
             ^:static [fire [simbip.structure.Compound] void]
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
             ^:static [currentPlace [simbip.structure.Atomic]
                       simbip.structure.Place]]))

(defn -createToken
  [value time]
  (create-token value time))

(defn -createExport
  [^simbip.structure.Port target ^simbip.structure.Port source source-component]
  {:target target :source source :source-component source-component})

(defn -fire
  [component]
  (fire! component))

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
  ([name source target port time]
   (create-transition name source target port time))
  ([name source target port time guard action]
   (create-transition name source target port time guard action)))

(defn -createAtomic
  ([name ports places init transitions time]
   (create-atomic name ports places init transitions time))
  ([name ports places init transitions time variables]
   (create-atomic name ports places init transitions time variables)))

(defn -createConnection
  [component port]
  {:component component :port port})

(defn -createInteraction
  ([name port connections time]
   (create-interaction name port connections time))
  ([name port connections time action-string-map var-list]
   (create-interaction name port connections time action-string-map)))


(defn -createCompound
  [name subcomponents exports]
  (create-compound name subcomponents exports))

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
