(ns datamining.core)

(defn- edges
  "Gets the edges in a graph"
  [graph]
  (mapcat (fn [[start ends]] (map #(vec [start %]) ends)) graph))

(defn- rev-graph
  "Reverses the graph"
  [graph]
  (let [edge-list (edges graph)]
    (loop [rev-graph {} left edge-list]
      (if (empty? left) rev-graph
        (let [[in out] (first left)
              in-list (if (contains? rev-graph out)
                        (conj (rev-graph out) in) [in])]
          (recur (assoc rev-graph out in-list) (rest left)))))))

(defn- map-dist
  "'distance' between two maps
   assumes same keyset"
  [map1 map2]
  (apply + (map #(Math/abs (- (double (map1 %)) (double (map2 %))))
                (keys map1))))

(defn- power-iteration
  "Runs power iteration on a graph"
  [graph epsilon beta]
  (let [rev (rev-graph graph)
        nodes (keys graph)
        dead-ends (filter #(empty? (graph %)) (keys graph))]
    (loop [prior (zipmap nodes (repeat (/ 1 (count nodes))))]
      (let [scores (zipmap nodes
                           (for [node nodes]
                             (+ (* beta (apply + (map #(/ (prior %) (count (graph %))) (rev node))))
                                (* beta (apply + (map #(/ (prior %) (count (keys graph))) dead-ends)))
                                (* (- 1 beta) (/ 1 (count nodes))))))]
        (if (< epsilon (map-dist prior scores))
          (recur scores)
          scores))))) 

(defn page-rank
  "runs pagerank against a graph"
  [graph & {:keys [epsilon beta] :or {epsilon 0.01 beta 17/20}}]
  (power-iteration graph epsilon beta))
