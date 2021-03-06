(ns xogame.core
  (:use [seesaw.core :as s]
        [seesaw.font :as f]
        [xogame.model])

  (:gen-class))

(declare main-frame
         add-buttons
         make-move
         my-content
         gameover
         )

(def turn (atom :cross))

(defn my-content []
  (s/horizontal-panel :items (add-buttons field-size)))

(defn refresh-content []
  (config! main-frame :content (my-content)))

(defn add-buttons [count]
  (vec (map
        (fn [x] (s/vertical-panel
                :items
                (vec (map (fn [y] (s/button
                                   :text (:view (get-in @game-field [x y]))
                                   :size [42 :by 42]
                                   :font (f/font :size 10)
                                   :listen [:action (fn [e] (make-move x y turn))]))
                          (range count)))))
        (range count))))

(defn make-move [x y turn]
  (if (= :empty (:value (get-in @game-field [x y])))
   (do (swap! game-field update-in [x y] (fn [a] (tiles @turn)))
       (if (= @turn :cross) (swap! turn (fn [a] :zero))
           (swap! turn (fn [a] :cross)))
       (config! main-frame :content (my-content))
       (if (is-finished? [x y])
         (gameover)))))

(defn gameover []
  (->  (s/dialog
        :type :question
        :option-type :yes-no
        :content "Gave over. Start new?"
        :success-fn (fn [p] ((clear game-field)
                            (refresh-content)
                            (reset! turn :cross)
                            (.dispose (s/to-root p)))))
       pack! show!))

(defn main []
  (-> main-frame pack! show!))

(defn -main []
  (main))

(def actions {:new [:action (fn [e] (clear game-field) (refresh-content) (reset! turn :cross))]
              :exit [:action (fn [e] (System/exit 0))]})

(def main-frame (s/frame :title "XO"
                         :menubar (s/menubar :items
                                             [(s/menu :text "Game" :items
                                                      [(s/menu-item
                                                        :text "New"
                                                        :listen (actions :new))
                                                       (s/menu-item
                                                        :text "Exit"
                                                        :listen (actions :exit))])
                                              (s/menu :text "About")])
                         :on-close :exit :content (my-content)))
