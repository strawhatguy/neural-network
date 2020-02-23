(ns tutorial.lesson2)

(require '[uncomplicate.commons.core :refer [with-release]]
         '[uncomplicate.fluokitten.core :refer [fmap!]]
         '[uncomplicate.neanderthal
           [native :refer [dv dge]]
           [core :refer [mv! mv axpy! scal!]]
           [math :refer [signum exp]]
           [vect-math :refer [fmax! tanh! linear-frac!]]])

(defn step! [threshold x]
  (fmap! signum (axpy! -1.0 threshold (fmax! threshold x x))))

(defn relu! [threshold x]
  (axpy! -1.0 threshold (fmax! threshold x x)))

(defn sigmoid! [x]
  (linear-frac! 0.5 (tanh! (scal! 0.5 x)) 0.5))

(defn -main []
  (with-release [x (dv 0.3 0.9)
                 w1 (dge 4 2 [0.3 0.6
                              0.1 2.0
                              0.9 3.7
                              0.0 1.0]
                         {:layout :row})
                 threshold (dv 0.7 0.2 1.1 2)
                 bias (dv 0.7 0.2 1.1 2)
                 zero (dv 4)]
    ;;(println (mv w1 x))
    ;;(println (tanh! (axpy! -1.0 bias (mv w1 x))))
    ;;(println (relu! bias (mv w1 x)))
    (println (sigmoid! (axpy! -1.0 bias (mv w1 x))))
    (comment (step! zero (axpy! -1.0 bias (mv w1 x))))))

(defn final []
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


(comment
  (-main)
  (final)
    ;; don't use
  (set-default!)
  (set-engine!))
