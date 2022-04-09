package pro.devapp.apps.composetable.table

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData

@ExperimentalFoundationApi
@Composable
fun MultiScrollTable(
    list: LiveData<List<TableRow>>,
    isLoading: LiveData<Boolean>,
    onLoadMore: () -> Unit
) {
    //TODO
    // https://developer.android.com/jetpack/compose/lists#large-datasets

    val tableHorizontalScrollStateCoins = rememberLazyListState()
    val horizontalScrollState = rememberScrollState()

    val data by list.observeAsState(emptyList())
    val size = data.size - 1

    val loading by isLoading.observeAsState(false)

    LazyColumn (
        state = tableHorizontalScrollStateCoins,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
        stickyHeader {
            Row (modifier = Modifier.background(Color.White)) {
                Box(modifier = Modifier
                    .height(50.dp)
                    .width(100.dp)) {
                    Text(text = "Coin")
                }
                Row(Modifier.horizontalScroll(enabled = true, state = horizontalScrollState)) {
                    tableHeader.forEach { item ->
                        Box {
                            Text(modifier = Modifier
                                .height(50.dp)
                                .width(100.dp)
                                .align(Alignment.CenterStart),text = item.title)
                        }
                    }
                }
            }
        }
        itemsIndexed(
            items = data,
            key = { _, item ->
                item.id
            }
        ) { index, item ->
            Row {
                Column(modifier = Modifier
                    .height(70.dp)
                    .width(100.dp)) {
                    Column(modifier = Modifier.height(69.dp), verticalArrangement = Arrangement.Center) {
                        Text(text = item.name.title)
                        Text(text = item.name.symbol)
                    }
                    Divider(color = Color.Gray, thickness = 1.dp)
                }
                Row(Modifier.horizontalScroll(enabled = true, state = horizontalScrollState)) {
                    ItemRowValue(item.price)
                    ItemRowValue(item.chg_24h)
                    ItemRowValue(item.chg_7d)
                    ItemRowValue(item.marketCap)
                    ItemRowValue(item.vol_24)
                    ItemRowValue(item.total_vol)
                }
            }
            if (index == size) {
                onLoadMore()
            }
        }
        if (loading) {
            item {
                Box(modifier = Modifier.fillMaxWidth().height(70.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun ItemRowValue(item: TableRowItem) {
    Column(modifier = Modifier
        .height(70.dp)
        .width(100.dp)) {
        Column(modifier = Modifier.height(69.dp), verticalArrangement = Arrangement.Center) {
            Text(text = item.value)
        }
        Divider(color = Color.Gray, thickness = 1.dp)
    }
}