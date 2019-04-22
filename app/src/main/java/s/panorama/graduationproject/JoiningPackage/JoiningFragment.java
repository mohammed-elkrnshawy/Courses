package s.panorama.graduationproject.JoiningPackage;


import android.os.Bundle;
import android.support.annotation.NonNull;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import s.panorama.graduationproject.AddCourse.AddCourseClass;
import s.panorama.graduationproject.AddCourse.CoursesAdapter;
import s.panorama.graduationproject.AddCourse.CoursesPresenter;
import s.panorama.graduationproject.FollowingPackage.FollowingAdapter;
import s.panorama.graduationproject.Models.UserObjectClass;
import s.panorama.graduationproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoiningFragment extends Fragment implements JoinCourseInterface{


    @BindView(R.id.courses)
    RecyclerView courses;
    Unbinder unbinder;

    private View view;
    List<AddCourseClass> list;
    private JoinClass MessageClassObject;
    private JoinCoursePresenter joinCoursePresenter;
    private JoiningAdapter joiningAdapter;
    private UserObjectClass userObjectClass;






    public JoiningFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_joining, container, false);
        unbinder = ButterKnife.bind(this, view);
        initComponents();
        return view;
    }

    private void initComponents() {
        list = new ArrayList<>();
        userObjectClass = (UserObjectClass) getArguments().getSerializable("userData");
        joinCoursePresenter = new JoinCoursePresenter(this);
        joinCoursePresenter.Show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void ShowResponse() {
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        list.clear();
//        Query query = reference.child("Courses");
//        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getContext());
//        courses.setLayoutManager(layoutmanager);
//        joiningAdapter = new JoiningAdapter(list,getContext(),userObjectClass);
//        courses.setAdapter(joiningAdapter);
//
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnalpshot) {
//                if (dataSnapshot.exists()) {
//                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
//                        MessageClassObject=issue.getValue(AddCourseClass.class);
//                        list.add(MessageClassObject);
//                    }
//
//                    joiningAdapter.notifyDataSetChanged();
//                }
//
//                else {
//                    Toast.makeText(getContext(), "Not Courses in this Category", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        list.clear();
        Query query = reference.child("Join")/*.equalTo(MessageClassObject.getCourseID())*/;
        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getContext());
        courses.setLayoutManager(layoutmanager);
        joiningAdapter= new JoiningAdapter(list,getContext(),userObjectClass);
        courses.setAdapter(joiningAdapter);
        final List<String> ids = new ArrayList<>();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                            MessageClassObject=issue.getValue(JoinClass.class);

                        if (issue.child("userID").getValue().equals(userObjectClass.getUID()))
                        {
                            reference.child("Courses").orderByChild("courseID").equalTo(MessageClassObject.getCourseID()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        if (ids.contains(MessageClassObject.getCourseID())==false) {
                                            ids.add(MessageClassObject.getCourseID());
                                            list.add(snapshot.getValue(AddCourseClass.class));
                                        }
                                    }
                                    joiningAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }
                else {
                    Toast.makeText(getContext(), "No Courses in this Category", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
