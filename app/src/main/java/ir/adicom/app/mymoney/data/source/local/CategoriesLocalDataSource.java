package ir.adicom.app.mymoney.data.source.local;

import android.support.annotation.NonNull;

import java.util.List;

import ir.adicom.app.mymoney.data.Category;
import ir.adicom.app.mymoney.data.CategoryDao;
import ir.adicom.app.mymoney.data.source.CategoriesDataSource;
import ir.adicom.app.mymoney.util.AppExecutors;

public class CategoriesLocalDataSource implements CategoriesDataSource {

    private static volatile CategoriesLocalDataSource INSTANCE;
    private CategoryDao mCategoryDao;
    private AppExecutors mAppExecutors;

    private CategoriesLocalDataSource(@NonNull AppExecutors appExecutors, @NonNull CategoryDao categoryDao) {
        mAppExecutors = appExecutors;
        mCategoryDao = categoryDao;
    }

    public static CategoriesLocalDataSource getInstance(@NonNull AppExecutors appExecutors, @NonNull CategoryDao categoryDao) {
        if (INSTANCE == null) {
            synchronized (CategoriesLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CategoriesLocalDataSource(appExecutors, categoryDao);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getCategories(@NonNull final LoadCategoriesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Category> categories = mCategoryDao.loadAll();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (categories.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onCategoriesLoaded(categories);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getCategory(@NonNull final Long id, @NonNull final GetCategoriesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Category category = mCategoryDao.load(id);

                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (category != null) {
                            callback.onCategoryLoaded(category);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveCategory(@NonNull final Category category) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mCategoryDao.insert(category);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void updateCategory(final Category category) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mCategoryDao.update(category);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void deleteAllCategories() {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mCategoryDao.deleteAll();
            }
        };

        mAppExecutors.diskIO().execute(deleteRunnable);
    }

    @Override
    public void deleteCategory(@NonNull final Long id) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mCategoryDao.deleteByKey(id);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }
}
