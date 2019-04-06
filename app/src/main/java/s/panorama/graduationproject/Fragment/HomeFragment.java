package s.panorama.graduationproject.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import s.panorama.graduationproject.AddCourse.AddCourseActivity;
import s.panorama.graduationproject.R;

public class HomeFragment extends Fragment {

    @BindView(R.id.courses)
    RecyclerView courses;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    Unbinder unbinder;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
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
}
