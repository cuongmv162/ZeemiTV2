package cuongvo.zeemitv.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cuongmv162 on 7/21/2015.
 */

public class UserPOJO {
    @SerializedName("id")
    private String id;

    @SerializedName("username")
    private String userName;

    @SerializedName("profile_picture")
    private String profilePicture;

    @SerializedName("point")
    private int point;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
