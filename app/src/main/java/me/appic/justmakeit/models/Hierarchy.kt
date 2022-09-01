package me.appic.justmakeit.models

data class Hierarchy(
    val accountNumber: String,
    val brandNameList: List<BrandName>,
    var isSelected : Boolean = false
)