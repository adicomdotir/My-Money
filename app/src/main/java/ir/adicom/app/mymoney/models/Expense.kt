package ir.adicom.app.mymoney.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "expenses", foreignKeys = [
    ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"],
        onDelete = ForeignKey.CASCADE
    )
])
class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val price: Long,
    val categoryId: Int
)