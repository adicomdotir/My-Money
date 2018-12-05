package ir.adicom.app.mymoney.categories;

import java.util.List;

import ir.adicom.app.mymoney.data.Category;
import ir.adicom.app.mymoney.data.source.CategoriesDataSource;

/**
 *
 * Created by Y.P on 04/09/2018.
 */

public class CategoriesPresenter implements CategoriesContract.Presenter {

    private CategoriesContract.View mCatergiresView;
    private CategoriesDataSource mCategoriesDataSource;

    public CategoriesPresenter(CategoriesContract.View view, CategoriesDataSource cds) {
        this.mCatergiresView = view;
        this.mCategoriesDataSource = cds;
        mCatergiresView.setPresenter(this);
    }

    @Override
    public void start() {
        loadCategories();
    }

    public void loadCategories() {
        mCategoriesDataSource.getCategories(new CategoriesDataSource.LoadCategoriesCallback() {
            @Override
            public void onCategoriesLoaded(List<Category> categories) {
                mCatergiresView.showCategories(categories);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void addNewCategory() {
        mCatergiresView.showAddCategory();
    }
}
