(ns simbip.structures.Compound-test
  (use simbip.protocol)
  (use simbip.structure)
  (:require [clojure.test :refer :all ]))

(deftest all-in-one-test1
  (let [P1 (create-port "P1" true)
        Q1 (create-port "Q1" false)
        start1 (create-place "start1")
        end1 (create-place "end1")
        t1 (create-transition "t1" start1 end1 P1 0)
        t2 (create-transition "t2" end1 start1 Q1 1)
        c1 (create-atomic
             "c1"
             [P1 Q1]
             [start1 end1]
             start1
             [t1 t2]
             0)

        P2 (create-port "P2" true)
        Q2 (create-port "Q2" false)
        start2 (create-place "start2")
        end2 (create-place "end2")
        t3 (create-transition "t3" start2 end2 P2 1)
        t4 (create-transition "t4" end2 start2 Q2 2)
        c2 (create-atomic
             "c2"
             [P2 Q2]
             [start2 end2]
             start2
             [t3 t4]
             0)

        I1 (create-interaction
             "I1"
             nil
             [{:component c1 :port P1}
              {:component c2 :port P2}]
             1)

        c3 (create-compound
             "c3"
             [c1 c2 I1]
             [])]
    (testing "all-in-one test 1 of Compount"
      (is (not (enable? c1)))
      (is (not (enable? c2)))
      (is (enable? I1))
      (is (enable? c3))
      (do
        (fire! c3)
        (is (= 1 (get-timestamp c1)))
        (is (= 2 (get-timestamp c2)))
        (fire! c1)
        (fire! c2)
        (is (= 2 (get-timestamp c1)))
        (is (= 4 (get-timestamp c2)))))))


(deftest all-in-one-test2
  (let [P1 (create-port "P1" true)
        Q1 (create-port "Q1" false)
        start1 (create-place "start1")
        end1 (create-place "end1")
        t1 (create-transition "t1" start1 end1 P1)
        t2 (create-transition "t2" end1 start1 Q1)
        c1 (create-atomic
             "c1"
             [P1 Q1]
             [start1 end1]
             start1
             [t1 t2]
             0)

        P2 (create-port "P2" true)
        Q2 (create-port "Q2" false)
        start2 (create-place "start2")
        end2 (create-place "end2")
        t3 (create-transition "t3" start2 end2 P2 1)
        t4 (create-transition "t4" end2 start2 Q2)
        c2 (create-atomic
             "c2"
             [P2 Q2]
             [start2 end2]
             start2
             [t3 t4]
             0)

        EI (create-port "EI" true)
        I1 (create-interaction
             "I1"
             EI
             [{:component c1 :port P1}
              {:component c2 :port P2}]
             2)

        EC (create-port "EC" true)
        c3 (create-compound
             "c3"
             [c1 c2 I1]
             [{:target EC :source EI :source-component I1}])]
    (testing "all-in-one test 2 of Compount"
      (is (not (enable? c1)))
      (is (not (enable? c2)))
      (is (not (enable? I1)))
      (is (enable? I1 EI))
      (is (enable? c3 EC))
      (is (not= [] (retrieve-port c3 EC)))

      (do
        (assign-port! c3 EC {:timestamp 2})
        (is (enable? c1))
        (is (enable? c2))
        (is (enable? c3))
        (is (not (enable? I1)))

        (is (= 4 (get-timestamp c1)))
        (is (= 5 (get-timestamp c2)))

        (fire! c3)
        (fire! c3)

        (is (not (enable? c1)))
        (is (not (enable? c2)))
        (is (not (enable? I1)))
        (is (enable? I1 EI))
        (is (enable? c3 EC))))))


(deftest all-in-one-test3-with-vars
  (let [P1 (create-port "P1" true {:x :p1-x})
        Q1 (create-port "Q1" false)
        start1 (create-place "start1")
        end1 (create-place "end1")
        t1 (create-transition "t1" start1 end1 P1)
        t2 (create-transition "t2" end1 start1 Q1)
        c1 (create-atomic
             "c1"
             [P1 Q1]
             [start1 end1]
             start1
             [t1 t2]
             0
             {:x 1 :y 1})

        P2 (create-port "P2" true {:x :p2-x})
        Q2 (create-port "Q2" false)
        start2 (create-place "start2")
        end2 (create-place "end2")
        t3 (create-transition "t3" start2 end2 P2 1)
        t4 (create-transition "t4" end2 start2 Q2)
        c2 (create-atomic
             "c2"
             [P2 Q2]
             [start2 end2]
             start2
             [t3 t4]
             0
             {:x 1 :y 2})

        EI (create-port "EI" true)
        I1 (create-interaction
             "I1"
             EI
             [{:component c1 :port P1}
              {:component c2 :port P2}]
             2
             {
              :up-action    "if (true) { x1=dev.x;x2=ctrl.x; }"
              :down-action  "if (true) then dev.x=x1+x2;ctrl.x=x1+x2; fi"
              :guard-action "true;"
               }
             {:p1-x :dev-x :p2-x :ctrl-x})

        EC (create-port "EC" true)
        c3 (create-compound
             "c3"
             [c1 c2 I1]
             [{:target EC :source EI :source-component I1}])]
    (testing "all-in-one test 2 of Compount"
      (is (not (enable? c1)))
      (is (not (enable? c2)))
      (is (not (enable? I1)))
      (is (enable? I1 EI))
      (is (enable? c3 EC))
      (is (not= [] (retrieve-port c3 EC)))

      (do
        (assign-port! c3 EC {:value {} :timestamp 2})
        (is (enable? c1))
        (is (enable? c2))
        (is (enable? c3))
        (is (not (enable? I1)))

        (is (= 4 (get-timestamp c1)))
        (is (= 5 (get-timestamp c2)))

        (is (= 2 (get-variable c1 :x )))
        (is (= 2 (get-variable c2 :x )))

        (fire! c3)
        (fire! c3)

        (is (not (enable? c1)))
        (is (not (enable? c2)))
        (is (not (enable? I1)))
        (is (enable? I1 EI))
        (is (enable? c3 EC))))))

(deftest all-in-one-test4-with-vars-hierarchy
  (let [P1 (create-port "P1" true {:x :p1-x})
        Q1 (create-port "Q1" false)
        start1 (create-place "start1")
        end1 (create-place "end1")
        t1 (create-transition "t1" start1 end1 P1)
        t2 (create-transition "t2" end1 start1 Q1)
        c1 (create-atomic
             "c1"
             [P1 Q1]
             [start1 end1]
             start1
             [t1 t2]
             0
             {:x 2 :y 1})

        P2 (create-port "P2" true {:x :p2-x})
        Q2 (create-port "Q2" false)
        start2 (create-place "start2")
        end2 (create-place "end2")
        t3 (create-transition "t3" start2 end2 P2 1)
        t4 (create-transition "t4" end2 start2 Q2)
        c2 (create-atomic
             "c2"
             [P2 Q2]
             [start2 end2]
             start2
             [t3 t4]
             0
             {:x 2 :y 2})

        EI (create-port "EI" true {:x1 :ei-x1})
        I1 (create-interaction
             "I1"
             EI
             [{:component c1 :port P1}
              {:component c2 :port P2}]
             2
             {
              :up-action   "x1=dev.x;x2=ctrl.x;"
              :down-action "dev.x=x1+x1;ctrl.x=x1+x2;"
              :guard-action "true;"
               }
             {:p1-x :dev-x :p2-x :ctrl-x})

        EC (create-port "EC" true {:ei-x1 :ec-x1})
        c3 (create-compound
             "c3"
             [c1 c2 I1]
             [{:target EC :source EI :source-component I1}])

        P4 (create-port "P4" true {:x :p4-x})
        state (create-place "state")
        tloop (create-transition "tloop" state state P4 1)
        c4 (create-atomic
             "c4"
             [P4]
             [state]
             state
             [tloop]
             0
             {:x 1})

        I2 (create-interaction
             "I2"
             nil
             [{:component c3 :port EC}
              {:component c4 :port P4}]
             2
             {
              :up-action   "x1=dev.x1;x=ctrl.x;"
              :down-action "dev.x1 = x1+1;ctrl.x=x1+1;"
              :guard-action "true;"
               }
             {:ec-x1 :dev-x1 :p4-x :ctrl-x})]
    (testing "all-in-one test 2 of Compount"
      (do
        (is (not (enable? c1)))
        (is (not (enable? c2)))
        (is (not (enable? I1)))
        (is (enable? I1 EI))
        (is (enable? c3 EC))
        (is (= (get-export c3 EC)
              {:source EI :source-component I1 :target EC}))
        (is (= [{:timestamp 0 :value {:ei-x1 2}}] (retrieve-port I1 EI)))
        (is (= [{:timestamp 0 :value {:ec-x1 2}}] (retrieve-port c3 EC))))

      (do
        (let [c3-snapshot (get-snapshot c3)]
          (fire! I2)
          (println (get-snapshot c3))


          (is (= 6 (get-variable c1 :x )))
          (is (= 5 (get-variable c2 :x )))
          (is (= 3 (get-variable c4 :x )))


          (restore-snapshot! c3
            c3-snapshot)
          (is (= 2 (get-variable c1 :x )))
          (is (= 2 (get-variable c2 :x )))
          (do
            (is (not (enable? c1)))
            (is (not (enable? c2)))
            (is (not (enable? I1)))
            (is (enable? I1 EI))
            (is (enable? c3 EC))
            (is (= (get-export c3 EC)
                  {:source EI :source-component I1 :target EC}))
            (is (= [{:timestamp 0 :value {:ei-x1 2}}] (retrieve-port I1 EI)))
            (is (= [{:timestamp 0 :value {:ec-x1 2}}] (retrieve-port c3 EC)))))
        ))))



(deftest all-in-one-test4-with-vars-hierarchy-connector
;; -export-port-connector-connected-connector
  (let [P1 (create-port "P1" true {:x :p1-x})
        Q1 (create-port "Q1" false)
        start1 (create-place "start1")
        end1 (create-place "end1")
        t1 (create-transition "t1" start1 end1 P1)
        t2 (create-transition "t2" end1 start1 Q1)
        c1 (create-atomic
             "c1"
             [P1 Q1]
             [start1 end1]
             start1
             [t1 t2]
             0
             {:x 2 :y 1})

        P2 (create-port "P2" true {:x :p2-x})
        Q2 (create-port "Q2" false)
        start2 (create-place "start2")
        end2 (create-place "end2")
        t3 (create-transition "t3" start2 end2 P2 1)
        t4 (create-transition "t4" end2 start2 Q2)
        c2 (create-atomic
             "c2"
             [P2 Q2]
             [start2 end2]
             start2
             [t3 t4]
             0
             {:x 2 :y 2})

        EI (create-port "EI" true {:x1 :ei-x1})
        I1 (create-interaction
             "I1"
             EI
             [{:component c1 :port P1}
              {:component c2 :port P2}]
             2
             {
               :up-action   "x1=dev.x;x2=ctrl.x;"
               :down-action "dev.x=x1+x1;ctrl.x=x1+x2;"
               :guard-action "true;"
               }
             {:p1-x :dev-x :p2-x :ctrl-x})


        P4 (create-port "P4" true {:x :p4-x})
        state (create-place "state")
        tloop (create-transition "tloop" state state P4 1)
        c4 (create-atomic
             "c4"
             [P4]
             [state]
             state
             [tloop]
             0
             {:x 1})

        I2 (create-interaction
             "I2"
             nil
             [{:component I1 :port EI}
              {:component c4 :port P4}]
             2
             {
               :up-action   "x1=dev.x1;x=ctrl.x;"
               :down-action "dev.x1 = x1+1;ctrl.x=x1+1;"
               :guard-action "true;"
               }
             {:ei-x1 :dev-x1 :p4-x :ctrl-x})]
    (testing "all-in-one test 2 of Compount"
      (do
        (is (not (enable? c1)))
        (is (not (enable? c2)))
        (is (not (enable? I1)))
        (is (enable? I1 EI))
        (is (= [{:timestamp 0 :value {:ei-x1 2}}] (retrieve-port I1 EI)))

      (do
        (let []
          (is (= true (enable? I2)))
          (fire! I2)


          (is (= 6 (get-variable c1 :x )))
          (is (= 5 (get-variable c2 :x )))
          (is (= 3 (get-variable c4 :x )))
          )
        )))))
