package pro.devapp.apps.composetable

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pro.devapp.apps.composetable.ui.theme.ComposeTableTheme

class MainActivity : ComponentActivity() {

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeTableTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column (
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                        Text(text = "Multi scroll table with sticky header and column")
                        Button(onClick = {
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    MultiScrollTableActivity::class.java)
                            )
                        }) {
                            Text(text = "Multi scroll table")
                        }
                        Text(text = "Multi scroll table with using pointerInput")
                        Button(onClick = {
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    MultiScrollTable2Activity::class.java)
                            )
                        }) {
                            Text(text = "Multi scroll table 2")
                        }
                        Text(text = "Complex multi scroll table with sticky column in the center")
                        Button(onClick = {
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    MultiScrollComplexTableActivity::class.java)
                            )
                        }) {
                            Text(text = "Complex table")
                        }
                        Text(text = "Complex multi scroll table with sticky column in the center and headers")
                        Button(onClick = {
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    MultiScrollComplexTableWithHeadersActivity::class.java)
                            )
                        }) {
                            Text(text = "Complex table with headers")
                        }
                    }
                }
            }
        }
    }
}