# Quick Start Guide

## Generate a checkerboard in the REPL

```clojure
;; Start a Clojure REPL
$ clojure

;; Load the namespace
(require '[chess-variants-display.core :refer [checkerboard 
                                                checkerboard-with-pieces 
                                                standard-chess-position 
                                                render-checkerboard-html]])

;; Generate an 8x8 chess board SVG (dark top-left)
(println (checkerboard 8 8 :dark))

;; Generate a board with the standard chess starting position
(println (checkerboard-with-pieces 8 8 :dark (standard-chess-position)))

;; Place custom pieces on the board
(println (checkerboard-with-pieces 8 8 :dark {[3 3] :white-queen 
                                                [4 4] :black-king}))

;; Generate a 10x10 checkers board SVG (light top-left)
(println (checkerboard 10 10 :light))

;; Save a complete HTML page
(spit "my-board.html" (render-checkerboard-html 8 8 :dark))

;; With custom colors (brown wood theme)
(spit "wood-board.html" 
  (render-checkerboard-html 8 8 :dark "#b58863" "#f0d9b5"))
```

## Generate example files

```bash
clojure -M -m chess-variants-display.example
```

This creates:
- `example-chess.html` - Standard 8×8 chess board
- `example-checkers.html` - 10×10 checkers board  
- `example-custom.html` - 5×5 board with custom colors

## Run tests

```bash
clojure -M:test -e "(require 'chess-variants-display.core-test) (clojure.test/run-tests 'chess-variants-display.core-test)"
```

## Using in your own project

### Option 1: Git Dependency (Recommended)

Add to your `deps.edn`:

```clojure
{:deps {chess-variants-display/chess-variants-display 
        {:git/url "https://github.com/arachtivix/chess-variants-display"
         :git/sha "latest-commit-sha"}}}
```

Replace `latest-commit-sha` with the SHA from the latest [release](https://github.com/arachtivix/chess-variants-display/releases).

### Option 2: JAR Dependency

Download the JAR from [releases](https://github.com/arachtivix/chess-variants-display/releases) and add to your `deps.edn`:

```clojure
{:paths ["src" "libs/chess-variants-display-1.0.X.jar"]}
```

### Using the Library

Then in your code:

```clojure
(ns my-app.core
  (:require [chess-variants-display.core :refer [checkerboard 
                                                  checkerboard-with-pieces 
                                                  standard-chess-position]]))

(defn my-handler [request]
  {:status 200
   :headers {"Content-Type" "image/svg+xml"}
   :body (checkerboard 8 8 :dark)})

(defn chess-game-handler [request]
  {:status 200
   :headers {"Content-Type" "image/svg+xml"}
   :body (checkerboard-with-pieces 8 8 :dark (standard-chess-position))})
```
