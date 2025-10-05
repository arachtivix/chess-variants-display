(ns chess-variants-display.demo
  (:require [chess-variants-display.core :refer [checkerboard checkerboard-with-pieces standard-chess-position]]))

(defn generate-demo-html
  "Generate the demo HTML page with multiple color themes."
  []
  (let [board-svg (checkerboard 8 8 :dark)
        board-with-pieces (checkerboard-with-pieces 8 8 :dark (standard-chess-position))
        custom-pieces {[3 3] :white-queen [3 4] :black-king 
                       [4 3] :black-knight [4 4] :white-bishop}
        board-custom (checkerboard-with-pieces 8 8 :dark custom-pieces)
        
        ;; Different board sizes
        small-board (checkerboard-with-pieces 5 5 :dark 
                      {[2 2] :white-king [1 1] :black-queen [3 3] :white-rook})
        large-board (checkerboard-with-pieces 10 10 :dark
                      {[0 0] :black-rook [0 9] :black-rook 
                       [9 0] :white-rook [9 9] :white-rook
                       [4 4] :white-queen [5 5] :black-king
                       [4 5] :white-king [5 4] :black-queen})
        rectangular-board (checkerboard-with-pieces 12 8 :dark
                            {[0 5] :black-king [0 6] :black-queen
                             [7 5] :white-king [7 6] :white-queen
                             [3 0] :black-rook [3 11] :black-rook
                             [4 0] :white-rook [4 11] :white-rook})
        
        ;; Different square sizes
        tiny-board (checkerboard-with-pieces 8 8 :dark 
                     {[0 4] :black-king [7 4] :white-king
                      [3 3] :white-queen [4 4] :black-queen} 30)
        large-squares (checkerboard-with-pieces 6 6 :dark
                        {[0 0] :black-rook [0 5] :black-knight
                         [5 0] :white-knight [5 5] :white-rook
                         [2 2] :white-bishop [3 3] :black-bishop} 70)
        
        ;; Boards with custom piece colors
        sample-pieces {[1 1] :white-queen [1 6] :black-king 
                       [6 1] :black-knight [6 6] :white-rook}
        red-pieces-board (checkerboard-with-pieces 8 8 :dark sample-pieces)
        blue-pieces-board (checkerboard-with-pieces 8 8 :dark sample-pieces)
        
        ;; Same pieces, different top-left colors
        demo-pieces {[2 2] :white-king [2 5] :black-queen
                     [5 2] :black-rook [5 5] :white-bishop}
        board-dark-topleft (checkerboard-with-pieces 8 8 :dark demo-pieces)
        board-light-topleft (checkerboard-with-pieces 8 8 :light demo-pieces)]
    (str "<!DOCTYPE html>\n"
         "<html>\n"
         "<head>\n"
         "    <meta charset=\"UTF-8\">\n"
         "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
         "    <title>CSS-Responsive Checkerboard Demo</title>\n"
         "    <style>\n"
         "        body {\n"
         "            font-family: Arial, sans-serif;\n"
         "            max-width: 800px;\n"
         "            margin: 20px auto;\n"
         "            padding: 20px;\n"
         "        }\n"
         "        \n"
         "        .board-container {\n"
         "            margin: 20px 0;\n"
         "            padding: 20px;\n"
         "            border: 1px solid #ccc;\n"
         "            border-radius: 8px;\n"
         "        }\n"
         "        \n"
         "        h2 {\n"
         "            margin-top: 0;\n"
         "        }\n"
         "        \n"
         "        /* Default chess colors */\n"
         "        .chess .dark-square { fill: #769656; }\n"
         "        .chess .light-square { fill: #eeeed2; }\n"
         "        \n"
         "        /* Red/White theme */\n"
         "        .red-white .dark-square { fill: #d32f2f; }\n"
         "        .red-white .light-square { fill: #ffffff; }\n"
         "        \n"
         "        /* Blue/Gold theme */\n"
         "        .blue-gold .dark-square { fill: #1565c0; }\n"
         "        .blue-gold .light-square { fill: #ffd54f; }\n"
         "        \n"
         "        /* Dark mode */\n"
         "        .dark-mode .dark-square { fill: #2c2c2c; }\n"
         "        .dark-mode .light-square { fill: #5c5c5c; }\n"
         "        \n"
         "        /* Brown wood theme */\n"
         "        .wood .dark-square { fill: #b58863; }\n"
         "        .wood .light-square { fill: #f0d9b5; }\n"
         "        \n"
         "        /* Chess piece styling */\n"
         "        .chess-piece {\n"
         "            font-size: 40px;\n"
         "            fill: #000;\n"
         "            pointer-events: none;\n"
         "        }\n"
         "        \n"
         "        /* Colored piece themes */\n"
         "        .red-pieces .chess-piece { fill: #c62828; }\n"
         "        .blue-pieces .chess-piece { fill: #1565c0; }\n"
         "        .purple-pieces .chess-piece { fill: #6a1b9a; }\n"
         "        .gold-pieces .chess-piece { fill: #f9a825; }\n"
         "        .white-pieces .chess-piece { fill: #ffffff; stroke: #000; stroke-width: 1; }\n"
         "        \n"
         "        /* Responsive container widths */\n"
         "        .small-board { max-width: 300px; }\n"
         "        .medium-board { max-width: 500px; }\n"
         "        .large-board { max-width: 700px; }\n"
         "    </style>\n"
         "</head>\n"
         "<body>\n"
         "    <h1>CSS-Responsive Checkerboard Demonstration</h1>\n"
         "    <p>This demonstrates how the same SVG checkerboard can be styled differently using CSS classes.</p>\n"
         "    \n"
         "    <div class=\"board-container chess\">\n"
         "        <h2>Standard Chess Starting Position</h2>\n"
         "        " board-with-pieces "\n"
         "    </div>\n"
         "    \n"
         "    <div class=\"board-container wood\">\n"
         "        <h2>Custom Piece Placement</h2>\n"
         "        <p>Place any pieces at any position on the board.</p>\n"
         "        " board-custom "\n"
         "    </div>\n"
         "    \n"
         "    <h2>Different Board Sizes</h2>\n"
         "    \n"
         "    <div class=\"board-container chess small-board\">\n"
         "        <h3>5×5 Mini Board</h3>\n"
         "        <p>A compact board perfect for quick games or puzzles.</p>\n"
         "        " small-board "\n"
         "    </div>\n"
         "    \n"
         "    <div class=\"board-container blue-gold large-board\">\n"
         "        <h3>10×10 Large Board</h3>\n"
         "        <p>Extended board with rooks in corners and royalty in center.</p>\n"
         "        " large-board "\n"
         "    </div>\n"
         "    \n"
         "    <div class=\"board-container red-white\">\n"
         "        <h3>12×8 Rectangular Board</h3>\n"
         "        <p>Wide board variant with pieces spread across the width.</p>\n"
         "        " rectangular-board "\n"
         "    </div>\n"
         "    \n"
         "    <h2>Different Square Dimensions</h2>\n"
         "    \n"
         "    <div class=\"board-container dark-mode small-board\">\n"
         "        <h3>Tiny Squares (30px)</h3>\n"
         "        <p>Compact display with smaller square dimensions.</p>\n"
         "        " tiny-board "\n"
         "    </div>\n"
         "    \n"
         "    <div class=\"board-container wood large-board\">\n"
         "        <h3>Large Squares (70px)</h3>\n"
         "        <p>Spacious board with larger square dimensions for better visibility.</p>\n"
         "        " large-squares "\n"
         "    </div>\n"
         "    \n"
         "    <h2>Empty Boards with Different Themes</h2>\n"
         "    \n"
         "    <div class=\"board-container chess\">\n"
         "        <h3>Standard Chess Colors</h3>\n"
         "        " board-svg "\n"
         "    </div>\n"
         "    \n"
         "    <div class=\"board-container red-white\">\n"
         "        <h3>Red & White Theme</h3>\n"
         "        " board-svg "\n"
         "    </div>\n"
         "    \n"
         "    <div class=\"board-container blue-gold\">\n"
         "        <h3>Blue & Gold Theme</h3>\n"
         "        " board-svg "\n"
         "    </div>\n"
         "    \n"
         "    <div class=\"board-container dark-mode\">\n"
         "        <h3>Dark Mode Theme</h3>\n"
         "        " board-svg "\n"
         "    </div>\n"
         "    \n"
         "    <div class=\"board-container wood\">\n"
         "        <h3>Brown Wood Theme</h3>\n"
         "        " board-svg "\n"
         "    </div>\n"
         "    \n"
         "    <h2>Custom Piece Colors</h2>\n"
         "    <p>Chess pieces can be styled with different colors using CSS classes.</p>\n"
         "    \n"
         "    <div class=\"board-container chess red-pieces\">\n"
         "        <h3>Red Pieces</h3>\n"
         "        <p>Same pieces as below, but colored red using CSS.</p>\n"
         "        " red-pieces-board "\n"
         "    </div>\n"
         "    \n"
         "    <div class=\"board-container wood blue-pieces\">\n"
         "        <h3>Blue Pieces</h3>\n"
         "        <p>Same pieces as above, but colored blue using CSS.</p>\n"
         "        " blue-pieces-board "\n"
         "    </div>\n"
         "    \n"
         "    <h2>Top-Left Color Parameter Demonstration</h2>\n"
         "    <p>The <code>top-left-color</code> parameter controls whether the top-left square is dark or light.</p>\n"
         "    <p>Below are two boards with the same pieces but different top-left square colors:</p>\n"
         "    \n"
         "    <div class=\"board-container chess\">\n"
         "        <h3>Top-Left: DARK (top-left-color = :dark)</h3>\n"
         "        <p>The top-left square is a dark square.</p>\n"
         "        " board-dark-topleft "\n"
         "    </div>\n"
         "    \n"
         "    <div class=\"board-container chess\">\n"
         "        <h3>Top-Left: LIGHT (top-left-color = :light)</h3>\n"
         "        <p>The top-left square is a light square. Notice the checkerboard pattern is inverted.</p>\n"
         "        " board-light-topleft "\n"
         "    </div>\n"
         "</body>\n"
         "</html>")))

(defn -main
  "Generate the demo HTML file in the docs folder"
  [& args]
  (let [output-dir "docs"
        output-file (str output-dir "/index.html")]
    ;; Create docs directory if it doesn't exist
    (.mkdir (java.io.File. output-dir))
    ;; Generate and write the demo HTML
    (spit output-file (generate-demo-html))
    (println (str "Generated demo HTML: " output-file))))
