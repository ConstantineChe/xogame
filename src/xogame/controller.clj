(ns xogame.controller
  (:use [xogame.model :only [game-field tiles]]))

(defn make-move [x y turn]
  (do (swap! game-field update-in [x y] (fn [a] (tiles @turn)))
      (if (= @turn :cross) (swap! turn (fn [a] :zero))
          (swap! turn (fn [a] :cross)))))
