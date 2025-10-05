# Release Workflow Implementation Summary

## Overview

This implementation adds automated release creation to the chess-variants-display repository. When code is merged to the `main` branch and passes all tests, a GitHub release is automatically created with a JAR file that can be used as a dependency in other projects.

## What Was Implemented

### 1. Build Configuration (`deps.edn`)

Added build aliases to support JAR creation:

- `:build` alias - Uses `tools.build` for building JARs
- `:jar` alias - Alternative using `depstar` (backup option)

### 2. Build Script (`build.clj`)

Created a comprehensive build script with:

- **JAR building** - `clojure -T:build jar`
  - Generates JAR with versioning based on git commit count (1.0.X format)
  - Creates POM file with project metadata
  - Bundles all source files
  
- **Local installation** - `clojure -T:build install`
  - Installs to local Maven repository (~/.m2)
  - Useful for local development and testing

- **Clean** - `clojure -T:build clean`
  - Removes build artifacts from target/

### 3. POM File (`pom.xml`)

Created Maven-compatible metadata including:
- Project information (groupId, artifactId, version)
- Dependencies (Clojure 1.11.1)
- SCM information (git repository links)
- License information (EPL)

### 4. Release Workflow (`.github/workflows/release.yml`)

Automated workflow that:

**Triggers:**
- On push to `main` branch
- Can also be manually triggered via `workflow_dispatch`

**Test Phase:**
- Runs all tests before creating release
- Uses same test command as existing test workflow
- Release only proceeds if tests pass

**Build Phase:**
- Checks out code with full git history (for versioning)
- Sets up Java 11 and Clojure CLI
- Builds JAR using `clojure -T:build jar`
- Extracts version number from build output
- Falls back to git commit count if needed

**Release Phase:**
- Creates GitHub release with tag `v1.0.X`
- Uploads JAR file as release asset
- Uploads POM file as release asset
- Generates release notes with installation instructions
- Uses modern `softprops/action-gh-release@v2` action

### 5. Documentation

**RELEASE.md:**
- Comprehensive guide to the release process
- Instructions for using releases as dependencies
- Local build instructions
- Troubleshooting guide

**README.md updates:**
- Added installation section with git and JAR dependency options
- Updated CI section to mention automated releases
- Links to release documentation

**QUICKSTART.md updates:**
- Added installation options (git dependency and JAR)
- Updated usage examples to include both methods

### 6. Configuration Files

**VERSION:**
- Base version file (1.0.0)
- Can be updated to change major/minor version

**.gitignore updates:**
- Removed `pom.xml` from ignore list (now tracked)
- Build artifacts still excluded (target/, *.jar)

## How It Works

### Workflow Flow

```
Push to main
    ↓
Run Tests (test job)
    ↓ (only if tests pass)
Build JAR (release job)
    ↓
Create GitHub Release
    ↓
Upload JAR & POM
```

### Versioning Strategy

- Format: `1.0.X` where X = git commit count
- Examples:
  - v1.0.50 - after 50 commits
  - v1.0.100 - after 100 commits
- Major/minor version can be changed by updating VERSION file and build.clj

### Release Assets

Each release includes:
1. **JAR file** - `chess-variants-display-1.0.X.jar`
   - Contains all source code
   - Can be added directly to classpath
   
2. **POM file** - `chess-variants-display-1.0.X.pom`
   - Maven metadata
   - Useful for dependency management

## Using the Release

### Option 1: Git Dependency (Recommended)

```clojure
{:deps {chess-variants-display/chess-variants-display 
        {:git/url "https://github.com/arachtivix/chess-variants-display"
         :git/sha "sha-from-release"}}}
```

### Option 2: JAR Dependency

```clojure
{:paths ["src" "libs/chess-variants-display-1.0.X.jar"]}
```

### Option 3: Local Maven Install

```bash
clojure -T:build install
```

Then:
```clojure
{:deps {chess-variants-display/chess-variants-display {:mvn/version "1.0.X"}}}
```

## Files Changed

- **Added:**
  - `.github/workflows/release.yml` - Release automation workflow
  - `build.clj` - Build script using tools.build
  - `pom.xml` - Maven metadata
  - `RELEASE.md` - Release documentation
  - `VERSION` - Version file

- **Modified:**
  - `deps.edn` - Added build aliases
  - `.gitignore` - Keep pom.xml in repo
  - `README.md` - Added installation and release info
  - `QUICKSTART.md` - Added installation options

## Validation

The workflow has been configured to:
- ✅ Run tests before creating release
- ✅ Only proceed if tests pass
- ✅ Generate proper version numbers
- ✅ Create releases with JAR and POM files
- ✅ Use modern GitHub Actions (no deprecated actions)
- ✅ Include helpful release notes

## Benefits

1. **Automated Releases** - No manual steps needed
2. **Quality Assurance** - Tests must pass before release
3. **Easy Integration** - JAR ready for other projects
4. **Version Tracking** - Automatic versioning based on commits
5. **Documentation** - Clear instructions for users
6. **Professional** - Standard Maven-compatible packaging

## Next Steps (Optional)

Future enhancements could include:
- Publishing to Clojars for easier dependency management
- Semantic versioning with conventional commits
- Changelog generation from commit messages
- Release notes from PR descriptions
- Multi-platform testing before release

## Testing the Workflow

To test the workflow:
1. Merge this PR to main
2. Workflow will automatically run
3. Check Actions tab for workflow execution
4. Verify release is created with JAR and POM files
5. Test downloading and using the JAR

## Troubleshooting

If the workflow fails:
1. Check Actions tab for error logs
2. Verify Java/Clojure setup steps succeed
3. Ensure tests pass
4. Check build output for JAR creation
5. See RELEASE.md for common issues and solutions
