# Release Process

## Overview

This repository automatically creates releases when code is merged to the `main` branch and all tests pass. Each release includes:

- **JAR file** - Ready to use as a dependency in other projects
- **POM file** - Maven metadata for dependency management
- **Automatic versioning** - Based on git commit count (1.0.X format)

## How It Works

The release process is handled by the `.github/workflows/release.yml` workflow:

1. **Test Phase:**
   - Runs all tests to ensure code quality
   - Only proceeds if tests pass

2. **Build Phase:**
   - Builds JAR file using `clojure -T:build jar`
   - Generates version number: `1.0.{commit-count}`
   - Creates POM file with project metadata

3. **Release Phase:**
   - Creates a GitHub release with tag `v1.0.X`
   - Uploads JAR file as release asset
   - Uploads POM file as release asset
   - Generates release notes with installation instructions

## Using a Release as a Dependency

### Option 1: Git Dependency (Recommended)

Add to your `deps.edn`:

```clojure
{:deps {chess-variants-display/chess-variants-display 
        {:git/url "https://github.com/arachtivix/chess-variants-display"
         :git/sha "latest-commit-sha"}}}
```

Replace `latest-commit-sha` with the actual SHA from the release tag.

### Option 2: JAR File Dependency

1. Download the JAR file from the [Releases page](https://github.com/arachtivix/chess-variants-display/releases)
2. Add to your project's classpath:

```clojure
{:paths ["src" "libs/chess-variants-display-1.0.X.jar"]}
```

### Option 3: Local Maven Installation

If you have the repository cloned:

```bash
clojure -T:build install
```

Then reference in `deps.edn`:

```clojure
{:deps {chess-variants-display/chess-variants-display {:mvn/version "1.0.X"}}}
```

## Building Locally

### Build JAR

```bash
clojure -T:build jar
```

Output: `target/chess-variants-display-1.0.X.jar`

### Install to Local Maven

```bash
clojure -T:build install
```

This installs the JAR to your local `~/.m2/repository` for use in other local projects.

### Clean Build Artifacts

```bash
clojure -T:build clean
```

## Versioning

Versions follow the format `1.0.X` where `X` is the git commit count:

- `1.0.1` - First commit
- `1.0.50` - After 50 commits
- etc.

To update the major/minor version, edit the `VERSION` file and update the `build.clj` accordingly.

## Workflow Files

- `.github/workflows/release.yml` - Automated release workflow
- `build.clj` - Build configuration using tools.build
- `deps.edn` - Dependency and build alias configuration
- `pom.xml` - Maven metadata template
- `VERSION` - Version file (currently 1.0.0 base)

## Troubleshooting

### Release Fails

**Issue:** Workflow fails with permission error
- **Solution:** Ensure GitHub Actions has write permissions for releases
- Go to Settings → Actions → General → Workflow permissions
- Select "Read and write permissions"

**Issue:** JAR build fails
- **Solution:** Check that `build.clj` is valid Clojure code
- Test locally: `clojure -T:build jar`

**Issue:** Version number is wrong
- **Solution:** The version is based on git commit count
- Ensure you have full git history (workflow uses `fetch-depth: 0`)

### Using the JAR

**Issue:** JAR doesn't work in other projects
- **Solution:** Make sure you're using Java 8 or higher
- Check that all dependencies are included (pom.xml lists Clojure 1.11.1)

## Manual Release (if needed)

If the automated workflow fails, you can create a release manually:

1. Build the JAR:
   ```bash
   clojure -T:build jar
   ```

2. Create a git tag:
   ```bash
   git tag v1.0.X
   git push origin v1.0.X
   ```

3. Go to GitHub → Releases → Create new release
4. Select the tag
5. Upload the JAR and POM files from `target/`

## Next Steps

After a successful release, the JAR can be:

- Downloaded and added to any Java/Clojure project's classpath
- Referenced as a git dependency in `deps.edn`
- Installed to local Maven for development
- Published to Clojars (future enhancement)
