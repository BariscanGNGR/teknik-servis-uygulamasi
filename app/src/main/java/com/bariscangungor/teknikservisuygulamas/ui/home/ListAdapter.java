package com.bariscangungor.teknikservisuygulamas.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bariscangungor.teknikservisuygulamas.R;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Kayit>
{
    private final LayoutInflater inflater;
    private final Context context;
    private ViewHolder holder;
    private final ArrayList<Kayit> kayitlar;

    public ListAdapter(@NonNull Context context, ArrayList<Kayit> kayitlar) {
        super(context, 0,kayitlar);
        this.context=context;
        this.kayitlar=kayitlar;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
        {
            convertView = inflater.inflate(R.layout.list_view_item_main,null);

            holder = new ViewHolder();
            holder.adsoyad=(TextView) convertView.findViewById(R.id.item_AdSoyad);
            holder.marka=(TextView) convertView.findViewById(R.id.item_Marka);
            holder.model=(TextView) convertView.findViewById(R.id.item_Model);
            holder.ariza=(TextView) convertView.findViewById(R.id.item_Ariza);
            holder.sonuc=(TextView) convertView.findViewById(R.id.item_sonuc);
            holder.tarih=(TextView) convertView.findViewById(R.id.item_tarih);
            holder.sonucResim=(ImageView) convertView.findViewById(R.id.item_imageSonuc);
            convertView.setTag(holder);

        }
        else
        {
            holder = (ViewHolder)convertView.getTag();
        }

        Kayit kayit = kayitlar.get(position);
        if(kayit!=null)
        {
            holder.adsoyad.setText(kayit.getAdsoyad());
            holder.marka.setText(kayit.getMarka());
            holder.model.setText(kayit.getModel());
            holder.ariza.setText(kayit.getAriza());
            holder.sonuc.setText(kayit.getSonuc());
            holder.tarih.setText(kayit.getTarih());
            holder.sonucResim.setImageResource(kayit.getSonucPhoto());
        }

        return convertView;
    }

    public int getCount()
    {
        return kayitlar.size();
    }
    public Kayit getItem(int index)
    {
        return kayitlar.get(index);
    }

    public long getItemId(int index) {
        return kayitlar.get(index).hashCode();
    }
}
