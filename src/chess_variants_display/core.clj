(ns chess-variants-display.core)

(defn- html-escape
  "Escape HTML special characters."
  [s]
  (-> s
      (clojure.string/replace "&" "&amp;")
      (clojure.string/replace "<" "&lt;")
      (clojure.string/replace ">" "&gt;")
      (clojure.string/replace "\"" "&quot;")))

(defn checkerboard
  "Generate an HTML SVG checkerboard.
  
  Parameters:
  - width: number of squares wide
  - height: number of squares tall
  - top-left-color: :light or :dark, determines the color of the top-left square
  
  Returns an SVG string with CSS-responsive dark squares."
  [width height top-left-color]
  (let [square-size 50
        svg-width (* width square-size)
        svg-height (* height square-size)
        squares (for [row (range height)
                      col (range width)]
                  (let [is-even-sum (even? (+ row col))
                        is-dark (if (= top-left-color :dark)
                                 is-even-sum
                                 (not is-even-sum))
                        x (* col square-size)
                        y (* row square-size)
                        css-class (if is-dark "dark-square" "light-square")]
                    (format "<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" class=\"%s\"/>"
                            x y square-size square-size css-class)))]
    (str "<svg width=\"100%\" height=\"100%\" viewBox=\"0 0 " svg-width " " svg-height "\" xmlns=\"http://www.w3.org/2000/svg\">"
         (clojure.string/join "" squares)
         "</svg>")))

(defn render-checkerboard-html
  "Generate a complete HTML page with an embedded checkerboard SVG.
  
  Parameters:
  - width: number of squares wide
  - height: number of squares tall
  - top-left-color: :light or :dark
  - dark-color: CSS color value for dark squares (default: \"#769656\")
  - light-color: CSS color value for light squares (default: \"#eeeed2\")
  
  Returns a complete HTML document string."
  ([width height top-left-color]
   (render-checkerboard-html width height top-left-color "#769656" "#eeeed2"))
  ([width height top-left-color dark-color light-color]
   (str "<!DOCTYPE html>"
        "<html>"
        "<head>"
        "<meta charset=\"UTF-8\">"
        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
        "<title>" width "x" height " Checkerboard</title>"
        "<style>"
        ".dark-square { fill: " dark-color "; }\n"
        ".light-square { fill: " light-color "; }"
        "</style>"
        "</head>"
        "<body>"
        "<div style=\"max-width: 600px; margin: 0 auto;\">"
        (checkerboard width height top-left-color)
        "</div>"
        "</body>"
        "</html>")))

