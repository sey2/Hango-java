package hango_java.com.Data;

public class UserInfoData {
    String userID;
    String userName;
    String userProfile;
    String userMbti;

    public UserInfoData(String userID, String userName, String userProfile, String userMbti){
        this.userID = userID;
        this.userName = userName;
        this.userProfile = userProfile;
        this.userMbti = userMbti;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserMbti() {return userMbti;}

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

    public void setUserMbti(String userMbti) { this.userMbti = userMbti; }
}
