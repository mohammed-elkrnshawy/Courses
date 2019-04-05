package s.panorama.graduationproject.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import s.panorama.graduationproject.Models.UserObjectClass;
import s.panorama.graduationproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalPageFragment extends Fragment {

    private View view;
    private UserObjectClass userObjectClass;

    public PersonalPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_personal_page, container, false);

        getIntentData();

        return view;
    }

    private void getIntentData() {
        userObjectClass = (UserObjectClass) getArguments().getSerializable("userData");
    }

}
