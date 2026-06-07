#!/usr/bin/env python3
"""
Scrape egypttrains.com for all Egyptian National Railways train schedules
and emit egypt_railways.json in the format the Android app expects.

The output schema is identical to the hand-curated egypt_railways.json that
lives in app/src/main/assets/. The app's MonorailViewModel.loadRailwaysData()
and mergeRailwaysFromJson() consume it as-is.
"""
from __future__ import annotations

import json
import re
import sys
import time
from datetime import datetime, timezone
from pathlib import Path
from typing import Any

import requests
from bs4 import BeautifulSoup, Tag

BASE_URL = "https://egypttrains.com"
USER_AGENT = (
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
    "(KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36"
)
HEADERS = {
    "User-Agent": USER_AGENT,
    "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
    "Accept-Language": "en-US,en;q=0.9,ar;q=0.8",
}
REQUEST_TIMEOUT = 20
SLEEP_BETWEEN_REQUESTS = 1.0

ROUTES: list[dict[str, str]] = [
    {"slug": "cairo/alexandria",     "lineId": "RAILWAY_CAIRO_ALEX",     "src": "Cairo",          "dst": "Alexandria",     "srcAr": "القاهرة",  "dstAr": "الإسكندرية"},
    {"slug": "cairo/aswan",          "lineId": "RAILWAY_CAIRO_ASWAN",     "src": "Cairo",          "dst": "Aswan",          "srcAr": "القاهرة",  "dstAr": "أسوان"},
    {"slug": "cairo/luxor",          "lineId": "RAILWAY_CAIRO_LUXOR",     "src": "Cairo",          "dst": "Luxor",          "srcAr": "القاهرة",  "dstAr": "الأقصر"},
    {"slug": "cairo/asyut",          "lineId": "RAILWAY_CAIRO_ASWAN",     "src": "Cairo",          "dst": "Asyut",          "srcAr": "القاهرة",  "dstAr": "أسيوط"},
    {"slug": "cairo/sohag",          "lineId": "RAILWAY_CAIRO_ASWAN",     "src": "Cairo",          "dst": "Sohag",          "srcAr": "القاهرة",  "dstAr": "سوهاج"},
    {"slug": "cairo/qena",           "lineId": "RAILWAY_CAIRO_ASWAN",     "src": "Cairo",          "dst": "Qena",           "srcAr": "القاهرة",  "dstAr": "قنا"},
    {"slug": "cairo/beni-suef",      "lineId": "RAILWAY_CAIRO_ASWAN",     "src": "Cairo",          "dst": "Beni Suef",      "srcAr": "القاهرة",  "dstAr": "بني سويف"},
    {"slug": "cairo/minya",          "lineId": "RAILWAY_CAIRO_ASWAN",     "src": "Cairo",          "dst": "Minya",          "srcAr": "القاهرة",  "dstAr": "المنيا"},
    {"slug": "cairo/faiyum",         "lineId": "RAILWAY",                "src": "Cairo",          "dst": "Fayoum",         "srcAr": "القاهرة",  "dstAr": "الفيوم"},
    {"slug": "cairo/mansoura",       "lineId": "RAILWAY_CAIRO_MANSOURA",  "src": "Cairo",          "dst": "Mansoura",       "srcAr": "القاهرة",  "dstAr": "المنصورة"},
    {"slug": "cairo/damietta",       "lineId": "RAILWAY_CAIRO_MANSOURA",  "src": "Cairo",          "dst": "Damietta",       "srcAr": "القاهرة",  "dstAr": "دمياط"},
    {"slug": "cairo/tanta",          "lineId": "RAILWAY_CAIRO_TANTA",     "src": "Cairo",          "dst": "Tanta",          "srcAr": "القاهرة",  "dstAr": "طنطا"},
    {"slug": "cairo/zagazig",        "lineId": "RAILWAY",                "src": "Cairo",          "dst": "Zagazig",        "srcAr": "القاهرة",  "dstAr": "الزقازيق"},
    {"slug": "cairo/ismailia",       "lineId": "RAILWAY",                "src": "Cairo",          "dst": "Ismailia",       "srcAr": "القاهرة",  "dstAr": "الإسماعيلية"},
    {"slug": "cairo/port-said",      "lineId": "RAILWAY_BENHA_PORT_SAID", "src": "Cairo",          "dst": "Port Said",      "srcAr": "القاهرة",  "dstAr": "بورسعيد"},
    {"slug": "cairo/suez",           "lineId": "RAILWAY",                "src": "Cairo",          "dst": "Suez",           "srcAr": "القاهرة",  "dstAr": "السويس"},
    {"slug": "cairo/damanhour",      "lineId": "RAILWAY_CAIRO_ALEX",     "src": "Cairo",          "dst": "Damanhour",      "srcAr": "القاهرة",  "dstAr": "دمنهور"},
    {"slug": "cairo/marsa-matruh",   "lineId": "RAILWAY",                "src": "Cairo",          "dst": "Marsa Matrouh",  "srcAr": "القاهرة",  "dstAr": "مرسى مطروح"},
    {"slug": "alexandria/cairo",     "lineId": "RAILWAY_CAIRO_ALEX",     "src": "Alexandria",     "dst": "Cairo",          "srcAr": "الإسكندرية", "dstAr": "القاهرة"},
    {"slug": "aswan/cairo",          "lineId": "RAILWAY_CAIRO_ASWAN",     "src": "Aswan",          "dst": "Cairo",          "srcAr": "أسوان",     "dstAr": "القاهرة"},
    {"slug": "luxor/cairo",          "lineId": "RAILWAY_CAIRO_LUXOR",     "src": "Luxor",          "dst": "Cairo",          "srcAr": "الأقصر",    "dstAr": "القاهرة"},
    {"slug": "asyut/cairo",          "lineId": "RAILWAY_CAIRO_ASWAN",     "src": "Asyut",          "dst": "Cairo",          "srcAr": "أسيوط",     "dstAr": "القاهرة"},
    {"slug": "sohag/cairo",          "lineId": "RAILWAY_CAIRO_ASWAN",     "src": "Sohag",          "dst": "Cairo",          "srcAr": "سوهاج",     "dstAr": "القاهرة"},
    {"slug": "qena/cairo",           "lineId": "RAILWAY_CAIRO_ASWAN",     "src": "Qena",           "dst": "Cairo",          "srcAr": "قنا",       "dstAr": "القاهرة"},
    {"slug": "beni-suef/cairo",      "lineId": "RAILWAY_CAIRO_ASWAN",     "src": "Beni Suef",      "dst": "Cairo",          "srcAr": "بني سويف",  "dstAr": "القاهرة"},
    {"slug": "minya/cairo",          "lineId": "RAILWAY_CAIRO_ASWAN",     "src": "Minya",          "dst": "Cairo",          "srcAr": "المنيا",     "dstAr": "القاهرة"},
    {"slug": "faiyum/cairo",         "lineId": "RAILWAY",                "src": "Fayoum",         "dst": "Cairo",          "srcAr": "الفيوم",    "dstAr": "القاهرة"},
    {"slug": "mansoura/cairo",       "lineId": "RAILWAY_CAIRO_MANSOURA",  "src": "Mansoura",       "dst": "Cairo",          "srcAr": "المنصورة",  "dstAr": "القاهرة"},
    {"slug": "damietta/cairo",       "lineId": "RAILWAY_CAIRO_MANSOURA",  "src": "Damietta",       "dst": "Cairo",          "srcAr": "دمياط",     "dstAr": "القاهرة"},
    {"slug": "tanta/cairo",          "lineId": "RAILWAY_CAIRO_TANTA",     "src": "Tanta",          "dst": "Cairo",          "srcAr": "طنطا",      "dstAr": "القاهرة"},
    {"slug": "zagazig/cairo",        "lineId": "RAILWAY",                "src": "Zagazig",        "dst": "Cairo",          "srcAr": "الزقازيق",  "dstAr": "القاهرة"},
    {"slug": "ismailia/cairo",       "lineId": "RAILWAY",                "src": "Ismailia",       "dst": "Cairo",          "srcAr": "الإسماعيلية","dstAr": "القاهرة"},
    {"slug": "port-said/cairo",      "lineId": "RAILWAY_BENHA_PORT_SAID", "src": "Port Said",      "dst": "Cairo",          "srcAr": "بورسعيد",   "dstAr": "القاهرة"},
    {"slug": "suez/cairo",           "lineId": "RAILWAY",                "src": "Suez",           "dst": "Cairo",          "srcAr": "السويس",    "dstAr": "القاهرة"},
    {"slug": "damanhour/cairo",      "lineId": "RAILWAY_CAIRO_ALEX",     "src": "Damanhour",      "dst": "Cairo",          "srcAr": "دمنهور",    "dstAr": "القاهرة"},
    {"slug": "marsa-matruh/cairo",   "lineId": "RAILWAY",                "src": "Marsa Matrouh",  "dst": "Cairo",          "srcAr": "مرسى مطروح","dstAr": "القاهرة"},
]

STATION_NAME_TO_ID: dict[str, str] = {
    "cairo":            "RAIL_CAIRO_RAMSES",
    "ramses":           "RAIL_CAIRO_RAMSES",
    "giza":             "RAIL_GIZA",
    "imbaba":           "RAIL_IMBABA",
    "benha":            "RAIL_BENHA",
    "tanta":            "RAIL_TANTA",
    "damanhour":        "RAIL_DAMANHOUR",
    "damanhur":         "RAIL_DAMANHOUR",
    "damietta":         "RAIL_DAMIETTA",
    "mansoura":         "RAIL_MANSOURA",
    "zagazig":          "RAIL_ZAGAZIG",
    "mit ghamr":        "RAIL_MIT_GHAMR",
    "salheya":          "RAIL_SALHEYA",
    "kafr el sheikh":   "RAIL_KAFR_EL_SHEIKH",
    "kafr el-sheikh":   "RAIL_KAFR_EL_SHEIKH",
    "qleen":            "RAIL_QLEEN",
    "quesna":           "RAIL_QUESNA",
    "berket saba":      "RAIL_BERKET_SABA",
    "berket el-saba":   "RAIL_BERKET_SABA",
    "itay barud":       "RAIL_ITAY_BARUD",
    "itay el-barud":    "RAIL_ITAY_BARUD",
    "shibin el qanater":"RAIL_SHIBIN_EL_QANATER",
    "shibin qanatir":   "RAIL_SHIBIN_EL_QANATER",
    "qanater":          "RAIL_SHIBIN_EL_QANATER",
    "qanater el khayreya": "RAIL_SHIBIN_EL_QANATER",
    "toukh":            "RAIL_TOUKH",
    "qaha":             "RAIL_QAHA",
    "menouf":           "RAIL_MENOUF",
    "minuf":            "RAIL_MENOUF",
    "shebin el koom":   "RAIL_SHEBIN_EL_KOOM",
    "shibin el kom":    "RAIL_SHEBIN_EL_KOOM",
    "kafr el-zayat":    "RAIL_KAFR_EL_ZAYAT",
    "kafr el zayat":    "RAIL_KAFR_EL_ZAYAT",
    "baltim":           "RAIL_BALTIM",
    "desouk":           "RAIL_DESOUK",
    "rashid":           "RAIL_RASHID",
    "rosetta":          "RAIL_RASHID",
    "ismailia":         "RAIL_ISMAILIA",
    "port said":        "RAIL_PORT_SAID",
    "portsaid":         "RAIL_PORT_SAID",
    "suez":             "RAIL_SUEZ",
    "qantara gharb":    "RAIL_QANTARA_GHARB",
    "qantara":          "RAIL_QANTARA_GHARB",
    "biar el abd":      "RAIL_BIAR_EL_ABD",
    "bir el abd":       "RAIL_BIAR_EL_ABD",
    "alexandria":       "RAIL_ALEX_MISR",
    "alex":             "RAIL_ALEX_MISR",
    "sidi gaber":       "RAIL_ALEX_SIDI_GABER",
    "beni suef":        "RAIL_BENI_SUEF",
    "fayoum":           "RAIL_FAYOUM",
    "minya":            "RAIL_MINYA",
    "asyut":            "RAIL_ASYUT",
    "sohag":            "RAIL_SOHAG",
    "qena":             "RAIL_QENA",
    "luxor":            "RAIL_LUXOR",
    "aswan":            "RAIL_ASWAN",
    "naqada":           "RAIL_NAQADA",
    "nag hammadi":      "RAIL_NAG_HAMMADI",
    "abu tist":         "RAIL_ABU_TIST",
    "dishna":           "RAIL_DISHNA",
    "samalut":          "RAIL_SAMALUT",
    "maghagha":         "RAIL_MAGHAGHA",
    "bani mazar":       "RAIL_BANI_MAZAR",
    "matai":            "RAIL_MATAI",
    "dayrout":          "RAIL_DAYROUT",
    "manfalut":         "RAIL_MANFALUT",
    "el qusiya":        "RAIL_EL_QUSIYA",
    "abnub":            "RAIL_ABNUB",
    "tima":             "RAIL_TIMA",
    "girga":            "RAIL_GIRGA",
    "tahta":            "RAIL_TAHTA",
    "maragha":          "RAIL_MARAGHA",
    "akhmim":           "RAIL_AKHMIM",
    "esna":             "RAIL_ESNA",
    "edfu":             "RAIL_EDFU",
    "kom ombo":         "RAIL_KOM_OMBO",
    "marsa matrouh":    "RAIL_MARSA_MATROUH",
    "matrouh":          "RAIL_MARSA_MATROUH",
}

TYPE_DISPLAY_TO_AR: dict[str, str] = {
    "Talgo":            "تالجو",
    "Russian":          "روسي",
    "AC Russian":       "AC روسي",
    "AC Spanish":       "مكيف إسباني",
    "AC French":        "مكيف فرنسي",
    "VIP":              "VIP",
    "Abu Al-Hol":       "أبو الهول",
    "Abu Al-Hol + Sleep": "أبو الهول + نوم",
    "Premium":          "بريميوم",
    "Premium + VIP":    "بريميوم + VIP",
    "Premium + Sleep":  "بريميوم + نوم",
    "Sleep":            "نوم",
    "Sleeper":          "نوم",
    "Improved":         "محسن",
    "Mix":              "مختلط",
    "Third Class":      "ثالثة مروحية",
    "Second Class AC":  "ثانية مكيف",
    "First Class AC":   "أولى مكيف",
}


def minutes_to_clock(total_min: int) -> str:
    """Convert minutes-from-midnight to 'H:MM AM/PM' (12-hour, no leading 0)."""
    total_min = int(total_min) % (24 * 60)
    h24, m = divmod(total_min, 60)
    period = "AM" if h24 < 12 else "PM"
    h12 = h24 % 12
    if h12 == 0:
        h12 = 12
    return f"{h12}:{m:02d} {period}"


def normalize_type(raw: str) -> str:
    """egypttrains.com raw 'data-type' → human display string."""
    raw = (raw or "").strip().lower()
    if not raw:
        return ""
    parts = [p.strip() for p in raw.split(",") if p.strip()]
    norm: list[str] = []
    for p in parts:
        if p == "russian":
            norm.append("Russian")
        elif p == "ac-russian":
            norm.append("AC Russian")
        elif p == "ac-spanish":
            norm.append("AC Spanish")
        elif p == "ac-french":
            norm.append("AC French")
        elif p == "vip":
            norm.append("VIP")
        elif p == "talgo":
            norm.append("Talgo")
        elif p == "abu-al-hol":
            norm.append("Abu Al-Hol")
        elif p in ("sleeper", "sleep"):
            norm.append("Sleep")
        elif p == "premium":
            norm.append("Premium")
        elif p == "improved":
            norm.append("Improved")
        elif p == "mix":
            norm.append("Mix")
        elif p == "third-class-fan":
            norm.append("Third Class")
        elif p == "second-class-ac":
            norm.append("Second Class AC")
        elif p == "first-class-ac":
            norm.append("First Class AC")
        else:
            norm.append(p)
    if not norm:
        return ""
    if len(norm) == 1:
        return norm[0]
    return " + ".join(norm)


def name_to_station_id(name: str) -> str | None:
    if not name:
        return None
    key = name.strip().lower()
    if key in STATION_NAME_TO_ID:
        return STATION_NAME_TO_ID[key]
    no_punct = re.sub(r"[^a-z0-9 ]+", " ", key)
    no_punct = re.sub(r"\s+", " ", no_punct).strip()
    if no_punct in STATION_NAME_TO_ID:
        return STATION_NAME_TO_ID[no_punct]
    return None


def parse_route(html: str, route: dict[str, str]) -> list[dict[str, Any]]:
    soup = BeautifulSoup(html, "lxml")
    table = soup.find("table", id="tripsList")
    if not isinstance(table, Tag):
        return []
    rows = table.select("tbody tr.rp-row")
    if not rows:
        return []
    trains: list[dict[str, Any]] = []
    for tr in rows:
        d = tr.attrs
        train_id_raw = d.get("data-train-id", "").strip()
        if not train_id_raw:
            continue
        type_display = normalize_type(d.get("data-type", ""))
        if not type_display:
            continue
        try:
            dep_min = int(d.get("data-dep", "0"))
            arr_min = int(d.get("data-arr", "0"))
            dur_min = int(d.get("data-dur", "0"))
            price_min = int(d.get("data-price", "0"))
        except (TypeError, ValueError):
            continue
        if dur_min <= 0 or not type_display:
            continue
        tpl = tr.select_one(".rp-c-details template")
        timeline: list[tuple[str, str]] = []
        if isinstance(tpl, Tag):
            tpl_html = tpl.decode_contents()
            if tpl_html.strip():
                tpl_soup = BeautifulSoup(tpl_html, "lxml")
                for li in tpl_soup.select("li.rp-timeline__item"):
                    time_text = li.select_one(".rp-timeline__time")
                    name_text = li.select_one(".rp-timeline__name")
                    if time_text and name_text:
                        timeline.append((time_text.get_text(strip=True),
                                         name_text.get_text(strip=True)))
        station_ids: list[str] = []
        for _, station_name in timeline:
            sid = name_to_station_id(station_name)
            if sid and sid not in station_ids:
                station_ids.append(sid)
        if not station_ids:
            continue
        type_ar = TYPE_DISPLAY_TO_AR.get(type_display, type_display)
        number = train_id_raw
        trains.append({
            "id":              f"RAIL_TRAIN_{number}",
            "number":          number,
            "nameEn":          f"{type_display} {number}",
            "nameAr":          f"{type_ar} {number}",
            "lineId":          route["lineId"],
            "type":            type_display,
            "directionEn":     f"{route['src']} → {route['dst']}",
            "directionAr":     f"{route['srcAr']} ← {route['dstAr']}",
            "departureTime":   minutes_to_clock(dep_min),
            "arrivalTime":     minutes_to_clock(arr_min),
            "durationMinutes": dur_min,
            "fare":            float(price_min) if price_min > 0 else 0.0,
            "stationIds":      station_ids,
        })
    return trains


def fetch_route(session: requests.Session, route: dict[str, str]) -> str | None:
    url = f"{BASE_URL}/{route['slug']}?lang=en"
    try:
        r = session.get(url, headers=HEADERS, timeout=REQUEST_TIMEOUT)
        r.raise_for_status()
        r.encoding = "utf-8"
        return r.text
    except requests.RequestException as exc:
        print(f"  ✗ {route['slug']}: {exc}", file=sys.stderr)
        return None


def merge_unique(trains: list[dict[str, Any]]) -> list[dict[str, Any]]:
    by_id: dict[str, dict[str, Any]] = {}
    for t in trains:
        tid = t.get("id", "")
        if not tid:
            continue
        if tid not in by_id:
            by_id[tid] = t
        else:
            existing = by_id[tid]
            if len(t.get("stationIds", [])) > len(existing.get("stationIds", [])):
                by_id[tid] = t
    return list(by_id.values())


def main() -> int:
    out_path = Path(__file__).resolve().parent.parent / "egypt_railways.json"
    print(f"Scraping {len(ROUTES)} routes from {BASE_URL} ...")
    session = requests.Session()
    session.headers.update(HEADERS)
    all_trains: list[dict[str, Any]] = []
    for i, route in enumerate(ROUTES, 1):
        print(f"  [{i:2d}/{len(ROUTES)}] {route['slug']:<22}", end="", flush=True)
        html = fetch_route(session, route)
        if not html:
            print()
            continue
        trains = parse_route(html, route)
        all_trains.extend(trains)
        print(f"  -> {len(trains):3d} trains")
        if i < len(ROUTES):
            time.sleep(SLEEP_BETWEEN_REQUESTS)
    all_trains = merge_unique(all_trains)
    all_trains.sort(key=lambda t: (t["lineId"], t["number"], t["departureTime"]))
    today = datetime.now(timezone.utc).strftime("%Y-%m-%d")
    output = {
        "dataVersion": 2,
        "version":     "6.4",
        "lastUpdated": today,
        "source":      "egypttrains.com (live scrape of Egyptian National Railways schedules)",
        "trains":      all_trains,
    }
    out_path.write_text(json.dumps(output, indent=2, ensure_ascii=False), encoding="utf-8")
    print(f"\nWrote {len(all_trains)} unique trains → {out_path}")
    return 0


if __name__ == "__main__":
    sys.exit(main())
