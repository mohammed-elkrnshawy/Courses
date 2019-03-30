package s.panorama.graduationproject.Remote;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import s.panorama.graduationproject.Classes.Constant;
import s.panorama.graduationproject.Models.UserObjectClass;

public class AuthClass {

    private StorageReference storageReference= FirebaseStorage.getInstance().getReference();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private Context context;
    private UserObjectClass userObject;
    private Uri filePath;

    public AuthClass(Context context){
        this.context=context;
    }

    public void registerUsers(final UserObjectClass userObject,Uri filePath) {
        this.filePath=filePath;
        this.userObject=userObject;

        mAuth.createUserWithEmailAndPassword(userObject.getEmail(),userObject.getPassword())
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("G", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Verification(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("G", "createUserWithEmail:failure", task.getException());
                            /*updateUI(null);*/
                        }

                    }
                });
    }

    private void Verification(final FirebaseUser user) {
        mAuth = FirebaseAuth.getInstance();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "Email sent.");
                            userObject.setUID(user.getUid());
                            mDatabase.child(Constant.rootUsers).child(user.getUid()).setValue(userObject);
                            uploadPhoto();
                        }
                    }
                });
    }

    private void uploadPhoto() {
        if (filePath!=null)
        {
            final StorageReference ref=storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful())
                            {
                                userObject.setPersonalPhoto(task.getResult().getMetadata().getPath());
                                mDatabase.child(Constant.rootUsers).child(userObject.getUID()).setValue(userObject);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double process=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        }
                    });
        }
    }

}
