package com.bariscangungor.teknikservisuygulamas;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.bariscangungor.teknikservisuygulamas.ui.home.Kayit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SMSReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        final String TAG = "sms";

        Bundle myBundle = intent.getExtras();
        SmsMessage[] messages = null;
        String strMessage = "";

        if (myBundle != null)
        {
            Object [] pdus = (Object[]) myBundle.get("pdus");
            messages = new SmsMessage[pdus.length];
            Database db = new Database(context);
            for (int i = 0; i < messages.length; i++)
            {
                //messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i],"3gpp");
                if(messages[i].getMessageBody().equalsIgnoreCase("sorgu"))
                {
                    Log.d("sms", "onReceive: test");
                    strMessage += "Sorgulama mesajı alındı işlem başlatılıyor...";

                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());
                    String tarih = sdf.format(new Date());


                    String numara = "";
                    if(messages[i].getOriginatingAddress().length() == 11)
                    {
                        numara = messages[i].getOriginatingAddress();
                    }else
                    {
                        int length = messages[i].getOriginatingAddress().length();
                        numara = messages[i].getOriginatingAddress().substring(length-11,length);
                    }
                    db.smsEkle(numara,tarih);
                    ArrayList<Kayit> sorgu = db.getKayitlarTelefon(numara);
                    String msg = "";
                    Log.d(TAG, "numara : "+numara);
                    SmsManager sms=SmsManager.getDefault();

                    Log.d(TAG, "if "+ sorgu.size());
                    for(int j = 0 ; j < sorgu.size() ; j++)
                    {
                        msg="Sayın "+sorgu.get(j).getAdsoyad()+"\n";
                        msg+=sorgu.get(j).getMarka()+" "+sorgu.get(j).getModel()+" cihazınızın durumu "+sorgu.get(j).getSonuc()+"";

                        sms.sendTextMessage(numara, null, msg , null,null);
                        Log.d(TAG, "sms : "+msg+"\n\n\n"+messages[i].getOriginatingAddress());
                    }
                    if(sorgu.size() == 0)
                        sms.sendTextMessage(messages[i].getOriginatingAddress(), null, "Bu numaraya ait sorgu bulunamamıştır..." , null,null);

                }
            }

            Toast.makeText(context, strMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
