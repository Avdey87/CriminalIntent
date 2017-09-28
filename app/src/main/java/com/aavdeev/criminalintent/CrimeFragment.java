package com.aavdeev.criminalintent;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;

public class CrimeFragment extends Fragment {
    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    //Прив вызове CrimeFragment вызывается CrimeFragment.newInstance
    //в который передается занчение тапа UUID (индетификатор)
    public static CrimeFragment newInstance(UUID crimeId) {
        //для создания аргумента мы создаем объект Bundle
        //этот объект содержит ключ и значения
        Bundle args = new Bundle();
        //args кладем значения crimeId типа ARG_CRIME_ID
        args.putSerializable( ARG_CRIME_ID, crimeId );

        //Создаем экземпляр класса CrimeFragment
        CrimeFragment fragment = new CrimeFragment();
        //устанавливаем в экземпляр класса в качестве параметра аргумент
        fragment.setArguments( args );
        //возвращаем экземпляр класса CrimeFragment
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //в переменную записываем индетификатор полученный
        // из константы ARG_CRIME_ID
        UUID crimeId = (UUID) getArguments().getSerializable( ARG_CRIME_ID );
        //mCrime записываем id элемента списка (помещаем в переменную объект списка)
        mCrime = CrimeLab.get( getActivity() ).getCrime( crimeId );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Явное заполнение фрагмента, LayoutInflater.inflate с передачей
        //индетификатор ресурса макета.
        //Container определяет родительское представления
        // false указывает нужно ли включать заполненое представление в родителя
        View v = inflater.inflate( R.layout.fragment_crime, container, false );

        //Настройка реакции виджета EditText на ввод пользователя
        mTitleField = (EditText) v.findViewById( R.id.crime_title );
        //выводим краткое описание
        mTitleField.setText( mCrime.getTitle() );
        //Получение ссылки на EditText и установка слушателя
        //Создаем анонимный внутрений класс котороый реализует слушателя TextWatcher
        mTitleField.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //onTextChanged
            //Метод возвращает строку которая используется для задания заголовка Crime
            @Override
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mCrime.setTitle( c.toString() );
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );

        //Получаем ссылку на кнопку и задаём в текст кнопки дату
        mDateButton = (Button) v.findViewById( R.id.crime_date );
        //устанавливаем текст в кнопку
        //установить текст вызываем DateFormat.format для форматирования даты
        // вызвав getDate() у обьета Crime,mCrime, получаем текущую дату
        mDateButton.setText( DateFormat.format( "EEEE, MMM dd, yyyy", mCrime.getDate() ) );
        //Устанвливаем слушатель на кнопку
       mDateButton.setOnClickListener(new View.OnClickListener() {
           //обработчик нажатия кнопки
           @Override
           public void onClick(View v) {
               //Создаем экземпляр FragmentManager и присваеваем ему значение
               //из FragmentManager  ????
               FragmentManager fragmentManager = getFragmentManager();
               //создаем экземпляр DatePickerFragment рписваевеваем занчения хранящиеся в newInstance класса DatePickerFragment и передаем в качестве параметров дату
               DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
               //заполняем вновь созданный диалог и выводим его на экран
               //ключ выводимого дилог хрнаиться в переменной fragmentManager
               // а текстовое значение в DIALOG_DATE
               dialog.show(fragmentManager, DIALOG_DATE);
           }
       });

        //Получаем ссылку на галочку
        mSolvedCheckBox = (CheckBox) v.findViewById( R.id.crime_solved );
        //выводим состояние чек бокса
        mSolvedCheckBox.setChecked( mCrime.isSolved() );
        //Устаналиваем слушаетль на CheckBox
        mSolvedCheckBox.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //назначение флага
                //Объекту Crime,mCrime, устанавливаем значение isChecked
                mCrime.setSolved( isChecked );
            }
        } );

        return v;
    }
}
