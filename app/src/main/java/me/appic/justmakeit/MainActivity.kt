package me.appic.justmakeit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.appic.justmakeit.ui.theme.JustMakeItTheme
import me.appic.justmakeit.viewModel.MainViewModel
import me.appic.justmakeit.screens.FilterListScreen
import me.appic.justmakeit.screens.MainScreen

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
  //  private var filteredData: MutableList<FilterData> by mutableStateOf(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JustMakeItTheme {
               // Log.d("from view Model", filteredResult?.message.toString())
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = NavRoutes.MainScreen.route,
                ) {
                    composable(NavRoutes.MainScreen.route) {
                        MainScreen(navController = navController, viewModel)
                    }
                    composable(NavRoutes.FilterListScreen.route+ "/{gsonString}") {
                            backStackEntry ->
                        val gsonString = backStackEntry.arguments?.getString("gsonString")
                        FilterListScreen(navController = navController, viewModel, gsonString)
                    }
                }
/*                viewModel.filerData.observe(this){
                  if(it.status == RESULT_SUCCESS){
                      filteredData = it.filterData.toMutableList()
                  }
                }*/


            }
        }
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JustMakeItTheme {
        //MainScreen()
    }
}
