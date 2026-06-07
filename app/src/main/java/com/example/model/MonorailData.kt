package com.example.model

enum class TransitMode(val nameAr: String, val nameEn: String, val iconName: String) {
    METRO("مترو القاهرة", "Cairo Metro", "metro"),
    MONORAIL("المونوريل", "Monorail", "monorail"),
    LRT("قطار العاصمة", "Capital Train", "lrt"),
    RAILWAY("سكك حديد مصر", "Egypt Railways", "railway"),
    BRT("BRT", "BRT", "brt"),
    BUS_AUTHORITY("أتوبيسات الهيئة", "Public Bus Authority", "bus_auth"),
    BUS_MINI("أتوبيسات الميني باص", "Mini Bus", "bus_mini"),
    SUPER_JET("سوبر جيت", "Super Jet", "superjet"),
    MICROBUS("ميكروباص", "Microbus", "microbus"),
    RIDE_HAILING("النقل الذكي", "Ride Hailing", "ride")
}

enum class TransportLine(val nameAr: String, val nameEn: String, val colorHex: String, val mode: TransitMode, val isOpen: Boolean = true) {
    // Metro
    METRO_LINE_1("الخط الأول (حلوان - المرج)", "Line 1 (Helwan - Marg)", "3F51B5", TransitMode.METRO),
    METRO_LINE_2("الخط الثاني (شبرا - المنيب)", "Line 2 (Shobra - Mounib)", "4CAF50", TransitMode.METRO),
    METRO_LINE_3("الخط الثالث (عدلي منصور - روض الفرج)", "Line 3 (Adly Mansour - Rod El-Farag)", "2196F3", TransitMode.METRO),
    METRO_LINE_3_UNIV("الخط الثالث (فرع جامعة القاهرة)", "Line 3 (Cairo University branch)", "2196F3", TransitMode.METRO),
    METRO_LINE_4("الخط الرابع (النصر - حدائق الأهرام)", "Line 4 (Nasr - Pyramids Gardens)", "9E9E9E", TransitMode.METRO, false),

    // Monorail
    EAST_NILE("شرق النيل (العاصمة الإدارية)", "East Nile (New Capital)", "E53935", TransitMode.MONORAIL),
    WEST_NILE("غرب النيل (6 أكتوبر)", "West Nile (6th of October)", "10B981", TransitMode.MONORAIL),

    // LRT
    LRT_LINE_1("القطار الكهربائي (عدلي منصور - العاشر)", "Capital LRT (Adly Mansour - 10th Ramadan)", "9C27B0", TransitMode.LRT),
    LRT_LINE_1_CAPITAL("القطار الكهربائي (فرع العاصمة)", "Capital LRT (New Capital branch)", "E91E63", TransitMode.LRT),

    // Railways
    RAILWAY_CAIRO_ALEX("القاهرة - الإسكندرية", "Cairo - Alexandria", "1A5276", TransitMode.RAILWAY),
    RAILWAY_CAIRO_LUXOR("القاهرة - الأقصر", "Cairo - Luxor", "D4AC0D", TransitMode.RAILWAY),
    RAILWAY_CAIRO_ASWAN("القاهرة - أسوان", "Cairo - Aswan", "8E24AA", TransitMode.RAILWAY),
    RAILWAY_BENHA_PORT_SAID("بنها - بورسعيد", "Benha - Port Said", "00ACC1", TransitMode.RAILWAY),
    RAILWAY_CAIRO_MANSOURA("القاهرة - المنصورة", "Cairo - Mansoura", "F4511E", TransitMode.RAILWAY),
    RAILWAY_CAIRO_TANTA("القاهرة - طنطا", "Cairo - Tanta", "6A1B9A", TransitMode.RAILWAY),

    // BRT
    BRT_RING_ROAD("الأتوبيس BRT (الدائري)", "BRT Bus (Ring Road)", "FF9800", TransitMode.BRT),

    // Bus Authority - Complete list from busmaps.com (259 routes)
    BUS_AUTH_CTA_1("أتوبيس 1 (التجمع الثالث - المرج الجديدة)", "Bus 1 (3rd Settlement - New Marg)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_2("أتوبيس 2 (حدائق القبة - الجامعة الأمريكية)", "Bus 2 (Hadayeq Al Qobba - AUC)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_7("أتوبيس 7 (العمرانية الجديدة - العتبة)", "Bus 7 (Al Omraneyya Al Gadida - Attaba)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_11("أتوبيس 11 (مدينة نصر - شبرا الخيمة)", "Bus 11 (Nasr City - Shubra El-Kheima)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_15("أتوبيس 15 (عبد المنعم رياض - صفط اللبن)", "Bus 15 (Abd Al Moneim Riad - Saft Al Laban)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_28("أتوبيس 28 (عبد المنعم رياض - أم بيومي)", "Bus 28 (Abd Al Moneim Riad - Om Bayoumi)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_32("أتوبيس 32 (الحي الثامن - عبد المنعم رياض)", "Bus 32 (8th District - Abd Al Moneim Riad)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_38("أتوبيس 38 (عبد المنعم رياض - الأميرية)", "Bus 38 (Abd Al Moneim Riad - Amiriya)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_46("أتوبيس 46 (بولاق الدكرور - مساكن الشروق)", "Bus 46 (Bulaq Al Dakrur - Masaken El Shorouk)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_111("أتوبيس 111 (شبرا الخيمة - مطار القاهرة)", "Bus 111 (Shubra El-Kheima - Cairo Airport)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_124("أتوبيس 124 (عبد المنعم رياض - المنيب)", "Bus 124 (Abd Al Moneim Riad - Moneeb)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_132("أتوبيس 132 (الجامعة الأمريكية - عبد المنعم رياض)", "Bus 132 (AUC - Abd Al Moneim Riad)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_154("أتوبيس 154 (عبد المنعم رياض - بساتين)", "Bus 154 (Abd Al Moneim Riad - Basateen)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_356("أتوبيس 356 (عبد المنعم رياض - مطار القاهرة) (مكيف)", "Bus 356 (Abd Al Moneim Riad - Cairo Airport) (AC)", "E53935", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_366("أتوبيس 366 (أحمد حلمي - مطار القاهرة)", "Bus 366 (Ahmed Helmy - Cairo Airport)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_407("أتوبيس 407 (أحمد حلمي - الأسمرات)", "Bus 407 (Ahmed Helmy - Asmarrat)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_507("أتوبيس 507 (ميدان الجيزة - مساكن شيراتون)", "Bus 507 (Giza Square - Masaken Sheraton)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_530("أتوبيس 530 (مطار القاهرة - قليوب)", "Bus 530 (Cairo Airport - Qalyoub)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_3("أتوبيس 3 (أحمد حلمي - مطار القاهرة)", "Bus 3 (Ahmed Helmy - Cairo Airport)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_14("أتوبيس 14 (شبرا الخيمة - العتبة)", "Bus 14 (Shubra El-Kheima - Attaba)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_10("أتوبيس 10 (إمبابة - مدينة نصر)", "Bus 10 (Imbaba - Nasr City)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_24("أتوبيس 24 (شبرا الخيمة - العتبة)", "Bus 24 (Shubra El-Kheima - Attaba)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_55("أتوبيس 55 (المرج الجديدة - عبد المنعم رياض)", "Bus 55 (New Marg - Abd Al Moneim Riad)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_66("أتوبيس 66 (المظلات - ميدان الجيزة)", "Bus 66 (Mezallat - Giza Square)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_80("أتوبيس 80 (الهرم - عبد المنعم رياض)", "Bus 80 (Haram - Abd Al Moneim Riad)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_92("أتوبيس 92 (فيصل - رمسيس)", "Bus 92 (Faisal - Ramses)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_97("أتوبيس 97 (بولاق الدكرور - السيدة عائشة)", "Bus 97 (Bulaq Al Dakrur - Sayeda Aisha)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_102("أتوبيس 102 (حلوان - رمسيس)", "Bus 102 (Helwan - Ramses)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_108("أتوبيس 108 (المعادي - عبد المنعم رياض)", "Bus 108 (Maadi - Abd Al Moneim Riad)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_120("أتوبيس 120 (السيدة عائشة - شبرا الخيمة)", "Bus 120 (Sayeda Aisha - Shubra El-Kheima)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_142("أتوبيس 142 (المهندسين - المرج الجديدة)", "Bus 142 (Mohandessin - New Marg)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_150("أتوبيس 150 (الأميرية - المنيب)", "Bus 150 (Amiriya - Moneeb)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_184("أتوبيس 184 (إمبابة - السيدة عائشة)", "Bus 184 (Imbaba - Sayeda Aisha)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_202("أتوبيس 202 (المرج الجديدة - مدينة نصر)", "Bus 202 (New Marg - Nasr City)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_210("أتوبيس 210 (رمسيس - مدينة الشروق)", "Bus 210 (Ramses - Shorouk City)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_220("أتوبيس 220 (شبرا الخيمة - ميدان الجيزة)", "Bus 220 (Shubra El-Kheima - Giza Square)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_302("أتوبيس 302 (أحمد حلمي - التجمع الخامس)", "Bus 302 (Ahmed Helmy - 5th Settlement)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_304("أتوبيس 304 (عبد المنعم رياض - الرحاب) (مكيف)", "Bus 304 (Abd Al Moneim Riad - Rehab) (AC)", "E53935", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_322("أتوبيس 322 (حدائق القبة - المرج الجديدة)", "Bus 322 (Hadayeq Al Qobba - New Marg)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_358("أتوبيس 358 (عبد المنعم رياض - حلوان) (مكيف)", "Bus 358 (Abd Al Moneim Riad - Helwan) (AC)", "E53935", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_400("أتوبيس 400 (عبد المنعم رياض - مطار القاهرة) (مكيف)", "Bus 400 (Abd Al Moneim Riad - Cairo Airport) (AC)", "E53935", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_500("أتوبيس 500 (ميدان الجيزة - مدينة بدر) (مكيف)", "Bus 500 (Giza Square - Badr City) (AC)", "E53935", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_606("أتوبيس 606 (الهرم - 6 أكتوبر)", "Bus 606 (Haram - 6th of October)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_777("أتوبيس 777 (رمسيس - الشيخ زايد) (مكيف)", "Bus 777 (Ramses - Sheikh Zayed) (AC)", "E53935", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_800("أتوبيس 800 (إمبابة - 6 أكتوبر)", "Bus 800 (Imbaba - 6th of October)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_888("أتوبيس 888 (مصر الجديدة - الجامعة الأمريكية)", "Bus 888 (Heliopolis - AUC)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_900("أتوبيس 900 (حلوان - ميدان الجيزة)", "Bus 900 (Helwan - Giza Square)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_950("أتوبيس 950 (المرج الجديدة - مدينة العبور)", "Bus 950 (New Marg - Obour City)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_999("أتوبيس 999 (العباسية - مدينة بدر)", "Bus 999 (Abbasseya - Badr City)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_115("أتوبيس 115 (عبد المنعم رياض - مطار القاهرة)", "Bus 115 (Abd Al Moneim Riad - Cairo Airport)", "4CAF50", TransitMode.BUS_AUTHORITY),
BUS_AUTH_CTA_1001("أتوبيس 1001 (تحرير - التجمع الاول)", "Bus 1001 (TAHRIR - ELTEGEME3 ELAWEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1002("أتوبيس 1002 (مساكن عين شمس - المطبعة)", "Bus 1002 (MASAKEN AIN SHAMS - ELMATBAA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1004("أتوبيس 1004 (التجمع - شبرا)", "Bus 1004 (ELTEGEME3 - SHOBRA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1008("أتوبيس 1008 (صقر قريش - مساكن الوحدة)", "Bus 1008 (SE2ER 2ERISH - MASAKEN ELWEHEDA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1009("أتوبيس 1009 (بيتشو - عتبة)", "Bus 1009 (BITESHU - 3ETEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1010("أتوبيس 1010 (عتبة - التجمع الاول)", "Bus 1010 (3ETEBA - ELTEGEME3 ELAWEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1011("أتوبيس 1011 (المنيب - الكيلو 4.5)", "Bus 1011 (ELMONEAB - ELKILU 4E.E5)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1016("أتوبيس 1016 (مدينة الشروق - دوران شبرا)", "Bus 1016 (MADINA ELSHOROU2 - DAWARAN SHOBRA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1017("أتوبيس 1017 (شبرا الخيمه - ٦ اكتوبر)", "Bus 1017 (SHOBRA ELKEHIMEH - 6 OCTOBER)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1018("أتوبيس 1018 (احمد حلمى - التجمع التالت)", "Bus 1018 (AHEMED HELEMA - ELTEGEME3 ELTALET)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1019("أتوبيس 1019 (مساكن زينهم - التجمع الاول)", "Bus 1019 (MASAKEN ZINEHEM - ELTEGEME3 ELAWEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1023("أتوبيس 1023 (مدينة بدر - احمد حلمى)", "Bus 1023 (MADINA BADR - AHEMED HELEMA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1024("أتوبيس 1024 (القطامية - احمد حلمي)", "Bus 1024 (EL2ETAMIA - AHEMED HELMY)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1028("أتوبيس 1028 (إبراهيم بك - المنيب)", "Bus 1028 (IBRAHEEM BEK - ELMONEAB)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1029("أتوبيس 1029 (إمبابة - التجمع الاول)", "Bus 1029 (EMBABA - ELTEGEME3 ELAWEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1031("أتوبيس 1031 (مساكن بيتشو - امبابة)", "Bus 1031 (MASAKEN BITESHU - AMEBABA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_104("أتوبيس 104 (بشتيل - التجمع الخامس)", "Bus 104 (BASHTEEL - ELTAGAMOA ELKHAMES)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1046("أتوبيس 1046 (المنيب - قسم الحدائق)", "Bus 1046 (ELMONEAB - 2ESEM ELHEDAYE2)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_105("أتوبيس 105 (قليوب البلد - السيدة عائشة)", "Bus 105 (QALYOUB ELBALAD - ELSEYYEDA AYESHA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1052("أتوبيس 1052 (القناطر الخيرية - عتبة)", "Bus 1052 (EL2ANATER ELKHEYREYA - 3ETEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1056("أتوبيس 1056 (مدينة الشروق - عبد المنعم رياض)", "Bus 1056 (MADINA ELSHOROU2 - ABDEL ELMONEEM RYAD)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_106("أتوبيس 106 (موقف السلام الجديد - عتبة)", "Bus 106 (MAW2AF ELSALAM ELGEDEED - 3ETEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1062("أتوبيس 1062 (تحرير - التجمع الخامس)", "Bus 1062 (TAHRIR - ELTAGAMOA ELKHAMES)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1064("أتوبيس 1064 (عتبة - التجمع التالت)", "Bus 1064 (3ETEBA - ELTEGEME3 ELTALET)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1068("أتوبيس 1068 (السادات - العباسية)", "Bus 1068 (ELSADAT - EL3ABASEYA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1069("أتوبيس 1069 (المنيب - عتبة)", "Bus 1069 (ELMONEAB - 3ETEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_107("أتوبيس 107 (مساكن أسكو - المطبعة)", "Bus 107 (MASAKEN ASKO - ELMATBAA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1070("أتوبيس 1070 (المنيب - التبة)", "Bus 1070 (ELMONEAB - ELTEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1072("أتوبيس 1072 (مساكن الوحدة - الاباجية)", "Bus 1072 (MASAKEN ELWEHEDA - ELABAGIA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1073("أتوبيس 1073 (شبرا الخيمة - جامع عمرو)", "Bus 1073 (SHOBRA ELKHEMA - GAME3 3EMERU)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1076("أتوبيس 1076 (مؤسسة الزكاة - عتبة)", "Bus 1076 (MOASSASA ELZEKAA - 3ETEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1083("أتوبيس 1083 (مدينة العبور - عبد المنعم رياض)", "Bus 1083 (MADINA EL3ABOUR - ABDEL ELMONEEM RYAD)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1088("أتوبيس 1088 (15 مايو - عباسية)", "Bus 1088 (15 MAYO - 3ABASEYA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1089("أتوبيس 1089 (التبين - احمد حلمي)", "Bus 1089 (ELTEBIN - AHEMED HELMY)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1090("أتوبيس 1090 (الاميرية - الاباجية)", "Bus 1090 (ELAMIRIA - ELABAGIA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1092("أتوبيس 1092 (أحمد - العام)", "Bus 1092 (AHMED - EL3AM)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1095("أتوبيس 1095 (مينا هاوس - عباسية)", "Bus 1095 (MINA HAWES - 3ABASEYA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1096("أتوبيس 1096 (المنيب - التبة)", "Bus 1096 (ELMONEAB - ELTEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1097("أتوبيس 1097 (السادات - العباسية)", "Bus 1097 (ELSADAT - EL3ABASEYA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1100("أتوبيس 1100 (مدينة السلام - تحرير)", "Bus 1100 (MADINA ELSALAM - TAHRIR)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1103("أتوبيس 1103 (شبرا الخيمة - التجمع الاول)", "Bus 1103 (SHOBRA ELKHEMA - ELTEGEME3 ELAWEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1107("أتوبيس 1107 (القطامية - إمبابة)", "Bus 1107 (EL2ETAMIA - EMBABA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1111("أتوبيس 1111 (شبرا الخيمة - التجمع الثالث)", "Bus 1111 (SHOBRA ELKHEMA - ELTAGAMOA ELTALET)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_112("أتوبيس 112 (مساكن شيراتون - السيدة نفيسة)", "Bus 112 (MASAKEN SHERATON - ELSEYYEDA NAFEESA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1122("أتوبيس 1122 (المرج الجديدة - التجمع الثالث)", "Bus 1122 (ELMARG ELGEDEDA - ELTAGAMOA ELTALET)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1123("أتوبيس 1123 (قسم الحدائق - بولاق الدكرور)", "Bus 1123 (2ESEM ELHEDAYE2 - BOLAQ ELDEKEREWER)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1124("أتوبيس 1124 (التجمع الخامس -الجامعة الامريكية -المستشفي الجوي -الغاز - المرج الجديدة)", "Bus 1124 (ELTAGAMOA ELKHAMES -ALEGAME3A ELAMERIKIA -ALEMESETESHEFEY ELGEWEY -ALEGEHAZ - ELMARG ELGEDEDA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1125("أتوبيس 1125 (وراق العرب - عباسية)", "Bus 1125 (WERE2 EL3ARAB - 3ABASEYA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1126("أتوبيس 1126 (بولاق الدكرور - الطوب الرملى)", "Bus 1126 (BOLAQ ELDEKEREWER - ELTEWEB ELREMELA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1128("أتوبيس 1128 (الاسمرات - مظلات)", "Bus 1128 (ELASEMERAT - MAZALAT)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1129("أتوبيس 1129 (المنيب - الاميرية)", "Bus 1129 (ELMONEAB - ELAMIRIA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_113("أتوبيس 113 (امبابة - كيلو 4.5)", "Bus 113 (AMEBABA - KILU 4E.E5)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1132("أتوبيس 1132 (المطبعة - احمد حلمي)", "Bus 1132 (ELMATBAA - AHEMED HELMY)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1133("أتوبيس 1133 (مساكن الوحدة - بولاق الدكرور)", "Bus 1133 (MASAKEN ELWEHEDA - BOLAQ ELDEKEREWER)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1137("أتوبيس 1137 (عبد المنعم رياض - التجمع الثالث)", "Bus 1137 (ABDEL ELMONEEM RYAD - ELTAGAMOA ELTALET)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1138("أتوبيس 1138 (مظلات - مطار القاهرة)", "Bus 1138 (MAZALAT - MATAR ELCAHIRA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1142("أتوبيس 1142 (الاسمرات - أثر النبى)", "Bus 1142 (ELASEMERAT - ATER ELNEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1143("أتوبيس 1143 (ابراهيم بك - المماليك)", "Bus 1143 (ABERAHIM BK - ELMEMALIK)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1144("أتوبيس 1144 (الاباجية - شبرا الخيمة)", "Bus 1144 (ELABAGIA - SHOBRA ELKHEMA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1145("أتوبيس 1145 (هضبة الهرم - مظلات)", "Bus 1145 (HEZEBA HARAM - MAZALAT)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1146("أتوبيس 1146 (إبراهيم بك - شيراتون)", "Bus 1146 (IBRAHEEM BEK - SHIRATEWEN)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_1151("أتوبيس 1151 (الاسمرات - عبد المنعم رياض)", "Bus 1151 (ELASEMERAT - ABDEL ELMONEEM RYAD)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_117("أتوبيس 117 (كلية الزراعه - ام المصريين)", "Bus 117 (KOLLEYA ELZERE3EH - OM ELMASREYEEN)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_119("أتوبيس 119 (بشتيل - مساكن زينهم)", "Bus 119 (BASHTEEL - MASAKEN ZINEHEM)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_12("أتوبيس 12 (ميدان الجيزة - التجمع الاول)", "Bus 12 (MAYDAN ELGEZA - ELTEGEME3 ELAWEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_121("أتوبيس 121 (البدرشين - مدينة الأمل (عزبة الهجانة))", "Bus 121 (ELBADRASHEEN - MADINA ELAMEL (E3EZEBA ELHEGANA))", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_122("أتوبيس 122 (اميرية - مساكن زينهم)", "Bus 122 (AMIRIA - MASAKEN ZINEHEM)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_123("أتوبيس 123 (موقف الرماية - التبة)", "Bus 123 (MAW2AF ELREMAYA - ELTEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_125("أتوبيس 125 (صفاء حجازى - العباسية)", "Bus 125 (SEFE2 HEGAZA - EL3ABASEYA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_126("أتوبيس 126 (دوران شبرا - التجمع الاول)", "Bus 126 (DAWARAN SHOBRA - ELTEGEME3 ELAWEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_127("أتوبيس 127 (بولاق الدكرور - مساكن شيراتون)", "Bus 127 (BOLAQ ELDEKEREWER - MASAKEN SHERATON)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_129("أتوبيس 129 (عرب الطوايلة - عتبة)", "Bus 129 (ARAB ELTEWAILA - 3ETEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_13("أتوبيس 13 (مساكن عين شمس - الأسمرات)", "Bus 13 (MASAKEN AIN SHAMS - ELASEMERAT)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_130("أتوبيس 130 (مظلات - مصر)", "Bus 130 (MAZALAT - MESER)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_131("أتوبيس 131 (دوران شبرا - التجمع الاول)", "Bus 131 (DAWARAN SHOBRA - ELTEGEME3 ELAWEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_133("أتوبيس 133 (كفر الشرفا - موقف التجمع الاول)", "Bus 133 (KAFR ELSHEREFA - MAW2AF ELTEGEME3 ELAWEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_134("أتوبيس 134 (السيدة نفيسة - مظلات)", "Bus 134 (ELSEYYEDA NAFEESA - MAZALAT)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_135("أتوبيس 135 (القلعة - الهايكستيب)", "Bus 135 (EL2ALAA - ELHAIKESETIB)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_136("أتوبيس 136 (السيدة عائشة - التجمع الاول)", "Bus 136 (ELSEYYEDA AYESHA - ELTEGEME3 ELAWEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_137("أتوبيس 137 (شارع الوحدة - مساكن شيراتون)", "Bus 137 (SHAREA3 ELWEHEDA - MASAKEN SHERATON)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_138("أتوبيس 138 (تحرير - الرحاب بوابة 1.6.23)", "Bus 138 (TAHRIR - ELREHAB BEWABA 1E.E6E.E2E3)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_140("أتوبيس 140 (بشتيل - مدينة الامل)", "Bus 140 (BASHTEEL - MADINA ELAMEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_144("أتوبيس 144 (المطبعة - زهراء مدينة نصر)", "Bus 144 (ELMATBAA - ZAHRA2 MADINET NASR)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_146("أتوبيس 146 (كفر الشرفا - السيدة نفيسة)", "Bus 146 (KAFR ELSHEREFA - ELSEYYEDA NAFEESA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_148("أتوبيس 148 (محمد نجيب - السيدة عائشة)", "Bus 148 (MEHEMED NEGIB - ELSEYYEDA AYESHA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_149("أتوبيس 149 (شارع الوحده - السيدة عائشة)", "Bus 149 (SHAREA3 ELWEHEDEH - ELSEYYEDA AYESHA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_151("أتوبيس 151 (المرج الجديده - احمد حلمى)", "Bus 151 (ELMARG ELGEDIDEH - AHEMED HELEMA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_155("أتوبيس 155 (المرج الجديده - عتبة)", "Bus 155 (ELMARG ELGEDIDEH - 3ETEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_159("أتوبيس 159 (دوران شبرا - التجمع الاول)", "Bus 159 (DAWARAN SHOBRA - ELTEGEME3 ELAWEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_16("أتوبيس 16 (السادات - المظلات)", "Bus 16 (ELSADAT - ELMAZALAT)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_160("أتوبيس 160 (صقر قريش - العمرانية)", "Bus 160 (SE2ER 2ERISH - EL3OMRANEYA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_162("أتوبيس 162 (مؤسسة - مدينة الأمل (عزبة الهجانة))", "Bus 162 (MOASSASA - MADINA ELAMEL (E3EZEBA ELHEGANA))", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_165("أتوبيس 165 (جزيرة محمد - البساتين)", "Bus 165 (GEZIRA MEHEMED - ELBESATIN)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_166("أتوبيس 166 (تحرير - عتبة)", "Bus 166 (TAHRIR - 3ETEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_167("أتوبيس 167 (زنين - الدراسة)", "Bus 167 (ZENEIN - ELDARASA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_17("أتوبيس 17 (جامع عمرو - عمرانية)", "Bus 17 (GAME3 3EMERU - 3EMERANIA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_170("أتوبيس 170 (ابو وافية - منيل الروضة)", "Bus 170 (ABU WAFEYA - MENIL ELREWEZA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_18("أتوبيس 18 (مساكن أسكو - مساكن الشروق)", "Bus 18 (MASAKEN ASKO - MASAKEN ELSHOROU2)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_186("أتوبيس 186 (بولاق الدكرور - الدراسة)", "Bus 186 (BOLAQ ELDEKEREWER - ELDARASA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_190("أتوبيس 190 (امبابة - دراسة)", "Bus 190 (AMEBABA - DERASA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_20("أتوبيس 20 (الحوامدية - جامعة القاهرة)", "Bus 20 (ELHAWAMDEYA - GAM3A ELCAHIRA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_200("أتوبيس 200 (اوسيم - هضبة الهرم)", "Bus 200 (AWESIM - HEZEBA HARAM)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_2001("أتوبيس 2001 (شبرا الخيمة - حدائق اكتوبر)", "Bus 2001 (SHOBRA ELKHEMA - HADAEK OCTOBER)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_2002("أتوبيس 2002 (شبرا الخيمة - هضبة الهرم)", "Bus 2002 (SHOBRA ELKHEMA - HEZEBA HARAM)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_201("أتوبيس 201 (اوسيم - ميدان الجيزة)", "Bus 201 (AWESIM - MAYDAN ELGEZA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_203("أتوبيس 203 (ام بيومي - عزبة الهجانة الكيلو ٤.٥)", "Bus 203 (AM BIWEMEY - 3EZEBA ELHEGANA ELKILU 4E.E5)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_208("أتوبيس 208 (شبرا الخيمة - عباسية)", "Bus 208 (SHOBRA ELKHEMA - 3ABASEYA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_21("أتوبيس 21 (الحوامدية - قسم حدائق القبة)", "Bus 21 (ELHAWAMDEYA - 2ESEM HADAEK ELKOBBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_211("أتوبيس 211 (قليوب البلد - أسبيكو)", "Bus 211 (QALYOUB ELBALAD - ASEBIKU)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_212("أتوبيس 212 (القناطر الخيريه - المقطم)", "Bus 212 (EL2ANATER ELKEHIRIH - ELMO2ATTAM)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_215("أتوبيس 215 (مساكن عين شمس - بشتيل)", "Bus 215 (MASAKEN AIN SHAMS - BASHTEEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_216("أتوبيس 216 (صقر قريش - بشتيل)", "Bus 216 (SE2ER 2ERISH - BASHTEEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_217("أتوبيس 217 (العمرانية - مساكن الماظة)", "Bus 217 (EL3OMRANEYA - MASAKEN ELMAZA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_219("أتوبيس 219 (بشتيل - موقف السلام الجديد)", "Bus 219 (BASHTEEL - MAW2AF ELSALAM ELGEDEED)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_22("أتوبيس 22 (العمرانية - باب الشعرية)", "Bus 22 (EL3OMRANEYA - BAB ELSHA3REYA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_222("أتوبيس 222 (وردان - الرحاب بوابة 6)", "Bus 222 (WEREDAN - ELREHAB BEWABA 6)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_226("أتوبيس 226 (جراج - الكيلو 4.5)", "Bus 226 (GARAG - ELKILU 4E.E5)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_23("أتوبيس 23 (كفر طهرمس - قسم الحدائق)", "Bus 23 (KAFR THEREMES - 2ESEM ELHEDAYE2)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_235("أتوبيس 235 (شبرا الخيمة - القلعة)", "Bus 235 (SHOBRA ELKHEMA - EL2ALAA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_25("أتوبيس 25 (مساكن الشروق - التحرير)", "Bus 25 (MASAKEN ELSHOROU2 - TAHRIR)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_259("أتوبيس 259 (مساكن أسكو - مساكن شيراتون)", "Bus 259 (MASAKEN ASKO - MASAKEN SHERATON)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_26("أتوبيس 26 (بيجام - مساكن الشروق)", "Bus 26 (BIGAM - MASAKEN ELSHOROU2)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_265("أتوبيس 265 (المرج الجديدة - مدينة نصر)", "Bus 265 (ELMARG ELGEDEDA - NASR CITY)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_27("أتوبيس 27 (أم بيومى - السيدة عائشة)", "Bus 27 (OM BIWEMA - ELSEYYEDA AYESHA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_29("أتوبيس 29 (موقف مسطرد الجديد - ميدان الجيزة)", "Bus 29 (MAW2AF MOSTOROD ELGEDEED - MAYDAN ELGEZA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_290("أتوبيس 290 (الخصوص - احمد حلمى)", "Bus 290 (ELKHOSOUS - AHEMED HELEMA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_30("أتوبيس 30 (عبدالمنعم رياض - السيده نفيسه)", "Bus 30 (3EBEDALEMENE3EM RYAD - ELSIDEH NEFISH)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_300("أتوبيس 300 (المماليك - التجمع الخامس)", "Bus 300 (ELMEMALIK - ELTAGAMOA ELKHAMES)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_305("أتوبيس 305 (احمد حلمي - مساكن شيراتون)", "Bus 305 (AHEMED HELMY - MASAKEN SHERATON)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_306("أتوبيس 306 (اميرية - ميدان الجيزة)", "Bus 306 (AMIRIA - MAYDAN ELGEZA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_307("أتوبيس 307 (دوران شبرا - القطامية)", "Bus 307 (DAWARAN SHOBRA - EL2ETAMIA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_308("أتوبيس 308 (الخصوص - الرحاب بوابة 6)", "Bus 308 (ELKHOSOUS - ELREHAB BEWABA 6)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_309("أتوبيس 309 (شارع محمد نجيب - المقطم)", "Bus 309 (SHAREA3 MEHEMED NEGIB - ELMO2ATTAM)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_31("أتوبيس 31 (مساكن عثمان - الدراسة)", "Bus 31 (MASAKEN 3ETEMAN - ELDARASA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_310("أتوبيس 310 (الخصوص - الاباجية)", "Bus 310 (ELKHOSOUS - ELABAGIA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_311("أتوبيس 311 (الخصوص - السيدة عائشة)", "Bus 311 (ELKHOSOUS - ELSEYYEDA AYESHA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_314("أتوبيس 314 (دوران شبرا - دار مصر)", "Bus 314 (DAWARAN SHOBRA - DAR MASR)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_318("أتوبيس 318 (مساكن عين شمس - مدرية الأمن الجديدة)", "Bus 318 (MASAKEN AIN SHAMS - MEDERIA ELAMEN ELGEDEDA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_323("أتوبيس 323 (موقف السلام - عبد المنعم رياض)", "Bus 323 (MAW2AF ELSALAM - ABDEL ELMONEEM RYAD)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_329("أتوبيس 329 (بولاق الدكرور - مدينة الأمل (عزبة الهجانة))", "Bus 329 (BOLAQ ELDEKEREWER - MADINA ELAMEL (E3EZEBA ELHEGANA))", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_33("أتوبيس 33 (العمرانية - الأول)", "Bus 33 (EL3OMRANEYA - ELAWEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_330("أتوبيس 330 (مدينة السلام - عبدالمنعم رياض)", "Bus 330 (MADINA ELSALAM - 3EBEDALEMENE3EM RYAD)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_333("أتوبيس 333 (هضبة الهرم - عتبة)", "Bus 333 (HEZEBA HARAM - 3ETEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_34("أتوبيس 34 (العمرانية - مدينة الأمل (عزبة الهجانة))", "Bus 34 (EL3OMRANEYA - MADINA ELAMEL (E3EZEBA ELHEGANA))", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_340("أتوبيس 340 (١٥ مايو - كيلو ٤.٥)", "Bus 340 (15 MAIU - KILU 4E.E5)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_341("أتوبيس 341 (١٥ مايو - التجمع الخامس)", "Bus 341 (15 MAIU - ELTAGAMOA ELKHAMES)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_343("أتوبيس 343 (15 مايو - عبدالمنعم رياض)", "Bus 343 (15 MAYO - 3EBEDALEMENE3EM RYAD)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_35("أتوبيس 35 (صفط اللبن - النهضة)", "Bus 35 (SAFT ELLABAN - ELNEHEZA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_351("أتوبيس 351 (كرداسة - عبد المنعم رياض)", "Bus 351 (KEREDASA - ABDEL ELMONEEM RYAD)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_355("أتوبيس 355 (الخانكة - القلعة)", "Bus 355 (ELKHANKA - EL2ALAA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_37("أتوبيس 37 (كفر طهرمس - الكيلو 4.5)", "Bus 37 (KAFR THEREMES - ELKILU 4E.E5)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_380("أتوبيس 380 (العمرانية - التبة)", "Bus 380 (EL3OMRANEYA - ELTEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_39("أتوبيس 39 (زنين - التبة)", "Bus 39 (ZENEIN - ELTEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_4("أتوبيس 4 (مدينة بدر - مساكن شيراتون)", "Bus 4 (MADINA BADR - MASAKEN SHERATON)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_40("أتوبيس 40 (ميدان الجيزة - كيلو 4.5)", "Bus 40 (MAYDAN ELGEZA - KILU 4E.E5)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_401("أتوبيس 401 (المقطم - ميدان الجيزة)", "Bus 401 (ELMO2ATTAM - MAYDAN ELGEZA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_41("أتوبيس 41 (مساكن عثمان - عتبة)", "Bus 41 (MASAKEN 3ETEMAN - 3ETEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_410("أتوبيس 410 (التبين - البوابة الأولى)", "Bus 410 (ELTEBIN - ELBAWABA EL2WALA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_42("أتوبيس 42 (العمرانية - المقطم)", "Bus 42 (EL3OMRANEYA - ELMO2ATTAM)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_421("أتوبيس 421 (الخصوص - السيدة نفيسة)", "Bus 421 (ELKHOSOUS - ELSEYYEDA NAFEESA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_424("أتوبيس 424 (زهراء مدينة نصر - العمرانية)", "Bus 424 (ZAHRA2 MADINET NASR - EL3OMRANEYA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_43("أتوبيس 43 (المطرية - التبة)", "Bus 43 (ELMATAREYA - ELTEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_430("أتوبيس 430 (عباسية - مطبعة)", "Bus 430 (3ABASEYA - METEBE3A)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_44("أتوبيس 44 (المطبعة - النزهه الجديده)", "Bus 44 (ELMATBAA - ELNEZEHEH ELGEDIDEH)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_446("أتوبيس 446 (15 مايو - موقف اتوبيسات العباسية)", "Bus 446 (15 MAYO - MAW2AF ATEWEBISAT EL3ABASEYA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_45("أتوبيس 45 (المطبعة - موقف التجمع الاول)", "Bus 45 (ELMATBAA - MAW2AF ELTEGEME3 ELAWEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_48("أتوبيس 48 (عرب المعادى - الزاوية الحمراء)", "Bus 48 (ARAB ELME3ADA - ELZAWYA ELHAMRA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_49("أتوبيس 49 (زنين - كيلو 4.5)", "Bus 49 (ZENEIN - KILU 4E.E5)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_5("أتوبيس 5 (مترو كلية الزراعة - التبة)", "Bus 5 (METRO KOLLEYET ELZERAA - ELTEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_50("أتوبيس 50 (زنين - 6))", "Bus 50 (ZENEIN - 6))", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_505("أتوبيس 505 (صفاء حجازى - التجمع الخامس)", "Bus 505 (SEFE2 HEGAZA - ELTAGAMOA ELKHAMES)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_51("أتوبيس 51 (زنين - مساكن عين شمس)", "Bus 51 (ZENEIN - MASAKEN AIN SHAMS)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_52("أتوبيس 52 (صفط اللبن - مظلات)", "Bus 52 (SAFT ELLABAN - MAZALAT)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_53("أتوبيس 53 (السادات - التجمع الخامس)", "Bus 53 (ELSADAT - ELTAGAMOA ELKHAMES)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_54("أتوبيس 54 (هضبة الهرم - الحى السابع)", "Bus 54 (HEZEBA HARAM - ELHA ELSABE3)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_555("أتوبيس 555 (الخصوص - المماليك)", "Bus 555 (ELKHOSOUS - ELMEMALIK)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_56("أتوبيس 56 (بيتشو - التبة)", "Bus 56 (BITESHU - ELTEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_568("أتوبيس 568 (منشأة القناطر - عتبة)", "Bus 568 (MENESHAA EL2ANATER - 3ETEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_57("أتوبيس 57 (صفط اللبن - التبة)", "Bus 57 (SAFT ELLABAN - ELTEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_573("أتوبيس 573 (الالف مصنع - المنيب)", "Bus 573 (ELALEF MESENE3 - ELMONEAB)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_579("أتوبيس 579 (عباسية - الالف مصنع)", "Bus 579 (3ABASEYA - ELALEF MESENE3)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_580("أتوبيس 580 (بيجام - عتبة)", "Bus 580 (BIGAM - 3ETEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_59("أتوبيس 59 (التبة - احمد حلمي)", "Bus 59 (ELTEBA - AHEMED HELMY)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_60("أتوبيس 60 (دوران شبرا - النزهه الجديده)", "Bus 60 (DAWARAN SHOBRA - ELNEZEHEH ELGEDIDEH)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_62("أتوبيس 62 (الزاوية الحمراء - التجمع الاول)", "Bus 62 (ELZAWYA ELHAMRA - ELTEGEME3 ELAWEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_63("أتوبيس 63 (عتبة - العاصمة الإدارية الجديدة)", "Bus 63 (3ETEBA - EL3ASEMA ELEDARIA ELGEDEDA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_64("أتوبيس 64 (الزاوية الحمراء - كيلو 4.5)", "Bus 64 (ELZAWYA ELHAMRA - KILU 4E.E5)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_662("أتوبيس 662 (احمد حلمي - النائب العام)", "Bus 662 (AHEMED HELMY - ELNAYEB EL3AM)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_666("أتوبيس 666 (مدينة بدر - عبدالمنعم رياض)", "Bus 666 (MADINA BADR - 3EBEDALEMENE3EM RYAD)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_69("أتوبيس 69 (عرب الطوايلة - عتبة)", "Bus 69 (ARAB ELTEWAILA - 3ETEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_700("أتوبيس 700 (القناطر الخيرية - بوابات اكتوبر)", "Bus 700 (EL2ANATER ELKHEYREYA - BEWABAT OCTOBER)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_701("أتوبيس 701 (المطبعة - العوايد)", "Bus 701 (ELMATBAA - EL3EWAID)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_702("أتوبيس 702 (هضبة الهرم - مظلات)", "Bus 702 (HEZEBA HARAM - MAZALAT)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_704("أتوبيس 704 (ميدان الشهداء - نادي الصيد)", "Bus 704 (MAYDAN ELSHOHADA2 - NADEY ELSID)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_706("أتوبيس 706 (اخر فيصل - زهراء مدينة نصر)", "Bus 706 (AKEHER FAISAL - ZAHRA2 MADINET NASR)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_707("أتوبيس 707 (ميدان الجيزة - مساكن جاردينيا)", "Bus 707 (MAYDAN ELGEZA - MASAKEN GAREDINIA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_708("أتوبيس 708 (تحرير - مدرية أمن القاهرة الجديدة)", "Bus 708 (TAHRIR - MEDERIA AMEN ELCAHIRA ELGEDEDA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_72("أتوبيس 72 (صقر قريش - مساكن الشروق)", "Bus 72 (SE2ER 2ERISH - MASAKEN ELSHOROU2)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_724("أتوبيس 724 (الخصوص - عبد المنعم رياض)", "Bus 724 (ELKHOSOUS - ABDEL ELMONEEM RYAD)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_728("أتوبيس 728 (الخصوص - عتبة)", "Bus 728 (ELKHOSOUS - 3ETEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_74("أتوبيس 74 (بولاق الدكرور - بيرتى)", "Bus 74 (BOLAQ ELDEKEREWER - BIRETA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_76("أتوبيس 76 (إمبابة - شيراتون)", "Bus 76 (EMBABA - SHIRATEWEN)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_77("أتوبيس 77 (البساتين - أم بيومى)", "Bus 77 (ELBESATIN - OM BIWEMA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_775("أتوبيس 775 (شبرا الخيمة - دار مصر)", "Bus 775 (SHOBRA ELKHEMA - DAR MASR)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_779("أتوبيس 779 (ابراهيم بك - دار مصر)", "Bus 779 (ABERAHIM BK - DAR MASR)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_78("أتوبيس 78 (العمرانية - الدراسة)", "Bus 78 (EL3OMRANEYA - ELDARASA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_79("أتوبيس 79 (عرب الطوايلة - البساتين)", "Bus 79 (ARAB ELTEWAILA - ELBESATIN)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_8("أتوبيس 8 (المنيب - 13)", "Bus 8 (ELMONEAB - 13)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_803("أتوبيس 803 (قسم الحدائق - العمرانية)", "Bus 803 (2ESEM ELHEDAYE2 - EL3OMRANEYA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_810("أتوبيس 810 (قسم الحدائق - العمرانية)", "Bus 810 (2ESEM ELHEDAYE2 - EL3OMRANEYA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_814("أتوبيس 814 (بولاق الدكرور- شارع السودان - الأباجية)", "Bus 814 (BOLAQ ELDEKEREWERE- SHAREA3 ELSEWEDAN - ELABAGIA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_82("أتوبيس 82 (احمد حلمى - التجمع الاول)", "Bus 82 (AHEMED HELEMA - ELTEGEME3 ELAWEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_820("أتوبيس 820 (العمرانية - إبراهيم بك)", "Bus 820 (EL3OMRANEYA - IBRAHEEM BEK)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_824("أتوبيس 824 (ابو وافية - بيتشو)", "Bus 824 (ABU WAFEYA - BITESHU)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_83("أتوبيس 83 (جامع عمرو - التجمع الاول)", "Bus 83 (GAME3 3EMERU - ELTEGEME3 ELAWEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_831("أتوبيس 831 (الزاوية الحمراء - حدائق زينهم)", "Bus 831 (ELZAWYA ELHAMRA - HADAEK ZINEHEM)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_833("أتوبيس 833 (ام بيومى - ميدان الجيزة)", "Bus 833 (AM BIWEMA - MAYDAN ELGEZA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_84("أتوبيس 84 (احمد حلمى - التبة)", "Bus 84 (AHEMED HELEMA - ELTEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_840("أتوبيس 840 (المطبعة - إبراهيم بك)", "Bus 840 (ELMATBAA - IBRAHEEM BEK)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_85("أتوبيس 85 (مساكن الشروق - الوراق)", "Bus 85 (MASAKEN ELSHOROU2 - ELWERE2)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_86("أتوبيس 86 (وراق العرب - الأباجية)", "Bus 86 (WERE2 EL3ARAB - ELABAGIA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_87("أتوبيس 87 (مساكن اسكو - التجمع الاول)", "Bus 87 (MASAKEN ASEKU - ELTEGEME3 ELAWEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_88("أتوبيس 88 (مساكن أسكو - السيدة عائشة)", "Bus 88 (MASAKEN ASKO - ELSEYYEDA AYESHA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_89("أتوبيس 89 (مساكن أسكو - السيدة نفيسة)", "Bus 89 (MASAKEN ASKO - ELSEYYEDA NAFEESA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_9("أتوبيس 9 (اسبيكو - عتبة)", "Bus 9 (ASEBIKU - 3ETEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_90("أتوبيس 90 (المظلات - السيدة زينب)", "Bus 90 (ELMAZALAT - ELSEYYEDA ZEINAB)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_910("أتوبيس 910 (ميدان الجيزة - التجمع الاول)", "Bus 910 (MAYDAN ELGEZA - ELTEGEME3 ELAWEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_912("أتوبيس 912 (المنيب - الحى السابع)", "Bus 912 (ELMONEAB - ELHA ELSABE3)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_915("أتوبيس 915 (ابراهيم بك - عباسية)", "Bus 915 (ABERAHIM BK - 3ABASEYA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_917("أتوبيس 917 (قليوب البلد - عتبة)", "Bus 917 (QALYOUB ELBALAD - 3ETEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_918("أتوبيس 918 (شبين القناطر - عتبة)", "Bus 918 (SHEBIN EL2ANATER - 3ETEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_920("أتوبيس 920 (طوخ - موقف احمد حلمى)", "Bus 920 (TOKH - MAW2AF AHEMED HELEMA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_922("أتوبيس 922 (صقر قريش - عباسية)", "Bus 922 (SE2ER 2ERISH - 3ABASEYA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_923("أتوبيس 923 (الأباجية - المنيب)", "Bus 923 (ELABAGIA - ELMONEAB)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_925("أتوبيس 925 (دوران شبرا - مطار القاهرة)", "Bus 925 (DAWARAN SHOBRA - MATAR ELCAHIRA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_927("أتوبيس 927 (شبرا الخيمة - الكيلو 4.5)", "Bus 927 (SHOBRA ELKHEMA - ELKILU 4E.E5)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_93("أتوبيس 93 (البساتين - مظلات)", "Bus 93 (ELBESATIN - MAZALAT)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_945("أتوبيس 945 (مساكن عين شمس - ميدان الجيزة)", "Bus 945 (MASAKEN AIN SHAMS - MAYDAN ELGEZA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_953("أتوبيس 953 (القناطر الخيرية - عبد المنعم رياض)", "Bus 953 (EL2ANATER ELKHEYREYA - ABDEL ELMONEEM RYAD)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_96("أتوبيس 96 (اثر النبى - بولاق الدكرور)", "Bus 96 (ATER ELNEBA - BOLAQ ELDEKEREWER)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_967("أتوبيس 967 (إبراهيم بك - عتبة)", "Bus 967 (IBRAHEEM BEK - 3ETEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_970("أتوبيس 970 (شبرا الخيمه - مطار القاهرة)", "Bus 970 (SHOBRA ELKEHIMEH - MATAR ELCAHIRA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_972("أتوبيس 972 (ام بيومى - احمد حلمى)", "Bus 972 (AM BIWEMA - AHEMED HELEMA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_982("أتوبيس 982 (الحوامدية - دقى)", "Bus 982 (ELHAWAMDEYA - DE2A)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_984("أتوبيس 984 (مدينة السلام - إبراهيم بك)", "Bus 984 (MADINA ELSALAM - IBRAHEEM BEK)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_988("أتوبيس 988 (البدرشين - احمد حلمى)", "Bus 988 (ELBADRASHEEN - AHEMED HELEMA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_990("أتوبيس 990 (مساكن عين شمس - المنيب)", "Bus 990 (MASAKEN AIN SHAMS - ELMONEAB)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_992("أتوبيس 992 (أم بيومى - عتبة)", "Bus 992 (OM BIWEMA - 3ETEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_996("أتوبيس 996 (المنيب - عباسية)", "Bus 996 (ELMONEAB - 3ABASEYA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_997("أتوبيس 997 (هضبة الهرم - احمد حلمي)", "Bus 997 (HEZEBA HARAM - AHEMED HELMY)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_998("أتوبيس 998 (صقر قريش - شركة كهرباء الهرم)", "Bus 998 (SE2ER 2ERISH - SHEREKA KEHEREBE2 HARAM)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_AL3TRANSPORTATION("أتوبيس AL3TRANSPORTATION (شبرا الخيمه - إسكان شباب العبور)", "Bus AL3TRANSPORTATION (SHOBRA ELKEHIMEH - ESEKAN SHEBAB EL3ABOUR)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_AN1("أتوبيس AN1 (جنة العبور - الحى الترفيهى)", "Bus AN1 (GENA EL3ABOUR - ELHA ELTEREFIHA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_AN2("أتوبيس AN2 (كارفور العبور - جنة العبور)", "Bus AN2 (KAREFEWER EL3ABOUR - GENA EL3ABOUR)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_AN3("أتوبيس AN3 (مركز شرطة العبور - سنتر الياسمين)", "Bus AN3 (MEREKEZ SHERETA EL3ABOUR - SENETER ELYASMEEN)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_AN4("أتوبيس AN4 (الياقوت - كارفور المدينة)", "Bus AN4 (ELYE2EWET - KAREFEWER ELMEDINA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_B167("أتوبيس B167 (السادات - العباسية)", "Bus B167 (ELSADAT - EL3ABASEYA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_E20("أتوبيس E20 (كفر طهرمس - التبة)", "Bus E20 (KAFR THEREMES - ELTEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_E250("أتوبيس E250 (دائرى - صقر قريش)", "Bus E250 (DAYERA - SE2ER 2ERISH)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_E303("أتوبيس E303 (مؤسسة الزكاة - المماليك)", "Bus E303 (MOASSASA ELZEKAA - ELMEMALIK)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_G1("أتوبيس G1 (بنها - العاصمة الإدارية الجديدة)", "Bus G1 (BANHA - EL3ASEMA ELEDARIA ELGEDEDA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_G2("أتوبيس G2 (طوخ - العاصمة الإدارية الجديدة)", "Bus G2 (TOKH - EL3ASEMA ELEDARIA ELGEDEDA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_G3("أتوبيس G3 (القناطر الخيرية - العاصمة الإدارية الجديدة)", "Bus G3 (EL2ANATER ELKHEYREYA - EL3ASEMA ELEDARIA ELGEDEDA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_G58("أتوبيس G58 (العمرانية - الكيلو 4.5)", "Bus G58 (EL3OMRANEYA - ELKILU 4E.E5)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_G8("أتوبيس G8 (العمرانية - عتبة)", "Bus G8 (EL3OMRANEYA - 3ETEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_L13("أتوبيس L13 (السيدة نفيسة - مدينة الامل(عزبة الهجانة) كيلو 4.5)", "Bus L13 (ELSEYYEDA NAFEESA - MADINA ELAMELE(E3EZEBA ELHEGANA) KILU 4E.E5)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_L40("أتوبيس L40 (البراجيل - موقف التبة)", "Bus L40 (ELBERAGIL - MAW2AF ELTEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_L62("أتوبيس L62 (كلية الهندسة - النائب العام)", "Bus L62 (KOLLEYET ELHANDASA - ELNAYEB EL3AM)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_M10("أتوبيس M10 (السادات - صفاء حجازى)", "Bus M10 (ELSADAT - SEFE2 HEGAZA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_M11("أتوبيس M11 (دريم بارك - سيتى ستارز)", "Bus M11 (DERIM BAREK - SITA SETAREZ)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_M20("أتوبيس M20 (الشيخ زايد - جامعة القاهرة)", "Bus M20 (SHEIKH ZAYED - GAM3A ELCAHIRA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_M21("أتوبيس M21 (أكتوبر - عبدالمنعم رياض)", "Bus M21 (OCTOBER - 3EBEDALEMENE3EM RYAD)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_M22("أتوبيس M22 (كوبرى ثروت - حدائق أكتوبر)", "Bus M22 (KEWEBERA TEREWET - HADAEK OCTOBER)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_M26("أتوبيس M26 (التبين - العاصمة الإدارية الجديدة)", "Bus M26 (ELTEBIN - EL3ASEMA ELEDARIA ELGEDEDA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_M27("أتوبيس M27 (المعادي - العاصمة الإدارية الجديدة)", "Bus M27 (ELMAADI - EL3ASEMA ELEDARIA ELGEDEDA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_M28("أتوبيس M28 (مصر القديمه - العاصمة الإدارية الجديدة)", "Bus M28 (MESER EL2EDIMEH - EL3ASEMA ELEDARIA ELGEDEDA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_M29("أتوبيس M29 (المرج - العاصمة الإدارية الجديدة)", "Bus M29 (ELMARG - EL3ASEMA ELEDARIA ELGEDEDA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_M30("أتوبيس M30 (عين شمس - العاصمة الإدارية الجديدة)", "Bus M30 (AIN SHAMS - EL3ASEMA ELEDARIA ELGEDEDA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_M31("أتوبيس M31 (ميدان المطرية - العاصمة الإدارية الجديدة)", "Bus M31 (MAYDAN ELMATAREYA - EL3ASEMA ELEDARIA ELGEDEDA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_M5("أتوبيس M5 (التجمع الخامس - عبد المنعم رياض)", "Bus M5 (ELTAGAMOA ELKHAMES - ABDEL ELMONEEM RYAD)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_M512("أتوبيس M512 (ابراهيم بك - التجمع الخامس)", "Bus M512 (ABERAHIM BK - ELTAGAMOA ELKHAMES)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_M705("أتوبيس M705 (شارع فيصل - مدينة الأمل)", "Bus M705 (SHAREA3 FAISAL - MADINA ELAMEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_M8("أتوبيس M8 (*اتجاه ميدان الجيزة - المرحلة الثالثة/ داون تاون مول لـ اللوتس بـ 21 ج)", "Bus M8 (*ATEGAH MAYDAN ELGEZA - ELMEREHELA ELTALETA/ DAWEN TAWEN MEWEL Lـ ELLOTUS Bـ 21 G)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_M870("أتوبيس M870 (مساكن أسكو - مدرية الامن الجديدة)", "Bus M870 (MASAKEN ASKO - MEDERIA ELAMEN ELGEDEDA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_M9("أتوبيس M9 (التجمع الخامس - المعادى)", "Bus M9 (ELTAGAMOA ELKHAMES - ELME3ADA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_M900("أتوبيس M900 (قليوب - العاصمة الإدارية الجديدة)", "Bus M900 (QALYOUB - EL3ASEMA ELEDARIA ELGEDEDA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_M947("أتوبيس M947 (كلية الهندسة - دار مصر)", "Bus M947 (KOLLEYET ELHANDASA - DAR MASR)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_MC3("أتوبيس MC3 (جامعة الأزهر - مستقبل سيتي)", "Bus MC3 (GAM3A ELAZHAR - MESETE2EBEL SITEY)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_N11("أتوبيس N11 (السبت - الخميس)", "Bus N11 (ELSEBET - ELKEHEMIS)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_N3("أتوبيس N3 (صفط - أكتوبر)", "Bus N3 (SEFET - OCTOBER)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_N33("أتوبيس N33 (غرب المطار - الحصرى)", "Bus N33 (GEHEREB ELMETAR - ELHESERA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_N5("أتوبيس N5 (الحصري - ١١٨٥)", "Bus N5 (ELHOSARY - 1185)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_N77("أتوبيس N77 (حدائق أكتوبر - رمسيس)", "Bus N77 (HADAEK OCTOBER - RAMSES)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_N800("أتوبيس N800 (ربوة اكتوبر - رمسيس)", "Bus N800 (REBEWA OCTOBER - RAMSES)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_N99("أتوبيس N99 (حدائق اكتوبر - الأزبكية)", "Bus N99 (HADAEK OCTOBER - ELAZEBEKIA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_NC1("أتوبيس NC1 (القطامية - موقف التجمع الاول)", "Bus NC1 (EL2ETAMIA - MAW2AF ELTEGEME3 ELAWEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_NC10("أتوبيس NC10 (مساكن القطامية - عبد المنعم رياض)", "Bus NC10 (MASAKEN EL2ETAMIA - ABDEL ELMONEEM RYAD)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_NC2("أتوبيس NC2 (التجمع الخامس - موقف القطامية)", "Bus NC2 (ELTAGAMOA ELKHAMES - MAW2AF EL2ETAMIA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_NC3("أتوبيس NC3 (محطه اللوتس - موقف التجمع الثالث)", "Bus NC3 (MEHETH ELLOTUS - MAW2AF ELTAGAMOA ELTALET)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_NC4("أتوبيس NC4 (موقف المنطقة الصناعية - موقف التجمع الأول)", "Bus NC4 (MAW2AF ELMANTEA EL2SENAAEYA - MAW2AF ELTAGAMOA EL2WAL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_NC9("أتوبيس NC9 (دار مصر الأندلس بوابة ١ - اللوتس)", "Bus NC9 (DAR MASR ELANEDELES BEWABA 1 - ELLOTUS)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_NS5("أتوبيس NS5 (الشروق - عباسية)", "Bus NS5 (ELSHOROU2 - 3ABASEYA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_NS7("أتوبيس NS7 (مدينة الشروق - مترو سراى القبة)", "Bus NS7 (MADINA ELSHOROU2 - METRO SERAA ELKOBBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_NS9("أتوبيس NS9 (مدينة الشروق - شارع التسعين الجنوبى)", "Bus NS9 (MADINA ELSHOROU2 - SHAREA3 ELTESAEEN ELGENEWEBA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_OC1("أتوبيس OC1 (الحصري - الشيخ زايد)", "Bus OC1 (ELHOSARY - SHEIKH ZAYED)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_OC10("أتوبيس OC10 (الحى السادس - ابنى بيتك 5)", "Bus OC10 (ELHA ELSADES - ABENA BITEK 5)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_OC11("أتوبيس OC11 (ميدان الحصرى - اولاد الجيزة)", "Bus OC11 (MAYDAN ELHESERA - AWELAD ELGIZA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_OC14("أتوبيس OC14 (الحصرى - دهشور)", "Bus OC14 (ELHESERA - DEHESHEWER)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_OC17("أتوبيس OC17 (الحى السادس - دهشور)", "Bus OC17 (ELHA ELSADES - DEHESHEWER)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_OC19("أتوبيس OC19 (حدائق أكتوبر - ميدان الرماية)", "Bus OC19 (HADAEK OCTOBER - MAYDAN ELREMAIA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_OC3("أتوبيس OC3 (6 اكتوبر - الشيخ زايد)", "Bus OC3 (6 OCTOBER - SHEIKH ZAYED)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_OC4("أتوبيس OC4 (6 اكتوبر - المدرسة اليابانية)", "Bus OC4 (6 OCTOBER - ELMEDERESA ELYABANIA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_OC5("أتوبيس OC5 (6 اكتوبر - المدرسة اليابانية)", "Bus OC5 (6 OCTOBER - ELMEDERESA ELYABANIA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_OC8("أتوبيس OC8 (ميدا الحصري - اكتوبر الجديدة)", "Bus OC8 (MIDA ELHOSARY - OCTOBER ELGEDEDA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Q10("أتوبيس Q10 (ام بيومي - الحي السابع.)", "Bus Q10 (AM BIWEMEY - ELHAY ELSABE3E.)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Q100("أتوبيس Q100 (شبين القناطر - مسطرد)", "Bus Q100 (SHEBIN EL2ANATER - MOSTOROD)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Q107("أتوبيس Q107 (دائري مساكن اسكو - المطبعة)", "Bus Q107 (DAYEREY MASAKEN ASEKU - ELMATBAA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Q108("أتوبيس Q108 (مساكن أسكو - السيدة عائشة)", "Bus Q108 (MASAKEN ASKO - ELSEYYEDA AYESHA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Q115("أتوبيس Q115 (مؤسسة - موقف التجمع الاول)", "Bus Q115 (MOASSASA - MAW2AF ELTEGEME3 ELAWEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Q13("أتوبيس Q13 (كوبرى عرابى - التجمع الاول)", "Bus Q13 (KEWEBERA 3ERABA - ELTEGEME3 ELAWEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Q2("أتوبيس Q2 (شبرا الخيمه - إسكان شباب العبور)", "Bus Q2 (SHOBRA ELKEHIMEH - ESEKAN SHEBAB EL3ABOUR)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Q20("أتوبيس Q20 (المؤسسة - النزهة الجديدة)", "Bus Q20 (ELMOASSASA - ELNEZEHA ELGEDEDA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Q216("أتوبيس Q216 (صقر قريش - بشتيل)", "Bus Q216 (SE2ER 2ERISH - BASHTEEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Q22("أتوبيس Q22 (جامعة القاهرة - 6 اكتوبر)", "Bus Q22 (GAM3A ELCAHIRA - 6 OCTOBER)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Q3("أتوبيس Q3 (عبد المنعم رياض - مدينة العبور الجديدة)", "Bus Q3 (ABDEL ELMONEEM RYAD - MADINA EL3ABOUR ELGEDEDA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Q315("أتوبيس Q315 (مساكن عين شمس - بشتيل)", "Bus Q315 (MASAKEN AIN SHAMS - BASHTEEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Q316("أتوبيس Q316 (مساكن عين شمس - ميدان الجيزة)", "Bus Q316 (MASAKEN AIN SHAMS - MAYDAN ELGEZA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Q380("أتوبيس Q380 (العمرانية - زهراء مدينة نصر)", "Bus Q380 (EL3OMRANEYA - ZAHRA2 MADINET NASR)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Q45("أتوبيس Q45 (المطبعة - التجمع الاول)", "Bus Q45 (ELMATBAA - ELTEGEME3 ELAWEL)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Q5("أتوبيس Q5 (قليوب المحطة - مساكن اسكو)", "Bus Q5 (QALYOUB ELMEHETA - MASAKEN ASEKU)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Q7("أتوبيس Q7 (عمرانية - زهراء عين شمس)", "Bus Q7 (3EMERANIA - ZAHRA2 AIN SHAMS)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Q8("أتوبيس Q8 (الزاوية الحمراء - التجمع الخامس)", "Bus Q8 (ELZAWYA ELHAMRA - ELTAGAMOA ELKHAMES)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Q80("أتوبيس Q80 (كوم - الدراسة)", "Bus Q80 (KOM - ELDARASA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Q810("أتوبيس Q810 (كوبري عرابي - التجمع الخامس)", "Bus Q810 (KOBRI ORABI - ELTAGAMOA ELKHAMES)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Q815("أتوبيس Q815 (مساكن أسكو - الرحاب بوابة 6)", "Bus Q815 (MASAKEN ASKO - ELREHAB BEWABA 6)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Q9("أتوبيس Q9 (الزاويه الحمراء - العبور)", "Bus Q9 (ELZAWIH ELHAMRA - EL3ABOUR)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_S1("أتوبيس S1 (المظلات - الرحاب بوابة 1.5.6.9)", "Bus S1 (ELMAZALAT - ELREHAB BEWABA 1E.E5E.E6E.E9)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_S2("أتوبيس S2 (محطة النقل العام بالمنيب - العاصمة الإدارية الجديدة)", "Bus S2 (MAHATTA ELNA2L EL3AM BALEMENIB - EL3ASEMA ELEDARIA ELGEDEDA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_S3("أتوبيس S3 (قسم الاهرام - العاصمة الإدارية الجديدة)", "Bus S3 (2ESEM ELAHERAM - EL3ASEMA ELEDARIA ELGEDEDA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_S4("أتوبيس S4 (موقف ميدان الجيزة - العاصمة الإدارية الجديدة)", "Bus S4 (MAW2AF MAYDAN ELGEZA - EL3ASEMA ELEDARIA ELGEDEDA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_S5("أتوبيس S5 (أول - ص)", "Bus S5 (2WAL - S)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_S6("أتوبيس S6 (٦ اكتوبر - العاصمة الإدارية الجديدة)", "Bus S6 (6 OCTOBER - EL3ASEMA ELEDARIA ELGEDEDA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_S7("أتوبيس S7 (أول - ص)", "Bus S7 (2WAL - S)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_S8("أتوبيس S8 (٦ اكتوبر - جامعة القاهرة)", "Bus S8 (6 OCTOBER - GAM3A ELCAHIRA)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_T99("أتوبيس T99 (جامعة - العام)", "Bus T99 (GAM3A - EL3AM)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Z1("أتوبيس Z1 (محطة مول مزار (بدر الدين) - محطة هايبر وان)", "Bus Z1 (MAHATTA MEWEL MEZAR (EBEDER ELDINE) - MAHATTA HYPER ONE)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Z2("أتوبيس Z2 (محطة مول مزار (بدر الدين) - محطة هايبر وان)", "Bus Z2 (MAHATTA MEWEL MEZAR (EBEDER ELDINE) - MAHATTA HYPER ONE)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Z3("أتوبيس Z3 (محطة مول مزار (بدر الدين) - محطة هايبر وان)", "Bus Z3 (MAHATTA MEWEL MEZAR (EBEDER ELDINE) - MAHATTA HYPER ONE)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Z4("أتوبيس Z4 (دهشور - موقف هايبر ١)", "Bus Z4 (DEHESHEWER - MAW2AF HYPER 1)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Z5("أتوبيس Z5 (محطة مول مزار - 6 اكتوبر)", "Bus Z5 (MAHATTA MEWEL MEZAR - 6 OCTOBER)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Z6("أتوبيس Z6 (محطة مول مزار (بدر الدين) - محطة هايبر وان)", "Bus Z6 (MAHATTA MEWEL MEZAR (EBEDER ELDINE) - MAHATTA HYPER ONE)", "4CAF50", TransitMode.BUS_AUTHORITY),
    BUS_AUTH_CTA_Z7("أتوبيس Z7 (هايبر وان - حدائق الاهرام)", "Bus Z7 (HYPER ONE - HADAEK ELAHERAM)", "4CAF50", TransitMode.BUS_AUTHORITY),


    BUS_MINI_N98("ميني باص N98 (حدائق أكتوبر - رمسيس)", "Mini Bus N98 (Hadayeq October - Ramses)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_N99("ميني باص N99 (حدائق أكتوبر - التحرير) (مكيف)", "Mini Bus N99 (Hadayeq October - Tahrir) (AC)", "E53935", TransitMode.BUS_MINI),
    BUS_MINI_M5("مواصلات مصر M5 (الجامعة الأمريكية - عبد المنعم رياض) (مكيف)", "Mwasalat Misr M5 (AUC - Abd Al Moneim Riad) (AC)", "E53935", TransitMode.BUS_MINI),
    BUS_MINI_M6("مواصلات مصر M6 (الجامعة الأمريكية - المظلات) (مكيف)", "Mwasalat Misr M6 (AUC - Mezallat) (AC)", "E53935", TransitMode.BUS_MINI),
    BUS_MINI_M7("مواصلات مصر M7 (بوابات حدائق الأهرام - عبد المنعم رياض) (مكيف)", "Mwasalat Misr M7 (Hadayeq Al Ahram Gates - Abd Al Moneim Riad) (AC)", "E53935", TransitMode.BUS_MINI),
    BUS_MINI_M8("مواصلات مصر M8 (الجامعة الأمريكية - ميدان الجيزة) (مكيف)", "Mwasalat Misr M8 (AUC - Giza Square) (AC)", "E53935", TransitMode.BUS_MINI),
    BUS_MINI_M9("مواصلات مصر M9 (الجامعة الأمريكية - المعادي) (مكيف)", "Mwasalat Misr M9 (AUC - Maadi) (AC)", "E53935", TransitMode.BUS_MINI),
    BUS_MINI_M10("مواصلات مصر M10 (6 أكتوبر - الرابطة) (مكيف)", "Mwasalat Misr M10 (6th of October - El-Rabta) (AC)", "E53935", TransitMode.BUS_MINI),
    BUS_MINI_M11("مواصلات مصر M11 (الشيخ زايد - عبد المنعم رياض) (مكيف)", "Mwasalat Misr M11 (Sheikh Zayed - Abd Al Moneim Riad) (AC)", "E53935", TransitMode.BUS_MINI),
    BUS_MINI_M20("مواصلات مصر M20 (مصر الجديدة - الجامعة الأمريكية) (مكيف)", "Mwasalat Misr M20 (Heliopolis - AUC) (AC)", "E53935", TransitMode.BUS_MINI),
    BUS_MINI_1001("مواصلات مصر 1001 (الجامعة الأمريكية - عبد المنعم رياض) (مكيف)", "Mwasalat Misr 1001 (AUC - Abd Al Moneim Riad) (AC)", "E53935", TransitMode.BUS_MINI),
    BUS_MINI_1003("مواصلات مصر 1003 (بولاق الدكرور - مطار القاهرة) (مكيف)", "Mwasalat Misr 1003 (Bulaq Al Dakrur - Cairo Airport) (AC)", "E53935", TransitMode.BUS_MINI),
    BUS_MINI_12("ميني باص 12 (إمبابة - روكسي)", "Mini Bus 12 (Imbaba - Roxy)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_18("ميني باص 18 (ميدان الجيزة - رمسيس)", "Mini Bus 18 (Giza Square - Ramses)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_1005("مواصلات مصر 1005 (بولاق الدكرور - مدينتي) (مكيف)", "Mwasalat Misr 1005 (Bulaq Al Dakrur - Madinaty) (AC)", "E53935", TransitMode.BUS_MINI),
    BUS_MINI_25("ميني باص 25 (حلوان - السيدة عائشة)", "Mini Bus 25 (Helwan - Sayeda Aisha)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_35("ميني باص 35 (المرج الجديدة - مدينة العبور)", "Mini Bus 35 (New Marg - Obour City)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_42("ميني باص 42 (شبرا الخيمة - أحمد حلمي)", "Mini Bus 42 (Shubra El-Kheima - Ahmed Helmy)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_52("ميني باص 52 (الأميرية - التحرير)", "Mini Bus 52 (Amiriya - Tahrir)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_63("ميني باص 63 (بولاق الدكرور - رمسيس)", "Mini Bus 63 (Bulaq Al Dakrur - Ramses)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_72("ميني باص 72 (المعادي - ميدان الجيزة)", "Mini Bus 72 (Maadi - Giza Square)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_83("ميني باص 83 (فيصل - رمسيس)", "Mini Bus 83 (Faisal - Ramses)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_91("ميني باص 91 (الهرم - رمسيس)", "Mini Bus 91 (Haram - Ramses)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_105("ميني باص 105 (بوابات حدائق الأهرام - ميدان الجيزة)", "Mini Bus 105 (Hadayeq Al Ahram Gates - Giza Square)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_112("ميني باص 112 (شبرا الخيمة - العباسية)", "Mini Bus 112 (Shubra El-Kheima - Abbasseya)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_122("ميني باص 122 (مطار القاهرة - رمسيس)", "Mini Bus 122 (Cairo Airport - Ramses)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_135("ميني باص 135 (التجمع الخامس - رمسيس)", "Mini Bus 135 (5th Settlement - Ramses)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_144("ميني باص 144 (حدائق القبة - التحرير)", "Mini Bus 144 (Hadayeq Al Qobba - Tahrir)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_155("ميني باص 155 (المعادي - رمسيس)", "Mini Bus 155 (Maadi - Ramses)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_180("ميني باص 180 (إمبابة - ميدان الجيزة)", "Mini Bus 180 (Imbaba - Giza Square)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_199("ميني باص 199 (الشيخ زايد - ميدان الجيزة)", "Mini Bus 199 (Sheikh Zayed - Giza Square)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_205("ميني باص 205 (6 أكتوبر - ميدان الجيزة)", "Mini Bus 205 (6th of October - Giza Square)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_256("ميني باص 256 (الهرم - التحرير)", "Mini Bus 256 (Haram - Tahrir)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_300("ميني باص 300 (المرج الجديدة - روكسي)", "Mini Bus 300 (New Marg - Roxy)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_309("ميني باص 309 (أحمد حلمي - مطار القاهرة)", "Mini Bus 309 (Ahmed Helmy - Cairo Airport)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_312("ميني باص 312 (المظلات - العباسية)", "Mini Bus 312 (Mezallat - Abbasseya)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_320("ميني باص 320 (مصر الجديدة - التجمع الخامس)", "Mini Bus 320 (Heliopolis - 5th Settlement)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_350("ميني باص 350 (العباسية - التجمع الخامس)", "Mini Bus 350 (Abbasseya - 5th Settlement)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_410("ميني باص 410 (رمسيس - مدينة الشروق)", "Mini Bus 410 (Ramses - Shorouk City)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_420("ميني باص 420 (التحرير - مدينتي) (مكيف)", "Mini Bus 420 (Tahrir - Madinaty) (AC)", "E53935", TransitMode.BUS_MINI),
    BUS_MINI_450("ميني باص 450 (موقف العاشر - مدينة الشروق)", "Mini Bus 450 (10th of Ramadan Stop - Shorouk City)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_505("ميني باص 505 (حلوان - رمسيس)", "Mini Bus 505 (Helwan - Ramses)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_600("ميني باص 600 (6 أكتوبر - ميدان الجيزة)", "Mini Bus 600 (6th of October - Giza Square)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_700("ميني باص 700 (حدائق أكتوبر - ميدان الجيزة)", "Mini Bus 700 (Hadayeq October - Giza Square)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_810("ميني باص 810 (الوراق - التحرير)", "Mini Bus 810 (Warraq - Tahrir)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_820("ميني باص 820 (البراجيل - ميدان الجيزة)", "Mini Bus 820 (Barageel - Giza Square)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_850("ميني باص 850 (بولاق الدكرور - ميدان الجيزة)", "Mini Bus 850 (Bulaq Al Dakrur - Giza Square)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_910("ميني باص 910 (المرج الجديدة - موقف العاشر)", "Mini Bus 910 (New Marg - 10th of Ramadan Stop)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_920("ميني باص 920 (عين شمس - التحرير)", "Mini Bus 920 (Ain Shams - Tahrir)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_980("ميني باص 980 (حدائق أكتوبر - ميدان الجيزة)", "Mini Bus 980 (Hadayeq October - Giza Square)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_10("ميني باص 10 (ابراهيم بك - التجمع الاول)", "Mini Bus 10 (ABERAHIM BK - ELTEGEME3 ELAWEL)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_106("ميني باص 106 (موقف السلام الجديد - عتبة)", "Mini Bus 106 (MAW2AF ELSALAM ELGEDEED - 3ETEBA)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_107("ميني باص 107 (مساكن أسكو - المطبعة)", "Mini Bus 107 (MASAKEN ASKO - ELMATBAA)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_123("ميني باص 123 (موقف الرماية - التبة)", "Mini Bus 123 (MAW2AF ELREMAYA - ELTEBA)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_130("ميني باص 130 (مظلات - دار مصر)", "Mini Bus 130 (MAZALAT - DAR MASR)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_131("ميني باص 131 (دوران شبرا - التجمع الاول)", "Mini Bus 131 (Dawaran Shubra - ELTEGEME3 ELAWEL)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_138("ميني باص 138 (تحرير - الرحاب بوابة 1.6.23)", "Mini Bus 138 (TAHRIR - ELREHAB BEWABA 1E.E6E.E2E3)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_142("ميني باص 142 (شبرامنت - جاردينيا)", "Mini Bus 142 (SHOBRAMANT - GAREDINIA)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_151("ميني باص 151 (المرج الجديده - احمد حلمى)", "Mini Bus 151 (ELMARG ELGEDIDEH - AHEMED HELEMA)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_20("ميني باص 20 (الحوامدية - جامعة القاهرة)", "Mini Bus 20 (ELHAWAMDEYA - GAM3ET ELQAHERA)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_211("ميني باص 211 (كفر طهرمس - أسبيكو)", "Mini Bus 211 (KAFR THEREMES - ASEBIKU)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_22("ميني باص 22 (ابو النمرس - باب الشعرية)", "Mini Bus 22 (ABU ELNEMERES - BAB ELSHA3REYA)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_255("ميني باص 255 (المسرح القومي - أحمد ماهر)", "Mini Bus 255 (MASRA7 ELQAWMY - AHMED MAHER)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_302("ميني باص 302 (المماليك - موقف التجمع الأول)", "Mini Bus 302 (ELMEMALIK - MAW2AF ELTAGAMOA EL2WAL)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_304("ميني باص 304 (المظلات - التجمع الاول)", "Mini Bus 304 (Mezallat - ELTEGEME3 ELAWEL)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_308("ميني باص 308 (الخصوص - الرحاب بوابة 6)", "Mini Bus 308 (ELKHOSOUS - ELREHAB BEWABA 6)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_318("ميني باص 318 (مساكن عين شمس - مدرية الأمن الجديدة)", "Mini Bus 318 (MASAKEN AIN SHAMS - MEDERIA ELAMEN ELGEDEDA)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_32("ميني باص 32 (أبو النمرس - الزاوية الحمراء)", "Mini Bus 32 (ABU ELNOMROS - ELZAWYA ELHAMRA)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_341("ميني باص 341 (١٥ مايو - التجمع الخامس)", "Mini Bus 341 (15 MAIU - 5th Settlement)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_343("ميني باص 343 (15 مايو - عبدالمنعم رياض)", "Mini Bus 343 (15 MAYO - 3EBEDALEMENE3EM RYAD)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_4("ميني باص 4 (مدينة بدر - مسطرد)", "Mini Bus 4 (MADINA BADR - MOSTOROD)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_424("ميني باص 424 (زهراء مدينة نصر - العمرانية)", "Mini Bus 424 (ZAHRA2 MADINET NASR - EL3OMRANEYA)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_45("ميني باص 45 (المطبعة - موقف التجمع الاول)", "Mini Bus 45 (ELMATBAA - MAW2AF ELTEGEME3 ELAWEL)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_500("ميني باص 500 (موقف حدائق القبة الحضاري - المطبعة)", "Mini Bus 500 (MAW2AF HADAEK ELKOBBA ELHADARY - ELMATBAA)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_53("ميني باص 53 (صفط اللبن - التجمع الخامس)", "Mini Bus 53 (Saft Al Laban - 5th Settlement)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_555("ميني باص 555 (الخصوص - المماليك)", "Mini Bus 555 (ELKHOSOUS - ELMEMALIK)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_57("ميني باص 57 (صفط اللبن - التبة)", "Mini Bus 57 (Saft Al Laban - ELTEBA)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_580("ميني باص 580 (بيجام - عتبة)", "Mini Bus 580 (BIGAM - 3ETEBA)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_662("ميني باص 662 (احمد حلمي - النائب العام)", "Mini Bus 662 (AHEMED HELMY - ELNAYEB EL3AM)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_666("ميني باص 666 (مدينة بدر - عبدالمنعم رياض)", "Mini Bus 666 (MADINA BADR - 3EBEDALEMENE3EM RYAD)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_708("ميني باص 708 (تحرير - مدرية أمن القاهرة الجديدة)", "Mini Bus 708 (TAHRIR - MEDERIA AMEN ELCAHIRA ELGEDEDA)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_777("ميني باص 777 (عرب الطوايله - دار مصر)", "Mini Bus 777 (ARAB ELTEWAILEH - DAR MASR)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_779("ميني باص 779 (ابراهيم بك - دار مصر)", "Mini Bus 779 (ABERAHIM BK - DAR MASR)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_79("ميني باص 79 (عرب الطوايلة - البساتين)", "Mini Bus 79 (ARAB ELTEWAILA - ELBESATIN)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_80("ميني باص 80 (مساكن أسكو - الدراسة)", "Mini Bus 80 (MASAKEN ASKO - ELDARASA)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_84("ميني باص 84 (احمد حلمى - التبة)", "Mini Bus 84 (AHEMED HELEMA - ELTEBA)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_88("ميني باص 88 (مساكن أسكو - السيدة عائشة)", "Mini Bus 88 (MASAKEN ASKO - Sayeda Aisha)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_9("ميني باص 9 (اسبيكو - عتبة)", "Mini Bus 9 (ASEBIKU - 3ETEBA)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_93("ميني باص 93 (البساتين - مظلات)", "Mini Bus 93 (ELBESATIN - MAZALAT)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_L13("ميني باص L13 (السيدة نفيسة - مدينة الامل(عزبة الهجانة) كيلو 4.5)", "Mini Bus L13 (ELSEYYEDA NAFEESA - MADINA ELAMELE(E3EZEBA ELHEGANA) KILU 4E.E5)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_L40("ميني باص L40 (البراجيل - موقف التبة)", "Mini Bus L40 (Barageel - MAW2AF ELTEBA)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_L62("ميني باص L62 (كلية الهندسة - النائب العام)", "Mini Bus L62 (KOLLEYET ELHANDASA - ELNAYEB EL3AM)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_M512("ميني باص M512 (ابراهيم بك - التجمع الخامس)", "Mini Bus M512 (ABERAHIM BK - 5th Settlement)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_M87("ميني باص M87 (البدرشين - المقطم)", "Mini Bus M87 (ELBADRASHEEN - ELMO2ATTAM)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_N11("ميني باص N11 (أكتوبر الجديدة - قسم الأزبكية)", "Mini Bus N11 (OCTOBER ELGEDEDA - QISM ELAZBAKEYA)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_N3("ميني باص N3 (صفط اللبن - 6 أكتوبر)", "Mini Bus N3 (Saft Al Laban - 6th of October)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_N5("ميني باص N5 (الحصري - ١١٨٥)", "Mini Bus N5 (Hosary Square - 1185)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_N800("ميني باص N800 (ربوة اكتوبر - رمسيس)", "Mini Bus N800 (REBEWA OCTOBER - Ramses)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_NC10("ميني باص NC10 (مساكن القطامية - عبد المنعم رياض)", "Mini Bus NC10 (MASAKEN EL2ETAMIA - Abd Al Moneim Riad)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_NC3("ميني باص NC3 (محطه اللوتس - موقف التجمع الثالث)", "Mini Bus NC3 (MEHETH ELLOTUS - MAW2AF ELTAGAMOA ELTALET)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_Q10("ميني باص Q10 (ام بيومي - الحي السابع)", "Mini Bus Q10 (AM BIWEMEY - 7th District)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_Q107("ميني باص Q107 (دائري مساكن اسكو - المطبعة)", "Mini Bus Q107 (DAYEREY MASAKEN ASEKU - ELMATBAA)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_Q115("ميني باص Q115 (مؤسسة - موقف التجمع الاول)", "Mini Bus Q115 (MOASSASA - MAW2AF ELTEGEME3 ELAWEL)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_Q22("ميني باص Q22 (جامعة القاهرة - 6 اكتوبر)", "Mini Bus Q22 (GAM3ET ELQAHERA - 6 OCTOBER)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_Q316("ميني باص Q316 (مساكن عين شمس - ميدان الجيزة)", "Mini Bus Q316 (MASAKEN AIN SHAMS - Giza Square)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_Q380("ميني باص Q380 (العمرانية - زهراء مدينة نصر)", "Mini Bus Q380 (EL3OMRANEYA - ZAHRA2 MADINET NASR)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_Q5("ميني باص Q5 (قليوب المحطة - مساكن اسكو)", "Mini Bus Q5 (QALYOUB ELMEHETA - MASAKEN ASEKU)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_Q80("ميني باص Q80 (كوم أشفين - الدراسة)", "Mini Bus Q80 (KOM ASHEFIN - ELDARASA)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_Q810("ميني باص Q810 (كوبري عرابي - التجمع الخامس)", "Mini Bus Q810 (KOBRI ORABI - 5th Settlement)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_Q815("ميني باص Q815 (مساكن أسكو - الرحاب بوابة 6)", "Mini Bus Q815 (MASAKEN ASKO - ELREHAB BEWABA 6)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_S1("ميني باص S1 (المظلات - الرحاب بوابة 1.5.6.9)", "Mini Bus S1 (Mezallat - ELREHAB BEWABA 1E.E5E.E6E.E9)", "1E88E5", TransitMode.BUS_MINI),
    BUS_MINI_T99("ميني باص T99 (جامعة القاهرة - العام)", "Mini Bus T99 (GAM3ET ELQAHERA - EL3AM)", "1E88E5", TransitMode.BUS_MINI),


    SUPER_JET_GO_BUS("جو باص", "Go Bus", "E53935", TransitMode.SUPER_JET),
    SUPER_JET_BLUE("بلو باص", "Blue Bus", "1E88E5", TransitMode.SUPER_JET),
    SUPER_JET_WEST("ويست باص", "West Bus", "43A047", TransitMode.SUPER_JET),
    SUPER_JET_EAST("ايست باص", "East Bus", "8E24AA", TransitMode.SUPER_JET),
    SUPER_JET_HAPPY("هابي باص", "Happy Bus", "FF9800", TransitMode.SUPER_JET),
    SUPER_JET_EAST_DELTA("شرق الدلتا", "East Delta", "2E7D32", TransitMode.SUPER_JET),

    // Microbus
    MICROBUS_CAIRO("مواقف القاهرة الكبرى", "Greater Cairo Terminals", "FF5722", TransitMode.MICROBUS),
    MICROBUS_ALEX("مواقف الإسكندرية", "Alexandria Terminals", "3F51B5", TransitMode.MICROBUS),
    MICROBUS_DELTA("مواقف محافظات الدلتا", "Delta Terminals", "4CAF50", TransitMode.MICROBUS),
    MICROBUS_UPPER_EGYPT("مواقف محافظات الصعيد", "Upper Egypt Terminals", "FFC107", TransitMode.MICROBUS)
}

enum class StationStatus { ACTIVE, COMING_SOON }

data class Train(
    val id: String,
    val number: String,
    val nameAr: String,
    val nameEn: String,
    val lineId: String,
    val type: String,
    val directionAr: String,
    val directionEn: String,
    val departureTime: String,
    val arrivalTime: String,
    val durationMinutes: Int,
    val fare: Double,
    val stationIds: List<String>
)

data class SuperJetDestination(
    val id: String,
    val nameAr: String,
    val nameEn: String,
    val stationIds: List<String>
)

data class SuperJetTrip(
    val id: String,
    val companyId: String,
    val sourceId: String,
    val destId: String,
    val departureTime: String,
    val arrivalTime: String,
    val durationMinutes: Int = 0,
    val fare: Double,
    val sourceTerminalAr: String = "",
    val sourceTerminalEn: String = "",
    val destTerminalAr: String = "",
    val destTerminalEn: String = ""
)

data class Station(
    val id: String,
    val nameAr: String,
    val nameEn: String,
    val line: TransportLine,
    val sequentialNumber: Int,
    val mapX: Float,
    val mapY: Float,
    val addressAr: String,
    val addressEn: String,
    val connectionAr: String? = null,
    val connectionEn: String? = null,
    val landmarksAr: List<String>,
    val landmarksEn: List<String>,
    val guideTipAr: String,
    val guideTipEn: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val fareInfo: String? = null,
    val operatingHours: String? = null,
    val status: StationStatus = StationStatus.ACTIVE,
    val connectsTo: List<String> = emptyList()
)

data class AppNotification(
    val id: String,
    val titleAr: String,
    val titleEn: String,
    val bodyAr: String,
    val bodyEn: String,
    val timestamp: String
)

val TransportLine.dynamicColorHex: String
    get() = MonorailData.lineColors[this.name] ?: this.colorHex

val Station.dynamicColorHex: String
    get() = MonorailData.stationColors[this.id] ?: this.line.dynamicColorHex

val Station.dynamicStatus: StationStatus
    get() = MonorailData.stationStatusOverrides[this.id] ?: this.status

object MonorailData {
    const val ASSETS_VERSION = 116
    var farePerZone1 = 10.0

    var farePerZone2 = 12.0

    var farePerZone3 = 15.0

    var farePerZone4 = 20.0
    var fareMonorail1 = 20.0
    var fareMonorail2 = 40.0
    var fareMonorail3 = 55.0
    var fareMonorail4 = 80.0
    var fareLRT1 = 15.0
    var fareLRT2 = 25.0
    var fareLRT3 = 35.0
    var fareLRT4 = 45.0
    var fareBRT1 = 10.0
    var fareBRT2 = 20.0
    var fareBRT3 = 30.0
    var defaultGreetingAr = "طاب يومك وسفرك"
    var defaultGreetingEn = "Enjoy your trip!"
    var showBannerNotice = false
    var bannerNoticeAr = ""
    var bannerNoticeEn = ""
    var admobEnabled = true
    var instapayAccount = "01000000000"
    var donationTextAr = "ادعم تطوير التطبيق"
    var donationTextEn = "Support App Development"

    // Ride Hailing calculator data
    var uberPricePerKm = 12.0
    var careemPricePerKm = 14.0
    var didiPricePerKm = 10.0
    var inDrivePricePerKm = 11.0
    var boltPricePerKm = 13.0

    // Social media links
    var socialLinks: List<SocialLink> = emptyList()

    // Ride-hailing companies with tips
    var rideHailingCompanies: List<RideCompany> = emptyList()

    data class SocialLink(val platform: String, val url: String, val iconUrl: String = "")

    data class RideCompany(
        val id: String,
        val nameAr: String,
        val nameEn: String,
        val pricePerKm: Double,
        val phone: String = "",
        val website: String = "",
        val tipsAr: List<String> = emptyList(),
        val tipsEn: List<String> = emptyList()
    )

    // lineConfigs override: line name -> isOpen
    var lineConfigs: Map<String, Boolean> = emptyMap()
    var lineColors: Map<String, String> = emptyMap()
    var stationColors: Map<String, String> = emptyMap()
    var stationStatusOverrides: Map<String, StationStatus> = emptyMap()
    var notifications: List<AppNotification> = emptyList()

    fun isLineOpen(line: TransportLine): Boolean {
        return lineConfigs[line.name] ?: line.isOpen
    }

    fun initializeFromJson(jsonString: String) {
        val root = org.json.JSONObject(jsonString)

        // Track the data version to prevent remote overwrite with older data
        val incomingVersion = root.optInt("dataVersion", 0)
        if (incomingVersion < loadedVersion) {
            android.util.Log.w("MonorailData", "Skipping older data version $incomingVersion < $loadedVersion")
            return
        }

        // To ensure safety, parse all components into local variables first.
        // If any exception occurs during parsing (e.g. malformed JSON, type mismatch, missing keys),
        // it throws an exception, keeping the previous loaded state fully intact.
        val appConfig = root.getJSONObject("appConfig")
        val tempFarePerZone1 = appConfig.optDouble("farePerZone1", 6.0)
        val tempFarePerZone2 = appConfig.optDouble("farePerZone2", 8.0)
        val tempFarePerZone3 = appConfig.optDouble("farePerZone3", 12.0)
        val tempFarePerZone4 = appConfig.optDouble("farePerZone4", 15.0)
        val tempFareMonorail1 = appConfig.optDouble("fareMonorail1", 20.0)
        val tempFareMonorail2 = appConfig.optDouble("fareMonorail2", 40.0)
        val tempFareMonorail3 = appConfig.optDouble("fareMonorail3", 55.0)
        val tempFareMonorail4 = appConfig.optDouble("fareMonorail4", 80.0)
        val tempFareLRT1 = appConfig.optDouble("fareLRT1", 15.0)
        val tempFareLRT2 = appConfig.optDouble("fareLRT2", 25.0)
        val tempFareLRT3 = appConfig.optDouble("fareLRT3", 35.0)
        val tempFareLRT4 = appConfig.optDouble("fareLRT4", 45.0)
        val tempFareBRT1 = appConfig.optDouble("fareBRT1", 10.0)
        val tempFareBRT2 = appConfig.optDouble("fareBRT2", 20.0)
        val tempFareBRT3 = appConfig.optDouble("fareBRT3", 30.0)
        val tempFareCtaNormal = appConfig.optDouble("fareCtaNormal", 13.0)
        val tempFareCtaAc = appConfig.optDouble("fareCtaAc", 25.0)
        val tempFareMiniNormal = appConfig.optDouble("fareMiniNormal", 19.0)
        val tempFareMiniAc = appConfig.optDouble("fareMiniAc", 25.0)
        val tempDefaultGreetingAr = appConfig.optString("defaultGreetingAr", "طاب يومك وسفرك")
        val tempDefaultGreetingEn = appConfig.optString("defaultGreetingEn", "Enjoy your trip!")
        val tempShowBannerNotice = appConfig.optBoolean("showBannerNotice", false)
        val tempBannerNoticeAr = appConfig.optString("bannerNoticeAr", "")
        val tempBannerNoticeEn = appConfig.optString("bannerNoticeEn", "")
        val tempAdmobEnabled = appConfig.optBoolean("admobEnabled", true)
        val tempInstapayAccount = appConfig.optString("instapayAccount", "01000000000")
        val tempDonationTextAr = appConfig.optString("donationTextAr", "ادعم تطوير التطبيق")
        val tempDonationTextEn = appConfig.optString("donationTextEn", "Support App Development")
        val tempUberPricePerKm = appConfig.optDouble("uberPricePerKm", 12.0)
        val tempCareemPricePerKm = appConfig.optDouble("careemPricePerKm", 14.0)
        val tempDidiPricePerKm = appConfig.optDouble("didiPricePerKm", 10.0)
        val tempInDrivePricePerKm = appConfig.optDouble("inDrivePricePerKm", 11.0)
        val tempBoltPricePerKm = appConfig.optDouble("boltPricePerKm", 13.0)

        // Parse social links
        val tempSocialLinks: List<SocialLink>
        val slArr = root.optJSONArray("socialLinks")
        if (slArr != null) {
            val list = mutableListOf<SocialLink>()
            for (i in 0 until slArr.length()) {
                val obj = slArr.getJSONObject(i)
                list.add(SocialLink(
                    platform = obj.getString("platform"),
                    url = obj.getString("url"),
                    iconUrl = obj.optString("iconUrl", "")
                ))
            }
            tempSocialLinks = list
        } else {
            tempSocialLinks = emptyList()
        }

        // Parse ride-hailing companies
        val tempRideCompanies: List<RideCompany>
        val rcArr = root.optJSONArray("rideCompanies")
        if (rcArr != null) {
            val list = mutableListOf<RideCompany>()
            for (i in 0 until rcArr.length()) {
                val obj = rcArr.getJSONObject(i)
                val tipsArList = mutableListOf<String>()
                val ta = obj.optJSONArray("tipsAr")
                if (ta != null) { for (j in 0 until ta.length()) { tipsArList.add(ta.getString(j)) } }
                val tipsEnList = mutableListOf<String>()
                val te = obj.optJSONArray("tipsEn")
                if (te != null) { for (j in 0 until te.length()) { tipsEnList.add(te.getString(j)) } }
                list.add(RideCompany(
                    id = obj.getString("id"),
                    nameAr = obj.getString("nameAr"),
                    nameEn = obj.getString("nameEn"),
                    pricePerKm = obj.getDouble("pricePerKm"),
                    phone = obj.optString("phone", ""),
                    website = obj.optString("website", ""),
                    tipsAr = tipsArList,
                    tipsEn = tipsEnList
                ))
            }
            tempRideCompanies = list
        } else {
            tempRideCompanies = emptyList()
        }

        // Parse lineConfigs and lineColors
        val tempLineConfigs: Map<String, Boolean>
        val tempLineColors: Map<String, String>
        val lcObj = root.optJSONObject("lineConfigs")
        if (lcObj != null) {
            val lcMap = mutableMapOf<String, Boolean>()
            val colorsMap = mutableMapOf<String, String>()
            for (key in lcObj.keys()) {
                val lineObj = lcObj.optJSONObject(key)
                if (lineObj != null) {
                    lcMap[key] = lineObj.optBoolean("isOpen", true)
                    if (lineObj.has("colorHex")) {
                        colorsMap[key] = lineObj.getString("colorHex")
                    }
                }
            }
            tempLineConfigs = lcMap
            tempLineColors = colorsMap
        } else {
            tempLineConfigs = emptyMap()
            tempLineColors = emptyMap()
        }

        // Parse stationColors
        val tempStationColors: Map<String, String>
        val scObj = root.optJSONObject("stationColors")
        if (scObj != null) {
            val scMap = mutableMapOf<String, String>()
            for (key in scObj.keys()) {
                scMap[key] = scObj.getString(key)
            }
            tempStationColors = scMap
        } else {
            tempStationColors = emptyMap()
        }

        // Parse stationStatusOverrides
        val tempStationStatusOverrides: Map<String, StationStatus>
        val ssoObj = root.optJSONObject("stationStatusOverrides")
        if (ssoObj != null) {
            val ssoMap = mutableMapOf<String, StationStatus>()
            for (key in ssoObj.keys()) {
                val rawStat = ssoObj.getString(key)
                val stat = try { StationStatus.valueOf(rawStat) } catch (_: Exception) { StationStatus.ACTIVE }
                ssoMap[key] = stat
            }
            tempStationStatusOverrides = ssoMap
        } else {
            tempStationStatusOverrides = emptyMap()
        }

        // Parse notifications
        val tempNotifications: List<AppNotification>
        val notifArr = root.optJSONArray("notifications")
        if (notifArr != null) {
            val list = mutableListOf<AppNotification>()
            for (i in 0 until notifArr.length()) {
                val n = notifArr.getJSONObject(i)
                list.add(AppNotification(
                    id = n.getString("id"),
                    titleAr = n.getString("titleAr"),
                    titleEn = n.getString("titleEn"),
                    bodyAr = n.getString("bodyAr"),
                    bodyEn = n.getString("bodyEn"),
                    timestamp = n.optString("timestamp", "")
                ))
            }
            tempNotifications = list
        } else {
            tempNotifications = emptyList()
        }

        val stationsArray = root.getJSONArray("stations")
        val tempStations = mutableListOf<Station>()
        for (i in 0 until stationsArray.length()) {
            val sObj = stationsArray.getJSONObject(i)
            val lineId = sObj.getString("lineId")
            val lineEnum = try {
                TransportLine.valueOf(lineId)
            } catch (e: Exception) {
                continue
            }

            val landmarksArList = mutableListOf<String>()
            val lAr = sObj.optJSONArray("landmarksAr")
            if (lAr != null) {
                for (j in 0 until lAr.length()) {
                    landmarksArList.add(lAr.getString(j))
                }
            }

            val landmarksEnList = mutableListOf<String>()
            val lEn = sObj.optJSONArray("landmarksEn")
            if (lEn != null) {
                for (j in 0 until lEn.length()) {
                    landmarksEnList.add(lEn.getString(j))
                }
            }

            val rawStatus = sObj.optString("status", "ACTIVE")
            val stat = try { StationStatus.valueOf(rawStatus) } catch (_: Exception) { StationStatus.ACTIVE }

            val connectsToList = mutableListOf<String>()
            val ctArr = sObj.optJSONArray("connectsTo")
            if (ctArr != null) {
                for (j in 0 until ctArr.length()) {
                    connectsToList.add(ctArr.getString(j))
                }
            }

            val station = Station(
                id = sObj.getString("id"),
                nameAr = sObj.getString("nameAr"),
                nameEn = sObj.getString("nameEn"),
                line = lineEnum,
                sequentialNumber = sObj.getInt("sequentialNumber"),
                mapX = sObj.optDouble("mapX", 0.0).toFloat(),
                mapY = sObj.optDouble("mapY", 0.0).toFloat(),
                addressAr = sObj.optString("addressAr", ""),
                addressEn = sObj.optString("addressEn", ""),
                connectionAr = if (sObj.isNull("connectionAr")) null else sObj.getString("connectionAr"),
                connectionEn = if (sObj.isNull("connectionEn")) null else sObj.getString("connectionEn"),
                landmarksAr = landmarksArList,
                landmarksEn = landmarksEnList,
                guideTipAr = sObj.optString("guideTipAr", ""),
                guideTipEn = sObj.optString("guideTipEn", ""),
                latitude = if (sObj.has("latitude")) sObj.getDouble("latitude") else null,
                longitude = if (sObj.has("longitude")) sObj.getDouble("longitude") else null,
                fareInfo = if (sObj.has("fareInfo") && !sObj.isNull("fareInfo")) sObj.getString("fareInfo") else null,
                operatingHours = if (sObj.has("operatingHours") && !sObj.isNull("operatingHours")) sObj.getString("operatingHours") else null,
                status = stat,
                connectsTo = connectsToList
            )
            tempStations.add(station)
        }

        // Parse trains
        val tempTrains = mutableListOf<Train>()
        val trainsArray = root.optJSONArray("trains")
        if (trainsArray != null) {
            for (i in 0 until trainsArray.length()) {
                val t = trainsArray.getJSONObject(i)
                val stationIdsArr = t.optJSONArray("stationIds")
                val sIds = mutableListOf<String>()
                if (stationIdsArr != null) {
                    for (j in 0 until stationIdsArr.length()) {
                        sIds.add(stationIdsArr.getString(j))
                    }
                }
                tempTrains.add(Train(
                    id = t.getString("id"),
                    number = t.getString("number"),
                    nameAr = t.optString("nameAr", ""),
                    nameEn = t.optString("nameEn", ""),
                    lineId = t.getString("lineId"),
                    type = t.optString("type", ""),
                    directionAr = t.optString("directionAr", ""),
                    directionEn = t.optString("directionEn", ""),
                    departureTime = t.optString("departureTime", ""),
                    arrivalTime = t.optString("arrivalTime", ""),
                    durationMinutes = t.optInt("durationMinutes", 0),
                    fare = t.optDouble("fare", 0.0),
                    stationIds = sIds
                ))
            }
        }

        // Parse Super Jet trips
        val tempSuperjetTrips = mutableListOf<SuperJetTrip>()
        val sjArray = root.optJSONArray("superjet_trips")
        if (sjArray != null) {
            for (i in 0 until sjArray.length()) {
                val t = sjArray.getJSONObject(i)
                tempSuperjetTrips.add(SuperJetTrip(
                    id = t.getString("id"),
                    companyId = t.getString("companyId"),
                    sourceId = t.getString("sourceId"),
                    destId = t.getString("destId"),
                    departureTime = t.optString("departureTime", ""),
                    arrivalTime = t.optString("arrivalTime", ""),
                    durationMinutes = t.optInt("durationMinutes", 0),
                    fare = t.optDouble("fare", 0.0),
                    sourceTerminalAr = t.optString("sourceTerminalAr", ""),
                    sourceTerminalEn = t.optString("sourceTerminalEn", ""),
                    destTerminalAr = t.optString("destTerminalAr", ""),
                    destTerminalEn = t.optString("destTerminalEn", "")
                ))
            }
        }

        // Apply fields only when parsing completed completely without any exception
        loadedVersion = incomingVersion
        farePerZone1 = tempFarePerZone1
        farePerZone2 = tempFarePerZone2
        farePerZone3 = tempFarePerZone3
        farePerZone4 = tempFarePerZone4
        fareMonorail1 = tempFareMonorail1
        fareMonorail2 = tempFareMonorail2
        fareMonorail3 = tempFareMonorail3
        fareMonorail4 = tempFareMonorail4
        fareLRT1 = tempFareLRT1
        fareLRT2 = tempFareLRT2
        fareLRT3 = tempFareLRT3
        fareLRT4 = tempFareLRT4
        fareBRT1 = tempFareBRT1
        fareBRT2 = tempFareBRT2
        fareBRT3 = tempFareBRT3
        fareCtaNormal = tempFareCtaNormal
        fareCtaAc = tempFareCtaAc
        fareMiniNormal = tempFareMiniNormal
        fareMiniAc = tempFareMiniAc
        defaultGreetingAr = tempDefaultGreetingAr
        defaultGreetingEn = tempDefaultGreetingEn
        showBannerNotice = tempShowBannerNotice
        bannerNoticeAr = tempBannerNoticeAr
        bannerNoticeEn = tempBannerNoticeEn
        admobEnabled = tempAdmobEnabled
        instapayAccount = tempInstapayAccount
        donationTextAr = tempDonationTextAr
        donationTextEn = tempDonationTextEn
        uberPricePerKm = tempUberPricePerKm
        careemPricePerKm = tempCareemPricePerKm
        didiPricePerKm = tempDidiPricePerKm
        inDrivePricePerKm = tempInDrivePricePerKm
        boltPricePerKm = tempBoltPricePerKm

        socialLinks = tempSocialLinks
        rideHailingCompanies = tempRideCompanies

        lineConfigs = tempLineConfigs
        lineColors = tempLineColors
        stationColors = tempStationColors
        stationStatusOverrides = tempStationStatusOverrides
        notifications = tempNotifications

        if (tempStations.isNotEmpty()) {
            allStations = tempStations
            try {
                val microbusDefaults = MicrobusTerminals.getAll()
                val existingIds = tempStations.map { it.id }.toSet()
                val newMicrobus = microbusDefaults.filter { it.id !in existingIds }
                allStations = tempStations + newMicrobus
            } catch (e: Exception) {
                android.util.Log.e("MonorailData", "Failed to add microbus terminals", e)
            }
            try {
                val railwayStations = EgyptRailwayData.getRailwayStations()
                val existingIds2 = allStations.map { it.id }.toSet()
                val newRailwayStations = railwayStations.filter { it.id !in existingIds2 }
                allStations = allStations + newRailwayStations
            } catch (e: Exception) {
                android.util.Log.e("MonorailData", "Failed to add railway stations", e)
            }
            stationsByLine = allStations.groupBy { it.line }
        }
        if (tempTrains.isNotEmpty()) {
            trains = tempTrains
        } else {
            trains = emptyList()
        }
        try {
            val railwayTrains = EgyptRailwayData.getAllTrains()
            val existingTrainIds = trains.map { it.id }.toSet()
            val newRailwayTrains = railwayTrains.filter { it.id !in existingTrainIds }
            trains = trains + newRailwayTrains
        } catch (e: Exception) {
            android.util.Log.e("MonorailData", "Failed to add railway trains", e)
        }
        trainsByLine = trains.groupBy { it.lineId }
        if (tempSuperjetTrips.isNotEmpty()) {
            superjetTrips = tempSuperjetTrips
            superjetTripsByCompany = superjetTrips.groupBy { it.companyId }
        }

        android.util.Log.d("MonorailData", "Atomic loading complete for version $incomingVersion: ${allStations.size} stations.")
    }

        var fareCtaNormal = 13.0
    var fareCtaAc = 25.0
    var fareMiniNormal = 19.0
    var fareMiniAc = 25.0
    var loadedVersion = 0
    var allStations = listOf<Station>()
    var stationsByLine = emptyMap<TransportLine, List<Station>>()
    var trains = listOf<Train>()
    var trainsByLine = emptyMap<String, List<Train>>()
    var superjetTrips = listOf<SuperJetTrip>()
    var superjetTripsByCompany = emptyMap<String, List<SuperJetTrip>>()

    val superjetDestinations = listOf(
        SuperJetDestination("CAIRO", "القاهرة", "Cairo", listOf("SJ1", "SJ4", "SJ5")),
        SuperJetDestination("ALEX", "الإسكندرية", "Alexandria", listOf("SJ2")),
        SuperJetDestination("HURG", "الغردقة", "Hurghada", listOf("SJ3")),
        SuperJetDestination("SHRM", "شرم الشيخ", "Sharm El-Sheikh", listOf("SJ6")),
        SuperJetDestination("LUXOR", "الأقصر", "Luxor", listOf("SJ_LUX")),
        SuperJetDestination("ASWAN", "أسوان", "Aswan", listOf("SJ_ASW")),
        SuperJetDestination("MANSOURA", "المنصورة", "Mansoura", listOf("SJ_MAN")),
        SuperJetDestination("FAYOUM", "الفيوم", "Fayoum", listOf("SJ_FAY")),
        SuperJetDestination("PORT_SAID", "بورسعيد", "Port Said", listOf("SJ_PSD")),
        SuperJetDestination("SUEZ", "السويس", "Suez", listOf("SJ_SUE")),
        SuperJetDestination("ISMAILIA", "الإسماعيلية", "Ismailia", listOf("SJ_ISM")),
        SuperJetDestination("MATROUH", "مرسى مطروح", "Marsa Matrouh", listOf("SJ_MAT")),
        SuperJetDestination("SOHAG", "سوهاج", "Sohag", listOf("SJ_SOH")),
        SuperJetDestination("ASYUT", "أسيوط", "Asyut", listOf("SJ_ASY")),
        SuperJetDestination("QENA", "قنا", "Qena", listOf("SJ_QEN")),
        SuperJetDestination("DAHAB", "دهب", "Dahab", listOf("SJ_DHB")),
        SuperJetDestination("DAMIETTA", "دمياط", "Damietta", listOf("SJ_DMT"))
    )

    private val stationToDestId = superjetDestinations.flatMap { dest ->
        dest.stationIds.map { it to dest.id }
    }.toMap()

    fun findTripsForDestinations(sourceDestId: String, destDestId: String): List<SuperJetTrip> {
        return superjetTrips.filter { it.sourceId == sourceDestId && it.destId == destDestId }
    }

    data class InterchangeStep(
        val stationId: String,
        val stationNameAr: String,
        val stationNameEn: String,
        val fromLine: TransportLine,
        val toLine: TransportLine,
        val directionAr: String,
        val directionEn: String
    )

    data class JourneyPlan(
        val source: Station,
        val destination: Station,
        val path: List<Station>,
        val ticketPrice: Double,
        val approxDurationMinutes: Int,
        val interchangeNeeded: Boolean,
        val interchangeStationsAr: String?,
        val interchangeStationsEn: String?,
        val interchangeSteps: List<InterchangeStep> = emptyList()
    )

    fun getStationById(id: String): Station? = allStations.find { it.id == id }

    fun getTrainsForRoute(sourceId: String, destId: String, lineName: String): List<Train> {
        val srcNameAr = getStationById(sourceId)?.nameAr ?: return emptyList()
        val destNameAr = getStationById(destId)?.nameAr ?: return emptyList()
        return trains.filter { train ->
            val trainStationNames = train.stationIds.mapNotNull { getStationById(it)?.nameAr }
            val srcIdx = trainStationNames.indexOf(srcNameAr)
            val dstIdx = trainStationNames.indexOf(destNameAr)
            srcIdx >= 0 && dstIdx >= 0 && srcIdx < dstIdx
        }
    }

    fun getSuperJetTripsForRoute(sourceId: String, destId: String): List<SuperJetTrip> {
        return superjetTrips.filter { it.sourceId == sourceId && it.destId == destId }
    }

    fun getCompaniesForRoute(sourceId: String, destId: String): List<TransportLine> {
        val companyIds = superjetTrips.filter { it.sourceId == sourceId && it.destId == destId }.map { it.companyId }.toSet()
        return companyIds.mapNotNull { try { TransportLine.valueOf(it) } catch (_: Exception) { null } }
    }

    fun planJourney(srcId: String, destId: String, mode: TransitMode): JourneyPlan? {
        val src = getStationById(srcId) ?: return null
        val dest = getStationById(destId) ?: return null

        val searched = mutableSetOf<String>()
        val queue = ArrayDeque<List<Station>>()
        queue.add(listOf(src))

        while (queue.isNotEmpty()) {
            val path = queue.removeFirst()
            val current = path.last()

            if (current.id == dest.id) return buildPlan(path, src, dest)
            if (current.id in searched) continue
            searched.add(current.id)

            val neighbors = mutableListOf<Station>()

            // 1. Adjacent stations on the same line (sequential)
            val sameLineNeighbors = allStations.filter { s ->
                s.id != current.id && s.line == current.line &&
                s.dynamicStatus == StationStatus.ACTIVE &&
                kotlin.math.abs(s.sequentialNumber - current.sequentialNumber) == 1
            }
            neighbors.addAll(sameLineNeighbors)

            // 2. Interchange stations via connectsTo (same transit mode, different line)
            if (current.connectsTo.isNotEmpty()) {
                val transferLines = current.connectsTo.mapNotNull { targetLineName ->
                    try { TransportLine.valueOf(targetLineName) } catch (_: Exception) { null }
                }.filter { it.mode == mode && isLineOpen(it) }

                for (targetLine in transferLines) {
                    val transferStations = allStations.filter { s ->
                        s.line == targetLine &&
                        s.dynamicStatus == StationStatus.ACTIVE &&
                        s.connectsTo.contains(current.line.name)
                    }
                    neighbors.addAll(transferStations)
                }
            }

            for (next in neighbors) {
                if (next.id !in searched) {
                    queue.add(path + next)
                }
            }
        }
        return null
    }

    private fun buildPlan(path: List<Station>, src: Station, dest: Station): JourneyPlan {
        val stopCount = path.size - 1
        val usesMonorail = path.any { it.line.mode == TransitMode.MONORAIL }
        val usesLRT = path.any { it.line.mode == TransitMode.LRT }
        val usesBRT = path.any { it.line.mode == TransitMode.BRT }

        val price = when {
            usesMonorail -> when {
                stopCount <= 5 -> fareMonorail1
                stopCount <= 10 -> fareMonorail2
                stopCount <= 15 -> fareMonorail3
                else -> fareMonorail4
            }
            usesLRT -> when {
                stopCount <= 4 -> fareLRT1
                stopCount <= 8 -> fareLRT2
                stopCount <= 12 -> fareLRT3
                else -> fareLRT4
            }
            usesBRT -> when {
                stopCount <= 5 -> fareBRT1
                stopCount <= 10 -> fareBRT2
                else -> fareBRT3
            }
            else -> when {
                stopCount <= 9 -> farePerZone1
                stopCount <= 16 -> farePerZone2
                stopCount <= 23 -> farePerZone3
                else -> farePerZone4
            }
        }
        val duration = stopCount * 3

        // Find line transitions in the path
        val steps = mutableListOf<InterchangeStep>()
        var currentLine = src.line
        var segmentStartIdx = 0
        for (i in path.indices) {
            if (path[i].line != currentLine) {
                // Transition at path[i] from currentLine to path[i].line
                val fromLine = currentLine
                val toLine = path[i].line
                val st = path[i]

                // Determine direction on the new line
                val remainingOnLine = path.drop(i).filter { it.line == toLine }
                val dirAr: String
                val dirEn: String
                if (remainingOnLine.size >= 2) {
                    val firstSeq = remainingOnLine.first().sequentialNumber
                    val lastSeq = remainingOnLine.last().sequentialNumber
                    // Look up terminal names for direction description
                    val lineStations = allStations.filter { it.line == toLine && it.dynamicStatus == StationStatus.ACTIVE }
                    val minSeq = lineStations.minOfOrNull { it.sequentialNumber } ?: 1
                    val maxSeq = lineStations.maxOfOrNull { it.sequentialNumber } ?: 1
                    if (lastSeq > firstSeq) {
                        val terminus = lineStations.find { it.sequentialNumber == maxSeq }
                        dirAr = "اتجاه ${terminus?.nameAr ?: "الطرف الآخر"}"
                        dirEn = "Towards ${terminus?.nameEn ?: "the other terminus"}"
                    } else {
                        val terminus = lineStations.find { it.sequentialNumber == minSeq }
                        dirAr = "اتجاه ${terminus?.nameAr ?: "الطرف الآخر"}"
                        dirEn = "Towards ${terminus?.nameEn ?: "the other terminus"}"
                    }
                } else {
                    dirAr = toLine.nameAr
                    dirEn = toLine.nameEn
                }

                steps.add(InterchangeStep(
                    stationId = st.id,
                    stationNameAr = st.nameAr,
                    stationNameEn = st.nameEn,
                    fromLine = fromLine,
                    toLine = toLine,
                    directionAr = dirAr,
                    directionEn = dirEn
                ))
                currentLine = toLine
                segmentStartIdx = i
            }
        }

        val interchangeNeeded = steps.isNotEmpty()

        val stepsAr = steps.joinToString("\n") { step ->
            "• انزل في ${step.stationNameAr}\n  (${step.fromLine.nameAr} ← ${step.toLine.nameAr})\n  ${step.directionAr}"
        }
        val stepsEn = steps.joinToString("\n") { step ->
            "• Get off at ${step.stationNameEn}\n  (${step.fromLine.nameEn} → ${step.toLine.nameEn})\n  ${step.directionEn}"
        }

        return JourneyPlan(
            source = src,
            destination = dest,
            path = path,
            ticketPrice = price,
            approxDurationMinutes = duration,
            interchangeNeeded = interchangeNeeded,
            interchangeStationsAr = if (interchangeNeeded) "تحويل في:\n$stepsAr" else null,
            interchangeStationsEn = if (interchangeNeeded) "Interchange:\n$stepsEn" else null,
            interchangeSteps = steps
        )
    }
}