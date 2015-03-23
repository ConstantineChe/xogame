(ns xogame.core
  (:use [seesaw.core :as s]
        [xogame.model :only [game-field field-size tiles]])
  (:require [clojure.math.combinatorics :as comb]))

(declare main-frame
         add-buttons
         make-move
         is-finished?)

(defn main []
  (-> main-frame pack! show!))

(defn -main []
  (in-ns 'xogame.core)
  (main))



(defn my-content []
  (s/horizontal-panel :items (add-buttons field-size)))


(def turn (atom :cross))



(defn refresh-content []
  (config! main-frame :content (my-content)))

(defn add-buttons [count]
  (vec (map
        (fn [x] (s/vertical-panel
                :items
                (vec (map (fn [y] (s/button
                                   :text (:view (get-in @game-field [x y]))
                                   :size [45 :by 45]
                                   :listen [:action (fn [e] (make-move x y turn))]))
                          (range count)))))
        (range count))))

(defn make-move [x y turn]
  (if (= :empty (:value (get-in @game-field [x y])))
   (do (swap! game-field update-in [x y] (fn [a] (tiles @turn)))
       (if (= @turn :cross) (swap! turn (fn [a] :zero))
           (swap! turn (fn [a] :cross)))
       (config! main-frame :content (my-content))
       (is-finished? [x y]))))

(def main-frame (s/frame :title "XO", :on-close :hide, :content (my-content)))

(defn check-direction [current direction counter reverse?]
  "check for line in direction"
  (let [next (map (fn [x y] (+ x y)) current direction)]
      (if (= (get-in @game-field current)
             (get-in @game-field next))
        (recur next direction (inc counter) false)
        (if (not reverse?) (recur current (map #(* % -1) direction) 1 true)
            counter))))

(defn is-finished? [current]
  "check if line is done."
  (if (some #(> % 4) (map
             (fn [direction]
               (check-direction current direction 1 false))
             (filter #(not (= [0 0] %)) (map vec (comb/cartesian-product [1 0] [0 1])))))
    (s/alert "done")))
