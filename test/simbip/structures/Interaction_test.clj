(ns simbip.structures.Interaction-test
  (use simbip.protocol)
  (use simbip.structure)
  (:require [clojure.test :refer :all ]))


#_(deftest all-in-one-test
  (let [start (create-place "start")
        end (create-place "end")

        I1 (create-port "I1" false)
        I2 (create-port "I2" false)
        E1 (create-port "E1" true)

        t1 (create-transition "t1" start end I1)
        t2 (create-transition "t2" start end E1)
        t3 (create-transition "t3" end start I2)

        C1 (create-atomic
             "C1"
             [I1 I2 E1]
             [start end]
             start
             [t1 t2 t3]
             0)
        ;;------------------------------------;;
        idle (create-place "idle")
        run (create-place "run")

        R1 (create-port "R1" true)
        R2 (create-port "R2" false)

        t4 (create-transition "t4" idle run R1)
        t5 (create-transition "t5" run idle R2)

        C2 (create-atomic
             "C2"
             [R1 R2]
             [idle run]
             idle
             [t4 t5]
             0)
        ;;------------------------------------;;
        PG (create-port "PG" true)
        G1 (create-interaction
             "G1"
             nil
             [{:component C1 :port E1}
              {:component C2 :port R1}]
             1)
        G2 (create-interaction
             "G2"
             PG
             [{:component C1 :port E1}
              {:component C2 :port R1}]
             1)]
    (testing "all-in-one testing of Interaction"
      (is (enable? C1 E1))
      (is (enable? C2 R1))
      (is (enable? G1))
      (is (enable? G2 PG))
      (is (not= [] (retrieve-port G2 PG)))
      (do
        (assign-port! C1 E1 {:time 1})
        (is (= 1 (get-time C1)))
        (is (enable? C1))
        (fire! C1)
        (is (enable? C1))
        (is (enable? C1 E1))
        (set-time C1 0))
      (do
        (assign-port! C2 R1 {:time 2})
        (is (enable? C2))
        (fire! C2)
        (is (enable? C2 R1))
        (is (= 2 (get-time C2)))
        (set-time C2 0))
      (do
        (fire! G1)
        (is (enable? C1))
        (is (enable? C2))
        (is (= 1 (get-time C1)))
        (is (= 1 (get-time C2))))
      (do
        (fire! C1)
        (fire! C2)
        (is (enable? G1))
        (is (enable? G2 PG)))

      (do
        (assign-port! G2 PG {:time 0})
        (is (enable? C1))
        (is (enable? C2))
        (is (= 1 (get-time C1)))
        (is (= 1 (get-time C2))))
      (do
        (fire! C1)
        (fire! C2)
        (is (enable? G1))
        (is (enable? G2 PG)))
      (is (not= [] (retrieve-port G2 PG))))))

(deftest all-in-one-test-with-vars
  (let [start (create-place "start")
        end (create-place "end")

        I1 (create-port "I1" false)
        I2 (create-port "I2" false)
        E1 (create-port "E1" true {:x :e1-x})

        t1 (create-transition "t1" start end I1)
        t2 (create-transition "t2" start end E1)
        t3 (create-transition "t3" end start I2)

        C1 (create-atomic
             "C1"
             [I1 I2 E1]
             [start end]
             start
             [t1 t2 t3]
             0
             {:x 1})
        ;;------------------------------------;;
        idle (create-place "idle")
        run (create-place "run")

        R1 (create-port "R1" true {:x :r1-x})
        R2 (create-port "R2" false)

        t4 (create-transition "t4" idle run R1)
        t5 (create-transition "t5" run idle R2)

        C2 (create-atomic
             "C2"
             [R1 R2]
             [idle run]
             idle
             [t4 t5]
             0
             {:x 2})
        ;;------------------------------------;;
        PG (create-port "PG" true {:xx :pg-xx})
        G1 (create-interaction
             "G1"
             nil
             [{:component C1 :port E1}
              {:component C2 :port R1}]
             1
             {
              :up-action   ""
              :down-action "dev.x=dev.x+ctrl.x;"
              :guard-action "1==1"
               }
             {:e1-x :dev-x :r1-x :ctrl-x})
        G2 (create-interaction
             "G2"
             PG
             [{:component C1 :port E1}
              {:component C2 :port R1}]
             1
             {
              :up-action "xx=dev.x;"
              :down-action ""
              :guard-action "true"
               }
             {:e1-x :dev-x :r1-x :ctrl-x})]
    (testing "all-in-one testing of Interaction with vars."
      (is (enable? C1 E1))
      (is (enable? C2 R1))
      (is (enable? G1))
      (is (enable? G2 PG))
      (is (= [{:time 0 :value {:r1-x 2}}] (retrieve-port C2 R1)))

      (do
        (assign-port! C1 E1
                         (create-token
                           {}
                           1))
        (is (= 1 (get-time C1)))
        (is (enable? C1))
        (set-time C1 0)
        (fire! C1)
        (is (enable? C1))
        (is (enable? C1 E1)))

      (do
        (assign-port! C2 R1
                         (create-token
                           {}
                           2))
        (is (enable? C2))
        (is (= 2 (get-time C2)))
        (set-time C2 0)
        (fire! C2)
        (is (enable? C2 R1)))


      (do
        (is (= 0 (get-time C1)))
        (is (= 0 (get-time C2)))
        (is (= 1 (get-variable C1 :x )))
        (is (= 2 (get-variable C2 :x )))

        (is (= [{:value {:e1-x 1} :time 0}]
              (retrieve-port C1 E1)))
        (is (= [{:value {:r1-x 2} :time 0}]
              (retrieve-port C2 R1)))

        (is (= true (enable? G1)))
        (fire! G1)
        (is (= 3 (get-variable C1 :x )))
        (is (enable? C1))
        (is (enable? C2))
        (is (= 1 (get-time C1)))
        (is (= 1 (get-time C2))))


      (do
        (set-time C1 0)
        (set-time C2 0)
        (set-variable C1 :x 1)
        (set-variable C2 :x 2)
        (fire! C1)
        (fire! C2)
        (is (enable? G1))
        (is (enable? G2 PG))

        (is (=
              [(create-token
                 {:pg-xx 1}
                 0)]
              (retrieve-port G2 PG))))

      (do
        (assign-port!
          G2
          PG
          (create-token
            {}
            0))
        (is (enable? C1))
        (is (enable? C2))
        (is (= 1 (get-time C1)))
        (is (= 1 (get-time C2))))
      (do
        (fire! C1)
        (fire! C2)
        (is (enable? G1))
        (is (enable? G2 PG)))
      (is (not= [] (retrieve-port G2 PG))))))
