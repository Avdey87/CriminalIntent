package com.aavdeev.criminalintent.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.aavdeev.criminalintent.database.CrimeDbSchema.CrimeTable;

//Клас для работы с БД
public class CrimeBaseHelper extends SQLiteOpenHelper {
    //Константа в которой храним версию БД
    private static final int VERSION = 1;
    //Константа для хранения имя БД
    private static final String DATABASE_NAME = "crimeBase.db";

    //Конструктр для создание БД
    public CrimeBaseHelper(Context context) {
        //создаем БД с именем и версией
        super( context, DATABASE_NAME, null, VERSION );
    }

    //Метод для создания базы данных
    @Override
    public void onCreate(SQLiteDatabase db) {
        //саем команду создать БД с именем и передаем ей параметры создание табллицы
        db.execSQL( "create table " + CrimeTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                CrimeTable.Cols.UUID + ", " +
                CrimeTable.Cols.TITLE + ", " +
                CrimeTable.Cols.DATE + ", " +
                CrimeTable.Cols.SOLVED + ", " +
                CrimeTable.Cols.SUSPECT +", "+
                CrimeTable.Cols.CONTACT+ ")" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
