(ns simbip.structures.Transition-test
  (use simbip.protocol)
  (use simbip.structure)
  (:require [clojure.test :refer :all ]))

;;TODO: find other value-binding scheme.
(deftest enable?-test
  (testing "enable? of Transition"
    (let [current (create-place "start")
          other (create-place "end")
          p0 (create-port "P" false)
          t1 (create-transition "t1" current current p0)
          p1 (create-port "P" false)
          p2 (create-port "Q" false)]
      (is (= true (enable? t1 current p1)))
      (is (= false (enable? t1 current p2)))
      (is (= false (enable? t1 other p1))))))
