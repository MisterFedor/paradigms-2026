(defn make-operation [operation]
  (fn [& args]
    (fn [operands]
      (apply operation (map #(% operands) args)))))

(defn constant [cnst]
  (fn [_]  cnst))

(defn variable [var]
  #(get % var))

(defn mod2 [x]
  (let [k (Math/floor (/ (+ x 1.0) 2.0))]
    (- x (* 2.0 k))))

(def arcSin (make-operation #(Math/asin (double (mod2 %)))))
(def arcCos (make-operation #(Math/acos (double (mod2 %)))))
(def arcTan12 (make-operation #(Math/atan2 (double %1) (double %2))))
(def add (make-operation +))
(def subtract (make-operation -))
(def multiply (make-operation *))
(def negate (make-operation -))

(def divide
  (fn [& args]
    (fn [operands]
      (reduce #(/ (double %1) (double %2))
              (map #(% operands) args)))))

(def operations
  {"+" add
   "-" subtract
   "*" multiply
   "/" divide
   "negate" negate
   "asin" arcSin
   "acos" arcCos
   "atan12" arcTan12})

;; (defn parse-expression [expr]
;;   (cond
;;     (number? expr) (constant expr) (symbol? expr) (variable (name expr))
;;     :else (apply (get operations (name (first expr)))
;;                  (map parse-expression (rest expr)))))
;; 
;; (defn parseFunction [input_expr]
;;   (parse-expression (read-string input_expr)))

(defn proto-get
  "Returns object property respecting the prototype chain"
  ([this key] (proto-get this key nil))
  ([this key default]
   (cond
     (contains? this key) (this key)
     (contains? this :prototype) (proto-get (this :prototype) key default)
     :else default)))

(defn proto-call
  "Calls object method respecting the prototype chain"
  [this key & args]
  (apply (proto-get this key) this args))

(defn field
  "Creates field"
  [key] (fn
          ([this] (proto-get this key))
          ([this def] (proto-get this key def))))

(defn method
  "Creates method"
  [key] (fn [this & args] (apply proto-call this key args)))

(defn constructor
  "Defines constructor"
  [ctor prototype]
  (fn [& args] (apply ctor {:prototype prototype} args)))

(def evaluate (method :evaluate))

(def toString (method :toString))

(def value (field :value))

(def operands (field :operands))

(def operation (field :operation))

(def operation-sign (field :operation-sign))

(def variable-name (field :variable-name))


(def Constant
  (constructor
   (fn [this value]
     (assoc this :value value))
   {:evaluate (fn [this args]
                (value this))
    :toString (fn [this]
                (str (value this)))}))
(def Variable
  (constructor
   (fn [this var-name]
     (assoc this :variable-name var-name))
   {:evaluate (fn [this args]
                (get args (variable-name this)))
    :toString (fn [this]
                (str (variable-name this)))}))

(def operation-proto
  {:evaluate
   (fn [this args]
     (apply (operation this)
            (map #(evaluate % args) (operands this))))
   :toString
   (fn [this]
     (str "("
          (operation-sign this)
          (apply str
                 (map #(str " " (toString %))
                      (operands this)))
          ")"))})


(defn make-operation-proto [operation operation-sign]
  (constructor
   (fn [this & args]
     (assoc this :operands args))
   {:prototype operation-proto
    :operation operation
    :operation-sign operation-sign}))

(def Add (make-operation-proto + "+"))
(def Subtract (make-operation-proto - "-"))
(def Multiply (make-operation-proto * "*"))
(def Divide
  (make-operation-proto
   (fn [& args]
     (reduce (fn [fst scnd]
               (/ (double fst) (double scnd)))
             args)) "/"))
(def Negate (make-operation-proto - "negate"))

(defn sum-exp-op [& args]
  (reduce + (map #(Math/exp %) args)))

(def SumExp (make-operation-proto sum-exp-op "sumExp"))
(def Lse (make-operation-proto
          (fn [& args]
            (Math/log (apply sum-exp-op args))) "lse"))

(defn base-parser [expr vr cnst operations]
  (cond
    (number? expr) (cnst expr) (symbol? expr) (vr (name expr))
    :else (apply (get operations (name (first expr)))
                 (map (fn [arg]
                        (base-parser arg vr cnst operations))
                      (rest expr)))))
(def operations-object
  {"+" Add
   "-" Subtract
   "*" Multiply
   "/" Divide
   "negate" Negate
   "sumExp" SumExp
   "lse" Lse})

(defn parseObject [input-expr]
  (base-parser
   (read-string input-expr) Variable Constant operations-object))

(defn parseFunction [input-expr]
  (base-parser
   (read-string input-expr) variable constant operations))