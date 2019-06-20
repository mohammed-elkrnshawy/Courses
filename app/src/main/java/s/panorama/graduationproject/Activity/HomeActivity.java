package s.panorama.graduationproject.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import s.panorama.graduationproject.AddCourse.AddCourseClass;
import s.panorama.graduationproject.AddCourse.AddCoursePresenter;
import s.panorama.graduationproject.Classes.CitiesClass;
import s.panorama.graduationproject.Fragment.AboutFragment;
import s.panorama.graduationproject.FollowingPackage.FollowingFragment;
import s.panorama.graduationproject.Fragment.HomeFragment;
import s.panorama.graduationproject.JoiningPackage.JoiningFragment;
import s.panorama.graduationproject.Fragment.NotificationFragment;
import s.panorama.graduationproject.ProfilePackage.ProfileFragment;
import s.panorama.graduationproject.Models.UserObjectClass;
import s.panorama.graduationproject.R;

public class HomeActivity extends AppCompatActivity {

    private Bundle bundleFragments;
    private View header;
    private LinearLayout relativeHome,PersonalPage,Following,Joining,About,Logout;
    private static TextView userName;
    private static ImageView personPhoto;
    private static UserObjectClass userObject;
    private CitiesClass citiesClass;



    @BindView(R.id.toolbar)
    Toolbar toolbar ;
    @BindView(R.id.navigationView)
    NavigationView navigationView ;
    @BindView(R.id.drawer)
    DrawerLayout drawerLayout ;


    Spinner edtAdress;
    private String cityName;
    private int cityID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setToolBar();
        InitComponents();
        getIntentData();
        setFragment(new HomeFragment(),getString(R.string.home));
        onClick();

    }

    private void getIntentData() {
        Bundle bundle=getIntent().getExtras();
        if (!bundle.isEmpty()) {
            userObject=(UserObjectClass) bundle.get("userData");
            setData(userObject);
        }
    }

    public static void setData(UserObjectClass userObject2) {
        userObject=userObject2;
        userName.setText(userObject.getUsername());
        ImageLoader.getInstance().displayImage(userObject.getPersonalPhoto(),personPhoto);
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
        relativeHome=header.findViewById(R.id.relativeHome);
        PersonalPage=header.findViewById(R.id.personal);
        Joining=header.findViewById(R.id.joining);
        Following=header.findViewById(R.id.following);
        About=header.findViewById(R.id.about);
        Logout=header.findViewById(R.id.logout);
        personPhoto=header.findViewById(R.id.userImage);
        userName=header.findViewById(R.id.userName);



        citiesClass=new CitiesClass(this);

        citiesClass.PrepareSpinner(edtAdress);

    }

    private void setFragment(Fragment fragment, String Title) {
        bundleFragments.putSerializable("userData",userObject);
        fragment.setArguments(bundleFragments);
        getSupportFragmentManager().beginTransaction().replace(R.id.home_container, fragment).addToBackStack(Title)
                .commitAllowingStateLoss();
        drawerLayout.closeDrawers();
    }

    private void onClick() {

        relativeHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new HomeFragment(),getString(R.string.home));
            }
        });

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
                new AsyncTask<Void,Void,Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try
                        {
                            FirebaseInstanceId.getInstance().deleteInstanceId();
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        // Call your Activity where you want to land after log out
                        drawerLayout.closeDrawers();
                        ClearSharedPreferencesPut();
                        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                        finishAffinity();
                    }
                }.execute();
            }
        });

    }

    private void setToolBar(){
        final LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.home_bar , null);

        ImageView menu = view.findViewById(R.id.menu);
        ImageView location = view.findViewById(R.id.location);
        edtAdress = view.findViewById(R.id.edtAdress);

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


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtAdress.setVisibility(View.VISIBLE);
            }
        });


        edtAdress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CitiesClass.cityData data = (CitiesClass.cityData) parent.getItemAtPosition(position);
                cityID = data.getID();
                cityName = data.getName();
                bundleFragments.putString("city",cityName);
                HomeFragment fragment = new HomeFragment();
                setFragment(fragment, getString(R.string.home));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }

    private void ClearSharedPreferencesPut() {
        SharedPreferences.Editor editor = getSharedPreferences(getApplication().getPackageName(), MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        finish();
    }

}
