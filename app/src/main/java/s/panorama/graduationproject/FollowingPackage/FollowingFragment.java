package s.panorama.graduationproject.FollowingPackage;


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
import s.panorama.graduationproject.JoiningPackage.JoinCoursePresenter;
import s.panorama.graduationproject.JoiningPackage.JoiningAdapter;
import s.panorama.graduationproject.Models.UserObjectClass;
import s.panorama.graduationproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingFragment extends Fragment implements FollowCourseInterface {


    @BindView(R.id.following)
    RecyclerView following;
    Unbinder unbinder;


    private View view;
    List<UserObjectClass> list;
    private UserObjectClass MessageClassObject;
    private FollowingAdapter followingAdapter;
    private FollowCoursePresenter followCoursePresenter;





    public FollowingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_following, container, false);
        unbinder = ButterKnife.bind(this, view);
        initComponents();
        return view;
    }

    private void initComponents() {
        list = new ArrayList<>();
        MessageClassObject = (UserObjectClass) getArguments().getSerializable("userData");
        followCoursePresenter = new FollowCoursePresenter(this);
        followCoursePresenter.Show();
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void ShowResponse() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        list.clear();
        Query query = reference.child("Follow").orderByChild("followerID").equalTo(MessageClassObject.getUID());
        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getContext());
        following.setLayoutManager(layoutmanager);
        followingAdapter = new FollowingAdapter(list,getContext());
        following.setAdapter(followingAdapter);
final List<String> ids = new ArrayList<>();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {

                        if (issue.child("followerID").getValue().equals(MessageClassObject.getUID()))
                        {
                            reference.child("Users").orderByChild("uid").equalTo(issue.child("follwedID").getValue().toString()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        UserObjectClass userObjectClass = snapshot.getValue(UserObjectClass.class);
                                        if (ids.contains(userObjectClass.getUID())==false) {
                                            ids.add(userObjectClass.getUID());
                                            list.add(userObjectClass);
                                        }
                                    }
                                    followingAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }
                else {
                    Toast.makeText(getContext(), "No Followers in this Category", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    }

