# GitHub Pages Setup

This repository uses GitHub Pages to host live demos of the chess-variants-display library.

## Overview

The demos are automatically built and deployed when changes are pushed to the `main` branch.

## How It Works

1. **Landing Page**: `index.html` serves as the main landing page with links to all demos
2. **Demos Directory**: All demo files are stored in the `demos/` directory
3. **Automated Build**: The `.github/workflows/pages.yml` workflow:
   - Triggers on pushes to `main`
   - Installs Clojure and dependencies
   - Generates demo files from scratch using the example.clj script
   - Deploys everything to GitHub Pages

## Demo Files

- **`demos/css-themes.html`**: Manually crafted demo showing CSS theming capabilities (tracked in git)
- **`demos/example-chess.html`**: 8×8 chess board (auto-generated, not in git)
- **`demos/example-checkers.html`**: 10×10 checkers board (auto-generated, not in git)
- **`demos/example-custom.html`**: 5×5 custom colors board (auto-generated, not in git)

The example-*.html files are excluded from git via `.gitignore` because they are regenerated on each deployment.

## Enabling GitHub Pages

To enable GitHub Pages for this repository:

1. Go to repository **Settings** → **Pages**
2. Under "Build and deployment":
   - **Source**: Select "GitHub Actions"
3. The workflow will automatically deploy on the next push to `main`

## Viewing the Demos

Once deployed, the demos will be available at:
`https://arachtivix.github.io/chess-variants-display/`

## Local Testing

To test the demos locally:

```bash
# Generate demo files
clojure -M -m chess-variants-display.example
mv example-*.html demos/

# Start a local server
python3 -m http.server 8000

# Visit http://localhost:8000 in your browser
```

## Workflow Details

The deployment workflow (`.github/workflows/pages.yml`):
- Runs only on pushes to `main` branch
- Uses GitHub Actions permissions to deploy to Pages
- Builds demos from scratch each time to ensure they're always up-to-date
- Uses caching to speed up dependency downloads
