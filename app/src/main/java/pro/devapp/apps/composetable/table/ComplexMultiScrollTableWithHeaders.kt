package pro.devapp.apps.composetable.table

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.LiveData
import pro.devapp.apps.composetable.component.*
import pro.devapp.apps.composetable.data.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ComplexMultiScrollTableWithHeaders(
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

    val headers = remember {
        hashMapOf<Long, TableRowFull>()
    }
    val headersPosition = remember {
        hashMapOf<Long, Float>()
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

        val firstIndex = lazyListStateStickyColumn.layoutInfo.visibleItemsInfo[0].index
        val lastIndex =
            lazyListStateStickyColumn.layoutInfo.visibleItemsInfo[lazyListStateStickyColumn.layoutInfo.visibleItemsInfo.size - 1].index
        val visibleIds = mutableListOf<Long>()
        for (index in firstIndex..lastIndex) {
            val item = data.getOrNull(index)
            if (item is TableRowFull) {
                if (headers[item.id] == null) {
                    headers[item.id] = item
                }
                visibleIds.add(item.id)
            }
        }
        headers.keys.filter { !visibleIds.contains(it) }
            .forEach {
                headers.remove(it)
            }
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

        headers.forEach {
            val y = LocalDensity.current.run {
                headersPosition[it.key]?.toDp()
            }
            Box(
                modifier = Modifier
                    .offset(x = 0.dp, y = y ?: 0.dp)
                    .fillMaxWidth()
                    .height(cellHeight)
                    .background(color = Color.Blue)
                    .zIndex(100f),
                contentAlignment = Alignment.Center
            ) {
                ItemRowFullWidth(
                    cellHeight = cellHeight,
                    item = it.value
                )
            }
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
                            if (item is TableRowData) {
                                Row {
                                    ItemRowCell(cellHeight, item.price)
                                    ItemRowCell(cellHeight, item.price2)
                                    ItemRowCell(cellHeight, item.price3)
                                    ItemRowCell(cellHeight, item.price4)
                                    ItemRowCell(cellHeight, item.price5)
                                    ItemRowCell(cellHeight, item.price6)
                                }
                            }
                            if (item is TableRowFull) {
                                Box(
                                    modifier = Modifier
                                        .width(firstColumnWidth * 6)
                                        .height(cellHeight)
                                        .background(color = Color.White)
                                )
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
                    if (item is TableRowData) {
                        FirstColumnCell(
                            firstColumnWidth = firstColumnWidth,
                            cellHeight = cellHeight,
                            item = item
                        )
                    }
                    if (item is TableRowFull) {
                        Box(
                            modifier = Modifier
                                .width(firstColumnWidth)
                                .height(cellHeight)
                                .background(color = Color.White)
                                .onGloballyPositioned {
                                    headersPosition[item.id] = it.positionInParent().y
                                }
                        )
                    }
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
                            if (item is TableRowData) {
                                Row {
                                    ItemRowCell(cellHeight, item.price)
                                    ItemRowCell(cellHeight, item.price2)
                                    ItemRowCell(cellHeight, item.price3)
                                    ItemRowCell(cellHeight, item.price4)
                                    ItemRowCell(cellHeight, item.price5)
                                    ItemRowCell(cellHeight, item.price6)
                                }
                            }
                            if (item is TableRowFull) {
                                Box(
                                    modifier = Modifier
                                        .width(firstColumnWidth * 6)
                                        .height(cellHeight)
                                        .background(color = Color.White)
                                )
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
fun ItemRowFullWidth(cellHeight: Dp, item: TableRowFull) {
    Column(
        modifier = Modifier
            .height(cellHeight)
    ) {
        Divider(color = Color.Gray, thickness = 1.dp)
        Box(
            modifier = Modifier
                .height(cellHeight)
                .width(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = item.title)
        }
    }
}