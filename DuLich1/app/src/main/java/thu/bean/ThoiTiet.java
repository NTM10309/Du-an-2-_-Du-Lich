package thu.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by PC on 11/10/2016.
 */

public class ThoiTiet implements Serializable{
    @SerializedName("weatherId")
    private int weatherId;
    @SerializedName("ngay")
    private String ngay;
    @SerializedName("nhietdo")
    private String nhietdo;
    @SerializedName("anh")
    private String anh;
    @SerializedName("mota")
    private String mota;

    public ThoiTiet(){

    }

    public ThoiTiet(int weatherId, String nhietdo, String ngay, String anh, String mota) {
        this.weatherId = weatherId;
        this.nhietdo = nhietdo;
        this.ngay = ngay;
        this.anh = anh;
        this.mota = mota;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getNhietdo() {
        return nhietdo;
    }

    public void setNhietdo(String nhietdo) {
        this.nhietdo = nhietdo;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

}
