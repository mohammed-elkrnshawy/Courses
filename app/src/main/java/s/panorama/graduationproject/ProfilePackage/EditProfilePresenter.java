package s.panorama.graduationproject.ProfilePackage;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import s.panorama.graduationproject.Classes.Constant;
import s.panorama.graduationproject.Classes.SharedInterface;
import s.panorama.graduationproject.Models.UserObjectClass;

public class EditProfilePresenter {

    private EditProfileActivity view;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public EditProfilePresenter(EditProfileActivity view){
        this.view=view;
    }

    public void viewData() {
        view.setDataToView();
    }

    public void checkData(){
        view.checkData();
    }

    public void saveData(UserObjectClass objectClass){
        mDatabase.child(Constant.rootUsers).child(objectClass.getUID()).setValue(objectClass)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            view.finishActivity();
                        }else {

                        }
                    }
                });
    }
}
