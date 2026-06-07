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

## How the Gist stays in sync (optional)

The workflow commits the new JSON to the **repo**, but not the Gist. To
keep the Gist updated automatically, you have two options:

**A) Manual** — after each workflow run, copy `egypt_railways.json` from
the repo to the Gist (overwriting). Takes 30 seconds.

**B) Automatic** — add a second workflow step that uses
[`actions/github-script`](https://github.com/actions/github-script) to
update the Gist via the GitHub API with a `GIST_TOKEN` secret. The token
only needs `gist` scope.

A reference snippet (drop into `update-trains.yml` after the commit step):

```yaml
      - name: Sync to Gist
        env:
          GIST_TOKEN: ${{ secrets.GIST_TOKEN }}
          GIST_ID:    ${{ secrets.GIST_ID }}
        run: |
          curl -fsSL -X PATCH \
            -H "Authorization: token $GIST_TOKEN" \
            -H "Content-Type: application/json" \
            -d "$(jq -nc --arg c "$(cat egypt_railways.json)" \
                  '{files: {"egypt_railways.json": {content: $c}}}')" \
            "https://api.github.com/gists/$GIST_ID"
```

To get `GIST_ID` and `GIST_TOKEN`:
- `GIST_ID` is the URL path of your Gist (the part after `/gist/`)
- `GIST_TOKEN` is a personal access token with `gist` scope (Settings →
  Developer settings → Personal access tokens)

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
