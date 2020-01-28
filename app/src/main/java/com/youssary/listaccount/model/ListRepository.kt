package com.youssary.listaccount.model

import android.app.Activity
import retrofit2.await

class ListRepository(activity: Activity) {


    suspend fun getList() =
        APIDb.service
            .getItems()
            .await()
}