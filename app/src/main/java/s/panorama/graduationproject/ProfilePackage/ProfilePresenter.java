package s.panorama.graduationproject.ProfilePackage;

public class ProfilePresenter {

    private ProfileFragment view;

    public ProfilePresenter(ProfileFragment view){
        this.view=view;
    }

    public void viewData() {
        view.setDataToView();
    }
}
