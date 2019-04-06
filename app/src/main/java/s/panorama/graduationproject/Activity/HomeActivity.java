package s.panorama.graduationproject.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import butterknife.BindView;
import butterknife.ButterKnife;
import s.panorama.graduationproject.Fragment.AboutFragment;
import s.panorama.graduationproject.Fragment.FollowingFragment;
import s.panorama.graduationproject.Fragment.HomeFragment;
import s.panorama.graduationproject.Fragment.JoiningFragment;
import s.panorama.graduationproject.Fragment.NotificationFragment;
import s.panorama.graduationproject.ProfilePackage.ProfileFragment;
import s.panorama.graduationproject.Models.UserObjectClass;
import s.panorama.graduationproject.R;

public class HomeActivity extends AppCompatActivity {

    private Bundle bundleFragments;
    private View header;
    private LinearLayout PersonalPage,Following,Joining,About,Logout;
    private static TextView userName;
    private static ImageView personPhoto;
    private static UserObjectClass userObject;


    @BindView(R.id.toolbar)
    Toolbar toolbar ;
    @BindView(R.id.navigationView)
    NavigationView navigationView ;
    @BindView(R.id.drawer)
    DrawerLayout drawerLayout ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        InitComponents();
        getIntentData();
        setToolBar();
        setFragment(new HomeFragment(),getString(R.string.personal));
        onClick();

    }

    private void getIntentData() {
        Bundle bundle=getIntent().getExtras();
        if (!bundle.isEmpty()) {
            userObject=(UserObjectClass) bundle.get("userData");
            bundleFragments.putSerializable("userData",userObject);
            setData(userObject);
        }
    }

    public static void setData(UserObjectClass userObject2) {
        userObject=userObject2;
        userName.setText(userObject2.getUsername());
        ImageLoader.getInstance().displayImage(userObject2.getPersonalPhoto(),personPhoto);
    }

    private void InitComponents() {

        //region ImageLoader
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
        //endregion

        bundleFragments=new Bundle();

        header=navigationView.getHeaderView(0);
        PersonalPage=header.findViewById(R.id.personal);
        Joining=header.findViewById(R.id.joining);
        Following=header.findViewById(R.id.following);
        About=header.findViewById(R.id.about);
        Logout=header.findViewById(R.id.logout);
        personPhoto=header.findViewById(R.id.userImage);
        userName=header.findViewById(R.id.userName);

    }

    private void setFragment(Fragment fragment, String Title) {
        fragment.setArguments(bundleFragments);
        getSupportFragmentManager().beginTransaction().replace(R.id.home_container, fragment).addToBackStack(Title)
                .commitAllowingStateLoss();
        drawerLayout.closeDrawers();

    }

    private void onClick() {
        PersonalPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ProfileFragment(),getResources().getString(R.string.personal));
            }
        });


        Following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new FollowingFragment(),getResources().getString(R.string.following));
            }
        });


        Joining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new JoiningFragment(),getResources().getString(R.string.joining));
            }
        });



        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new AboutFragment(),getResources().getString(R.string.about));
            }
        });


        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawers();
            }
        });

    }

    private void setToolBar(){
        final LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.home_bar , null);

        ImageView menu = view.findViewById(R.id.menu);
        ImageView location = view.findViewById(R.id.location);
        ImageView notification = view.findViewById(R.id.notification);

        toolbar.addView(view);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawers();
                }else{
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new NotificationFragment(),getString(R.string.notification));
            }
        });

    }

}
