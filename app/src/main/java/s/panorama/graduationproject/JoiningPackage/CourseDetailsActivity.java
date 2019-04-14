package s.panorama.graduationproject.JoiningPackage;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.flags.impl.DataUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.internal.Utils;
import s.panorama.graduationproject.AddCourse.AddCourseClass;
import s.panorama.graduationproject.Classes.CameraFirebase;
import s.panorama.graduationproject.Models.UserObjectClass;
import s.panorama.graduationproject.ProfilePackage.EditProfilePresenter;
import s.panorama.graduationproject.R;

public class CourseDetailsActivity extends AppCompatActivity implements CourseDetailsInterface {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.radio)
    RadioButton radio;
    @BindView(R.id.edtTitle)
    TextView edtTitle;
    @BindView(R.id.edtdesc)
    TextView edtdesc;
    @BindView(R.id.edtPrice)
    TextView edtPrice;
    @BindView(R.id.edtLocation)
    TextView edtLocation;
    @BindView(R.id.edtAdress)
    TextView edtAdress;
    @BindView(R.id.edtstart)
    TextView edtstart;
    @BindView(R.id.edtend)
    TextView edtend;
    @BindView(R.id.edtattendence)
    TextView edtattendence;
    @BindView(R.id.edtcurrent)
    TextView edtcurrent;
    @BindView(R.id.followCourse)
    Button followCourse;
    @BindView(R.id.joinCourse)
    Button joinCourse;

    AddCourseClass addCourseClass;
    CourseDetailsPresenter courseDetailsPresenter;
    UserObjectClass userObjectClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        ButterKnife.bind(this);
        getIntentData();
        initComponents();
    }

    private void getIntentData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            addCourseClass = (AddCourseClass) extras.getSerializable("CourseData");
            userObjectClass = (UserObjectClass) extras.getSerializable("userData");
        }
    }

    private void initComponents() {
        courseDetailsPresenter=new CourseDetailsPresenter(this);
        courseDetailsPresenter.viewData();
    }



    @OnClick({R.id.followCourse, R.id.joinCourse})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.followCourse:
                break;
            case R.id.joinCourse:
                if(Integer.parseInt(addCourseClass.getCurrentAttendence())< Integer.parseInt(addCourseClass.getNumOfAttendence())) {
                    JoinClass j = new JoinClass(addCourseClass.getCourseID(), userObjectClass.getUID());
                    addCourseClass.setCurrentAttendence((Integer.parseInt(addCourseClass.getCurrentAttendence())+1)+"");
                    courseDetailsPresenter.saveDatabase(j, addCourseClass);
                }
                else {
                    Toast.makeText(this, "Course is full", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void setDataToView() {
        ImageLoader.getInstance().displayImage(addCourseClass.getCourseImage(),image);
        edtTitle.setText(addCourseClass.getCourseTitle());
        edtdesc.setText(addCourseClass.getCourseDesc());
        edtLocation.setText(addCourseClass.getCourseLocation());
        edtAdress.setText(addCourseClass.getCourseAddress());
        edtPrice.setText(addCourseClass.getCoursePrice());
        edtstart.setText(addCourseClass.getCourseStart());
        edtend.setText(addCourseClass.getCourseEnd());
        edtcurrent.setText(addCourseClass.getCurrentAttendence());
        edtattendence.setText(addCourseClass.getNumOfAttendence());
        radio.setChecked(true);
        if(addCourseClass.getUID().equals(userObjectClass.getUID()) == true){
        joinCourse.setVisibility(View.GONE);
        }

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Join");
        rootRef.orderByChild("CourseID").equalTo(addCourseClass.getCourseID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                    if(snapshot.getRef().child("CourseID").equals(addCourseClass.getCourseID())&& snapshot.getRef().child("UserID").equals(userObjectClass.getUID()))
                      joinCourse.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
