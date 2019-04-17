package s.panorama.graduationproject.FollowingPackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import s.panorama.graduationproject.AddCourse.AddCourseClass;
import s.panorama.graduationproject.JoiningPackage.JoiningAdapter;
import s.panorama.graduationproject.Models.UserObjectClass;
import s.panorama.graduationproject.R;


public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.MyHolder> {


    List<UserObjectClass> listdata;
    Context context;

    public FollowingAdapter(List<UserObjectClass> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.following_row,parent,false);

        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final UserObjectClass data = listdata.get(position);
        holder.userName.setText(data.getUsername());
        ImageLoader.getInstance().displayImage(data.getPersonalPhoto(),holder.userImage);
    }

    @Override
    public int getItemCount() {
        return listdata.size();    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView userName;
        ImageView userImage;

        public MyHolder(View itemView) {
            super(itemView);
            userName =  itemView.findViewById(R.id.userName);
            userImage=itemView.findViewById(R.id.userImage);

        }
    }

}
