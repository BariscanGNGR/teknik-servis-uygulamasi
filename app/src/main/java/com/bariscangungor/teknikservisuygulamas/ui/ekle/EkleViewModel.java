package com.bariscangungor.teknikservisuygulamas.ui.ekle;

import androidx.lifecycle.ViewModel;

import com.bariscangungor.teknikservisuygulamas.Database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class EkleViewModel extends ViewModel {

    private String adsoyad="" , telefon_no="",marka="",model="",ariza="";
    public final HashMap<String,String> hatalar = new HashMap<String,String>();
    private Database db;

    public EkleViewModel()
    {

        hatalar.put("başarılı","Başarılı");
        hatalar.put("telefon","Telefon numarası alan kodu olmadan ve eksiksiz giriniz!");
        hatalar.put("formlar","Formlar boş olamaz!");
        hatalar.put("isimboyut","Ad soyad bu kadar uzun olamaz!");
        hatalar.put("markaboyut","Marka bu kadar uzun olamaz!");
        hatalar.put("modelboyut","Model bu kadar uzun olamaz!");
        hatalar.put("arizaboyut","Arıza açıklaması bu kadar uzun olamaz!");
    }

    public void setData(String adsoyad , String telefon_no,
                        String marka,String model,String ariza)
    {
        this.adsoyad=adsoyad;
        this.telefon_no=telefon_no;
        this.marka=marka;
        this.model=model;
        this.ariza=ariza;
    }

    public String ekle(Database db)
    {
        if(!adsoyad.equals("") && !telefon_no.equals("") && !marka.equals("") &&
                !model.equals("") && !ariza.equals(""))
        {if(telefon_no.length() == 11){
            if(adsoyad.length() < 100){
                    if(marka.length() < 50){
                        if (model.length() < 150) {
                            if (ariza.length() < 500) {
                                //database kodları buraya
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());
                                String tarih = sdf.format(new Date());
                                db.KayitEkle(adsoyad,telefon_no,marka,model,ariza,tarih);

                                return hatalar.get("başarılı");
                            }else { return hatalar.get("arizaboyut"); }
                        } else  { return hatalar.get("modelboyut"); }
                    }else {  return hatalar.get("markaboyut");  }
                } else  { return hatalar.get("isimboyut"); }
            } else { return hatalar.get("telefon"); }
        } else{ return hatalar.get("formlar"); }
    }

    public String getAdsoyad() {
        return adsoyad;
    }

    public String getAriza() {
        return ariza;
    }

    public String getMarka() {
        return marka;
    }

    public String getModel() {
        return model;
    }

    public String getTelefon_no() {
        return telefon_no;
    }
}