package pro.devapp.apps.composetable.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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