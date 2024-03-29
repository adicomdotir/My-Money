package ir.adicom.app.mymoney.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "categories")
@Parcelize
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String
) : Parcelable {
    override fun toString(): String {
        return title
    }
}