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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ComplexMultiScrollTable(
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

    val lazyListStateRightTable = rememberLazyListState()
    val lazyListStateLeftTable = rememberLazyListState()

    val lazyListStateStickyColumn = rememberLazyListState()

    val horizontalScrollStateRight = rememberScrollState()
    val horizontalScrollStateLeft = rememberScrollState()

    val isMainColumnScrollDisabled by remember {
        derivedStateOf { lazyListStateStickyColumn.isScrollInProgress }
    }

    val isFirstColumnScrollDisabled by remember {
        derivedStateOf { lazyListStateRightTable.isScrollInProgress }
    }

    LaunchedEffect(lazyListStateLeftTable.firstVisibleItemScrollOffset) {
        if (!lazyListStateStickyColumn.isScrollInProgress && !lazyListStateRightTable.isScrollInProgress) {
            lazyListStateStickyColumn.scrollToItem(
                lazyListStateLeftTable.firstVisibleItemIndex,
                lazyListStateLeftTable.firstVisibleItemScrollOffset
            )
            lazyListStateRightTable.scrollToItem(
                lazyListStateLeftTable.firstVisibleItemIndex,
                lazyListStateLeftTable.firstVisibleItemScrollOffset
            )
        }
    }

    LaunchedEffect(lazyListStateRightTable.firstVisibleItemScrollOffset) {
        if (!lazyListStateStickyColumn.isScrollInProgress && !lazyListStateLeftTable.isScrollInProgress) {
            lazyListStateStickyColumn.scrollToItem(
                lazyListStateRightTable.firstVisibleItemIndex,
                lazyListStateRightTable.firstVisibleItemScrollOffset
            )
            lazyListStateLeftTable.scrollToItem(
                lazyListStateRightTable.firstVisibleItemIndex,
                lazyListStateRightTable.firstVisibleItemScrollOffset
            )
        }
    }

    LaunchedEffect(lazyListStateStickyColumn.firstVisibleItemScrollOffset) {
        if (!lazyListStateRightTable.isScrollInProgress && !lazyListStateLeftTable.isScrollInProgress) {
            lazyListStateRightTable.scrollToItem(
                lazyListStateStickyColumn.firstVisibleItemIndex,
                lazyListStateStickyColumn.firstVisibleItemScrollOffset
            )
            lazyListStateLeftTable.scrollToItem(
                lazyListStateStickyColumn.firstVisibleItemIndex,
                lazyListStateStickyColumn.firstVisibleItemScrollOffset
            )
        }
        onVerticalScroll(
            lazyListStateStickyColumn.isScrollInProgress,
            lazyListStateStickyColumn.firstVisibleItemIndex <= 1
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

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.5f)
                    .background(Color.Red)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .horizontalScroll(
                            state = horizontalScrollStateLeft,
                            reverseScrolling = true
                        )
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            .disabledVerticalPointerInputScroll(isMainColumnScrollDisabled)
                            .disabledHorizontalPointerInputScroll(),
                        contentPadding = PaddingValues(bottom = 8.dp),
                        state = lazyListStateLeftTable
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

            LazyColumn(
                modifier = Modifier
                    .width(firstColumnWidth)
                    .fillMaxHeight()
                    .background(Color.LightGray)
                    .disabledVerticalPointerInputScroll(isFirstColumnScrollDisabled)
                    .disabledHorizontalPointerInputScroll(),
                contentPadding = PaddingValues(bottom = 8.dp),
                state = lazyListStateStickyColumn
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
                ) { _, item ->
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
                    .weight(0.5f)
                    .background(Color.Green)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .horizontalScroll(state = horizontalScrollStateRight)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            .disabledVerticalPointerInputScroll(isMainColumnScrollDisabled)
                            .disabledHorizontalPointerInputScroll(),
                        contentPadding = PaddingValues(bottom = 8.dp),
                        state = lazyListStateRightTable
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