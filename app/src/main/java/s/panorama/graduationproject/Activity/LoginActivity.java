package s.panorama.graduationproject.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import s.panorama.graduationproject.Models.UserObjectClass;
import s.panorama.graduationproject.R;
import s.panorama.graduationproject.Remote.AuthClass;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.txtRegister)
    TextView txtRegister;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.txtForget)
    TextView txtForget;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.edtEmail)
    EditText edtEmail;


    private UserObjectClass userObject;
    private AuthClass authClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initComponents();

    }

    private void initComponents() {
        userObject=new UserObjectClass();
        authClass=new AuthClass(this);
    }

    @OnClick({R.id.txtRegister,R.id.txtForget,R.id.btnLogin}) void onButtonClick (View view) {
        switch (view.getId()) {
            case R.id.txtRegister:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            case R.id.txtForget:
                startActivity(new Intent(LoginActivity.this,ForgetActivity.class));
                break;
            case R.id.btnLogin:
                validateData();
                break;
        }
    }

    private void validateData() {

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText()).matches()){
            edtEmail.setError(getResources().getString(R.string.PleaseEnterYourEmail));
            edtEmail.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(edtPassword.getText())){
            edtPassword.setError(getResources().getString(R.string.pleaseEnterPassword));
            edtPassword.requestFocus();
            return;
        }

        userObject.setEmail(edtEmail.getText().toString().trim());
        userObject.setPassword(edtPassword.getText().toString().trim());
        authClass.Login(userObject);
    }

}
