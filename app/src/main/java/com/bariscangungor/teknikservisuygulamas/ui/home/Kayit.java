package com.bariscangungor.teknikservisuygulamas.ui.home;

import android.util.Log;

import com.bariscangungor.teknikservisuygulamas.R;

public class Kayit
{
    private String adsoyad,marka,model,ariza,tarih,telefon_no,bitis_tarih,teknisyen_aciklama;
    private int sonuc;
    private int id;
    private int ucret;

    public Kayit(int id, String adsoyad, String marka, String model, String ariza, int sonuc, String tarih, String telefon_no)
    {
        this.id=id;
        this.adsoyad=adsoyad;
        this.marka=marka;
        this.model=model;
        this.ariza=ariza;
        this.sonuc=sonuc;
        this.tarih=tarih;
        this.telefon_no=telefon_no;
    }

    public void setUcret(int ucret) {
        this.ucret = ucret;
    }

    public int getUcret() {
        Log.d("kayit","ucret : "+ucret);
        return ucret;
    }

    public void setAdsoyad(String adsoyad) {
        this.adsoyad = adsoyad;
    }

    public void setAriza(String ariza) {
        this.ariza = ariza;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public void setTelefon_no(String telefon_no) {
        this.telefon_no = telefon_no;
    }

    public void setTeknisyen_aciklama(String teknisyen_aciklama) {
        this.teknisyen_aciklama = teknisyen_aciklama;
    }

    public String getTeknisyen_aciklama() {
        if(teknisyen_aciklama!=null)
            return teknisyen_aciklama;
        else
            return "";
    }

    public String getBitis_tarih() {
        if (bitis_tarih!= null)
            return bitis_tarih;
        else
            return "";
    }

    public void setBitis_tarih(String bitis_tarih) {
        this.bitis_tarih = bitis_tarih;
    }

    public String getTelefon_no() {
        return telefon_no;
    }

    public int getId()
    {
        return id;
    }
    public int getSonucPhoto()
    {
        Log.d("database", "sonuc =  "+sonuc);
        switch (sonuc)
        {
            case 1://yapıldı
                return R.drawable.basarili;
            case 2://"Tamir Başarısız":
                return R.drawable.basarisiz;
            case 0://"Yapım aşamasında":
                return R.drawable.yapilacak;
            default://hatali
                return 2;
        }
    }

    public void setSonuc(int sonuc) {
        this.sonuc = sonuc;
    }
    public int getSonucInt()
    {
        return sonuc;
    }

    public String getSonuc() {
        String ret= "";
        switch (sonuc)
        {
            case 1://yapıldı
                ret = "Başarılı";
                break;
            case 2://"Tamir Başarısız":
                ret = "Tamir Başarısız";
                break;
            case 0://"Yapım aşamasında":
                ret = "Yapım Aşamasında";
                break;
            default://hatali
                ret = "Hata!!";
                break;
        }
        return ret;
    }

    public String getModel() {
        return model;
    }

    public String getMarka() {
        return marka;
    }

    public String getAriza() {
        return ariza;
    }

    public String getAdsoyad() {
        return adsoyad;
    }

    public String getTarih() {
        return tarih;
    }
}
