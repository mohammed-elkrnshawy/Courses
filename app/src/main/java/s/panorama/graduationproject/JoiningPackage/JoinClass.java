package s.panorama.graduationproject.JoiningPackage;

public class JoinClass {
    private String CourseID;
    private String UserID;

    public JoinClass() {
    }

    public JoinClass(String courseID, String userID) {
        CourseID = courseID;
        UserID = userID;
    }

    public String getCourseID() {
        return CourseID;
    }

    public void setCourseID(String courseID) {
        CourseID = courseID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }


}
