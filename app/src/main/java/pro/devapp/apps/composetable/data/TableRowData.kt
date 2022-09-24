package pro.devapp.apps.composetable.data

sealed class TableRow(open val id: Long)

data class TableRowData(
    override val id: Long,
    val name: TableCoinNameItem,
    val price: TableRowItem,
    val price2: TableRowItem,
    val price3: TableRowItem,
    val price4: TableRowItem,
    val price5: TableRowItem,
    val price6: TableRowItem
): TableRow(id)

data class TableRowFull(
    override val id: Long,
    val title: String
): TableRow(id)