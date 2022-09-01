package me.appic.justmakeit.models

data class Response(
    val errorCode: String?,
    val filterData: List<FilterData>?,
    val message: String?,
    val status: String?
)