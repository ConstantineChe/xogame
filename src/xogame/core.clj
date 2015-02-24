(ns xogame.core
  (:use [seesaw.core :as s]
        [xogame.model :only [game-field field-size]])
  (:require [xogame.controller :as controller]))

(def main-frame (s/frame :title "XO", :on-close :hide, :content (my-content)))

(def turn (atom :cross))

(-> main-frame pack! show!)

(defn refresh-content []
  (config! main-frame :content (my-content)))

(defn add-buttons [count]
  (vec (map
        (fn [x] (s/vertical-panel
                :items
                (vec (map (fn [y] (s/button
                                   :text (:view (get-in @game-field [x y]))
                                   :size [45 :by 45]
                                   :listen [:action (fn [e] (controller/make-move x y turn))]))
                          (range count)))))
        (range count))))

(defn my-content []
  (s/horizontal-panel :items (add-buttons field-size)))
