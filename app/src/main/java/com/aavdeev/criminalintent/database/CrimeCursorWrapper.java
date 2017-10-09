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
        String uuidString = getString( getColumnIndex( CrimeDbSchema.CrimeTable.Cols.UUID ) );
        String title = getString( getColumnIndex( CrimeDbSchema.CrimeTable.Cols.TITLE ) );
        long date = getLong( getColumnIndex( CrimeDbSchema.CrimeTable.Cols.DATE ) );
        int isSolved = getInt( getColumnIndex( CrimeDbSchema.CrimeTable.Cols.SOLVED ) );

        Crime crime = new Crime( UUID.fromString( uuidString ) );
        crime.setTitle( title );
        crime.setDate( new Date( date ) );
        crime.setSolved( isSolved != 0 );
        return crime;
    }
}
