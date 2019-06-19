package s.panorama.graduationproject.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import s.panorama.graduationproject.AddCourse.AddCourseActivity;
import s.panorama.graduationproject.AddCourse.AddCourseClass;
import s.panorama.graduationproject.AddCourse.CoursesAdapter;
import s.panorama.graduationproject.AddCourse.CoursesInterface;
import s.panorama.graduationproject.AddCourse.CoursesPresenter;
import s.panorama.graduationproject.Classes.CameraFirebase;
import s.panorama.graduationproject.Classes.CitiesClass;
import s.panorama.graduationproject.Classes.Constant;
import s.panorama.graduationproject.Models.UserObjectClass;
import s.panorama.graduationproject.R;

public class HomeFragment extends Fragment implements CoursesInterface {

    @BindView(R.id.courses)
    RecyclerView courses;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    Unbinder unbinder;

    private View view;
    private List<AddCourseClass> list;
    private AddCourseClass MessageClassObject;
    private CoursesPresenter coursesPresenter;
    private CoursesAdapter coursesAdapter;
    private UserObjectClass userObjectClass;



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        initComponents();
        return view;
    }
    private void initComponents() {
        list = new ArrayList<>();
        userObjectClass = (UserObjectClass) getArguments().getSerializable("userData");
        coursesPresenter = new CoursesPresenter(this);
        coursesPresenter.Show();
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fab)
    public void onClick() {
        Intent intent=new Intent(getContext(), AddCourseActivity.class);
        intent.putExtra("userData", userObjectClass);
        startActivity(intent);
    }


    @Override
    public void ShowResponse() {
       DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        list.clear();
        Query query = reference.child("Courses");
        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getContext());
        courses.setLayoutManager(layoutmanager);
        coursesAdapter = new CoursesAdapter(list,getContext(),userObjectClass);
        courses.setAdapter(coursesAdapter);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        MessageClassObject=issue.getValue(AddCourseClass.class);
                        list.add(MessageClassObject);
                    }

                    coursesAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(view.getContext(), "Not Courses in this Category", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}
