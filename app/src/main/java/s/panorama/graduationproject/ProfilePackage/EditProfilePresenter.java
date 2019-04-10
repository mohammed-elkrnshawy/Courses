package s.panorama.graduationproject.ProfilePackage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.UUID;

import s.panorama.graduationproject.Activity.LoginActivity;
import s.panorama.graduationproject.Classes.Constant;
import s.panorama.graduationproject.Classes.SharedInterface;
import s.panorama.graduationproject.Classes.SharedUtils;
import s.panorama.graduationproject.Models.UserObjectClass;

public class EditProfilePresenter {

    private Dialog progressDialog;
    private EditProfileActivity view;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public EditProfilePresenter(EditProfileActivity view){
        this.view=view;
        progressDialog= SharedUtils.ShowWaiting(view,progressDialog);
    }

    public void viewData() {
        view.setDataToView();
    }

    public void checkData(){
        view.checkData();
    }

    public void uploadPhoto(final Uri filePath, final UserObjectClass userObject) {
        progressDialog.show();
        if (filePath != null) {
            final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return ref.getDownloadUrl();
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri taskResult = task.getResult();
                                userObject.setPersonalPhoto(taskResult.toString());
                                saveDatabase(userObject);
                            }else {
                                uploadPhoto(filePath,userObject);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(view, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else {
            saveDatabase(userObject);
        }
    }

    private void saveDatabase(UserObjectClass objectClass){
        mDatabase.child(Constant.rootUsers).child(objectClass.getUID()).setValue(objectClass)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            view.finishActivity();
                        } else {

                        }
                    }
                });
    }

}
