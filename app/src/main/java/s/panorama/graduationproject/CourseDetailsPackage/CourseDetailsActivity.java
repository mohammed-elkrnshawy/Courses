package s.panorama.graduationproject.CourseDetailsPackage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import s.panorama.graduationproject.AddCourse.AddCourseClass;
import s.panorama.graduationproject.JoiningPackage.FollowClass;
import s.panorama.graduationproject.JoiningPackage.JoinClass;
import s.panorama.graduationproject.Models.UserObjectClass;
import s.panorama.graduationproject.R;

public class CourseDetailsActivity extends AppCompatActivity implements CourseDetailsInterface {

    @BindView(R.id.image)
    ImageView image;
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
        courseDetailsPresenter = new CourseDetailsPresenter(this);
        courseDetailsPresenter.viewData();
    }


    @OnClick({R.id.followCourse, R.id.joinCourse})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.followCourse:
                FollowClass followClass = new FollowClass(userObjectClass.getUID(), addCourseClass.getUID());
                courseDetailsPresenter.FollowDatabase(followClass);
                break;
            case R.id.joinCourse:
                if(Integer.parseInt(addCourseClass.getCurrentAttendence())< Integer.parseInt(addCourseClass.getNumOfAttendence())) {
                    JoinClass joinObject = new JoinClass(addCourseClass.getCourseID(), userObjectClass.getUID());
                    addCourseClass.setCurrentAttendence((Integer.parseInt(addCourseClass.getCurrentAttendence())+1)+"");
                    edtcurrent.setText(addCourseClass.getCurrentAttendence());
                    courseDetailsPresenter.saveDatabase(joinObject, addCourseClass);
                }
                else {
                    Toast.makeText(this, "Course is full", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void setDataToView() {
        ImageLoader.getInstance().displayImage(addCourseClass.getCourseImage(), image);
        edtTitle.setText(addCourseClass.getCourseTitle());
        edtdesc.setText(addCourseClass.getCourseDesc());
        edtLocation.setText(addCourseClass.getCourseLocation());
        edtAdress.setText(addCourseClass.getCourseAddress());
        edtPrice.setText(addCourseClass.getCoursePrice());
        edtstart.setText(addCourseClass.getCourseStart());
        edtend.setText(addCourseClass.getCourseEnd());
        edtcurrent.setText(addCourseClass.getCurrentAttendence());
        edtattendence.setText(addCourseClass.getNumOfAttendence());

        if (addCourseClass.getUID().equals(userObjectClass.getUID())) {
            joinCourse.setVisibility(View.GONE);
            followCourse.setVisibility(View.GONE);
        }


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Join");
        rootRef.orderByChild("courseID").equalTo(addCourseClass.getCourseID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                if (tasksSnapshot.exists()) {
                    for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                        if (snapshot.child("courseID").getValue().equals(addCourseClass.getCourseID()) && snapshot.child("userID").getValue().equals(userObjectClass.getUID()))
                            joinCourse.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference rootRef2 = FirebaseDatabase.getInstance().getReference("Follow");
        rootRef2.orderByChild("follwedID").equalTo(userObjectClass.getUID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot: tasksSnapshot.getChildren())
                {
                    if(snapshot.child("followerID").getValue().equals(addCourseClass.getUID())&& snapshot.child("follwedID").getValue().equals(userObjectClass.getUID()))
                        followCourse.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
