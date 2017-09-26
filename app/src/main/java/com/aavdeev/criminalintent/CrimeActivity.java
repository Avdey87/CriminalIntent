package com.aavdeev.criminalintent;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CrimeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        //добавления фрагмента в активность
        FragmentManager fm = getSupportFragmentManager();
        //Получение экземпляра CrimeFragment по инидификатору контейнерного представления
        Fragment fragment = fm.findFragmentById( R.id.fragmenrContainer );
//если  fragment уже находиться в списке FragmentManager то вызывается он
        //если нет то создается новый (например поворот экрна)
        if (fragment == null) {
            fragment = new CrimeFragment();
            //Создаем и закрепляем транзакцию кода
            fm.beginTransaction()
                    //передаем методу add 2 параметра индетификатор контейнераного фрагменат(где находиться фрагмент)
                    // и  и созданный объект CrimeFragment
                    .add( R.id.fragmenrContainer, fragment )
                    .commit();

        }
    }
}
