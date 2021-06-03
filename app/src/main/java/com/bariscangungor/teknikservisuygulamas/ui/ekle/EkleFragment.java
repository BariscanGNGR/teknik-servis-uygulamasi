package com.bariscangungor.teknikservisuygulamas.ui.ekle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bariscangungor.teknikservisuygulamas.Database;
import com.bariscangungor.teknikservisuygulamas.R;

import java.util.HashMap;

public class EkleFragment extends Fragment {

    private EkleViewModel ekleViewModel;

    private EditText adsoyad;
    private EditText telefon_no;
    private EditText marka;
    private EditText model;
    private EditText ariza;

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ekleViewModel = new ViewModelProvider(this).get(EkleViewModel.class);
        root = inflater.inflate(R.layout.fragment_ekle, container, false);
        //yenile yazılması lazım
        Database db = new Database(getActivity());

        adsoyad = root.findViewById(R.id.adsoyad);adsoyad.setText(ekleViewModel.getAdsoyad());
        telefon_no = root.findViewById(R.id.telefon_no);telefon_no.setText(ekleViewModel.getTelefon_no());
        marka = root.findViewById(R.id.marka);marka.setText(ekleViewModel.getMarka());
        model = root.findViewById(R.id.model);model.setText(ekleViewModel.getModel());
        ariza = root.findViewById(R.id.ariza);ariza.setText(ekleViewModel.getAriza());

        Button ekle_btn = root.findViewById(R.id.ekle_btn);

        ekle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ekleViewModel.setData(adsoyad.getText().toString(),telefon_no.getText().toString(),
                        marka.getText().toString(),model.getText().toString(),ariza.getText().toString());
                String islem = ekleViewModel.ekle(db);
                HashMap<String,String> hatalar = ekleViewModel.hatalar;
                if(islem.equals(hatalar.get("başarılı"))) {
                    Toast.makeText(getActivity(), hatalar.get("başarılı"), Toast.LENGTH_SHORT).show();
                    adsoyad.setText("");
                    telefon_no.setText("");
                    marka.setText("");
                    model.setText("");
                    ariza.setText("");
                }
                else if(islem.equals(hatalar.get("telefon")))
                {
                    Toast.makeText(getActivity(), hatalar.get("telefon"), Toast.LENGTH_SHORT).show();
                }
                else if(islem.equals(hatalar.get("formlar")))
                {
                    Toast.makeText(getActivity(), hatalar.get("formlar"), Toast.LENGTH_SHORT).show();
                }
            }
        });

//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        ekleViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
        ekleViewModel.setData(adsoyad.getText().toString(),telefon_no.getText().toString(),
                marka.getText().toString(),model.getText().toString(),ariza.getText().toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        adsoyad.setText(ekleViewModel.getAdsoyad());
        telefon_no.setText(ekleViewModel.getTelefon_no());
        marka.setText(ekleViewModel.getMarka());
        model.setText(ekleViewModel.getModel());
        ariza.setText(ekleViewModel.getAriza());
    }
}