package s.panorama.graduationproject.JoiningPackage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import s.panorama.graduationproject.AddCourse.AddCourseClass;
import s.panorama.graduationproject.CourseDetailsPackage.CourseDetailsActivity;
import s.panorama.graduationproject.Models.UserObjectClass;
import s.panorama.graduationproject.R;

public class JoiningAdapter extends RecyclerView.Adapter<JoiningAdapter.MyHolder>{

    List<AddCourseClass> listdata;
    Context context;
    UserObjectClass userObjectClass;

    public JoiningAdapter(List<AddCourseClass> listdata, Context context, UserObjectClass userObjectClass) {
        this.listdata = listdata;
        this.context = context;
        this.userObjectClass = userObjectClass;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.joining_row,parent,false);

     MyHolder myHolder = new MyHolder(view);
        return myHolder;

    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final AddCourseClass data = listdata.get(position);
        holder.CourseName.setText(data.getCourseTitle());
        holder.CourseDesc.setText(data.getCourseDesc());
        ImageLoader.getInstance().displayImage(data.getCourseImage(),holder.CourseImage);
        holder.see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, CourseDetailsActivity.class);
                intent.putExtra("CourseData",data);
                intent.putExtra("userData",userObjectClass);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView CourseName,see,CourseDesc;
        ImageView CourseImage;

        public MyHolder(View itemView) {
            super(itemView);
            CourseName =  itemView.findViewById(R.id.name);
            CourseDesc =  itemView.findViewById(R.id.desc);
            CourseImage=itemView.findViewById(R.id.image);
            see=itemView.findViewById(R.id.seeMore);

        }
    }



}
