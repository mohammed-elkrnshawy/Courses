package s.panorama.graduationproject.AddCourse;

import android.app.Dialog;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import s.panorama.graduationproject.Classes.SharedUtils;
import s.panorama.graduationproject.ProfilePackage.ProfileFragment;

public class CoursesPresenter {

    private HomeFragment view;

    public CoursesPresenter(HomeFragment view) {
        this.view = view;
    }

    public void Show() {
        view.ShowResponse();
    }

}
