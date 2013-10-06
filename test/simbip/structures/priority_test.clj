(ns simbip.structures.priority-test
  (use simbip.structure)
  (:require [clojure.test :refer :all ]))


(deftest priority-test
  (let [priority-list [(make-priority :a :b "true")
                       (make-priority :a :d "true")
                       (make-priority :b :c "true")]
        priority-table (compute-priority-table priority-list)
        ]
    (testing "priority test"
      (do
        (is (= 1 1))
        (is (=
              (set [:c :d])
              (set (top-priorities
                     [:a :b :c :d]
                     priority-table))))))))