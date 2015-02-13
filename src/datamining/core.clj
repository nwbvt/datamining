(ns datamining.core)

(defn edges
  "Gets the edges in a graph"
  [graph]
  (mapcat (fn [[start ends]] (map #(vec [start %]) ends)) graph))

(defn rev-graph
  "Reverses the graph"
  [graph]
  (let [edge-list (edges graph)]
    (loop [rev-graph {} left edge-list]
      (if (empty? left) rev-graph
        (let [[in out] (first left)
              in-list (if (contains? rev-graph out)
                        (conj (rev-graph out) in) [in])]
          (recur (assoc rev-graph out in-list) (rest left)))))))

(defn map-dist
  "'distance' between two maps
   assumes same keyset"
  [map1 map2]
  (apply + (map #(Math/abs (- (double (map1 %)) (double (map2 %))))
                (keys map1))))

(defn- power-iteration
  "Runs power iteration on a graph"
  [graph epsilon]
  (let [rev (rev-graph graph)
        nodes (keys graph)]
    (loop [prior (zipmap nodes (repeat (/ 1 (count nodes))))]
      (let [scores (zipmap nodes
                           (for [node nodes]
                             (apply + (map #(/ (prior %) (count (graph %))) (rev node)))))]
        (if (< epsilon (map-dist prior scores))
          (recur scores)
          scores))))) 

(defn page-rank
  "runs pagerank against a graph"
  [graph]
  (power-iteration graph 0.01))
