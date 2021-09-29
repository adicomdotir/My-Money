package ir.adicom.app.mymoney

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.adicom.app.mymoney.db.AppDatabase
import ir.adicom.app.mymoney.fragments.CategoryHomeFragment
import ir.adicom.app.mymoney.models.Category
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val categoryHomeFragment = CategoryHomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_fragment_place_holder, categoryHomeFragment)
            .commit()

//        val appDatabase = AppDatabase.invoke(this)
//        MainScope().launch {
//            appDatabase.getCategoryDao().addCategory(Category(0, "Food"))
//            appDatabase.getCategoryDao().addCategory(Category(0, "Health"))
//        }
    }
}