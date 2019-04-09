package s.panorama.graduationproject.ProfilePackage;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import s.panorama.graduationproject.Activity.HomeActivity;
import s.panorama.graduationproject.AddCourse.AddCourseClass;
import s.panorama.graduationproject.AddCourse.CoursesAdapter;
import s.panorama.graduationproject.Models.UserObjectClass;
import s.panorama.graduationproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProfileInterface {

    @BindView(R.id.posts)
    RecyclerView posts;
    private View view;
    private UserObjectClass userObjectClass;
    private ProfilePresenter profilePresenter;
    private CoursesAdapter coursesAdapter;
    List<AddCourseClass> list;
    private AddCourseClass MessageClassObject;

    private final int editInteger = 0;

    @BindView(R.id.imgEdit)
    ImageView imgEdit;
    @BindView(R.id.userImage)
    CircleImageView userImage;
    @BindView(R.id.txtUsername)
    TextView txtUsername;
    @BindView(R.id.txtBio)
    TextView txtBio;
    @BindView(R.id.txtPhone)
    TextView txtPhone;
    @BindView(R.id.txtFollowing)
    TextView txtFollowing;
    @BindView(R.id.txtFollower)
    TextView txtFollower;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_personal_page, container, false);
        ButterKnife.bind(this, view);
        getIntentData();
        showResponse();
        return view;
    }

    private void showResponse() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        list.clear();
        Query query = reference.child("Courses");
        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getContext());
        posts.setLayoutManager(layoutmanager);
        coursesAdapter = new CoursesAdapter(list,getContext());
        posts.setAdapter(coursesAdapter);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {

                        if (issue.child("uid").getValue().equals(userObjectClass.getUID())) {
                            MessageClassObject = issue.getValue(AddCourseClass.class);
                            list.add(MessageClassObject);

                        }


                    }
                    coursesAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(), "Not Courses in this Category", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getIntentData() {
        list = new ArrayList<>();
        profilePresenter = new ProfilePresenter(this);
        userObjectClass = (UserObjectClass) getArguments().getSerializable("userData");
        profilePresenter.viewData();
    }

    @OnClick({R.id.imgEdit})
    void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.imgEdit:
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                intent.putExtra("userData", userObjectClass);
                startActivityForResult(intent, editInteger);
                break;
        }
    }


    @Override
    public void setDataToView() {
        ImageLoader.getInstance().displayImage(userObjectClass.getPersonalPhoto(), userImage);
        txtUsername.setText(userObjectClass.getUsername());
        txtPhone.setText(userObjectClass.getPhone());
        txtBio.setText(userObjectClass.getBio());
        txtFollower.setText(userObjectClass.getFollower() + " " + getContext().getString(R.string.followers));
        txtFollowing.setText(userObjectClass.getFollowing() + " " + getContext().getString(R.string.following));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == editInteger) {
            userObjectClass = (UserObjectClass) data.getSerializableExtra("userData");
            profilePresenter.viewData();
            HomeActivity.setData(userObjectClass);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
