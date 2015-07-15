(ns xogame.model
  (:require [clojure.math.combinatorics :as comb]
            [seesaw.core :as s])
)

(defrecord Tile [view value])

(def tiles
  {:empty (new Tile "-" :empty)
   :cross (new Tile "X" :cross)
   :zero  (new Tile "O" :zero)})

(def field-size 15)

(def game-field
  (atom (vec (repeatedly field-size #(vec (repeatedly field-size (fn [] (tiles :empty))))))))

(defn clear [field]
  (reset! field (vec (repeatedly field-size #(vec (repeatedly field-size (fn [] (tiles :empty))))))))

(defn check-direction [current direction counter reverse?]
  "check for line in direction"
  (let [next (map (fn [x y] (+ x y)) current direction)]
      (if (= (get-in @game-field current)
             (get-in @game-field next))
        (recur next direction (inc counter) reverse?)
        (if (not reverse?) (recur current (map #(* % -1) direction) 1 true)
            counter))))

(defn is-finished? [current]
  "check if line is done."
  (if (some #(> % 4) (map
             (fn [direction]
               (check-direction current direction 1 false))
             [[0 1] [1 0] [1 1] [-1 1]]))
    true))
