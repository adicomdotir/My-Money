package ir.adicom.app.mymoney.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ir.adicom.app.mymoney.models.ExpenseAndCategory
import ir.adicom.app.mymoney.models.Expense

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addExpense(expense: Expense)

    @Update
    suspend fun updateExpense(expense: Expense)

    @Delete
    suspend fun deleteExpense(expense: Expense)

    @Query("SELECT * FROM expenses ORDER BY date DESC ")
    fun getAllExpenses(): LiveData<List<Expense>>

    @Query("SELECT 1 FROM expenses WHERE categoryId = :categoryId")
    fun existCategoryIdOnExpense(categoryId: Int): Boolean

    @Transaction
    @Query("SELECT * FROM expenses ORDER BY date DESC ")
    fun getAllExpensesWithCategory(): LiveData<List<ExpenseAndCategory>>
}