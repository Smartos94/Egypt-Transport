package com.example.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import com.example.model.MonorailData
import com.example.model.Station
import com.example.model.StationStatus
import com.example.model.Train
import com.example.model.SuperJetTrip
import com.example.model.TransitMode
import com.example.model.TransportLine
import com.example.model.AppNotification
import com.example.model.dynamicColorHex
import com.example.model.dynamicStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SavedPlace(
    val name: String,
    val address: String = "",
    val notes: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val id: String = java.util.UUID.randomUUID().toString(),
    val type: String = "address", // "address" or "trip"
    val sourceStationId: String? = null,
    val destStationId: String? = null,
    val sourceNameAr: String? = null,
    val sourceNameEn: String? = null,
    val destNameAr: String? = null,
    val destNameEn: String? = null,
    val fare: Double? = null,
    val duration: Int? = null,
    val lineMode: String? = null
)

class MonorailViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPrefs = application.getSharedPreferences("transport_prefs", Context.MODE_PRIVATE)

    private val _isEnglish = MutableStateFlow(sharedPrefs.getBoolean("is_english", false))
    val isEnglish: StateFlow<Boolean> = _isEnglish.asStateFlow()

    private val _isDarkMode = MutableStateFlow(sharedPrefs.getBoolean("is_dark_mode", true))
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _adsRemoved = MutableStateFlow(sharedPrefs.getBoolean("ads_removed", false))
    val adsRemoved: StateFlow<Boolean> = _adsRemoved.asStateFlow()

    fun isAdsRemoved(): Boolean = _adsRemoved.value

    data class BypassCode(
        val code: String,
        val deviceId: String
    )

    private val _bypassCodes = mutableListOf<BypassCode>()
    private val _validCodes = mutableListOf<String>()

    private val _visibleNotifications = MutableStateFlow<List<AppNotification>>(emptyList())
    val visibleNotifications = _visibleNotifications.asStateFlow()

    private val _unreadNotificationsCount = MutableStateFlow(0)
    val unreadNotificationsCount = _unreadNotificationsCount.asStateFlow()

    val deviceId: String
        get() = getOrCreateDeviceId()

    fun getOrCreateDeviceId(): String {
        var deviceId = sharedPrefs.getString("device_id", null)
        if (deviceId == null) {
            deviceId = java.util.UUID.randomUUID().toString().take(8).uppercase()
            sharedPrefs.edit().putString("device_id", deviceId).apply()
        }
        return deviceId
    }

    fun updateNotificationsList() {
        val dismissed = getDismissedNotificationIds()
        val read = getReadNotificationIds()
        val active = MonorailData.notifications.filter { it.id !in dismissed }
        _visibleNotifications.value = active
        _unreadNotificationsCount.value = active.count { it.id !in read }
    }

    fun dismissNotification(id: String) {
        val set = getDismissedNotificationIds().toMutableSet()
        set.add(id)
        sharedPrefs.edit().putStringSet("dismissed_notifications", set).apply()
        updateNotificationsList()
    }

    fun markAllNotificationsAsRead() {
        val activeIds = MonorailData.notifications.map { it.id }.toSet()
        val set = getReadNotificationIds().toMutableSet()
        set.addAll(activeIds)
        sharedPrefs.edit().putStringSet("read_notifications", set).apply()
        updateNotificationsList()
    }

    private fun getDismissedNotificationIds(): Set<String> {
        return sharedPrefs.getStringSet("dismissed_notifications", emptySet()) ?: emptySet()
    }

    private fun getReadNotificationIds(): Set<String> {
        return sharedPrefs.getStringSet("read_notifications", emptySet()) ?: emptySet()
    }

    private fun parseCodesFromJson(jsonString: String) {
        try {
            val root = org.json.JSONObject(jsonString)
            val newValidCodes = mutableListOf<String>()
            val newBypassCodes = mutableListOf<BypassCode>()

            if (root.has("codes")) {
                val arr = root.getJSONArray("codes")
                for (i in 0 until arr.length()) {
                    newValidCodes.add(arr.getString(i).trim().uppercase())
                }
            }

            if (root.has("bypassCodes")) {
                val arr = root.getJSONArray("bypassCodes")
                for (i in 0 until arr.length()) {
                    val obj = arr.getJSONObject(i)
                    newBypassCodes.add(BypassCode(
                        code = obj.getString("code").trim().uppercase(),
                        deviceId = obj.optString("deviceId", "ALL").trim().uppercase()
                    ))
                }
            }

            synchronized(this) {
                _validCodes.clear()
                _validCodes.addAll(newValidCodes)
                _bypassCodes.clear()
                _bypassCodes.addAll(newBypassCodes)
            }
            android.util.Log.d("MonorailViewModel", "Parsed codes: ${newValidCodes.size} global, ${newBypassCodes.size} device-bound.")
        } catch (e: Exception) {
            android.util.Log.e("MonorailViewModel", "Error parsing codes from JSON: ${e.message}", e)
        }
    }

    fun verifyRemoveAdsCode(code: String): Boolean {
        if (code.isBlank()) return false
        val formattedCode = code.trim().uppercase()
        val currentDeviceId = getOrCreateDeviceId()

        val used = sharedPrefs.getStringSet("used_codes", emptySet()) ?: emptySet()
        if (used.contains(formattedCode)) {
            if (!_adsRemoved.value) {
                _adsRemoved.value = true
                sharedPrefs.edit().putBoolean("ads_removed", true).apply()
            }
            return true
        }

        synchronized(this) {
            // Check global legacy codes
            if (_validCodes.contains(formattedCode)) {
                _adsRemoved.value = true
                val newUsed = HashSet(used)
                newUsed.add(formattedCode)
                sharedPrefs.edit()
                    .putBoolean("ads_removed", true)
                    .putStringSet("used_codes", newUsed)
                    .apply()
                return true
            }

            // Check device-bound codes
            val match = _bypassCodes.find { it.code.equals(formattedCode, ignoreCase = true) }
            if (match != null) {
                val allowedDevice = match.deviceId
                if (allowedDevice.equals("ALL", ignoreCase = true) || allowedDevice.equals(currentDeviceId, ignoreCase = true)) {
                    _adsRemoved.value = true
                    val newUsed = HashSet(used)
                    newUsed.add(formattedCode)
                    sharedPrefs.edit()
                        .putBoolean("ads_removed", true)
                        .putStringSet("used_codes", newUsed)
                        .apply()
                    return true
                }
            }
        }
        return false
    }

    private val _dataUpdateTick = MutableStateFlow(0)
    val dataUpdateTick: StateFlow<Int> = _dataUpdateTick.asStateFlow()

    private val _activeMode = MutableStateFlow(TransitMode.METRO)
    val activeMode: StateFlow<TransitMode> = _activeMode.asStateFlow()

    val stations: List<Station>
        get() {
            val result = MonorailData.allStations.filter { s ->
                s.line.mode == _activeMode.value
            }
            android.util.Log.d("MonorailViewModel", "stations() called: mode=${_activeMode.value.name} total=${MonorailData.allStations.size} filtered=${result.size}")
            return result
        }

    private val _sourceStation = MutableStateFlow<Station?>(null)
    val sourceStation: StateFlow<Station?> = _sourceStation.asStateFlow()

    private val _destStation = MutableStateFlow<Station?>(null)
    val destStation: StateFlow<Station?> = _destStation.asStateFlow()

    private val _sjSourceDest = MutableStateFlow<String?>(null)
    val sjSourceDest: StateFlow<String?> = _sjSourceDest.asStateFlow()
    private val _sjDestDest = MutableStateFlow<String?>(null)
    val sjDestDest: StateFlow<String?> = _sjDestDest.asStateFlow()

    fun setSjSourceDest(destId: String?) { _sjSourceDest.value = destId }
    fun setSjDestDest(destId: String?) { _sjDestDest.value = destId }
    fun swapSjDestinations() {
        val temp = _sjSourceDest.value
        _sjSourceDest.value = _sjDestDest.value
        _sjDestDest.value = temp
    }

    private val _journeyPlan = MutableStateFlow<MonorailData.JourneyPlan?>(null)
    val journeyPlan: StateFlow<MonorailData.JourneyPlan?> = _journeyPlan.asStateFlow()

    private val _selectedStation = MutableStateFlow<Station?>(null)
    val selectedStation: StateFlow<Station?> = _selectedStation.asStateFlow()

    private val _savedPlaces = MutableStateFlow<List<SavedPlace>>(emptyList())
    val savedPlaces: StateFlow<List<SavedPlace>> = _savedPlaces.asStateFlow()

    private val _favoriteRoutes = MutableStateFlow<List<Pair<Station, Station>>>(emptyList())
    val favoriteRoutes: StateFlow<List<Pair<Station, Station>>> = _favoriteRoutes.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Ride Hailing calculator
    data class RideCostEstimate(
        val companyName: String,
        val pricePerKm: Double,
        val estimatedMin: Double,
        val estimatedMax: Double,
        val currency: String = "EGP"
    )

    fun calculateRideCosts(distanceKm: Double): List<RideCostEstimate> {
        val companies = MonorailData.rideHailingCompanies
        if (companies.isNotEmpty()) {
            return companies.map { c ->
                RideCostEstimate(c.nameEn, c.pricePerKm, distanceKm * c.pricePerKm * 0.85, distanceKm * c.pricePerKm * 1.15)
            }
        }
        return listOf(
            RideCostEstimate("Uber", MonorailData.uberPricePerKm, distanceKm * MonorailData.uberPricePerKm * 0.85, distanceKm * MonorailData.uberPricePerKm * 1.25),
            RideCostEstimate("Careem", MonorailData.careemPricePerKm, distanceKm * MonorailData.careemPricePerKm * 0.9, distanceKm * MonorailData.careemPricePerKm * 1.3),
            RideCostEstimate("Didi", MonorailData.didiPricePerKm, distanceKm * MonorailData.didiPricePerKm * 0.8, distanceKm * MonorailData.didiPricePerKm * 1.2),
            RideCostEstimate("InDrive", MonorailData.inDrivePricePerKm, distanceKm * MonorailData.inDrivePricePerKm * 0.85, distanceKm * MonorailData.inDrivePricePerKm * 1.15),
            RideCostEstimate("Bolt", MonorailData.boltPricePerKm, distanceKm * MonorailData.boltPricePerKm * 0.9, distanceKm * MonorailData.boltPricePerKm * 1.2)
        )
    }

    data class SavingsSuggestion(
        val suggestionAr: String,
        val suggestionEn: String,
        val estimatedSavingsAr: String,
        val estimatedSavingsEn: String
    )

    fun getSavingsSuggestions(currentCost: Double, distanceKm: Double): List<SavingsSuggestion> {
        return listOf(
            SavingsSuggestion(
                suggestionAr = "استخدم المترو بدلاً من النقل الذكي لهذه المسافة",
                suggestionEn = "Use Metro instead of ride-hailing for this distance",
                estimatedSavingsAr = "وفر تقريباً ${String.format("%.0f", currentCost * 0.6)} جنيهاً",
                estimatedSavingsEn = "Save approximately ${String.format("%.0f", currentCost * 0.6)} EGP"
            ),
            SavingsSuggestion(
                suggestionAr = "اركب النقل الذكي لأقرب محطة مترو ثم أكمل بالمترو",
                suggestionEn = "Take ride-hailing to nearest metro station then continue by metro",
                estimatedSavingsAr = "وفر تقريباً ${String.format("%.0f", currentCost * 0.35)} جنيهاً",
                estimatedSavingsEn = "Save approximately ${String.format("%.0f", currentCost * 0.35)} EGP"
            ),
            SavingsSuggestion(
                suggestionAr = "اركب النقل الذكي لأقرب محطة أتوبيس BRT ثم أكمل",
                suggestionEn = "Take ride-hailing to nearest BRT station then continue",
                estimatedSavingsAr = "وفر تقريباً ${String.format("%.0f", currentCost * 0.4)} جنيهاً",
                estimatedSavingsEn = "Save approximately ${String.format("%.0f", currentCost * 0.4)} EGP"
            )
        )
    }

    init {
        loadFavorites()
        loadSavedPlaces()
        viewModelScope.launch(Dispatchers.IO) {
            loadLocalData()
            loadRailwaysData()
            fetchRemoteData()
            updateNotificationsList()
        }
    }

    private fun loadLocalData() {
        try {
            val cachedJson = sharedPrefs.getString("cached_transport_json", null)
            if (cachedJson != null) {
                val cachedVersion = org.json.JSONObject(cachedJson).optInt("dataVersion", 0)
                if (cachedVersion >= MonorailData.ASSETS_VERSION) {
                    try {
                        MonorailData.initializeFromJson(cachedJson)
                        parseCodesFromJson(cachedJson)
                        _dataUpdateTick.value++
                        return
                    } catch (e: Exception) {
                        android.util.Log.e("MonorailViewModel", "Cached transport JSON is corrupt or invalid. Discarding.", e)
                        sharedPrefs.edit().remove("cached_transport_json").apply()
                    }
                } else {
                    sharedPrefs.edit().remove("cached_transport_json").apply()
                }
            }
        } catch (e: Exception) { e.printStackTrace() }
        try {
            val inputStream = getApplication<Application>().assets.open("monorail_data.json")
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            MonorailData.initializeFromJson(jsonString)
            parseCodesFromJson(jsonString)
            _dataUpdateTick.value++
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadRailwaysData() {
        try {
            val inputStream = getApplication<Application>().assets.open("egypt_railways.json")
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            mergeRailwaysFromJson(jsonString)
            _dataUpdateTick.value++
        } catch (e: Exception) {
            android.util.Log.w("MonorailViewModel", "No railways JSON found in assets, using hard-coded data only", e)
        }
    }

    private fun mergeRailwaysFromJson(jsonString: String) {
        try {
            val root = org.json.JSONObject(jsonString)
            val trainsArray = root.optJSONArray("trains") ?: return
            val newTrains = mutableListOf<com.example.model.Train>()
            for (i in 0 until trainsArray.length()) {
                val t = trainsArray.getJSONObject(i)
                val stationIdsJson = t.optJSONArray("stationIds")
                val stationIds = if (stationIdsJson != null) {
                    (0 until stationIdsJson.length()).map { stationIdsJson.getString(it) }
                } else emptyList()
                newTrains.add(com.example.model.Train(
                    id = t.optString("id"),
                    number = t.optString("number"),
                    nameAr = t.optString("nameAr"),
                    nameEn = t.optString("nameEn"),
                    lineId = t.optString("lineId"),
                    type = t.optString("type"),
                    directionAr = t.optString("directionAr"),
                    directionEn = t.optString("directionEn"),
                    departureTime = t.optString("departureTime"),
                    arrivalTime = t.optString("arrivalTime"),
                    durationMinutes = t.optInt("durationMinutes"),
                    fare = t.optDouble("fare", 0.0),
                    stationIds = stationIds
                ))
            }
            val byId = MonorailData.trains.associateBy { it.id }.toMutableMap()
            var replaced = 0
            var added = 0
            for (jt in newTrains) {
                if (byId.containsKey(jt.id)) {
                    if (byId[jt.id] != jt) {
                        byId[jt.id] = jt
                        replaced++
                    }
                } else {
                    byId[jt.id] = jt
                    added++
                }
            }
            if (replaced > 0 || added > 0) {
                MonorailData.trains = byId.values.toList()
                MonorailData.trainsByLine = MonorailData.trains.groupBy { it.lineId }
                android.util.Log.d("MonorailViewModel", "Railways JSON: $replaced replaced, $added added (total ${MonorailData.trains.size})")
            }
        } catch (e: Exception) {
            android.util.Log.e("MonorailViewModel", "Failed to merge railways JSON: ${e.message}", e)
        }
    }

    private fun fetchRemoteData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val urlConnection = java.net.URL("$DATA_URL?t=${System.currentTimeMillis()}").openConnection() as java.net.HttpURLConnection
                urlConnection.connectTimeout = 10000
                urlConnection.readTimeout = 10000
                val jsonString = urlConnection.inputStream.bufferedReader().use { it.readText() }

                val root = org.json.JSONObject(jsonString)
                val remoteVersion = root.optInt("dataVersion", 1)

                // Always parse codes from remote JSON if download succeeded
                parseCodesFromJson(jsonString)

                if (remoteVersion > MonorailData.loadedVersion) {
                    // Try parsing the download first. If it throws, it fails here and doesn't save to preferences.
                    MonorailData.initializeFromJson(jsonString)
                    // If parsing succeeded without exceptions, save it to cached_transport_json:
                    sharedPrefs.edit().putString("cached_transport_json", jsonString).apply()
                    loadFavorites()
                    _dataUpdateTick.value++
                    updateNotificationsList()
                    android.util.Log.d("MonorailViewModel", "Successfully fetched and parsed version $remoteVersion from Gist.")
                }
            } catch (e: Exception) {
                android.util.Log.e("MonorailViewModel", "Failed to fetch or parse remote JSON: ${e.message}", e)
            }
        }
    }

    fun setLanguage(isEn: Boolean) {
        _isEnglish.value = isEn
        sharedPrefs.edit().putBoolean("is_english", isEn).apply()
    }

    fun setDarkMode(isDark: Boolean) {
        _isDarkMode.value = isDark
        sharedPrefs.edit().putBoolean("is_dark_mode", isDark).apply()
    }

    fun setActiveMode(mode: TransitMode) {
        _activeMode.value = mode
        _sourceStation.value = null
        _destStation.value = null
        _journeyPlan.value = null
        _selectedStation.value = null
        loadFavorites()
    }

    fun setSourceStation(station: Station?) {
        _sourceStation.value = station
        calculatePlan()
    }

    fun setDestStation(station: Station?) {
        _destStation.value = station
        calculatePlan()
    }

    fun getAvailableTrains(): List<Train> {
        val src = _sourceStation.value ?: return emptyList()
        val dest = _destStation.value ?: return emptyList()
        return MonorailData.getTrainsForRoute(src.id, dest.id, src.line.name)
    }

    fun getSjCompaniesForDestinations(): List<TransportLine> {
        val srcDest = _sjSourceDest.value ?: return emptyList()
        val dstDest = _sjDestDest.value ?: return emptyList()
        val companyIds = MonorailData.findTripsForDestinations(srcDest, dstDest)
            .map { it.companyId }.toSet()
        return companyIds.mapNotNull { try { TransportLine.valueOf(it) } catch (_: Exception) { null } }
    }

    fun getSjTripsForCompanyAndDestinations(companyId: String): List<SuperJetTrip> {
        val srcDest = _sjSourceDest.value ?: return emptyList()
        val dstDest = _sjDestDest.value ?: return emptyList()
        return MonorailData.findTripsForDestinations(srcDest, dstDest)
            .filter { it.companyId == companyId }
    }

    fun swapStations() {
        val temp = _sourceStation.value
        _sourceStation.value = _destStation.value
        _destStation.value = temp
        calculatePlan()
    }

    fun selectStation(station: Station?) {
        _selectedStation.value = station
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun calculatePlan() {
        val src = _sourceStation.value
        val dest = _destStation.value
        if (src != null && dest != null) {
            _journeyPlan.value = MonorailData.planJourney(src.id, dest.id, _activeMode.value)
        } else {
            _journeyPlan.value = null
        }
    }

    fun toggleFavorite(srcId: String, destId: String) {
        val stringSet = sharedPrefs.getStringSet("favorite_routes", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        val key = "$srcId|$destId"
        if (stringSet.contains(key)) {
            stringSet.remove(key)
        } else {
            stringSet.add(key)
        }
        sharedPrefs.edit().putStringSet("favorite_routes", stringSet).apply()
        loadFavorites()
    }

    fun isFavorite(srcId: String, destId: String): Boolean {
        val stringSet = sharedPrefs.getStringSet("favorite_routes", emptySet()) ?: emptySet()
        return stringSet.contains("$srcId|$destId")
    }

    fun loadFavorites() {
        val stringSet = sharedPrefs.getStringSet("favorite_routes", emptySet()) ?: emptySet()
        val favoritesList = mutableListOf<Pair<Station, Station>>()
        for (item in stringSet) {
            val parts = item.split("|")
            if (parts.size == 2) {
                val s = MonorailData.getStationById(parts[0])
                val d = MonorailData.getStationById(parts[1])
                if (s != null && d != null &&
                    s.dynamicStatus == StationStatus.ACTIVE &&
                    d.dynamicStatus == StationStatus.ACTIVE &&
                    s.line.mode == _activeMode.value && d.line.mode == _activeMode.value &&
                    MonorailData.isLineOpen(s.line) && MonorailData.isLineOpen(d.line)) {
                    favoritesList.add(Pair(s, d))
                }
            }
        }
        _favoriteRoutes.value = favoritesList
    }

    // Saved Places
    fun savePlace(place: SavedPlace) {
        val current = _savedPlaces.value.toMutableList()
        current.add(place)
        _savedPlaces.value = current
        savePlacesToPrefs(current)
    }

    fun saveTrip(plan: MonorailData.JourneyPlan, isEn: Boolean = false) {
        val exists = _savedPlaces.value.any {
            it.type == "trip" && it.sourceStationId == plan.source.id && it.destStationId == plan.destination.id
        }
        if (!exists) {
            val srcName = if (isEn) plan.source.nameEn else plan.source.nameAr
            val dstName = if (isEn) plan.destination.nameEn else plan.destination.nameAr
            val tripPlace = SavedPlace(
                name = "$srcName → $dstName",
                address = "${plan.ticketPrice.toInt()} EGP • ${plan.approxDurationMinutes} min",
                type = "trip",
                sourceStationId = plan.source.id,
                destStationId = plan.destination.id,
                sourceNameAr = plan.source.nameAr,
                sourceNameEn = plan.source.nameEn,
                destNameAr = plan.destination.nameAr,
                destNameEn = plan.destination.nameEn,
                fare = plan.ticketPrice,
                duration = plan.approxDurationMinutes,
                lineMode = plan.source.line.mode.name
            )
            savePlace(tripPlace)
        }
    }

    fun isTripSaved(srcId: String, destId: String): Boolean {
        return _savedPlaces.value.any { it.type == "trip" && it.sourceStationId == srcId && it.destStationId == destId }
    }

    fun updatePlace(placeId: String, newName: String, newAddress: String, newNotes: String) {
        val current = _savedPlaces.value.map {
            if (it.id == placeId) it.copy(name = newName, address = newAddress, notes = newNotes) else it
        }
        _savedPlaces.value = current
        savePlacesToPrefs(current)
    }

    fun removePlace(placeId: String) {
        val current = _savedPlaces.value.filter { it.id != placeId }
        _savedPlaces.value = current
        savePlacesToPrefs(current)
    }

    private fun loadSavedPlaces() {
        val json = sharedPrefs.getString("saved_places", null) ?: return
        try {
            val arr = org.json.JSONArray(json)
            val list = mutableListOf<SavedPlace>()
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                list.add(SavedPlace(
                    id = obj.getString("id"),
                    name = obj.getString("name"),
                    address = obj.optString("address", ""),
                    notes = obj.optString("notes", ""),
                    latitude = obj.optDouble("latitude", 0.0),
                    longitude = obj.optDouble("longitude", 0.0),
                    type = obj.optString("type", "address"),
                    sourceStationId = if (obj.has("sourceStationId")) obj.optString("sourceStationId") else null,
                    destStationId = if (obj.has("destStationId")) obj.optString("destStationId") else null,
                    sourceNameAr = if (obj.has("sourceNameAr")) obj.optString("sourceNameAr") else null,
                    sourceNameEn = if (obj.has("sourceNameEn")) obj.optString("sourceNameEn") else null,
                    destNameAr = if (obj.has("destNameAr")) obj.optString("destNameAr") else null,
                    destNameEn = if (obj.has("destNameEn")) obj.optString("destNameEn") else null,
                    fare = if (obj.has("fare")) obj.optDouble("fare") else null,
                    duration = if (obj.has("duration")) obj.optInt("duration") else null,
                    lineMode = if (obj.has("lineMode")) obj.optString("lineMode") else null
                ))
            }
            _savedPlaces.value = list
        } catch (_: Exception) {}
    }

    private fun savePlacesToPrefs(places: List<SavedPlace>) {
        val arr = org.json.JSONArray()
        for (p in places) {
            arr.put(org.json.JSONObject().apply {
                put("id", p.id)
                put("name", p.name)
                put("address", p.address)
                put("notes", p.notes)
                put("latitude", p.latitude)
                put("longitude", p.longitude)
                put("type", p.type)
                put("sourceStationId", p.sourceStationId)
                put("destStationId", p.destStationId)
                put("sourceNameAr", p.sourceNameAr)
                put("sourceNameEn", p.sourceNameEn)
                put("destNameAr", p.destNameAr)
                put("destNameEn", p.destNameEn)
                put("fare", p.fare)
                put("duration", p.duration)
                put("lineMode", p.lineMode)
            })
        }
        sharedPrefs.edit().putString("saved_places", arr.toString()).apply()
    }

    companion object {
        const val DATA_URL = "https://gist.githubusercontent.com/Smartos94/15a2c2c80510266693390ce126eab2d1/raw/monorail_data.json"
        const val RAILWAYS_URL = "https://gist.githubusercontent.com/Smartos94/7f6e8b2d105f74d9157ddfeef6c6b0fc/raw/egypt_railways.json"
    }
}
