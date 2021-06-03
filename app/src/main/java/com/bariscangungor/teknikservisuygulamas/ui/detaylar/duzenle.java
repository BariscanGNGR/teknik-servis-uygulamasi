package com.bariscangungor.teknikservisuygulamas.ui.detaylar;

import android.app.PendingIntent;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bariscangungor.teknikservisuygulamas.Database;
import com.bariscangungor.teknikservisuygulamas.R;
import com.bariscangungor.teknikservisuygulamas.ui.home.Kayit;

import java.util.ArrayList;
import java.util.List;

public class duzenle extends Fragment {


    private View root;
    private Database db;
    private Kayit kayit;
    private String TAG = "duzenle";
    duzenleViewModel dv;

    private EditText adsoyad,telefon,ariza ,marka,model,ucret,teknisyen_aciklama;
    private TextView tarih,bitis_tarih;
    //TextView sonuc;
    private Spinner sonuc;
    private ImageView imgSonuc;
   // private Button btn;

    private void init()
    {
        db = new Database(root.getContext());

//        kayit = new Kayit(getArguments().getInt("id"),getArguments().getString("adsoyad"),getArguments().getString("marka"),getArguments().getString("model"),getArguments().getString("ariza"),getArguments().getInt("sonuc"),getArguments().getString("tarih"),getArguments().getString("telefon_no"));
//        kayit.setBitis_tarih(getArguments().getString("bitis_tarih"));
//        kayit.setTeknisyen_aciklama(getArguments().getString("teknisyen_aciklama"));

        kayit = db.getKayit( getArguments().getInt("id"));


        //btn = root.findViewById(R.id.duzenle_btn);
        //sonuc = root.findViewById(R.id.duzen);tv.setText(kayit.getSonuc());
        sonuc = root.findViewById(R.id.duzenle_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(root.getContext(),R.array.spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sonuc.setAdapter(adapter);
        imgSonuc = root.findViewById(R.id.duzenle_img); imgSonuc.setImageResource(kayit.getSonucPhoto());
        adsoyad = root.findViewById(R.id.duzenle_adsoyad); adsoyad.setText(kayit.getAdsoyad());
        telefon = root.findViewById(R.id.duzenle_telefon); telefon.setText(kayit.getTelefon_no());
        ariza = root.findViewById(R.id.duzenle_ariza); ariza.setText(kayit.getAriza());
        tarih = root.findViewById(R.id.duzenle_tarih); tarih.setText(kayit.getTarih());
        bitis_tarih = root.findViewById(R.id.duzenle_bitis_tarih); bitis_tarih.setText(kayit.getBitis_tarih());
        marka = root.findViewById(R.id.duzenle_marka); marka.setText(kayit.getMarka());
        model = root.findViewById(R.id.duzenle_model); model.setText(kayit.getModel());
        ucret = root.findViewById(R.id.duzenle_ucret); if(kayit.getUcret()!=0)ucret.setText(String.valueOf(kayit.getUcret())); else ucret.setHint("Fiyat Belirtilmedi");
        teknisyen_aciklama = root.findViewById(R.id.duzenle_teknisyen_ariza); teknisyen_aciklama.setText(kayit.getTeknisyen_aciklama());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_duzenle, container, false);
        init();
        dv = new duzenleViewModel();
        setHasOptionsMenu(true);
        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if(ab!=null) {
            Log.d("detay", "test ");
            ab.setHomeButtonEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);;
        }

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.duzenle_menu,menu);
    }
    private boolean mesajGonder = false;
    private String mesajIcerik= "";
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(R.id.duzenle_kaydet == item.getItemId()) //eğer kaydet tuşuna basıldıysa
        {
            if(!ucret.getText().toString().equals("") && !teknisyen_aciklama.getText().toString().equals("") && !ariza.getText().toString().equals("") &&
                    !adsoyad.getText().toString().equals("") && !marka.getText().toString().equals("") && !model.getText().toString().equals("") &&
                    !telefon.getText().toString().equals("") )
            {
                Object selectedItem = sonuc.getSelectedItem();
//            if ("Yapılacak".equalsIgnoreCase(selectedItem.toString())) {
//                kayit.setSonuc(0);
//            } else if ("Başarılı".equalsIgnoreCase(selectedItem.toString())) {
//                kayit.setSonuc(1);
//            }
//            else{kayit.setSonuc(2);}
                mesajGonder=false;
                switch (selectedItem.toString()) {
                    case "Yapılacak":
                        kayit.setSonuc(0);
                        break;
                    case "Başarısız":
                        kayit.setSonuc(2);
                        mesajGonder=true;
                        mesajIcerik += "Sayın "+adsoyad.getText().toString()+"\n"+marka.getText().toString()+" "
                                +model.getText().toString()+" Cıhazınızın tamiratı Başarısızdır.\n"+teknisyen_aciklama.getText().toString()+" açıklaması yapılmıştır";
                        if(ucret.getText().toString().equals("0"))
                            mesajIcerik+= "\nÜcreti "+ucret.getText().toString()+" TL tutmuştur";
                        break;
                    case "Başarılı":
                        kayit.setSonuc(1);
                        mesajGonder=true;
                        mesajIcerik += "Sayın "+adsoyad.getText().toString()+"\n"+marka.getText().toString()+" "
                                +model.getText().toString()+" Cıhazınızın tamiratı Başarılıdır.\n"+teknisyen_aciklama.getText().toString()+" açıklaması yapılmıştır"+
                        "\nÜcreti "+ucret.getText().toString()+" TL tutmuştur";
                        break;
                    default:
                        kayit.setSonuc(4);
                        break;
                }


                kayit.setAdsoyad(adsoyad.getText().toString().trim());
                if(telefon.getText().length() == 11)
                    kayit.setTelefon_no(telefon.getText().toString().trim());
                kayit.setMarka(marka.getText().toString().trim());
                kayit.setModel(model.getText().toString().trim());
                kayit.setAriza(ariza.getText().toString().trim());
                kayit.setTeknisyen_aciklama(teknisyen_aciklama.getText().toString().trim());

                SmsManager sms = SmsManager.getDefault();
                Log.d(TAG, "bool="+mesajGonder+"\nmsg="+mesajIcerik+"\nnumara="+telefon.getText().toString());
                if(mesajGonder) {
                    try {
                            ArrayList<String> parca = sms.divideMessage(mesajIcerik);
                            sms.sendMultipartTextMessage(kayit.getTelefon_no().toString(),null,parca,null,null);
                            //sms.sendTextMessage(String.valueOf(kayit.getTelefon_no()), null, String.valueOf(mesajIcerik), null, null);
                            Toast.makeText(getContext(), "Bilgilendirme mesajı gönderilmiştir..", Toast.LENGTH_SHORT).show();
                    }catch (Exception e)
                    {
                        Log.e(TAG,"sms hata : "+e.getStackTrace().toString());
                    }
                }

                Log.d("duzenle", "ucret : '" + ucret.getText() + "'");

                try {
                    kayit.setUcret(Integer.parseInt(ucret.getText().toString().trim()));
                } catch (NumberFormatException nfe) {
                    kayit.setUcret(0);
                }

                Log.d("duzenle", "kayit.ucret : '" + kayit.getUcret() + "'");


                if (dv.yazdir(kayit, root.getContext())) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", kayit.getId());
                    getActivity().onBackPressed();
                }
            }
            else
            {
                Toast.makeText(getContext(), "Boş kutucuk olamaz", Toast.LENGTH_SHORT).show();
            }

        }

        return super.onOptionsItemSelected(item);
    }
}