package com.example.model

object MicrobusTerminals {
    fun getAll(): List<Station> {
        val list = mutableListOf<Station>()
        var seq = 1
        fun add(id: String, ar: String, en: String, addrAr: String, addrEn: String, lAr: List<String>, lEn: List<String>, tip: String = "", tipEn: String = "") {
            list.add(Station(
                id = "MICRO_$id",
                nameAr = ar,
                nameEn = en,
                line = TransportLine.MICROBUS_CAIRO,
                sequentialNumber = seq++,
                mapX = 0f, mapY = 0f,
                addressAr = addrAr,
                addressEn = addrEn,
                landmarksAr = lAr,
                landmarksEn = lEn,
                guideTipAr = tip,
                guideTipEn = tipEn
            ))
        }

        add("ATABA", "موقف العتبة", "Ataba Terminal",
            "شارع محمد علي الكبير، حي الموسكي، وسط البلد، القاهرة",
            "Mohamed Ali Al-Kebir Street, Al-Muski, Downtown, Cairo",
            listOf("محطة مترو العتبة - الخط الثاني والثالث", "ميدان العتبة", "سينما راديو", "مول العتبة", "مستشفى الهلال"),
            listOf("Ataba Metro Station (L2 & L3)", "Ataba Square", "Radio Cinema", "Ataba Mall", "El-Hilal Hospital"),
            "أكبر موقف ميكروباص في وسط القاهرة، تتجمع فيه خطوط القاهرة والجيزة والقليوبية. ينقسم إلى 4 قطاعات رئيسية على جانبي شارع محمد علي.",
            "Largest microbus hub in downtown Cairo. Buses for Cairo, Giza, and Qalyubia converge here, divided into 4 main sectors on Mohamed Ali Street.")

        add("RAMSES", "موقف رمسيس", "Ramses Terminal",
            "ميدان رمسيس، باب اللوق، عابدين، القاهرة",
            "Ramses Square, Bab El-Louq, Abdeen, Cairo",
            listOf("محطة مصر (سكك حديد مصر)", "محطة مترو رمسيس - الخط الثالث", "مستشفى الهلال الأحمر", "كنيسة قصر الدوبارة", "مول رمسيس"),
            listOf("Misr Railway Station", "Ramses Metro Station (L3)", "Red Crescent Hospital", "Qasr El-Doubara Church", "Ramses Mall"),
            "يقع تحت كوبري 6 أكتوبر، مقسوم لقطاعين: شرقي للمحافظات وغربي للقاهرة الكبرى. مجاور لمحطة مصر للسكك الحديدية.",
            "Located under the 6th October Bridge. Split into east (governorates) and west (Greater Cairo) sectors. Adjacent to Misr Railway Station.")

        add("ABD_MONEAM_RIAD", "موقف عبد المنعم رياض", "Abd El-Moneim Riad Terminal",
            "شارع عبد المنعم رياض، عابدين، القاهرة",
            "Abd El-Moneim Riad Street, Abdeen, Cairo",
            listOf("ميدان طلعت حرب", "محافظة القاهرة", "جريدة الأهرام", "البنك المركزي المصري", "متجر عمر أفندي"),
            listOf("Talat Harb Square", "Cairo Governorate", "Al-Ahram Newspaper HQ", "Central Bank of Egypt", "Omar Effendi Store"),
            "يقع بين ميدان طلعت حرب وميدان عبد المنعم رياض، متخصص في خطوط وسط القاهرة والمتحف المصري.",
            "Between Talat Harb and Abd El-Moneim Riad squares. Serves central Cairo and Egyptian Museum routes.")

        add("MOHANDISEEN", "موقف المهندسين", "Mohandiseen Terminal",
            "شارع جامعة الدول العربية، المهندسين، الجيزة",
            "Arab League Street, Mohandiseen, Giza",
            listOf("محطة مترو المهندسين - الخط الثالث", "سينما مصر", "دار القضاء العالي", "ستاد القاهرة الدولي", "المركز التجاري"),
            listOf("Mohandiseen Metro Station (L3)", "Misr Cinema", "High Court", "Cairo Stadium", "Trade Center"),
            "أهم موقف في المهندسين والعجوزة، يخدم الجيزة وأكتوبر والشيخ زايد. يقع تحت كوبري 6 أكتوبر.",
            "Main hub for Mohandiseen and Agouza. Serves Giza, 6th October, Sheikh Zayed. Under 6th October Bridge.")

        add("GIZA_KITKAT", "موقف الجيزة (الكيت كات)", "Giza Kit Kat Terminal",
            "ميدان الكيت كات، الجيزة، محافظة الجيزة",
            "Kit Kat Square, Giza, Giza Governorate",
            listOf("محطة مترو الجيزة - الخط الثاني", "مستشفى الجيزة العام", "مجلس مدينة الجيزة", "جامعة القاهرة (المدخل الرئيسي)", "كوبري الجامعة"),
            listOf("Giza Metro Station (L2)", "Giza General Hospital", "Giza City Council", "Cairo University (main gate)", "University Bridge"),
            "يخدم خطوط الجيزة وأبو النمرس والبدرشين والمنصورية، محوري لشمال ووسط الجيزة.",
            "Serves Giza, Abu Nomros, Badrashin, Mansouriya. Central to north and central Giza.")

        add("TAHRIR", "موقف التحرير", "Tahrir Terminal",
            "ميدان التحرير، عابدين، القاهرة",
            "Tahrir Square, Abdeen, Cairo",
            listOf("محطة مترو أنور السادات - الخط الثاني والثالث", "المتحف المصري", "جامعة الدول العربية", "مبنى الجامعة العربية", "مجمع التحرير"),
            listOf("Sadat Metro Station (L2 & L3)", "Egyptian Museum", "Arab League HQ", "Arab League Building", "Tahrir Complex"),
            "يقع بجانب المتحف المصري، متخصص في خطوط وسط القاهرة والمكاتب الحكومية.",
            "Next to the Egyptian Museum. Specialized in central Cairo and government office routes.")

        add("DOKKI", "موقف الدقي", "Dokki Terminal",
            "شارع التحرير، الدقي، الجيزة",
            "Tahrir Street, Dokki, Giza",
            listOf("محطة مترو الدقي - الخط الثاني", "مستشفى الزراعيين", "مركز الدقي للقلب", "جامعة الدول العربية (قريب)", "كوبري الدقي"),
            listOf("Dokki Metro Station (L2)", "Agricultural Hospital", "Dokki Heart Center", "Arab League (nearby)", "Dokki Bridge"),
            "موقف رئيسي لخطوط الدقي والمهندسين وبولاق الدكرور.",
            "Main hub for Dokki, Mohandiseen, and Bulaq El-Dakrur routes.")

        add("FAISAL", "موقف فيصل", "Faisal Terminal",
            "شارع الملك فيصل، الطالبية، الجيزة",
            "King Faisal Street, Talbeyya, Giza",
            listOf("محطة مترو فيصل - الخط الثاني", "جامعة القاهرة (فرع الفيلات)", "مستشفى فيصل الجامعي", "كوبري فيصل", "مول العرب (قريب)"),
            listOf("Faisal Metro Station (L2)", "Cairo University (villas branch)", "Faisal University Hospital", "Faisal Bridge", "Mall of Arabia (nearby)"),
            "أكبر موقف ميكروباص في الجيزة، يخدم جنوب الجيزة والفيوم وأكتوبر. يتكون من 3 مستويات.",
            "Largest microbus terminal in Giza. Serves south Giza, Fayoum, 6th October. 3-level structure.")

        add("HARAM", "موقف الهرم", "Haram Terminal",
            "شارع الهرم، الجيزة",
            "Haram Street, Giza",
            listOf("محطة مترو الجيزة - الخط الثاني", "أهرامات الجيزة (قريب)", "المتحف المصري الكبير", "دريم بارك (قريب)", "كوبري الهرم"),
            listOf("Giza Metro Station (L2)", "Pyramids of Giza (nearby)", "Grand Egyptian Museum", "Dream Park (nearby)", "Haram Bridge"),
            "متفرع من موقف فيصل، متخصص في خطوط حدائق الأهرام وكوم الأخضر ومنشأة القناطر.",
            "Branch of Faisal terminal. Specialized in Hadayek Al-Haram, Kom Al-Akhdar, and Manshiyat El-Qanater routes.")

        add("HADAYEK_HARAM", "موقف حدائق الأهرام", "Hadayek Al-Haram Terminal",
            "شارع حدائق الأهرام، الجيزة",
            "Hadayek Al-Haram Street, Giza",
            listOf("بوابة حدائق الأهرام", "دريم لاند", "مستشفى بهية", "كايرو مول", "محطة المريوطية"),
            listOf("Hadayek Al-Haram Gate", "Dream Land", "Baheya Hospital", "Cairo Mall", "El-Maryoteya Station"),
            "البوابة الجنوبية لحدائق الأهرام، يخدم مدخل 6 أكتوبر من الجهة الشرقية.",
            "Southern gate of Hadayek Al-Haram. Eastern entrance to 6th October City.")

        add("OCT_6", "موقف 6 أكتوبر", "6th of October Terminal",
            "الحي السابع، مدينة 6 أكتوبر، الجيزة",
            "District 7, 6th of October City, Giza",
            listOf("مول العرب (قريب)", "المحور المركزي", "جامعة 6 أكتوبر", "المستشفى الدولي", "ميدان جهينة"),
            listOf("Mall of Arabia (nearby)", "Central Axis Road", "6th October University", "International Hospital", "Juhayna Square"),
            "المحطة الرئيسية لمدينة 6 أكتوبر، يتقسم إلى: الحي السابع، الحي العاشر، الكوم الأخضر، الحصري.",
            "Main hub of 6th October City. Divided into 7th District, 10th District, Kom Al-Akhdar, Hosary.")

        add("SHEIKH_ZAYED", "موقف الشيخ زايد", "Sheikh Zayed Terminal",
            "الحي الثالث، مدينة الشيخ زايد، الجيزة",
            "District 3, Sheikh Zayed City, Giza",
            listOf("كورنيش النيل (قريب)", "بالم هيلز", "المدرسة البريطانية", "الحي الثاني عشر", "ميدان الجولف"),
            listOf("Nile Corniche (nearby)", "Palm Hills", "British School", "12th District", "Golf Square"),
            "موقف مخصص لخطوط الشيخ زايد وحدائق الأهرام وأكتوبر الجديدة.",
            "Dedicated to Sheikh Zayed, Hadayek Al-Haram, and New October routes.")

        add("MAADI", "موقف المعادي", "Maadi Terminal",
            "شارع 9، المعادي، القاهرة",
            "Street 9, Maadi, Cairo",
            listOf("محطة مترو المعادي - الخط الأول", "كورنيش النيل", "المعادي هايتس", "المدرسة الأمريكية", "مستشفى المعادي العسكري"),
            listOf("Maadi Metro Station (L1)", "Nile Corniche", "Maadi Heights", "American School", "Maadi Military Hospital"),
            "يقع في قلب المعادي القديمة، يخدم المعادي الجديدة والمقطم وحلوان. له عدة مواقف فرعية.",
            "In Old Maadi center. Serves New Maadi, Mokattam, Helwan. Has multiple sub-terminals.")

        add("HELWAN", "موقف حلوان", "Helwan Terminal",
            "ميدان المحطة، حلوان، القاهرة",
            "Station Square, Helwan, Cairo",
            listOf("محطة مترو حلوان - الخط الأول", "جامعة حلوان", "مستشفى حلوان العام", "مصنع الحديد والصلب", "المركز الطبي"),
            listOf("Helwan Metro Station (L1)", "Helwan University", "Helwan General Hospital", "Iron & Steel Factory", "Medical Center"),
            "المحطة النهائية للخط الأول جنوباً، يخدم حلوان والتبين و15 مايو.",
            "Southern terminus of Line 1. Serves Helwan, Tibeen, 15th of May City.")

        add("SHUBRA_KHEIMA", "موقف شبرا الخيمة", "Shubra El-Kheima Terminal",
            "شارع 15 مايو، شبرا الخيمة، القليوبية",
            "15th May Street, Shubra El-Kheima, Qalyubia",
            listOf("محطة قطار شبرا الخيمة", "كوبري شبرا الخيمة", "مستشفى شبرا الخيمة العام", "المحكمة", "مجمع المدارس"),
            listOf("Shubra El-Kheima Train Station", "Shubra El-Kheima Bridge", "Shubra El-Kheima General Hospital", "Court Complex", "Schools Complex"),
            "أكبر موقف في القليوبية، يخدم شبرا الخيمة وقليوب والقناطر الخيرية. متصل بمترو شبرا.",
            "Largest terminal in Qalyubia. Serves Shubra, Qalyoub, Qanater. Connected to Shubra metro.")

        add("SHUBRA_MISR", "موقف شبرا مصر", "Shubra Misr Terminal",
            "شبرا، حي شبرا، القاهرة",
            "Shubra, Shubra District, Cairo",
            listOf("محطة مترو شبرا - الخط الثاني", "كوبري شبرا", "مستشفى شبرا العام", "كنيسة العذراء بشبرا", "ميدان شبرا"),
            listOf("Shubra Metro Station (L2)", "Shubra Bridge", "Shubra General Hospital", "Shubra Virgin Mary Church", "Shubra Square"),
            "أقدم موقف في شبرا، يخدم شمال القاهرة وبعض مناطق الجيزة الشمالية.",
            "Oldest terminal in Shubra. Serves north Cairo and some north Giza areas.")

        add("ABBASSEYA", "موقف العباسية", "Abbasseya Terminal",
            "شارع العباسية، العباسية، القاهرة",
            "Abbasseya Street, Abbasseya, Cairo",
            listOf("محطة مترو العباسية - الخط الثالث", "مستشفى عين شمس التخصصي", "جامعة عين شمس (قريب)", "نادي الشمس", "قصر العباسية"),
            listOf("Abbasseya Metro Station (L3)", "Ain Shams Specialized Hospital", "Ain Shams University (nearby)", "El-Shams Club", "Abbasseya Palace"),
            "يقع تحت كوبري العباسية، يخدم العباسية ومصر الجديدة ومدينة نصر.",
            "Under Abbasseya Bridge. Serves Abbasseya, Heliopolis, Nasr City.")

        add("HELIOPOLIS", "موقف هليوبوليس", "Heliopolis Terminal",
            "شارع الأهرام، مصر الجديدة، القاهرة",
            "Pyramids Street, Heliopolis, Cairo",
            listOf("مستشفى هليوبوليس", "قصر البارون", "كنيسة البشارة", "الهيئة العامة للأرصاد", "ميدان هليوبوليس"),
            listOf("Heliopolis Hospital", "Baron Palace", "Annunciation Church", "Meteorological Authority", "Heliopolis Square"),
            "يقع في قلب مصر الجديدة، يخدم روكسي والنزهة ومدينة نصر وعين شمس.",
            "In Heliopolis center. Serves Roxy, El-Nuzha, Nasr City, Ain Shams.")

        add("NASR_CITY", "موقف مدينة نصر", "Nasr City Terminal",
            "طريق النصر، مدينة نصر، القاهرة",
            "Nasr Road, Nasr City, Cairo",
            listOf("استاد القاهرة الدولي", "المدينة الرياضية", "جامعة الأزهر", "المعهد القومي للبحوث الفلكية", "سيراميكا كيلوباترا"),
            listOf("Cairo International Stadium", "Sports City", "Al-Azhar University", "National Astronomy Institute", "SC Sporting Club"),
            "أكبر موقف شرق القاهرة، مقسم لثلاثة مواقف: (1) للمدن الجديدة، (2) للمعادي والمقطم، (3) لمصر الجديدة.",
            "Largest east Cairo terminal. Divided into 3: New Cities, Maadi/Mokattam, Heliopolis sectors.")

        add("FIFTH_SETTLEMENT", "موقف التجمع الخامس", "5th Settlement Terminal",
            "شارع التسعين الشمالي، التجمع الخامس، القاهرة الجديدة",
            "North 90th Street, 5th Settlement, New Cairo",
            listOf("جامعة المستقبل", "القطامية هايتس", "البنفسج", "الأندلس", "مول ذا سبيس"),
            listOf("Future University", "Katameya Heights", "El-Banafsaj", "Al-Andalus", "The Space Mall"),
            "المحطة الرئيسية للقاهرة الجديدة، يخدم التجمع الأول والخامس والعاصمة الإدارية الجديدة.",
            "Main hub for New Cairo. Serves 1st, 5th Settlements, and New Administrative Capital.")

        add("EL_SALAM", "موقف السلام", "El-Salam Terminal",
            "شارع مصر والسودان، حدائق القبة، القاهرة",
            "Egypt-Sudan Street, Hadayeq El-Qobba, Cairo",
            listOf("محطة مترو حدائق الزيتون - الخط الثالث", "مستشفى السلام", "ميدان السواح", "كوبري السلام", "مستشفى الزهراء"),
            listOf("Hadayeq El-Zeitoun Metro (L3)", "El-Salam Hospital", "El-Sawah Square", "El-Salam Bridge", "El-Zahraa Hospital"),
            "يخدم شمال شرق القاهرة: السلام، المرج، عين شمس، منشية ناصر.",
            "Serves northeast Cairo: El-Salam, El-Marg, Ain Shams, Manshiyat Naser.")

        add("EL_MARG", "موقف المرج", "El-Marg Terminal",
            "شارع مؤسسة الزكاة، المرج، القاهرة",
            "Zakat Foundation Street, El-Marg, Cairo",
            listOf("محطة قطار المرج", "كوبري المرج", "مستشفى المرج", "ميدان المرج الجديد", "أسواق المرج"),
            listOf("El-Marg Train Station", "El-Marg Bridge", "El-Marg Hospital", "New El-Marg Square", "El-Marg Markets"),
            "المحطة الطرفية لشمال شرق القاهرة، يخدم المرج وعزبة النخل والخصوص.",
            "Northern terminal of northeast Cairo. Serves El-Marg, Ezbet El-Nakhl, El-Khosos.")

        add("AIN_SHAMS", "موقف عين شمس", "Ain Shams Terminal",
            "شارع عين شمس، عين شمس، القاهرة",
            "Ain Shams Street, Ain Shams, Cairo",
            listOf("جامعة عين شمس (المدخل الرئيسي)", "مستشفى عين شمس التخصصي", "مسجد عين شمس", "ميدان أحمد فؤاد سليم", "كوبري عين شمس"),
            listOf("Ain Shams University (main gate)", "Ain Shams Specialized Hospital", "Ain Shams Mosque", "Ahmed Fouad Selim Square", "Ain Shams Bridge"),
            "مهم لشرق القاهرة، يخدم عين شمس وعزبة طلبة والخصوص.",
            "Key for east Cairo. Serves Ain Shams, Ezbet Talba, El-Khosos.")

        add("EL_NOZHA", "موقف النزهة", "El-Nuzha Terminal",
            "شارع النزهة، مصر الجديدة، القاهرة",
            "El-Nuzha Street, Heliopolis, Cairo",
            listOf("ميدان الحجاز", "مستشفى النزهة", "نادي هليوبوليس", "كوبري الجلاء (قريب)", "شيراتون المطار"),
            listOf("El-Hegaz Square", "El-Nuzha Hospital", "Heliopolis Club", "Galaa Bridge (nearby)", "Airport Sheraton"),
            "يخدم شمال مصر الجديدة والنزهة وصلاح سالم والمطار.",
            "Serves north Heliopolis, El-Nuzha, Salah Salem, Airport area.")

        add("EL_SHOROUK", "موقف الشروق", "El-Shorouk Terminal",
            "مدخل مدينة الشروق، الشروق، القاهرة",
            "Shorouk City Entrance, El-Shorouk, Cairo",
            listOf("جامعة الشروق", "بوابة الشروق الرئيسية", "كورنيش الشروق", "مستشفى الشروق", "مجمع المدارس"),
            listOf("El-Shorouk University", "El-Shorouk Main Gate", "El-Shorouk Corniche", "El-Shorouk Hospital", "Schools Complex"),
            "المحطة الرئيسية لمدينة الشروق، يخدم بدر والعاصمة الإدارية الجديدة.",
            "Main hub of El-Shorouk City. Serves Badr and New Administrative Capital.")

        add("EL_OBOR", "موقف العبور", "El-Obour Terminal",
            "مدخل مدينة العبور، العبور، القليوبية",
            "Obour City Entrance, El-Obour, Qalyubia",
            listOf("جامعة العبور", "بوابة العبور", "مستشفى العبور", "الحي الأول", "الحي التاسع"),
            listOf("Obour University", "Obour Gate", "Obour Hospital", "1st District", "9th District"),
            "مخصص لخطوط العبور، المستقبل، الشروق. متصل بطريق الإسماعيلية الصحراوي.",
            "Dedicated to El-Obour, El-Mostaqbal, El-Shorouk routes. Connected to Ismailia Desert Road.")

        add("BADR", "موقف بدر", "Badr Terminal",
            "مدخل مدينة بدر، بدر، القاهرة",
            "Badr City Entrance, Badr, Cairo",
            listOf("جامعة بدر", "كورنيش بدر", "مستشفى بدر الجامعي", "بوابة بدر الرئيسية", "الحي المتميز"),
            listOf("Badr University", "Badr Corniche", "Badr University Hospital", "Badr Main Gate", "Distinguished District"),
            "يخدم مدينة بدر ومنطقة الروبيكي والعاصمة الإدارية.",
            "Serves Badr City, Robaiki area, and New Administrative Capital.")

        add("ROD_EL_FARAG", "موقف روض الفرج", "Rod El-Farag Terminal",
            "شريط روض الفرج، حي روض الفرج، القاهرة",
            "Rod El-Farag, Rod El-Farag District, Cairo",
            listOf("كوبري روض الفرج", "مستشفى روض الفرج", "مدرسة روض الفرج الثانوية", "كورنيش النيل", "مركز شباب روض الفرج"),
            listOf("Rod El-Farag Bridge", "Rod El-Farag Hospital", "Rod El-Farag High School", "Nile Corniche", "Rod El-Farag Youth Center"),
            "يقع على كورنيش النيل شمال القاهرة، يخدم روض الفرج وبولاق أبو العلا والشرابية.",
            "On Nile Corniche in north Cairo. Serves Rod El-Farag, Bulaq, Sharabeya.")

        add("BULAQ_DAKRUR", "موقف بولاق الدكرور", "Bulaq El-Dakrur Terminal",
            "شارع ترعة الزمر، بولاق الدكرور، الجيزة",
            "Tura El-Zomar Street, Bulaq El-Dakrur, Giza",
            listOf("محطة مترو بولاق الدكرور - الخط الثالث", "مستشفى بولاق", "ميدان الكيت كات (قريب)", "جامعة القاهرة (المدخل الخلفي)", "كوبري بولاق"),
            listOf("Bulaq Metro Station (L3)", "Bulaq Hospital", "Kit Kat Square (nearby)", "Cairo University (back gate)", "Bulaq Bridge"),
            "يخدم بولاق الدكرور وأبو النمرس وترعة الزمر.",
            "Serves Bulaq El-Dakrur, Abu Nomros, Tura El-Zomar.")

        add("IMBABA", "موقف إمبابة", "Imbaba Terminal",
            "ميدان إمبابة، حي إمبابة، الجيزة",
            "Imbaba Square, Imbaba District, Giza",
            listOf("محطة مترو إمبابة - الخط الثالث", "كوبري إمبابة", "مستشفى إمبابة العام", "مجمع مدارس إمبابة", "ميدان المطافي"),
            listOf("Imbaba Metro Station (L3)", "Imbaba Bridge", "Imbaba General Hospital", "Imbaba Schools Complex", "El-Matafi Square"),
            "أكبر موقف في شمال الجيزة، مقسم لقطاعات: قري إمبابة، الوراق، أوسيم، الكوم الأخضر.",
            "Largest terminal in north Giza. Divided into Imbaba villages, El-Warraq, Awsim, Kom Al-Akhdar sectors.")

        add("EL_WARRAQ", "موقف الوراق", "El-Warraq Terminal",
            "شريط الوراق، حي الوراق، الجيزة",
            "El-Warraq, El-Warraq District, Giza",
            listOf("كوبري الوراق", "مستشفى الوراق المركزي", "مجمع مدارس الوراق", "كنيسة الوراق", "كورنيش النيل"),
            listOf("El-Warraq Bridge", "El-Warraq Central Hospital", "El-Warraq Schools", "El-Warraq Church", "Nile Corniche"),
            "يقع على كورنيش النيل، يخدم الوراق والوراق الجديدة وأجزاء من أوسيم.",
            "On Nile Corniche. Serves El-Warraq, New El-Warraq, parts of Awsim.")

        add("AWSIM", "موقف أوسيم", "Awsim Terminal",
            "طريق مصر إسكندرية الزراعي، أوسيم، الجيزة",
            "Cairo-Alex Agricultural Road, Awsim, Giza",
            listOf("مستشفى أوسيم المركزي", "كوبري أوسيم", "ميدان أوسيم", "سوق أوسيم", "مركز شرطة أوسيم"),
            listOf("Awsim Central Hospital", "Awsim Bridge", "Awsim Square", "Awsim Market", "Awsim Police Station"),
            "يقع على الطريق الزراعي، بوابة شمالية لمنطقة الجيزة، يخدم أطفيح ومنشأة القناطر.",
            "On Agricultural Road. Northern gate of Giza region. Serves Atfih and Manshiyat El-Qanater.")

        add("EL_HAWAMDEYA", "موقف الحوامدية", "El-Hawamdeya Terminal",
            "شريط الحوامدية، الحوامدية، الجيزة",
            "El-Hawamdeya, El-Hawamdeya, Giza",
            listOf("محطة قطار الحوامدية", "كوبري الحوامدية", "مستشفى الحوامدية", "ميدان الحوامدية", "مصنع الحوامدية للسكر"),
            listOf("El-Hawamdeya Train Station", "El-Hawamdeya Bridge", "El-Hawamdeya Hospital", "El-Hawamdeya Square", "El-Hawamdeya Sugar Factory"),
            "يقع جنوب الجيزة، بوابة الصعيد، يخدم أطفيح والعياط.",
            "In south Giza. Gateway to Upper Egypt. Serves Atfih and El-Ayyat.")

        add("EL_BADRASHIN", "موقف البدرشين", "El-Badrashin Terminal",
            "شريط البدرشين، البدرشين، الجيزة",
            "El-Badrashin, El-Badrashin, Giza",
            listOf("محطة قطار البدرشين", "مستشفى البدرشين", "جامعة البدرشين", "ميدان البدرشين", "مصنع البدرشين"),
            listOf("El-Badrashin Train Station", "El-Badrashin Hospital", "El-Badrashin University", "El-Badrashin Square", "El-Badrashin Factory"),
            "يخدم البدرشين ومنشأة القناطر وأبو النمرس.",
            "Serves El-Badrashin, Manshiyat El-Qanater, Abu Nomros.")

        add("ABU_NOMROS", "موقف أبو النمرس", "Abu Nomros Terminal",
            "شريط أبو النمرس، أبو النمرس، الجيزة",
            "Abu Nomros, Abu Nomros, Giza",
            listOf("مستشفى أبو النمرس", "كوبري أبو النمرس", "ميدان أبو النمرس", "مجمع مدارس أبو النمرس", "سوق أبو النمرس"),
            listOf("Abu Nomros Hospital", "Abu Nomros Bridge", "Abu Nomros Square", "Abu Nomros Schools", "Abu Nomros Market"),
            "يخدم أبو النمرس ومنطقة المريوطية.",
            "Serves Abu Nomros and El-Maryoteya area.")

        add("TORA", "موقف طرة", "Tora Terminal",
            "شريط طرة، طرة، القاهرة",
            "Tora, Tora, Cairo",
            listOf("محطة مترو طرة - الخط الأول", "مستشفى طرة", "مصنع أسمنت طرة", "ميدان طرة", "كوبري طرة"),
            listOf("Tora Metro Station (L1)", "Tora Hospital", "Tora Cement Factory", "Tora Square", "Tora Bridge"),
            "يقع على طريق مصر حلوان الزراعي، يخدم طرة والمعصرة وعزبة الوالدة.",
            "On Cairo-Helwan Agricultural Road. Serves Tora, El-Maasara, Ezbet El-Walida.")

        add("EL_MAASARA", "موقف المعصرة", "El-Maasara Terminal",
            "شريط المعصرة، حلوان، القاهرة",
            "El-Maasara, Helwan, Cairo",
            listOf("محطة مترو المعصرة - الخط الأول", "مستشفى المعصرة", "كوبري المعصرة", "ميدان المعصرة", "مجمع مدارس المعصرة"),
            listOf("El-Maasara Metro Station (L1)", "El-Maasara Hospital", "El-Maasara Bridge", "El-Maasara Square", "El-Maasara Schools"),
            "يخدم المعصرة وكفر علام وعزبة علام.",
            "Serves El-Maasara, Kafr El-Alam, Ezbet El-Alam.")

        add("MISR_EL_QADIMA", "موقف مصر القديمة", "Old Cairo Terminal",
            "شريط مصر القديمة، حي مصر القديمة، القاهرة",
            "Old Cairo, Old Cairo District, Cairo",
            listOf("كنيسة المعلقة", "الكنيس اليهودي", "حصن بابليون", "متحف أم المصريين", "مستشفى مصر القديمة"),
            listOf("Hanging Church", "Ben Ezra Synagogue", "Babylon Fortress", "Coptic Museum", "Old Cairo Hospital"),
            "مهم للسياحة والمسيحيين، يخدم مصر القديمة والدراسة ودير المقطم.",
            "Key for tourism and Coptic Christians. Serves Old Cairo, El-Darasa, Deir Mokattam.")

        add("EL_SAYEDA_ZEINAB", "موقف السيدة زينب", "Sayeda Zeinab Terminal",
            "شريط السيدة زينب، حي السيدة زينب، القاهرة",
            "Sayeda Zeinab, Sayeda Zeinab District, Cairo",
            listOf("مسجد السيدة زينب", "محطة مترو السيدة زينب - الخط الأول", "مستشفى الحسين الجامعي", "خان الخليلي (قريب)", "ميدان السيدة"),
            listOf("Sayeda Zeinab Mosque", "Sayeda Zeinab Metro (L1)", "Al-Hussein University Hospital", "Khan El-Khalili (nearby)", "Sayeda Square"),
            "يقع عند مسجد السيدة زينب، متخصص في خطوط وسط القاهرة والمناطق الأثرية.",
            "At Sayeda Zeinab Mosque. Specialized in central Cairo and historic area routes.")

        add("EL_KHALIFA", "موقف الخليفة", "El-Khalifa Terminal",
            "شريط الخليفة، حي الخليفة، القاهرة",
            "El-Khalifa, El-Khalifa District, Cairo",
            listOf("مقابر الخلود", "مسجد عمرو بن العاص (قريب)", "مجمع الأديان", "مستشفى الخليفة", "ميدان الخليفة"),
            listOf("Khalifa Tombs", "Amr Ibn El-As Mosque (nearby)", "Religious Complex", "El-Khalifa Hospital", "El-Khalifa Square"),
            "يخدم الخليفة والدرب الأحمر ومنطقة الفسطاط.",
            "Serves El-Khalifa, Darb El-Ahmar, Fustat area.")

        add("EL_MANYAL", "موقف المنيل", "El-Manyal Terminal",
            "شريط المنيل، حي المنيل، القاهرة",
            "El-Manyal, El-Manyal District, Cairo",
            listOf("قصر المنيل", "مستشفى المنيل الجامعي", "مستشفى أبو الريش (قريب)", "نادي الجزيرة", "كورنيش النيل"),
            listOf("Manial Palace", "El-Manyal University Hospital", "Abu El-Reesh Hospital (nearby)", "Gezira Club", "Nile Corniche"),
            "يقع على كورنيش النيل، يخدم المنيل والقلعة والروضة.",
            "On Nile Corniche. Serves El-Manyal, El-Qalaa, El-Roda.")

        add("DAR_EL_SALAM", "موقف دار السلام", "Dar El-Salam Terminal",
            "شريط دار السلام، حي دار السلام، القاهرة",
            "Dar El-Salam, Dar El-Salam District, Cairo",
            listOf("محطة مترو دار السلام - الخط الأول", "مستشفى دار السلام", "كوبري دار السلام", "ميدان دار السلام", "مجمع مدارس دار السلام"),
            listOf("Dar El-Salam Metro (L1)", "Dar El-Salam Hospital", "Dar El-Salam Bridge", "Dar El-Salam Square", "Dar El-Salam Schools"),
            "يخدم دار السلام والبساتين ودجلة.",
            "Serves Dar El-Salam, El-Basatin, Degla.")

        add("EL_BASATIN", "موقف البساتين", "El-Basatin Terminal",
            "شريط البساتين، حي البساتين، القاهرة",
            "El-Basatin, El-Basatin District, Cairo",
            listOf("مستشفى البساتين", "ميدان البساتين", "كوبري البساتين", "مجمع مدارس البساتين", "مسجد البساتين الكبير"),
            listOf("El-Basatin Hospital", "El-Basatin Square", "El-Basatin Bridge", "El-Basatin Schools", "Grand El-Basatin Mosque"),
            "يخدم البساتين القديمة والجديدة ودجلة والقطامية.",
            "Serves Old and New El-Basatin, Degla, Katameya.")

        add("MAADI_HADAYEK", "موقف حدائق المعادي", "Maadi Hadayek Terminal",
            "شريط حدائق المعادي، المعادي، القاهرة",
            "Maadi Hadayek, Maadi, Cairo",
            listOf("دجلة بلازا", "ديستريكت 5", "المدرسة الفرنسية", "نادي المعادي", "كورنيش المعادي"),
            listOf("Degla Plaza", "District 5", "French School", "Maadi Club", "Maadi Corniche"),
            "يخدم المعادي الجديدة وحدائق المعادي ودجلة.",
            "Serves New Maadi, Maadi Hadayek, Degla.")

        add("MAADI_EL_GEDIDA", "موقف المعادي الجديدة", "New Maadi Terminal",
            "شارع 9، المعادي الجديدة، القاهرة",
            "Street 9, New Maadi, Cairo",
            listOf("ديستريكت 1", "ديستريكت 2", "الكلية الكندية", "نادي رويال", "كورنيش المعادي"),
            listOf("District 1", "District 2", "Canadian College", "Royal Club", "Maadi Corniche"),
            "يخدم المعادي الجديدة والقطامية ومشروع دجلة.",
            "Serves New Maadi, Katameya, Degla Project.")

        add("KATAMEYA", "موقف القطامية", "Katameya Terminal",
            "شريط القطامية، القطامية، القاهرة",
            "Katameya, Katameya, Cairo",
            listOf("القطامية هايتس", "بالم كاتامي", "دجلة بلازا", "الكلية الأمريكية", "كورنيش القطامية"),
            listOf("Katameya Heights", "Palm Katameya", "Degla Plaza", "American College", "Katameya Corniche"),
            "يخدم القطامية وكمبوندات شرق القاهرة، متصل بطريق السويس.",
            "Serves Katameya and east Cairo compounds. Connected to Suez Road.")

        add("AIRPORT", "موقف مطار القاهرة", "Cairo Airport Terminal",
            "مطار القاهرة الدولي، طريق مصر حلوان الزراعي، النزهة",
            "Cairo International Airport, Cairo-Helwan Agricultural Road, El-Nuzha",
            listOf("مطار القاهرة الدولي - صالة 1", "مطار القاهرة الدولي - صالة 2", "مطار القاهرة الدولي - صالة 3", "مستشفى الشروق (قريب)", "فندق المطار"),
            listOf("Cairo Airport Terminal 1", "Cairo Airport Terminal 2", "Cairo Airport Terminal 3", "El-Shorouk Hospital (nearby)", "Airport Hotel"),
            "يقع تحت مطار القاهرة مباشرة، يخدم جميع صالات السفر. متصل بطريق المطار.",
            "Directly under Cairo Airport. Serves all terminals. Connected to Airport Road.")

        add("AZBAKEYA", "موقف الأزبكية", "Azbakeya Terminal",
            "شريط الأزبكية، حي الأزبكية، القاهرة",
            "Azbakeya, Azbakeya District, Cairo",
            listOf("حديقة الأزبكية", "دار الكتب والوثائق القومية", "مسرح محمد علي", "مكتبة مصر العامة", "ميدان الأوبرا"),
            listOf("Azbakeya Garden", "National Library & Archives", "Mohamed Ali Theater", "Misr Public Library", "Opera Square"),
            "يقع بجوار حديقة الأزبكية، مخصص لخطوط وسط القاهرة والمكاتب.",
            "Next to Azbakeya Garden. Dedicated to central Cairo and office routes.")

        add("BAB_EL_SHARIA", "موقف باب الشعري", "Bab El-Sharia Terminal",
            "باب الشعري، حي الموسكي، القاهرة",
            "Bab El-Sharia, Al-Muski, Cairo",
            listOf("جامع الأزهر (قريب)", "خان الخليلي (قريب)", "وكالة البابلي", "مستشفى باب الشعري", "سوق العتبة"),
            listOf("Al-Azhar Mosque (nearby)", "Khan El-Khalili (nearby)", "El-Babili Wakala", "Bab El-Sharia Hospital", "Ataba Market"),
            "يقع في قلب القاهرة القديمة، يخدم الموسكي وجمالية والعتبة.",
            "In old Cairo heart. Serves Al-Muski, Gamaliya, Ataba.")

        add("BULAQ_ABU_EL_ALA", "موقف بولاق أبو العلا", "Bulaq Abu El-Ala Terminal",
            "شريط بولاق أبو العلا، حي بولاق، القاهرة",
            "Bulaq Abu El-Ala, Bulaq District, Cairo",
            listOf("مستشفى بولاق أبو العلا", "كوبري 15 مايو", "كورنيش النيل", "مجمع مدارس بولاق", "سوق بولاق"),
            listOf("Bulaq Abu El-Ala Hospital", "May 15 Bridge", "Nile Corniche", "Bulaq Schools", "Bulaq Market"),
            "يقع على كورنيش النيل، يخدم بولاق أبو العلا والزاوية الحمراء.",
            "On Nile Corniche. Serves Bulaq Abu El-Ala and El-Zawya El-Hamra.")

        add("KERDASA", "موقف كرداسة", "Kerdasa Terminal",
            "شريط كرداسة، كرداسة، الجيزة",
            "Kerdasa, Kerdasa, Giza",
            listOf("مسجد سيدي أحمد البدوي", "مستشفى كرداسة", "ميدان كرداسة", "كوبري كرداسة", "سوق كرداسة"),
            listOf("Sidi Ahmed El-Badawi Mosque", "Kerdasa Hospital", "Kerdasa Square", "Kerdasa Bridge", "Kerdasa Market"),
            "يقع شمال الجيزة، يخدم كرداسة وأبو رواش ومنشأة القناطر.",
            "In north Giza. Serves Kerdasa, Abu Rawash, Manshiyat El-Qanater.")

        add("SAQQARA", "موقف سقارة", "Saqqara Terminal",
            "طريق سقارة، الجيزة",
            "Saqqara Road, Giza",
            listOf("منطقة سقارة الأثرية", "هرم سقارة", "جامعة سقارة", "مركز الزوار", "فندق مينا هاوس (قريب)"),
            listOf("Saqqara Archaeological Zone", "Saqqara Pyramid", "Saqqara University", "Visitor Center", "Mena House Hotel (nearby)"),
            "يقع قرب أهرامات سقارة، مهم للسياحة، يخدم حدائق الأهرام وأبو النمرس.",
            "Near Saqqara pyramids. Key for tourism. Serves Hadayek Al-Haram and Abu Nomros.")

        add("KIMA", "موقف كيما", "Kima Terminal",
            "ميدان كيما، أسوان", "Kima Square, Aswan",
            listOf("مصنع كيما", "ميدان كيما", "كورنيش النيل", "محطة قطار أسوان (قريب)", "مستشفى أسوان الجامعي"),
            listOf("Kima Factory", "Kima Square", "Nile Corniche", "Aswan Train Station (nearby)", "Aswan University Hospital"),
            "نموذج أول لإضافة مواقف محافظات الصعيد مستقبلاً.",
            "Template for adding Upper Egypt governorate terminals in the future.")

        return list
    }
}
