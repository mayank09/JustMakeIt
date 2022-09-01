package me.appic.justmakeit.screens

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.google.gson.Gson
import kotlinx.coroutines.launch
import me.appic.justmakeit.*
import me.appic.justmakeit.R
import me.appic.justmakeit.components.*
import me.appic.justmakeit.models.BrandName
import me.appic.justmakeit.models.FilterData
import me.appic.justmakeit.models.LocationName
import me.appic.justmakeit.viewModel.MainViewModel

@ExperimentalMaterialApi
@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel) {

    val filteredData = viewModel.filterResult.value?.filterData

    val accounts = filteredData?.get(0)?.hierarchy
    val brandNames = accounts?.filterBrandsForHierarchy()
    val locations = brandNames?.filterLocationsForBrands()

    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Expanded
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    BottomSheetHeader(label = stringResource(R.string.apply_filter)) {
                        scope.launch {
                            sheetState.collapse()
                        }
                    }
                    CompanyFilter(filteredData)
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray)
                    )
                    {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)) {
                            Row(){
                                FilterCard(string = "Acc No: ${accounts?.size.toString()}")
                                FilterCard(string = "Brand: ${brandNames?.size.toString()}")
                                FilterCard(string = "Location: ${locations?.size.toString()}")
                            }

                        }

                        ClickableText(text = AnnotatedString(stringResource(id = R.string.clear)),
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            onClick = {}
                        )
                    }
                    AccountCard(account = accounts){
                        viewModel.setAccounts()
                        navController.navigate(NavRoutes.FilterListScreen.route + "/Account")
                    }


                    BrandCard(brands = brandNames){
                            navController.navigate(NavRoutes.FilterListScreen.route + "/Brand")
                    }

                    LocationCard(locationNames = locations){
                        navController.navigate(NavRoutes.FilterListScreen.route + "/Location")
                    }
                }

            }
        },
        sheetBackgroundColor = Color.White,
        sheetPeekHeight = 0.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                scope.launch {
                    if (sheetState.isCollapsed) {
                        sheetState.expand()
                    } else {
                        sheetState.collapse()
                    }
                }
            }) {
                Text(text = stringResource(id = R.string.bottom_sheet))
            }
        }
    }
}