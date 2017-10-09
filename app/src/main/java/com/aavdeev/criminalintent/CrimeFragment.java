package com.aavdeev.criminalintent;


import android.app.Activity;
import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CrimeFragment extends Fragment {
    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private static final String DIALOG_TIME = "time";
    private static final int REQUEST_TIME = -1;
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mDeleteButtom;
    private Button mTimeButton;
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
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
        updateDate();
        //Устанвливаем слушатель на кнопку
        mDateButton.setOnClickListener( new View.OnClickListener() {
            //обработчик нажатия кнопки
            @Override
            public void onClick(View v) {
                //Создаем экземпляр FragmentManager и присваеваем ему значение
                //из FragmentManager  ????
                FragmentManager fragmentManager = getFragmentManager();
                //создаем экземпляр DatePickerFragment рписваевеваем занчения хранящиеся в newInstance класса DatePickerFragment и передаем в качестве параметров дату
                DatePickerFragment dialog = DatePickerFragment.newInstance( mCrime.getDate() );
                //устанавливает целевым фрагментом текущий
                //REQUEST_DATE хранит данные он полученных данных о дате
                dialog.setTargetFragment( CrimeFragment.this, REQUEST_DATE );
                //заполняем вновь созданный диалог и выводим его на экран
                //ключ выводимого дилог хрнаиться в переменной fragmentManager
                // а текстовое значение в DIALOG_DATE
                dialog.show( fragmentManager, DIALOG_DATE );
            }
        } );

        //Получаем ссылку на кнопку и задаём в текст кнопки время
        mTimeButton = (Button) v.findViewById( R.id.crime_time );
        //обновление времени
        updateTime();
        //установить слушатель на кнопку время
        mTimeButton.setOnClickListener( new View.OnClickListener() {
            //реализация нажатой кнопки
            @Override
            public void onClick(View v) {
                //создаем экземпляр FragmentManager
                //в который устанвливаем активити которая была получена
                // из getSupportFragmentManager
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();

                //создаем экземпляр TimePickerFragment
                // присваеваем ему значение TimePickerFragment
                //полученные из интстенс , в ингстенс паредаем дату ( mCrime.getDate())
                TimePickerFragment dialog = TimePickerFragment
                        .newInstance( mCrime.getDate() );
                //устанавливаем в экземпляр TimePickerFragment
                // Текущей фрагмент CrimeFragment.this- ключ, и значение REQUEST_TIME
                dialog.setTargetFragment( CrimeFragment.this, REQUEST_TIME );
                //показать диалог с 2 переданными параметрами ( fm, DIALOG_TIME )
                //фрагмент и его значение.
                dialog.show( fm, DIALOG_TIME );
            }
        } );

        mDeleteButtom = (Button) v.findViewById( R.id.delete_crime );

        mDeleteButtom.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Создаем экхземпляр объекта CrimeLab
                CrimeLab crime = CrimeLab.get( getActivity() );

                //вызываем у объекта deleteCrime и пердаем ему в качестве параметра
                // id текущего объекта в списке
                crime.deleteCrime( mCrime.getId() );
                crime.getCrimes();
                //завершаем текущую активити
                getActivity().finish();


            }
        } );
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



    private void updateDate(CharSequence format) {
        mDateButton.setText( format );
    }

    //Метод для возвращенгия данных целевому фрагменту
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //если данны соответствуют текущим данным активити то про ретерн
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        //если данные равны данны REQUEST_TIME
        if (requestCode == REQUEST_TIME
                // и получаем дата из TimePickerFragment не равна нулю
                && data.getSerializableExtra
                ( TimePickerFragment.EXTRA_TIME ) != null) {
//устанавливаем времы в значение получаемо из TimePickerFragment
            Date date = (Date) data.getSerializableExtra
                    ( TimePickerFragment.EXTRA_TIME );
//устанвливаем время в объект Crime
            mCrime.setTime( date );
            //обновить время
            updateTime();
        }

//если requestCode соответствует  REQUEST_DATE и дата получаемая из DatePickerFragment
        //не равна нулю
        if (requestCode == REQUEST_DATE &&
                data.getSerializableExtra
                        ( DatePickerFragment.EXTRA_DATE ) != null) {
            //Устанвливаем дату полученую из DatePickerFragment
            Date date = (Date) data.getSerializableExtra( DatePickerFragment.EXTRA_DATE );
            //Создаем новую Calendar переменную в которую записываем инстенс
            //получаемый из календаря
            Calendar calendar = Calendar.getInstance();
            //устанвливаем в календарь время
            //из DatePickerFragment с переданным параметром date
            calendar.setTime( date );
            //устанвливаем в экземпляр оъекта Crime значение из calendar
            mCrime.setDate( date );
            updateDate();
        }
    }

    //Метод обновления даты в заданном формате
    private void updateDate() {
        //устанавливаем текст в кнопку
        //установить текст вызываем DateFormat.format для форматирования даты
        // вызвав getDate() у обьета Crime,mCrime, получаем текущую дату
        mDateButton.setText( DateFormat.format( "EEEE, MMM dd, yyyy", mCrime.getDate() ) );
    }

    //Метод обновления времени
    private void updateTime() {
        //устанавливаем формат выводы даты
        SimpleDateFormat tf = new SimpleDateFormat( "kk:mm" );
        //предаем установленный формат в текст кнопки
        mTimeButton.setText( tf.format( mCrime.getDate() ) );

    }
}
