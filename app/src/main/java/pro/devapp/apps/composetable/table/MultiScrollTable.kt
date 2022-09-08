package pro.devapp.apps.composetable.table

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.LiveData

@ExperimentalFoundationApi
@Composable
fun MultiScrollTable(
    list: LiveData<List<TableRow>>,
    isLoading: LiveData<Boolean>,
    onVerticalScroll: (Boolean, Boolean) -> Unit,
    onLoadMore: () -> Unit,
) {

    val data by list.observeAsState(emptyList())
    val size = data.size - 1

    val firstColumnWidth = 100.dp
    val cellHeight = 50.dp

    val loading by isLoading.observeAsState(false)

    val lazyListStateMainTable = rememberLazyListState()
    val lazyListStateFirstColumn = rememberLazyListState()
    val horizontalScrollState = rememberScrollState()

    val isMainColumnScrollDisabled by remember {
        derivedStateOf { lazyListStateFirstColumn.isScrollInProgress }
    }

    val isFirstColumnScrollDisabled by remember {
        derivedStateOf { lazyListStateMainTable.isScrollInProgress }
    }

    LaunchedEffect(lazyListStateMainTable.firstVisibleItemScrollOffset) {
        if (!lazyListStateFirstColumn.isScrollInProgress) {
            lazyListStateFirstColumn.scrollToItem(
                lazyListStateMainTable.firstVisibleItemIndex,
                lazyListStateMainTable.firstVisibleItemScrollOffset
            )
        }
    }

    LaunchedEffect(lazyListStateFirstColumn.firstVisibleItemScrollOffset) {
        if (!lazyListStateMainTable.isScrollInProgress) {
            lazyListStateMainTable.scrollToItem(
                lazyListStateFirstColumn.firstVisibleItemIndex,
                lazyListStateFirstColumn.firstVisibleItemScrollOffset
            )
        }
        onVerticalScroll(
            lazyListStateFirstColumn.isScrollInProgress,
            lazyListStateFirstColumn.firstVisibleItemIndex <= 1
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .zIndex(100f)
                .padding(50.dp)
        ) {
            LoadingIndicator(loading)
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .width(firstColumnWidth)
                    .fillMaxHeight()
                    .background(Color.LightGray)
                    .disabledVerticalPointerInputScroll(isFirstColumnScrollDisabled)
                    .disabledHorizontalPointerInputScroll(),
                contentPadding = PaddingValues(bottom = 8.dp),
                state = lazyListStateFirstColumn
            ) {
                stickyHeader {
                    FirstColumnHeader(
                        firstColumnWidth = firstColumnWidth,
                        height = cellHeight,
                        text = "Title"
                    )
                }
                itemsIndexed(
                    items = data,
                    key = { _, item ->
                        item.id
                    }
                ) { index, item ->
                    FirstColumnCell(
                        firstColumnWidth = firstColumnWidth,
                        cellHeight = cellHeight,
                        item = item
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Green)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .horizontalScroll(state = horizontalScrollState)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            .disabledVerticalPointerInputScroll(isMainColumnScrollDisabled)
                            .disabledHorizontalPointerInputScroll(),
                        contentPadding = PaddingValues(bottom = 8.dp),
                        state = lazyListStateMainTable
                    ) {
                        stickyHeader {
                            Row {
                                tableHeader.forEach { item ->
                                    ItemRowHeader(cellHeight, item)
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
                                ItemRowCell(cellHeight, item.price)
                                ItemRowCell(cellHeight, item.price2)
                                ItemRowCell(cellHeight, item.price3)
                                ItemRowCell(cellHeight, item.price4)
                                ItemRowCell(cellHeight, item.price5)
                                ItemRowCell(cellHeight, item.price6)
                            }
                            if (index == size && size > 0) {
                                onLoadMore()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemRowHeader(cellHeight: Dp, item: TableHeaderItem) {
    Box(
        modifier = Modifier
            .height(cellHeight)
            .width(100.dp)
            .background(Color.Green),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = item.title
        )
    }
}

@Composable
fun ItemRowCell(cellHeight: Dp, item: TableRowItem) {
    Column(
        modifier = Modifier
            .height(cellHeight)
            .width(100.dp)
    ) {
        Divider(color = Color.Gray, thickness = 1.dp)
        Box(
            modifier = Modifier
                .height(cellHeight)
                .width(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = item.value)
        }
    }
}

@Composable
fun FirstColumnHeader(
    firstColumnWidth: Dp,
    height: Dp,
    text: String
) {
    Box(
        modifier = Modifier
            .height(height)
            .width(firstColumnWidth)
            .background(Color.LightGray)
            .padding(horizontal = 8.dp)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .width(firstColumnWidth),
            text = text
        )
    }
}

@Composable
fun FirstColumnCell(firstColumnWidth: Dp, cellHeight: Dp, item: TableRow) {
    Box(
        modifier = Modifier
            .width(firstColumnWidth)
            .height(cellHeight)
    ) {
        Column(
            modifier = Modifier
                .width(firstColumnWidth)
                .height(cellHeight)
        ) {
            Divider(color = Color.Gray, thickness = 1.dp)
            Column(
                modifier = Modifier
                    .width(firstColumnWidth)
                    .height(cellHeight)
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = item.name.title)
                Text(text = item.name.symbol)
            }
        }
    }
}

@Composable
fun LoadingIndicator(isLoading: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.Red,
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.Center),
                strokeWidth = 2.dp
            )
        }
    }
}