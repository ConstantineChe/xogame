(ns xogame.model)

(def field-size 15)

(defrecord Tile [view value])

(def tiles
  {:empty (new Tile "-" :empty)
   :cross (new Tile "X" :cross)
   :zero  (new Tile "O" :zero)})

(def game-field
  (atom (vec (repeatedly field-size #(vec (repeatedly field-size (fn [] (tiles :empty))))))))
