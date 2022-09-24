package pro.devapp.apps.composetable.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pro.devapp.apps.composetable.data.TableRowData

@Composable
fun FirstColumnCell(firstColumnWidth: Dp, cellHeight: Dp, item: TableRowData) {
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