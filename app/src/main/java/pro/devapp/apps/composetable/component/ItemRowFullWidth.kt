package pro.devapp.apps.composetable.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pro.devapp.apps.composetable.data.TableRowFull

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