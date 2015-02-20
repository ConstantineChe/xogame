(ns xogame.controller
  (:use [xogame.model :only [game-field tiles]]
        [xogame.core :only [main-frame my-content]]
        [seesaw.core]))

(defn make-move [x y turn]
  (swap! game-field update-in [x y] (fn [a] (tiles turn)))
  (if (= @turn :cross) (swap! turn (fn [a] :zero))
      (swap! turn (fn [a] :cross)))
  (config! main-frame :content (my-content))
    (alert x "tes"))
