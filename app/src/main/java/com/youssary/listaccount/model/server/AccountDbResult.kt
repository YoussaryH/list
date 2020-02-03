package com.youssary.listaccount.model.server

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AccountDbResult(
    @SerializedName("id")
    @Expose
    var id: Int? = 0,
    @SerializedName("date")
    @Expose
    var date: String? = "",
    @SerializedName("amount")
    @Expose
    var amount: Double = 0.0,
    @SerializedName("fee")
    @Expose
    var fee: Double = 0.0,
    @SerializedName("description")
    @Expose
    var description: String? = "",
    var isdateok: Boolean,
    var total: Double = amount + fee
) : Parcelable {
}

