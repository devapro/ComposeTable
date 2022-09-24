package pro.devapp.apps.composetable.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pro.devapp.apps.composetable.table.TableHeaderItem


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