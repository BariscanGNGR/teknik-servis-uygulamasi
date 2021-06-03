package com.bariscangungor.teknikservisuygulamas;

import android.provider.BaseColumns;

public class TablesInfo {

        public static final class Kayitlar implements BaseColumns
        {
                public static final String TABLE_NAME = "kayitlar";

                public static final String COLUMN_ID = "id";
                public static final String COLUMN_TELEFON_NO = "telefon_no";
                public static final String COLUMN_AD_SOYAD = "ad_soyad";
                public static final String COLUMN_MARKA = "marka";
                public static final String COLUMN_MODEL = "model";
                public static final String COLUMN_ARIZA = "ariza";
                public static final String COLUMN_GIRDI_TARIH = "girdi_tarih";
                public static final String COLUMN_BITIS_TARIH = "bitis_tarih";
                public static final String COLUMN_TEKNISYEN_ACIKLAMA = "teknisyen_aciklama";
                public static final String COLUMN_SONUC = "sonuc";
                public static final String COLUMN_UCRET = "ucret";
        }

        public static final class smsKayitlar implements BaseColumns
        {
                public static final String TABLE_NAME = "smsKayitlari";

                public static final String COLUMN_ID = "id";
                public static final String COLUMN_GELEN_NO = "gelen_no";
                public static final String COLUMN_TARIH = "tarih";

        }


}