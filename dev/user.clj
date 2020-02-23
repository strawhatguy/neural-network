(require  '[clojure.java.io :as io]
          '[clojure.string :as str]
          '[clojure.pprint :refer (pprint)]
          '[clojure.repl :refer :all]
          '[clojure.test :as test])

(defn file->bytes [file]
  (with-open [xin (io/input-stream file)
              xout (java.io.ByteArrayOutputStream.)]
    (io/copy xin xout)
    (.toByteArray xout)))

(defonce mnist-train-images (file->bytes (io/resource "train-images-idx3-ubyte")))
(defonce mnist-train-labels (file->bytes (io/resource "train-labels-idx1-ubyte")))
(defonce mnist-test-images (file->bytes (io/resource "t10k-images-idx3-ubyte")))
(defonce mnist-test-labels (file->bytes (io/resource "t10k-labels-idx1-ubyte")))

