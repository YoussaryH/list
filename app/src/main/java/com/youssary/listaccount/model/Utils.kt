package com.youssary.listaccount.model

import java.text.SimpleDateFormat
import java.util.*

public final class Utils {
    fun validarFecha(fecha: String): Boolean {
        val date = fecha
        val dateformat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val output = SimpleDateFormat("dd.MM.yyyy hh:mm")
        var d: Date? = null
        try {
            d = dateformat.parse(date /*your date as String*/)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

}