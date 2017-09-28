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
import android.widget.Toast;

import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecycleView;
    private CrimeAdapter mAdapter;
    private Crime mCrime;

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
    //он отвечает за отдельный элемент в списке
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;
        private Crime mCrime;

        //Создаем объект типа View
        //записываем данные в переменную mTitleTextView
        //типа TextView для отображения в Activity
        public CrimeHolder(View itemView) {
            super( itemView );
            //устанавливаем слушатель на itemView
            itemView.setOnClickListener( this );
            //Присваеваем mTitleTextView объект типа TextView
            // и прикрепляем шаблон отображения из xml файла (list_item_crime)
            mTitleTextView = (TextView) itemView.findViewById
                    ( R.id.list_item_crime_title_text_view );
            // -//-
            mDateTextView = (TextView) itemView.findViewById
                    ( R.id.list_item_crime_date_text_view );
            // -//-
            mSolvedCheckBox = (CheckBox) itemView.findViewById
                    ( R.id.list_item_crime_solved_check_box );
        }

        public void bindCrime(Crime crime) {
            //присваем mCrime(объект типа Crime)
            //значение crime (переданное методу bindCrime)
            mCrime = crime;
            //устанавливаем текст для mTitleTextView полученный от  mCrime.getTitle()
            mTitleTextView.setText( mCrime.getTitle() );
            // устанавливаем текст(дату) и выводим это в строку полученные
            // от mCrime.getDate().toString()
            mDateTextView.setText( mCrime.getDate().toString() );
            //устанавливаем состояние флага mSolvedCheckBox
            //полученого из mCrime.isSolved()
            mSolvedCheckBox.setChecked( mCrime.isSolved() );
        }


        @Override
        public void onClick(View v) {
            //Toast оброботчик события нажатая кнопка
            //созадем текст при нажатой кнопки,makeText принмает несколько параметров получаем ID (getActivity)активити, вызываем экземпляр  объекта Crime mCrime у которого вызываем метод getTitle который возвращает стороку в нее пишем "clicked!", передаем в makeText параметр LENGTH_SHORT(легкое нажатие) далее вызываем метод show() отобразить на экране
            Toast.makeText( getActivity(),
                    mCrime.getTitle() + " clicked! ",
                    Toast.LENGTH_SHORT ).show();
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
            //объект типа CrimeHolder (holder)
            //вызывает метод bindCrime
            //bindCrime устанавливает Title, дату,
            // состояние флажка
            holder.bindCrime( crime );
        }

        //возвращает размер списка mCrimes
        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
