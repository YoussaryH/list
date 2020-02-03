package com.youssary.database

import com.youssary.domain.ListDB
import java.text.SimpleDateFormat
import java.util.*

class ListRepository(val localDataSource : LocalDataSource, val remoteDataSouse:RemoteDataSouse){

    fun getList():List<ListDB>{

        if(localDataSource.isEmpty()){

            val list = remoteDataSouse.getList()
            list.forEach {
                var isDateOK = validarFecha(it.date.toString())
                it.isdateok = isDateOK
            }
            localDataSource.saveList(list)
        }

        return localDataSource.getList()
    }
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
interface LocalDataSource{
    fun isEmpty(): Boolean
    abstract  fun saveList(listDB : List<ListDB>)
    abstract  fun getList(): List<ListDB>
}
interface RemoteDataSouse{
    abstract fun getList():List<ListDB>
}


