package ir.adicom.app.mymoney.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ir.adicom.app.mymoney.models.Expense

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addExpense(expense: Expense)

    @Update
    suspend fun updateExpense(expense: Expense)

    @Delete
    suspend fun deleteExpense(expense: Expense)

    @Query("SELECT * FROM expenses ORDER BY id DESC ")
    fun getAllExpenses(): LiveData<List<Expense>>
}