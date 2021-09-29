package ir.adicom.app.mymoney.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ir.adicom.app.mymoney.models.Category
import ir.adicom.app.mymoney.models.Expense

@Database(entities = [Category::class, Expense::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getExpenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "my_money_db"
        ).build()
    }
}