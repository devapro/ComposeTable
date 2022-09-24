package pro.devapp.apps.composetable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.ViewModelProvider
import pro.devapp.apps.composetable.table.MultiScrollTable
import pro.devapp.apps.composetable.ui.theme.ComposeTableTheme
import pro.devapp.apps.composetable.viewmodel.MultiScrollTableViewModel

class MultiScrollTableActivity : AppCompatActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this).get(MultiScrollTableViewModel::class.java)

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
                    ) {
                        viewModel.loadMoreData()
                    }
                }
            }
        }

        viewModel.loadMoreData()
    }
}