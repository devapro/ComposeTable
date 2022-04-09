package pro.devapp.apps.composetable.table

data class TableHeaderItem(
    val title: String,
    val icon: Int? = null
)

data class TableCoinNameItem(
    val title: String,
    val symbol: String,
    val icon: Int? = null
)

data class TableRowItem (
    val value: String,
    val color: String? = null
)

data class TableRow(
    val id: Long,
    val name: TableCoinNameItem,
    val price: TableRowItem,
    val chg_24h: TableRowItem,
    val chg_7d: TableRowItem,
    val marketCap: TableRowItem,
    val vol_24: TableRowItem,
    val total_vol: TableRowItem
)

val tableHeader = listOf(
    TableHeaderItem("Price(USD)"),
    TableHeaderItem("Chg(24h)"),
    TableHeaderItem("Chg(7D)"),
    TableHeaderItem("Market Cap"),
    TableHeaderItem("Vol (24)"),
    TableHeaderItem("Total Vol")
)

//val tableData = listOf(
//    TableRow(
//        TableCoinNameItem("Bitcoin", "BTC"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01")
//    ),
//    TableRow(
//        TableCoinNameItem("Ethereum", "ETH"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01")
//    ),
//    TableRow(
//        TableCoinNameItem("BNB", "BNB"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01")
//    ),
//    TableRow(
//        TableCoinNameItem("Tether", "USDT"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01")
//    ),
//    TableRow(
//        TableCoinNameItem("Solana", "SOL"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01")
//    ),
//    TableRow(
//        TableCoinNameItem("Solana2", "SOL2"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01")
//    ),
//    TableRow(
//        TableCoinNameItem("Ethereum2", "ETH2"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01")
//    ),
//    TableRow(
//        TableCoinNameItem("BNB3", "BNB3"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01")
//    ),
//    TableRow(
//        TableCoinNameItem("Tether4", "USDT4"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01")
//    ),
//    TableRow(
//        TableCoinNameItem("BNB1", "BNB1"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01")
//    ),
//    TableRow(
//        TableCoinNameItem("Tether1", "USDT2"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01")
//    ),
//    TableRow(
//        TableCoinNameItem("Solana1", "SOL1"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01")
//    ),
//    TableRow(
//        TableCoinNameItem("Solana21", "SOL21"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01")
//    ),
//    TableRow(
//        TableCoinNameItem("Ethereum22", "ETH22"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01")
//    ),
//    TableRow(
//        TableCoinNameItem("BNB31", "BNB31"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01")
//    ),
//    TableRow(
//        TableCoinNameItem("Tether41", "USDT41"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01")
//    ),
//    TableRow(
//        TableCoinNameItem("BNB11", "BNB11"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01")
//    ),
//    TableRow(
//        TableCoinNameItem("Tether11", "USDT21"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01")
//    ),
//    TableRow(
//        TableCoinNameItem("Solana11", "SOL11"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01")
//    ),
//    TableRow(
//        TableCoinNameItem("Solana211", "SOL211"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01")
//    ),
//    TableRow(
//        TableCoinNameItem("Ethereum221", "ETH221"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01"),
//        TableRowItem("1,01")
//    )
//)