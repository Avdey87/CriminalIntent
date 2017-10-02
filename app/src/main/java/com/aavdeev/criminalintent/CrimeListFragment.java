package com.aavdeev.criminalintent;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    //создаем переменную типа int и присваемваем ей отрицательное значение
    //отрицательное значение будет означать что изменений не было внесено
    //так как в методе onClick значения больше нуля означают что были сделаны изменения
    private int lastUpdatePosition = -1;

//переопределяем метод
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //сообщаем экземпляру класса CrimeListFragment
        //что он должен полкчать обратный вызов меню
        setHasOptionsMenu( true );
    }

    //Создаем фрагмент RecyclerView
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Создаем view (отображение на экране)
        //передаем ей разметку fragment_crime_list, помещаем все эт ов контейнер
        // устнанавливаем значение отображение по умолчанию false
        View view = inflater.inflate(R.layout.fragment_crime_list, container,
                false);
        // в mCrimeRecycleView записываем значение типо RecyclerView
        //в котором создаем view с разметкой определенной в crime_recycler_view
        mCrimeRecycleView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        //устанавливаем значения mCrimeRecycleView в LayoutManager
        // создаем новый LinearLayoutManager который получает даный
        //из активити getActivity
        //RecycleView назначается объект LayoutManager.
        //RecycleView не отображает элементы на экране эту задачу он передаёт
        //LayoutManager. LayoutManager управляет позиционированием и определяет поведение
        //прокрутки
        mCrimeRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        //Возвращаем view
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    //переопределяем метод создания меню
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu( menu, inflater );
        //создаем меню в файле fragment_crime_list
        inflater.inflate( R.menu.fragment_crime_list, menu );
    }

    private void updateUI() {
        //Создаем объект типа CrimeLab
        //записываем в него новую активити
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        //Создаем список и в который записываем данные
        //из getCrimes (создается список из 100 элементов)
        List<Crime> crimes = crimeLab.getCrimes();
//если объект mAdapter не создан
        if (mAdapter == null) {
            //создаме переменную в которую записываем
            //список объектов Crime
            mAdapter = new CrimeAdapter(crimes);
            //у станвливаем в mCrimeRecycleView занчения mAdapter
            //в прокручиваемый список помещаем списко Crimes
            mCrimeRecycleView.setAdapter(mAdapter);
        } else {
            //если lastUpdatePosition больше -1 значит вносились изменения
            if (lastUpdatePosition > -1) {
                //обновляем состояние объекта списка Crimes вызываем
                //для объекта списка Crime, не всего списка Crimes
                mAdapter.notifyItemChanged(lastUpdatePosition);
                //после обноления возвращаем состояние объекта списка в состояние не менялся, устанавливаем отрицательное значение
                lastUpdatePosition = -1;
            } else {

                mAdapter.notifyDataSetChanged();
            }


            //проверяет изменялись ли данные в mAdapter
            //mAdapter.notifyDataSetChanged();
        }
    }

    //Создаме внутрений класс CrimeHolder
    //он отвечает за отдельный элемент в списке
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;
        private Crime mCrime;


        public CrimeHolder(View itemView) {
            super(itemView);
            //устанавливаем слушатель на itemView
            itemView.setOnClickListener(this);
            //Присваеваем mTitleTextView объект типа TextView
            // и прикрепляем шаблон отображения из xml файла (list_item_crime)
            mTitleTextView = (TextView) itemView.findViewById
                    (R.id.list_item_crime_title_text_view);
            // -//-
            mDateTextView = (TextView) itemView.findViewById
                    (R.id.list_item_crime_date_text_view);
            // -//-
            mSolvedCheckBox = (CheckBox) itemView.findViewById
                    (R.id.list_item_crime_solved_check_box);
        }

        public void bindCrime(Crime crime) {
            //присваем mCrime(объект типа Crime)
            //значение crime (переданное методу bindCrime)
            mCrime = crime;
            //устанавливаем текст для mTitleTextView полученный от  mCrime.getTitle()
            mTitleTextView.setText(mCrime.getTitle());
            // устанавливаем текст(дату) и выводим это в строку полученные
            // от mCrime.getDate().toString()
            mDateTextView.setText(mCrime.getDate().toString());
            //устанавливаем состояние флага mSolvedCheckBox
            //полученого из mCrime.isSolved()
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }


        @Override
        public void onClick(View v) {
//создаем новый Intent которуму присваем класс CrimePagerActivity
            //и передаем в качестве параметра индетификатор mCrime (элемент списка)
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            //перепменной lastUpdatePosition присваем текущее состояние
            //объекта списка
            lastUpdatePosition = this.getAdapterPosition();
            startActivity(intent);
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
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            //Создаем объект View упаковываем его в ViewHolder
            //макет для отображения View определен в list_item_crime
            View view = layoutInflater
                    .inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        //Связывает представление View объекта ViewHolder с объектом модели
        //При вызове он получает ViewHolder и позицию в наборе данных
        // (В какую позицию поставить элемент списка)
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            //Создаем переменную типа Crime в которую записываем позицию
            //элемента Crime в массиве
            //позиция определяется индексом объекта Crime в массиве
            Crime crime = mCrimes.get(position);
            //объект типа CrimeHolder (holder)
            //вызывает метод bindCrime
            //bindCrime устанавливает Title, дату,
            // состояние флажка
            holder.bindCrime(crime);
        }

        //возвращает размер списка mCrimes
        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

    //переопределяем метод выбраное меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //в цикл switch переадем id выбрана item (item.getItemId())
        switch (item.getItemId()) {
            //если Id совпадает с menu_item_new_crime
            case R.id.menu_item_new_crime:
                //Создаем новый экземпляр Crime
                Crime crime = new Crime();
                //у CrimeLab вызываем метод get(пробегает по всему списку
                // и возвращает id объекта в списке) псоле чего вызываем
                //addCrime для добавление экземпляра crime в коенц списка
                CrimeLab.get( getActivity() ).addCrime( crime );
                //Создаем Intent записываем а него интент с параметрами
                //getActivity получить активити(найти нужную ативити)
                //с id полученым из экземпляр класса Crime метода getId
                Intent intent = CrimePagerActivity
                        .newIntent( getActivity(), crime.getId() );
                //Запустить активите с усатановлеными параметрами intent
                startActivity( intent );
                //вернуть true , то есть отбразить ативити
                return true;
            //если не найден id в case-ах выполняем
            default:
                //вернуть меню родительского класса
                return super.onOptionsItemSelected( item );
        }
    }
}
