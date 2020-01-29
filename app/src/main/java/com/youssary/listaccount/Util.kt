package com.youssary.listaccount

import java.text.SimpleDateFormat
import java.util.*

object Util {
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

    fun formatFecha(fecha: String): String {
        val date = fecha
        val dateformat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val output = SimpleDateFormat("dd/MM/yyyy hh:mm")
        var d: Date? = null
        try {
            d = dateformat.parse(date /*your date as String*/)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return d.toString()
    }
}