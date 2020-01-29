package com.youssary.listaccount.database

import androidx.room.*


@Entity
data class ListDB(
    @PrimaryKey(autoGenerate = true) val codeId: Int,
    val id: Int?,
    val date: String?,
    val amount: Double,
    val fee: Double,
    val description: String?,
    var isdateok: Boolean
)