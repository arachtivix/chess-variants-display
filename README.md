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

## Continuous Integration

This project uses GitHub Actions to automatically run tests on pull requests. The workflow:

- Runs tests automatically when a PR is opened or updated
- Skips running when a PR is in draft mode (e.g., when Copilot is still working on changes)
- Must pass before a PR can be merged to the main branch
- Also runs on pushes to the main branch to verify the main branch remains healthy

To make the test workflow a required check:
1. Go to repository Settings → Branches
2. Add a branch protection rule for `main`
3. Enable "Require status checks to pass before merging"
4. Select "test" as a required status check

## GitHub Pages Demo

This repository automatically deploys the `demo.html` file to GitHub Pages whenever changes are merged to the main branch.

**Live Demo:** https://arachtivix.github.io/chess-variants-display/demo.html

The deployment workflow:
- Triggers automatically on pushes to the `main` branch
- Deploys the demo.html file and any supporting assets
- Updates the live demo within minutes of merging changes

### Setting up GitHub Pages (First Time)

If this is a new repository, you'll need to enable GitHub Pages:

1. Go to repository Settings → Pages
2. Under "Build and deployment", set:
   - Source: **GitHub Actions**
3. The deployment workflow will handle the rest automatically

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

**Live Demo:** View the demo online at https://arachtivix.github.io/chess-variants-display/demo.html

## License

This project is open source.
