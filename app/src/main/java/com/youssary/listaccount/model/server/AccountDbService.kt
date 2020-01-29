package com.youssary.listaccount.model.server

import com.youssary.listaccount.model.server.AccountDbResult
import retrofit2.Call
import retrofit2.http.GET

interface AccountDbService {

    @GET("1a30k8")
    fun getItems(): Call<MutableList<AccountDbResult>>

}