package s.panorama.graduationproject.AddCourse;

import android.app.Dialog;
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

import java.util.UUID;

import s.panorama.graduationproject.Classes.Constant;
import s.panorama.graduationproject.Classes.SharedUtils;
import s.panorama.graduationproject.Models.UserObjectClass;

public class AddCoursePresenter {
    private Dialog progressDialog;
    private AddCourseActivity view;
    private FirebaseDatabase firebasedatabase;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public AddCoursePresenter(AddCourseActivity view) {
        this.view = view;
        progressDialog = SharedUtils.ShowWaiting(view, progressDialog);
    }

    public void validate() {
        view.Validate();
    }

    public void uploadPhoto(final Uri filePath, final AddCourseClass userObject) {
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
                                userObject.setCourseImage(taskResult.toString());
                                saveDatabase(userObject);
                            } else {
                                uploadPhoto(filePath, userObject);
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
        } else {
            saveDatabase(userObject);
        }
    }

    private void saveDatabase(AddCourseClass objectClass) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Courses");
        String key =myRef.push().getKey();
        objectClass.setCourseID(key);

        myRef.child(key).setValue(objectClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    view.finish();
                }
            }
        });


    }





}
