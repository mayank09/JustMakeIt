package me.appic.justmakeit.models

data class FilterData(
    val Cif: String,
    val companyName: String,
    val hierarchy: List<Hierarchy>
)