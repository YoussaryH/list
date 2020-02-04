package com.youssary.listaccount

import com.youssary.listaccount.support.EFormat
import com.youssary.listaccount.support.EFormatData
import java.text.DecimalFormat
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
    fun getDate(it: String): String? {
        val dateFormat =
            SimpleDateFormat(EFormat.DATE_HOUR_SCREEN.value(), Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

    fun formatearNumeroDecimal2Decimales(
        numero: Double,
        separadorMiles: Boolean
    ): String? {
        val formato =
            DecimalFormat.getInstance(Locale.getDefault()) as DecimalFormat
        if (separadorMiles) {
            formato.applyPattern(EFormatData.FMT_NUMERO_NORMALIZADO_TRES_DECIMALES_SEPARADOR_MILES.value())
        } else {
            formato.applyPattern(EFormatData.FMT_NUMERO_NORMALIZADO_TRES_DECIMALES_SEPARADOR_MILES.value())
        }
        return formato.format(numero)
    }

}