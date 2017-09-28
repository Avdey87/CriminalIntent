package com.aavdeev.criminalintent;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {
    private static final String EXTRA_CRIME_ID = "com.aavdeev.criminalintent.crime_id";

    //создаем новый метод типа интент с 2 параметрами
    public static Intent newIntent(Context packageContext, UUID crimeId) {
        //создаем Intent в который пердаем packageContext
        // и packageContext.class. Сообщаем CrimeFragment, какой
        // объект Crime следует отображать
        Intent intent = new Intent( packageContext, CrimeActivity.class );
        //передаем EXTRA_CRIME_ID-ключ объекта и crimeId-связанное с ним значение
        //сохраняем индетификатор преступления
        intent.putExtra( EXTRA_CRIME_ID, crimeId );
        return intent;
    }

    //Создаем фрагмент с индетификатором.
    @Override
    protected Fragment createFragment() {
        //переменной crimeId присваеваем значения типа UUID
        //которок получаем из константы EXTRA_CRIME_ID
        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra( EXTRA_CRIME_ID );
        //возвращаем в экземпляр индетификатор CrimeFragment.newInstance

        return CrimeFragment.newInstance( crimeId );
    }
}
