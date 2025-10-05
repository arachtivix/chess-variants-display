(ns chess-variants-display.core-test
  (:require [clojure.test :refer :all]
            [chess-variants-display.core :refer :all]))

(deftest test-checkerboard-basic
  (testing "8x8 checkerboard with dark top-left square"
    (let [svg (checkerboard 8 8 :dark)]
      (is (string? svg))
      (is (re-find #"<svg" svg))
      (is (re-find #"viewBox=\"0 0 400 400\"" svg))
      (is (re-find #"dark-square" svg))
      (is (re-find #"light-square" svg)))))

(deftest test-checkerboard-dimensions
  (testing "5x3 checkerboard"
    (let [svg (checkerboard 5 3 :light)]
      (is (re-find #"viewBox=\"0 0 250 150\"" svg))))
  (testing "10x10 checkerboard"
    (let [svg (checkerboard 10 10 :dark)]
      (is (re-find #"viewBox=\"0 0 500 500\"" svg)))))

(deftest test-top-left-color
  (testing "dark top-left square"
    (let [svg (checkerboard 2 2 :dark)]
      ;; Top-left (0,0) should be dark
      (is (re-find #"<rect[^>]*x=\"0\"[^>]*y=\"0\"[^>]*class=\"dark-square\"" svg))))
  (testing "light top-left square"
    (let [svg (checkerboard 2 2 :light)]
      ;; Top-left (0,0) should be light
      (is (re-find #"<rect[^>]*x=\"0\"[^>]*y=\"0\"[^>]*class=\"light-square\"" svg)))))

(deftest test-render-complete-html
  (testing "complete HTML page generation"
    (let [html (render-checkerboard-html 8 8 :dark)]
      (is (re-find #"<html>" html))
      (is (re-find #"<svg" html))
      (is (re-find #"\.dark-square" html))
      (is (re-find #"\.light-square" html)))))

(deftest test-custom-colors
  (testing "custom colors in HTML"
    (let [html (render-checkerboard-html 8 8 :dark "#ff0000" "#00ff00")]
      (is (re-find #"#ff0000" html))
      (is (re-find #"#00ff00" html)))))

(deftest test-checkerboard-with-pieces
  (testing "board with pieces has chess-piece text elements"
    (let [pieces {[0 0] :white-king [7 7] :black-queen}
          svg (checkerboard-with-pieces 8 8 :dark pieces)]
      (is (string? svg))
      (is (re-find #"<svg" svg))
      (is (re-find #"chess-piece" svg))
      (is (re-find #"♔" svg))  ; white king
      (is (re-find #"♛" svg)))) ; black queen
  (testing "empty piece map creates board without pieces"
    (let [svg (checkerboard-with-pieces 8 8 :dark {})]
      (is (re-find #"<svg" svg))
      (is (not (re-find #"chess-piece" svg))))))

(deftest test-standard-chess-position
  (testing "standard chess position has all pieces"
    (let [pos (standard-chess-position)]
      (is (= 32 (count pos)))  ; 32 pieces on the board
      (is (= :white-king (get pos [7 4])))
      (is (= :black-king (get pos [0 4])))
      (is (= :white-rook (get pos [7 0])))
      (is (= :black-rook (get pos [0 0]))))))
