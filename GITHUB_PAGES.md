# GitHub Pages Deployment

## Overview

This repository automatically deploys the `demo.html` file to GitHub Pages whenever code is merged to the main branch. The live demo showcases the CSS-responsive checkerboard functionality with multiple color themes.

**Live Demo:** https://arachtivix.github.io/chess-variants-display/demo.html

## How It Works

The deployment is handled by the `.github/workflows/deploy-pages.yml` workflow:

1. **Triggers:** Automatically runs on every push to the `main` branch
2. **Checkout:** Retrieves the latest code from the repository
3. **Setup Pages:** Configures GitHub Pages environment
4. **Upload Artifact:** Packages all repository files (including demo.html)
5. **Deploy:** Publishes the artifact to GitHub Pages

The workflow uses official GitHub Actions:
- `actions/checkout@v4` - Checks out the repository
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

The workflow deploys all files in the repository root:
- `demo.html` - The main demo file (primary content)
- Any other files in the repository (for completeness)

The demo will be accessible at the root URL and via the direct path:
- https://arachtivix.github.io/chess-variants-display/
- https://arachtivix.github.io/chess-variants-display/demo.html

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

1. Edit `demo.html` locally
2. Commit and push to your branch
3. Create a pull request to `main`
4. After merging, the workflow automatically deploys
5. Changes appear within 1-2 minutes

## Files

- `.github/workflows/deploy-pages.yml` - Deployment workflow
- `demo.html` - Demo page (gets deployed)
- `GITHUB_PAGES.md` - This documentation

## Benefits

- 🚀 **Automatic Deployment:** No manual steps required
- ⚡ **Fast Updates:** Changes deploy within minutes of merging
- 🔒 **Secure:** Uses official GitHub Actions with proper permissions
- 📦 **Simple:** Just merge to main, and it deploys
- 🌐 **Professional:** Clean URL for sharing demos
