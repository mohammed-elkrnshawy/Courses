package s.panorama.graduationproject.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import s.panorama.graduationproject.Activity.HomeActivity;
import s.panorama.graduationproject.AddCourse.AddCourseActivity;
import s.panorama.graduationproject.AddCourse.AddCourseClass;
import s.panorama.graduationproject.AddCourse.CoursesAdapter;
import s.panorama.graduationproject.AddCourse.CoursesInterface;
import s.panorama.graduationproject.AddCourse.CoursesPresenter;
import s.panorama.graduationproject.Classes.CameraFirebase;
import s.panorama.graduationproject.Classes.CitiesClass;
import s.panorama.graduationproject.Classes.Constant;
import s.panorama.graduationproject.Classes.getCurrentLocation;
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
    private Geocoder coder;
    private String City;
    public static Location defaultLocation;
    public static getCurrentLocation currentLocation;

    private EditText edtSearch;
    private Dialog searchDialog;






    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        HomeActivity.linearLayoutSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openSearchDialog();

            }
        });
        if(getArguments() != null)
        City = getArguments().getString("city");
        initComponents();




        return view;
    }
    private void initComponents() {
        list = new ArrayList<>();
        userObjectClass = (UserObjectClass) getArguments().getSerializable("userData");
        coursesPresenter = new CoursesPresenter(this);
        coursesPresenter.Show();

    }

    private void openSearchDialog() {
        searchDialog = new Dialog(getActivity());
        searchDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        searchDialog.setContentView(R.layout.dialog_search);
        Objects.requireNonNull(searchDialog.getWindow()).setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        searchDialog.setCancelable(true);
        edtSearch = searchDialog.findViewById(R.id.searchQuery);

        Button btnConfirm = searchDialog.findViewById(R.id.btnConfirm);
        Button btnClear = searchDialog.findViewById(R.id.btnClear);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coursesAdapter = new CoursesAdapter(list,getContext(),userObjectClass);
                courses.setAdapter(coursesAdapter);
                searchDialog.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (!edtSearch.getText().toString().isEmpty()) {
                        coursesPresenter.Search(edtSearch.getText().toString().trim());
                    }
                }

        });
        searchDialog.show();
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
Address address2;
Address addressCity;
    @Override
    public void ShowResponse() {
        coder = new Geocoder(getContext());
        currentLocation=new getCurrentLocation(getContext());

        defaultLocation=currentLocation.getCurrentLocation();

        if(defaultLocation == null) {
            defaultLocation = new Location("");
            defaultLocation.setLatitude(0);
            defaultLocation.setLongitude(0);
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    ShowResponse();
                }
            }, 3000);
            Toast.makeText(getContext(),"Need to open GPS",Toast.LENGTH_LONG);
            return;
        }
       DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        list.clear();
        Query query = reference.child("Courses");
        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getContext());
        courses.setLayoutManager(layoutmanager);
        coursesAdapter = new CoursesAdapter(list,getContext(),userObjectClass);
        courses.setAdapter(coursesAdapter);
        try {
            List<Address> addressList = coder.getFromLocation(defaultLocation.getLatitude(),defaultLocation.getLongitude(),1);
            if(addressList != null && addressList.size() > 0){
                address2 = addressList.get(0);
            }
            if(City != null){
                List<Address>  addressesCities = coder.getFromLocationName(City,1);
                if(addressesCities != null && addressesCities.size() > 0){
                    addressCity = addressesCities.get(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        MessageClassObject=issue.getValue(AddCourseClass.class);
                        try {
                            Address address;
                            List<Address> addresses = coder.getFromLocationName(MessageClassObject.getCourseLocation(),1);
                            if(addresses != null && addresses.size() > 0)
                            {
                            address = addresses.get(0);
                            if(address.getAdminArea() != null){

                                if(City == null && address.getAdminArea().equalsIgnoreCase(address2.getAdminArea())){
                                    list.add(MessageClassObject);
                                }
                                else if(City != null && (address.getAdminArea().equalsIgnoreCase(addressCity.getAdminArea()) || address.getAdminArea().equalsIgnoreCase(addressCity.getLocality()))){
                                    list.add(MessageClassObject);
                                }
                            }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

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


    @Override
    public void search(String query) {
        List<AddCourseClass> tempList = new ArrayList<>(list);
        for(int i = 0; i <  tempList.size(); i++){
                if (!tempList.get(i).getCourseDesc().contains(query)) {
                    tempList.remove(tempList.get(i));
                    i--;
                }

        }
        searchDialog.dismiss();
        coursesAdapter = new CoursesAdapter(tempList, getContext(), userObjectClass);
        courses.setAdapter(coursesAdapter);
    }
}
