package s.panorama.graduationproject.JoiningPackage;

public class FollowClass {
    private String FollwedID;
    private String FollowerID;

    public FollowClass(String follwedID, String followerID) {
        FollwedID = follwedID;
        FollowerID = followerID;
    }

    public FollowClass()
    {

    }

    public String getFollwedID() {
        return FollwedID;
    }

    public void setFollwedID(String follwedID) {
        FollwedID = follwedID;
    }

    public String getFollowerID() {
        return FollowerID;
    }

    public void setFollowerID(String followerID) {
        FollowerID = followerID;
    }
}
