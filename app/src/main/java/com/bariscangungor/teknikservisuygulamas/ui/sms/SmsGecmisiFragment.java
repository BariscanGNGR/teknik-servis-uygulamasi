package com.bariscangungor.teknikservisuygulamas.ui.sms;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bariscangungor.teknikservisuygulamas.Database;
import com.bariscangungor.teknikservisuygulamas.R;
import com.bariscangungor.teknikservisuygulamas.ui.home.Kayit;
import com.bariscangungor.teknikservisuygulamas.ui.home.ListAdapter;

import java.util.ArrayList;

public class SmsGecmisiFragment extends Fragment {

    private ArrayList<SMS> kayit;
    private ListView listView;
    private listAdapterSms la;

    private View root;

    private void init()
    {
        kayit = new ArrayList<SMS>();
        listView = (ListView)root.findViewById(R.id.sms_list);
        la = new listAdapterSms(root.getContext(),kayit);
        listView.setAdapter(la);
    }

    private void listeyiDoldur()
    {
        /*for (int i = 0 ; i < 20 ; i++)
        {
            Kayit k = new Kayit(i,"bar","samsung","s9+","hoparlor patlak",0,"10/2/2020","05412422389");
            kayit.add(k);
        }*/
        Database db = new Database(root.getContext());
        kayit.addAll(db.getSMS());
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //evViewModel = new ViewModelProvider(this).get(EvViewModel.class);
        root = inflater.inflate(R.layout.fragment_smsgecmisi, container, false);
        init();
        listeyiDoldur();

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////                Toast.makeText(root.getContext(), kayit.get(i).getAdsoyad()+" "+i, Toast.LENGTH_SHORT).show();
//                Bundle bundle = new Bundle();
//                bundle.putInt("id", kayit.get(i).getId());
//                Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_detaylar, bundle);
//            }
//        });
        // Button btn = (Button)root.findViewById(R.id.btn_gecmis);
        //btn.setText("Geçmiş");
        // btn.setTextColor();

        return root;
    }
}