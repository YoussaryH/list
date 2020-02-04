package com.youssary.listaccount.support

enum class EFormat(private val value: String) {
    DATE_DATABASE("yyyyMMdd"),
    DATE_DATABASE_SHORT("yyyyMM"),
    DATE_HOUR_DATABASE("yyyyMMddHHmmss"),
    DATE_SCREEN("dd/MM/yyyy"),
    DATE_SCREEN_SHORT("dd/MM"),
    DATE_HOUR_SCREEN("EEEE dd MMMM yyyy  HH:mm:ss"),
    HOUR_MINUTES_DATABASE("HHmm"),
    HOUR_MINUTES_SCREEN("HH:mm"),
    HOUR_MINUTES_SECONDS_DATABASE("HHmmss"),
    HOUR_MINUTES_SECONDS_SCREEN("HH:mm:ss"),
    CALENDAR_DATE_SCREEN("yyyy-MM-dd"),
    CALENDAR_DATE_SCREEN_SHORT("yyyyMM");

    fun value(): String {
        return value
    }

}