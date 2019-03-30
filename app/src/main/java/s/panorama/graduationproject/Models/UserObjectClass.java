package s.panorama.graduationproject.Models;

import java.io.Serializable;

public class UserObjectClass implements Serializable {

    private String UID;
    private String Email;
    private String Password;
    private String PersonalPhoto;

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getUID() {
        return UID;
    }

    public String getPersonalPhoto() {
        return PersonalPhoto;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setPersonalPhoto(String personalPhoto) {
        PersonalPhoto = personalPhoto;
    }
}
