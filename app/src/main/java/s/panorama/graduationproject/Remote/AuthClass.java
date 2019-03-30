package s.panorama.graduationproject.Remote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import s.panorama.graduationproject.Activity.HomeActivity;
import s.panorama.graduationproject.Classes.Constant;
import s.panorama.graduationproject.Models.UserObjectClass;

import static android.support.constraint.Constraints.TAG;

public class AuthClass {

    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    ;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Context context;
    private UserObjectClass userObject;
    private Uri filePath;

    public AuthClass(Context context) {
        this.context = context;
    }

    public void registerUsers(final UserObjectClass userObject, Uri filePath) {
        this.filePath = filePath;
        this.userObject = userObject;

        mAuth.createUserWithEmailAndPassword(userObject.getEmail(), userObject.getPassword())
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
        final String[] Url = new String[1];
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
                                startHomeActivity();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void startHomeActivity() {
        mDatabase.child(Constant.rootUsers).child(userObject.getUID()).setValue(userObject)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(context, HomeActivity.class);
                            intent.putExtra("userData", userObject);
                            context.startActivity(intent);
                        }
                    }
                });
    }

    public void SignIn(final UserObjectClass userObject) {
        this.userObject = userObject;
        mAuth.signInWithEmailAndPassword(userObject.getEmail(), userObject.getPassword())
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user.isEmailVerified()) {
                                userObject.setUID(user.getUid());
                                Search();
                            } else {
                                Toast.makeText(context, "Pls Valid Email", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed." + "\n" + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void Search() {
        Query query = mDatabase.child(Constant.rootUsers).child(userObject.getUID());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(context, "Done Here", Toast.LENGTH_SHORT).show();
                    startHomeActivity();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
