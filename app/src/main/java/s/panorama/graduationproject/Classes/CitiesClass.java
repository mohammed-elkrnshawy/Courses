package s.panorama.graduationproject.Classes;

import android.content.Context;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import s.panorama.graduationproject.Adapter.LocationAdapter;

public class CitiesClass {


    public CitiesClass()
    {

    }


    public class cityData{
        private String name;
        private int ID;

        public cityData(String name,int ID){
            this.ID=ID;
            this.name=name;
        }


        public cityData()
        {

        }

        public int getID() {
            return ID;
        }

        public String getName() {
            return name;
        }
    }

    private Context context;
    private List<cityData> dataList=new ArrayList<>();
    private LocationAdapter locationAdapter;

    public CitiesClass(Context context){
        this.context=context;
    }

    public void PrepareSpinner(Spinner spinner){
        locationAdapter=new LocationAdapter(dataList,context);
        spinner.setAdapter(locationAdapter);
        getData();
    }

    private void getData(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Cities");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        dataList.add(new cityData(snapshot.child(SharedUtils.getLocalization(context)).getValue().toString(),Integer.parseInt(snapshot.getKey())));
                    }
                }
                else {
                    Toast.makeText(context, "Not Data", Toast.LENGTH_LONG).show();
                }
                locationAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

}
