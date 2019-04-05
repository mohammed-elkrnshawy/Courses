package s.panorama.graduationproject.ProfilePackage;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import s.panorama.graduationproject.Activity.ForgetActivity;
import s.panorama.graduationproject.Activity.LoginActivity;
import s.panorama.graduationproject.Activity.RegisterActivity;
import s.panorama.graduationproject.Models.UserObjectClass;
import s.panorama.graduationproject.ProfilePackage.ProfileInterface;
import s.panorama.graduationproject.ProfilePackage.ProfilePresenter;
import s.panorama.graduationproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProfileInterface {

    private View view;
    private UserObjectClass userObjectClass;
    private ProfilePresenter profilePresenter;


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
        view =inflater.inflate(R.layout.fragment_personal_page, container, false);
        ButterKnife.bind(this,view);
        getIntentData();


        return view;
    }

    private void getIntentData() {
        profilePresenter=new ProfilePresenter(this);
        userObjectClass = (UserObjectClass) getArguments().getSerializable("userData");
        profilePresenter.viewData();
    }

    @OnClick({R.id.imgEdit}) void onButtonClick (View view) {
        switch (view.getId()) {
            case R.id.imgEdit:
                Intent intent=new Intent(getContext(),EditProfileActivity.class);
                intent.putExtra("userData",userObjectClass);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void setDataToView() {
        ImageLoader.getInstance().displayImage(userObjectClass.getPersonalPhoto(),userImage);
        txtUsername.setText(userObjectClass.getUsername());
        txtPhone.setText(userObjectClass.getPhone());
        txtBio.setText(userObjectClass.getBio());
        txtFollower.setText(userObjectClass.getFollower()+" "+getContext().getString(R.string.followers));
        txtFollowing.setText(userObjectClass.getFollowing()+" "+getContext().getString(R.string.following));
    }
}
