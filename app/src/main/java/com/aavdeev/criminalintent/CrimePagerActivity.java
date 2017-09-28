package com.aavdeev.criminalintent;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends FragmentActivity {
    private static final String EXTRA_CRIME_ID = "com.aavdeev.criminalintent.crime_id";
    private ViewPager mViewPaper;
    private List<Crime> mCrimes;

    //создаем новый метод типа интент с 2 параметрами
    public static Intent newIntent(Context packageContext, UUID crimeId) {
        //создаем Intent в который пердаем packageContext
        // и packageContext.class. Сообщаем CrimeFragment, какой
        // объект Crime следует отображать
        Intent intent = new Intent( packageContext, CrimePagerActivity.class );
        //передаем EXTRA_CRIME_ID-ключ объекта и crimeId-связанное с ним значение
        //сохраняем индетификатор преступления
        intent.putExtra( EXTRA_CRIME_ID, crimeId );
        return intent;
    }

    //создаем активность настройки хранятся в файле activity_crime_pager_view_pager
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_crime_pager );
//в переменную записываем индетификатор полученный
        // из константы EXTRA_CRIME_ID
        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra( EXTRA_CRIME_ID );

        //создаем объект ViewPager хранятся в файле activity_crime_pager_view_pager
        mViewPaper = (ViewPager) findViewById( R.id.activity_crime_pager_view_pager );
        //создаем список, получаем данные для списка из метода get
        //класса CrimeLab и получаем список для текущей активности
        mCrimes = CrimeLab.get( this ).getCrimes();
        //получаем экземпляр FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        //mViewPaper устанавливаем занчения который создаются
        // в вложенном классе. FragmentStatePagerAdapter уравляет взаимодействием с ViewPager
        mViewPaper.setAdapter( new FragmentStatePagerAdapter( fragmentManager ) {
            //метод который возвращает текущую позицию курсора
            @Override
            public Fragment getItem(int position) {
                //создаем объектCrime и записываем в него текущую позиуцию
                Crime crime = mCrimes.get( position );
                //возвращаем объект CrimeFragment.newInstance с
                // Id эелементом списка(индетификаторм объекта списка)
                return CrimeFragment.newInstance( crime.getId() );
            }

            //возращаем текущее кодичество элементов в списке
            @Override
            public int getCount() {
                return mCrimes.size();
            }
        } );
        //в цикле пробегаем по всем списку mCrimes.size()
        for (int i=0; i<mCrimes.size();i++) {
            //если находим экземплер Crime у которого Id совпадает с crimeId
            //в дополении intent
            if (mCrimes.get( i ).getId().equals( crimeId )) {
                //меняем текущий элемент по индексу найденого Crime
                mViewPaper.setCurrentItem( i );
                break;
            }
        }
    }
}
