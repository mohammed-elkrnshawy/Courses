package s.panorama.graduationproject.ProfilePackage;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import s.panorama.graduationproject.Classes.SharedInterface;

public class EditProfilePresenter {

    private EditProfileActivity view;

    public EditProfilePresenter(EditProfileActivity view){
        this.view=view;
    }

    public void viewData() {
        view.setDataToView();
    }

}
