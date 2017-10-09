package com.aavdeev.criminalintent;


import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;

    public CrimeLab() {

    }

    public void addCrime(Crime c) {
        mCrimes.add( c );
    }

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab( context );
        }
        return sCrimeLab;
    }

    public CrimeLab(Context context) {
        //Создаем пустой списко объектов Crime
        mCrimes = new ArrayList<>();

    }

    public void deleteCrime(UUID id) {
        Iterator<Crime> i = mCrimes.iterator();
        while (i.hasNext()) {
            Crime crime = i.next();
            if (crime.getId().equals( id )) {
                i.remove();
                return;
            }
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
