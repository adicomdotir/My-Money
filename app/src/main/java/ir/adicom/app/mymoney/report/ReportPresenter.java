package ir.adicom.app.mymoney.report;

/**
 * Created by Y.P on 28/08/2018.
 */

public class ReportPresenter implements ReportContract.Presenter {

    private ReportContract.View mRegisterView;

    public ReportPresenter(ReportContract.View registerView) {
        this.mRegisterView = registerView;
        this.mRegisterView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
