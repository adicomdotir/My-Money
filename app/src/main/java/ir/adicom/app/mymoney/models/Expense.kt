package ir.adicom.app.mymoney.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "expenses", foreignKeys = [
    ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"],
        onDelete = ForeignKey.CASCADE
    )
])
@Parcelize
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val price: Long,
    val categoryId: Int
): Parcelable {}