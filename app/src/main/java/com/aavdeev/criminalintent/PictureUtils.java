package com.aavdeev.criminalintent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PictureUtils {
    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {
        //Чтение размеров изображения на диске
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds =true;
        BitmapFactory.decodeFile( path, options );

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        //Вычисление степени масштабирования
        int inSampleSize = 1;
        if (srcHeight > destHeight || srcWidth > destWidth) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round( srcHeight / destHeight );
            } else {
                inSampleSize = Math.round( srcWidth / destWidth );
            }
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        //чтение данных и создание итогового изображения
        return BitmapFactory.decodeFile( path, options );
    }


}
