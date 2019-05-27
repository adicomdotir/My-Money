package ir.adicom.app.mymoney.data.source.local;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ir.adicom.app.mymoney.data.CategoryDao;
import ir.adicom.app.mymoney.data.DaoSession;
import ir.adicom.app.mymoney.data.Expense;
import ir.adicom.app.mymoney.data.ExpenseDao;
import ir.adicom.app.mymoney.data.Filter;
import ir.adicom.app.mymoney.data.source.ExpensesDataSource;
import ir.adicom.app.mymoney.util.AppExecutors;

public class ExpensesLocalDataSource implements ExpensesDataSource {

    private static volatile ExpensesLocalDataSource INSTANCE;
    private ExpenseDao mExpenseDao;
    private AppExecutors mAppExecutors;
    private DaoSession mDaoSession;

    private ExpensesLocalDataSource(@NonNull AppExecutors appExecutors, @NonNull ExpenseDao expenseDao) {
        mAppExecutors = appExecutors;
        mExpenseDao = expenseDao;
    }

    public static ExpensesLocalDataSource getInstance(@NonNull AppExecutors appExecutors, @NonNull ExpenseDao expenseDao) {
        if (INSTANCE == null) {
            synchronized (ExpensesLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ExpensesLocalDataSource(appExecutors, expenseDao);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void setSessionDao(DaoSession dao) {
        mDaoSession = dao;
    }

    @Override
    public void getExpenses(@NonNull final LoadExpensesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Expense> expenses = mExpenseDao.queryBuilder()
                        .orderDesc(ExpenseDao.Properties.Date, ExpenseDao.Properties.Id)
                        .list();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (expenses.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onExpensesLoaded(expenses);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getExpense(@NonNull final Long id, @NonNull final GetExpenseCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Expense expense = mExpenseDao.load(id);

                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (expense != null) {
                            callback.onExpenseLoaded(expense);
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
    public void getExpenseByCategory(final Long categoryId, final LoadExpensesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Expense> expenses = mExpenseDao.queryBuilder()
                        .where(ExpenseDao.Properties.CategoryId.eq(categoryId))
                        .orderDesc(ExpenseDao.Properties.Date, ExpenseDao.Properties.Id)
                        .list();

                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (expenses.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onExpensesLoaded(expenses);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }
	
	@Override
	public void getExpensesGroupBy(final LoadExpensesCallback callback) {
		Runnable runnable = new Runnable() {
            @Override
            public void run() {
				// Todo: sql query isn't valid! return other model
                Cursor cursor = mDaoSession.getDatabase()
                        .rawQuery("SELECT sum(" + ExpenseDao.Properties.Price.columnName + ") as sum, count(" + ExpenseDao.Properties.Price.columnName + ") as count, CATEGORY.TITLE FROM " + mExpenseDao.getTablename() + " INNER JOIN " + mDaoSession.getCategoryDao().getTablename() + " on " + ExpenseDao.Properties.CategoryId.columnName + " == CATEGORY._id GROUP BY " + ExpenseDao.Properties.CategoryId.columnName + " ORDER BY sum DESC", new String []{});

				final List<Filter> filters = new ArrayList<>();

                while (cursor.moveToNext()) {
                    filters.add(new Filter(cursor.getLong(0), cursor.getLong(1), cursor.getString(2)));
                }

                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (filters.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onFiltersLoaded(filters);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveExpense(@NonNull final Expense expense) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mExpenseDao.insert(expense);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void updateExpense(final Expense expense) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mExpenseDao.update(expense);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void deleteAllExpenses() {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mExpenseDao.deleteAll();
            }
        };

        mAppExecutors.diskIO().execute(deleteRunnable);
    }

    @Override
    public void deleteExpense(@NonNull final Long id) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mExpenseDao.deleteByKey((long) id);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }
}
