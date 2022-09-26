package pro.devapp.apps.composetable.table

import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.scrollBy
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.LiveData
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import pro.devapp.apps.composetable.component.*
import pro.devapp.apps.composetable.data.*

@Composable
fun ComplexMultiScrollTableWithHeadersLite(
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

    val coroutineScope = rememberCoroutineScope()

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

    var headers by remember {
        mutableStateOf(mapOf<Int, TableRowFull>())
    }
    var headersPosition by remember {
        mutableStateOf(mapOf<Int, Float>())
    }

    LaunchedEffect(lazyListStateStickyColumn.firstVisibleItemScrollOffset) {
        onVerticalScroll(
            lazyListStateStickyColumn.isScrollInProgress,
            lazyListStateStickyColumn.firstVisibleItemIndex <= 1
        )

        if (lazyListStateStickyColumn.layoutInfo.visibleItemsInfo.isNotEmpty()) {
            val firstIndex = lazyListStateStickyColumn.layoutInfo.visibleItemsInfo[0].index
            val lastIndex =
                lazyListStateStickyColumn.layoutInfo.visibleItemsInfo[lazyListStateStickyColumn.layoutInfo.visibleItemsInfo.size - 1].index
            val mutableHeaders = headers.toMutableMap()
            headers.keys.filter { it !in firstIndex..lastIndex }
                .forEach {
                    mutableHeaders.remove(it)
                }
            headers = mutableHeaders
        }
    }

    var lastDragAmount = 0f

    fun handleAnimation(dragAmount: Float) {
        coroutineScope.launch {
            lazyListStateLeftTable.scrollBy(-dragAmount)
            lazyListStateRightTable.scrollBy(-dragAmount)
            lazyListStateStickyColumn.scrollBy(-dragAmount)
            lastDragAmount = dragAmount
        }
    }

    fun handleFinishScroll() {
        coroutineScope.launch {
            val def1 = async {
                lazyListStateStickyColumn.animateScrollBy(-lastDragAmount*2, keyframes{
                    durationMillis = 100
                })
            }
            val def2 = async {
                lazyListStateLeftTable.animateScrollBy(-lastDragAmount*2, keyframes{
                    durationMillis = 100
                })
            }
            val def3 = async {
                lazyListStateRightTable.animateScrollBy(-lastDragAmount*2, keyframes{
                    durationMillis = 100
                })
            }
            def1.await()
            def2.await()
            def3.await()
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

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Headers
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cellHeight)
                    .zIndex(200f)
            ) {

                Box(
                    modifier = Modifier
                        .height(cellHeight)
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
                        Row {
                            tableHeader.forEach { item ->
                                ItemRowHeader(cellHeight, item)
                            }
                        }
                    }
                }

                FirstColumnHeader(
                    firstColumnWidth = firstColumnWidth,
                    height = cellHeight,
                    text = "Title"
                )

                Box(
                    modifier = Modifier
                        .height(cellHeight)
                        .weight(0.5f)
                        .background(Color.Red)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .horizontalScroll(
                                state = horizontalScrollStateRight
                            )
                    ) {
                        Row {
                            tableHeader.forEach { item ->
                                ItemRowHeader(cellHeight, item)
                            }
                        }
                    }
                }

            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Tables
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
                                    .disabledHorizontalPointerInputScroll()
                                    .pointerInput(Unit) {
                                        detectVerticalDragGestures(
                                            onDragEnd = {
                                                handleFinishScroll()
                                            },
                                            onVerticalDrag = { pointer, dragAmount ->
                                                handleAnimation(dragAmount)
                                            }
                                        )
                                    },
                                contentPadding = PaddingValues(bottom = 8.dp),
                                state = lazyListStateLeftTable,
                                userScrollEnabled = false
                            ) {
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
                            .disabledHorizontalPointerInputScroll()
                            .pointerInput(Unit) {
                                detectVerticalDragGestures(
                                    onDragEnd = {
                                        handleFinishScroll()
                                    },
                                    onVerticalDrag = { pointer, dragAmount ->
                                        handleAnimation(dragAmount)
                                    }
                                )
                            },
                        contentPadding = PaddingValues(bottom = 8.dp),
                        state = lazyListStateStickyColumn,
                        userScrollEnabled = false
                    ) {
                        itemsIndexed(
                            items = data,
                            key = { _, item ->
                                item.id
                            }
                        ) { index, item ->
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
                                            headersPosition = headersPosition
                                                .toMutableMap()
                                                .apply { put(index, it.positionInParent().y) }
                                        }
                                ) {
                                    if (headers[index] == null){
                                        headers = headers.toMutableMap().apply { put(index, item) }
                                    }
                                }
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
                                    .disabledHorizontalPointerInputScroll()
                                    .pointerInput(Unit) {
                                        detectVerticalDragGestures(
                                            onDragEnd = {
                                                handleFinishScroll()
                                            },
                                            onVerticalDrag = { pointer, dragAmount ->
                                                handleAnimation(dragAmount)
                                            }
                                        )
                                    },
                                contentPadding = PaddingValues(bottom = 8.dp),
                                state = lazyListStateRightTable,
                                userScrollEnabled = false
                            ) {
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

                headers
                    .forEach {
                        val yPx = headersPosition[it.key] ?: 0f
                        val y = LocalDensity.current.run {
                            yPx.toDp()
                        }
                        Box(
                            modifier = Modifier
                                .offset(x = 0.dp, y = y)
                                .fillMaxWidth()
                                .height(cellHeight)
                                .background(color = Color.Blue)
                                .zIndex(10f),
                            contentAlignment = Alignment.Center
                        ) {
                            ItemRowFullWidth(
                                cellHeight = cellHeight,
                                item = it.value
                            )
                        }
                    }
            }
        }
    }
}