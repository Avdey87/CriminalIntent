package com.aavdeev.criminalintent;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class DatePickerFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       //возвращаем новый  созданый диалог AlertDialog.Builder
        return new AlertDialog.Builder( getActivity() )
                // в который устанавливаем заголовк из строики date_picker_title
                .setTitle( R.string.date_picker_title )
                //устанвливаем кнопку с надписью "ОК"
                // PositiveButton подтверждение информации в диалоговом окне
                .setPositiveButton( android.R.string.ok, null )
                // выполняем действие создать  .create()
                .create();
    }

}
