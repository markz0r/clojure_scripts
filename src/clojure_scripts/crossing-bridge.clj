(ns clojure-scripts.crossing-bridge)

(def objective 18)
(defonce group [1,2,5,10])

(defn permutations [s]
  (lazy-seq (if (seq (rest s)) (apply concat (for [x s]
                     (map #(cons x %) (permutations (remove #{x} s))))) [s])))


(defn cross-options [onside]  (permutations onside))

(defn expand-node [node]
  (map #(vector (%1, (second node), (last node))) (permutations (first node))))

(def first-node [group, [], 0])
(expand-node first-node)

(defn cross-there [r-group, l-group, total]
  (map #(if ( > 17 (last %1)) %1)
         (set (map #(vector (take-last 2 %1),
                            (concat l-group (take 2 %1)),
                            (apply max (take 2 %1))) (cross-options r-group)))))

(defn cross-back [invec]
  (defn remove-min [vecr] (remove #(=(apply min vecr)%1) vecr))
  (map #(vector (concat (apply min (second %1)) (first %1)),
                (remove-min (second %1)),
                (+ (last %1)(apply min (second %1)))) invec))

(def cross1 (cross-there group,[],0))
(def cross2 (cross-back cross1))
