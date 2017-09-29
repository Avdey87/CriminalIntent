package com.aavdeev.criminalintent;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

//Создаем класс для создание фрагмента с временим


public class TimePickerFragment extends DialogFragment {
    //Создаем строковую константу для хранения времени, досту к константе публичный
    public static final String EXTRA_TIME = "com.aavdeev.crimeintent.time";
    //Создаме константу для аргумента
    private static final String ARG_TIME = "time";
    //Создаем  TimePicker объект
    private Date mTime;

    //Создаем Instance для хрениня данных с аргументам
    public static TimePickerFragment newInstance(Date time) {
        //Создаем свзываемый объект аргумента
        Bundle args = new Bundle();
        //устанавливаем в даный объект занчения текущее
        args.putSerializable( ARG_TIME, time );

        //создаем экземпляр класса TimePickerFragment
        TimePickerFragment fragment = new TimePickerFragment();
        //устанвливаем в данный экземпляр аргументы
        fragment.setArguments( args );
        //возвращаем экземпляр TimePickerFragment с аргументами
        return fragment;
    }

    //данный метод создает intent помещает в него дату как дополнение inten
    private void sendResult(int resultCode) {
//если получаем фрагмент пустой вернуться
        if (getTargetFragment() == null)
            return;
        //создаем новый интент
        Intent intent = new Intent();
        //помещаем в интент дату
        intent.putExtra( EXTRA_TIME, mTime );
        //получаем данные их фрагмента
        getTargetFragment()
                .onActivityResult( getTargetRequestCode(), resultCode, intent );
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //создаем Date переменную и записываем в нее Date данные которые получаем из аргумента ARG_TIME
        mTime = (Date) getArguments().getSerializable( ARG_TIME );

        //создаем Calendar переменную и устанавливаем в нее Calendar полученные из getInstance
        Calendar calendar = Calendar.getInstance();
        //устанавливаем время, парметром указываем переменную date в хрониться дата
        calendar.setTime( mTime );
        //создаем 2 переменных час, минуты
        int hour = calendar.get( Calendar.HOUR );
        int minute = calendar.get( Calendar.MINUTE );

        //создаем экземпляр типа View. Создаем из layout файла элемент вью
        //указываем из какого фала барть данные для создания View(dialog_time)
        //inflate метод который создает View из xml
        View v = getActivity().getLayoutInflater()
                .inflate( R.layout.dialog_time, null );

        //Создаем экземпляр TimePicker и присваеваем ей настройки из xml файла по id
        TimePicker timePicker = (TimePicker) v.findViewById( R.id.dialog_time_time_picker );
        //установить час
        timePicker.setCurrentHour( hour );
        //установить минуты
        timePicker.setCurrentMinute( minute );
        //устанавливаем слушаетль на timePicker который отслеживает изменения времени
        timePicker.setOnTimeChangedListener( new TimePicker.OnTimeChangedListener() {
            //отслеживание изменение в TimePicker вью в 2 обектах, часы и минуты
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                //присваеваем mTime текуще время
                mTime.setHours( hourOfDay );
                mTime.setMinutes( minute );
                //получаем время из getArguments с установлеными параметрами состояния
                //EXTRA_TIME-ключ, mTime- значение
                getArguments().putSerializable( EXTRA_TIME, mTime );
            }
        } );

        //возвращаем новый  созданый диалог AlertDialog.Builder
        return new android.app.AlertDialog.Builder( getActivity() )
                .setView( v )
                // в который устанавливаем заголовк из строики time_picker_title
                .setTitle( R.string.time_picker_title )
                //устанвливаем кнопку с надписью "ОК"
                //и устанавливаем слушатель на данный диалог
                .setPositiveButton( android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            //создаем обработчик события нажатая кнопка
                            //в него передаем данные выбранные из диалога
                            // и что было выбрано
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult( Activity.RESULT_OK );
                            }
                        } )
                .create();

    }
}
