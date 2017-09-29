package com.aavdeev.criminalintent;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {
    private static final String ARG_DATE = "date";
    public static final String EXTRA_DATE = "com.aavdeev.criminalintent";
    private DatePicker mDatePicker;

    public static DatePickerFragment newInstance(Date date) {
        //для создания аргумента мы создаем объект Bundle
        //этот объект содержит ключ и значения
        Bundle args = new Bundle();
        //кладем в аргумент данные: дату и аргумент
        args.putSerializable( ARG_DATE, date );

        //создаем DatePickerFragment объект  новый
        DatePickerFragment fragment = new DatePickerFragment();
        //устанавливаем в него значение аргумента, то есть дату
        fragment.setArguments( args );
        //возвращаем fragment (дату)
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //создаем Date переменную и записываем в нее Date данные которые получаем из аргумента ARG_DATE
        Date date = (Date) getArguments().getSerializable( ARG_DATE );

        //создаем Calendar переменную и устанавливаем в нее Calendar полученные из getInstance
        Calendar calendar = Calendar.getInstance();
        //устанавливаем время, парметром указываем переменную date в хрониться дата
        calendar.setTime( date );
        //создаем 3 переременных для отображения года, месяца и дня
        int year = calendar.get( Calendar.YEAR );
        int month = calendar.get( Calendar.MONTH );
        int day = calendar.get( Calendar.DAY_OF_MONTH );
        //создаем экземпляр типа View. Создаем из layout файла элемент вью
        //указываем из какого фала барть данные для создания View(dialog_date)
        View v = LayoutInflater.from( getActivity() )
                //inflate метод который создает View из xml
                .inflate( R.layout.dialog_date, null );

        mDatePicker = (DatePicker) v.findViewById( R.id.dialog_date_date_picker );
        mDatePicker.init( year, month, day, null );

        //возвращаем новый  созданый диалог AlertDialog.Builder
        return new AlertDialog.Builder( getActivity() )
                .setView( v )
                // в который устанавливаем заголовк из строики date_picker_title
                .setTitle( R.string.date_picker_title )
                //устанвливаем кнопку с надписью "ОК"
                //и устанавливаем слушатель на данный диалог
                .setPositiveButton( android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            //создаем обработчик события нажатая кнопка
                            //в него передаем данные выбранные из диалога
                            // и что было выбрано
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //получаем год из DatePicker
                                int year = mDatePicker.getYear();
                                //получаем месяц из DatePicker
                                int month = mDatePicker.getMonth();
                                //получаем день из DatePicker
                                int day = mDatePicker.getDayOfMonth();
                                //Создаем Date переменную в которую устанавливаем
                                //полученные из DatePicker данные и получаем время
                                Date date = new GregorianCalendar( year, month, day )
                                        .getTime();
                                //отправляем все полцченные данные в sendResult
                                sendResult( Activity.RESULT_OK, date );
                            }
                        } )
                // выполняем действие создать  .create()
                .create();
    }

    //данный метод создает intent помещает в него дату как дополнение intent
    //
    private void sendResult(int resultCode, Date date) {
        //если получаем фрагмент пустой вернуться
        if (getTargetFragment() == null) {
            return;
        }
        //создаем новый интент
        Intent intent = new Intent();
        //помещаем в интент дату
        intent.putExtra( EXTRA_DATE, date );
        //получаем данные их фрагмента
        getTargetFragment()
                //
                .onActivityResult( getTargetRequestCode(), resultCode, intent );
    }

}
