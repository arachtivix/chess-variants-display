# Setting Up Branch Protection

To enforce that tests must pass before merging to the main branch, follow these steps:

## Step 1: Navigate to Branch Protection Settings

1. Go to your repository on GitHub: `https://github.com/arachtivix/chess-variants-display`
2. Click on **Settings** (you need admin access)
3. In the left sidebar, click on **Branches**

## Step 2: Add Branch Protection Rule

1. Click **Add branch protection rule** (or **Add rule**)
2. In the "Branch name pattern" field, enter: `main`

## Step 3: Configure Protection Rules

Enable the following settings:

### Required Status Checks
- ✅ Check **Require status checks to pass before merging**
- ✅ Check **Require branches to be up to date before merging**
- In the search box, type `test` and select it from the list (it will appear after the first workflow run)

### Additional Recommended Settings
- ✅ **Require a pull request before merging** - Prevents direct pushes to main
  - Optionally: **Require approvals** (set to 1 or more)
- ✅ **Do not allow bypassing the above settings** - Enforces rules for everyone, including admins

## Step 4: Save Changes

Click **Create** or **Save changes** at the bottom of the page.

## How the Workflow Works

The GitHub Actions workflow (`.github/workflows/test.yml`) will:

1. **Trigger on:**
   - Pull request events: `opened`, `synchronize`, `reopened`, `ready_for_review`
   - Pushes to the `main` branch

2. **Smart Draft Detection:**
   - Skips running when a PR is in draft mode
   - Only runs when PR is ready for review
   - This helps avoid wasting CI minutes when Copilot is still working on changes

3. **Test Execution:**
   - Sets up Java and Clojure
   - Caches dependencies for faster runs
   - Runs the test suite
   - Reports success/failure

## Verifying Setup

After setting up branch protection:

1. The "test" status check will appear as required on all PRs
2. PRs cannot be merged if tests fail
3. The merge button will be disabled until tests pass
4. Draft PRs will show "Some checks haven't been completed yet" but won't actually run until the PR is marked as ready for review

## Troubleshooting

**Q: The "test" check doesn't appear in the required status checks list**
- A: The check name only appears after the workflow has run at least once. Create a test PR to trigger the workflow first.

**Q: I want to merge despite test failures**
- A: An admin can temporarily disable branch protection or use the "Administrator override" option (not recommended for main branch)

**Q: The workflow runs on draft PRs**
- A: The workflow has a condition to skip draft PRs. Verify the workflow file has: `if: github.event_name == 'push' || github.event.pull_request.draft == false`
