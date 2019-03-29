package s.panorama.graduationproject.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import s.panorama.graduationproject.R;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.imgPhoto)
    ImageView imgPhoto;
    @BindView(R.id.edtUsername)
    EditText edtUsername;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.edtConfirmPassword)
    EditText edtConfirmPassword;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }
    
    
    @OnClick({R.id.btnRegister}) void onButtonClick(View view){
        switch (view.getId()){
            case R.id.btnRegister:
                validateData();
                break;
        }
    }

    private void validateData() {
    }


}
