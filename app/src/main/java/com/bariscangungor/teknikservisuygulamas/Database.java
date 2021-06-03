package com.bariscangungor.teknikservisuygulamas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.annotation.Nullable;

import com.bariscangungor.teknikservisuygulamas.ui.home.Kayit;
import com.bariscangungor.teknikservisuygulamas.ui.sms.SMS;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "kayit.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_KAYITLAR_CREATE = "CREATE TABLE kayitlar (" +
            "    "+ TablesInfo.Kayitlar.COLUMN_ID +"                  INTEGER     PRIMARY KEY AUTOINCREMENT," +
            "    "+TablesInfo.Kayitlar.COLUMN_TELEFON_NO+"            TEXT (11)   ," +
            "    "+ TablesInfo.Kayitlar.COLUMN_AD_SOYAD +"            TEXT (100)  ," +
            "    "+ TablesInfo.Kayitlar.COLUMN_MARKA +"               TEXT (50)   ," +
            "    "+ TablesInfo.Kayitlar.COLUMN_MODEL +"               TEXT (150)  ," +
            "    "+ TablesInfo.Kayitlar.COLUMN_ARIZA +"               TEXT (500)  ," +
            "    "+ TablesInfo.Kayitlar.COLUMN_GIRDI_TARIH +"         TEXT (30)   ," +
            "    "+ TablesInfo.Kayitlar.COLUMN_BITIS_TARIH +"         TEXT (30)," +
            "    "+ TablesInfo.Kayitlar.COLUMN_TEKNISYEN_ACIKLAMA +"  TEXT (100)," +
            "    "+ TablesInfo.Kayitlar.COLUMN_SONUC +"               INTEGER (1) , " +
            "    "+ TablesInfo.Kayitlar.COLUMN_UCRET +"               INTEGER (11)"  +
            ");";


    private static final String TABLE_SMS_CREATE= "CREATE TABLE smsKayitlari (" +
            "    "+TablesInfo.smsKayitlar.COLUMN_ID+"       INTEGER   PRIMARY KEY AUTOINCREMENT," +
            "    "+TablesInfo.smsKayitlar.COLUMN_GELEN_NO+" TEXT (11)," +
            "    "+TablesInfo.smsKayitlar.COLUMN_TARIH+"    TEXT (30) " +
            ");";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_KAYITLAR_CREATE);
        sqLiteDatabase.execSQL(TABLE_SMS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TablesInfo.Kayitlar.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean KayitEkle(String adsoyad , String telefon_no,
                          String marka,String model,String ariza, String tarih)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        boolean donut = false;

        cv.put(TablesInfo.Kayitlar.COLUMN_AD_SOYAD,adsoyad.trim());
        cv.put(TablesInfo.Kayitlar.COLUMN_TELEFON_NO,telefon_no.trim());
        cv.put(TablesInfo.Kayitlar.COLUMN_MARKA,marka.trim());
        cv.put(TablesInfo.Kayitlar.COLUMN_MODEL,model.trim());
        cv.put(TablesInfo.Kayitlar.COLUMN_ARIZA,ariza.trim());
        cv.put(TablesInfo.Kayitlar.COLUMN_GIRDI_TARIH,tarih.trim());
        cv.put(TablesInfo.Kayitlar.COLUMN_SONUC,0);//sonuç 0 ise arıza devam 1 ise tamamlanmış 2 ise elimizde patlamış

        long result = db.insert(TablesInfo.Kayitlar.TABLE_NAME,null,cv);

        if(result > -1)
        {
            Log.i("Database","Kaydedildi");
            donut = true;
        }
        else
        {
            Log.e("Database","Sorun çıktı usta");
            donut=false;
        }
        db.close();
        return donut;
    }

    public ArrayList<Kayit> getKayitlar(int i) //veritabanındaki kayıtları ister arraylist altında kayit sınıfı olarak olarak geri döndürür
    {
        ArrayList<Kayit> data = new ArrayList<>(); //arraylist oluşturuyoz.
        SQLiteDatabase db =this.getReadableDatabase();//sqlite database çağırıyoruz.
//        String[] satir = {TablesInfo.Kayitlar.COLUMN_ID,TablesInfo.Kayitlar.COLUMN_AD_SOYAD,TablesInfo.Kayitlar.COLUMN_MARKA,TablesInfo.Kayitlar.COLUMN_MODEL,TablesInfo.Kayitlar.COLUMN_ARIZA,TablesInfo.Kayitlar.COLUMN_GIRDI_TARIH,TablesInfo.Kayitlar.COLUMN_SONUC,TablesInfo.Kayitlar.COLUMN_TELEFON_NO};// hangi sutünları istediğimizi ekliyoruz.
//        Cursor c = db.query(TablesInfo.Kayitlar.TABLE_NAME,satir,"sonuc='0'",null,null,null,null);//burda sql komutunu çağırıyoruz ve gelen dönütü kaydediyoruz.
        Cursor  c;
        if(i == 0) {
            c = db.rawQuery("select * from "+TablesInfo.Kayitlar.TABLE_NAME+" Where sonuc='"+i+"'",null);
        } else
        {
            c = db.rawQuery("select * from "+TablesInfo.Kayitlar.TABLE_NAME+" Where sonuc='"+i+"' order by id desc",null);
        }
        while(c.moveToNext()) //döngüye başlıyoruz tüm satırlar okunana kadar dönüyo
        {
            //mevcut satırdaki tüm veriyi kayıt sınıfına kaydediyo sonra arraylistin içine saklıyo.
            data.add(new Kayit(c.getInt(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_ID)),c.getString(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_AD_SOYAD)),c.getString(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_MARKA)),c.getString(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_MODEL)),c.getString(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_ARIZA)),c.getInt(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_SONUC)),c.getString(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_GIRDI_TARIH)),c.getString(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_TELEFON_NO))));
        }

        c.close();
        db.close();//sqliteyi kapatıyoruz.

        return data;//veriyi geri gönderiyoruz.
    }

    public ArrayList<Kayit> getKayitlarTelefon(String telefon_no) //veritabanındaki kayıtları ister arraylist altında kayit sınıfı olarak olarak geri döndürür
    {
        ArrayList<Kayit> data = new ArrayList<>(); //arraylist oluşturuyoz.
        SQLiteDatabase db =this.getReadableDatabase();//sqlite database çağırıyoruz.
        String[] satir = {TablesInfo.Kayitlar.COLUMN_ID,TablesInfo.Kayitlar.COLUMN_AD_SOYAD,TablesInfo.Kayitlar.COLUMN_MARKA,TablesInfo.Kayitlar.COLUMN_MODEL,TablesInfo.Kayitlar.COLUMN_ARIZA,TablesInfo.Kayitlar.COLUMN_GIRDI_TARIH,TablesInfo.Kayitlar.COLUMN_SONUC,TablesInfo.Kayitlar.COLUMN_TELEFON_NO};// hangi sutünları istediğimizi ekliyoruz.
        Cursor c = db.query(TablesInfo.Kayitlar.TABLE_NAME,satir,"sonuc='0' AND telefon_no='"+telefon_no+"'",null,null,null,null);//burda sql komutunu çağırıyoruz ve gelen dönütü kaydediyoruz.
        while(c.moveToNext()) //döngüye başlıyoruz tüm satırlar okunana kadar dönüyo
        {
            //mevcut satırdaki tüm veriyi kayıt sınıfına kaydediyo sonra arraylistin içine saklıyo.
            data.add(new Kayit(c.getInt(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_ID)),c.getString(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_AD_SOYAD)),c.getString(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_MARKA)),c.getString(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_MODEL)),c.getString(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_ARIZA)),c.getInt(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_SONUC)),c.getString(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_GIRDI_TARIH)),c.getString(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_TELEFON_NO))));
        }

        c.close();
        db.close();//sqliteyi kapatıyoruz.

        return data;//veriyi geri gönderiyoruz.
    }

    public Kayit getKayit(int id)
    {
        Log.i("database","id "+id);
        ArrayList<Kayit> data = new ArrayList<>();
        Kayit kayit = null;
        SQLiteDatabase db =this.getReadableDatabase();
//        String[] satir = {TablesInfo.Kayitlar.COLUMN_ID,TablesInfo.Kayitlar.COLUMN_AD_SOYAD,TablesInfo.Kayitlar.COLUMN_MARKA,TablesInfo.Kayitlar.COLUMN_MODEL,TablesInfo.Kayitlar.COLUMN_ARIZA,TablesInfo.Kayitlar.COLUMN_GIRDI_TARIH,TablesInfo.Kayitlar.COLUMN_SONUC,TablesInfo.Kayitlar.COLUMN_TELEFON_NO,TablesInfo.Kayitlar.COLUMN_UCRET,TablesInfo.Kayitlar.COLUMN_TEKNISYEN_ACIKLAMA,TablesInfo.Kayitlar.COLUMN_BITIS_TARIH};
//        Cursor c = db.query(TablesInfo.Kayitlar.TABLE_NAME,satir,"sonuc='0'",null,null,null,null);
        Cursor  c = db.rawQuery("select * from "+TablesInfo.Kayitlar.TABLE_NAME+" where 1",null);
        while(c.moveToNext())
        {
            if(id == c.getInt(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_ID)))
            {
                kayit= new Kayit(c.getInt(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_ID)),c.getString(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_AD_SOYAD)),c.getString(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_MARKA)),c.getString(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_MODEL)),c.getString(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_ARIZA)),c.getInt(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_SONUC)),c.getString(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_GIRDI_TARIH)),c.getString(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_TELEFON_NO)));

                if(!c.isNull(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_BITIS_TARIH)))
                    kayit.setBitis_tarih(c.getString(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_BITIS_TARIH)));
                else
                    Log.e("database","tarih");

                if(!c.isNull(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_TEKNISYEN_ACIKLAMA)))
                    kayit.setTeknisyen_aciklama(c.getString(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_TEKNISYEN_ACIKLAMA)));
                else
                    Log.e("database","aciklama");

                if(!c.isNull(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_UCRET)))
                    kayit.setUcret(c.getInt(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_UCRET)));
                else
                    Log.e("database","ucret");
            }
        }

        c.close();
        db.close();

        return kayit;
    }

    public void updateKayitlar(Kayit kayit) //kayitları güncelleme fonksiyonu
    {
        SQLiteDatabase db = this.getWritableDatabase(); // veritabanını oluşturuyoruz.
        ContentValues cv = new ContentValues();         // veritabanına yazılacak verileri saklar.
        //verileri geçici olarak içeri aktarıyoruz.
        cv.put(TablesInfo.Kayitlar.COLUMN_AD_SOYAD,kayit.getAdsoyad().trim());
        cv.put(TablesInfo.Kayitlar.COLUMN_TELEFON_NO,kayit.getTelefon_no().trim());
        cv.put(TablesInfo.Kayitlar.COLUMN_MARKA,kayit.getMarka().trim());
        cv.put(TablesInfo.Kayitlar.COLUMN_MODEL,kayit.getModel().trim());
        cv.put(TablesInfo.Kayitlar.COLUMN_ARIZA,kayit.getAriza().trim());
        cv.put(TablesInfo.Kayitlar.COLUMN_GIRDI_TARIH,kayit.getTarih().trim());
        cv.put(TablesInfo.Kayitlar.COLUMN_BITIS_TARIH,kayit.getBitis_tarih().trim());
        cv.put(TablesInfo.Kayitlar.COLUMN_TEKNISYEN_ACIKLAMA,kayit.getTeknisyen_aciklama().trim());
        cv.put(TablesInfo.Kayitlar.COLUMN_SONUC,kayit.getSonucInt());
        cv.put(TablesInfo.Kayitlar.COLUMN_UCRET,kayit.getUcret());

        db.update(TablesInfo.Kayitlar.TABLE_NAME,cv,"id="+kayit.getId(),null); //sqlite komutu çalıştırır ve verileri yazar.
        Log.d("database","updateok");//geliştiriciye hata ayıklamada yardımcı uyaru verir
    }

    public void deleteKayitlar(Kayit kayit)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TablesInfo.Kayitlar.TABLE_NAME,TablesInfo.Kayitlar.COLUMN_ID+"=?",new String[]{String.valueOf(kayit.getId())});
        db.close();
    }

    //sms database
    public boolean smsEkle(String telefon_no, String tarih)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        boolean donut = false;

        cv.put(TablesInfo.smsKayitlar.COLUMN_GELEN_NO,telefon_no.trim());
        cv.put(TablesInfo.smsKayitlar.COLUMN_TARIH,tarih.trim());

        long result = db.insert(TablesInfo.smsKayitlar.TABLE_NAME,null,cv);

        if(result > -1)
        {
            Log.i("Database","Kaydedildi");
            donut = true;
        }
        else
        {
            Log.e("Database","Sorun çıktı usta");
            donut=false;
        }
        db.close();
        return donut;
    }

    public ArrayList<SMS> getSMS() //veritabanındaki kayıtları ister arraylist altında kayit sınıfı olarak olarak geri döndürür
    {
        ArrayList<SMS> data = new ArrayList<>(); //arraylist oluşturuyoz.
        SQLiteDatabase db =this.getReadableDatabase();//sqlite database çağırıyoruz.
//        String[] satir = {TablesInfo.smsKayitlar.COLUMN_ID,TablesInfo.smsKayitlar.COLUMN_GELEN_NO,TablesInfo.smsKayitlar.COLUMN_TARIH};// hangi sutünları istediğimizi ekliyoruz.
//        Cursor c = db.query(TablesInfo.Kayitlar.TABLE_NAME,satir,"WHERE 1",null,null,null,null);//burda sql komutunu çağırıyoruz ve gelen dönütü kaydediyoruz.
        Cursor  c = db.rawQuery("select * from "+TablesInfo.smsKayitlar.TABLE_NAME+" order by id desc",null);
        while(c.moveToNext()) //döngüye başlıyoruz tüm satırlar okunana kadar dönüyo
        {
            //mevcut satırdaki tüm veriyi kayıt sınıfına kaydediyo sonra arraylistin içine saklıyo.
            String ad =getAdSoyad(c.getString(c.getColumnIndex(TablesInfo.smsKayitlar.COLUMN_GELEN_NO)));
            data.add(new SMS(c.getInt(c.getColumnIndex(TablesInfo.smsKayitlar.COLUMN_ID)),c.getString(c.getColumnIndex(TablesInfo.smsKayitlar.COLUMN_TARIH)),c.getString(c.getColumnIndex(TablesInfo.smsKayitlar.COLUMN_GELEN_NO)),ad));
        }

        c.close();
        db.close();//sqliteyi kapatıyoruz.

        return data;//veriyi geri gönderiyoruz.
    }

    private String getAdSoyad(String telefon_no)
    {
        Log.i("database","id "+telefon_no);
        ArrayList<Kayit> data = new ArrayList<>();
        Kayit kayit = null;
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor  c = db.rawQuery("select "+TablesInfo.Kayitlar.COLUMN_AD_SOYAD+" from "+TablesInfo.Kayitlar.TABLE_NAME+" Where "+TablesInfo.Kayitlar.COLUMN_TELEFON_NO+"='"+telefon_no+"'",null);
        String ad ="";

        while(c.moveToNext())
        {
            ad = c.getString(c.getColumnIndex(TablesInfo.Kayitlar.COLUMN_AD_SOYAD));
        }

        c.close();
        db.close();

        return ad;
    }
}

