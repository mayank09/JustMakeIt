package me.appic.justmakeit.components

import android.accounts.Account
import android.content.res.Resources
import android.service.autofill.OnClickAction
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.appic.justmakeit.R
import me.appic.justmakeit.models.BrandName
import me.appic.justmakeit.models.FilterData
import me.appic.justmakeit.models.Hierarchy
import me.appic.justmakeit.models.LocationName

@Composable
fun BottomSheetHeader(label: String, onClick:() -> Unit ){
    Box(Modifier.fillMaxWidth()) {
        Text(
            text = label,
            modifier = Modifier.align(Alignment.Center)
        )
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(32.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_close_24),
                contentDescription = ""
            )
        }
    }
    Divider(color = Color.Black, thickness = 1.dp)
}

@Composable
fun CompanyFilter(filterData: List<FilterData>?){
    LazyRow (){
        filterData?.let {
            items (it){ filter ->
                FilterCard(string = filter.companyName)

            }
        }
    }
}
@Composable
fun AccountCard(account: List<Hierarchy>?, onClick:() -> Unit ){
    SelectionListItem(name = stringResource(R.string.select_acc), list = account, onClick = onClick)
}

@Composable
fun BrandCard(brands: List<BrandName>?, onClick:() -> Unit){
    SelectionListItem(name = stringResource(R.string.select_brand), list = brands, onClick = onClick )
}

@Composable
fun LocationCard(locationNames: List<LocationName>?, onClick:() -> Unit){
    SelectionListItem(name = stringResource(R.string.select_loc), list = locationNames, onClick = onClick)
}

@Composable
fun Filters(filterList: List<String>) {
    LazyRow (){
        items (filterList){ filter ->
            FilterCard(string = filter)
        }
    }
}

@Composable
fun FilterCard(string: String){
    Box(modifier = Modifier
        .background(Color.Blue)) {
        Text(text = string, color = Color.White,
            modifier = Modifier.padding(16.dp),
            overflow = TextOverflow.Ellipsis)
    }
}
@Composable
fun <T>SelectionList(list: List<FilteredData>){
    LazyColumn(){
        items(list) {filterData ->
            //SelectionListItem(filterData)
        }
    }
}

@Composable
fun <T> SelectionListItem(name: String, list: List<T>?, onClick: () -> Unit){

        Row(modifier = Modifier.clickable{onClick()}) {
            var size = 0
            list?.let {
                size = it.size
            }
            Text(text = name)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = size.toString())
            Icon(painter = painterResource(id = R.drawable.ic_baseline_chevron_right_24), contentDescription = null)
    }
        Divider(color = Color.Black, thickness = 1.dp)
}

@Composable
fun AllFilterListItem(isChecked: Boolean, name: String){
    Row() {
        Checkbox(checked = isChecked, onCheckedChange = {})
        Text(text = name)
    }
}

data class FilteredData(val name: String, val count: Int?)

