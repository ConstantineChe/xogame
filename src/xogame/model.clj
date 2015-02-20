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
  (swap! field (fn [a] (vec (repeatedly field-size #(vec (repeatedly field-size (fn [] (tiles :empty)))))))))

(clear game-field)

(defn line? [field-state]
  (let [n (atom 0)]
    (for [x (range field-size) y (range field-size)]
      (if (and (not (= (get-in field-state [x y]) (:empty tiles)))
               (= (get-in field-state [x y])
                  (get-in field-state [(inc x) y])))
        (swap! n inc) (swap! n * 0)))))

(filter #(> % 1) (line? @game-field))
(print @game-field)
