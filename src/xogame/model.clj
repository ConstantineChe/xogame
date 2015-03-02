(ns xogame.model)

(def field-size 15)

(defrecord Tile [view value])

(def tiles
  {:empty (new Tile "-" :empty)
   :cross (new Tile "X" :cross)
   :zero  (new Tile "O" :zero)})

(def game-field
  (atom (vec (repeatedly field-size #(vec (repeatedly field-size (fn [] (tiles :empty))))))))

(defn clear [field]
  (reset! field (vec (repeatedly field-size #(vec (repeatedly field-size (fn [] (tiles :empty))))))))

(clear game-field)

(defn line? [field-state]
  (let [n (atom 0)]
    (for [x (range field-size) y (range field-size)]
      (if (and (not (= (get-in field-state [x y]) (:empty tiles)))
               (= (get-in field-state [x y])
                  (get-in field-state [x (inc y)])))
        (swap! n inc) (swap! n * 0)))))

(filter #(> % 0) (line? @game-field))

(for [x (range field-size) y (range field-size)]
  (if (< y 14) (print [x y]) (println  [x y])))
