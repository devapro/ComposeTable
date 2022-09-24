package pro.devapp.apps.composetable.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pro.devapp.apps.composetable.data.*
import kotlin.random.Random

private val titles = listOf("A", "B", "C", "D", "E")
private val subtitles = listOf("a", "b", "c", "d", "e")

class MultiScrollTableWithHeadersViewModel: ViewModel() {

    val tableData = MutableLiveData<List<TableRow>>()
    val isLoading = MutableLiveData<Boolean>()

    fun loadMoreData() {
        isLoading.postValue(true)
        viewModelScope.launch {
            val items = tableData.value?.toMutableList() ?: mutableListOf()

            for (i in 1..10){
                items.add(createItem())
                delay(100)
            }
            items.add(
                TableRowFull(
                    id = 122111,
                    title = "Title"
                )
            )
            for (i in 1..10){
                items.add(createItem())
                delay(100)
            }
//            delay(1000)
            tableData.postValue(items)
            isLoading.postValue(false)
        }
    }


    private fun createItem(): TableRow {
        val index = Random.nextInt(0, 4)
        val prefix = Random.nextInt(0, 100)
        return TableRowData (
            id = System.currentTimeMillis(),
            name = TableCoinNameItem(title = titles[index] + prefix.toString(), symbol = subtitles[index] + prefix.toString()),
            price = TableRowItem(value = Math.random().toString().substring(0, 4)),
            price2 = TableRowItem(value = Math.random().toString().substring(0, 4)),
            price3 = TableRowItem(value = Math.random().toString().substring(0, 4)),
            price4 = TableRowItem(value = Math.random().toString().substring(0, 4)),
            price5 = TableRowItem(value = Math.random().toString().substring(0, 4)),
            price6 = TableRowItem(value = Math.random().toString().substring(0, 4))
        )
    }
}