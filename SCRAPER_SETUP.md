# How to enable free auto-updating of the railway data

The scraper + GitHub Actions workflow in this repo can keep
`egypt_railways.json` in sync with egypttrains.com automatically. The app
already loads it on startup, so once this is wired up your users always see
fresh data — no Play Store update needed.

## What you have

```
scraper/
├── scrape_egypt_trains.py     # Python scraper (uses requests + bs4)
├── requirements.txt           # pinned dependencies
└── README.md
.github/workflows/
└── update-trains.yml          # runs scraper every 6 h, commits JSON
```

## One-time setup (≈ 5 minutes, all free)

### 1. Push this repo to GitHub

```
git init
git add .
git commit -m "Initial commit"
gh repo create egypt-railways-data --public --source=. --push
```

### 2. Create a public Gist for the JSON

The app's `RAILWAYS_URL` points at a Gist. Gist content can be updated
without changing the URL, so the app always gets the latest version.

- Go to https://gist.github.com and create a **public** Gist
- Filename: `egypt_railways.json`
- Paste the contents of the local `egypt_railways.json`
- Click **Create public gist**
- Copy the **raw URL** (right-click *Raw* → *Copy link*).
  It will look like:
  `https://gist.githubusercontent.com/<user>/<id>/raw/egypt_railways.json`

### 3. Verify the Gist URL in the app

`app/src/main/java/com/example/viewmodel/MonorailViewModel.kt` already has:

```kotlin
const val RAILWAYS_URL = "https://gist.githubusercontent.com/Smartos94/15a2c2c80510266693390ce126eab2d1/raw/egypt_railways.json"
```

If your Gist has a different URL, change this constant. Rebuild + publish.

### 4. Enable the workflow

The cron schedule (`0 */6 * * *`) only fires on the **default branch** of a
GitHub-hosted repo. After pushing:

- Open the repo → **Actions** tab → enable workflows if prompted
- The next run will be within 6 hours
- Or trigger manually: **Actions** → *Update Egyptian Railways Data* →
  **Run workflow**

## How the Gist stays in sync ✅

The workflow automatically syncs the new JSON to the Gist after each scrape
via the `Sync to Gist` step. Two repo secrets store the credentials:

- `GIST_ID` = `7f6e8b2d105f74d9157ddfeef6c6b0fc` (already set)
- `GIST_TOKEN` = a GitHub PAT with `gist` scope (already set)

These were configured with:
```
gh secret set GIST_ID --repo Smartos94/Egypt-Transport --body "7f6e8b2d105f74d9157ddfeef6c6b0fc"
gh secret set GIST_TOKEN --repo Smartos94/Egypt-Transport --body "$(gh auth token)"
```

If the PAT expires, regenerate it at https://github.com/settings/tokens
(with `gist` scope) and update the secret.

## Cost

- **GitHub Actions**: 2 000 free minutes / month on public repos. This
  workflow uses ~1 minute per run = ~120 minutes / month.
- **Gist**: free, public, unlimited size (single file limit 1 MB; our JSON
  is ~165 KB).
- **Egypttrains.com scraping**: free. Their `robots.txt` only disallows
  `/cdn-cgi/`, not the schedule pages.

## Local development

```bash
pip install -r scraper/requirements.txt
python scraper/scrape_egypt_trains.py
# Writes egypt_railways.json in the project root
```

To test the parser on one route only:

```bash
python scraper/test_parse_one.py
```
