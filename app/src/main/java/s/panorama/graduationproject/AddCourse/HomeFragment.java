package s.panorama.graduationproject.AddCourse;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import s.panorama.graduationproject.AddCourse.AddCourseActivity;
import s.panorama.graduationproject.Classes.CameraFirebase;
import s.panorama.graduationproject.Classes.Constant;
import s.panorama.graduationproject.R;

public class HomeFragment extends Fragment implements CoursesInterface {

    @BindView(R.id.courses)
    RecyclerView courses;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    Unbinder unbinder;

    private View view;
    List<AddCourseClass> list;
    private CoursesPresenter coursesPresenter;

    private Dialog progressDialog;
    FirebaseDatabase database;
    DatabaseReference myRef;



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
        coursesPresenter = new CoursesPresenter(this);
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fab)
    public void onClick() {
        Intent intent=new Intent(getContext(), AddCourseActivity.class);
        startActivity(intent);
    }


    @Override
    public void ShowResponse() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(Constant.rootCourses);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
