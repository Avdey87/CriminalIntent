package com.aavdeev.criminalintent;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aavdeev.criminalintent.database.CrimeBaseHelper;
import com.aavdeev.criminalintent.database.CrimeCursorWrapper;
import com.aavdeev.criminalintent.database.CrimeDbSchema;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;


    //Конструктор для создания БД
    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper( mContext )
                //открывает файл БД CrimeDbSchema если файл не существует то
                //он создается
                .getWritableDatabase();

    }

//метод для добавление элемента в таблицу
    public void addCrime(Crime c) {
        ContentValues values = getContentValues( c );
        mDatabase.insert( CrimeDbSchema.CrimeTable.NAME, null, values );
    }

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab( context );
        }
        return sCrimeLab;
    }


    public void deleteCrime(UUID id) {
        /*Iterator<Crime> i = mCrimes.iterator();
        while (i.hasNext()) {
            Crime crime = i.next();
            if (crime.getId().equals( id )) {
                i.remove();
                return;
            }
        }*/
    }

    //Возвращает List Crime-ов (упорядочный список объектов Crime)
    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor = queryCrimes( null, null );
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add( cursor.getCrime() );
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return crimes;
    }

    //возвращает объект Crime с заданным индетификатором
    public Crime getCrime(UUID id) {

        CrimeCursorWrapper cursor = queryCrimes(
                CrimeDbSchema.CrimeTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );
        try{
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        }finally {
            cursor.close();
        }

    }

    // метод для записи данных в БД
    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
       //пишим UUID,  TITLE ......
        values.put( CrimeDbSchema.CrimeTable.Cols.UUID, crime.getId().toString() );
        values.put( CrimeDbSchema.CrimeTable.Cols.TITLE, crime.getTitle() );
        values.put( CrimeDbSchema.CrimeTable.Cols.DATE, crime.getDate().getTime() );
        values.put( CrimeDbSchema.CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0 );
        return values;
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeDbSchema.CrimeTable.NAME,
                null,//Сolumns -null выбирает все столбцы
                whereClause,
                whereArgs,
                null,//groupBy
                null,//having
                null//orderBy
        );
        return new CrimeCursorWrapper( cursor );
    }

  //Метод добавление строк в БД
    public void updateCrime(Crime crime) {
        //пишим в строковое занчение id добовлемой строки
        String uuidString = crime.getId().toString();
        //создаем ContentValues переменую  для записи в БД
        ContentValues values = getContentValues( crime );

        //запись в БД с всеми необходимыми параметрами
        //определяем имя таблице и объект values который должне быть присвоен
        //каждой обновляемой записи
        mDatabase.update( CrimeDbSchema.CrimeTable.NAME, values,
                CrimeDbSchema.CrimeTable.Cols.UUID + " = ?",
                new String[]{uuidString} );
    }




}
