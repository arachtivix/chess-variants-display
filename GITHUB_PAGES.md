# GitHub Pages Deployment

## Overview

This repository automatically generates and deploys a demo HTML page to GitHub Pages whenever code is merged to the main branch. The demo is built from Clojure code and showcases the CSS-responsive checkerboard functionality with multiple color themes.

**Live Demo:** https://arachtivix.github.io/chess-variants-display/

## How It Works

The deployment is handled by the `.github/workflows/deploy-pages.yml` workflow:

1. **Triggers:** Automatically runs on every push to the `main` branch
2. **Checkout:** Retrieves the latest code from the repository
3. **Setup Java & Clojure:** Installs the required build tools
4. **Generate Demo:** Runs `clojure -M -m chess-variants-display.demo` to build the HTML
5. **Setup Pages:** Configures GitHub Pages environment
6. **Upload Artifact:** Packages the `docs/` folder with generated HTML
7. **Deploy:** Publishes the artifact to GitHub Pages

The workflow uses official GitHub Actions:
- `actions/checkout@v4` - Checks out the repository
- `actions/setup-java@v4` - Installs Java
- `DeLaGuardo/setup-clojure@12.5` - Installs Clojure CLI
- `actions/configure-pages@v4` - Sets up Pages configuration
- `actions/upload-pages-artifact@v3` - Uploads the content
- `actions/deploy-pages@v4` - Deploys to GitHub Pages

## Initial Setup

### Enable GitHub Pages (One-Time Setup)

1. Go to the repository on GitHub
2. Navigate to **Settings** → **Pages**
3. Under **"Build and deployment"**:
   - **Source:** Select **GitHub Actions**
4. Save the settings

That's it! The workflow will automatically deploy on the next push to main.

### Permissions

The workflow has the necessary permissions configured:
- `contents: read` - Read repository files
- `pages: write` - Deploy to GitHub Pages
- `id-token: write` - Required for GitHub Pages deployment

### Concurrency Control

The workflow includes concurrency settings to:
- Allow only one deployment at a time
- Queue new deployments while one is in progress
- Prevent canceling in-progress deployments (to ensure complete deployments)

## What Gets Deployed

The workflow generates and deploys the demo HTML from code:
- **Source:** `src/chess-variants-display/demo.clj` - Clojure code that generates the demo
- **Output:** `docs/index.html` - Generated demo page (created during build)
- **Deployed to:** GitHub Pages at the repository URL

The demo will be accessible at:
- https://arachtivix.github.io/chess-variants-display/

### Building the Demo Locally

You can generate the demo HTML locally to test changes:

```bash
clojure -M -m chess-variants-display.demo
```

This creates `docs/index.html` with the complete demo page.

## Monitoring Deployments

### View Deployment Status

1. Go to the repository on GitHub
2. Click on **Actions** tab
3. Look for "Deploy to GitHub Pages" workflow runs
4. Each run shows:
   - Deployment status (success/failure)
   - Deployment URL
   - Time taken

### Deployment Environment

The workflow uses a `github-pages` environment:
- View it in **Settings** → **Environments**
- Shows deployment history
- Displays the current deployment URL

## Troubleshooting

### Deployment Fails

**Issue:** Workflow fails with permission error
- **Solution:** Ensure GitHub Pages is enabled in Settings → Pages with "GitHub Actions" as the source

**Issue:** 404 error when accessing the page
- **Solution:** Wait a few minutes after first deployment. GitHub Pages can take time to provision initially

**Issue:** Old content shows after deployment
- **Solution:** Clear browser cache or wait a few minutes for CDN to update

### Making Changes to the Demo

1. Edit `src/chess-variants-display/demo.clj` to modify the demo
2. Test locally: `clojure -M -m chess-variants-display.demo`
3. Verify `docs/index.html` looks correct
4. Commit and push to your branch
5. Create a pull request to `main`
6. After merging, the workflow automatically rebuilds and deploys
7. Changes appear within 1-2 minutes

## Files

- `.github/workflows/deploy-pages.yml` - Deployment workflow
- `src/chess-variants-display/demo.clj` - Demo generator (source)
- `docs/index.html` - Generated demo page (created by build, not committed)
- `GITHUB_PAGES.md` - This documentation

## Benefits

- 🚀 **Automatic Deployment:** No manual steps required
- ⚡ **Fast Updates:** Changes deploy within minutes of merging
- 🔒 **Secure:** Uses official GitHub Actions with proper permissions
- 📦 **Simple:** Just merge to main, and it deploys
- 🌐 **Professional:** Clean URL for sharing demos
- 🔨 **Built from Code:** Demo is generated from source, ensuring consistency
