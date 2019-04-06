package s.panorama.graduationproject.ProfilePackage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import s.panorama.graduationproject.Classes.CameraFirebase;
import s.panorama.graduationproject.Models.UserObjectClass;
import s.panorama.graduationproject.R;

public class EditProfileActivity extends AppCompatActivity implements EditProfileInterface {

    @BindView(R.id.imgPhoto)
    ImageView imgPhoto;
    @BindView(R.id.edtUsername)
    EditText edtUsername;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPhone)
    EditText edtPhone;
    @BindView(R.id.edtBio)
    EditText edtBio;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    private CameraFirebase cameraFirebase;
    private UserObjectClass userObject;
    private EditProfilePresenter profilePresenter;
    private Uri uriFilePath;
    private boolean photoChanged=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);


        getIntentData();
        initComponents();

    }

    private void initComponents() {
        cameraFirebase=new CameraFirebase(this);
        profilePresenter=new EditProfilePresenter(this);
        profilePresenter.viewData();
    }

    private void getIntentData() {
        Bundle bundle=getIntent().getExtras();
        if (!bundle.isEmpty()){
            userObject=(UserObjectClass) bundle.getSerializable("userData");
        }
    }

    @OnClick({R.id.btnRegister,R.id.imgPhoto}) void onButtonClick (View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                profilePresenter.checkData();
                break;
            case R.id.imgPhoto:
                cameraFirebase.SelectPhotoDialog();
                break;
        }
    }

    @Override
    public void setDataToView() {
        ImageLoader.getInstance().displayImage(userObject.getPersonalPhoto(),imgPhoto);
        edtBio.setText(userObject.getBio());
        edtEmail.setText(userObject.getEmail());
        edtPhone.setText(userObject.getPhone());
        edtUsername.setText(userObject.getUsername());
    }

    @Override
    public void checkData() {

        if(TextUtils.isEmpty(edtUsername.getText())){
            edtUsername.setError(getResources().getString(R.string.requiredField));
            edtUsername.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText()).matches()){
            edtEmail.setError(getResources().getString(R.string.PleaseEnterYourEmail));
            edtEmail.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(edtPhone.getText())){
            edtPhone.setError(getResources().getString(R.string.requiredField));
            edtPhone.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(edtBio.getText())){
            edtBio.setError(getResources().getString(R.string.pleaseEnterBio));
            edtBio.requestFocus();
            return;
        }

        userObject.setFollowing(userObject.getFollower());
        userObject.setFollower(userObject.getFollower());
        userObject.setUID(userObject.getUID());
        userObject.setPassword(userObject.getPassword());
        userObject.setPersonalPhoto(userObject.getPersonalPhoto());
        userObject.setEmail(edtEmail.getText().toString().trim());
        userObject.setUsername(edtUsername.getText().toString().trim());
        userObject.setPhone(edtPhone.getText().toString().trim());
        userObject.setBio(edtBio.getText().toString().trim());

        profilePresenter.uploadPhoto(uriFilePath,userObject);

    }

    @Override
    public void finishActivity() {
        Intent intent=getIntent();
        intent.putExtra("userData",userObject);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uriFilePath=cameraFirebase.onResult(requestCode,imgPhoto,data);
    }
}
