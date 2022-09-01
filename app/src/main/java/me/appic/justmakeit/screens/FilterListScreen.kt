package me.appic.justmakeit.screens

import android.accounts.Account
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.node.modifierElementOf
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.NavHostController
import com.google.gson.Gson
import me.appic.justmakeit.R
import me.appic.justmakeit.components.BottomSheetHeader
import me.appic.justmakeit.models.BrandName
import me.appic.justmakeit.models.Hierarchy
import me.appic.justmakeit.viewModel.MainViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterListScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    gsonString: String?
) {
    val filteredData = viewModel.filterResult.value?.filterData

    Log.d("json data", gsonString!!)
    val gson = Gson()

    /*val itemList = remember {
        mutableStateOf(gson.fromJson(gsonString, Array<Hierarchy>::class.java).asList())
    }*/
    val itemList = remember {
        mutableStateOf(viewModel.accountList.value)
    }


    fun setSelectedAccounts(){
        itemList.value?.filter {
            it.isSelected
        }
}
    val itemm = itemList.value

    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val isAllChecked = remember { mutableStateOf(false) }
    val isAllClear = remember { mutableStateOf(true)}
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            BottomSheetHeader(label = itemList.value?.size.toString()) {
            }
            Row(Modifier.fillMaxWidth()) {
                Checkbox(checked = isAllChecked.value, onCheckedChange = {
                    isAllChecked.value = it
                    itemList.value = itemm?.map {hierarchy ->
                        hierarchy.isSelected = it
                            hierarchy
                    }

                })
                Text(text = stringResource(id = R.string.select_all))
                Text(text = stringResource(id = R.string.clear),
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    modifier = Modifier.clickable {
                        isAllChecked.value = false
                        itemList.value = itemm?.map {hierarchy ->
                            hierarchy.isSelected = false
                            hierarchy
                        }
                 })
            }
            Divider()
            LazyColumn() {
                itemm?.forEachIndexed{i,hire ->
                    val isItemChecked = mutableStateOf(hire.isSelected)
                    item{
                            Row() {
                            Checkbox(checked =  isItemChecked.value,
                                onCheckedChange = {
                                    isItemChecked.value= it
                                    itemList.value = itemm.map {hierarchy ->
                                        if(hierarchy.accountNumber == hire.accountNumber)
                                            hierarchy.isSelected = it
                                        hierarchy
                                    }
                                })
                            Text(text = hire.accountNumber)
                        }
                        Divider()
                    }
                }

            }
            Button(modifier = Modifier.fillMaxWidth(), onClick = {  },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue),)
            {
                Text(text = stringResource(id = R.string.add_to_filter), color = Color.White)
            }
        }) {

    }
}