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
