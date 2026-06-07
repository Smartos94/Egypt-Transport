"""Test the scraper on one route without sleeping through all 36."""
import io
import sys
from pathlib import Path
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding="utf-8")
sys.path.insert(0, str(Path(__file__).resolve().parent))

import scrape_egypt_trains as s
import requests

session = requests.Session()
session.headers.update(s.HEADERS)

print(f"Routes configured: {len(s.ROUTES)}")
print(f"Station map size:  {len(s.STATION_NAME_TO_ID)}")
print()

url = f"{s.BASE_URL}/cairo/alexandria?lang=en"
print(f"GET {url}")
r = session.get(url, timeout=20)
r.encoding = "utf-8"
print(f"  status: {r.status_code}  bytes: {len(r.text)}")

trains = s.parse_route(r.text, s.ROUTES[0])
print(f"  parsed: {len(trains)} trains")
print()
for t in trains[:8]:
    print(f"  #{t['number']:<6} {t['type']:<25} {t['departureTime']:<10} -> {t['arrivalTime']:<10} ({t['durationMinutes']:>3}m) {t['fare']:>5.0f} EGP")
    print(f"     EN: {t['nameEn']}")
    print(f"     AR: {t['nameAr']}")
    print(f"     dir: {t['directionEn']}")
    print(f"     ar:  {t['directionAr']}")
    print(f"     st:  {t['stationIds']}")
    print()

# Summary stats
all_stations = {sid for t in trains for sid in t["stationIds"]}
print(f"Unique stations used: {len(all_stations)}")
print(f"Sample stations: {sorted(all_stations)[:5]}")

# Test minutes_to_clock
print()
print("Clock test:")
for m in [0, 60, 240, 720, 780, 1439, 1500, 1320]:
    print(f"  {m:>4} min -> {s.minutes_to_clock(m)}")

