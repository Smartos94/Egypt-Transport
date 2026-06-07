# Egyptian Railways Live Scraper

Scrapes egypttrains.com for the latest train schedules and prices, and emits
`egypt_railways.json` in the exact format the Android app expects.

## Why

The original `egypt_railways.json` was hand-curated from multiple news sites
(almasryalyoum, elwatannews, masralyoum, dostor, enr.gov.eg). egypttrains.com
is the most accurate, up-to-date mirror of the official Egyptian National
Railways schedule (37 trains Cairo–Alexandria alone, with live stops and
prices). This scraper keeps the JSON current automatically.

## Run locally

```bash
pip install -r requirements.txt
python scrape_egypt_trains.py
```

Output: `egypt_railways.json` at the project root (the file the Android app
loads from `assets/`).

## How it works

- For each of 40 routes (Cairo ↔ 19 destinations, return trips), it fetches
  `https://egypttrains.com/<route>?lang=en`.
- Each train is a `<tr class="rp-row">` with rich `data-*` attributes
  (departure/arrival in minutes from 00:00, duration, price, stops count).
- The `<template>` inside each row contains a full `<ol class="rp-timeline">`
  with every intermediate stop and its time.
- It normalizes English station names to the app's `RAIL_*` IDs, translates
  train types to Arabic, and emits a JSON object shaped exactly like the
  app's hard-coded `EgyptRailwayData.kt` expects.

## GitHub Actions

`.github/workflows/update-trains.yml` runs this every 6 hours and auto-commits
the JSON to a Gist (the URL is wired into `MonorailViewModel.RAILWAYS_URL`),
so the app stays current with no manual work.
