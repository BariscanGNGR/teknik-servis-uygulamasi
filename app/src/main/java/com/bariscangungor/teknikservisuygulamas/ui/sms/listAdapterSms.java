package com.bariscangungor.teknikservisuygulamas.ui.sms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bariscangungor.teknikservisuygulamas.R;

import java.util.ArrayList;

public class listAdapterSms extends ArrayAdapter<SMS> {

    private final LayoutInflater inflater;
    private final Context context;
    private ViewHoldersms holder;
    private final ArrayList<SMS> kayitlar;

    public listAdapterSms(@NonNull Context context, ArrayList<SMS> kayitlar) {
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
            convertView = inflater.inflate(R.layout.list_view_item_sms,null);

            holder = new ViewHoldersms();
            holder.telefon=(TextView) convertView.findViewById(R.id.item_sms_telefon);
            holder.tarih=(TextView) convertView.findViewById(R.id.item_sms_tarih);
            holder.ad = (TextView) convertView.findViewById(R.id.item_sms_AdSoyad);
            convertView.setTag(holder);

        }
        else
        {
            holder = (ViewHoldersms)convertView.getTag();
        }

        SMS kayit = kayitlar.get(position);
        if(kayit!=null)
        {
            holder.telefon.setText(kayit.getTelefon_no());
            holder.tarih.setText(kayit.getTarih());
            holder.ad.setText(kayit.getAdsoyad());
        }

        return convertView;
    }

    public int getCount()
    {
        return kayitlar.size();
    }
    public SMS getItem(int index)
    {
        return kayitlar.get(index);
    }

    public long getItemId(int index) {
        return kayitlar.get(index).hashCode();
    }

    public class ViewHoldersms
    {
        TextView telefon,tarih,ad;
    }

}

