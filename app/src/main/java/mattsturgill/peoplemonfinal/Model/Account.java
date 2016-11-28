package mattsturgill.peoplemonfinal.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by matthewsturgill on 11/27/16.
 */

public class Account {

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

    public Account() {
    }

    public Account(String email, String fullName, String avatarBase64, String apiKey, String password) {
        this.email = email;
        this.fullName = fullName;
        this.avatarBase64 = avatarBase64;
        this.apiKey = apiKey;
        this.password = password;
    }

    public Account(String fullName, String avatarBase64) {
        this.fullName = fullName;
        this.avatarBase64 = avatarBase64;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatarBase64() {
        return avatarBase64;
    }

    public void setAvatarBase64(String avatarBase64) {
        this.avatarBase64 = avatarBase64;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
