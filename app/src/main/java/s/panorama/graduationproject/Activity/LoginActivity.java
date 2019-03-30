package s.panorama.graduationproject.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
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


    private UserObjectClass userObject;
    private AuthClass authClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

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

        userObject.setEmail("mohammedelkrnshawy@gmail.com");
        userObject.setPassword("123456");

        authClass.SignIn(userObject);
    }

}
