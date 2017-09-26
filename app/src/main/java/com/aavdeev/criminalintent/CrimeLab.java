package com.aavdeev.criminalintent;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static  CrimeLab sCrimeLab;
    private List<Crime> mCrimes;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        //Создаем пустой списко объектов Crime
        mCrimes = new ArrayList<>();
        //mAppContext = appContext;
        //создаем 100 объектов Crime
        for (int i =0 ; i<100;i++) {
            Crime crime = new Crime();
            //Устанавливаем название для каждого нового
            //объеккта вид Crime #1....
            crime.setTitle( "Crime # " + i );
            //Каждому втрому созданому элементу устанавливаем гплочку
            crime.setSolved( i % 2 == 0 );
            mCrimes.add( crime );
        }
    }

    //Возвращает List Crime-ов (упорядочный список объектов Crime)
    public List<Crime> getCrimes() {
        return mCrimes;
    }

    //возвращает объект Crime с заданным индетификатором
    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals( id )) {
                return crime;
            }
        }
        return null;
    }
}
