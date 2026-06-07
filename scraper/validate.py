"""Final integration check: confirm scraped data matches what the app expects."""
import json

with open("../egypt_railways.json", encoding="utf-8") as f:
    d = json.load(f)

trains = d["trains"]
print(f"Total: {len(trains)}")
print()

required_keys = {"id", "number", "nameAr", "nameEn", "lineId", "type",
                 "directionAr", "directionEn", "departureTime", "arrivalTime",
                 "durationMinutes", "fare", "stationIds"}
bad = 0
for i, t in enumerate(trains):
    missing = required_keys - set(t.keys())
    if missing:
        print(f"BAD train #{i}: missing {missing}")
        bad += 1
    if not isinstance(t.get("stationIds"), list) or len(t["stationIds"]) < 2:
        print(f"BAD train #{i} id={t.get('id')}: stationIds={t.get('stationIds')}")
        bad += 1
    if t.get("durationMinutes", 0) <= 0:
        print(f"BAD train #{i} id={t.get('id')}: duration={t.get('durationMinutes')}")
        bad += 1
print(f"\nValidation: {bad} bad trains, {len(trains)-bad} good")

print()
print("Sample by line:")
from collections import defaultdict
by_line = defaultdict(list)
for t in trains:
    by_line[t["lineId"]].append(t)

for lid in sorted(by_line.keys()):
    samples = by_line[lid][:2]
    print(f"\n  [{lid}] ({len(by_line[lid])} trains)")
    for t in samples:
        print(f"    #{t['number']:>5} {t['nameEn']:30s} {t['departureTime']:>9s}->{t['arrivalTime']:<9s} {t['fare']:>5.0f} EGP")
        print(f"           stations ({len(t['stationIds'])}): {', '.join(t['stationIds'][:4])}{'...' if len(t['stationIds'])>4 else ''}")

# Also check vs old JSON
print()
print("Compare with old JSON:")
try:
    with open("../app/src/main/assets/egypt_railways.json.bak", encoding="utf-8") as f:
        old = json.load(f)
    print(f"  Old: {len(old.get('trains', []))} trains")
    print(f"  New: {len(trains)} trains  (+{len(trains) - len(old.get('trains', []))})")
except FileNotFoundError:
    print("  (no backup to compare)")
