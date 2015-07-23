package cuongvo.zeemitv.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cuongmv162 on 7/21/2015.
 */
public abstract class BasePOJO {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("rating")
    private int rating;

    @SerializedName("cover_image")
    private String coverImage;

    @SerializedName("featured")
    private String featured;

    @SerializedName("picked")
    private boolean picked;

    @SerializedName("is_hot_picked")
    private boolean isHotPicked;

    @SerializedName("followers_count")
    private int followersCount;

    @SerializedName("viewers_count")
    private int viewersCount;

    @SerializedName("is_live")
    private boolean isLive;

    @SerializedName("next_performance")
    private String nextPerfomance;

    @SerializedName("user")
    private UserPOJO userPOJO;

    public boolean isHotPicked() {
        return isHotPicked;
    }

    public void setIsHotPicked(boolean isHotPicked) {
        this.isHotPicked = isHotPicked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public boolean isPicked() {
        return picked;
    }

    public void setPicked(boolean picked) {
        this.picked = picked;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getViewersCount() {
        return viewersCount;
    }

    public void setViewersCount(int viewersCount) {
        this.viewersCount = viewersCount;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setIsLive(boolean isLive) {
        this.isLive = isLive;
    }

    public String getNextPerfomance() {
        return nextPerfomance;
    }

    public void setNextPerfomance(String nextPerfomance) {
        this.nextPerfomance = nextPerfomance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserPOJO getUserPOJO() {
        return userPOJO;
    }

    public void setUserPOJO(UserPOJO userPOJO) {
        this.userPOJO = userPOJO;
    }
}
