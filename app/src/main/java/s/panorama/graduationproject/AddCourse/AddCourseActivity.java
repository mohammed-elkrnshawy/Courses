package s.panorama.graduationproject.AddCourse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import s.panorama.graduationproject.R;

public class AddCourseActivity extends AppCompatActivity implements AddCourseInterface {

    @BindView(R.id.userImage)
    CircleImageView userImage;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.radio)
    RadioButton radio;
    @BindView(R.id.instructorName)
    EditText instructorName;
    @BindView(R.id.edtTitle)
    EditText edtTitle;
    @BindView(R.id.edtdesc)
    EditText edtdesc;
    @BindView(R.id.edtPrice)
    EditText edtPrice;
    @BindView(R.id.edtLocation)
    EditText edtLocation;
    @BindView(R.id.edtAdress)
    EditText edtAdress;
    @BindView(R.id.edtstart)
    EditText edtstart;
    @BindView(R.id.edtend)
    EditText edtend;
    @BindView(R.id.edtattendence)
    EditText edtattendence;
    @BindView(R.id.edtcurrent)
    EditText edtcurrent;
    @BindView(R.id.btncancel)
    Button btncancel;
    @BindView(R.id.btncancelJoin)
    Button btncancelJoin;


    private AddCourseClass addCourseClass;
    private AddCoursePresenter addCoursePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more);
        ButterKnife.bind(this);
        initComponents();

    }

    private void initComponents() {
        addCourseClass = new AddCourseClass();
        addCoursePresenter = new AddCoursePresenter(this);
        addCoursePresenter.validate();
    }


    @Override
    public void Validate() {

        if (TextUtils.isEmpty(instructorName.getText())) {
            instructorName.setError(getResources().getString(R.string.requiredField));
            instructorName.requestFocus();
            return;
        }


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



        addCourseClass.setInstructorName(instructorName.getText().toString().trim());
        addCourseClass.setCourseTitle(edtTitle.getText().toString().trim());
        addCourseClass.setCourseDesc(edtdesc.getText().toString().trim());
        addCourseClass.setCourseLocation(edtLocation.getText().toString().trim());
        addCourseClass.setCourseAddress(edtAdress.getText().toString().trim());
        addCourseClass.setCoursePrice(edtPrice.getText().toString().trim());
        addCourseClass.setCourseStart(edtstart.getText().toString().trim());
        addCourseClass.setCourseEnd(edtend.getText().toString().trim());
        addCourseClass.setCurrentAttendence(edtcurrent.getText().toString().trim());
        addCourseClass.setNumOfAttendence(edtattendence.getText().toString().trim());


    }

    @OnClick(R.id.btncancelJoin)
    public void onClick() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Courses");

        String userId = mDatabase.push().getKey();

        addCourseClass = new AddCourseClass(instructorName.getText().toString(), edtTitle.getText().toString(), edtdesc.getText().toString(), edtPrice.getText().toString(), edtLocation.getText().toString(), edtAdress.getText().toString(), edtstart.getText().toString(), edtend.getText().toString(), edtattendence.getText().toString(), edtcurrent.getText()
                .toString());

        mDatabase.push().setValue(addCourseClass);

    }

}
