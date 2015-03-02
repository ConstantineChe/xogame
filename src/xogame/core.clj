(ns xogame.core
  (:use [seesaw.core :as s]
        [xogame.model :only [game-field field-size tiles]]))

(defn -main []
  (print *ns*)
  (in-ns 'xogame.core)
  (print *ns*))

(defn main []
  (-> main-frame pack! show!))

(defn my-content []
  (s/horizontal-panel :items (add-buttons field-size)))

(def main-frame (s/frame :title "XO", :on-close :hide, :content (my-content)))

(def turn (atom :cross))



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
       (config! main-frame :content (my-content)))))
