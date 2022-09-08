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
    val price2: TableRowItem,
    val price3: TableRowItem,
    val price4: TableRowItem,
    val price5: TableRowItem,
    val price6: TableRowItem
)

val tableHeader = listOf(
    TableHeaderItem("Column 1"),
    TableHeaderItem("Column 2"),
    TableHeaderItem("Column 3"),
    TableHeaderItem("Column 4"),
    TableHeaderItem("Column 5"),
    TableHeaderItem("Column 6")
)