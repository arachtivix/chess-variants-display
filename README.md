# chess-variants-display

A Clojure library for rendering checkerboards as responsive HTML SVG elements. The checkerboards can be customized with different dimensions and colors, with CSS-responsive styling.

## Features

- Generate checkerboards of any width × height dimensions
- Control the color of the top-left square (`:light` or `:dark`)
- SVG output is fully responsive and scalable
- CSS-based color theming - change colors dynamically using CSS
- Pure Clojure implementation with no external dependencies beyond core Clojure

## Requirements

- Java 8 or higher
- Clojure CLI tools (or Leiningen)

## Installation

Clone the repository:

```bash
git clone https://github.com/arachtivix/chess-variants-display.git
cd chess-variants-display
```

## Usage

### As a Library

The core namespace provides two main functions:

#### `checkerboard`

Generate an SVG checkerboard:

```clojure
(require '[chess-variants-display.core :refer [checkerboard]])

;; 8x8 chess board with dark top-left square
(checkerboard 8 8 :dark)

;; 10x10 checkers board with light top-left square
(checkerboard 10 10 :light)
```

Returns an SVG string with CSS classes `dark-square` and `light-square` for the squares.

#### `render-checkerboard-html`

Generate a complete HTML page with embedded SVG:

```clojure
(require '[chess-variants-display.core :refer [render-checkerboard-html]])

;; With default colors (chess green/cream)
(render-checkerboard-html 8 8 :dark)

;; With custom colors
(render-checkerboard-html 8 8 :dark "#b58863" "#f0d9b5")
```

### Running Examples

Generate example HTML files:

```bash
clojure -M -m chess-variants-display.example
```

This creates three HTML files:
- `example-chess.html` - 8×8 standard chess board
- `example-checkers.html` - 10×10 checkers board
- `example-custom.html` - 5×5 board with custom colors

### CSS Customization

The SVG uses CSS classes for styling, making it easy to change colors:

```css
.dark-square { fill: #769656; }  /* Dark squares */
.light-square { fill: #eeeed2; } /* Light squares */
```

You can override these in your stylesheet to create different themes. See `demo.html` for examples of multiple color schemes.

## Running Tests

```bash
clojure -M:test -e "(require 'chess-variants-display.core-test) (clojure.test/run-tests 'chess-variants-display.core-test)"
```

## API Reference

### `checkerboard [width height top-left-color]`

Parameters:
- `width` - Number of squares wide (integer)
- `height` - Number of squares tall (integer)  
- `top-left-color` - `:light` or `:dark` - determines the color of the top-left square

Returns: SVG string with responsive viewBox

### `render-checkerboard-html [width height top-left-color & [dark-color light-color]]`

Parameters:
- `width` - Number of squares wide (integer)
- `height` - Number of squares tall (integer)
- `top-left-color` - `:light` or `:dark`
- `dark-color` - (optional) CSS color value for dark squares (default: `"#769656"`)
- `light-color` - (optional) CSS color value for light squares (default: `"#eeeed2"`)

Returns: Complete HTML document string

## Examples

See `demo.html` for a comprehensive demonstration of CSS-responsive theming with multiple color schemes.

## License

This project is open source.
