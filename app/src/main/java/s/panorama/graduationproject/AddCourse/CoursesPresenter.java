package s.panorama.graduationproject.AddCourse;

import s.panorama.graduationproject.Fragment.HomeFragment;

public class CoursesPresenter {

    private HomeFragment view;

    public CoursesPresenter(HomeFragment view) {
        this.view = view;
    }

    public void Show() {
        view.ShowResponse();
    }

}
