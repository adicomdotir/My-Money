package ir.adicom.app.mymoney.addeditexpense;

/**
 * AddEditExpensePresenter
 * Created by Y.P on 20/11/2018.
 */

public class AddEditExpensePresenter implements AddEditExpenseContract.Presenter {
    private  AddEditExpenseContract.View mView;

    public AddEditExpensePresenter(AddEditExpenseContract.View view) {
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
