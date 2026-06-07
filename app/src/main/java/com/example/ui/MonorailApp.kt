package com.example.ui

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.MonorailData
import com.example.model.MonorailData.RideCompany
import com.example.model.Train
import com.example.model.SuperJetTrip
import com.example.model.Station
import com.example.model.StationStatus
import com.example.model.AppNotification
import com.example.model.dynamicColorHex
import com.example.model.dynamicStatus
import com.example.model.TransitMode
import com.example.model.TransportLine
import com.example.viewmodel.MonorailViewModel
import com.example.viewmodel.SavedPlace
import com.example.model.SmartSearch
import com.example.model.SmartResult
import com.example.ui.components.*
import com.example.ui.theme.*
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.math.roundToInt
import kotlin.math.ceil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonorailApp(viewModel: MonorailViewModel) {
    val isEn by viewModel.isEnglish.collectAsState()
    val isDark by viewModel.isDarkMode.collectAsState()
    val dataUpdateTick by viewModel.dataUpdateTick.collectAsState()
    val activeMode by viewModel.activeMode.collectAsState()
    val sourceStation by viewModel.sourceStation.collectAsState()
    val destStation by viewModel.destStation.collectAsState()
    val journeyPlan by viewModel.journeyPlan.collectAsState()
    val currentSelectedStation by viewModel.selectedStation.collectAsState()
    val favoriteRoutes by viewModel.favoriteRoutes.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val savedPlaces by viewModel.savedPlaces.collectAsState()
    val adsRemoved by viewModel.adsRemoved.collectAsState()
    val unreadNotifCount by viewModel.unreadNotificationsCount.collectAsState()
    val visibleNotifs by viewModel.visibleNotifications.collectAsState()
    var showNotificationsDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    var showDisclaimer by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        val prefs = context.getSharedPreferences("disclaimer_prefs", android.content.Context.MODE_PRIVATE)
        if (!prefs.getBoolean("disclaimer_shown", false)) {
            showDisclaimer = true
        }
    }

    var activeTab by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    val layoutDirection = if (isEn) androidx.compose.ui.unit.LayoutDirection.Ltr else androidx.compose.ui.unit.LayoutDirection.Rtl

    CompositionLocalProvider(
        androidx.compose.ui.platform.LocalLayoutDirection provides layoutDirection
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                                    .border(1.2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DirectionsTransit,
                                    contentDescription = "Logo",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Column(modifier = Modifier.weight(1f, fill = false)) {
                                Text(
                                    text = if (isEn) MonorailData.defaultGreetingEn else MonorailData.defaultGreetingAr,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontSize = 15.sp
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Notification Bell
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(MaterialTheme.colorScheme.surface)
                                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f), RoundedCornerShape(14.dp))
                                    .clickable {
                                        showNotificationsDialog = true
                                        viewModel.markAllNotificationsAsRead()
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Notifications",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                if (unreadNotifCount > 0) {
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(top = 4.dp, end = 4.dp)
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.error)
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(MaterialTheme.colorScheme.surface)
                                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f), RoundedCornerShape(14.dp))
                                    .clickable { viewModel.setDarkMode(!isDark) },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                                    contentDescription = "Toggle Theme",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .height(42.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(MaterialTheme.colorScheme.surface)
                                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f), RoundedCornerShape(14.dp))
                                    .clickable { viewModel.setLanguage(!isEn) }
                                    .padding(horizontal = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Icon(
                                        imageVector = Icons.Default.Language,
                                        contentDescription = "Toggle Language",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = if (isEn) "العربية" else "EN",
                                        fontWeight = FontWeight.Black,
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            },
            bottomBar = {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().height(74.dp).padding(horizontal = 6.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val items = listOf(
                            Triple(if (isEn) "Plan" else "الرحلات", Icons.Default.DirectionsSubway, Icons.Outlined.DirectionsSubway),
                            Triple(if (isEn) "Stations" else "المحطات", Icons.Default.Map, Icons.Outlined.Map),
                            Triple(if (isEn) "Saved" else "المحفوظات", Icons.Default.Bookmark, Icons.Outlined.BookmarkBorder),
                            Triple(if (isEn) "About" else "حول", Icons.Default.Info, Icons.Outlined.Info)
                        )
                        items.forEachIndexed { index, (label, filledIcon, outlinedIcon) ->
                            val isSelected = activeTab == index
                            Column(
                                modifier = Modifier.weight(1f).clip(RoundedCornerShape(16.dp)).clickable { activeTab = index }.padding(vertical = 4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier.size(38.dp).clip(CircleShape).background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (isSelected) filledIcon else outlinedIcon,
                                        contentDescription = label,
                                        tint = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = label,
                                    fontSize = 10.sp,
                                    fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues).drawBehind {
                    val brush = Brush.radialGradient(
                        colors = listOf(
                            (if (isDark) Color(0xFFFFD54F) else Color(0xFFFFC107)).copy(alpha = 0.07f),
                            Color.Transparent
                        ),
                        center = Offset(size.width * 0.8f, size.height * 0.2f),
                        radius = size.width * 0.6f
                    )
                    drawRect(brush)
                }
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    if (MonorailData.showBannerNotice) {
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.9f)),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.4f))
                        ) {
                            Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Icon(imageVector = Icons.Default.Warning, contentDescription = "Warning", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(20.dp))
                                Text(text = if (isEn) MonorailData.bannerNoticeEn else MonorailData.bannerNoticeAr, style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onErrorContainer, fontWeight = FontWeight.Bold), modifier = Modifier.weight(1f))
                            }
                        }
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        Crossfade(targetState = activeTab, label = "tab") { tab ->
                            when (tab) {
                                 0 -> RoutePlannerView(viewModel, isEn, sourceStation, destStation, journeyPlan, favoriteRoutes, savedPlaces, adsRemoved)
                                1 -> StationsDirectoryView(viewModel, isEn)
                                 2 -> SavedPlacesView(viewModel, isEn, savedPlaces, adsRemoved)
                                3 -> AboutView(
                                    isEn = isEn,
                                    adsRemoved = adsRemoved,
                                    deviceId = viewModel.deviceId,
                                    onVerifyCode = { viewModel.verifyRemoveAdsCode(it) }
                                )
                            }
                        }
                    }
                    AdBanner(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp), isEn = isEn, adsRemoved = adsRemoved)
                }
                currentSelectedStation?.let { station ->
                    StationDetailBottomSheetDialog(station = station, isEn = isEn, onDismiss = { viewModel.selectStation(null) })
                }
            }
        }
    }

    if (showDisclaimer) {
        DisclaimerDialog(isEn = isEn, onDismiss = {
            showDisclaimer = false
            context.getSharedPreferences("disclaimer_prefs", android.content.Context.MODE_PRIVATE).edit().putBoolean("disclaimer_shown", true).apply()
        })
    }

    if (showNotificationsDialog) {
        NotificationsDialog(
            isEn = isEn,
            notifications = visibleNotifs,
            onDismiss = { showNotificationsDialog = false },
            onDelete = { viewModel.dismissNotification(it) }
        )
    }
}

// ==================== TAB 0: ROUTE PLANNER ====================
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RoutePlannerView(
    viewModel: MonorailViewModel,
    isEn: Boolean,
    sourceStation: Station?,
    destStation: Station?,
    journeyPlan: MonorailData.JourneyPlan?,
    favoriteRoutes: List<Pair<Station, Station>>,
    savedPlaces: List<SavedPlace>,
    adsRemoved: Boolean = false
) {
    val activeMode by viewModel.activeMode.collectAsState()
    val sjSourceDest by viewModel.sjSourceDest.collectAsState()
    val sjDestDest by viewModel.sjDestDest.collectAsState()
    var showSourceSelector by remember { mutableStateOf(false) }
    var showDestSelector by remember { mutableStateOf(false) }
    var showSJSourceDestPicker by remember { mutableStateOf(false) }
    var showSJDestDestPicker by remember { mutableStateOf(false) }
    var showDetails by remember { mutableStateOf(false) }

    var busSelectedRoute by remember { mutableStateOf<TransportLine?>(null) }
    var busSearchQuery by remember { mutableStateOf("") }
    var busDropdownExpanded by remember { mutableStateOf(true) }
    var showBusDetails by remember { mutableStateOf(false) }

    var expandedTrainId by remember { mutableStateOf<String?>(null) }
    var expandedSJCompanyId by remember { mutableStateOf<String?>(null) }

    // Ride hailing calculator state
    var rideDistanceText by remember { mutableStateOf("") }
    var rideUnitKm by remember { mutableStateOf(true) }
    var rideSelectedCompany by remember { mutableStateOf<RideCompany?>(null) }
    var rideCompanyDropdownExpanded by remember { mutableStateOf(false) }
    var rideCalculatedCost by remember { mutableStateOf<Double?>(null) }
    var showRideDetails by remember { mutableStateOf(false) }
    var showRailwayDetails by remember { mutableStateOf(false) }
    var showSjDetails by remember { mutableStateOf(false) }
    var mainSearchQuery by remember { mutableStateOf("") }
    var debouncedSearchQuery by remember { mutableStateOf("") }
    LaunchedEffect(mainSearchQuery) {
        kotlinx.coroutines.delay(220)
        debouncedSearchQuery = mainSearchQuery
    }
    val smartSearchResults = remember(debouncedSearchQuery) { if (debouncedSearchQuery.isNotEmpty()) SmartSearch.search(debouncedSearchQuery) else emptyList() }

    LaunchedEffect(journeyPlan) { showDetails = false }
    LaunchedEffect(activeMode) {
        showBusDetails = false
        if (activeMode != TransitMode.BUS_AUTHORITY && activeMode != TransitMode.BUS_MINI) { busSelectedRoute = null; busSearchQuery = "" }
        if (activeMode != TransitMode.SUPER_JET) { expandedSJCompanyId = null; viewModel.setSjSourceDest(null); viewModel.setSjDestDest(null) }
        showRideDetails = false
        showRailwayDetails = false
        showSjDetails = false
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().testTag("route_planner_container"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header: Search + Mode Grid
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.fillMaxWidth().background(
                    Brush.linearGradient(colors = listOf(MaterialTheme.colorScheme.primary.copy(alpha = 0.18f), MaterialTheme.colorScheme.surface))
                ).padding(20.dp)) {
                    // Search bar (replaces old "خطط رحلتك" badge)
                    TextField(value = mainSearchQuery, onValueChange = { mainSearchQuery = it },
                        placeholder = { Text(text = if (isEn) "Search any station..." else "ابحث عن أي محطة...", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp)) },
                        trailingIcon = { if (mainSearchQuery.isNotEmpty()) IconButton(onClick = { mainSearchQuery = "" }) { Icon(Icons.Default.Clear, contentDescription = "Clear", tint = MaterialTheme.colorScheme.onBackground) } },
                        singleLine = true, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp),
                        colors = TextFieldDefaults.colors(focusedContainerColor = MaterialTheme.colorScheme.surface, unfocusedContainerColor = MaterialTheme.colorScheme.surface, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, disabledContainerColor = MaterialTheme.colorScheme.surface, disabledIndicatorColor = Color.Transparent))

                    if (mainSearchQuery.isEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = if (isEn) "Choose Your Transport" else "اختر وسيلة مواصلاتك", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black, fontSize = 21.sp, color = MaterialTheme.colorScheme.onBackground))
                        Spacer(modifier = Modifier.height(12.dp))

                        // 3+3+3 Grid (Metro, Monorail, LRT | Railway, BRT, Bus Auth | Mini Bus, Super Jet, Ride Hailing)
                        val transportRows = listOf(
                            listOf(TransitMode.METRO, TransitMode.MONORAIL, TransitMode.LRT),
                            listOf(TransitMode.RAILWAY, TransitMode.BRT, TransitMode.BUS_AUTHORITY),
                            listOf(TransitMode.BUS_MINI, TransitMode.SUPER_JET, TransitMode.RIDE_HAILING)
                        )
                        for (row in transportRows) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                row.forEach { mode ->
                                    val isActive = activeMode == mode
                                    Card(
                                        modifier = Modifier.weight(1f).height(54.dp).clip(RoundedCornerShape(14.dp)).clickable { viewModel.setActiveMode(mode) },
                                        border = BorderStroke(if (isActive) 1.8.dp else 1.dp, if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                                        colors = CardDefaults.cardColors(containerColor = if (isActive) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface)
                                    ) {
                                        Row(modifier = Modifier.fillMaxSize().padding(horizontal = 6.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                            val icon = getModeIcon(mode)
                                            Box(modifier = Modifier.size(28.dp).clip(RoundedCornerShape(8.dp)).background(if (isActive) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else Color.Transparent), contentAlignment = Alignment.Center) {
                                                Icon(imageVector = icon, contentDescription = null, tint = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f), modifier = Modifier.size(18.dp))
                                            }
                                            Text(
                                                text = if (isEn) mode.nameEn else mode.nameAr,
                                                fontSize = 8.sp,
                                                lineHeight = 10.sp,
                                                fontWeight = FontWeight.Black,
                                                color = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                                                maxLines = 2,
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }

        // Smart search results (when typing)
        if (mainSearchQuery.isNotEmpty()) {
            val smartResults = smartSearchResults
            val showLoading = debouncedSearchQuery != mainSearchQuery
            if (showLoading) {
                item {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        CircularProgressIndicator(modifier = Modifier.size(14.dp), strokeWidth = 2.dp, color = MaterialTheme.colorScheme.primary)
                        Text(if (isEn) "Searching..." else "جاري البحث...", fontSize = 11.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                    }
                }
            } else {
                item {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                        Text(text = if (isEn) "${smartResults.size} result(s) for" else "${smartResults.size} نتيجة لـ", fontSize = 11.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                        Text("\"$debouncedSearchQuery\"", fontSize = 11.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
            if (smartResults.isNotEmpty()) {
                items(smartResults) { r ->
                    val lns = r.lines
                    val lineClr = Color(android.graphics.Color.parseColor("#" + lns.first().dynamicColorHex))
                    Card(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(18.dp)),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Box(modifier = Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(lineClr.copy(alpha = 0.12f)), contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.DirectionsTransit, contentDescription = null, tint = lineClr, modifier = Modifier.size(18.dp))
                                }
                                Column {
                                    Text(text = if (isEn) r.stationNameEn else r.stationNameAr, fontWeight = FontWeight.Black, fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground)
                                    Text(text = "${lns.size} ${if (isEn) "line(s)" else "خط"}", fontSize = 11.sp, color = lineClr)
                                }
                            }
                            FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                lns.distinct().sortedBy { it.mode.ordinal }.forEach { line ->
                                    val lc = Color(android.graphics.Color.parseColor("#" + line.dynamicColorHex))
                                    Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(lc.copy(alpha = 0.1f)).clickable {
                                        viewModel.setActiveMode(line.mode)
                                    }.padding(horizontal = 8.dp, vertical = 4.dp)) {
                                        Text(text = if (isEn) line.nameEn else line.nameAr, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = lc)
                                    }
                                }
                            }
                            val addr = if (isEn) r.stations.first().addressEn else r.stations.first().addressAr
                            if (addr.isNotEmpty()) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Icon(Icons.Default.Place, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f), modifier = Modifier.size(12.dp))
                                    Text(addr, fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f), maxLines = 1, overflow = TextOverflow.Ellipsis)
                                }
                            }
                        }
                    }
                }
            } else if (debouncedSearchQuery.length >= 2 && !showLoading) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(imageVector = Icons.Default.SearchOff, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f), modifier = Modifier.size(48.dp))
                            Text(text = if (isEn) "No results for \"$debouncedSearchQuery\"" else "لا توجد نتائج لـ \"$debouncedSearchQuery\"", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                            Text(text = if (isEn) "Try a shorter name or check spelling" else "جرب اسماً أقصر أو تأكد من الإملاء", fontSize = 11.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f))
                        }
                    }
                }
            }
        }

        // Line 4 under construction notice (METRO mode)
        if (activeMode == TransitMode.METRO && !MonorailData.isLineOpen(TransportLine.METRO_LINE_4)) {
            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF9E9E9E).copy(alpha = 0.10f)),
                    border = BorderStroke(1.dp, Color(0xFF9E9E9E).copy(alpha = 0.4f))) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Icon(Icons.Default.Build, contentDescription = null, tint = Color(0xFF9E9E9E), modifier = Modifier.size(24.dp))
                        Column {
                            Text(text = if (isEn) "Line 4 Under Construction" else "الخط الرابع تحت الإنشاء",
                                fontWeight = FontWeight.Black, fontSize = 13.sp, color = Color(0xFF9E9E9E))
                            Text(text = if (isEn) "Metro Line 4 stations are coming soon and not yet available for trip planning." else "محطات الخط الرابع قادمة قريباً وغير متاحة لتخطيط الرحلات حالياً.",
                                fontSize = 11.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f))
                        }
                    }
                }
            }
        }

        // West Nile under construction notice (MONORAIL mode)
        if (activeMode == TransitMode.MONORAIL && !MonorailData.isLineOpen(TransportLine.WEST_NILE)) {
            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF9E9E9E).copy(alpha = 0.10f)),
                    border = BorderStroke(1.dp, Color(0xFF9E9E9E).copy(alpha = 0.4f))) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Icon(Icons.Default.Build, contentDescription = null, tint = Color(0xFF9E9E9E), modifier = Modifier.size(24.dp))
                        Column {
                            Text(text = if (isEn) "West Nile Under Construction" else "غرب النيل تحت الإنشاء",
                                fontWeight = FontWeight.Black, fontSize = 13.sp, color = Color(0xFF9E9E9E))
                            Text(text = if (isEn) "West Nile Monorail stations are coming soon and not yet available for trip planning." else "محطات غرب النيل قادمة قريباً وغير متاحة لتخطيط الرحلات حالياً.",
                                fontSize = 11.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f))
                        }
                    }
                }
            }
        }

        // Bus Route Browser (BUS_AUTHORITY / BUS_MINI)
        if (activeMode == TransitMode.BUS_AUTHORITY || activeMode == TransitMode.BUS_MINI) {
            val busRoutes = TransportLine.values().filter { it.mode == activeMode }
            val filteredRoutes = if (busSearchQuery.isEmpty()) busRoutes
                else busRoutes.filter {
                    val normalizedQuery = busSearchQuery.replace(" ", "").lowercase()
                        .replace("[أإآ]".toRegex(), "ا")
                        .replace("ة", "ه")
                        .replace("ى", "ي")
                    val normalizedAr = it.nameAr.replace(" ", "").lowercase()
                        .replace("[أإآ]".toRegex(), "ا")
                        .replace("ة", "ه")
                        .replace("ى", "ي")
                    val normalizedEn = it.nameEn.replace(" ", "").lowercase()
                    normalizedAr.contains(normalizedQuery) || normalizedEn.contains(normalizedQuery)
                }

            if (busSelectedRoute == null) {
                // Step 1: Route selector with search bar
                item {
                    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(28.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                                    Icon(imageVector = getModeIcon(activeMode), contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
                                }
                                Column {
                                    Text(text = if (isEn) activeMode.nameEn else activeMode.nameAr, fontWeight = FontWeight.Black, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                                    Text(text = if (isEn) "Select a route from the list" else "اختر خط سير من القائمة", fontSize = 11.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                }
                            }
                            // Search field
                            TextField(value = busSearchQuery, onValueChange = { busSearchQuery = it; busDropdownExpanded = true },
                                placeholder = { Text(text = if (isEn) "Search route number..." else "ابحث عن رقم الخط...", fontSize = 12.sp) },
                                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp)) },
                                trailingIcon = {
                                    if (busSearchQuery.isNotEmpty()) {
                                        IconButton(onClick = { busSearchQuery = "" }) { Icon(Icons.Default.Clear, contentDescription = "Clear") }
                                    } else {
                                        IconButton(onClick = { busDropdownExpanded = !busDropdownExpanded }) {
                                            Icon(if (busDropdownExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore, contentDescription = "Toggle", tint = MaterialTheme.colorScheme.primary)
                                        }
                                    }
                                },
                                singleLine = true, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp),
                                colors = TextFieldDefaults.colors(focusedContainerColor = MaterialTheme.colorScheme.surface, unfocusedContainerColor = MaterialTheme.colorScheme.surface, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent))
                        }
                    }
                }

                // Step 2: Dropdown items as separate lazy items in the parent LazyColumn
                if (busDropdownExpanded) {
                    if (filteredRoutes.isEmpty()) {
                        item {
                            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(MaterialTheme.colorScheme.surface).padding(16.dp), contentAlignment = Alignment.Center) {
                                  Text(text = if (isEn) "No routes found" else "لا توجد خطوط", fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                            }
                        }
                    } else {
                        items(filteredRoutes) { route ->
                            val routeClr = Color(android.graphics.Color.parseColor("#" + route.dynamicColorHex))
                            Card(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).clickable { busSelectedRoute = route; busDropdownExpanded = false },
                                border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                        Box(modifier = Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(routeClr.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                                            Text(text = getLineShortName(route, isEn), fontWeight = FontWeight.Black, fontSize = 10.sp, color = routeClr)
                                        }
                                        Column {
                                            Text(text = getLineShortName(route, isEn), fontWeight = FontWeight.Black, fontSize = 13.sp, color = MaterialTheme.colorScheme.onBackground)
                                            val cnt = MonorailData.allStations.filter { it.line == route }.sortedBy { it.sequentialNumber }.distinctBy { if (isEn) it.nameEn else it.nameAr }.size
                                            Text(text = if (isEn) "$cnt stops" else "$cnt محطة", fontSize = 10.sp, color = routeClr)
                                        }
                                    }
                                    Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f), modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            } else {
                // Route details - shown directly
                val route = busSelectedRoute!!
                val routeClr = Color(android.graphics.Color.parseColor("#" + route.dynamicColorHex))
                val routeStations = MonorailData.allStations.filter { it.line == route }.sortedBy { it.sequentialNumber }.distinctBy { if (isEn) it.nameEn else it.nameAr }
                val fare = when (activeMode) {
                    TransitMode.BUS_AUTHORITY -> {
                        if (route.nameAr.contains("مكيف") || route.nameEn.contains("AC")) MonorailData.fareCtaAc.toInt()
                        else MonorailData.fareCtaNormal.toInt()
                    }
                    TransitMode.BUS_MINI -> {
                        if (route.nameAr.contains("مكيف") || route.nameEn.contains("AC")) MonorailData.fareMiniAc.toInt()
                        else MonorailData.fareMiniNormal.toInt()
                    }
                    else -> when {
                        routeStations.size <= 9 -> MonorailData.farePerZone1.toInt()
                        routeStations.size <= 16 -> MonorailData.farePerZone2.toInt()
                        routeStations.size <= 23 -> MonorailData.farePerZone3.toInt()
                        else -> MonorailData.farePerZone4.toInt()
                    }
                }
                val totalMinutes = routeStations.size * 2.5
                val timeText = if (totalMinutes >= 60) "${(totalMinutes / 60).toInt()}h ${(totalMinutes % 60).toInt()}min" else "${totalMinutes.toInt()} min"

                // Summary card with header + change button
                item {
                    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp),
                        border = BorderStroke(1.5.dp, routeClr.copy(alpha = 0.6f)),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Box(modifier = Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(routeClr.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                                        Icon(imageVector = getModeIcon(activeMode), contentDescription = null, tint = routeClr, modifier = Modifier.size(22.dp))
                                    }
                                    Column {
                                        Text(text = getLineShortName(route, isEn), fontWeight = FontWeight.Black, fontSize = 16.sp, color = routeClr)
                                    }
                                }
                                Box(modifier = Modifier.clip(RoundedCornerShape(10.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)).clickable { busSelectedRoute = null; busSearchQuery = ""; busDropdownExpanded = true }.padding(horizontal = 10.dp, vertical = 6.dp)) {
                                    Text(text = if (isEn) "Change" else "تغيير", fontSize = 10.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                                }
                            }
                            HorizontalDivider(color = routeClr.copy(alpha = 0.2f))
                            // 3 stats: Stops, Fare, Time
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(14.dp)).background(routeClr.copy(alpha = 0.08f)).padding(12.dp), contentAlignment = Alignment.Center) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(Icons.Default.DirectionsBus, contentDescription = null, tint = routeClr, modifier = Modifier.size(20.dp))
                                        Text(text = if (isEn) "Stops" else "محطات", fontSize = 9.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                        Text(text = "${routeStations.size}", fontWeight = FontWeight.Black, fontSize = 18.sp, color = routeClr)
                                    }
                                }
                                Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(14.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)).padding(12.dp), contentAlignment = Alignment.Center) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(Icons.Default.Payments, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                                        Text(text = if (isEn) "Fare" else "الأجرة", fontSize = 9.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                        Text(text = "$fare EGP", fontWeight = FontWeight.Black, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                                    }
                                }
                                Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(14.dp)).background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f)).padding(12.dp), contentAlignment = Alignment.Center) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(Icons.Default.AccessTime, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground, modifier = Modifier.size(20.dp))
                                        Text(text = if (isEn) "Time" else "الوقت", fontSize = 9.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                        Text(text = timeText, fontWeight = FontWeight.Black, fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)
                                    }
                                }
                            }
                        }
                    }
                }

                // Show Route Details button (ad-gated)
                if (!showBusDetails) {
                    item {
                        val ctx = LocalContext.current
                        Button(onClick = {
                            val activity = ctx.findActivity()
                            if (activity != null) {
                                if (adsRemoved) showBusDetails = true
                                else MediationManager.showInterstitialAd(activity) { showBusDetails = true }
                            } else showBusDetails = true
                        }, modifier = Modifier.fillMaxWidth().height(56.dp).padding(vertical = 4.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)) {
                            Text(text = if (isEn) "Show Route Details ➔" else "إظهار تفاصيل الخط ➔", fontSize = 15.sp, fontWeight = FontWeight.Black)
                        }
                    }
                } else {
                    // Stations list header
                    item {
                        Text(text = if (isEn) "Route Stations" else "محطات الخط", fontWeight = FontWeight.Black, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(start = 4.dp))
                    }
                    if (routeStations.isEmpty()) {
                        item {
                            Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(MaterialTheme.colorScheme.surface).padding(24.dp), contentAlignment = Alignment.Center) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(Icons.Default.SearchOff, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f), modifier = Modifier.size(48.dp))
                                    Text(text = if (isEn) "No station data available yet" else "لا توجد بيانات محطات متاحة بعد", fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f), modifier = Modifier.padding(top = 8.dp))
                                }
                            }
                        }
                    } else {
                        items(routeStations) { station ->
                            val stClr = if (station.dynamicStatus == StationStatus.ACTIVE) {
                                Color(android.graphics.Color.parseColor("#" + station.dynamicColorHex))
                            } else {
                                Color(0xFF9E9E9E)
                            }
                            val isTerminus = station == routeStations.first() || station == routeStations.last()
                        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = if (isTerminus) routeClr.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surface),
                            border = BorderStroke(0.5.dp, stClr.copy(alpha = if (isTerminus) 0.4f else 0.15f))) {
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Box(modifier = Modifier.size(30.dp).clip(CircleShape).background(if (isTerminus) stClr else stClr.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                                    Text(text = "${station.sequentialNumber}", fontWeight = FontWeight.Black, fontSize = 10.sp, color = if (isTerminus) MaterialTheme.colorScheme.surface else stClr)
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = if (isEn) station.nameEn else station.nameAr, fontWeight = if (isTerminus) FontWeight.Black else FontWeight.Bold, fontSize = if (isTerminus) 14.sp else 12.sp, color = MaterialTheme.colorScheme.onBackground)
                                    Text(text = if (isEn) station.addressEn else station.addressAr, fontSize = 9.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                                }
                            }
                        }
                    }
                }
            }
            }
        } else if (activeMode == TransitMode.RIDE_HAILING) {
            val companies = MonorailData.rideHailingCompanies
            val displayCompanies = if (companies.isNotEmpty()) companies
                else listOf(
                    MonorailData.RideCompany("uber", "أوبر", "Uber", MonorailData.uberPricePerKm, "19901", "",
                        listOf("تجنب أوقات الذروة للحصول على أسعار أقل", "استخدم خاصية مشاركة الرحلة مع الأصدقاء لتوفير المال", "تأكد من وجود رصيد كافٍ في محفظتك قبل الطلب", "قم بتقييم السائق لتحسين جودة الخدمة", "استخدم كود الخصم للرحلات الأولى"),
                        listOf("Avoid peak hours for lower prices", "Use the split fare feature with friends", "Make sure you have enough balance in your wallet", "Rate your driver to improve service quality", "Use promo codes for your first rides")),
                    MonorailData.RideCompany("careem", "كريم", "Careem", MonorailData.careemPricePerKm, "16677", "",
                        listOf("احجز مسبقًا للرحلات المهمة لضمان التوفر", "استخدم عروض الاشتراك الشهري لتوفير المال", "تابع العروض والتخفيضات في التطبيق", "اختر نوع السيارة المناسب لاحتياجاتك", "شارك رمز الدعوة مع أصدقائك للحصول على خصومات"),
                        listOf("Book in advance for important trips", "Use monthly subscription plans to save money", "Check the app for ongoing promotions", "Choose the right car type for your needs", "Share your referral code with friends for discounts")),
                    MonorailData.RideCompany("didi", "ديدي", "Didi", MonorailData.didiPricePerKm, "19910", "",
                        listOf("استفد من الخصومات اليومية على التطبيق", "جرب خاصية الرحلة المشتركة لتوفير المال", "تأكد من صحة وجهتك قبل تأكيد الرحلة", "استخدم المحفظة الإلكترونية للدفع السريع", "تابع نقاط الولاء للحصول على مكافآت"),
                        listOf("Take advantage of daily discounts on the app", "Try the shared ride feature to save money", "Double-check your destination before confirming", "Use the e-wallet for quick payments", "Track your loyalty points for rewards")),
                    MonorailData.RideCompany("indrive", "ان درايف", "InDrive", MonorailData.inDrivePricePerKm, "19966", "",
                        listOf("حدد سعرك المناسب قبل الموافقة على الرحلة", "لا تتردد في التفاوض على السعر مع السائق", "تأكد من أن السعر المتفق عليه نهائي قبل الانطلاق", "استخدم خاصية التقييم لاختيار أفضل السائقين", "قم بدفع الأجرة نقدًا أو إلكترونيًا حسب راحتك"),
                        listOf("Set your preferred price before accepting the ride", "Don't hesitate to negotiate the fare with the driver", "Make sure the agreed price is final before starting", "Use the rating feature to choose the best drivers", "Pay in cash or electronically as you prefer")),
                    MonorailData.RideCompany("bolt", "بولت", "Bolt", MonorailData.boltPricePerKm, "19967", "",
                        listOf("استخدم Bolt لرحلات قصيرة للحصول على أفضل قيمة", "جرب خاصية Safer للحصول على رحلة آمنة", "شارك رمز الدعوة مع الأصدقاء للحصول على رحلات مجانية", "تأكد من تفعيل خاصية تتبع الرحلة", "استخدم الدفع الإلكتروني لتوفير الوقت"),
                        listOf("Use Bolt for short trips for the best value", "Try the Safer feature for a safer ride", "Share your referral code for free rides", "Enable ride tracking for safety", "Use electronic payment to save time"))
                )

            item {
                val context = LocalContext.current
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(28.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))) {
                    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        Icon(Icons.Default.LocalTaxi, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
                        Text(text = if (isEn) "Ride Hailing Calculator" else "حاسبة النقل الذكي", fontWeight = FontWeight.Black, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                        Text(text = if (isEn) "Enter the distance and select a company to estimate the fare" else "أدخل المسافة واختر الشركة لتقدير الأجرة", fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f))

                        // Distance input with unit toggle
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            OutlinedTextField(value = rideDistanceText, onValueChange = { rideDistanceText = it; rideCalculatedCost = null },
                                label = { Text(if (isEn) "Distance" else "المسافة") },
                                modifier = Modifier.weight(1f), shape = RoundedCornerShape(16.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                singleLine = true)
                            Box(modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)).clickable {
                                rideUnitKm = !rideUnitKm; rideCalculatedCost = null
                            }.padding(horizontal = 14.dp, vertical = 12.dp)) {
                                Text(text = if (rideUnitKm) "KM" else "M", fontWeight = FontWeight.Black, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                            }
                        }

                        // Company selector
                        Box(modifier = Modifier.fillMaxWidth().clickable { rideCompanyDropdownExpanded = !rideCompanyDropdownExpanded }) {
                            OutlinedTextField(value = rideSelectedCompany?.let { if (isEn) it.nameEn else it.nameAr } ?: "", onValueChange = {},
                                readOnly = true,
                                label = { Text(if (isEn) "Company" else "الشركة") },
                                placeholder = { Text(if (isEn) "Select a company..." else "اختر شركة...") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                trailingIcon = { IconButton(onClick = { rideCompanyDropdownExpanded = !rideCompanyDropdownExpanded }) {
                                    Icon(if (rideCompanyDropdownExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore, contentDescription = null)
                                } },
                                singleLine = true)
                            DropdownMenu(expanded = rideCompanyDropdownExpanded, onDismissRequest = { rideCompanyDropdownExpanded = false },
                                modifier = Modifier.fillMaxWidth(0.9f)) {
                                val displayCompanies = displayCompanies
                                displayCompanies.forEach { company ->
                                    DropdownMenuItem(text = {
                                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                            Box(modifier = Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)), contentAlignment = Alignment.Center) {
                                                Text(text = company.nameEn.take(2).uppercase(), fontWeight = FontWeight.Black, fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                                            }
                                            Column {
                                                Text(text = if (isEn) company.nameEn else company.nameAr, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onBackground)
                                                Text(text = "${"%.0f".format(company.pricePerKm)} EGP/km", fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                                            }
                                        }
                                    }, onClick = { rideSelectedCompany = company; rideCompanyDropdownExpanded = false; rideCalculatedCost = null })
                                }
                            }
                        }

                        // Calculate button - ad gated
                        Button(onClick = {
                            val dist = rideDistanceText.toDoubleOrNull()
                            if (dist != null && rideSelectedCompany != null) {
                                val km = if (rideUnitKm) dist else dist / 1000.0
                                rideCalculatedCost = rideSelectedCompany!!.pricePerKm * km
                                val activity = context.findActivity()
                                if (activity != null) {
                                    if (adsRemoved) showRideDetails = true
                                    else MediationManager.showInterstitialAd(activity) { showRideDetails = true }
                                } else showRideDetails = true
                            }
                        }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
                            enabled = rideDistanceText.toDoubleOrNull() != null && rideSelectedCompany != null,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                            Text(text = if (isEn) "Calculate" else "احسب", fontWeight = FontWeight.Black)
                        }
                    }
                }
            }
            // Full details after ad
            if (showRideDetails && rideCalculatedCost != null && rideSelectedCompany != null) {
                val selComp = rideSelectedCompany!!
                val km = if (rideUnitKm) rideDistanceText.toDoubleOrNull() ?: 0.0 else (rideDistanceText.toDoubleOrNull() ?: 0.0) / 1000.0
                val displayCompanies = displayCompanies
                val allCosts = displayCompanies.map { it to it.pricePerKm * km }.sortedBy { it.second }
                val minFare = allCosts.first().second.toInt()
                val maxFare = allCosts.last().second.toInt()
                item {
                    val context = LocalContext.current
                    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            // Header
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)), contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.LocalTaxi, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
                                }
                                Column {
                                    Text(text = if (isEn) "Ride Hailing Fare Comparison" else "مقارنة أسعار النقل الذكي", fontWeight = FontWeight.Black, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                                    Text(text = if (isEn) "Fare range: $minFare - $maxFare EGP" else "نطاق السعر: $minFare - $maxFare EGP", fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                                }
                            }
                            // All companies comparison
                            Text(text = if (isEn) "All Companies" else "جميع الشركات", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                            allCosts.forEachIndexed { idx, (comp, cost) ->
                                val isSelected = comp.id == selComp.id
                                Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(
                                    if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.08f) else Color.Transparent
                                ).padding(horizontal = 10.dp, vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = "${idx + 1}.", fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f))
                                        Text(text = if (isEn) comp.nameEn else comp.nameAr, fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium, fontSize = 13.sp, color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground)
                                    }
                                    Text(text = "${"%.0f".format(cost)} EGP", fontWeight = FontWeight.Black, fontSize = 13.sp, color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f))
                                }
                                if (idx < allCosts.size - 1) HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
                            }
                            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                            // Selected company breakdown
                            Text(text = if (isEn) "Breakdown for ${selComp.nameEn}" else "تفاصيل ${selComp.nameAr}", fontWeight = FontWeight.Black, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                Text(text = if (isEn) "Distance" else "المسافة", fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                Text(text = "${"%.2f".format(km)} km", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground)
                            }
                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                Text(text = if (isEn) "Price per km" else "السعر لكل كم", fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                Text(text = "${"%.0f".format(selComp.pricePerKm)} EGP", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground)
                            }
                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                Text(text = if (isEn) "Estimated Fare" else "الأجرة التقريبية", fontWeight = FontWeight.Black, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                                Text(text = "${"%.0f".format(rideCalculatedCost!!)} EGP", fontWeight = FontWeight.Black, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
                            }
                            // Company tips
                            val tips = if (isEn) selComp.tipsEn else selComp.tipsAr
                            if (tips.isNotEmpty()) {
                                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                                tips.forEachIndexed { tIdx, tip ->
                                    Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Color(0xFFFFC107).copy(alpha = 0.08f)).padding(10.dp), verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(18.dp))
                                        Text(text = "${tIdx + 1}. $tip", fontSize = 11.sp, color = MaterialTheme.colorScheme.onBackground)
                                    }
                                }
                            }
                            // Phone
                            if (selComp.phone.isNotEmpty()) {
                                Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surface).clickable {
                                    try { context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${selComp.phone}"))) } catch (_: Exception) {}
                                }.padding(10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Icon(Icons.Default.Phone, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(20.dp))
                                    Column {
                                        Text(text = if (isEn) "Customer Support" else "خدمة العملاء", fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                                        Text(text = selComp.phone, fontWeight = FontWeight.Black, fontSize = 13.sp, color = MaterialTheme.colorScheme.onBackground)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Stats Cards (only for Metro, Monorail, LRT, BRT - 2x2 grid)
        if (activeMode in listOf(TransitMode.METRO, TransitMode.MONORAIL, TransitMode.LRT, TransitMode.BRT)) {
            item {
                val activeLines = TransportLine.values().filter { it.mode == activeMode && it != TransportLine.METRO_LINE_3_UNIV }.take(4)
                if (activeLines.isNotEmpty()) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        for (rowIndex in 0..1) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                for (col in 0..1) {
                                    val idx = rowIndex * 2 + col
                                    if (idx < activeLines.size) {
                                        val line = activeLines[idx]
                                        val isOpen = MonorailData.isLineOpen(line)
                                        val lineClr = Color(android.graphics.Color.parseColor("#" + line.dynamicColorHex))
                                        val lineStations = MonorailData.allStations.filter { it.line == line }
                                        Card(modifier = Modifier.weight(1f).height(96.dp), shape = RoundedCornerShape(20.dp),
                                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
                                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                                            Column(modifier = Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.SpaceBetween) {
                                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.weight(1f)) {
                                                        Text(text = getLineShortName(line, isEn), fontSize = 10.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f), maxLines = 1, overflow = TextOverflow.Ellipsis)
                                                        if (!isOpen) {
                                                            Box(modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(Color(0xFF9E9E9E).copy(alpha = 0.2f)).padding(horizontal = 4.dp, vertical = 1.dp)) {
                                                                Text(text = if (isEn) "Under const." else "تحت الإنشاء", fontSize = 6.sp, fontWeight = FontWeight.Black, color = Color(0xFF9E9E9E))
                                                            }
                                                        }
                                                    }
                                                    Box(modifier = Modifier.size(26.dp).clip(RoundedCornerShape(10.dp)).background(lineClr.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                                                        Icon(imageVector = getModeIcon(activeMode), contentDescription = null, tint = if (isOpen) lineClr else Color(0xFF9E9E9E), modifier = Modifier.size(14.dp))
                                                    }
                                                }
                                                Column {
                                                    Text(text = if (isEn) "${lineStations.size} Stations" else "${lineStations.size} محطة", fontSize = 14.sp, fontWeight = FontWeight.Black, color = if (isOpen) MaterialTheme.colorScheme.onBackground else Color(0xFF9E9E9E))
                                                }
                                            }
                                        }
                                    } else {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (activeMode == TransitMode.SUPER_JET) {
            item {
                Text(text = if (isEn) "Select Destination" else "اختر الوجهة", fontWeight = FontWeight.Black, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(start = 4.dp, top = 4.dp))
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Card(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp)).clickable { showSJSourceDestPicker = true },
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                                Box(modifier = Modifier.size(46.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                                    Icon(imageVector = Icons.Default.RadioButtonChecked, contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
                                }
                                Column {
                                    Text(text = if (isEn) "From" else "من", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                                    val sjSrcDest = MonorailData.superjetDestinations.find { it.id == sjSourceDest }
                                    Text(text = sjSrcDest?.let { if (isEn) it.nameEn else it.nameAr } ?: (if (isEn) "Select departure..." else "اختر وجهة الانطلاق..."),
                                        fontSize = 14.sp, fontWeight = FontWeight.Black,
                                        color = if (sjSrcDest != null) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f))
                                }
                            }
                            Icon(imageVector = if (isEn) Icons.Default.ChevronRight else Icons.Default.ChevronLeft, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f), modifier = Modifier.size(20.dp))
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        IconButton(onClick = { viewModel.swapSjDestinations() },
                            modifier = Modifier.size(42.dp).border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f), CircleShape).background(MaterialTheme.colorScheme.surface)) {
                            Icon(imageVector = Icons.Default.SwapVert, contentDescription = "Swap", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        }
                    }
                    Card(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp)).clickable { showSJDestDestPicker = true },
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                                Box(modifier = Modifier.size(46.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                                    Icon(imageVector = Icons.Default.Place, contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
                                }
                                Column {
                                    Text(text = if (isEn) "To" else "إلى", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                                    val sjDstDest = MonorailData.superjetDestinations.find { it.id == sjDestDest }
                                    Text(text = sjDstDest?.let { if (isEn) it.nameEn else it.nameAr } ?: (if (isEn) "Select destination..." else "اختر وجهة الوصول..."),
                                        fontSize = 14.sp, fontWeight = FontWeight.Black,
                                        color = if (sjDstDest != null) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f))
                                }
                            }
                            Icon(imageVector = if (isEn) Icons.Default.ChevronRight else Icons.Default.ChevronLeft, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f), modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }
        } else if (activeMode != TransitMode.RIDE_HAILING && activeMode != TransitMode.BUS_AUTHORITY && activeMode != TransitMode.BUS_MINI) {
            item {
                Text(text = if (isEn) "Select Stations" else "اختر المحطات", fontWeight = FontWeight.Black, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(start = 4.dp, top = 4.dp))
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Card(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp)).clickable { showSourceSelector = true },
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                                Box(modifier = Modifier.size(46.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                                    Icon(imageVector = Icons.Default.RadioButtonChecked, contentDescription = null,
                                        tint = if (sourceStation != null) Color(android.graphics.Color.parseColor("#" + sourceStation.dynamicColorHex)) else MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
                                }
                                Column {
                                    Text(text = if (isEn) "From" else "من", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                                    Text(text = sourceStation?.let { if (isEn) it.nameEn else it.nameAr } ?: (if (isEn) "Select departure..." else "اختر محطة الانطلاق..."),
                                        fontSize = 14.sp, fontWeight = FontWeight.Black,
                                        color = if (sourceStation != null) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f))
                                }
                            }
                            Icon(imageVector = if (isEn) Icons.Default.ChevronRight else Icons.Default.ChevronLeft, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f), modifier = Modifier.size(20.dp))
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        IconButton(onClick = { viewModel.swapStations() }, modifier = Modifier.size(42.dp).border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f), CircleShape).background(MaterialTheme.colorScheme.surface)) {
                            Icon(imageVector = Icons.Default.SwapVert, contentDescription = "Swap", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        }
                    }
                    Card(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp)).clickable { showDestSelector = true },
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                                Box(modifier = Modifier.size(46.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                                    Icon(imageVector = Icons.Default.Place, contentDescription = null,
                                        tint = if (destStation != null) Color(android.graphics.Color.parseColor("#" + destStation.dynamicColorHex)) else MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
                                }
                                Column {
                                    Text(text = if (isEn) "To" else "إلى", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                                    Text(text = destStation?.let { if (isEn) it.nameEn else it.nameAr } ?: (if (isEn) "Select destination..." else "اختر محطة الوصول..."),
                                        fontSize = 14.sp, fontWeight = FontWeight.Black,
                                        color = if (destStation != null) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f))
                                }
                            }
                            Icon(imageVector = if (isEn) Icons.Default.ChevronRight else Icons.Default.ChevronLeft, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f), modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }
        }

        // Trains for RAILWAY
        if (activeMode == TransitMode.RAILWAY && sourceStation != null && destStation != null) {
            val trains = viewModel.getAvailableTrains()
            if (trains.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(MaterialTheme.colorScheme.surface).padding(24.dp), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Train, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f), modifier = Modifier.size(48.dp))
                            Text(text = if (isEn) "No trains available for this route" else "لا توجد قطارات متاحة لهذه الوجهة", fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f), modifier = Modifier.padding(top = 8.dp))
                        }
                    }
                }
            } else {
                // Summary card
                item {
                    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))) {
                        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Box(modifier = Modifier.size(32.dp).clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.Train, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                                }
                                Text(text = if (isEn) "Railway Routes" else "رحلات القطار", fontWeight = FontWeight.Black, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                            }
                            val minFare = trains.minOf { it.fare }.toInt()
                            val maxFare = trains.maxOf { it.fare }.toInt()
                            val minDuration = trains.minOf { it.durationMinutes }
                            val maxDuration = trains.maxOf { it.durationMinutes }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "${trains.size}", fontWeight = FontWeight.Black, fontSize = 22.sp, color = MaterialTheme.colorScheme.primary)
                                    Text(text = if (isEn) "Trains" else "قطارات", fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "$minFare-$maxFare EGP", fontWeight = FontWeight.Black, fontSize = 16.sp, color = Color(0xFF4CAF50))
                                    Text(text = if (isEn) "Fare Range" else "نطاق السعر", fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    val minH = minDuration / 60; val minM = minDuration % 60
                                    val maxH = maxDuration / 60; val maxM = maxDuration % 60
                                    val minStr = if (minH > 0) "${minH}h ${minM}min" else "${minM}min"
                                    val maxStr = if (maxH > 0) "${maxH}h ${maxM}min" else "${maxM}min"
                                    Text(text = "$minStr-$maxStr", fontWeight = FontWeight.Black, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                                    Text(text = if (isEn) "Duration" else "المدة", fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                }
                            }
                        }
                    }
                }
                // Ad-gated details
                if (!showRailwayDetails) {
                    item {
                        val context = LocalContext.current
                        Button(onClick = {
                            val activity = context.findActivity()
                            if (activity != null) {
                                if (adsRemoved) showRailwayDetails = true
                                else MediationManager.showInterstitialAd(activity) { showRailwayDetails = true }
                            } else showRailwayDetails = true
                        }, modifier = Modifier.fillMaxWidth().height(56.dp).padding(vertical = 4.dp),
                            shape = RoundedCornerShape(16.dp), elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                            Text(text = if (isEn) "Show Train Details ➔" else "إظهار تفاصيل القطار ➔", fontSize = 15.sp, fontWeight = FontWeight.Black)
                        }
                    }
                } else {
                    item {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(modifier = Modifier.size(32.dp).clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Train, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                            }
                            Text(text = if (isEn) "Available Trains (${trains.size})" else "القطارات المتاحة (${trains.size})", fontWeight = FontWeight.Black, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                    items(trains) { train: Train ->
                    val isExpanded = expandedTrainId == train.id
                    val trainClr = Color(android.graphics.Color.parseColor("#1A5276"))
                    val srcName = if (isEn) sourceStation!!.nameEn else sourceStation!!.nameAr
                    val dstName = if (isEn) destStation!!.nameEn else destStation!!.nameAr

                    Card(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)),
                        border = BorderStroke(1.dp, if (isExpanded) trainClr.copy(alpha = 0.6f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                        colors = CardDefaults.cardColors(containerColor = if (isExpanded) trainClr.copy(alpha = 0.05f) else MaterialTheme.colorScheme.surface)) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(modifier = Modifier.fillMaxWidth().clickable { expandedTrainId = if (isExpanded) null else train.id }, horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Box(modifier = Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(trainClr.copy(alpha = 0.12f)), contentAlignment = Alignment.Center) {
                                        Text(text = train.number, fontWeight = FontWeight.Black, fontSize = 14.sp, color = trainClr)
                                    }
                                    Column {
                                        Text(text = train.number + " " + (if (isEn) train.nameEn else train.nameAr), fontWeight = FontWeight.Black, fontSize = 13.sp, color = MaterialTheme.colorScheme.onBackground)
                                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                            Box(modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(trainClr.copy(alpha = 0.12f)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                                                Text(text = if (isEn) train.type else train.type, fontSize = 8.sp, fontWeight = FontWeight.Bold, color = trainClr)
                                            }
                                            Text(text = if (isEn) train.directionEn else train.directionAr, fontSize = 9.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                        }
                                    }
                                }
                                Icon(imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore, contentDescription = null, tint = trainClr, modifier = Modifier.size(22.dp))
                            }

                            if (isExpanded) {
                                HorizontalDivider(color = trainClr.copy(alpha = 0.2f))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = trainClr, modifier = Modifier.size(18.dp))
                                        Text(text = if (isEn) "Departure" else "الانطلاق", fontSize = 9.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                        Text(text = train.departureTime, fontWeight = FontWeight.Black, fontSize = 16.sp, color = trainClr)
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(Icons.Default.AccessTime, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                                        Text(text = if (isEn) "Duration" else "المدة", fontSize = 9.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                        val h = train.durationMinutes / 60
                                        val m = train.durationMinutes % 60
                                        Text(text = if (h > 0) "${h}h ${m}min" else "${m}min", fontWeight = FontWeight.Black, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(Icons.Default.Payments, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(18.dp))
                                        Text(text = if (isEn) "Fare" else "السعر", fontSize = 9.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                        Text(text = "${train.fare.toInt()} EGP", fontWeight = FontWeight.Black, fontSize = 16.sp, color = Color(0xFF4CAF50))
                                    }
                                }
                                HorizontalDivider(color = trainClr.copy(alpha = 0.2f))
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Icon(Icons.Default.Route, contentDescription = null, tint = trainClr, modifier = Modifier.size(16.dp))
                                    Text(text = (if (isEn) "Stops: $srcName → $dstName" else "المحطات: $srcName ← $dstName"), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                                }
                                val trainStationNames = train.stationIds.mapNotNull { MonorailData.getStationById(it)?.nameAr }
                                val srcIdx = trainStationNames.indexOf(sourceStation!!.nameAr)
                                val dstIdx = trainStationNames.indexOf(destStation!!.nameAr)
                                val stopsBetween = if (srcIdx >= 0 && dstIdx >= 0) kotlin.math.abs(dstIdx - srcIdx) else train.stationIds.size - 1
                                val displayStations = if (srcIdx >= 0 && dstIdx >= 0) {
                                    if (srcIdx < dstIdx) train.stationIds.subList(srcIdx, dstIdx + 1)
                                    else train.stationIds.subList(dstIdx, srcIdx + 1)
                                } else {
                                    train.stationIds
                                }
                                val showStationList = remember { mutableStateOf(false) }
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(text = if (isEn) "Route stations:" else "محطات الطريق:", fontSize = 9.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                                    Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(trainClr.copy(alpha = 0.08f)).clickable { showStationList.value = !showStationList.value }.padding(horizontal = 8.dp, vertical = 4.dp)) {
                                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                            Text(text = "$stopsBetween ${if (isEn) "stops" else "محطات"}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = trainClr)
                                            Icon(imageVector = if (showStationList.value) Icons.Default.ExpandLess else Icons.Default.ExpandMore, contentDescription = null, tint = trainClr, modifier = Modifier.size(14.dp))
                                        }
                                    }
                                }
                                if (showStationList.value) {
                                    Column(modifier = Modifier.heightIn(max = 180.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        displayStations.forEachIndexed { idx, sId ->
                                            val s = MonorailData.getStationById(sId)
                                            val isTerm = s?.nameAr == sourceStation!!.nameAr || s?.nameAr == destStation!!.nameAr
                                            if (s != null) {
                                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(vertical = 2.dp)) {
                                                    Box(modifier = Modifier.size(24.dp).clip(CircleShape).background(if (isTerm) trainClr else trainClr.copy(alpha = 0.12f)), contentAlignment = Alignment.Center) {
                                                        Text(text = "${idx + 1}", fontWeight = FontWeight.Black, fontSize = 9.sp, color = if (isTerm) MaterialTheme.colorScheme.surface else trainClr)
                                                    }
                                                    Text(text = if (isEn) s.nameEn else s.nameAr, fontWeight = if (isTerm) FontWeight.Black else FontWeight.Normal, fontSize = if (isTerm) 12.sp else 11.sp, color = MaterialTheme.colorScheme.onBackground)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            }
        } else if (activeMode == TransitMode.SUPER_JET && sjSourceDest != null && sjDestDest != null) {
            val srcDest = MonorailData.superjetDestinations.find { it.id == sjSourceDest }
            val dstDest = MonorailData.superjetDestinations.find { it.id == sjDestDest }
            val srcName = if (isEn) srcDest?.nameEn else srcDest?.nameAr
            val dstName = if (isEn) dstDest?.nameEn else dstDest?.nameAr
            val sjCompanies = viewModel.getSjCompaniesForDestinations()

            item {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(modifier = Modifier.size(32.dp).clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.AirportShuttle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                    }
                    Text(text = if (isEn) "Super Jet" else "سوبر جيت", fontWeight = FontWeight.Black, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                }
            }

            item {
                Text(text = "$srcName → $dstName", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f), modifier = Modifier.padding(bottom = 4.dp))
            }

            if (sjCompanies.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(MaterialTheme.colorScheme.surface).padding(24.dp), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.AirportShuttle, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f), modifier = Modifier.size(48.dp))
                            Text(text = if (isEn) "No companies available for this route" else "لا توجد شركات متاحة لهذه الوجهة", fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f), modifier = Modifier.padding(top = 8.dp))
                        }
                    }
                }
            } else {
                // Summary card
                item {
                    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))) {
                        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Box(modifier = Modifier.size(32.dp).clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.AirportShuttle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                                }
                                Text(text = if (isEn) "Super Jet Trips" else "رحلات سوبر جيت", fontWeight = FontWeight.Black, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                            }
                            val totalTrips = sjCompanies.sumOf { viewModel.getSjTripsForCompanyAndDestinations(it.name).size }
                            val allFares = sjCompanies.flatMap { viewModel.getSjTripsForCompanyAndDestinations(it.name) }.map { it.fare.toInt() }
                            val minFare = allFares.minOrNull() ?: 0
                            val maxFare = allFares.maxOrNull() ?: 0
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "${sjCompanies.size}", fontWeight = FontWeight.Black, fontSize = 22.sp, color = MaterialTheme.colorScheme.primary)
                                    Text(text = if (isEn) "Companies" else "شركات", fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "$totalTrips", fontWeight = FontWeight.Black, fontSize = 22.sp, color = Color(0xFF4CAF50))
                                    Text(text = if (isEn) "Trips" else "رحلات", fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "$minFare-$maxFare EGP", fontWeight = FontWeight.Black, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                                    Text(text = if (isEn) "Fare Range" else "نطاق السعر", fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                }
                            }
                        }
                    }
                }
                // Ad-gated details
                if (!showSjDetails) {
                    item {
                        val context = LocalContext.current
                        Button(onClick = {
                            val activity = context.findActivity()
                            if (activity != null) {
                                if (adsRemoved) showSjDetails = true
                                else MediationManager.showInterstitialAd(activity) { showSjDetails = true }
                            } else showSjDetails = true
                        }, modifier = Modifier.fillMaxWidth().height(56.dp).padding(vertical = 4.dp),
                            shape = RoundedCornerShape(16.dp), elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                            Text(text = if (isEn) "Show Trip Details ➔" else "إظهار تفاصيل الرحلات ➔", fontSize = 15.sp, fontWeight = FontWeight.Black)
                        }
                    }
                } else {
                    items(sjCompanies) { company ->
                        val sjClr = Color(android.graphics.Color.parseColor("#" + company.dynamicColorHex))
                        val isExpanded = expandedSJCompanyId == company.name
                        val trips = viewModel.getSjTripsForCompanyAndDestinations(company.name)

                        Card(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)),
                            border = BorderStroke(1.dp, if (isExpanded) sjClr.copy(alpha = 0.5f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                            colors = CardDefaults.cardColors(containerColor = if (isExpanded) sjClr.copy(alpha = 0.05f) else MaterialTheme.colorScheme.surface)) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Row(modifier = Modifier.fillMaxWidth().clickable { expandedSJCompanyId = if (isExpanded) null else company.name }, horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                        Box(modifier = Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(sjClr.copy(alpha = 0.12f)), contentAlignment = Alignment.Center) {
                                            Icon(Icons.Default.AirportShuttle, contentDescription = null, tint = sjClr, modifier = Modifier.size(22.dp))
                                        }
                                        Column {
                                            Text(text = if (isEn) company.nameEn else company.nameAr, fontWeight = FontWeight.Black, fontSize = 13.sp, color = MaterialTheme.colorScheme.onBackground)
                                            Text(text = "${trips.size} ${if (isEn) "trips" else "رحلات"}", fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                        }
                                    }
                                    Icon(imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore, contentDescription = null, tint = sjClr, modifier = Modifier.size(22.dp))
                                }

                                if (isExpanded) {
                                    trips.forEach { trip ->
                                        HorizontalDivider(color = sjClr.copy(alpha = 0.2f))
                                        val depTerm = if (isEn) trip.sourceTerminalEn.ifEmpty { trip.sourceId } else trip.sourceTerminalAr.ifEmpty { trip.sourceId }
                                        val arrTerm = if (isEn) trip.destTerminalEn.ifEmpty { trip.destId } else trip.destTerminalAr.ifEmpty { trip.destId }
                                        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.DepartureBoard, contentDescription = null, tint = sjClr, modifier = Modifier.size(16.dp))
                                            Text(text = if (isEn) "From: $depTerm" else "من: $depTerm", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f))
                                        }
                                        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.Place, contentDescription = null, tint = Color(0xFFE53935), modifier = Modifier.size(16.dp))
                                            Text(text = if (isEn) "To: $arrTerm" else "إلى: $arrTerm", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f))
                                        }
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                Text(text = trip.departureTime, fontWeight = FontWeight.Black, fontSize = 18.sp, color = sjClr)
                                                Text(text = if (isEn) "Departure" else "الانطلاق", fontSize = 9.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                            }
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                Icon(Icons.Default.AccessTime, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                                                Text(text = if (isEn) "Duration" else "المدة", fontSize = 9.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                                val h = trip.durationMinutes / 60
                                                val m = trip.durationMinutes % 60
                                                Text(text = if (h > 0) "${h}h ${m}min" else "${m}min", fontWeight = FontWeight.Black, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                                            }
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                Text(text = trip.arrivalTime, fontWeight = FontWeight.Black, fontSize = 18.sp, color = sjClr)
                                                Text(text = if (isEn) "Arrival" else "الوصول", fontSize = 9.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                                            }
                                        }
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.Payments, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(16.dp))
                                            Text(text = " ${trip.fare.toInt()} EGP", fontWeight = FontWeight.Black, fontSize = 14.sp, color = Color(0xFF4CAF50))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (journeyPlan != null) {
            item {
                val saved = savedPlaces.any { it.type == "trip" && it.sourceStationId == journeyPlan.source.id && it.destStationId == journeyPlan.destination.id }
                JourneyCard(plan = journeyPlan, isEn = isEn, isTripSaved = saved,
                    onSaveTrip = { viewModel.saveTrip(journeyPlan, isEn) })
            }
            if (!showDetails) {
                item {
                    val context = LocalContext.current
                    Button(onClick = {
                        val activity = context.findActivity()
                        if (activity != null) {
                            if (adsRemoved) showDetails = true
                            else com.example.ui.components.MediationManager.showInterstitialAd(activity) { showDetails = true }
                        } else showDetails = true
                    }, modifier = Modifier.fillMaxWidth().height(56.dp).padding(vertical = 4.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = if (isSystemInDarkTheme()) Color(0xFF0C1014) else Color(0xFFFFFFFF)),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)) {
                        Text(text = if (isEn) "Show Journey Details ➔" else "إظهار تفاصيل الرحلة ➔", fontSize = 15.sp, fontWeight = FontWeight.Black)
                    }
                }
            } else {
                if (journeyPlan.interchangeNeeded) {
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            journeyPlan.interchangeSteps.forEach { step ->
                                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
                                    border = BorderStroke(1.dp, Color(0xFFFF9800).copy(alpha = 0.5f))) {
                                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                        Icon(Icons.Default.DirectionsTransit, contentDescription = null, tint = Color(0xFFFF9800), modifier = Modifier.size(24.dp))
                                        Column {
                                            Text(text = "${if (isEn) step.stationNameEn else step.stationNameAr} - ${if (isEn) "Interchange" else "محطة تبادلية"}",
                                                fontWeight = FontWeight.Black, fontSize = 14.sp, color = Color(0xFF795548))
                                            Text(text = if (isEn) "Get off ${step.fromLine.nameEn} and board ${step.toLine.nameEn}"
                                                else "انزل من ${step.fromLine.nameAr} واركب ${step.toLine.nameAr}",
                                                fontSize = 12.sp, color = Color(0xFF795548).copy(alpha = 0.8f))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                item {
                    Text(text = if (isEn) "Trip Route Stops" else "محطات الرحلة", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 8.dp, bottom = 4.dp))
                }
                val interchangeStationIds = journeyPlan.interchangeSteps.map { it.stationId }.toSet()
                items(journeyPlan.path) { station ->
                    RouteStationItem(station = station, isEn = isEn, isTerminus = station.id == journeyPlan.source.id || station.id == journeyPlan.destination.id,
                        isInterchange = station.id in interchangeStationIds,
                        nextLineName = journeyPlan.interchangeSteps.find { it.stationId == station.id }?.let { if (isEn) it.toLine.nameEn else it.toLine.nameAr },
                        viewModel = viewModel)
                }
            }
        } else if (favoriteRoutes.isNotEmpty() && activeMode != TransitMode.RIDE_HAILING && activeMode != TransitMode.BUS_AUTHORITY && activeMode != TransitMode.BUS_MINI) {
            item {
                Text(text = if (isEn) "Favorite Commutes" else "الرحلات المفضلة", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 8.dp))
            }
            items(favoriteRoutes) { (src, dest) ->
                Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(MaterialTheme.colorScheme.surface).clickable { viewModel.setSourceStation(src); viewModel.setDestStation(dest) }
                    .border(0.8.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(16.dp)).padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Star, contentDescription = "Fav", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(text = if (isEn) "${src.nameEn} ➔ ${dest.nameEn}" else "${src.nameAr} ➔ ${dest.nameAr}", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                        }
                        Icon(imageVector = if (isEn) Icons.Default.ChevronRight else Icons.Default.ChevronLeft, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f))
                    }
                }
            }
        }
    }

    if (showSourceSelector && activeMode != TransitMode.BUS_AUTHORITY && activeMode != TransitMode.BUS_MINI) {
        StationSelectorDialog(title = if (isEn) "Choose Departure" else "اختر محطة الانطلاق", stations = MonorailData.allStations.filter { it.line.mode == activeMode }, isEn = isEn,
            onDismiss = { showSourceSelector = false }, onSelect = { viewModel.setSourceStation(it); showSourceSelector = false })
    }
    if (showDestSelector && activeMode != TransitMode.BUS_AUTHORITY && activeMode != TransitMode.BUS_MINI) {
        StationSelectorDialog(title = if (isEn) "Choose Destination" else "اختر محطة الوصول", stations = MonorailData.allStations.filter { it.line.mode == activeMode }, isEn = isEn,
            onDismiss = { showDestSelector = false }, onSelect = { viewModel.setDestStation(it); showDestSelector = false })
    }
    if (showSJSourceDestPicker) {
        DestinationPickerDialog(title = if (isEn) "Choose Departure" else "اختر وجهة الانطلاق", isEn = isEn,
            onDismiss = { showSJSourceDestPicker = false },
            onSelect = { viewModel.setSjSourceDest(it); showSJSourceDestPicker = false })
    }
    if (showSJDestDestPicker) {
        DestinationPickerDialog(title = if (isEn) "Choose Destination" else "اختر وجهة الوصول", isEn = isEn,
            selectedDest = sjSourceDest,
            onDismiss = { showSJDestDestPicker = false },
            onSelect = { viewModel.setSjDestDest(it); showSJDestDestPicker = false })
    }
}

// ==================== TAB 1: STATIONS DIRECTORY ====================
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StationsDirectoryView(viewModel: MonorailViewModel, isEn: Boolean) {
    val isDark = isSystemInDarkTheme()
    var selectedMode by remember { mutableStateOf<TransitMode?>(null) }
    var selectedLine by remember { mutableStateOf<TransportLine?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Header
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(28.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))) {
            Row(modifier = Modifier.fillMaxWidth().background(
                Brush.linearGradient(colors = listOf(MaterialTheme.colorScheme.primary.copy(alpha = 0.18f), MaterialTheme.colorScheme.surface))
            ).padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(text = if (isEn) "Stations Directory" else "دليل المحطات", fontWeight = FontWeight.Black, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                    Text(text = if (isEn) "All stations with addresses" else "جميع المحطات مع العناوين", fontSize = 11.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                }
                if (selectedLine != null) {
                    Box(modifier = Modifier.clip(RoundedCornerShape(10.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)).clickable { selectedLine = null; selectedMode = null }.padding(horizontal = 10.dp, vertical = 6.dp)) {
                        Text(text = if (isEn) "Back to Modes" else "العودة للوسائل", fontSize = 10.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }

        // Mode Selector (only when no mode selected)
        if (selectedMode == null) {
            val transportRows = listOf(
                listOf(TransitMode.METRO, TransitMode.MONORAIL),
                listOf(TransitMode.LRT, TransitMode.RAILWAY),
                listOf(TransitMode.BRT, TransitMode.SUPER_JET)
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                for (row in transportRows) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        row.forEach { mode ->
                            val isActive = selectedMode == mode
                            ModeCard(
                                mode = mode, isActive = isActive, isEn = isEn,
                                modifier = Modifier.weight(1f),
                                onClick = { selectedMode = if (isActive) null else mode; selectedLine = null }
                            )
                        }
                    }
                }
                // Full-width Microbus card
                val isActive = selectedMode == TransitMode.MICROBUS
                ModeCard(
                    mode = TransitMode.MICROBUS, isActive = isActive, isEn = isEn,
                    modifier = Modifier.fillMaxWidth().height(54.dp),
                    label = if (isEn) "Microbus (Terminals)" else "ميكروباص ( المواقف )",
                    onClick = { selectedMode = if (isActive) null else TransitMode.MICROBUS; selectedLine = null }
                )
            }
        }

        // Content area
        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            val lines = selectedMode?.let { selected -> TransportLine.values().filter { l -> l.mode == selected && l != TransportLine.METRO_LINE_3_UNIV } } ?: emptyList()

            if (selectedMode == null) {
                // Initial empty state
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(imageVector = Icons.Default.Train, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f), modifier = Modifier.size(56.dp))
                        Text(text = if (isEn) "Select a transport mode" else "اختر وسيلة مواصلات", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                    }
                }
            } else if (selectedLine == null) {
                // Show lines for selected mode
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(lines) { line ->
                        val isOpen = MonorailData.isLineOpen(line)
                        val lineClr = Color(android.graphics.Color.parseColor("#" + line.dynamicColorHex))
                        val stationCount = MonorailData.allStations.count { it.line == line }
                        Card(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).clickable { selectedLine = line },
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                            Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                                    Box(modifier = Modifier.size(48.dp).clip(RoundedCornerShape(14.dp)).background(lineClr.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                                        Icon(imageVector = getModeIcon(selectedMode!!), contentDescription = null, tint = if (isOpen) lineClr else Color(0xFF9E9E9E), modifier = Modifier.size(24.dp))
                                    }
                                    Column {
                                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                            Text(text = if (isEn) line.nameEn else line.nameAr, fontWeight = FontWeight.Black, fontSize = 14.sp, color = if (isOpen) MaterialTheme.colorScheme.onBackground else Color(0xFF9E9E9E))
                                            if (!isOpen) {
                                                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(0xFF9E9E9E).copy(alpha = 0.5f)))
                                            }
                                        }
                                        Text(text = if (isEn) "$stationCount stations" else "$stationCount محطة", fontSize = 11.sp, color = if (isOpen) lineClr else Color(0xFF9E9E9E))
                                    }
                                }
                                Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f), modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                }
            } else {
                // Show stations for selected line with addresses
                val stations = MonorailData.allStations.filter { it.line == selectedLine }
                    .sortedBy { it.sequentialNumber }

                if (stations.isNotEmpty()) {
                    val lineClr = Color(android.graphics.Color.parseColor("#" + selectedLine!!.dynamicColorHex))
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        itemsIndexed(stations) { index, station ->
                            val isOpen = station.dynamicStatus == StationStatus.ACTIVE
                            val stColor = if (isOpen) Color(android.graphics.Color.parseColor("#" + station.dynamicColorHex)) else Color(0xFF9E9E9E)
                            Card(modifier = Modifier.fillMaxWidth().clickable { viewModel.selectStation(station) }, shape = RoundedCornerShape(20.dp),
                                border = BorderStroke(1.dp, stColor.copy(alpha = if (isOpen) 0.5f else 0.2f)),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                            Box(modifier = Modifier.size(32.dp).clip(CircleShape).background(stColor.copy(alpha = 1f)), contentAlignment = Alignment.Center) {
                                                Text(text = "${station.sequentialNumber}", fontWeight = FontWeight.Black, fontSize = 11.sp, color = Color.White)
                                            }
                                            Column {
                                                Text(text = if (isEn) station.nameEn else station.nameAr, fontWeight = FontWeight.Black, fontSize = 15.sp, color = if (isOpen) MaterialTheme.colorScheme.onBackground else Color(0xFF9E9E9E))
                                                    if (!isOpen) {
                                                    Text(text = if (isEn) "Under Construction" else "تحت الإنشاء", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF9E9E9E))
                                                }
                                            }
                                        }
                                        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = "Details", tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f), modifier = Modifier.size(16.dp))
                                    }
                                    Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Icon(Icons.Default.Place, contentDescription = null, tint = stColor, modifier = Modifier.size(16.dp).padding(top = 2.dp))
                                        Text(text = if (isEn) station.addressEn else station.addressAr, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f), overflow = TextOverflow.Ellipsis, maxLines = 2)
                                    }
                                    if (station.connectionAr != null) {
                                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                            Icon(Icons.Default.DirectionsTransit, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(14.dp))
                                            Text(text = if (isEn) station.connectionEn ?: "" else station.connectionAr, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                                        }
                                    }
                                    if (station.landmarksAr.isNotEmpty()) {
                                        FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                            val list = if (isEn) station.landmarksEn else station.landmarksAr
                                            list.take(3).forEach { landmark ->
                                                Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(stColor.copy(alpha = 0.08f)).padding(horizontal = 10.dp, vertical = 4.dp)) {
                                                    Text(landmark, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = stColor)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if ((index + 1) % 6 == 0) { Spacer(modifier = Modifier.height(10.dp)); NativeAdCard(isEn = isEn) }
                        }
                    }
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(imageVector = Icons.Default.SearchOff, contentDescription = "No results", tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f), modifier = Modifier.size(56.dp))
                            Text(text = if (isEn) "No stations found" else "لا توجد محطات", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                        }
                    }
                }
            }
        }
    }
}

// ==================== TAB 2: SAVED PLACES ====================
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SavedPlacesView(viewModel: MonorailViewModel, isEn: Boolean, savedPlaces: List<SavedPlace>, adsRemoved: Boolean = false) {
    val context = LocalContext.current
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedPlace by remember { mutableStateOf<SavedPlace?>(null) }
    var selectedSection by remember { mutableStateOf(0) } // 0 = Trips, 1 = Addresses
    var showTripDetail by remember { mutableStateOf<MonorailData.JourneyPlan?>(null) }

    val trips = savedPlaces.filter { it.type == "trip" }
    val addresses = savedPlaces.filter { it.type == "address" }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Header
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(28.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))) {
            Row(modifier = Modifier.fillMaxWidth().background(
                Brush.linearGradient(colors = listOf(MaterialTheme.colorScheme.primary.copy(alpha = 0.18f), MaterialTheme.colorScheme.surface))
            ).padding(18.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(text = if (isEn) "Saved Places" else "الأماكن المحفوظة", fontWeight = FontWeight.Black, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                    Text(text = if (isEn) "${savedPlaces.size} items saved" else "${savedPlaces.size} محفوظ", fontSize = 11.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                }
                Box(modifier = Modifier.size(42.dp).clip(RoundedCornerShape(14.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)).clickable { showAddDialog = true }, contentAlignment = Alignment.Center) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Place", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
                }
            }
        }

        // Section tabs
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(0 to (if (isEn) "Trips" else "الرحلات"), 1 to (if (isEn) "Addresses" else "العناوين")).forEach { (idx, label) ->
                val isActive = selectedSection == idx
                val count = if (idx == 0) trips.size else addresses.size
                Card(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(14.dp)).clickable { selectedSection = idx },
                    border = BorderStroke(if (isActive) 1.8.dp else 1.dp, if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                    colors = CardDefaults.cardColors(containerColor = if (isActive) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface)
                ) {
                    Row(modifier = Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        Text(text = label, fontSize = 13.sp, fontWeight = FontWeight.Black, color = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground)
                        if (count > 0) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(if (isActive) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                                Text(text = "$count", fontSize = 10.sp, fontWeight = FontWeight.Black, color = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                            }
                        }
                    }
                }
            }
        }

        // Content
        val currentList = if (selectedSection == 0) trips else addresses
        if (currentList.isEmpty()) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(
                        imageVector = if (selectedSection == 0) Icons.Default.DirectionsTransit else Icons.Default.BookmarkBorder,
                        contentDescription = null, tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f), modifier = Modifier.size(64.dp)
                    )
                    Text(
                        text = if (selectedSection == 0) {
                            if (isEn) "No saved trips yet" else "لا توجد رحلات محفوظة"
                        } else {
                            if (isEn) "No saved addresses yet" else "لا توجد عناوين محفوظة"
                        },
                        fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                    if (selectedSection == 1) {
                        Text(text = if (isEn) "Tap + to add a location" else "اضغط + لإضافة مكان", fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f))
                    } else {
                        Text(text = if (isEn) "Save a trip by tapping ★ in route summary" else "احفظ رحلة بالضغط على ★ في ملخص الرحلة", fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f))
                    }
                }
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(currentList) { place ->
                    if (place.type == "trip") {
                        TripCard(place = place, isEn = isEn, onDelete = { viewModel.removePlace(place.id) },
                            onClick = {
                                val srcId = place.sourceStationId
                                val dstId = place.destStationId
                                if (srcId != null && dstId != null) {
                                    val src = MonorailData.getStationById(srcId)
                                    val dst = MonorailData.getStationById(dstId)
                                    if (src != null && dst != null && src.line.mode == dst.line.mode) {
                                        val plan = MonorailData.planJourney(srcId, dstId, src.line.mode)
                                        if (plan != null) {
                                            val activity = context.findActivity()
                                            if (activity != null) {
                                                if (adsRemoved) showTripDetail = plan
                                                else com.example.ui.components.MediationManager.showInterstitialAd(activity) { showTripDetail = plan }
                                            } else showTripDetail = plan
                                        }
                                    }
                                }
                            })
                    } else {
                        AddressCard(place = place, isEn = isEn, context = context, onClick = { selectedPlace = place }, onDelete = { viewModel.removePlace(place.id) })
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddPlaceDialog(isEn = isEn, onDismiss = { showAddDialog = false }, onSave = { name, address, notes ->
            viewModel.savePlace(SavedPlace(name = name, address = address, notes = notes))
            showAddDialog = false
        })
    }

    selectedPlace?.let { place ->
        PlaceDetailDialog(place = place, isEn = isEn,
            onDismiss = { selectedPlace = null },
            onUpdate = { newName, newAddress, newNotes ->
                viewModel.updatePlace(place.id, newName, newAddress, newNotes)
                selectedPlace = null
            },
            onDelete = {
                viewModel.removePlace(place.id)
                selectedPlace = null
            }
        )
    }

    showTripDetail?.let { plan ->
        SavedTripDetailDialog(plan = plan, isEn = isEn, onDismiss = { showTripDetail = null })
    }
}

@Composable
private fun TripCard(place: SavedPlace, isEn: Boolean, onDelete: () -> Unit, onClick: () -> Unit = {}) {
    Card(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick), shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(modifier = Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)), contentAlignment = Alignment.Center) {
                    Icon(imageVector = Icons.Default.DirectionsTransit, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
                }
                Column {
                    val tripLabel = if (place.type == "trip") {
                        val src = if (isEn) place.sourceNameEn else place.sourceNameAr
                        val dst = if (isEn) place.destNameEn else place.destNameAr
                        "${src ?: place.name} → ${dst ?: ""}"
                    } else place.name
                    Text(text = tripLabel, fontWeight = FontWeight.Black, fontSize = 13.sp, color = MaterialTheme.colorScheme.onBackground, maxLines = 1)
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        if (place.fare != null) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                Icon(Icons.Default.Payments, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(12.dp))
                                Text(text = "${place.fare.toInt()} EGP", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
                            }
                        }
                        if (place.duration != null) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                Icon(Icons.Default.AccessTime, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f), modifier = Modifier.size(12.dp))
                                Text(text = "~${place.duration} ${if (isEn) "min" else "دقيقة"}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                            }
                        }
                    }
                }
            }
            IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
private fun AddressCard(place: SavedPlace, isEn: Boolean, context: android.content.Context, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick), shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Icon(imageVector = Icons.Default.Place, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                Column {
                    Text(text = place.name, fontWeight = FontWeight.Black, fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground)
                    if (place.address.isNotBlank()) Text(text = place.address, fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                    if (place.notes.isNotBlank()) Text(text = place.notes, fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f), maxLines = 2, overflow = TextOverflow.Ellipsis)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                if (place.latitude != 0.0 || place.longitude != 0.0) {
                    IconButton(onClick = {
                        val uri = "geo:${place.latitude},${place.longitude}?q=${place.latitude},${place.longitude}"
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
                    }, modifier = Modifier.size(32.dp)) { Icon(imageVector = Icons.Default.Directions, contentDescription = "Navigate", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp)) }
                }
                IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) { Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(18.dp)) }
            }
        }
    }
}

@Composable
fun SavedTripDetailDialog(plan: MonorailData.JourneyPlan, isEn: Boolean, onDismiss: () -> Unit) {
    val lineColor = Color(android.graphics.Color.parseColor("#" + plan.source.line.dynamicColorHex))
    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(28.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text(text = if (isEn) "Trip Route" else "خط سير الرحلة", fontWeight = FontWeight.Black, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = "Close", tint = MaterialTheme.colorScheme.onBackground) }
                }

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(lineColor.copy(alpha = 0.1f)).padding(12.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = if (isEn) plan.source.nameEn else plan.source.nameAr, fontWeight = FontWeight.Black, fontSize = 15.sp, color = lineColor)
                        Text(text = "↓", fontSize = 18.sp, fontWeight = FontWeight.Black, color = lineColor, modifier = Modifier.padding(vertical = 2.dp))
                        Text(text = if (isEn) plan.destination.nameEn else plan.destination.nameAr, fontWeight = FontWeight.Black, fontSize = 15.sp, color = lineColor)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "${plan.ticketPrice.toInt()} EGP", fontWeight = FontWeight.Black, fontSize = 20.sp, color = lineColor)
                        Text(text = "~${plan.approxDurationMinutes} ${if (isEn) "min" else "دقيقة"}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                    }
                }

                Text(text = if (isEn) "Route Stations" else "محطات الرحلة", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground)

                if (plan.interchangeNeeded && plan.interchangeSteps.isNotEmpty()) {
                    plan.interchangeSteps.forEach { step ->
                        Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.06f)).padding(10.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(Icons.Default.TransferWithinAStation, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                                Text(text = if (isEn) "Switch at ${step.stationNameEn}" else "تبديل في ${step.stationNameAr}", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }

                LazyColumn(modifier = Modifier.heightIn(max = 300.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    items(plan.path) { station ->
                        val stColor = Color(android.graphics.Color.parseColor("#" + station.dynamicColorHex))
                        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(stColor))
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(text = if (isEn) station.nameEn else station.nameAr, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                                Text(text = if (isEn) station.line.nameEn else station.line.nameAr, fontSize = 9.sp, color = stColor)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddPlaceDialog(isEn: Boolean, onDismiss: () -> Unit, onSave: (String, String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(28.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text(text = if (isEn) "Add a Place" else "إضافة مكان", fontWeight = FontWeight.Black, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text(if (isEn) "Place Name" else "اسم المكان") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp))
                OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text(if (isEn) "Address" else "العنوان") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp))
                OutlinedTextField(value = notes, onValueChange = { notes = it }, label = { Text(if (isEn) "Notes" else "ملاحظات") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), minLines = 2)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f), shape = RoundedCornerShape(16.dp)) { Text(if (isEn) "Cancel" else "إلغاء") }
                    Button(onClick = {
                        if (name.isNotBlank()) onSave(name, address, notes)
                    }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(16.dp)) { Text(if (isEn) "Save" else "حفظ") }
                }
            }
        }
    }
}

@Composable
fun PlaceDetailDialog(place: SavedPlace, isEn: Boolean, onDismiss: () -> Unit, onUpdate: (String, String, String) -> Unit, onDelete: () -> Unit) {
    var editing by remember { mutableStateOf(false) }
    var tempName by remember { mutableStateOf(place.name) }
    var tempAddress by remember { mutableStateOf(place.address) }
    var tempNotes by remember { mutableStateOf(place.notes) }

    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(28.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(imageVector = Icons.Default.Place, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
                    if (editing) {
                        OutlinedTextField(value = tempName, onValueChange = { tempName = it },
                            label = { Text(if (isEn) "Name" else "الاسم") },
                            modifier = Modifier.weight(1f), shape = RoundedCornerShape(16.dp))
                    } else {
                        Text(text = place.name, fontWeight = FontWeight.Black, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
                    }
                }

                if (editing) {
                    OutlinedTextField(value = tempAddress, onValueChange = { tempAddress = it },
                        label = { Text(if (isEn) "Address" else "العنوان") },
                        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp))
                    OutlinedTextField(value = tempNotes, onValueChange = { tempNotes = it },
                        label = { Text(if (isEn) "Notes" else "ملاحظات") },
                        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), minLines = 2)
                } else {
                    if (place.address.isNotBlank()) {
                        Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f), modifier = Modifier.size(18.dp).offset(y = 2.dp))
                            Text(text = place.address, fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground)
                        }
                    }
                    if (place.notes.isNotBlank()) {
                        Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f), modifier = Modifier.size(18.dp).offset(y = 2.dp))
                            Text(text = place.notes, fontSize = 13.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f))
                        }
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (editing) {
                        Button(onClick = {
                            if (tempName.isNotBlank()) {
                                onUpdate(tempName, tempAddress, tempNotes)
                            }
                        }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(16.dp)) {
                            Text(if (isEn) "Save" else "حفظ")
                        }
                        OutlinedButton(onClick = {
                            tempName = place.name
                            tempAddress = place.address
                            tempNotes = place.notes
                            editing = false
                        }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(16.dp)) {
                            Text(if (isEn) "Cancel" else "إلغاء")
                        }
                    } else {
                        OutlinedButton(onClick = { editing = true }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(16.dp)) {
                            Text(if (isEn) "Edit" else "تعديل")
                        }
                        OutlinedButton(onClick = onDelete, modifier = Modifier.weight(1f), shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)) {
                            Text(if (isEn) "Delete" else "حذف")
                        }
                        Button(onClick = onDismiss, modifier = Modifier.weight(1f), shape = RoundedCornerShape(16.dp)) {
                            Text(if (isEn) "Close" else "إغلاق")
                        }
                    }
                }
            }
        }
    }
}

// ==================== TAB 3: ABOUT ====================
@Composable
fun AboutView(isEn: Boolean, adsRemoved: Boolean = false, deviceId: String = "", onVerifyCode: ((String) -> Boolean)? = null) {
    val context = LocalContext.current
    val isDark = isSystemInDarkTheme()
    var showCodeDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        // Donate via InstaPay (direct link)
        item {
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, Color(0xFFFFC107).copy(alpha = 0.5f))) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(Color(0xFFFFC107).copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                            Icon(imageVector = Icons.Default.Favorite, contentDescription = "Support", tint = Color(0xFFFFC107), modifier = Modifier.size(18.dp))
                        }
                        Text(text = if (isEn) "Support Development" else "ادعم تطوير التطبيق", fontWeight = FontWeight.Black, fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground)
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Button(
                        onClick = {
                            try { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://ipn.eg/S/smartos/instapay/1urkgN"))) } catch (_: Exception) {}
                        },
                        modifier = Modifier.fillMaxWidth().height(36.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107), contentColor = if (isDark) Color(0xFF0C1014) else Color.White)
                    ) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = if (isEn) "Support us via InstaPay ★" else "ادعمنا عبر إنستا باي ★", fontWeight = FontWeight.Black, fontSize = 12.sp)
                    }
                }
            }
        }

        // Support via Ad (separate square)
        item {
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                            Icon(imageVector = Icons.Default.PlayCircle, contentDescription = "Ad", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                        }
                        Text(text = if (isEn) "Support with an Ad" else "ادعمنا بمشاهدة إعلان", fontWeight = FontWeight.Black, fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground)
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    val context2 = context
                    Button(
                        onClick = {
                            val activity = context2.findActivity()
                            if (activity != null) {
                                val rewardedShown = com.example.ui.components.MediationManager.showRewardedAd(activity) {
                                    Toast.makeText(context, if (isEn) "Thanks for your support!" else "شكراً لدعمك!", Toast.LENGTH_SHORT).show()
                                }
                                if (!rewardedShown) {
                                    Toast.makeText(context, if (isEn) "Loading ad..." else "جاري تحميل الإعلان...", Toast.LENGTH_SHORT).show()
                                    val interstitialShown = com.example.ui.components.MediationManager.showInterstitialAd(activity) {
                                        Toast.makeText(context, if (isEn) "Thanks for your support!" else "شكراً لدعمك!", Toast.LENGTH_SHORT).show()
                                    }
                                    if (!interstitialShown) {
                                        Toast.makeText(context, if (isEn) "Please try again later" else "حاول مرة أخرى", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                if (!rewardedShown) {
                                    val interstitialShown = com.example.ui.components.MediationManager.showInterstitialAd(activity) {
                                        Toast.makeText(context2, if (isEn) "Thank you for your support!" else "شكراً لدعمك!", Toast.LENGTH_SHORT).show()
                                    }
                                    if (!interstitialShown) {
                                        Toast.makeText(context2, if (isEn) "Loading ad, please wait..." else "جاري تحميل الإعلان، انتظر قليلاً...", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(36.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = if (isEn) "Watch Ad ★" else "شاهد إعلاناً ★", fontWeight = FontWeight.Black, fontSize = 12.sp)
                    }
                }
            }
        }

        // Remove Ads
        item {
            if (adsRemoved) {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50).copy(alpha = 0.08f)),
                    border = BorderStroke(1.dp, Color(0xFF4CAF50).copy(alpha = 0.5f))) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(22.dp))
                        Text(text = if (isEn) "Ads Removed ✓" else "تم إخفاء الإعلانات ✓", fontWeight = FontWeight.Black, fontSize = 14.sp, color = Color(0xFF4CAF50))
                    }
                }
            } else {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.5f))) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(MaterialTheme.colorScheme.error.copy(alpha = 0.12f)), contentAlignment = Alignment.Center) {
                                Icon(imageVector = Icons.Default.VisibilityOff, contentDescription = "Hide Ads", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(18.dp))
                            }
                            Text(text = if (isEn) "Remove Ads" else "إخفاء الإعلانات", fontWeight = FontWeight.Black, fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground)
                            Spacer(modifier = Modifier.weight(1f))
                        }
                        Button(
                            onClick = { showCodeDialog = true },
                            modifier = Modifier.fillMaxWidth().height(36.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = if (isEn) "Activate Code" else "إدخال كود التفعيل", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                        
                        // Show device ID
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = if (isEn) "Device ID" else "معرّف الجهاز",
                                    fontSize = 9.sp,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                                )
                                Text(
                                    text = deviceId,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            IconButton(onClick = {
                                val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                                val clip = android.content.ClipData.newPlainText("Device ID", deviceId)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, if (isEn) "Copied!" else "تم النسخ!", Toast.LENGTH_SHORT).show()
                            }, modifier = Modifier.size(32.dp)) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "Copy",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Quick Actions
        item {
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = if (isEn) "Quick Actions" else "إجراءات سريعة", fontWeight = FontWeight.Black, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)

                    Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).clickable {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${context.packageName}"))
                            intent.setPackage("com.android.vending")
                            context.startActivity(intent)
                        } catch (_: Exception) {
                            try { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=${context.packageName}"))) } catch (_: Exception) {}
                        }
                    }.padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(24.dp))
                        Column(modifier = Modifier.weight(1f)) { Text(text = if (isEn) "Rate the App" else "قيم التطبيق", fontWeight = FontWeight.Black, fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground); Text(text = if (isEn) "On Google Play" else "على جوجل بلاي", fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)) }
                        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f))
                    }
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                    Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).clickable {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply { type = "text/plain"; putExtra(Intent.EXTRA_TEXT, "Egypt Transport Guide - دليل المواصلات في مصر\nhttps://play.google.com/store/apps/details?id=${context.packageName}") }
                        context.startActivity(Intent.createChooser(shareIntent, "Share"))
                    }.padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                        Column(modifier = Modifier.weight(1f)) { Text(text = if (isEn) "Share the App" else "شارك التطبيق", fontWeight = FontWeight.Black, fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground); Text(text = if (isEn) "With friends & family" else "مع الأصدقاء والعائلة", fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)) }
                        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f))
                    }
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                    Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).clickable {
                        val intent = Intent(Intent.ACTION_SENDTO).apply { data = Uri.parse("mailto:smartos.help@gmail.com"); putExtra(Intent.EXTRA_SUBJECT, if (isEn) "Egypt Transport Guide Report" else "بلاغ - دليل المواصلات مصر") }
                        try { context.startActivity(intent) } catch (_: Exception) {}
                    }.padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Icon(imageVector = Icons.Default.BugReport, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(24.dp))
                        Column(modifier = Modifier.weight(1f)) { Text(text = if (isEn) "Report an Issue" else "الإبلاغ عن مشكلة", fontWeight = FontWeight.Black, fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground); Text(text = if (isEn) "Send an email" else "إرسال بريد إلكتروني", fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)) }
                        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f))
                    }
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                    Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).clickable {
                        val intent = Intent(Intent.ACTION_SENDTO).apply { data = Uri.parse("mailto:smartos.help@gmail.com"); putExtra(Intent.EXTRA_SUBJECT, if (isEn) "Contact - Egypt Transport Guide" else "تواصل - دليل المواصلات مصر") }
                        try { context.startActivity(intent) } catch (_: Exception) {}
                    }.padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(24.dp))
                        Column(modifier = Modifier.weight(1f)) { Text(text = if (isEn) "Contact Us" else "تواصل معنا", fontWeight = FontWeight.Black, fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground); Text(text = "smartos.help@gmail.com", fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)) }
                        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f))
                    }

                    HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.1f)),
                        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.6f))
                    ) {
                        Text(
                            text = if (isEn) "All transport data is for guidance only. Fares and schedules may change. Always verify with official sources." else "جميع بيانات المواصلات للإرشاد فقط. الأسعار والمواعيد قابلة للتغيير. يُرجى التأكد من المصادر الرسمية.",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }

        // Social Media
        val socialLinks = MonorailData.socialLinks
        if (socialLinks.isNotEmpty()) {
            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))) {
                    Column(modifier = Modifier.padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(text = if (isEn) "Follow Us" else "تابعنا", fontWeight = FontWeight.Black, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            socialLinks.forEach { link ->
                                val icon = when (link.platform.lowercase()) {
                                    "facebook" -> Icons.Default.Facebook
                                    "instagram" -> Icons.Default.PhotoCamera
                                    "tiktok" -> Icons.Default.MusicNote
                                    "youtube" -> Icons.Default.PlayArrow
                                    "threads" -> Icons.Default.AlternateEmail
                                    "x", "twitter" -> Icons.Default.Tag
                                    "telegram" -> Icons.Default.Send
                                    "whatsapp" -> Icons.Default.Chat
                                    else -> Icons.Default.Link
                                }
                                Box(modifier = Modifier.size(44.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)).clickable {
                                    try { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link.url))) } catch (_: Exception) {}
                                }, contentAlignment = Alignment.Center) {
                                    Icon(imageVector = icon, contentDescription = link.platform, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(22.dp))
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            Text(text = if (isEn) "Made with ❤️ in Egypt" else "صنع في مصر ❤️", fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f), textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        }
    }

    if (showCodeDialog) {
        var code by remember { mutableStateOf("") }
        var errorMsg by remember { mutableStateOf<String?>(null) }
        Dialog(onDismissRequest = { showCodeDialog = false; errorMsg = null }) {
            Card(
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Box(modifier = Modifier.size(56.dp).clip(CircleShape).background(MaterialTheme.colorScheme.error.copy(alpha = 0.12f)), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(28.dp))
                    }
                    Text(text = if (isEn) "Activate Code" else "إدخال كود التفعيل", fontWeight = FontWeight.Black, fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)
                    Text(text = if (isEn) "Enter the activation code you received after payment" else "أدخل كود التفعيل الذي استلمته بعد الدفع", fontSize = 13.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f), textAlign = TextAlign.Center)
                    Text(
                        text = (if (isEn) "Device ID: " else "معرّف الجهاز: ") + deviceId,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    OutlinedTextField(
                        value = code,
                        onValueChange = { code = it.uppercase(); errorMsg = null },
                        label = { Text(if (isEn) "Activation Code" else "كود التفعيل") },
                        placeholder = { Text("EGY-XXXXXXXXXX") },
                        singleLine = true,
                        isError = errorMsg != null,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    )
                    if (errorMsg != null) {
                        Text(text = errorMsg!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = {
                            if (onVerifyCode != null && onVerifyCode(code)) {
                                showCodeDialog = false
                                errorMsg = null
                            } else {
                                errorMsg = if (isEn) "Invalid code. Please check and try again." else "كود غير صالح. تأكد من الكود وحاول مرة أخرى."
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) { Text(text = if (isEn) "Verify" else "تأكيد", fontWeight = FontWeight.Bold, fontSize = 15.sp) }
                }
            }
        }
    }
}

// ==================== GLOBAL HELPER MODALS ====================

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StationSelectorDialog(title: String, stations: List<Station>, isEn: Boolean, onDismiss: () -> Unit, onSelect: (Station) -> Unit) {
    var query by remember { mutableStateOf("") }
    var selectedLineFilter by remember { mutableStateOf<TransportLine?>(null) }
    val isRailway = stations.any { it.line.mode == TransitMode.RAILWAY }
    val filtered = stations.filter {
        val matchesText = it.nameAr.contains(query, ignoreCase = true) || it.nameEn.contains(query, ignoreCase = true)
        val matchesFilter = selectedLineFilter == null || 
                it.line == selectedLineFilter || 
                (selectedLineFilter == TransportLine.METRO_LINE_3 && it.line == TransportLine.METRO_LINE_3_UNIV)
        matchesText && matchesFilter
    }.let { list ->
        if (isRailway && selectedLineFilter == null) {
            list.distinctBy { it.nameAr }
        } else {
            list
        }
    }
    Dialog(onDismissRequest = onDismiss) {
        val isDark = MaterialTheme.colorScheme.background == ObsidianDarkBg
        Card(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.85f), shape = RoundedCornerShape(28.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(modifier = Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onDismiss, modifier = Modifier.size(36.dp)) { Icon(Icons.Default.Close, contentDescription = "Close", tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)) }
                    Text(text = title, fontWeight = FontWeight.Black, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurface)
                }
                Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    val dialogLines = remember(stations) {
                        stations.map { it.line }
                            .filter { it != TransportLine.METRO_LINE_3_UNIV }
                            .distinct()
                    }
                    val filters = listOf(Pair<TransportLine?, String>(null, if (isEn) "All" else "الجميع")) + dialogLines.map { Pair<TransportLine?, String>(it, getLineShortName(it, isEn)) }
                    for (filter in filters) {
                        val line = filter.first; val label = filter.second; val isSelected = selectedLineFilter == line
                                val lineClr = line?.let { 
                                    if (MonorailData.isLineOpen(it)) Color(android.graphics.Color.parseColor("#" + it.dynamicColorHex)) 
                                    else Color(0xFF9E9E9E) 
                                } ?: MaterialTheme.colorScheme.primary
                        Box(modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(if (isSelected) lineClr.copy(alpha = 0.15f) else Color.Transparent)
                            .border(1.dp, if (isSelected) Color.Transparent else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                            .clickable { selectedLineFilter = line }.padding(horizontal = 14.dp, vertical = 8.dp)) {
                            Text(text = label, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = if (isSelected) lineClr else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                        }
                    }
                }
                TextField(value = query, onValueChange = { query = it }, placeholder = { Text(text = if (isEn) "Search..." else "ابحث...", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f), modifier = Modifier.size(20.dp)) },
                    singleLine = true, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f), unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f), focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, disabledIndicatorColor = Color.Transparent))
                LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(filtered) { station ->
                                val chipColor = if (station.dynamicStatus == StationStatus.ACTIVE && MonorailData.isLineOpen(station.line)) {
                                    Color(android.graphics.Color.parseColor("#" + station.dynamicColorHex))
                                } else {
                                    Color(0xFF9E9E9E)
                                }
                                 Card(modifier = Modifier.fillMaxWidth().clickable { if (station.dynamicStatus == StationStatus.ACTIVE) onSelect(station) else null }, shape = RoundedCornerShape(16.dp),
                                     border = BorderStroke(1.dp, if (isDark) MaterialTheme.colorScheme.outline.copy(alpha = 0.12f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
                                     colors = CardDefaults.cardColors(containerColor = if (isDark) MaterialTheme.colorScheme.background.copy(alpha = 0.45f) else MaterialTheme.colorScheme.background.copy(alpha = 0.35f))) {
                            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(chipColor.copy(alpha = 0.15f)).padding(horizontal = 10.dp, vertical = 4.dp)) { Text(text = getLineShortName(station.line, isEn), color = chipColor, fontSize = 10.sp, fontWeight = FontWeight.Black) }
                                Text(text = if (isEn) station.nameEn else station.nameAr, fontWeight = FontWeight.Black, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DestinationPickerDialog(title: String, isEn: Boolean, onDismiss: () -> Unit, onSelect: (String) -> Unit, selectedDest: String? = null) {
    val destinations = MonorailData.superjetDestinations.filter { it.id != selectedDest }
    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.6f), shape = RoundedCornerShape(28.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(modifier = Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(text = title, fontWeight = FontWeight.Black, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp).background(MaterialTheme.colorScheme.background, CircleShape)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", modifier = Modifier.size(18.dp))
                    }
                }
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(destinations) { dest ->
                        Card(modifier = Modifier.fillMaxWidth().clickable { onSelect(dest.id) }, shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.35f))) {
                            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)), contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.Place, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                                }
                                Text(text = if (isEn) dest.nameEn else dest.nameAr, fontWeight = FontWeight.Black, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StationDetailBottomSheetDialog(station: Station, isEn: Boolean, onDismiss: () -> Unit) {
    val lineColor = Color(android.graphics.Color.parseColor("#" + station.dynamicColorHex))
    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier.fillMaxWidth().wrapContentHeight(), shape = RoundedCornerShape(24.dp), border = BorderStroke(1.5.dp, lineColor), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(text = if (isEn) station.nameEn else station.nameAr, fontWeight = FontWeight.Black, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
                            Box(modifier = Modifier.clip(CircleShape).background(lineColor.copy(alpha = 0.15f)).padding(horizontal = 8.dp, vertical = 2.dp)) { Text(text = "No. ${station.sequentialNumber}", color = lineColor, fontSize = 10.sp, fontWeight = FontWeight.Black) }
                        }
                        Text(text = if (isEn) station.line.nameEn else station.line.nameAr, fontSize = 11.sp, color = lineColor, fontWeight = FontWeight.Bold)
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.background(MaterialTheme.colorScheme.background, CircleShape)) { Icon(Icons.Default.Close, contentDescription = "Close") }
                }
                Spacer(modifier = Modifier.height(2.dp).fillMaxWidth().background(lineColor))
                Text(text = if (isEn) "Location & Address" else "العنوان والموقع", fontWeight = FontWeight.Black, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(Icons.Default.Place, contentDescription = "Place", tint = lineColor, modifier = Modifier.size(20.dp))
                    Text(text = if (isEn) station.addressEn else station.addressAr, fontSize = 13.sp, lineHeight = 18.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f))
                }
                station.connectionAr?.let { connection ->
                    Text(text = if (isEn) "Connections" else "المواصلات التبادلية", fontWeight = FontWeight.Black, fontSize = 13.sp, color = MaterialTheme.colorScheme.secondary)
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Icon(Icons.Default.DirectionsTransit, contentDescription = "Interchange", tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(20.dp))
                        Text(text = if (isEn) station.connectionEn ?: "" else connection, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                    }
                }
                Text(text = if (isEn) "Nearby Landmarks" else "المعالم القريبة", fontWeight = FontWeight.Black, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    val list = if (isEn) station.landmarksEn else station.landmarksAr
                    list.forEach { item ->
                        Box(modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(lineColor.copy(alpha = 0.08f)).border(0.6.dp, lineColor.copy(alpha = 0.3f), RoundedCornerShape(12.dp)).padding(horizontal = 12.dp, vertical = 6.dp)) {
                            Text(text = item, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                        }
                    }
                }
                Text(text = if (isEn) "Smart Tip" else "نصيحة", fontWeight = FontWeight.Black, fontSize = 13.sp, color = Color(0xFFFFC107))
                Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Color(0xFFFFC107).copy(alpha = 0.08f)).border(0.6.dp, Color(0xFFFFC107).copy(alpha = 0.3f), RoundedCornerShape(12.dp)).padding(12.dp)) {
                    Text(text = if (isEn) station.guideTipEn else station.guideTipAr, fontSize = 12.sp, lineHeight = 17.sp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f))
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

// ==================== JOURNEY CARD ====================
@Composable
fun JourneyCard(plan: MonorailData.JourneyPlan, isEn: Boolean, isTripSaved: Boolean, onSaveTrip: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary)) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Column {
                    Text(text = if (isEn) "Route Summary" else "ملخص الرحلة", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary))
                    Text(text = if (isEn) "Price & stations" else "السعر والمحطات", style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)))
                }
                IconButton(onClick = onSaveTrip) { Icon(imageVector = if (isTripSaved) Icons.Default.Star else Icons.Default.StarBorder, contentDescription = "Save Trip", tint = if (isTripSaved) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f), modifier = Modifier.size(28.dp)) }
            }
            Spacer(modifier = Modifier.height(18.dp))
            val isDark = isSystemInDarkTheme()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(20.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = if (isDark) 0.12f else 0.16f))
                    .border(if (isDark) 1.2.dp else 2.dp, MaterialTheme.colorScheme.primary.copy(alpha = if (isDark) 0.35f else 0.8f), RoundedCornerShape(20.dp)).padding(horizontal = 8.dp, vertical = 14.dp), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Payments, contentDescription = "Price", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(26.dp))
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = if (isEn) "Price" else "السعر", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = "${plan.ticketPrice} EGP", fontWeight = FontWeight.Black, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                    }
                }
                Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(20.dp)).background(MaterialTheme.colorScheme.secondary.copy(alpha = if (isDark) 0.12f else 0.16f))
                    .border(if (isDark) 1.2.dp else 2.dp, MaterialTheme.colorScheme.secondary.copy(alpha = if (isDark) 0.35f else 0.8f), RoundedCornerShape(20.dp)).padding(horizontal = 8.dp, vertical = 14.dp), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.DirectionsTransit, contentDescription = "Stops", tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(26.dp))
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = if (isEn) "Stops" else "محطات", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = "${plan.path.size - 1} ${if (isEn) "stops" else "محطة"}", fontWeight = FontWeight.Black, fontSize = 18.sp, color = MaterialTheme.colorScheme.secondary)
                    }
                }
                Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(20.dp)).background(MaterialTheme.colorScheme.onBackground.copy(alpha = if (isDark) 0.08f else 0.1f))
                    .border(if (isDark) 1.2.dp else 2.dp, MaterialTheme.colorScheme.outline.copy(alpha = if (isDark) 0.4f else 0.8f), RoundedCornerShape(20.dp)).padding(horizontal = 8.dp, vertical = 14.dp), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.AccessTime, contentDescription = "Time", tint = MaterialTheme.colorScheme.onBackground, modifier = Modifier.size(26.dp))
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = if (isEn) "Time" else "الوقت", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = "~${plan.approxDurationMinutes} ${if (isEn) "min" else "دقيقة"}", fontWeight = FontWeight.Black, fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)
                    }
                }
            }
        }
    }
}

@Composable
fun RouteStationItem(station: Station, isEn: Boolean, isTerminus: Boolean, isInterchange: Boolean, viewModel: MonorailViewModel, nextLineName: String? = null) {
    val lineColor = Color(android.graphics.Color.parseColor("#" + station.dynamicColorHex))
    val outlineLineColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
    Row(modifier = Modifier.fillMaxWidth().clickable { viewModel.selectStation(station) }.padding(horizontal = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.width(42.dp).height(56.dp), contentAlignment = Alignment.Center) {
            Spacer(modifier = Modifier.width(4.dp).fillMaxHeight().background(lineColor))
            Spacer(modifier = Modifier.size(if (isTerminus) 22.dp else 14.dp).clip(CircleShape)
                .background(if (isTerminus) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
                .border(if (isTerminus) 3.5.dp else 2.5.dp, if (isTerminus) MaterialTheme.colorScheme.onPrimary else lineColor, CircleShape))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Row(modifier = Modifier.weight(1f).height(56.dp).drawBehind { drawLine(color = outlineLineColor, start = Offset(0f, size.height), end = Offset(size.width, size.height), strokeWidth = 1.dp.toPx()) },
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(text = if (isEn) station.nameEn else station.nameAr, fontWeight = if (isTerminus) FontWeight.Black else FontWeight.Bold, fontSize = if (isTerminus) 15.sp else 13.sp, color = if (isTerminus) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground)
                if (station.connectionAr != null) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.padding(top = 2.dp)) {
                        Icon(Icons.Default.DirectionsTransit, contentDescription = "Transfer", tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(12.dp))
                        Text(text = if (isEn) station.connectionEn ?: "" else station.connectionAr, fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                    }
                }
                if (isInterchange && nextLineName != null) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.padding(top = 2.dp)) {
                        Box(modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(Color(0xFFFF9800).copy(alpha = 0.15f)).border(0.5.dp, Color(0xFFFF9800).copy(alpha = 0.5f), RoundedCornerShape(6.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                            Text(text = if (isEn) "Change to $nextLineName" else "غير للخط $nextLineName",
                                fontSize = 10.sp, color = Color(0xFFFF9800), fontWeight = FontWeight.Black)
                        }
                    }
                }
            }
            Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(lineColor.copy(alpha = 0.15f)).border(0.5.dp, lineColor.copy(alpha = 0.5f), RoundedCornerShape(8.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                Text(text = getLineShortName(station.line, isEn), color = lineColor, fontSize = 10.sp, fontWeight = FontWeight.Black)
            }
        }
    }
}

// ==================== HELPERS ====================

@Composable
fun ModeCard(
    mode: TransitMode, isActive: Boolean, isEn: Boolean,
    modifier: Modifier = Modifier,
    label: String? = null,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.height(54.dp).clip(RoundedCornerShape(14.dp)).clickable(onClick = onClick),
        border = BorderStroke(if (isActive) 1.8.dp else 1.dp, if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
        colors = CardDefaults.cardColors(containerColor = if (isActive) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface)
    ) {
        Row(modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(modifier = Modifier.size(28.dp).clip(RoundedCornerShape(8.dp)).background(if (isActive) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else Color.Transparent), contentAlignment = Alignment.Center) {
                Icon(imageVector = getModeIcon(mode), contentDescription = null, tint = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f), modifier = Modifier.size(18.dp))
            }
            Text(text = label ?: if (isEn) mode.nameEn else mode.nameAr, fontSize = 11.sp, fontWeight = FontWeight.Black, color = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground, maxLines = 1)
        }
    }
}

fun getLineShortName(line: TransportLine, isEn: Boolean): String {
    if (line.mode == TransitMode.BUS_AUTHORITY || line.mode == TransitMode.BUS_MINI) {
        return line.name.substringAfter("BUS_AUTH_CTA_").substringAfter("BUS_MINI_")
    }
    return if (isEn) {
        when (line) {
            TransportLine.METRO_LINE_1 -> "Line 1"
            TransportLine.METRO_LINE_2 -> "Line 2"
            TransportLine.METRO_LINE_3 -> "Line 3"
            TransportLine.METRO_LINE_3_UNIV -> "Line 3"
            TransportLine.METRO_LINE_4 -> "Line 4"
            TransportLine.EAST_NILE -> "East Nile"
            TransportLine.WEST_NILE -> "West Nile"
            TransportLine.LRT_LINE_1 -> "LRT 10th Ramadan"
            TransportLine.LRT_LINE_1_CAPITAL -> "LRT Capital"
            TransportLine.RAILWAY_CAIRO_ALEX -> "Cairo-Alex"
            TransportLine.RAILWAY_CAIRO_LUXOR -> "Cairo-Luxor"
            TransportLine.RAILWAY_CAIRO_ASWAN -> "Cairo-Aswan"
            TransportLine.RAILWAY_BENHA_PORT_SAID -> "Benha-Port Said"
            TransportLine.RAILWAY_CAIRO_MANSOURA -> "Cairo-Mansoura"
            TransportLine.RAILWAY_CAIRO_TANTA -> "Cairo-Tanta"
            TransportLine.BRT_RING_ROAD -> "Ring Road"
            else -> line.nameEn.split(" (")[0]
        }
    } else {
        when (line) {
            TransportLine.METRO_LINE_1 -> "الأول"
            TransportLine.METRO_LINE_2 -> "الثاني"
            TransportLine.METRO_LINE_3 -> "الثالث"
            TransportLine.METRO_LINE_3_UNIV -> "الثالث"
            TransportLine.METRO_LINE_4 -> "الرابع"
            TransportLine.EAST_NILE -> "شرق النيل"
            TransportLine.WEST_NILE -> "غرب النيل"
            TransportLine.LRT_LINE_1 -> "LRT العاشر"
            TransportLine.LRT_LINE_1_CAPITAL -> "LRT العاصمة"
            TransportLine.RAILWAY_CAIRO_ALEX -> "القاهرة-الإسكندرية"
            TransportLine.RAILWAY_CAIRO_LUXOR -> "القاهرة-الأقصر"
            TransportLine.RAILWAY_CAIRO_ASWAN -> "القاهرة-أسوان"
            TransportLine.RAILWAY_BENHA_PORT_SAID -> "بنها-بورسعيد"
            TransportLine.RAILWAY_CAIRO_MANSOURA -> "القاهرة-المنصورة"
            TransportLine.RAILWAY_CAIRO_TANTA -> "القاهرة-طنطا"
            TransportLine.BRT_RING_ROAD -> "BRT"
            else -> line.nameAr.split(" (")[0]
        }
    }
}

fun getModeIcon(mode: TransitMode): ImageVector {
    return when (mode) {
        TransitMode.METRO -> Icons.Default.DirectionsSubway
        TransitMode.MONORAIL -> Icons.Default.DirectionsTransit
        TransitMode.LRT -> Icons.Default.Train
        TransitMode.RAILWAY -> Icons.Default.Train
        TransitMode.BRT -> Icons.Default.DirectionsBus
        TransitMode.BUS_AUTHORITY -> Icons.Default.DirectionsBus
        TransitMode.BUS_MINI -> Icons.Default.DirectionsBus
        TransitMode.SUPER_JET -> Icons.Default.AirportShuttle
        TransitMode.MICROBUS -> Icons.Default.DirectionsBus
        TransitMode.RIDE_HAILING -> Icons.Default.LocalTaxi
    }
}

@Composable
private fun DisclaimerDialog(isEn: Boolean, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(
                    modifier = Modifier.size(56.dp).clip(CircleShape).background(MaterialTheme.colorScheme.error.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(28.dp))
                }
                Text(
                    text = if (isEn) "Disclaimer" else "إخلاء مسؤولية",
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.error
                )
                Text(
                    text = if (isEn)
                        "This app is not affiliated with any government entity. All transport fares, station names, and routes are subject to change. Please verify information with official sources before traveling."
                    else
                        "هذا التطبيق غير تابع لأي جهة حكومية. جميع أسعار المواصلات وأسماء المحطات والخطوط قابلة للتغيير. يُرجى التأكد من المعلومات من المصادر الرسمية قبل السفر.",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(text = if (isEn) "Got it" else "حسناً", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }
        }
    }
}

@Composable
fun NotificationsDialog(
    isEn: Boolean,
    notifications: List<com.example.model.AppNotification>,
    onDismiss: () -> Unit,
    onDelete: (String) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isEn) "Notifications" else "الإشعارات",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                if (notifications.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                                modifier = Modifier.size(64.dp)
                            )
                            Text(
                                text = if (isEn) "No notifications yet" else "لا توجد إشعارات حالياً",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 8.dp)
                    ) {
                        items(notifications) { item ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                                ),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                            ) {
                                Column(
                                    modifier = Modifier.padding(14.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text(
                                            text = if (isEn) item.titleEn else item.titleAr,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.weight(1f)
                                        )
                                        IconButton(
                                            onClick = { onDelete(item.id) },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete",
                                                tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f),
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = if (isEn) item.bodyEn else item.bodyAr,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                                    )
                                    if (item.timestamp.isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = item.timestamp,
                                            fontSize = 10.sp,
                                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                                            textAlign = TextAlign.End,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

