(ns simbip.structures.Place-test
  (use simbip.protocol)
  (use simbip.structure)
  (:require [clojure.test :refer :all ]))


(deftest equal-name?-test
  (testing "equal? of Place"
    (is (equal-name? (create-place "start")
          (create-place "start")))
    (is (equal-name? (create-place "end")
          (create-place "end")))
    (is (= false (equal-name?
                   (create-place "start")
                   (create-place "end"))))))

(deftest enable?-test
  (testing "enable? of Place"
    (is (= false (enable? (create-place "start"))))
    (is (let [p (create-place "start")]
          (do
            (enable! p)
            (= true (enable? p)))))
    (is (let [p (create-place "start")]
          (do
            (enable! p)
            (clear! p)
            (= false (enable? p)))))))

(deftest clear!-test
  (testing "clear! of Place"
    (is (let [p (create-place "start")]
          (do
            (enable! p)
            (clear! p)
            (= false (enable? p)))))))

(deftest enable!-test
  (testing "enable! of Place"
    (is (let [p (create-place "start")]
          (do
            (enable! p)
            (= true (enable? p)))))))
