package thu.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by PC on 10/29/2016.
 */

public class BinhLuan implements Serializable {
    @SerializedName("macom")
    public int macom;

    @SerializedName("noidung")
    private String noidung;

    @SerializedName("username")
    private String username;

    @SerializedName("mand")
    public int mand;

    @SerializedName("ngaybl")
    private String ngaybl;

    @SerializedName("avat")
    private String avat;
    public BinhLuan(){

    }

    public BinhLuan(int macom, String avat, String ngaybl, int mand, String username, String noidung) {
        this.macom = macom;
        this.avat = avat;
        this.ngaybl = ngaybl;
        this.mand = mand;
        this.username = username;
        this.noidung = noidung;
    }

    public int getMacom() {
        return macom;
    }

    public void setMacom(int macom) {
        this.macom = macom;
    }

    public String getAvat() {
        return avat;
    }

    public void setAvat(String avat) {
        this.avat = avat;
    }

    public String getNgaybl() {
        return ngaybl;
    }

    public void setNgaybl(String ngaybl) {
        this.ngaybl = ngaybl;
    }

    public int getMand() {
        return mand;
    }

    public void setMand(int mand) {
        this.mand = mand;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
}
