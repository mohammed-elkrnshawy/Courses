package s.panorama.graduationproject.FollowingPackage;

import s.panorama.graduationproject.JoiningPackage.JoiningFragment;

public class FollowCoursePresenter {
    private FollowingFragment view;

    public FollowCoursePresenter(FollowingFragment view) {
        this.view = view;
    }

    public void Show() {
        view.ShowResponse();
    }

}
