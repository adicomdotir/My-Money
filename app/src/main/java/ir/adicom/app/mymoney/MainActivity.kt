package ir.adicom.app.mymoney

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.channels.FileChannel


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_nav).setupWithNavController(navController)

        exportDatabse("my_money_db", applicationContext)
    }
}

fun exportDatabse(databaseName: String, context: Context) {
    try {
        val sd: File = Environment.getExternalStorageDirectory()
        val data: File = Environment.getDataDirectory()
        val currentDBPath =
            "//data//" + context.getPackageName() + "//databases//" + databaseName + ""
        val backupDBPath = "MyMoney.db"
        val currentDB = File(data, currentDBPath)
        val backupDB = File(sd, backupDBPath)
        backupDB.createNewFile()
        if (currentDB.exists()) {
            val src: FileChannel = FileInputStream(currentDB).getChannel()
            val dst: FileChannel = FileOutputStream(backupDB).getChannel()
            dst.transferFrom(src, 0, src.size())
            src.close()
            dst.close()
        }
    } catch (e: Exception) {
        Log.e("MyMoney", e.message)
    }
}
