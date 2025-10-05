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

(defn- piece-unicode
  "Get Unicode character for a chess piece.
  
  Parameters:
  - piece: keyword like :white-king, :black-pawn, etc.
  
  Returns Unicode character for the piece."
  [piece]
  (case piece
    :white-king "♔"
    :white-queen "♕"
    :white-rook "♖"
    :white-bishop "♗"
    :white-knight "♘"
    :white-pawn "♙"
    :black-king "♚"
    :black-queen "♛"
    :black-rook "♜"
    :black-bishop "♝"
    :black-knight "♞"
    :black-pawn "♟"
    ""))

(defn standard-chess-position
  "Returns a map of the standard chess starting position.
  
  Uses standard chess notation where row 0 is the top (rank 8) and row 7 is bottom (rank 1).
  Returns a map of [row col] -> piece keyword."
  []
  {;; Black pieces (top of board)
   [0 0] :black-rook    [0 1] :black-knight  [0 2] :black-bishop  [0 3] :black-queen
   [0 4] :black-king    [0 5] :black-bishop  [0 6] :black-knight  [0 7] :black-rook
   [1 0] :black-pawn    [1 1] :black-pawn    [1 2] :black-pawn    [1 3] :black-pawn
   [1 4] :black-pawn    [1 5] :black-pawn    [1 6] :black-pawn    [1 7] :black-pawn
   ;; White pieces (bottom of board)
   [6 0] :white-pawn    [6 1] :white-pawn    [6 2] :white-pawn    [6 3] :white-pawn
   [6 4] :white-pawn    [6 5] :white-pawn    [6 6] :white-pawn    [6 7] :white-pawn
   [7 0] :white-rook    [7 1] :white-knight  [7 2] :white-bishop  [7 3] :white-queen
   [7 4] :white-king    [7 5] :white-bishop  [7 6] :white-knight  [7 7] :white-rook})

(defn checkerboard-with-pieces
  "Generate an HTML SVG checkerboard with chess pieces.
  
  Parameters:
  - width: number of squares wide
  - height: number of squares tall
  - top-left-color: :light or :dark, determines the color of the top-left square
  - pieces: map of positions to piece keywords, e.g. {[0 0] :white-rook, [0 1] :white-knight}
            positions are [row col] with [0 0] being top-left
  
  Returns an SVG string with CSS-responsive dark squares and pieces."
  [width height top-left-color pieces]
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
                            x y square-size square-size css-class)))
        piece-elements (for [[[row col] piece] pieces]
                        (let [x (* col square-size)
                              y (* row square-size)
                              text-x (+ x (/ square-size 2))
                              text-y (+ y (/ square-size 2))
                              unicode-char (piece-unicode piece)]
                          (format "<text x=\"%d\" y=\"%d\" class=\"chess-piece\" text-anchor=\"middle\" dominant-baseline=\"central\">%s</text>"
                                  text-x text-y unicode-char)))]
    (str "<svg width=\"100%\" height=\"100%\" viewBox=\"0 0 " svg-width " " svg-height "\" xmlns=\"http://www.w3.org/2000/svg\">"
         (clojure.string/join "" squares)
         (clojure.string/join "" piece-elements)
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

