package s.panorama.graduationproject.AddCourse;

import java.io.Serializable;

public class AddCourseClass implements Serializable {
    private String UID;
    private String PersonalPhoto;
    private String Username;
    private String CourseImage;
    private String CourseTitle;
    private String CourseDesc;
    private String CoursePrice;
    private String CourseLocation;
    private String CourseAddress;
    private String CourseStart;
    private String CourseEnd;
    private String NumOfAttendence;
    private String currentAttendence;

    public String getPersonalPhoto() {
        return PersonalPhoto;
    }

    public void setPersonalPhoto(String personalPhoto) {
        PersonalPhoto = personalPhoto;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public AddCourseClass()
    {

    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getCourseImage() {
        return CourseImage;
    }

    public void setCourseImage(String courseImage) {
        CourseImage = courseImage;
    }

    public String getCourseTitle() {
        return CourseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        CourseTitle = courseTitle;
    }

    public String getCourseDesc() {
        return CourseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        CourseDesc = courseDesc;
    }

    public String getCoursePrice() {
        return CoursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        CoursePrice = coursePrice;
    }

    public String getCourseLocation() {
        return CourseLocation;
    }

    public void setCourseLocation(String courseLocation) {
        CourseLocation = courseLocation;
    }

    public String getCourseAddress() {
        return CourseAddress;
    }

    public void setCourseAddress(String courseAddress) {
        CourseAddress = courseAddress;
    }

    public String getCourseStart() {
        return CourseStart;
    }

    public void setCourseStart(String courseStart) {
        CourseStart = courseStart;
    }

    public String getCourseEnd() {
        return CourseEnd;
    }

    public void setCourseEnd(String courseEnd) {
        CourseEnd = courseEnd;
    }

    public String getNumOfAttendence() {
        return NumOfAttendence;
    }

    public void setNumOfAttendence(String numOfAttendence) {
        NumOfAttendence = numOfAttendence;
    }

    public String getCurrentAttendence() {
        return currentAttendence;
    }

    public void setCurrentAttendence(String currentAttendence) {
        this.currentAttendence = currentAttendence;
    }
}