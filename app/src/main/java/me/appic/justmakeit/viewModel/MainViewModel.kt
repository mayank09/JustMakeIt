package me.appic.justmakeit.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.appic.justmakeit.filterAccountForCompanies
import me.appic.justmakeit.filterBrandsForHierarchy
import me.appic.justmakeit.filterLocationsForBrands
import me.appic.justmakeit.models.*
import me.appic.justmakeit.repository.MainRepository
import me.appic.justmakeit.screens.isAllSelected
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val filterResult : MutableState<Response?>  = mutableStateOf(null)

    val accountList : MutableState<List<Hierarchy>?> = mutableStateOf(mutableListOf())
    val brandList : MutableState<List<BrandName>?> = mutableStateOf(mutableListOf())
    val locations : MutableState<List<LocationName>?> = mutableStateOf(mutableListOf())

    init {
        viewModelScope.launch {
            val result = repository.getResponse()
            filterResult.value = result
        }
    }

    fun List<Hierarchy>.getFilteredResult(){


    }

    fun setAccounts(){
        accountList.value = filterResult.value?.filterData?.get(0)?.filterAccountForCompanies()
    }

    fun setBrands(hierarchy: List<Hierarchy>){
        brandList.value = hierarchy.filterBrandsForHierarchy()

    }

    fun setLocations (brandName: List<BrandName>){
        locations.value = brandName.filterLocationsForBrands()
    }



 /*   val filerData: LiveData<List<FilterData>>
        get() = repository.filterData*/




}