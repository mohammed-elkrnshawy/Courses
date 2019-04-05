package s.panorama.graduationproject.AddCourse;

public class AddCoursePresenter {
    private AddCourseActivity view;


    public AddCoursePresenter(AddCourseActivity view) {
        this.view = view;
    }

    public void validate() {
        view.Validate();
    }

    public void addCourses() {
        view.AddCourses();
    }

}
