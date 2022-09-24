package pro.devapp.apps.composetable

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import pro.devapp.apps.composetable.table.ComplexMultiScrollTable
import pro.devapp.apps.composetable.table.MultiScrollTable
import pro.devapp.apps.composetable.table.MultiScrollTableOptimized
import pro.devapp.apps.composetable.ui.theme.ComposeTableTheme

class MainActivity : ComponentActivity() {

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setContent {
            ComposeTableTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MultiScrollTable(
                        viewModel.tableData,
                        viewModel.isLoading,
                        { _, _ ->
                            Log.d("SCROLL", "onScroll")
                        },
                    ){
                        viewModel.loadMoreData()
                    }
                }
            }
        }

        viewModel.loadMoreData()
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTableTheme {
        Greeting("Android")
    }
}