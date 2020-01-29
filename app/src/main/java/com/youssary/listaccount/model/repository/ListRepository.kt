package com.youssary.listaccount.model.repository

import android.Manifest
import com.youssary.listaccount.App
import com.youssary.listaccount.Util
import com.youssary.listaccount.database.ListDB
import com.youssary.listaccount.model.Utils
import com.youssary.listaccount.model.permision.PermissionChecker
import com.youssary.listaccount.model.server.APIDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await
import java.text.SimpleDateFormat
import java.util.*
import com.youssary.listaccount.database.ListDB as DBList
import com.youssary.listaccount.model.server.AccountDbResult as ServerList

class ListRepository(application: App) {
    private val db = application.db

    suspend fun findListRoom(): List<ListDB> = withContext(Dispatchers.IO) {

        with(db.ListDao()) {
            if (listCount() > 0) {
                clearAll()
            }
            if (listCount() <= 0) {

                val list = APIDb.service
                    .getItems()
                    .await()

                list.forEach {
                    var isDateOK = Util.validarFecha(it.date.toString())
                    it.isdateok = isDateOK
                    it.date = Util.formatFecha(it.date!!)
                    it.id = 3371
                }
                insertList(list.map(ServerList::convertToDbMovie))
            }
            getData()
        }
    }

    suspend fun findById(id: Int): DBList = withContext(Dispatchers.IO) {
        db.ListDao().findById(id)
    }

    suspend fun update(list: DBList) = withContext(Dispatchers.IO) {
        db.ListDao().updateList(list)
    }

    private val coarsePermissionChecker =
        PermissionChecker(
            application, Manifest.permission.INTERNET
        )

    companion object {
        private val list = listOf<ServerList>().toMutableList()
    }

}

private fun ServerList.convertToDbMovie() = DBList(
    0,
    id,
    date,
    amount,
    fee,
    description,
    isdateok
)