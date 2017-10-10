package com.aavdeev.criminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.aavdeev.criminalintent.Crime;

import java.util.Date;
import java.util.UUID;

public class CrimeCursorWrapper extends CursorWrapper {
    public CrimeCursorWrapper(Cursor cursor) {
        super( cursor );
    }

    //Мотод получения даных из БД
    public Crime getCrime() {
       //получем значение полей из БД
        String uuidString = getString( getColumnIndex( CrimeDbSchema.CrimeTable.Cols.UUID ) );
        String title = getString( getColumnIndex( CrimeDbSchema.CrimeTable.Cols.TITLE ) );
        long date = getLong( getColumnIndex( CrimeDbSchema.CrimeTable.Cols.DATE ) );
        int isSolved = getInt( getColumnIndex( CrimeDbSchema.CrimeTable.Cols.SOLVED ) );
        String suspect = getString( getColumnIndex( CrimeDbSchema.CrimeTable.Cols.SUSPECT ) );

        //Создаем экземпляр  Crime
        Crime crime = new Crime( UUID.fromString( uuidString ) );
        //Устанавливаем полученные значения в crime
        crime.setTitle( title );
        crime.setDate( new Date( date ) );
        crime.setSolved( isSolved != 0 );
        crime.setSuspect( suspect );
        return crime;
    }
}
