(ns xogame.controller
  (:use [xogame.model :only [game-field tiles]]
        [xogame.core :only [refresh-content]]))

;; (defn- refresh-content []
;;   (resolve 'xogame.core/refresh-content))

;; (defn- my-content []
;;   (resolve 'xogame.core/my-content))

(defn make-move [x y turn]
  (do (swap! game-field update-in [x y] (fn [a] (tiles @turn)))
      (if (= @turn :cross) (reset! turn :zero)
          (reset! turn :cross))
      (refresh-content)))
