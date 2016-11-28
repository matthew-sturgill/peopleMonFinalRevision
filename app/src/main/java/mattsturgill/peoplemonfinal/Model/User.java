package mattsturgill.peoplemonfinal.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by matthewsturgill on 11/24/16.
 */

public class User {

    @SerializedName("Email")
    private String email;

    @SerializedName("FullName")
    private String fullName;

    @SerializedName("AvatarBase64")
    private String avatarBase64;

    @SerializedName("ApiKey")
    private String apiKey;

    @SerializedName("Password")
    private String password;

    @SerializedName("Longitude")
    private Double longitude;

    @SerializedName("Latitude")
    private Double latitude;

    @SerializedName("UserId")
    private String userId;

    @SerializedName("UserName")
    private String userName;

    @SerializedName("Created")
    private Date created;

    @SerializedName("CaughtUserId")
    private String caughtUserId;

    @SerializedName("RadiusInMeters")
    private Integer radiusInMeters;


    public User() {
    }

    public User(String email, String fullName, String avatarBase64, String apiKey, String password) {
        this.email = email;
        this.fullName = fullName;
        this.avatarBase64 = avatarBase64;
        this.apiKey = apiKey;
        this.password = password;
    }

    public User(String caughtUserId, Integer radiusInMeters) {
        this.caughtUserId = caughtUserId;
        this.radiusInMeters = radiusInMeters;
    }

    public User(String userId, String userName, Date created, String avatarBase64) {
        this.userId = userId;
        this.userName = userName;
        this.created = created;
        this.avatarBase64 = avatarBase64;
    }

    public User(String userId, String userName, Double longitude, Double latitude, String avatarBase64, Date created) {
        this.userId = userId;
        this.userName = userName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.avatarBase64 = avatarBase64;
        this.created = created;
    }

    public User(String fullName, String avatarBase64) {
        this.fullName = fullName;
        this.avatarBase64 = avatarBase64;
    }

    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCaughtUserId() {
        return caughtUserId;
    }

    public void setCaughtUserId(String caughtUserId) {
        this.caughtUserId = caughtUserId;
    }

    public Integer getRadiusInMeters() {
        return radiusInMeters;
    }

    public void setRadiusInMeters(Integer radiusInMeters) {
        this.radiusInMeters = radiusInMeters;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAvatarBase64() {
        return avatarBase64;
    }

    public void setAvatarBase64(String avatarBase64) {
        this.avatarBase64 = avatarBase64;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
