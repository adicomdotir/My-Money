package ir.adicom.app.mymoney;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import ir.adicom.app.mymoney.data.Category;
import ir.adicom.app.mymoney.data.CategoryDao;
import ir.adicom.app.mymoney.data.DaoMaster;
import ir.adicom.app.mymoney.data.DaoSession;
import ir.adicom.app.mymoney.data.Expense;
import ir.adicom.app.mymoney.data.ExpenseDao;

/**
 *
 * Created by Y.P on 29/08/2018.
 */

public class App extends Application {

    private DaoSession daoSession;
    public static final String TAG = "TAG";

    @Override
    public void onCreate() {
        super.onCreate();

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/samim.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "mymoney-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        // Seed
        if(daoSession.getCategoryDao().loadAll().size() == 0) {
            CategoryDao categoryDao = daoSession.getCategoryDao();
            categoryDao.insert(new Category(1L, "خوراک"));
            categoryDao.insert(new Category(2L, "پوشاک"));
            categoryDao.insert(new Category(3L, "وسیله نقلیه"));
            categoryDao.insert(new Category(4L, "سایر"));
        }
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
