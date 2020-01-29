package com.youssary.listaccount.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class AccountDbResult(
    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("date")
    @Expose
    var date: String? = null,
    @SerializedName("amount")
    @Expose
    var amount: Double = 0.0,
    @SerializedName("fee")
    @Expose
    var fee: Double = 0.0,
    @SerializedName("description")
    @Expose
    var description: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(date)
        parcel.writeDouble(amount)
        parcel.writeDouble(fee)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AccountDbResult> {
        override fun createFromParcel(parcel: Parcel): AccountDbResult {
            return AccountDbResult(parcel)
        }

        override fun newArray(size: Int): Array<AccountDbResult?> {
            return arrayOfNulls(size)
        }
    }
    fun getFormat(value: Double): String {
        return String.format("%,.2f", value)
    }
    fun getTotal(): String {
        return getFormat(amount+fee)
    }
}

