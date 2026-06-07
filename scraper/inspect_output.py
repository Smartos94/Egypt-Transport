"""Inspect the scraped egypt_railways.json output."""
import json
from collections import Counter

with open("../egypt_railways.json", encoding="utf-8") as f:
    d = json.load(f)

print(f"Version:        {d['version']}")
print(f"lastUpdated:    {d['lastUpdated']}")
print(f"source:         {d['source']}")
print(f"Total trains:   {len(d['trains'])}")
print()

lines = Counter(t["lineId"] for t in d["trains"])
print("By line:")
for lid, n in sorted(lines.items(), key=lambda x: -x[1]):
    print(f"  {n:4d}  {lid}")
print()

types = Counter(t["type"] for t in d["trains"])
print("By type:")
for ty, n in sorted(types.items(), key=lambda x: -x[1]):
    print(f"  {n:4d}  {ty}")
print()

st = set()
for t in d["trains"]:
    st.update(t["stationIds"])
print(f"Unique stations used: {len(st)}")
print(f"Sample: {sorted(st)[:10]}")
print()

print("First 3 trains:")
for t in d["trains"][:3]:
    print(f"  #{t['number']:>5} {t['nameEn']:30s} {t['departureTime']:>9s} -> {t['arrivalTime']:<9s} {t['fare']:>5.0f} EGP  line={t['lineId']}")

print()
print("Trains where stationIds is empty or 1-entry:")
for t in d["trains"]:
    if len(t.get("stationIds", [])) < 2:
        print(f"  {t['id']:20s} stations={t.get('stationIds')}")

print()
print("File size:")
import os
print(f"  {os.path.getsize('../egypt_railways.json')} bytes ({os.path.getsize('../egypt_railways.json') / 1024:.1f} KB)")
