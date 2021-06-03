package com.bariscangungor.teknikservisuygulamas.ui.home;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.bariscangungor.teknikservisuygulamas.Database;
import com.bariscangungor.teknikservisuygulamas.MainActivity;
import com.bariscangungor.teknikservisuygulamas.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class EvFragment extends Fragment {

    //private EvViewModel evViewModel;

    private ArrayList<Kayit> kayit;
    private ListView listView;
    private ListAdapter la;
    Spinner spinner;
    private String TAG = "ev";

    private View root;

    private void init()
    {
        kayit = new ArrayList<>();
        listView = (ListView)root.findViewById(R.id.listview);
        la = new ListAdapter(root.getContext(),kayit);
        listView.setAdapter(la);

        spinner = root.findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(root.getContext(),R.array.spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinnerListener();
    }

    private void spinnerListener()
    {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int parametre;
                switch (spinner.getSelectedItem().toString())
                {
                    case "Yapılacak":
                        parametre = 0;
                        break;
                    case "Başarısız":
                        parametre = 2;
                        break;
                    case "Başarılı":
                        parametre = 1;
                        break;
                    default:
                        parametre = 4;
                        break;
                }
                Log.d(TAG, "onItemSelected: "+parametre);
                Database db = new Database(root.getContext());
                kayit.clear();
                kayit.addAll(db.getKayitlar(parametre));
                la = new ListAdapter(root.getContext(),kayit);
                listView.setAdapter(la);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void listeyiDoldur()
    {
        /*for (int i = 0 ; i < 20 ; i++)
        {
            Kayit k = new Kayit(i,"bar","samsung","s9+","hoparlor patlak",0,"10/2/2020","05412422389");
            kayit.add(k);
        }*/
        int parametre;
        switch (spinner.getSelectedItem().toString())
        {
            case "Yapılacak":
                parametre = 0;
                break;
            case "Başarısız":
                parametre = 2;
                break;
            case "Başarılı":
                parametre = 1;
                break;
            default:
                parametre = 4;
                break;
        }

        Database db = new Database(root.getContext());
        kayit.addAll(db.getKayitlar(parametre));

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //evViewModel = new ViewModelProvider(this).get(EvViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        listeyiDoldur();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(root.getContext(), kayit.get(i).getAdsoyad()+" "+i, Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putInt("id",kayit.get(i).getId());
                Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_detaylar,bundle);
            }
        });
       // Button btn = (Button)root.findViewById(R.id.btn_gecmis);
        //btn.setText("Geçmiş");
       // btn.setTextColor();

        return root;
    }
}