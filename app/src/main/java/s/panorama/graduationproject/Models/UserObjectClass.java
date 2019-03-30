package s.panorama.graduationproject.Models;

import java.io.Serializable;

public class UserObjectClass implements Serializable {

    private String UID;
    private String Email;
    private String Password;
    private String PersonalPhoto;
    private String Username;
    private String Phone;

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

    public String getUsername() {
        return Username;
    }

    public String getPhone() {
        return Phone;
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

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
