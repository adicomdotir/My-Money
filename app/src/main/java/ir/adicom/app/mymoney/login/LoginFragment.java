package ir.adicom.app.mymoney.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ir.adicom.app.mymoney.BuildConfig;
import ir.adicom.app.mymoney.R;
import ir.adicom.app.mymoney.expenses.ExpensesActivity;
import ir.adicom.app.mymoney.register.RegisterActivity;
import ir.adicom.app.mymoney.util.CalendarTool;
import ir.adicom.app.mymoney.util.datepicker.DatePickerDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements LoginContract.View {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister;
    private LoginContract.Presenter mLoginPresenter;
    private CheckBox chkSaveLogin;
    private TextView tvVersion;

    public LoginFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        mLoginPresenter.isSaveLogin(getContext());

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginPresenter.clickRegisterBtn();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLoginPresenter.saveLogin(getContext(), chkSaveLogin.isChecked())) {
                    mLoginPresenter.saveLoginInfo(getContext(), etUsername.getText().toString(), etPassword.getText().toString());
                }
                mLoginPresenter.clickLoginBtn(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });
    }

    private void initView(View view) {
        etUsername = (EditText) view.findViewById(R.id.et_username);
        etPassword = (EditText) view.findViewById(R.id.et_password);
        btnLogin = (Button) view.findViewById(R.id.btn_login);
        btnRegister = (Button) view.findViewById(R.id.btn_register);
        chkSaveLogin = (CheckBox) view.findViewById(R.id.chk_save_login);
        tvVersion = (TextView) view.findViewById(R.id.tv_version);
        tvVersion.setText(BuildConfig.VERSION_NAME);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.mLoginPresenter = presenter;
    }

    @Override
    public void showRegisterScreen() {
        Intent intent = new Intent(getContext(), RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(getContext(), ExpensesActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void initLoginInfo(String username, String password) {
        etUsername.setText(username);
        etPassword.setText(password);
        chkSaveLogin.setChecked(true);
    }
}
