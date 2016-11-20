package thu.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by PC on 10/18/2016.
 */

public class Users implements Serializable {
    @SerializedName("id")
    public int id;

    @SerializedName("userName")
    private String userName;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("avatar")
    private String avatar;

    public Users(){

    }

    public Users(int id, String userName, String password, String email, String avatar) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
