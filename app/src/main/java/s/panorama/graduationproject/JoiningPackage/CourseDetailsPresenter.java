package s.panorama.graduationproject.JoiningPackage;

import android.app.Dialog;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import s.panorama.graduationproject.AddCourse.AddCourseClass;
import s.panorama.graduationproject.Classes.SharedUtils;


public class CourseDetailsPresenter {
    private Dialog progressDialog;
    private CourseDetailsActivity view;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public CourseDetailsPresenter(CourseDetailsActivity view) {
        this.view = view;
        progressDialog= SharedUtils.ShowWaiting(view,progressDialog);
    }

    public void viewData() {
        view.setDataToView();
    }


    public void saveDatabase(JoinClass objectClass, final AddCourseClass addCourseClass) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Join");
        String key =myRef.push().getKey();

        myRef.setValue(objectClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    database.getReference("Courses").orderByChild("courseID").equalTo(addCourseClass.getCourseID()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot tasksSnapshot) {
                            for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                                snapshot.getRef().child("currentAttendence").setValue(addCourseClass.getCurrentAttendence());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getMessage());

                        }
                    });
                }
            }
        });

// e3ml update hna

    }




}
