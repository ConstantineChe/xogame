(ns xogame.core
  (:use [seesaw.core :as s]
        [xogame.model :only [game-field field-size]]
        [xogame.controller :only [make-move]]))

(def main-frame (s/frame :title "XO", :on-close :hide, :content (my-content)))

(def turn (atom :cross))

(-> main-frame pack! show!)

(defn add-buttons [count]
  (vec (map
        (fn [x] (s/vertical-panel
                :items
                (vec (map (fn [y] (s/button
                                   :text (:view (get-in @game-field [x y]))
                                   :size [45 :by 45]
                                   :listen [:action (fn [e] (do (make-move x y turn)
                                                               (config! main-frame :content (my-content))))]))
                          (range count)))))
        (range count))))

(defn my-content []
  (s/horizontal-panel :items (add-buttons field-size)))
