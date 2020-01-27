package com.youssary.listaccount.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class AccountDbResult : Parcelable {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("date")
    @Expose
    var date: String? = null
    @SerializedName("amount")
    @Expose
    var amount: Double = 0.0
    @SerializedName("fee")
    @Expose
    var fee: Double = 0.0
    @SerializedName("description")
    @Expose
    var description: String? = null

    protected constructor(`in`: Parcel) {
        id = `in`.readValue(Int::class.java.classLoader) as Int?
        date = `in`.readValue(String::class.java.classLoader) as String?
        amount =
            `in`.readValue(Double::class.java.classLoader) as Double
        fee = `in`.readValue(Double::class.java.classLoader) as Double
        description =
            `in`.readValue(String::class.java.classLoader) as String?
    }

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param date
     * @param amount
     * @param fee
     * @param description
     * @param id
     */
    constructor(
        id: Int?,
        date: String?,
        amount: Double,
        fee: Double,
        description: String?
    ) : super() {
        this.id = id
        this.date = date
        this.amount = amount
        this.fee = fee
        this.description = description
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(date)
        parcel.writeValue(amount)
        parcel.writeValue(fee)
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

    fun getTotal(): Double? {
        return (amount+fee)
    }
}