(ns chess-variants-display.example
  (:require [chess-variants-display.core :refer [render-checkerboard-html]]))

(defn -main
  "Generate example checkerboard HTML files"
  [& args]
  ;; Standard 8x8 chess board with dark top-left square
  (spit "example-chess.html"
        (render-checkerboard-html 8 8 :dark))
  
  ;; 10x10 checkerboard with light top-left square (checkers style)
  (spit "example-checkers.html"
        (render-checkerboard-html 10 10 :light))
  
  ;; Custom size 5x5 with custom colors
  (spit "example-custom.html"
        (render-checkerboard-html 5 5 :dark "#b58863" "#f0d9b5"))
  
  (println "Generated example HTML files:")
  (println "- example-chess.html (8x8 chess board)")
  (println "- example-checkers.html (10x10 checkers board)")
  (println "- example-custom.html (5x5 custom colors)"))
