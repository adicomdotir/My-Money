package ir.adicom.app.mymoney.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ir.adicom.app.mymoney.models.Category

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategory(category: Category)

    @Update
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM categories ORDER BY id DESC")
    fun getAllCategories(): LiveData<List<Category>>
}