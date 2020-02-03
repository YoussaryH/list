package com.youssary.domain


data class ListDB(
    val codeId: Int,
    val id: Int?,
    val date: String?,
    val amount: Double,
    val fee: Double,
    val description: String?,
    var isdateok: Boolean
)