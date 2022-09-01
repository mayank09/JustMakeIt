package me.appic.justmakeit

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.appic.justmakeit.models.*
import java.io.IOException

const val RESULT_SUCCESS = "Success"

fun Context.getResponseFromAsset(fileName: String): Response? {
    val jsonString: String
    try {
        jsonString = assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    Log.d("filter Data", jsonString)
    val responseType = object : TypeToken<Response>() {}.type
    return Gson().fromJson(jsonString, responseType)
}

fun List<FilterData>.getFilteredHierarchy(): Triple<MutableList<String>, MutableList<BrandName>, MutableList<LocationName>> {
    val accountNumbers: MutableList<String> = ArrayList()
    val allBrandNames: MutableList<BrandName> = ArrayList()
    val allLocations: MutableList<LocationName> = ArrayList()
    this.forEach { filterData ->
        filterData.hierarchy.forEach { hierarchy ->
            accountNumbers.add(hierarchy.accountNumber)
            hierarchy.brandNameList.forEach { brandNames ->
                allBrandNames.add(brandNames)
                brandNames.locationNameList.forEach {
                    allLocations.add(it)
                }
            }
        }
    }

    return Triple(accountNumbers, allBrandNames, allLocations)
}

fun FilterData.filterAccountForCompanies(): MutableList<Hierarchy> {
    val accounts: MutableList<Hierarchy> = ArrayList()
    this.hierarchy.forEach {
        accounts.add(it)
    }
    return accounts
}

fun List<Hierarchy>.filterBrandsForHierarchy(): MutableList<BrandName> {
    val allBrandNames: MutableList<BrandName> = ArrayList()
    forEach { hierarchy ->
        allBrandNames.addAll(hierarchy.brandNameList)
    }
    return allBrandNames
}

fun List<BrandName>.filterLocationsForBrands(): MutableList<LocationName> {
    val allLocations: MutableList<LocationName> = ArrayList()
    forEach {  brandNames->
        allLocations.addAll(brandNames.locationNameList)
    }
    return allLocations
}
