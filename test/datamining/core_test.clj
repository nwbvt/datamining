(ns datamining.core-test
  (:require [clojure.test :refer :all]
            [datamining.core :refer :all]))

(deftest pagerank-test
  (testing "Page rank"
    (let [g {:y [:y :a] :a [:y :m] :m [:a]}
          results (page-rank g)]
      (is (= 2/5 (results :y)))
      (is (= 2/5 (results :a)))
      (is (= 1/5 (results :m))))))
