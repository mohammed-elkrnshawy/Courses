package s.panorama.graduationproject.AddCourse;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import s.panorama.graduationproject.Classes.CameraFirebase;
import s.panorama.graduationproject.Classes.Constant;
import s.panorama.graduationproject.Models.UserObjectClass;
import s.panorama.graduationproject.R;

public class AddCourseActivity extends AppCompatActivity implements AddCourseInterface {

    /*   @BindView(R.id.userImage)
        CircleImageView userImage;
        @BindView(R.id.userName)
        TextView userName;*/
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.radio)
    RadioButton radio;
    /*  @BindView(R.id.instructorName)
      EditText instructorName;*/
    @BindView(R.id.edtTitle)
    EditText edtTitle;
    @BindView(R.id.edtdesc)
    EditText edtdesc;
    @BindView(R.id.edtPrice)
    EditText edtPrice;
    @BindView(R.id.edtLocation)
    TextView edtLocation;
    @BindView(R.id.edtAdress)
    EditText edtAdress;
    @BindView(R.id.edtstart)
    TextView edtstart;
    @BindView(R.id.edtend)
    TextView edtend;
    @BindView(R.id.edtattendence)
    EditText edtattendence;
    @BindView(R.id.edtcurrent)
    EditText edtcurrent;

    @BindView(R.id.btncancelJoin)
    Button btncancelJoin;


    private CameraFirebase cameraFirebase;
    private AddCourseClass addCourseClass;
    private AddCoursePresenter addCoursePresenter;
    private Uri courseUriFilePath;
    private UserObjectClass userObjectClass;
    private String hours,minuts;
    public String TimeFrom;
    private String address;
    private double lat , lon ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more);
        ButterKnife.bind(this);
        getIntentData();
        initComponents();

    }

    private void getIntentData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userObjectClass = (UserObjectClass) extras.getSerializable("userData");
        }
    }

    private void initComponents() {
        cameraFirebase = new CameraFirebase(this);
        addCourseClass = new AddCourseClass();
        addCoursePresenter = new AddCoursePresenter(this);

        edtstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddCourseActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        TimeFrom = edtstart.getText().toString().trim();
                        NumberFormat nf = NumberFormat.getInstance(new Locale("en","US")); //or "nb","No" - for Norway
                        int sDistance =Integer.parseInt( nf.format(selectedHour));
                        int Distance = Integer.parseInt(nf.format(selectedMinute));

                        if(sDistance< 10)
                        {
                            hours="0"+sDistance;

                        }
                        else {
                            hours=sDistance+"";
                        }
                        if(Distance< 10)
                        {
                            minuts="0"+Distance;

                        }
                        else {
                            minuts=Distance+"";
                        }

                        edtstart.setText(hours+":"+minuts);

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(getString(R.string.select_time));
                mTimePicker.show();

            }
        });

        edtend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar currentTime = Calendar.getInstance();
                int Hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int Minute = currentTime.get(Calendar.MINUTE);
                TimePickerDialog TimePicker;
                TimePicker = new TimePickerDialog(AddCourseActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        NumberFormat nf = NumberFormat.getInstance(new Locale("en","US")); //or "nb","No" - for Norway
                        int sDistance =Integer.parseInt( nf.format(selectedHour));
                        int Distance = Integer.parseInt(nf.format(selectedMinute));
                        if(sDistance< 10)
                        {
                            hours="0"+sDistance;

                        }
                        else {
                            hours=sDistance+"";
                        }
                        if(Distance< 10)
                        {
                            minuts="0"+Distance;

                        }
                        else {
                            minuts=Distance+"";
                        }
                        edtend.setText(hours+":"+minuts);
                    }
                }, Hour, Minute, true);//Yes 24 hour time
                TimePicker.setTitle(getString(R.string.select_time));
                TimePicker.show();

            }
        });


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

        if (TextUtils.isEmpty(edtAdress.getText())) {
            edtAdress.setError(getResources().getString(R.string.requiredField));
            edtAdress.requestFocus();
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


        addCourseClass.setUID(userObjectClass.getUID());
        addCourseClass.setUsername(userObjectClass.getUsername());
        addCourseClass.setCourseTitle(edtTitle.getText().toString().trim());
        addCourseClass.setCourseDesc(edtdesc.getText().toString().trim());
        addCourseClass.setCourseLocation(edtLocation.getText().toString().trim());
        addCourseClass.setCourseAddress(edtAdress.getText().toString().trim());
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

    @OnClick({ R.id.image, R.id.btncancelJoin,R.id.edtLocation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image:
                cameraFirebase.SelectPhotoDialog();
                break;
            case R.id.btncancelJoin:
                addCoursePresenter.validate();
                break;
                case R.id.edtLocation:
                    startActivityForResult(new Intent(AddCourseActivity.this, MapsActivity.class), Constant.Map);
                break;
        }
    }


}
