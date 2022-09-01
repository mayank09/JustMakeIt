package me.appic.justmakeit.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import me.appic.justmakeit.getResponseFromAsset
import me.appic.justmakeit.models.FilterData
import me.appic.justmakeit.models.Response
import javax.inject.Inject
import javax.inject.Singleton

@ViewModelScoped
class MainRepository @Inject constructor(
    @ApplicationContext private val context: Context) {

/*    private val responseLiveData = MutableLiveData<Response>()

    val filterData: LiveData<Response>
        get() = responseLiveData*/


    suspend fun getResponse(): Response? {
        return context.getResponseFromAsset("TestJSON.text")
    }


}