package com.bariscangungor.teknikservisuygulamas.ui.detaylar;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.bariscangungor.teknikservisuygulamas.Database;
import com.bariscangungor.teknikservisuygulamas.ui.home.Kayit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class duzenleViewModel extends ViewModel {
    public final HashMap<String, String> hatalar = new HashMap<String, String>();
    private Database db;
    Kayit kayit;
    Context context;

    public duzenleViewModel() {
        hatalar.put("başarılı", "Başarılı");
        hatalar.put("telefon", "Telefon numarası alan kodu olmadan ve eksiksiz giriniz!");
        hatalar.put("formlar", "Formlar boş olamaz!");
        hatalar.put("isimboyut", "Ad soyad bu kadar uzun olamaz!");
        hatalar.put("markaboyut", "Marka bu kadar uzun olamaz!");
        hatalar.put("modelboyut", "Model bu kadar uzun olamaz!");
        hatalar.put("arizaboyut", "Arıza açıklaması bu kadar uzun olamaz!");
        hatalar.put("teknisyen_boyut","Teknisyen Açıklaması bu kadar uzun olamaz!");
    }

    public boolean yazdir(Kayit kayit, Context context) {
        this.context = context;
        this.kayit = kayit;

        if (sorgula()) {
            Database db = new Database(context);
            db.updateKayitlar(kayit);

            return true;
        } else {
            return false;
        }


    }

    private boolean sorgula() {

        if (!kayit.getAdsoyad().equals("") && !kayit.getTelefon_no().equals("") && !kayit.getMarka().equals("") &&
                !kayit.getModel().equals("") && !kayit.getAriza().equals("")) {
            if (kayit.getTelefon_no().length() == 11 || kayit.getTelefon_no().length() == 10) {
                if (kayit.getAdsoyad().length() < 100) {
                    if (kayit.getMarka().length() < 50) {
                        if (kayit.getModel().length() < 150) {
                            if (kayit.getAriza().length() < 500) {
                                if (kayit.getTeknisyen_aciklama() == null || kayit.getTeknisyen_aciklama().length() < 500) {
                                    return true;//db.KayitEkle(adsoyad,telefon_no,marka,model,ariza,tarih);
                                } else {
                                    Toast.makeText(context, hatalar.get("teknisyen_boyut"), Toast.LENGTH_SHORT).show();return false;
                                }
                            } else {
                                Toast.makeText(context, hatalar.get("arizaboyut"), Toast.LENGTH_SHORT).show();return false;
                            }
                        } else {
                            Toast.makeText(context, hatalar.get("modelboyut"), Toast.LENGTH_SHORT).show();return false;
                        }
                    } else {
                        Toast.makeText(context, hatalar.get("markaboyut"), Toast.LENGTH_SHORT).show();return false;
                    }
                } else {
                    Toast.makeText(context, hatalar.get("isimboyut"), Toast.LENGTH_SHORT).show();return false;
                }
            } else {
                Toast.makeText(context, hatalar.get("telefon"), Toast.LENGTH_SHORT).show();return false;
            }
        } else {
            Toast.makeText(context, hatalar.get("formlar"), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
