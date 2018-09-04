package ir.adicom.app.mymoney.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import ir.adicom.app.mymoney.data.Category;

public interface CategoriesDataSource {
    interface LoadCategoriesCallback {

        void onCategoriesLoaded(List<Category> categories);

        void onDataNotAvailable();
    }

    interface GetCategoriesCallback {

        void onCategoriesLoaded(Category category);

        void onDataNotAvailable();
    }

    void getCategories(@NonNull LoadCategoriesCallback callback);

    void getCategory(@NonNull Long id, @NonNull GetCategoriesCallback callback);

    void saveCategory(@NonNull Category category);

    void updateCategory(Category category);

    void deleteAllCategories();

    void deleteCategory(@NonNull int id);
}
