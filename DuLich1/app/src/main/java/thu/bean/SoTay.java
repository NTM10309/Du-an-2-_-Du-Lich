package thu.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by PC on 10/28/2016.
 */

public class SoTay implements Serializable {
    @SerializedName("maso")
    public int maso;

    @SerializedName("diadiem")
    private String diadiem;

    @SerializedName("noidung")
    private String noidung;

    @SerializedName("username")
    private String username;

    @SerializedName("ngaytao")
    private String ngaytao;

    public SoTay(){

    }

    public SoTay(int maso, String diadiem, String noidung, String username, String ngaytao) {
        this.maso = maso;
        this.diadiem = diadiem;
        this.noidung = noidung;
        this.username = username;
        this.ngaytao = ngaytao;
    }

    public int getMaso() {
        return maso;
    }

    public void setMaso(int maso) {
        this.maso = maso;
    }

    public String getDiadiem() {
        return diadiem;
    }

    public void setDiadiem(String diadiem) {
        this.diadiem = diadiem;
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

    public String getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(String ngaytao) {
        this.ngaytao = ngaytao;
    }
}
