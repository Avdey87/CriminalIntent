package com.aavdeev.criminalintent;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;

public class CrimePhotoDialogFragment extends DialogFragment {
    private static final String ARG_PHOTO_FILE = "photoFile";
    private ImageView mPhotoView;
    private File mPhotoFile;

   //создаем инстенс для CrimePhotoDialogFragment (метка для вызова фрагмента)
    public static CrimePhotoDialogFragment newInstance(File photoFile) {
        //для создания аргумента мы создаем объект Bundle
        //этот объект содержит ключ и значения
        Bundle args = new Bundle();
        //кладем в аргумент 2 параметра ARG_PHOTO_FILE и сам файл photoFile
        args.putSerializable( ARG_PHOTO_FILE, photoFile );

        //создаем переменную CrimePhotoDialogFragment типа
        CrimePhotoDialogFragment fragment = new CrimePhotoDialogFragment();
        //устанвливаем в нее занчения хранящиеся в fragment
        fragment.setArguments( args );
//возращаем экземпляр класс CrimePhotoDialogFragment
        return fragment;
    }


    //Метод для создание вью
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       //в переменную mPhotoFile устанавливаем значение ARG_PHOTO_FILE (информация о фотофайле, путь к нему)
        mPhotoFile = (File) getArguments().getSerializable( ARG_PHOTO_FILE );
//создаем переменную типв View и наполняем ее xml файлом dialog_crime_photo и начальным
// значением false
        View view = inflater.inflate( R.layout.dialog_crime_photo, container, false );
// создаем ImageView переменную и кладем в нее адрес ImageView в xml файле dialog_crime_photo
        //id для поля отображения картинки
        mPhotoView = (ImageView) view.findViewById( R.id.crime_photo_view );
//если фала нет
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            //ничего не отображаем
            mPhotoView.setImageDrawable( null );
        }
        //иначе ввыводим картинку
        else {
            Bitmap bitmap = PictureUtils.getScaledBitmap( mPhotoFile.getPath(), getActivity() );
            mPhotoView.setImageBitmap( bitmap );
        }
        //возвращаем вью с переданными выше параметрами
        return view;
    }
}
