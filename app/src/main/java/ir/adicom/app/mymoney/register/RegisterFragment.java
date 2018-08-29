package ir.adicom.app.mymoney.register;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ir.adicom.app.mymoney.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements RegisterContract.View {

    private RegisterContract.Presenter mPresenter;
    private EditText etEmail, etUsername, etPassword;
    private Button btnRegister;

    public RegisterFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.submitRegister(
                        etEmail.getText().toString(),
                        etUsername.getText().toString(),
                        etPassword.getText().toString()
                );
            }
        });
    }

    private void initView(View view) {
        etEmail = (EditText) view.findViewById(R.id.et_email);
        etUsername = (EditText) view.findViewById(R.id.et_username);
        etPassword = (EditText) view.findViewById(R.id.et_password);
        btnRegister = (Button) view.findViewById(R.id.btn_register);
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void successRegister() {
        getActivity().finish();
    }

    @Override
    public void failedRegister(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
