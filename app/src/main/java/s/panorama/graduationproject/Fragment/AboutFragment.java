package s.panorama.graduationproject.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import s.panorama.graduationproject.Classes.SharedUtils;
import s.panorama.graduationproject.FollowingPackage.FollowingAdapter;
import s.panorama.graduationproject.Models.UserObjectClass;
import s.panorama.graduationproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    @BindView(R.id.about)
    TextView about;


    private View view;

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this,view);
        getData();
        return view;
    }

    private void getData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Settings").child("About");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    about.setText(dataSnapshot.child(SharedUtils.getLocalization(view.getContext())).getValue().toString());
                }
                else {
                    Toast.makeText(getContext(), "Not Data", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
