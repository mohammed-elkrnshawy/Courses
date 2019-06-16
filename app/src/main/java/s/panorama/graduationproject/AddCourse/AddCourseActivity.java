package s.panorama.graduationproject.AddCourse;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import s.panorama.graduationproject.Activity.MapsActivity;
import s.panorama.graduationproject.Classes.CameraFirebase;
import s.panorama.graduationproject.Classes.CitiesClass;
import s.panorama.graduationproject.Classes.Constant;
import s.panorama.graduationproject.Models.UserObjectClass;
import s.panorama.graduationproject.R;

public class AddCourseActivity extends AppCompatActivity implements AddCourseInterface {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.radioCourse)
    RadioButton radioCourse;
    @BindView(R.id.radioPost)
    RadioButton radioPost;
    @BindView(R.id.edtTitle)
    EditText edtTitle;
    @BindView(R.id.edtdesc)
    EditText edtdesc;
    @BindView(R.id.edtPrice)
    EditText edtPrice;
    @BindView(R.id.edtLocation)
    TextView edtLocation;
    @BindView(R.id.edtAdress)
    Spinner edtAdress;
    @BindView(R.id.edtstart)
    TextView edtstart;
    @BindView(R.id.edtend)
    TextView edtend;
    @BindView(R.id.edtattendence)
    EditText edtattendence;
    @BindView(R.id.edtcurrent)
    EditText edtcurrent;


    private CitiesClass citiesClass;
    private CameraFirebase cameraFirebase;
    private AddCourseClass addCourseClass;
    private AddCoursePresenter addCoursePresenter;
    private Uri courseUriFilePath;
    private UserObjectClass userObjectClass;
    private String address,cityName;
    private int cityID;
    private double lat , lon ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        ButterKnife.bind(this);
        getIntentData();
        initComponents();
        changeSpinner();
    }

    private void changeSpinner() {
        edtAdress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CitiesClass.cityData data=(CitiesClass.cityData) parent.getItemAtPosition(position);
                cityID=data.getID();
                cityName=data.getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getIntentData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userObjectClass = (UserObjectClass) extras.getSerializable("userData");
        }
    }

    private void initComponents() {
        cameraFirebase = new CameraFirebase(this);
        citiesClass=new CitiesClass(this);
        addCourseClass = new AddCourseClass();
        addCoursePresenter = new AddCoursePresenter(this);
        citiesClass.PrepareSpinner(edtAdress);
    }


    @Override
    public void Validate() {

        if (TextUtils.isEmpty(edtTitle.getText())) {
            edtTitle.setError(getResources().getString(R.string.requiredField));
            edtTitle.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(edtdesc.getText())) {
            edtdesc.setError(getResources().getString(R.string.requiredField));
            edtdesc.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(edtPrice.getText())) {
            edtPrice.setError(getResources().getString(R.string.requiredField));
            edtPrice.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(edtLocation.getText())) {
            edtLocation.setError(getResources().getString(R.string.requiredField));
            edtLocation.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(edtstart.getText())) {
            edtstart.setError(getResources().getString(R.string.requiredField));
            edtstart.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(edtend.getText())) {
            edtend.setError(getResources().getString(R.string.requiredField));
            edtend.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(edtattendence.getText())) {
            edtattendence.setError(getResources().getString(R.string.requiredField));
            edtattendence.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(edtcurrent.getText())) {
            edtcurrent.setError(getResources().getString(R.string.requiredField));
            edtcurrent.requestFocus();
            return;
        }

        if (radioCourse.isChecked())
            addCourseClass.setCourseType(radioCourse.getText().toString().trim());
        else
            addCourseClass.setCourseType(radioPost.getText().toString().trim());

        addCourseClass.setUID(userObjectClass.getUID());
        addCourseClass.setUsername(userObjectClass.getUsername());
        addCourseClass.setCourseTitle(edtTitle.getText().toString().trim());
        addCourseClass.setCourseDesc(edtdesc.getText().toString().trim());
        addCourseClass.setCourseLocation(edtLocation.getText().toString().trim());
        addCourseClass.setCourseAddress(cityName);
        addCourseClass.setCourseAddressID(cityID);
        addCourseClass.setCoursePrice(edtPrice.getText().toString().trim());
        addCourseClass.setCourseStart(edtstart.getText().toString().trim());
        addCourseClass.setCourseEnd(edtend.getText().toString().trim());
        addCourseClass.setCurrentAttendence(edtcurrent.getText().toString().trim());
        addCourseClass.setNumOfAttendence(edtattendence.getText().toString().trim());
        addCourseClass.setPersonalPhoto(userObjectClass.getPersonalPhoto());
        addCourseClass.setCourseImage(addCourseClass.getCourseImage());



        addCoursePresenter.uploadPhoto(courseUriFilePath, addCourseClass);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        courseUriFilePath = cameraFirebase.onResult(requestCode, image, data);

        if (requestCode == Constant.Map && resultCode == RESULT_OK) {
            lat = data.getDoubleExtra("lat",0);
            lon = data.getDoubleExtra("lng",0);
            address = data.getStringExtra("address");
            edtLocation.setText(address);
        }
    }

    @OnClick({ R.id.image, R.id.btncancelJoin,R.id.edtLocation,R.id.edtstart,R.id.edtend})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image:
                cameraFirebase.SelectPhotoDialog();
                break;
            case R.id.btncancelJoin:
                addCoursePresenter.validate();
                break;
            case R.id.edtstart:
                addCoursePresenter.selectDate(edtstart);
                break;
            case R.id.edtend:
                addCoursePresenter.selectDate(edtend);
                break;
            case R.id.edtLocation:
                startActivityForResult(new Intent(AddCourseActivity.this, MapsActivity.class), Constant.Map);
                break;
        }
    }


}
