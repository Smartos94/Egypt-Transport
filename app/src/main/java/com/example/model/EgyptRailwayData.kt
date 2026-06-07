package com.example.model

object EgyptRailwayData {

    fun getAllTrains(): List<Train> {
        val trains = mutableListOf<Train>()
        var seq = 1
        fun add(number: String, nameAr: String, nameEn: String, typeAr: String, typeEn: String,
                dirAr: String, dirEn: String, line: TransportLine,
                dep: String, arr: String, durMin: Int, fare: Double,
                stationIds: List<String> = emptyList(),
                noteAr: String = "", noteEn: String = "") {
            val typeStr = if (typeAr == typeEn) typeEn else "$typeEn / $typeAr"
            val finalNameEn = if (noteEn.isNotEmpty()) "$nameEn — $noteEn" else nameEn
            val finalNameAr = if (noteAr.isNotEmpty()) "$nameAr — $noteAr" else nameAr
            trains.add(Train(
                id = "RAIL_TRAIN_$number",
                number = number,
                nameAr = finalNameAr,
                nameEn = finalNameEn,
                lineId = line.name,
                type = typeStr,
                directionAr = dirAr,
                directionEn = dirEn,
                departureTime = dep,
                arrivalTime = arr,
                durationMinutes = durMin,
                fare = fare,
                stationIds = stationIds
            ))
        }

        val alex = TransportLine.RAILWAY_CAIRO_ALEX
        val aswan = TransportLine.RAILWAY_CAIRO_ASWAN
        val luxor = TransportLine.RAILWAY_CAIRO_LUXOR
        val tanta = TransportLine.RAILWAY_CAIRO_TANTA
        val mansoura = TransportLine.RAILWAY_CAIRO_MANSOURA
        val benha_ps = TransportLine.RAILWAY_BENHA_PORT_SAID
        val railway = TransportLine.RAILWAY_CAIRO_TANTA

        add("2025", "تالجو 2025", "Talgo 2025", "تالجو", "Talgo",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "1:00 PM", "3:35 PM", 155, 250.0,
            stationIds = listOf("RAIL_CAIRO_RAMSES", "RAIL_TANTA", "RAIL_DAMANHOUR", "RAIL_ALEX_MISR"),
            noteAr = "أسرع قطار على خط القاهرة - الإسكندرية، خدمة VIP",
            noteEn = "Fastest train Cairo-Alex, VIP service")

        add("2023", "تالجو 2023", "Talgo 2023", "تالجو", "Talgo",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "7:00 PM", "9:35 PM", 155, 250.0,
            noteAr = "مسائي - خدمة VIP", noteEn = "Evening VIP service")

        add("2027", "تالجو 2027", "Talgo 2027", "تالجو", "Talgo",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "11:30 PM", "2:05 AM", 155, 250.0,
            noteAr = "ليلي - مقاعد مريحة", noteEn = "Overnight, comfortable seats")

        add("901", "قطار 901 VIP", "Train 901 VIP", "VIP", "VIP",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "8:10 AM", "11:15 AM", 185, 180.0,
            stationIds = listOf("RAIL_CAIRO_RAMSES", "RAIL_BENHA", "RAIL_TANTA", "RAIL_DAMANHOUR", "RAIL_ALEX_SIDI_GABER", "RAIL_ALEX_MISR"),
            noteAr = "محطات: بنها - طنطا - دمنهور - سيدي جابر",
            noteEn = "Stops: Banha - Tanta - Damanhour - Sidi Gaber")

        add("905", "قطار 905 VIP", "Train 905 VIP", "VIP", "VIP",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "10:00 AM", "1:05 PM", 185, 180.0)

        add("911", "قطار 911 VIP", "Train 911 VIP", "VIP", "VIP",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "1:00 PM", "4:05 PM", 185, 180.0)

        add("917", "قطار 917 VIP", "Train 917 VIP", "VIP", "VIP",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "3:00 PM", "6:05 PM", 185, 180.0)

        add("921", "قطار 921 VIP", "Train 921 VIP", "VIP", "VIP",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "5:00 PM", "8:05 PM", 185, 180.0)

        add("931", "قطار 931 VIP", "Train 931 VIP", "VIP", "VIP",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "8:00 PM", "11:05 PM", 185, 180.0)

        add("935", "قطار 935 VIP", "Train 935 VIP", "VIP", "VIP",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "9:30 PM", "12:35 AM", 185, 180.0)

        add("89", "قطار 89 مكيف", "Train 89 AC", "مكيف", "AC",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "11:15 AM", "2:50 PM", 215, 130.0,
            noteAr = "مكيف إسباني مطور", noteEn = "AC Spanish upgraded")

        add("903", "قطار 903 مكيف", "Train 903 AC", "مكيف إسباني", "AC Spanish",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "6:00 AM", "9:15 AM", 195, 150.0,
            stationIds = listOf("RAIL_CAIRO_RAMSES", "RAIL_BENHA", "RAIL_TANTA", "RAIL_DAMANHOUR", "RAIL_ALEX_MISR"),
            noteAr = "محطات: بنها - طنطا - دمنهور", noteEn = "Stops: Banha - Tanta - Damanhour")

        add("913", "قطار 913 مكيف", "Train 913 AC", "مكيف إسباني", "AC Spanish",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "2:15 PM", "5:30 PM", 195, 150.0)

        add("915", "قطار 915 مكيف", "Train 915 AC", "مكيف فرنساوي", "AC French",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "3:45 PM", "7:00 PM", 195, 150.0)

        add("919", "قطار 919 مكيف", "Train 919 AC", "مكيف إسباني", "AC Spanish",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "3:00 PM", "6:15 PM", 195, 150.0)

        add("923", "قطار 923 مكيف", "Train 923 AC", "مكيف إسباني", "AC Spanish",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "4:00 PM", "7:15 PM", 195, 150.0)

        add("925", "قطار 925 VIP", "Train 925 VIP", "VIP", "VIP",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "10:00 PM", "1:05 AM", 185, 180.0)

        add("927", "قطار 927 AC روسي", "Train 927 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "10:00 PM", "1:35 AM", 215, 130.0)

        add("3007", "قطار 3007 AC روسي", "Train 3007 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "11:00 PM", "2:35 AM", 215, 130.0)

        add("3009", "قطار 3009 AC روسي", "Train 3009 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "7:00 PM", "10:35 PM", 215, 130.0)

        add("3023", "قطار 3023 AC روسي", "Train 3023 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "5:00 PM", "8:35 PM", 215, 130.0)

        add("3025", "قطار 3025 AC روسي", "Train 3025 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "8:00 PM", "11:35 PM", 215, 130.0)

        add("1109", "قطار 1109 AC روسي", "Train 1109 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "7:35 AM", "11:10 AM", 215, 130.0)

        add("1113", "قطار 1113 AC روسي", "Train 1113 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "8:00 AM", "11:35 AM", 215, 130.0)

        add("1131", "قطار 1131 AC روسي", "Train 1131 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "7:00 AM", "10:35 AM", 215, 130.0)

        add("17", "قطار 17 AC روسي", "Train 17 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "9:20 PM", "12:55 AM", 215, 130.0,
            noteAr = "ليلي", noteEn = "Overnight")

        add("15", "قطار 15 روسي", "Train 15 Russian", "روسي", "Russian",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "2:15 PM", "6:20 PM", 245, 75.0)

        add("21", "قطار 21 روسي", "Train 21 Russian", "روسي", "Russian",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "3:30 PM", "7:35 PM", 245, 75.0)

        add("23", "قطار 23 روسي", "Train 23 Russian", "روسي", "Russian",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "4:30 PM", "8:35 PM", 245, 75.0)

        add("29", "قطار 29 روسي", "Train 29 Russian", "روسي", "Russian",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "9:30 PM", "1:35 AM", 245, 75.0)

        add("31", "قطار 31 روسي", "Train 31 Russian", "روسي", "Russian",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "9:00 PM", "1:05 AM", 245, 75.0,
            noteAr = "لا يعمل أيام الجمعة والعطلات", noteEn = "Not operating Fridays and holidays")

        add("35", "قطار 35 روسي", "Train 35 Russian", "روسي", "Russian",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "11:10 PM", "3:15 AM", 245, 75.0,
            noteAr = "آخر قطار", noteEn = "Last train")

        add("1083", "قطار 1083 نوم", "Train 1083 Sleep", "نوم", "Sleep",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "9:00 PM", "1:00 AM", 240, 350.0,
            noteAr = "نوم + مقاعد", noteEn = "Sleeper + seats")

        add("1089", "قطار 1089 نوم", "Train 1089 Sleep", "نوم", "Sleep",
            "القاهرة ← الإسكندرية", "Cairo → Alexandria", alex,
            "10:00 PM", "2:00 AM", 240, 350.0,
            noteAr = "نوم + مقاعد", noteEn = "Sleeper + seats")

        add("2030", "تالجو 2030", "Talgo 2030", "تالجو", "Talgo",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "7:00 PM", "6:40 AM", 700, 700.0,
            stationIds = listOf("RAIL_CAIRO_RAMSES", "RAIL_BENI_SUEF", "RAIL_MINYA", "RAIL_ASYUT", "RAIL_SOHAG", "RAIL_QENA", "RAIL_LUXOR", "RAIL_ASWAN"),
            noteAr = "الأسرع على خط الصعيد - 8h 40m", noteEn = "Fastest on Upper Egypt route - 8h 40m")

        add("2006", "قطار 2006 أبو الهول", "Train 2006 Abu Al-Hol", "VIP", "VIP",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "5:15 PM", "6:00 AM", 765, 500.0,
            noteAr = "خدمة خاصة - 9h 35m", noteEn = "Special service - 9h 35m")

        add("2008", "قطار 2008 VIP", "Train 2008 VIP", "VIP", "VIP",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "8:00 PM", "8:00 AM", 720, 500.0,
            noteAr = "VIP - خدمة كاملة", noteEn = "VIP - full service")

        add("2010", "قطار 2010 VIP", "Train 2010 VIP", "VIP", "VIP",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "7:00 PM", "4:30 AM", 570, 500.0)

        add("2012", "قطار 2012 AC روسي", "Train 2012 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "6:50 PM", "4:35 AM", 585, 400.0)

        add("2014", "قطار 2014 أبو الهول", "Train 2014 Abu Al-Hol", "VIP", "VIP",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "9:00 PM", "6:00 AM", 540, 500.0)

        add("1010", "قطار 1010 AC روسي", "Train 1010 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "12:05 AM", "1:50 PM", 705, 350.0)

        add("1012", "قطار 1012 AC روسي", "Train 1012 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "8:20 PM", "7:00 AM", 645, 350.0)

        add("1014", "قطار 1014 روسي", "Train 1014 Russian", "روسي", "Russian",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "8:20 PM", "9:30 AM", 790, 200.0)

        add("1004", "قطار 1004 AC روسي", "Train 1004 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "8:00 PM", "6:05 AM", 605, 350.0)

        add("1008", "قطار 1008 روسي", "Train 1008 Russian", "روسي", "Russian",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "10:00 PM", "8:00 AM", 600, 200.0)

        add("988", "قطار 988 AC روسي", "Train 988 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "8:00 PM", "5:25 AM", 565, 350.0)

        add("996", "قطار 996 VIP", "Train 996 VIP", "VIP", "VIP",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "10:10 PM", "8:10 AM", 600, 500.0)

        add("986", "قطار 986 AC إسباني", "Train 986 AC Spanish", "مكيف إسباني", "AC Spanish",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "2:00 PM", "12:45 AM", 645, 350.0)

        add("976", "قطار 976 VIP", "Train 976 VIP", "VIP", "VIP",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "8:00 PM", "8:00 AM", 720, 500.0,
            noteAr = "محطات: بني سويف - المنيا - أسيوط - سوهاج - قنا - الأقصر",
            noteEn = "Stops: Beni Suef - Minya - Asyut - Sohag - Qena - Luxor")

        add("980", "قطار 980 VIP", "Train 980 VIP", "VIP", "VIP",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "7:00 PM", "5:40 AM", 640, 500.0)

        add("982", "قطار 982 VIP", "Train 982 VIP", "VIP", "VIP",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "8:00 PM", "6:55 AM", 655, 500.0)

        add("872", "قطار 872 روسي", "Train 872 Russian", "روسي", "Russian",
            "القاهرة ← أسيوط", "Cairo → Asyut", aswan,
            "9:50 PM", "5:30 AM", 460, 150.0)

        add("890", "قطار 890 روسي", "Train 890 Russian", "روسي", "Russian",
            "القاهرة ← أسيوط", "Cairo → Asyut", aswan,
            "10:00 PM", "5:30 AM", 450, 150.0)

        add("80", "قطار 80 روسي", "Train 80 Russian", "روسي", "Russian",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "8:00 AM", "9:10 PM", 790, 200.0)

        add("158", "قطار 158 روسي", "Train 158 Russian", "روسي", "Russian",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "9:00 AM", "8:50 PM", 710, 200.0)

        add("160", "قطار 160 AC روسي", "Train 160 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "8:00 AM", "7:55 PM", 715, 350.0)

        add("162", "قطار 162 روسي", "Train 162 Russian", "روسي", "Russian",
            "القاهرة ← أسيوط", "Cairo → Asyut", aswan,
            "7:30 PM", "2:00 AM", 390, 130.0)

        add("164", "قطار 164 روسي", "Train 164 Russian", "روسي", "Russian",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "12:35 PM", "12:30 AM", 715, 200.0)

        add("188", "قطار 188 روسي", "Train 188 Russian", "روسي", "Russian",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "9:00 PM", "10:05 AM", 785, 200.0)

        add("90", "قطار 90 روسي", "Train 90 Russian", "روسي", "Russian",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "10:00 PM", "9:25 AM", 685, 200.0)

        add("88", "قطار 88 AC إسباني", "Train 88 AC Spanish", "مكيف إسباني", "AC Spanish",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "8:00 AM", "7:15 PM", 675, 350.0)

        add("974", "قطار 974 روسي", "Train 974 Russian", "روسي", "Russian",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "5:20 AM", "5:55 PM", 755, 200.0)

        add("1088", "قطار 1088 نوم", "Train 1088 Sleep", "نوم VIP", "VIP Sleep",
            "القاهرة ← الأقصر", "Cairo → Luxor", luxor,
            "7:30 PM", "6:55 AM", 685, 800.0,
            noteAr = "نوم + مقاعد - أحد وخميس فقط", noteEn = "Sleep + seats - Sun/Thu only")

        add("1090", "قطار 1090 نوم", "Train 1090 Sleep", "نوم", "Sleep",
            "القاهرة ← الأقصر", "Cairo → Luxor", luxor,
            "9:30 PM", "6:15 AM", 525, 700.0,
            noteAr = "Sat/Mon/Wed - خدمة نوم كاملة", noteEn = "Sat/Mon/Wed - full sleeper service")

        add("1092", "قطار 1092 بريميوم", "Train 1092 Premium", "بريميوم + نوم", "Premium + Sleep",
            "القاهرة ← أسوان", "Cairo → Aswan", aswan,
            "7:30 PM", "4:50 AM", 560, 880.0,
            noteAr = "بريميوم - Sat/Mon/Wed", noteEn = "Premium - Sat/Mon/Wed")

        add("1915", "قطار 1915", "Train 1915", "مكيف إسباني + محسن", "AC Spanish + Improved",
            "القاهرة ← المنصورة", "Cairo → Mansoura", mansoura,
            "6:30 AM", "9:00 AM", 150, 90.0,
            noteAr = "لا يعمل أيام الجمعة والعطلات", noteEn = "Not operating Fridays and holidays")

        add("941", "قطار 941 مختلط", "Train 941 Mix", "مختلط", "Mix",
            "القاهرة ← المنصورة", "Cairo → Mansoura", mansoura,
            "5:15 AM", "7:45 AM", 150, 75.0)

        add("965", "قطار 965 مختلط", "Train 965 Mix", "مختلط", "Mix",
            "القاهرة ← المنصورة", "Cairo → Mansoura", mansoura,
            "8:30 AM", "11:00 AM", 150, 75.0)

        add("967", "قطار 967 روسي", "Train 967 Russian", "روسي", "Russian",
            "القاهرة ← المنصورة", "Cairo → Mansoura", mansoura,
            "9:30 AM", "12:00 PM", 150, 50.0)

        add("969", "قطار 969 AC روسي", "Train 969 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← المنصورة", "Cairo → Mansoura", mansoura,
            "7:20 PM", "9:50 PM", 150, 90.0)

        add("519", "قطار 519 AC روسي", "Train 519 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← المنصورة", "Cairo → Mansoura", mansoura,
            "10:00 AM", "12:30 PM", 150, 90.0)

        add("948", "قطار 948 AC روسي", "Train 948 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← المنصورة", "Cairo → Mansoura", mansoura,
            "12:30 PM", "3:00 PM", 150, 90.0)

        add("3015", "قطار 3015 AC روسي", "Train 3015 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← الإسماعيلية", "Cairo → Ismailia", railway,
            "4:50 AM", "6:50 AM", 120, 75.0,
            noteAr = "أسرع قطار للإسماعيلية", noteEn = "Fastest train to Ismailia")

        add("945", "قطار 945 روسي", "Train 945 Russian", "روسي", "Russian",
            "القاهرة ← الإسماعيلية", "Cairo → Ismailia", railway,
            "6:10 AM", "8:30 AM", 140, 35.0,
            noteAr = "الأرخص - 35 جنيه", noteEn = "Cheapest - 35 EGP")

        add("185", "قطار 185 AC روسي", "Train 185 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← بورسعيد", "Cairo → Port Said", benha_ps,
            "9:10 AM", "1:05 PM", 235, 90.0,
            noteAr = "الأسرع - 3h 55m", noteEn = "Fastest - 3h 55m")

        add("951", "قطار 951 روسي", "Train 951 Russian", "روسي", "Russian",
            "القاهرة ← الإسماعيلية", "Cairo → Ismailia", railway,
            "7:30 AM", "10:30 AM", 180, 35.0)

        add("955", "قطار 955 AC روسي", "Train 955 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← بورسعيد", "Cairo → Port Said", benha_ps,
            "2:40 PM", "7:05 PM", 265, 90.0,
            noteAr = "عربة ثالثة مخصصة للقوات المسلحة", noteEn = "Third-class car for army personnel")

        add("959", "قطار 959 AC روسي", "Train 959 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← الإسماعيلية", "Cairo → Ismailia", railway,
            "1:50 PM", "4:30 PM", 160, 75.0)

        add("961", "قطار 961 AC روسي", "Train 961 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← الإسماعيلية", "Cairo → Ismailia", railway,
            "2:40 PM", "5:20 PM", 160, 75.0)

        add("963", "قطار 963 روسي", "Train 963 Russian", "روسي", "Russian",
            "القاهرة ← الإسماعيلية", "Cairo → Ismailia", railway,
            "5:50 PM", "8:30 PM", 160, 35.0)

        add("593", "قطار 593 روسي", "Train 593 Russian", "روسي", "Russian",
            "القاهرة ← السويس", "Cairo → Suez", railway,
            "5:00 AM", "10:00 AM", 300, 65.0,
            noteAr = "القطار الوحيد اليومي - 18 محطة", noteEn = "Only daily train - 18 stops")

        add("594", "قطار 594 روسي", "Train 594 Russian", "روسي", "Russian",
            "السويس ← القاهرة", "Suez → Cairo", railway,
            "3:00 PM", "8:15 PM", 315, 65.0,
            noteAr = "عودة من السويس", noteEn = "Return from Suez")

        add("535", "قطار 535 محسن", "Train 535 Improved", "محسن", "Improved",
            "القاهرة ← طنطا", "Cairo → Tanta", tanta,
            "5:15 AM", "6:50 AM", 95, 35.0)

        add("537", "قطار 537 محسن", "Train 537 Improved", "محسن", "Improved",
            "القاهرة ← طنطا", "Cairo → Tanta", tanta,
            "6:20 AM", "7:55 AM", 95, 35.0)

        add("539", "قطار 539 محسن", "Train 539 Improved", "محسن", "Improved",
            "القاهرة ← طنطا", "Cairo → Tanta", tanta,
            "7:30 AM", "9:05 AM", 95, 35.0)

        add("541", "قطار 541 محسن", "Train 541 Improved", "محسن", "Improved",
            "القاهرة ← طنطا", "Cairo → Tanta", tanta,
            "8:30 AM", "10:05 AM", 95, 35.0)

        add("543", "قطار 543 محسن", "Train 543 Improved", "محسن", "Improved",
            "القاهرة ← طنطا", "Cairo → Tanta", tanta,
            "9:10 AM", "10:45 AM", 95, 35.0)

        add("545", "قطار 545 محسن", "Train 545 Improved", "محسن", "Improved",
            "القاهرة ← طنطا", "Cairo → Tanta", tanta,
            "11:05 AM", "12:40 PM", 95, 35.0)

        add("547", "قطار 547 محسن", "Train 547 Improved", "محسن", "Improved",
            "القاهرة ← طنطا", "Cairo → Tanta", tanta,
            "12:05 PM", "1:40 PM", 95, 35.0)

        add("549", "قطار 549 محسن", "Train 549 Improved", "محسن", "Improved",
            "القاهرة ← طنطا", "Cairo → Tanta", tanta,
            "1:20 PM", "4:40 PM", 200, 35.0,
            noteAr = "31 محطة", noteEn = "31 stops")

        add("551", "قطار 551 محسن", "Train 551 Improved", "محسن", "Improved",
            "القاهرة ← طنطا", "Cairo → Tanta", tanta,
            "2:35 PM", "4:10 PM", 95, 35.0)

        add("553", "قطار 553 محسن", "Train 553 Improved", "محسن", "Improved",
            "القاهرة ← طنطا", "Cairo → Tanta", tanta,
            "4:10 PM", "5:45 PM", 95, 35.0)

        add("555", "قطار 555 محسن", "Train 555 Improved", "محسن", "Improved",
            "القاهرة ← طنطا", "Cairo → Tanta", tanta,
            "5:05 PM", "8:25 PM", 200, 35.0,
            noteAr = "31 محطة", noteEn = "31 stops")

        add("563", "قطار 563 محسن", "Train 563 Improved", "محسن", "Improved",
            "القاهرة ← طنطا", "Cairo → Tanta", tanta,
            "5:30 PM", "7:05 PM", 95, 35.0)

        add("377", "قطار 377 محسن", "Train 377 Improved", "محسن", "Improved",
            "القاهرة ← الزقازيق", "Cairo → Zagazig", railway,
            "5:00 AM", "6:40 AM", 100, 30.0)

        add("379", "قطار 379 محسن", "Train 379 Improved", "محسن", "Improved",
            "القاهرة ← الزقازيق", "Cairo → Zagazig", railway,
            "6:15 AM", "7:55 AM", 100, 30.0)

        add("381", "قطار 381 محسن", "Train 381 Improved", "محسن", "Improved",
            "القاهرة ← الزقازيق", "Cairo → Zagazig", railway,
            "6:30 AM", "8:10 AM", 100, 30.0)

        add("383", "قطار 383 محسن", "Train 383 Improved", "محسن", "Improved",
            "القاهرة ← الزقازيق", "Cairo → Zagazig", railway,
            "7:20 AM", "9:00 AM", 100, 30.0)

        add("385", "قطار 385 محسن", "Train 385 Improved", "محسن", "Improved",
            "القاهرة ← الزقازيق", "Cairo → Zagazig", railway,
            "8:00 AM", "9:40 AM", 100, 30.0)

        add("387", "قطار 387 محسن", "Train 387 Improved", "محسن", "Improved",
            "القاهرة ← الزقازيق", "Cairo → Zagazig", railway,
            "9:00 AM", "10:40 AM", 100, 30.0)

        add("389", "قطار 389 محسن", "Train 389 Improved", "محسن", "Improved",
            "القاهرة ← الزقازيق", "Cairo → Zagazig", railway,
            "9:30 AM", "11:10 AM", 100, 30.0)

        add("391", "قطار 391 محسن", "Train 391 Improved", "محسن", "Improved",
            "القاهرة ← الزقازيق", "Cairo → Zagazig", railway,
            "10:35 AM", "12:15 PM", 100, 30.0)

        add("393", "قطار 393 محسن", "Train 393 Improved", "محسن", "Improved",
            "القاهرة ← الزقازيق", "Cairo → Zagazig", railway,
            "11:40 AM", "1:20 PM", 100, 30.0)

        add("395", "قطار 395 محسن", "Train 395 Improved", "محسن", "Improved",
            "القاهرة ← الزقازيق", "Cairo → Zagazig", railway,
            "7:30 PM", "10:05 PM", 155, 30.0,
            noteAr = "22 محطة", noteEn = "22 stops")

        add("397", "قطار 397 محسن", "Train 397 Improved", "محسن", "Improved",
            "القاهرة ← الزقازيق", "Cairo → Zagazig", railway,
            "10:10 PM", "12:30 AM", 140, 30.0,
            noteAr = "22 محطة", noteEn = "22 stops")

        add("835", "قطار 835 محسن", "Train 835 Improved", "محسن", "Improved",
            "القاهرة ← ميت غمر", "Cairo → Mit Ghamer", railway,
            "4:45 PM", "6:40 PM", 115, 40.0,
            noteAr = "القطار الوحيد - 16 محطة", noteEn = "Only train - 16 stops")

        add("343", "قطار 343 محسن", "Train 343 Improved", "محسن", "Improved",
            "القاهرة ← الصالحية", "Cairo → Salheya", railway,
            "3:40 PM", "7:09 PM", 209, 50.0,
            noteAr = "3h 29m - خط شرق الدلتا", noteEn = "3h 29m - Eastern Delta line")

        add("332", "قطار 332 محسن", "Train 332 Improved", "محسن", "Improved",
            "الصالحية ← القاهرة", "Salheya → Cairo", railway,
            "4:00 AM", "7:50 AM", 230, 50.0,
            noteAr = "32 محطة - العودة", noteEn = "32 stops - Return")

        add("142", "قطار 142 روسي", "Train 142 Russian", "روسي", "Russian",
            "القاهرة ← الفيوم", "Cairo → Faiyum", railway,
            "8:45 AM", "12:15 PM", 210, 40.0,
            noteAr = "19 محطة - خط الفيوم", noteEn = "19 stops - Faiyum line")

        add("1110", "قطار 1110 روسي", "Train 1110 Russian", "روسي", "Russian",
            "القاهرة ← الفيوم", "Cairo → Faiyum", railway,
            "3:45 PM", "6:05 PM", 140, 40.0,
            noteAr = "6 محطات - الأسرع", noteEn = "6 stops - Fastest")

        add("196", "قطار 196 AC روسي", "Train 196 AC Russian", "مكيف روسي", "AC Russian",
            "القاهرة ← الفيوم", "Cairo → Faiyum", railway,
            "7:35 PM", "9:55 PM", 140, 80.0,
            noteAr = "الأسرع - مكيف", noteEn = "Fastest - AC")

        add("940", "قطار 940 صيفي", "Train 940 Summer", "ثالثة مكيفة", "AC Third Class",
            "القاهرة ← مرسى مطروح", "Cairo → Marsa Matrouh", railway,
            "5:50 AM", "1:40 PM", 470, 120.0,
            noteAr = "قطار صيفي فقط (يونيو-سبتمبر)", noteEn = "Summer only (June-September)")

        add("943", "قطار 943 صيفي", "Train 943 Summer", "ثالثة مكيفة", "AC Third Class",
            "مرسى مطروح ← القاهرة", "Marsa Matrouh → Cairo", railway,
            "10:00 PM", "5:00 AM", 420, 120.0,
            noteAr = "قطار صيفي - عودة", noteEn = "Summer - Return")

        add("1933", "قطار 1933 صيفي", "Train 1933 Summer", "ثالثة مكيفة", "AC Third Class",
            "القاهرة ← مرسى مطروح", "Cairo → Marsa Matrouh", railway,
            "11:15 PM", "7:00 AM", 465, 120.0,
            noteAr = "قطار ليلي صيفي", noteEn = "Overnight summer train")

        add("1935", "قطار 1935 صيفي", "Train 1935 Summer", "ثالثة مكيفة", "AC Third Class",
            "مرسى مطروح ← القاهرة", "Marsa Matrouh → Cairo", railway,
            "3:10 PM", "10:25 PM", 435, 120.0,
            noteAr = "قطار صيفي - عودة", noteEn = "Summer - Return")

        add("773", "قطار 773 نوم", "Train 773 Sleep", "نوم", "Sleep",
            "القاهرة ← مرسى مطروح", "Cairo → Marsa Matrouh", railway,
            "10:05 PM", "5:35 AM", 450, 350.0,
            noteAr = "نوم - Sat/Mon/Wed", noteEn = "Sleep - Sat/Mon/Wed")

        add("775", "قطار 775 نوم", "Train 775 Sleep", "نوم", "Sleep",
            "مرسى مطروح ← القاهرة", "Marsa Matrouh → Cairo", railway,
            "10:30 PM", "5:35 AM", 425, 350.0,
            noteAr = "نوم - Sun/Tue/Thu", noteEn = "Sleep - Sun/Tue/Thu")

        return trains
    }

    fun getRailwayStations(): List<Station> {
        val stations = mutableListOf<Station>()
        var seq = 1
        fun add(id: String, ar: String, en: String, govAr: String, govEn: String,
                lAr: List<String>, lEn: List<String>, tip: String = "", tipEn: String = "") {
            stations.add(Station(
                id = "RAIL_$id",
                nameAr = ar,
                nameEn = en,
                line = TransportLine.RAILWAY_CAIRO_TANTA,
                sequentialNumber = seq++,
                mapX = 0f, mapY = 0f,
                addressAr = govAr,
                addressEn = govEn,
                landmarksAr = lAr,
                landmarksEn = lEn,
                guideTipAr = tip,
                guideTipEn = tipEn
            ))
        }

        add("CAIRO_RAMSES", "محطة مصر برمسيس", "Misr Railway Station (Ramses)",
            "ميدان رمسيس، عابدين، القاهرة", "Ramses Square, Abdeen, Cairo",
            listOf("محطة مترو رمسيس - الخط الثالث", "موقف رمسيس للميكروباص", "كوبري 6 أكتوبر", "البنك المركزي", "مستشفى الهلال الأحمر"),
            listOf("Ramses Metro Station (L3)", "Ramses Microbus Terminal", "6th October Bridge", "Central Bank", "Red Crescent Hospital"),
            "أكبر محطة سكك حديدية في مصر والشرق الأوسط، تنطلق منها قطارات لكل محافظات مصر. مبنى تاريخي من 1894.",
            "Largest railway station in Egypt and the Middle East. Trains to all Egyptian governorates depart from here. Historic building from 1894.")

        add("ALEX_MISR", "محطة مصر الإسكندرية", "Misr Station Alexandria",
            "ميدان محطة مصر، الإسكندرية", "Misr Station Square, Alexandria",
            listOf("مكتبة الإسكندرية", "كورنيش الإسكندرية", "قلعة قايتباي", "متحف الإسكندرية القومي", "برج الساعة"),
            listOf("Bibliotheca Alexandrina", "Alexandria Corniche", "Qaitbay Citadel", "Alexandria National Museum", "Clock Tower"),
            "أهم محطة في الإسكندرية، استقبلت الملوك والرؤساء. مبنى على الطراز الفرنسي.",
            "Most important station in Alexandria, has hosted kings and presidents. French-style building.")

        add("ALEX_SIDI_GABER", "محطة سيدي جابر", "Sidi Gaber Station",
            "سيدي جابر، الإسكندرية", "Sidi Gaber, Alexandria",
            listOf("مستشفى سيدي جابر", "كورنيش سيدي جابر", "فندق هيلتون الإسكندرية", "نادي سبورتنج", "مول كارفور"),
            listOf("Sidi Gaber Hospital", "Sidi Gaber Corniche", "Hilton Alexandria", "Sporting Club", "Carrefour Mall"),
            "محطة ثانوية في الإسكندرية، تخدم القطارات السريعة.",
            "Secondary station in Alexandria, serves express trains.")

        add("TANTA", "محطة طنطا", "Tanta Station",
            "ميدان المحطة، طنطا، الغربية", "Station Square, Tanta, Gharbia",
            listOf("ميدان طنطا", "كورنيش طنطا", "جامعة طنطا", "مستشفى طنطا الجامعي", "مسجد السيد البدوي"),
            listOf("Tanta Square", "Tanta Corniche", "Tanta University", "Tanta University Hospital", "El-Sayed El-Badawi Mosque"),
            "محطة رئيسية في الدلتا، تقاطع خطوط القاهرة-الإسكندرية والقاهرة-المنصورة.",
            "Main Delta station, intersection of Cairo-Alex and Cairo-Mansoura lines.")

        add("MANSOURA", "محطة المنصورة", "Mansoura Station",
            "ميدان المحطة، المنصورة، الدقهلية", "Station Square, Mansoura, Dakahlia",
            listOf("جامعة المنصورة", "مستشفى الطوارئ بالمنصورة", "ميدان الشناوي", "دار ابن لقمان", "كورنيش النيل"),
            listOf("Mansoura University", "Mansoura Emergency Hospital", "El-Shennawy Square", "Dar Ibn Luqman", "Nile Corniche"),
            "محطة عاصمة الدقهلية، تنطلق منها قطارات للقاهرة ودمياط والإسكندرية.",
            "Capital of Dakahlia, trains to Cairo, Damietta, and Alexandria.")

        add("DAMIETTA", "محطة دمياط", "Damietta Station",
            "رأس البر، دمياط", "Ras El-Bar, Damietta",
            listOf("رأس البر", "مصب النيل", "متحف دمياط", "جامعة دمياط", "كورنيش النيل"),
            listOf("Ras El-Bar", "Nile Mouth", "Damietta Museum", "Damietta University", "Nile Corniche"),
            "محطة في محافظة دمياط، على ساحل البحر المتوسط.",
            "Station in Damietta Governorate, on the Mediterranean coast.")

        add("ZAGAZIG", "محطة الزقازيق", "Zagazig Station",
            "ميدان المحطة، الزقازيق، الشرقية", "Station Square, Zagazig, Sharqia",
            listOf("جامعة الزقازيق", "ميدان الجلاء", "مستشفى الزقازيق الجامعي", "كورنيش الزقازيق", "دار الكتب"),
            listOf("Zagazig University", "El-Galaa Square", "Zagazig University Hospital", "Zagazig Corniche", "House of Books"),
            "محطة عاصمة الشرقية، تقاطع مهم لخطوط القاهرة-قناة السويس والقاهرة-الدلتا.",
            "Capital of Sharqia, key intersection for Cairo-Suez Canal and Cairo-Delta lines.")

        add("ISMAILIA", "محطة الإسماعيلية", "Ismailia Station",
            "ميدان المحطة، الإسماعيلية", "Station Square, Ismailia",
            listOf("متحف ديليسبس", "كورنيش الإسماعيلية", "بحيرة التمساح", "نادي الإسماعيلية", "ميدان الشهداء"),
            listOf("De Lesseps Museum", "Ismailia Corniche", "Crocodile Lake", "Ismailia Club", "Martyrs Square"),
            "محطة قناة السويس، تنطلق منها قطارات لبورسعيد والسويس والقاهرة.",
            "Suez Canal station, trains to Port Said, Suez, and Cairo.")

        add("PORT_SAID", "محطة بورسعيد", "Port Said Station",
            "ميدان المحطة، بورسعيد", "Station Square, Port Said",
            listOf("متحف بورسعيد", "مبنى هيئة قناة السويس", "كورنيش بورسعيد", "الحي الإيطالي", "ميناء بورسعيد"),
            listOf("Port Said Museum", "Suez Canal Authority Building", "Port Said Corniche", "Italian Quarter", "Port Said Port"),
            "محطة على قناة السويس، مدينة تاريخية مقاومة 1956.",
            "Station on Suez Canal, historic resistance city 1956.")

        add("SUEZ", "محطة السويس", "Suez Station",
            "ميدان المحطة، السويس", "Station Square, Suez",
            listOf("ميناء السويس", "نفق الشهيد أحمد حمدي", "عيون موسى", "كورنيش السويس", "عش الملاحة"),
            listOf("Suez Port", "Ahmed Hamdi Tunnel", "Eyon Mousa", "Suez Corniche", "Ashtoum El-Nakhla"),
            "محطة على البحر الأحمر، قرب نفق الشهيد أحمد حمدي.",
            "Station on the Red Sea, near Ahmed Hamdi Memorial Tunnel.")

        add("BENHA", "محطة بنها", "Banha Station",
            "ميدان المحطة، بنها، القليوبية", "Station Square, Banha, Qalyubia",
            listOf("جامعة بنها", "كورنيش النيل", "مستشفى بنها الجامعي", "قصر محمد علي بشبرا شهاب", "متحف القليوبية"),
            listOf("Banha University", "Nile Corniche", "Banha University Hospital", "Mohamed Ali Palace Shubra Shahab", "Qalyubia Museum"),
            "محطة في القليوبية، 47 قطار/يوم، تقاطع طرق مهمة.",
            "Station in Qalyubia, 47 trains/day, important crossroads.")

        add("DAMANHOUR", "محطة دمنهور", "Damanhour Station",
            "ميدان المحطة، دمنهور، البحيرة", "Station Square, Damanhour, Beheira",
            listOf("جامعة دمنهور", "كورنيش دمنهور", "ميدان الجلاء", "متحف دمنهور", "مستشفى دمنهور العام"),
            listOf("Damanhour University", "Damanhour Corniche", "El-Galaa Square", "Damanhour Museum", "Damanhour General Hospital"),
            "محطة عاصمة البحيرة، تخدم 25-27 قطار/يوم.",
            "Capital of Beheira, serves 25-27 trains/day.")

        add("FAIYUM", "محطة الفيوم", "Faiyum Station",
            "ميدان المحطة، الفيوم", "Station Square, Faiyum",
            listOf("بحيرة قارون", "وادي الريان", "جامعة الفيوم", "متحف الفيوم", "كورنيش بحيرة قارون"),
            listOf("Lake Qarun", "Wadi El-Rayan", "Faiyum University", "Faiyum Museum", "Qarun Lake Corniche"),
            "محطة محافظة الفيوم، على بعد 100 كم جنوب غرب القاهرة.",
            "Faiyum Governorate station, 100 km southwest of Cairo.")

        add("BENI_SUEF", "محطة بني سويف", "Beni Suef Station",
            "ميدان المحطة، بني سويف", "Station Square, Beni Suef",
            listOf("جامعة بني سويف", "كورنيش النيل", "متحف بني سويف", "ميدان الكوم الأخضر", "مسجد التوبة"),
            listOf("Beni Suef University", "Nile Corniche", "Beni Suef Museum", "Kom El-Akhdar Square", "Tawba Mosque"),
            "محطة في صعيد مصر، أول مدن الصعيد.",
            "Station in Upper Egypt, first Upper Egypt city.")

        add("MINYA", "محطة المنيا", "Minya Station",
            "ميدان المحطة، المنيا", "Station Square, Minya",
            listOf("جامعة المنيا", "مقابر بني حسن", "تل العمارنة", "كورنيش النيل", "متحف ملوي"),
            listOf("Minya University", "Beni Hasan Tombs", "Amarna", "Nile Corniche", "Mallawi Museum"),
            "محطة عاصمة المنيا، قاعدة لزيارة آثار بني حسن وتل العمارنة.",
            "Capital of Minya, base for Beni Hasan and Amarna antiquities visits.")

        add("ASYUT", "محطة أسيوط", "Asyut Station",
            "ميدان المحطة، أسيوط", "Station Square, Asyut",
            listOf("جامعة أسيوط", "دير المحرق", "كورنيش النيل", "متحف أسيوط", "ميدان المجاهدين"),
            listOf("Asyut University", "El-Muharraq Monastery", "Nile Corniche", "Asyut Museum", "Mujahedeen Square"),
            "أكبر محطة في الصعيد الأوسط، عاصمة أسيوط. 50+ قطار/يوم.",
            "Largest station in Middle Upper Egypt, Asyut capital. 50+ trains/day.")

        add("SOHAG", "محطة سوهاج", "Sohag Station",
            "ميدان المحطة، سوهاج", "Station Square, Sohag",
            listOf("جامعة سوهاج", "المعبد الأبيض", "الدير الأحمر", "أخميم", "كورنيش النيل"),
            listOf("Sohag University", "White Monastery", "Red Monastery", "Akhmim", "Nile Corniche"),
            "محطة عاصمة سوهاج، بوابة لصعيد مصر الأعلى.",
            "Capital of Sohag, gateway to Upper Upper Egypt.")

        add("QENA", "محطة قنا", "Qena Station",
            "ميدان المحطة، قنا", "Station Square, Qena",
            listOf("جامعة جنوب الوادي", "معبد دندرة", "كورنيش النيل", "متحف قنا", "ميدان المحطة"),
            listOf("South Valley University", "Dendera Temple", "Nile Corniche", "Qena Museum", "Station Square"),
            "محطة عاصمة قنا، قرب معبد دندرة الشهير.",
            "Capital of Qena, near famous Dendera Temple.")

        add("LUXOR", "محطة الأقصر", "Luxor Station",
            "ميدان المحطة، الأقصر", "Station Square, Luxor",
            listOf("معبد الكرنك", "وادي الملوك", "معبد حتشبسوت", "تمثالا ممنون", "كورنيش النيل", "متحف الأقصر"),
            listOf("Karnak Temple", "Valley of the Kings", "Hatshepsut Temple", "Colossi of Memnon", "Nile Corniche", "Luxor Museum"),
            "محطة في قلب الأقصر، أطلال طيبة القديمة، عاصمة مصر الفرعونية. 38 قطار/يوم.",
            "Station in heart of Luxor, ancient Thebes ruins, pharaonic Egypt capital. 38 trains/day.")

        add("ASWAN", "محطة أسوان", "Aswan Station",
            "ميدان المحطة، أسوان", "Station Square, Aswan",
            listOf("السد العالي", "معبد فيلة", "معبد أبو سمبل", "جزيرة النباتات", "متحف أسوان", "كورنيش النيل"),
            listOf("High Dam", "Philae Temple", "Abu Simbel Temple", "Botanical Island", "Aswan Museum", "Nile Corniche"),
            "أقصى جنوب مصر، محطة أسوان. 38+ قطار/يوم، تالجو وأبو الهول VIP.",
            "Southernmost Egypt, Aswan station. 38+ trains/day, Talgo and Abu Al-Hol VIP.")

        add("MARSA_MATROUH", "محطة مرسى مطروح", "Marsa Matrouh Station",
            "ميدان المحطة، مرسى مطروح", "Station Square, Marsa Matrouh",
            listOf("شاطئ عجيبة", "شاطئ روميل", "كليوباترا", "كورنيش مطروح", "حمام كليوباترا"),
            listOf("Agiba Beach", "Rommel Beach", "Cleopatra", "Matrouh Corniche", "Cleopatra Bath"),
            "محطة الساحل الشمالي، خدمة صيفية فقط.",
            "North Coast station, summer service only.")

        add("MIT_GHAMER", "محطة ميت غمر", "Mit Ghamer Station",
            "ميدان المحطة، ميت غمر، الدقهلية", "Station Square, Mit Ghamer, Dakahlia",
            listOf("كورنيش النيل", "مستشفى ميت غمر", "ميدان المحطة"),
            listOf("Nile Corniche", "Mit Ghamer Hospital", "Station Square"),
            "محطة صغيرة في الدقهلية، قطار واحد/يوم.",
            "Small station in Dakahlia, one train/day.")

        add("SALHEYA", "محطة الصالحية", "Salheya Station",
            "ميدان المحطة، الصالحية الجديدة، الشرقية", "Station Square, New Salheya, Sharqia",
            listOf("الصالحية الجديدة", "مشروع شباب الخريجين", "ميدان المحطة"),
            listOf("New Salheya", "Graduates Youth Project", "Station Square"),
            "محطة شرق الدلتا، تخدم مشروع الصالحية الجديدة.",
            "Eastern Delta station, serves New Salheya project.")

        add("SHIBIN_QANATER", "محطة شبين القناطر", "Shibin Qanatir Station",
            "ميدان المحطة، شبين القناطر، القليوبية", "Station Square, Shibin Qanatir, Qalyubia",
            listOf("كورنيش شبين القناطر", "مستشفى شبين القناطر", "ميدان المحطة"),
            listOf("Shibin Qanatir Corniche", "Shibin Qanatir Hospital", "Station Square"),
            "محطة قريبة من القاهرة، 18+ قطار/يوم.",
            "Station near Cairo, 18+ trains/day.")

        add("QANATER", "محطة قناطر الخير", "Qanatir El-Kheir Station",
            "قناطر الخير، القليوبية", "Qanatir El-Kheir, Qalyubia",
            listOf("متحف الري", "سد وقناطر", "ميدان القناطر"),
            listOf("Irrigation Museum", "Barrage and locks", "Qanatir Square"),
            "محطة تاريخية على النيل، متحف الري المصري.",
            "Historic Nile station, Egyptian Irrigation Museum.")

        add("TOUKH", "محطة طوخ", "Toukh Station",
            "طوخ، القليوبية", "Toukh, Qalyubia",
            listOf("ميدان طوخ", "مستشفى طوخ", "كورنيش النيل"),
            listOf("Toukh Square", "Toukh Hospital", "Nile Corniche"),
            "محطة صغيرة بين القاهرة وبنها.",
            "Small station between Cairo and Banha.")

        add("QAHA", "محطة قها", "Qaha Station",
            "قها، القليوبية", "Qaha, Qalyubia",
            listOf("ميدان قها", "مستشفى قها", "كورنيش النيل"),
            listOf("Qaha Square", "Qaha Hospital", "Nile Corniche"),
            "محطة صغيرة، تمر عليها قطارات القاهرة-بنها-الإسكندرية.",
            "Small station, Cairo-Banha-Alexandria trains pass through.")

        add("SHEBEEN_KOM", "محطة شبين الكوم", "Shebeen El-Kom Station",
            "شبين الكوم، المنوفية", "Shebeen El-Kom, Monufia",
            listOf("جامعة المنوفية", "كورنيش النيل", "ميدان شبين", "مستشفى شبين الجامعي"),
            listOf("Menoufia University", "Nile Corniche", "Shebeen Square", "Shebeen University Hospital"),
            "محطة عاصمة المنوفية، على خط القاهرة-طنطا الفرعي.",
            "Capital of Monufia, on Cairo-Tanta branch line.")

        add("MENUF", "محطة منوف", "Menuf Station",
            "منوف، المنوفية", "Menuf, Monufia",
            listOf("جامعة منوف", "كورنيش النيل", "ميدان منوف", "مستشفى منوف"),
            listOf("Menuf University", "Nile Corniche", "Menuf Square", "Menuf Hospital"),
            "محطة على خط القاهرة-طنطا الفرعي عبر المنوفية.",
            "Station on Cairo-Tanta branch line via Menufia.")

        add("QUESNA", "محطة قويسنا", "Quesna Station",
            "قويسنا، المنوفية", "Quesna, Monufia",
            listOf("كورنيش النيل", "ميدان قويسنا", "مستشفى قويسنا"),
            listOf("Nile Corniche", "Quesna Square", "Quesna Hospital"),
            "محطة صغيرة بين شبين الكوم وبركة السبع.",
            "Small station between Shebeen El-Kom and Berket El-Saba.")

        add("KAFR_SHEIKH", "محطة كفر الشيخ", "Kafr Sheikh Station",
            "كفر الشيخ", "Kafr Sheikh",
            listOf("جامعة كفر الشيخ", "كورنيش النيل", "ميدان المحطة", "متحف كفر الشيخ"),
            listOf("Kafr Sheikh University", "Nile Corniche", "Station Square", "Kafr Sheikh Museum"),
            "محطة عاصمة كفر الشيخ، 19 قطار/يوم.",
            "Capital of Kafr Sheikh, 19 trains/day.")

        add("QLEEN", "محطة قلين", "Qleen Station",
            "قلين، كفر الشيخ", "Qleen, Kafr Sheikh",
            listOf("ميدان قلين", "كورنيش النيل", "مستشفى قلين"),
            listOf("Qleen Square", "Nile Corniche", "Qleen Hospital"),
            "محطة صغيرة في كفر الشيخ، على خط شمال الدلتا.",
            "Small station in Kafr Sheikh, on north Delta line.")

        add("SHIRBIN", "محطة شربين", "Shirbin Station",
            "شربين، الدقهلية", "Shirbin, Dakahlia",
            listOf("كورنيش النيل", "مستشفى شربين", "ميدان شربين"),
            listOf("Nile Corniche", "Shirbin Hospital", "Shirbin Square"),
            "محطة على خط شمال الدلتا، ربط بين دمياط والمنصورة.",
            "Station on north Delta line, connecting Damietta and Mansoura.")

        add("BIR_EL_ABD", "محطة بئر العبد", "Bir El-Abd Station",
            "بئر العبد، شمال سيناء", "Bir El-Abd, North Sinai",
            listOf("ميدان بئر العبد", "مستشفى بئر العبد"),
            listOf("Bir El-Abd Square", "Bir El-Abd Hospital"),
            "محطة في شمال سيناء، على خط القنطرة-بئر العبد.",
            "Station in North Sinai, on Qantara-Bir El-Abd line.")

        add("QANTARA_GHARB", "محطة القنطرة غرب", "Qantara Gharb Station",
            "القنطرة غرب، الإسماعيلية", "Qantara Gharb, Ismailia",
            listOf("ميدان القنطرة", "كورنيش قناة السويس"),
            listOf("Qantara Square", "Suez Canal Corniche"),
            "محطة غرب قناة السويس، معبر رئيسي لشمال سيناء.",
            "West Suez Canal station, main crossing to North Sinai.")

        add("NAJAA_HAMMADI", "محطة نجع حمادي", "Najaa Hammadi Station",
            "نجع حمادي، قنا", "Najaa Hammadi, Qena",
            listOf("جامعة جنوب الوادي", "كورنيش النيل", "متحف نجع حمادي", "ميدان المحطة"),
            listOf("South Valley University", "Nile Corniche", "Najaa Hammadi Museum", "Station Square"),
            "محطة في قنا، على خط القاهرة-أسوان.",
            "Station in Qena, on Cairo-Aswan line.")

        add("ABU_TISHT", "محطة أبو تشت", "Abu Tesht Station",
            "أبو تشت، قنا", "Abu Tesht, Qena",
            listOf("ميدان أبو تشت", "كورنيش النيل", "مستشفى أبو تشت"),
            listOf("Abu Tesht Square", "Nile Corniche", "Abu Tesht Hospital"),
            "محطة صغيرة بين قنا والأقصر.",
            "Small station between Qena and Luxor.")

        add("DISHNA", "محطة دشنا", "Dishna Station",
            "دشنا، قنا", "Dishna, Qena",
            listOf("ميدان دشنا", "كورنيش النيل", "مستشفى دشنا"),
            listOf("Dishna Square", "Nile Corniche", "Dishna Hospital"),
            "محطة صغيرة بين قنا والأقصر.",
            "Small station between Qena and Luxor.")

        return stations
    }
}
