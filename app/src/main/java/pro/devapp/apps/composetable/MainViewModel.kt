package pro.devapp.apps.composetable

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pro.devapp.apps.composetable.table.TableCoinNameItem
import pro.devapp.apps.composetable.table.TableRow
import pro.devapp.apps.composetable.table.TableRowItem
import kotlin.random.Random

private val coins = listOf("Bitcoin", "Ethereum", "BNB", "Tether", "Solana")
private val symbols = listOf("BTC", "ETH", "BNB", "USDT", "SOL")

class MainViewModel: ViewModel() {

    val tableData = MutableLiveData<List<TableRow>>()
    val isLoading = MutableLiveData<Boolean>()

    fun loadMoreData() {
        isLoading.postValue(true)
        viewModelScope.launch {
            val items = tableData.value?.toMutableList() ?: mutableListOf()

            for (i in 1..20){
                items.add(createItem())
                delay(100)
            }
            delay(1000)
            tableData.postValue(items)
            isLoading.postValue(false)
        }
    }


    private fun createItem(): TableRow {
        val index = Random.nextInt(0, 4)
        val prefix = Random.nextInt(0, 100)
        return TableRow (
            id = System.currentTimeMillis(),
            name = TableCoinNameItem(title = coins[index] + prefix.toString(), symbol = symbols[index] + prefix.toString()),
            price = TableRowItem(value = Math.random().toString().substring(0, 4)),
            chg_24h = TableRowItem(value = Math.random().toString().substring(0, 4)),
            chg_7d = TableRowItem(value = Math.random().toString().substring(0, 4)),
            marketCap = TableRowItem(value = Math.random().toString().substring(0, 4)),
            vol_24 = TableRowItem(value = Math.random().toString().substring(0, 4)),
            total_vol = TableRowItem(value = Math.random().toString().substring(0, 4))
        )
    }
}