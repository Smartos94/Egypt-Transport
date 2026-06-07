package com.example.model

import java.util.Locale

data class SmartResult(
    val stationNameAr: String,
    val stationNameEn: String,
    val lines: List<TransportLine>,
    val stations: List<Station>,
    val score: Double
)

object SmartSearch {
    private data class IndexEntry(
        val arNormalized: String,
        val enLower: String,
        val trigrams: Set<String>,
        val first: Station,
        val allStations: Set<Station>,
        val allLines: Set<TransportLine>
    )

    private var index = listOf<IndexEntry>()
    private var nameKeyToEntry = mapOf<String, IndexEntry>()

    fun rebuildIndex() {
        val active = MonorailData.allStations.filter { it.dynamicStatus == StationStatus.ACTIVE }
        val grouped = active.groupBy { ar(it.nameAr) }
        val entries = grouped.map { (nameKey, sts) ->
            val first = sts.first()
            IndexEntry(
                arNormalized = nameKey,
                enLower = first.nameEn.lowercase(Locale.ROOT),
                trigrams = trigramsOf(nameKey),
                first = first,
                allStations = sts.toSet(),
                allLines = sts.map { it.line }.toSet()
            )
        }
        index = entries
        nameKeyToEntry = entries.associateBy { it.arNormalized }
    }

    fun search(query: String, maxResults: Int = 12): List<SmartResult> {
        if (index.isEmpty()) rebuildIndex()
        val q = ar(query)
        if (q.length < 2) return emptyList()
        val qLower = query.lowercase(Locale.ROOT)
        val qLength = q.length
        val qTrigrams = trigramsOf(q)
        val results = mutableListOf<SmartResult>()

        for (e in index) {
            val s = e.arNormalized
            var score = 0.0

            if (qLength >= 3) {
                if (s == q) score = 100.0
                else if (s.startsWith(q)) score = 80.0
                else if (qLength >= 4 && s.contains(q)) score = 60.0
            } else {
                if (s == q) score = 100.0
                else if (s.startsWith(q)) score = 80.0
            }

            if (score == 0.0 && qLength >= 4) {
                val ld = levenshtein(q, s, 2)
                if (ld == 0) score = 50.0
                else if (ld == 1) score = 40.0
                else if (ld == 2 && qLength >= 6) score = 30.0
            }

            if (score == 0.0 && qLower.length >= 3) {
                val en = e.enLower
                if (en == qLower) score = 90.0
                else if (en.startsWith(qLower)) score = 70.0
                else if (qLower.length >= 4 && en.contains(qLower)) score = 50.0
            }

            if (score == 0.0 && qLength >= 5 && qTrigrams.isNotEmpty() && e.trigrams.isNotEmpty()) {
                val inter = qTrigrams.intersect(e.trigrams).size
                if (inter > 0) {
                    val ts = inter.toDouble() / qTrigrams.union(e.trigrams).size
                    if (ts >= 0.5) score = 15.0 + ts * 25.0
                }
            }

            if (score <= 0.0) continue
            results.add(SmartResult(e.first.nameAr, e.first.nameEn, e.allLines.toList(), e.allStations.toList(), score))
        }

        if (results.isEmpty()) {
            for ((alias, targets) in aliases) {
                val aliasNorm = ar(alias)
                if (q.contains(aliasNorm) || aliasNorm.contains(q)) {
                    for (t in targets) {
                        val e = nameKeyToEntry[ar(t)] ?: continue
                        if (results.any { it.stationNameAr == e.first.nameAr }) continue
                        results.add(SmartResult(e.first.nameAr, e.first.nameEn, e.allLines.toList(), e.allStations.toList(), 45.0))
                    }
                }
            }
        }

        return results
            .sortedByDescending { it.score }
            .distinctBy { ar(it.stationNameAr) }
            .take(maxResults)
    }

    fun getLinesFor(stationName: String): Set<TransportLine> {
        if (index.isEmpty()) rebuildIndex()
        return nameKeyToEntry[ar(stationName)]?.allLines ?: emptySet()
    }

    fun ar(s: String): String = s.lowercase(Locale.ROOT)
        .replace(Regex("[أإآٱ]"), "ا")
        .replace("ة", "ه").replace("ى", "ي")
        .replace("ؤ", "و").replace("ئ", "ي")
        .replace(Regex("[ًٌٍَُِ~ّْ]"), "").trim()

    private fun trigramsOf(s: String): Set<String> {
        if (s.length < 3) return emptySet()
        return (0..s.length - 3).mapTo(mutableSetOf()) { s.substring(it, it + 3) }
    }

    private fun levenshtein(a: String, b: String, maxDist: Int): Int {
        if (kotlin.math.abs(a.length - b.length) > maxDist) return -1
        val dp = IntArray(b.length + 1) { it }
        for (i in 1..a.length) {
            var prev = i - 1
            dp[0] = i
            var rowMin = dp[0]
            for (j in 1..b.length) {
                val cost = if (a[i - 1] == b[j - 1]) 0 else 1
                val tmp = minOf(dp[j] + 1, dp[j - 1] + 1, prev + cost)
                prev = dp[j]
                dp[j] = tmp
                if (tmp < rowMin) rowMin = tmp
            }
            if (rowMin > maxDist) return -1
        }
        return dp[b.length]
    }

    val aliases = mapOf(
        "رمسيس" to listOf("رمسيس", "محطة مصر"),
        "مصر" to listOf("محطة مصر", "مصر", "رمسيس"),
        "عباسية" to listOf("العباسية", "عباسية"),
        "تحرير" to listOf("التحرير", "تحرير", "ميدان التحرير"),
        "مطار" to listOf("مطار القاهرة", "المطار"),
        "جيزة" to listOf("الجيزة", "ميدان الجيزة"),
        "هليوبوليس" to listOf("هليوبوليس", "مصر الجديدة"),
        "حلوان" to listOf("حلوان"),
        "شبرا" to listOf("شبرا", "شبرا الخيمة"),
        "الهرم" to listOf("الهرم", "أهرام"),
        "فيصل" to listOf("فيصل"),
        "العتبة" to listOf("العتبة", "العتبه"),
        "الدقي" to listOf("الدقي", "دقي"),
        "الزمالك" to listOf("الزمالك"),
        "المهندسين" to listOf("المهندسين"),
    )
}
