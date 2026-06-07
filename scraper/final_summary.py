"""Final summary check of the deployed data."""
import json
import os

with open("../egypt_railways.json", encoding="utf-8") as f:
    d = json.load(f)

print(f"Version:    {d['version']}")
print(f"Updated:    {d['lastUpdated']}")
print(f"Source:     {d['source']}")
print(f"Trains:     {len(d['trains'])}")
print(f"File size:  {os.path.getsize('../egypt_railways.json'):,} bytes ({os.path.getsize('../egypt_railways.json')/1024:.1f} KB)")

from collections import Counter
lines = Counter(t["lineId"] for t in d["trains"])
types = Counter(t["type"] for t in d["trains"])
print(f"Lines:      {len(lines)} ({', '.join(sorted(lines))})")
print(f"Types:      {len(types)}")
print()
print("Trains by line:")
for lid, n in sorted(lines.items(), key=lambda x: -x[1]):
    print(f"  {n:4d}  {lid}")
print()
print("Trains by type (top 10):")
for ty, n in sorted(types.items(), key=lambda x: -x[1])[:10]:
    print(f"  {n:4d}  {ty}")
