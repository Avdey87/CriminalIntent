package com.aavdeev.criminalintent;

import android.content.Intent;
import android.support.v4.app.Fragment;

public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callbacks {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdatail;
    }
// определить интерфейс какого типа был заполнен
    @Override
    public void onCrimeSelected(Crime crime) {
        //Проверяем наличие индетификатора в detail_fragment_container
        //имеется ли у него контейнер detail_fragment_container
        // для размещения в  CrimeFragment.
        if (findViewById( R.id.detail_fragment_container ) == null) {
            Intent intent = CrimePagerActivity.newIntent( this, crime.getId() );
            startActivity( intent );
        }
        //если содержит
        else {
            //Создаем CrimeFragment. переменную и помещаем в нее
            //
            Fragment newDetail = CrimeFragment.newInstance( crime.getId() );
            getSupportFragmentManager().beginTransaction()
                    //помещаем в detail_fragment_container фрагмент который хотим видеть
                    .replace( R.id.detail_fragment_container, newDetail )
                    .commit();

        }

    }

}
