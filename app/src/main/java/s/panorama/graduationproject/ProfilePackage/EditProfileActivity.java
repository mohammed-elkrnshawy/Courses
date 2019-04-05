package s.panorama.graduationproject.ProfilePackage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import s.panorama.graduationproject.Models.UserObjectClass;
import s.panorama.graduationproject.R;

public class EditProfileActivity extends AppCompatActivity implements ProfileInterface {

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
    @BindView(R.id.edtPhone)
    EditText edtPhone;
    @BindView(R.id.edtBio)
    EditText edtBio;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    private UserObjectClass userObjectClass;
    private EditProfilePresenter profilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);


        getIntentData();
        initComponents();

    }

    private void initComponents() {
        profilePresenter=new EditProfilePresenter(this);
        profilePresenter.viewData();
    }

    private void getIntentData() {
        Bundle bundle=getIntent().getExtras();
        if (!bundle.isEmpty()){
            userObjectClass=(UserObjectClass) bundle.getSerializable("userData");
        }
    }

    @Override
    public void setDataToView() {
        ImageLoader.getInstance().displayImage(userObjectClass.getPersonalPhoto(),imgPhoto);
        edtBio.setText(userObjectClass.getBio());
        edtEmail.setText(userObjectClass.getEmail());
        edtPhone.setText(userObjectClass.getPhone());
        edtUsername.setText(userObjectClass.getUsername());
    }
}
