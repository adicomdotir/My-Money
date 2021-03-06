package ir.adicom.app.mymoney.categories;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.adicom.app.mymoney.R;
import ir.adicom.app.mymoney.addeditcategory.AddEditCategoryActivity;
import ir.adicom.app.mymoney.addeditcategory.AddEditCategoryFragment;
import ir.adicom.app.mymoney.data.Category;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment implements CategoriesContract.View {

    private CategoriesContract.Presenter mPresenter;
    private ListView lvCategories;
    private CategoriesAdapter mCategoriesAdapter;

    CategoryItemListener mItemListener = new CategoryItemListener() {
        @Override
        public void onCategoryClick(Category clickedCategory) {
            mPresenter.openCategoryDetail(clickedCategory);
        }
    };

    public CategoriesFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvCategories = (ListView) view.findViewById(R.id.categories_list);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_category);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.addNewCategory();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoriesAdapter = new CategoriesAdapter(new ArrayList<Category>(0), mItemListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void showCategories(List<Category> categories) {
        mCategoriesAdapter.replaceData(categories);
        lvCategories.setAdapter(mCategoriesAdapter);
    }

    @Override
    public void showAddCategory() {
        Intent intent = new Intent(getContext(), AddEditCategoryActivity.class);
        startActivityForResult(intent, AddEditCategoryActivity.REQUEST_ADD_CATEGORY);
    }

    @Override
    public void showCategoryDetailsUi(Long categoryId) {
        Intent intent = new Intent(getContext(), AddEditCategoryActivity.class);
        intent.putExtra(AddEditCategoryFragment.ARGUMENT_EDIT_CATEGORY_ID, categoryId);
        startActivity(intent);
    }

    @Override
    public void setPresenter(CategoriesContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    private static class CategoriesAdapter extends BaseAdapter {

        private List<Category> mCategories;
        private CategoriesFragment.CategoryItemListener mItemListener;

        public CategoriesAdapter(List<Category> categories, CategoriesFragment.CategoryItemListener itemListener) {
            setList(categories);
            mItemListener = itemListener;
        }

        public void replaceData(List<Category> categories) {
            setList(categories);
            notifyDataSetChanged();
        }

        private void setList(List<Category> categories) {
            mCategories = categories;
        }

        @Override
        public int getCount() {
            return mCategories.size();
        }

        @Override
        public Category getItem(int i) {
            return mCategories.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.category_item, viewGroup, false);
            }

            final Category category = getItem(i);
            TextView tvItem = (TextView) rowView.findViewById(R.id.tv_item);
            tvItem.setText(category.getTitle());

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemListener.onCategoryClick(category);
                }
            });

            return rowView;
        }
    }

    public interface CategoryItemListener {
        void onCategoryClick(Category clickedCategory);
    }
}
