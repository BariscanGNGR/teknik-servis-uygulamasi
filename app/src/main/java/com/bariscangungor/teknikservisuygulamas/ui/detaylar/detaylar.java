package com.bariscangungor.teknikservisuygulamas.ui.detaylar;

import android.content.ClipData;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bariscangungor.teknikservisuygulamas.Database;
import com.bariscangungor.teknikservisuygulamas.R;
import com.bariscangungor.teknikservisuygulamas.ui.home.Kayit;

public class detaylar extends Fragment {
    private Kayit kayit;
    private View root;

    private TextView tv , adsoyad,telefon,aciklama,tarih,marka,model,ucret,teknisyen;
    private ImageView img;
    Database db;

    private void init()
    {
        //kayit = getArguments().getParcelable("kayit");
        db = new Database(root.getContext());

        kayit = db.getKayit(getArguments().getInt("id"));

        tv = root.findViewById(R.id.detaylar_sonuc);tv.setText(kayit.getSonuc());
        img = root.findViewById(R.id.detaylar_img); img.setImageResource(kayit.getSonucPhoto());
        adsoyad = root.findViewById(R.id.detaylar_ad); adsoyad.setText(kayit.getAdsoyad());
        telefon = root.findViewById(R.id.detaylar_telefon); telefon.setText(kayit.getTelefon_no());
        aciklama = root.findViewById(R.id.detaylar_aciklama); aciklama.setText(kayit.getAriza());
        tarih = root.findViewById(R.id.detaylar_tarih); tarih.setText(kayit.getTarih());
        marka = root.findViewById(R.id.detaylar_marka); marka.setText(kayit.getMarka());
        model = root.findViewById(R.id.detaylar_model); model.setText(kayit.getModel());
        ucret = root.findViewById(R.id.detaylar_Ucret); if(kayit.getUcret()!=0)ucret.setText(kayit.getUcret()+" TL"); else ucret.setText("Fiyat Belirtilmedi");
        teknisyen = root.findViewById(R.id.detaylar_teknisyen); teknisyen.setText(kayit.getTeknisyen_aciklama());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_detaylar, container, false);
        setHasOptionsMenu(true);
        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        init();
        telefon.setLinksClickable(true);
        telefon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+kayit.getTelefon_no()));
                startActivity(i);
            }
        });

        if(ab!=null) {
            Log.d("detay", "test ");
            ab.setHomeButtonEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        //ab.hide();
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.detaylar_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
//            case R.id.detaylar_bitir:
//                switch (kayit.getSonucPhoto())
//                {
//                    case R.drawable.yapilacak:
//                        img.setImageResource(R.drawable.basarili);
//                        item.setIcon(R.drawable.basarisiz);
//                        kayit.setSonuc(1);
//                        tv.setText(kayit.getSonuc());
//                        db.updateKayitlar(kayit);
//                        break;
//                    case R.drawable.basarili:
//                        img.setImageResource(R.drawable.basarisiz);
//                        item.setIcon(R.drawable.yapilacak);
//                        kayit.setSonuc(2);
//                        tv.setText(kayit.getSonuc());
//                        db.updateKayitlar(kayit);
//                        break;
//                    case R.drawable.basarisiz:
//                        img.setImageResource(R.drawable.yapilacak);
//                        item.setIcon(R.drawable.basarili);
//                        kayit.setSonuc(0);
//                        tv.setText(kayit.getSonuc());
//                        db.updateKayitlar(kayit);
//                        break;
//                }
//                //sırayla başarılı başarısız yapım aşaması
//                return true;
            case R.id.detaylar_duzenle:
                //duzenle fragmentine geçiş
                Bundle bundle = new Bundle();
                bundle.putInt("id",kayit.getId());
                Navigation.findNavController(root).navigate(R.id.action_detaylar_to_duzenle,bundle);

                return true;
            case R.id.detaylar_sil:
                //silme komutu
                db.deleteKayitlar(kayit);
                getActivity().onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}