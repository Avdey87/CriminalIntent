package com.aavdeev.criminalintent;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecycleView;
    private CrimeAdapter mAdapter;

    //Создаем фрагмент RecyclerView
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Создаем view (отображение на экране)
        //передаем ей разметку fragment_crime_list, помещаем все эт ов контейнер
        // устнанавливаем значение отображение по умолчанию false
        View view = inflater.inflate( R.layout.fragment_crime_list, container,
                false );
        // в mCrimeRecycleView записываем значение типо RecyclerView
        //в котором создаем view с разметкой определенной в crime_recycler_view
        mCrimeRecycleView = (RecyclerView) view.findViewById( R.id.crime_recycler_view );
        //устанавливаем значения mCrimeRecycleView в LayoutManager
        // создаем новый LinearLayoutManager который получает даный
        //из активити getActivity
        //RecycleView назначается объект LayoutManager.
        //RecycleView не отображает элементы на экране эту задачу он передаёт
        //LayoutManager. LayoutManager управляет позиционированием и определяет поведение
        //прокрутки
        mCrimeRecycleView.setLayoutManager( new LinearLayoutManager( getActivity() ) );
        updateUI();
        //Возвращаем view
        return view;
    }

    private void updateUI() {
        //Создаем объект типа CrimeLab
        //записываем в него новую активити
        CrimeLab crimeLab = CrimeLab.get( getActivity() );
        //Создаем список и в который записываем данные
        //из getCrimes (создается список из 100 элементов)
        List<Crime> crimes = crimeLab.getCrimes();
        //создаем переменную mAdapter для записи в нее
        //списка crimes
        mAdapter = new CrimeAdapter( crimes );
        //устанавливаем в mCrimeRecycleView список crimes
        //полученный из crimes
        mCrimeRecycleView.setAdapter( mAdapter );
    }

    //Создаме внутрений класс CrimeHolder
    private class CrimeHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;

        //Создаем объект типа View
        //записываем данные в переменную mTitleTextView
        //типа TextView для отображения в Activity
        public CrimeHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView)itemView.findViewById
                    ( R.id.list_item_crime_title_text_view );
            mDateTextView = (TextView) itemView.findViewById
                    ( R.id.list_item_crime_date_text_view );
            mSolvedCheckBox = (CheckBox) itemView.findViewById
                    ( R.id.list_item_crime_solved_check_box );
        }


    }

    //Создаем адаптер(переходник) для возможности создавать
    //обекты типа ViewHolder или для связки его с Crime
    //RecyclerView ничего не знает о Crime все информация о Crime
    //храниться в адаптаре
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }
        //Вызываестя виджетом RecyclerView когда ему потребуется
        //новое представления для отображения элемента
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Создаем LayoutInflater и заполняем его данными которые получаем
        //из Активити getActivity
            LayoutInflater layoutInflater = LayoutInflater.from( getActivity() );
        //Создаем объект View упаковываем его в ViewHolder
        //макет для отображения View определен в list_item_crime
            View view = layoutInflater
                    .inflate( R.layout.list_item_crime, parent, false );
            return new CrimeHolder( view );
        }

        //Связывает представление View объекта ViewHolder с объектом модели
        //При вызове он получает ViewHolder и позицию в наборе данных
        // (В какую позицию поставить элемент списка)
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
        //Создаем переменную типа Crime в которую записываем позицию
        //элемента Crime в массиве
        //позиция определяется индексом объекта Crime в массиве
            Crime crime = mCrimes.get( position );
         //в holder устанавливаем текст типа TextView полученый из mTitleTextView
         // и устанавливаем его в объект который находиться в списке на позиции
         // полученой из Crime crime = mCrimes.get( position );
            holder.mTitleTextView.setText( crime.getTitle() );
        }

        //возвращает размер списка mCrimes
        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
