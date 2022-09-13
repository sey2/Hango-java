package hango_java.com.Data;

public class UserInfo {
    String userID;
    String userName;
    String userProfile;

    public UserInfo(String userID, String userName){
        this.userID = userID;
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }
}
