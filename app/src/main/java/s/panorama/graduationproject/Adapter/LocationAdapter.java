package s.panorama.graduationproject.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import s.panorama.graduationproject.Classes.CitiesClass;
import s.panorama.graduationproject.R;

public class LocationAdapter extends BaseAdapter {

    private List<CitiesClass.cityData> locationsList;
    private Context context;

    public LocationAdapter(List<CitiesClass.cityData> locationsList,Context context) {
        this.locationsList = locationsList;
        this.context=context;
    }

    @Override
    public int getCount() {
        return locationsList.size();
    }

    @Override
    public Object getItem(int position) {
        return locationsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LocationAdapter.ViewHolder holder = null ;

        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View row = inflater.inflate(R.layout.view_spinner_address,parent,false);

        holder = new LocationAdapter.ViewHolder() ;

        holder.txtAddress = row.findViewById(R.id.txtAddress)   ;

        holder.txtAddress.setText(locationsList.get(position).getName());

        return row ;
    }


    class ViewHolder  {
        TextView txtAddress;
    }

}