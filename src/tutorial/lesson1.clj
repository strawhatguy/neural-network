(ns tutorial.lesson1)

(require '[uncomplicate.commons.core :refer [with-release]]
         '[uncomplicate.clojurecl.core :refer [with-default-1 set-default! release-context!]]
         '[uncomplicate.neanderthal
           [native :refer [dv dge]]
           [core :refer [mv!]]
           [opencl :refer [clv clge with-default-engine set-engine!]]])

(defn -main []
  (with-release [x (dv 0.3 0.9)
                 w1 (dge 4 2 [0.3 0.6
                              0.1 2.0
                              0.9 3.7
                              0.0 1.0]
                         {:layout :row})
                 h1 (dv 4)
                 w2 (dge 1 4 [0.75 0.15 0.22 0.33])
                 y (dv 1)]
    (println (mv! w2 (mv! w1 x h1) y))))

;; doesn't work on mac?  Opencl is only 1.2, not version 2.
(defn with-gpu []
  (with-default-1
    (with-default-engine
      (with-release [x (clv 0.3 0.9)
                     w1 (clge 4 2 [0.3 0.6]
                              0.1 2.0
                              0.9 3.7
                              0.0 1.0
                              {:layout :row})
                     h1 (clv 4)
                     w2 (clge 1 4 [0.75 0.15 0.22 0.33])
                     y (clv 1)]
        (println (mv! w2 (mv! w1 x h1) y))))))

(comment
  (-main)

  ;; don't use
  (with-gpu)
  (set-default!)
  (set-engine!))
