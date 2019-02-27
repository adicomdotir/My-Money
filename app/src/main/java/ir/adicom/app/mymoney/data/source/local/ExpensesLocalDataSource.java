package ir.adicom.app.mymoney.data.source.local;

import android.support.annotation.NonNull;

import java.util.List;

import ir.adicom.app.mymoney.data.Expense;
import ir.adicom.app.mymoney.data.ExpenseDao;
import ir.adicom.app.mymoney.data.source.ExpensesDataSource;
import ir.adicom.app.mymoney.util.AppExecutors;

public class ExpensesLocalDataSource implements ExpensesDataSource {

    private static volatile ExpensesLocalDataSource INSTANCE;
    private ExpenseDao mExpenseDao;
    private AppExecutors mAppExecutors;

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
    public void getExpenses(@NonNull final LoadExpensesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Expense> expenses = mExpenseDao.queryBuilder().orderDesc(ExpenseDao.Properties.Date).list();
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
