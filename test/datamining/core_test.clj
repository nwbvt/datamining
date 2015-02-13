(ns datamining.core-test
  (:require [clojure.test :refer :all]
            [datamining.core :refer :all]))

(defn near
  "Test if something is within a particular distance of another value"
  ([val1 val2]
   (near val1 val2 0.01))
  ([val1 val2 epsilon]
   (> epsilon (Math/abs (- (double val1) (double val2))))))

(deftest pagerank-test
  (testing "Basic page rank"
    (let [g {:y [:y :a] :a [:y :m] :m [:a]}
          results (page-rank g :beta 1)]
      (is (near 1 (apply + (vals (page-rank g)))) 0.00001)
      (is (near 2/5 (results :y)))
      (is (near 2/5 (results :a)))
      (is (near 1/5 (results :m)))))
  (testing "Page Rank with spider traps"
    (let [g {:a [:b] :b [:a] :c [:a]}]
      (is (near 1 (apply + (vals (page-rank g)))) 0.00001)))
  #_(testing "Page Rank with dead end"
    (let [g {:a [:b] :b []}]
      (is (near 1 (apply + (vals (page-rank g)))) 0.00001))))
