package com.bariscangungor.teknikservisuygulamas.ui.sms;


public class SMS
{
    private String telefon_no,tarih,adsoyad;
    private int id;

    public SMS(int id, String tarih, String telefon_no,String adsoyad)
    {
        this.id=id;
        this.tarih=tarih;
        this.telefon_no=telefon_no;
        this.adsoyad=adsoyad;
    }

    public void setAdsoyad(String adsoyad) {
        this.adsoyad = adsoyad;
    }

    public String getAdsoyad() {
        return adsoyad;
    }

    public int getId() {
        return id;
    }

    public String getTarih() {
        return tarih;
    }

    public String getTelefon_no() {
        return telefon_no;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public void setTelefon_no(String telefon_no) {
        this.telefon_no = telefon_no;
    }
}
