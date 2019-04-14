package s.panorama.graduationproject.JoiningPackage;

public class JoinCoursePresenter {
    private JoiningFragment view;

    public JoinCoursePresenter(JoiningFragment view) {
        this.view = view;
    }

    public void Show() {
        view.ShowResponse();
    }



}
