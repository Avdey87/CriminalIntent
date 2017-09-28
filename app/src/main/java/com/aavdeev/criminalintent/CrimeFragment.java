package com.aavdeev.criminalintent;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class CrimeFragment extends Fragment {
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        mCrime = new Crime();
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
        // кнопка не активна setEnabled(false)
        mDateButton.setEnabled( false );

        //Получаем ссылку на галочку
        mSolvedCheckBox = (CheckBox) v.findViewById( R.id.crime_solved );
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
