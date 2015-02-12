(ns datamining.core)

(defn edges
  "Gets the edges in a graph"
  [graph]
  (mapcat (fn [[start ends]] (map #(vec [start %]) ends)) graph))

(defn- power-iteration
  "Runs power iteration on a graph"
  [graph epsilon]
  (loop [prior graph]
    )
  )

(defn page-rank
  "runs pagerank against a graph"
  [graph]
  )
