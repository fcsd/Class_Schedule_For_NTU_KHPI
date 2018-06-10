package com.khpi.classschedule.business

import com.khpi.classschedule.data.models.FullBuilding
import com.khpi.classschedule.data.models.FullBuildingPair
import com.khpi.classschedule.data.models.ShortBuilding

class BuildingManager {

    private var shortBuildings = listOf<ShortBuilding>()
    private var fullBuildings = listOf<FullBuilding>()

    init {
        shortBuildings = generateShortBuildingsInfo()
        fullBuildings = generateFullBuildingsInfo()
    }

    private fun generateShortBuildingsInfo() : ArrayList<ShortBuilding> {
        val shortBuildings = arrayListOf<ShortBuilding>()
        shortBuildings.add(ShortBuilding("Адміністративний корпус", "АК", 50.001024, 36.250337))
        shortBuildings.add(ShortBuilding("Вечірній корпус", "ВК", 50.000916, 36.251466))
        shortBuildings.add(ShortBuilding("Головний аудиторний корпус", "ГАК", 49.997488, 36.249079))
        shortBuildings.add(ShortBuilding("Інженерний корпус", "ІК", 49.999502, 36.248827))
        shortBuildings.add(ShortBuilding("Корпус громадських організацій", "КГО", 49.998378, 36.246434))
        shortBuildings.add(ShortBuilding("Математичний корпус", "МК", 49.999837, 36.250152))
        shortBuildings.add(ShortBuilding("Палац студентів", "ПС", 50.006675, 36.248948))
        shortBuildings.add(ShortBuilding("Радіокорпус", "РК", 49.999533, 36.252317))
        shortBuildings.add(ShortBuilding("Ректорський корпус", "ГК", 49.998985, 36.248358))
        shortBuildings.add(ShortBuilding("Спортивний корпус", "СК", 50.009393, 36.248526))
        shortBuildings.add(ShortBuilding("Технічний корпус", "ТК", 49.999387, 36.251520))
        shortBuildings.add(ShortBuilding("Корпус української мови", "КУМ", 50.013443, 36.249453))
        shortBuildings.add(ShortBuilding("Учбовий корпус №1", "У1", 49.998457, 36.251492))
        shortBuildings.add(ShortBuilding("Учбовий корпус №2", "У2", 49.998364, 36.247801))
        shortBuildings.add(ShortBuilding("Учбовий корпус №3", "У3", 49.9869675, 36.2342108))
        shortBuildings.add(ShortBuilding("Учбовий корпус №4", "У4", 50.012113, 36.255035))
        shortBuildings.add(ShortBuilding("Учбовий корпус №5", "У5", 50.007151, 36.247711))
        shortBuildings.add(ShortBuilding("Фізичний корпус", "ФК", 49.997946, 36.247860))
        shortBuildings.add(ShortBuilding("Хімічний корпус", "ХК", 49.998129, 36.250332))
        shortBuildings.add(ShortBuilding("Електротехнічний корпус", "ЕК", 50.000136, 36.249887))
        return shortBuildings
    }

    private fun generateFullBuildingsInfo() : ArrayList<FullBuilding> {

        val fullBuildings = arrayListOf<FullBuilding>()

        // Административный корпус
        fullBuildings.add(FullBuilding("АК", arrayListOf(
                FullBuildingPair("Скорочена назва", "АК"),
                FullBuildingPair("Кількість поверхів", "3")),
                mapOf("Відділи та підрозділи" to arrayListOf("Бухгалтерія (2 пов., к.1)","Канцелярія (2 пов., к.10,12)", "Відділ кадрів (3 пов., к.18)",
                        "Відділ коммандировок (2 пов., к.13)", "Відділ коммандировок (2 пов., к.13)", "Перший відділ (3 пов., к.15,15а)",
                        "Стипіндіальний відділ (2 пов., к.2,3)", "Типографія (1 пов.)", "Фінансо-плановий відділ (3 пов., к.23-25)",
                        "Юридичний відділ (2 пов.)", "Відділ договірної практики (2 пов.)", "МІПО (3 пов., к.305)", "Підготовчі курси (3 пов., к.302)"))))

        // Вечерний корпус
        fullBuildings.add(FullBuilding("ВК", arrayListOf(
                FullBuildingPair("Скорочена назва", "ВК"),
                FullBuildingPair("Кількість поверхів", "3")),
                mapOf("Відділи та підрозділи" to arrayListOf("Центр нових інформаційних технологій (1 пов.)"),
                        "Деканати" to arrayListOf("Комп'ютерних та інформаційних технологій"))))

        // Главный аудиторный корпус
        fullBuildings.add(FullBuilding("ГАК", arrayListOf(
                FullBuildingPair("Скорочена назва", "ГАК"),
                FullBuildingPair("Кількість поверхів", "3"),
                FullBuildingPair("Рік будівництва", "1885"),
                FullBuildingPair("Буфет", "1 поверх (праворуч)")),
                mapOf("Відділи та підрозділи" to arrayListOf("Бібліотека (1 пов.)"))))

        //Інженерний корпус
        fullBuildings.add(FullBuilding("ІК", arrayListOf(
                FullBuildingPair("Скорочена назва", "ІК"),
                FullBuildingPair("Кількість поверхів", "3"),
                FullBuildingPair("Рік будівництва", "1906"),
                FullBuildingPair("Архітектор", "Величко В.В.")),
                mapOf("Відділи та підрозділи" to arrayListOf("Центр підготовки іноземних громадян"))))

        //Корпус громадських організацій
        fullBuildings.add(FullBuilding("КГО", arrayListOf(
                FullBuildingPair("Скорочена назва", "КГО"),
                FullBuildingPair("Кількість поверхів", "2"),
                FullBuildingPair("Рік будівництва", "1902")),
                mapOf("Відділи та підрозділи" to arrayListOf("Перукарня (2 пов.)", "Профсоюз співробітників (2 пов.)",
                        "Профсоюз студентів (2 пов.)", "Столова (1 пов.)"))))

        //Математичний корпус
        fullBuildings.add(FullBuilding("МК", arrayListOf(
                FullBuildingPair("Скорочена назва", "МК"),
                FullBuildingPair("Кількість поверхів", "3")),
                mapOf("Відділи та підрозділи" to arrayListOf("Науково-дослідна частина", "Підготовче відділення",
                        "Редакційно-видавничий відділ"))))

        //Палац студентів
        fullBuildings.add(FullBuilding("ПС", arrayListOf(
                FullBuildingPair("Скорочена назва", "ПС"),
                FullBuildingPair("Кількість поверхів", "3"),
                FullBuildingPair("Рік будівництва", "1925"),
                FullBuildingPair("Адреса", "вул. Пушкінська 79/1 (за аркою)")),
                mapOf("Відділи та підрозділи" to arrayListOf("Бібліотека (1 пов.)", "Борцівський зал"))))

        //Радіокорпус
        fullBuildings.add(FullBuilding("РК", arrayListOf(
                FullBuildingPair("Скорочена назва", "РК"),
                FullBuildingPair("Кількість поверхів", "4 + цокольний")),
                mapOf()))

        // Ректорський корпус
        fullBuildings.add(FullBuilding("ГК", arrayListOf(
                FullBuildingPair("Скорочена назва", "ГК"),
                FullBuildingPair("Кількість поверхів", "3"),
                FullBuildingPair("Рік будівництва", "1901"),
                FullBuildingPair("Архітектор", "Ловцов М.І.")),
                mapOf("Відділи та підрозділи" to arrayListOf("Аспірантура, докторантура (2 пов., к.21)","Газета \"Політехнік\" (3 пов., к.5)", "Диспечерьска (3 пов., к.4)",
                        "Міжнародний відділ (к.23,38)", "Музей (3 пов., к.8)", "Прес-служба (1 пов., к.39)",
                        "Навчальний відділ (1 пов., к.39)", "Навчальна рада (к.7)", "Центр незалежного тестування (2 пов.)",
                        "Україно-Американський центр (1 пов., к.39)"))))

        //Спортивний корпус
        fullBuildings.add(FullBuilding("СК", arrayListOf(
                FullBuildingPair("Скорочена назва", "СК"),
                FullBuildingPair("Кількість поверхів", "3"),
                FullBuildingPair("Рік будівництва", "1991"),
                FullBuildingPair("Архітектор", "Пундик Ю.Л."),
                FullBuildingPair("Адреса", "вул. Алчевських 50а")),
                mapOf("Відділи та підрозділи" to arrayListOf("Бібліотека (1 пов.)", "Борцівський зал"))))

        //Технічний корпус
        fullBuildings.add(FullBuilding("ТК", arrayListOf(
                FullBuildingPair("Скорочена назва", "ТК"),
                FullBuildingPair("Кількість поверхів", "3"),
                FullBuildingPair("Рік будівництва", "1889"),
                FullBuildingPair("Архітектор", "Шпилегь А.К., Ловцов М.І."),
                FullBuildingPair("Буфет", "1 поверх")),
                mapOf()))

        //Український корпус
        fullBuildings.add(FullBuilding("КУМ", arrayListOf(
                FullBuildingPair("Скорочена назва", "КУМ"),
                FullBuildingPair("Кількість поверхів", "3"),
                FullBuildingPair("Адреса", "вул. Весніна 5а")),
                mapOf()))

        //Учбовий корпус №1
        fullBuildings.add(FullBuilding("У1", arrayListOf(
                FullBuildingPair("Скорочена назва", "У1"),
                FullBuildingPair("Кількість поверхів", "14"),
                FullBuildingPair("Рік будівництва", "1977"),
                FullBuildingPair("Архітектор", "Овсянкін І.Д., Лившиц В.І."),
                FullBuildingPair("Висота", "60м"),
                FullBuildingPair("Ліфт", "6"),
                FullBuildingPair("Буфет", "3 поверх")),
                mapOf("Деканати" to arrayListOf("Інститут економіки, менеджменту і міжнародного бізнесу"))))


        //Учбовий корпус №2
        fullBuildings.add(FullBuilding("У2", arrayListOf(
                FullBuildingPair("Скорочена назва", "У2"),
                FullBuildingPair("Кількість поверхів", "7 + цокольний"),
                FullBuildingPair("Рік будівництва", "1984"),
                FullBuildingPair("Ліфт", "4"),
                FullBuildingPair("Буфет", "1 поверх (праворуч)")),
                mapOf("Відділи та підрозділи" to arrayListOf("Вісник НТУ \"ХПІ\" (2 пов.)", "Канцторвари (1 пов.)",
                        "Приймальна комісія (1 пов.)", "Студенський Альянс (к.104)"),
                        "Деканати" to arrayListOf("Комп'ютерних наук і програмної інженерії", "Інженерно-фізичний інститут"))))

        //Учбовий корпус №3
        fullBuildings.add(FullBuilding("У3", arrayListOf(
                FullBuildingPair("Скорочена назва", "У3"),
                FullBuildingPair("Кількість поверхів", "4"),
                FullBuildingPair("Адреса", "вул. Гамарника 2")),
                mapOf()))

        //Учбовий корпус №4
        fullBuildings.add(FullBuilding("У4", arrayListOf(
                FullBuildingPair("Скорочена назва", "У4"),
                FullBuildingPair("Кількість поверхів", "3"),
                FullBuildingPair("Адреса", "вул. Пушкінська 85")),
                mapOf()))

        //Учбовий корпус №5
        fullBuildings.add(FullBuilding("У5", arrayListOf(
                FullBuildingPair("Скорочена назва", "У5"),
                FullBuildingPair("Кількість поверхів", "4"),
                FullBuildingPair("Адреса", "вул. Пушкінська 79/2 (напроти ПС)")),
                mapOf("Деканати" to arrayListOf("Соціально-гуманітарних технологій"))))

        //Фізичний корпус
        fullBuildings.add(FullBuilding("ФК", arrayListOf(
                FullBuildingPair("Скорочена назва", "ФК"),
                FullBuildingPair("Кількість поверхів", "4"),
                FullBuildingPair("Рік будівництва", "1875-1877"),
                FullBuildingPair("Архітектор", "Генріхсен Р.Р.")),
                mapOf()))

        //Хімічний корпус
        fullBuildings.add(FullBuilding("ХК", arrayListOf(
                FullBuildingPair("Скорочена назва", "ХК"),
                FullBuildingPair("Кількість поверхів", "4"),
                FullBuildingPair("Рік будівництва", "1875-1877"),
                FullBuildingPair("Архітектор", "Генріхсен Р.Р."),
                FullBuildingPair("Висота", "18м")),
                mapOf("Деканати" to arrayListOf("Інститут хімічних технологій та інженерії"))))

        //Електротехнічний корпус
        fullBuildings.add(FullBuilding("ЕК", arrayListOf(
                FullBuildingPair("Скорочена назва", "ЕК"),
                FullBuildingPair("Кількість поверхів", "3 + цокольний"),
                FullBuildingPair("Рік будівництва", "1930-ті"),
                FullBuildingPair("Архітектор", "Бекетов А.Н."),
                FullBuildingPair("Буфет", "1 поверх")),
                mapOf("Деканати" to arrayListOf("Інститут енергетики, електроніки та електромеханіки"))))

        return fullBuildings
    }

    fun getAllShortBuildings() : MutableList<ShortBuilding> = shortBuildings.toMutableList()

    fun getShortBuildingByShortName(shortName: String) : ShortBuilding? {
        shortBuildings.filter { it.shortName == shortName }.forEach { return it }
        return null
    }

    fun getFullBuildingByShortName(shortName: String) : FullBuilding? {
        fullBuildings.filter { it.shortName == shortName }.forEach { return it }
        return null
    }
}