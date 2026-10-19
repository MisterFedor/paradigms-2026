(defn vect_op [operation]
  (fn [& vects]
    (apply mapv operation vects)))

(def v+ (vect_op +))
(def v- (vect_op -))
(def v* (vect_op *))
(def vd (vect_op /))

(defn scalar [a b]
  (reduce + (v* a b)))

(defn vect [a b]
  (vector
   (- (* (a 1) (b 2)) (* (a 2) (b 1)))
   (- (* (a 2) (b 0)) (* (a 0) (b 2)))
   (- (* (a 0) (b 1)) (* (a 1) (b 0)))))

(defn v*s [vect sc]
  (mapv (fn [x] (* x sc))
        vect))

(def m+ (vect_op v+))
(def m- (vect_op v-))
(def m* (vect_op v*))
(def md (vect_op vd))

(defn m*s [matrix sc]
  (mapv
   (fn [row]
     (v*s row sc)) matrix))

(defn m*v [matrix vec]
  (mapv #(scalar %1 vec) matrix))

(defn transpose [matrix]
  (apply mapv vector matrix))

(defn m*m [a b]
  (let [transposed (transpose b)]
    (mapv
     (fn [row]
       (mapv
        (fn [col]
          (scalar row col)) transposed)) a)))

(defn shapeless_op [operation]
  (letfn [(rec [& operands]
            (if (vector? (first operands))
              (apply mapv rec operands)
              (apply operation operands)))]
    rec))

(def s+ (shapeless_op +))
(def s- (shapeless_op -))
(def s* (shapeless_op *))
(def sd (shapeless_op /))
