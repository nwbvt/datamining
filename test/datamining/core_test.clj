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
          results (page-rank g)]
      (is (near 2/5 (results :y)))
      (is (near 2/5 (results :a)))
      (is (near 1/5 (results :m)))))
  #_(testing "Page Rank with spider traps"
    (let [g {:a [:b] :b [:a] :c [:a]}]
      false ;add a test here once it won't go in infinite loop
      ))
  #_(testing "Page Rank with dead end"
    (let [g {:a [:b] :b []}]
      (is (= 1 (apply + (vals (page-rank g))) )))))
