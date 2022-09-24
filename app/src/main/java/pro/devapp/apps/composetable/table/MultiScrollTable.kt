package pro.devapp.apps.composetable.table

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.LiveData
import pro.devapp.apps.composetable.component.*
import pro.devapp.apps.composetable.data.TableRowData
import pro.devapp.apps.composetable.data.tableHeader

@ExperimentalFoundationApi
@Composable
fun MultiScrollTable(
    list: LiveData<List<TableRowData>>,
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