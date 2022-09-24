package pro.devapp.apps.composetable.data

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

val tableHeader = listOf(
    TableHeaderItem("Column 1"),
    TableHeaderItem("Column 2"),
    TableHeaderItem("Column 3"),
    TableHeaderItem("Column 4"),
    TableHeaderItem("Column 5"),
    TableHeaderItem("Column 6")
)