(ns tutorial.core
  (:require [uncomplicate.commons.core :refer [with-release]]
            [uncomplicate.fluokitten.core :refer [fmap!]]
            [uncomplicate.clojurecl
             [core :refer [with-platform platforms with-context context with-queue
                           sort-by-cl-version devices with-default-1 command-queue-1
                           set-default-1! release-context!]]]
            [uncomplicate.neanderthal
             [core :refer [asum dot mm mv! mv axpy! scal!]]
             [native :refer [dv dge]]
             [math :refer [signum exp]]
             [opencl :refer [clv clge with-default-engine set-engine!]]
             [vect-math :refer [fmax! tanh! linear-frac!]]]))



(defn step! [threshold x]
  (fmap! signum (axpy! -1.0 threshold (fmax! threshold x x))))

(defn relu! [threshold x]
  (axpy! -1.0 threshold (fmax! threshold x x)))

(defn sigmoid! [x]
  (linear-frac! 0.5 (tanh! (scal! 0.5 x)) 0.5))

(defn lesson-2 []
  (with-release [x (dv 0.3 0.9)
                 w1 (dge 4 2 [0.3 0.6
                              0.1 2.0
                              0.9 3.7
                              0.0 1.0]
                         {:layout :row})
                 bias1 (dv 0.7 0.2 1.1 2)
                 h1 (dv 4)
                 w2 (dge 1 4 [0.75 0.15 0.22 0.33])
                 bias2 (dv 0.3)
                 y (dv 1)]
    (tanh! (axpy! -1.0 bias1 (mv! w1 x h1)))
    (println (sigmoid! (axpy! -1.0 bias2 (mv! w2 h1 y))))))

(defn test-setup []
  (with-default-1
    (with-default-engine
      (with-release [x (clv 1 -2 5)
                     y (clv 10 20 30)
                     a (clge 3 2 [1 2 3 4 5 6])
                     b (clge 2 3 [10 20 30 40 50 60])]
        (dot x y)
        (mm a b)))))

(defn -main []
  (with-default-1
    (with-default-engine
      (with-release [gpu-x (clv 1 -2 5)]
        (println (asum gpu-x))))))
