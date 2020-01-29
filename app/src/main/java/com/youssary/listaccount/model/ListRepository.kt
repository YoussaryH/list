package com.youssary.listaccount.model

import android.Manifest
import android.app.Application
import retrofit2.await

class ListRepository(application: Application) {

    private val coarsePermissionChecker = PermissionChecker(application, Manifest.permission.INTERNET
    )
    companion object {
        private  val list  = listOf<AccountDbResult>()
    }

    suspend fun getList() =
        APIDb.service
            .getItems()
            .await()

    suspend fun findList(): MutableList<AccountDbResult> {
        val success = coarsePermissionChecker.check()
        return if (success) getList() else list.toMutableList()
    }

}