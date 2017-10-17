package com.aavdeev.criminalintent;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ShareCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
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
    private static final int REQUEST_CONTACT = 1;
    private static final int REQUEST_PHOTO = 2;

    private Crime mCrime;
    private EditText mTitleField;
    private File mPhotoFile;
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mSolvedCheckBox;
    private Button mReportButton;
    private Button mSuspectButton;
    private Button mCallButton;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;


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
        mPhotoFile = CrimeLab.get( getActivity() ).getPhotoFile( mCrime );
        setHasOptionsMenu( true );
    }

    //обновляем список mCrime
    @Override
    public void onPause() {
        super.onPause();

        CrimeLab.get( getActivity() ).updateCrime( mCrime );
    }


    //добавляем меню удалить в CrimeFragment (2 активити)
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu( menu, inflater );
        inflater.inflate( R.menu.fragment_crime_menu, menu );
    }

    //обработка нажатие на кнопку удалить в меню CrimeFragment(2 активити)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_crime_menu:
                //Получаем идетификатор активити (инидетификатор crime -запись в списке)
                //и удаляем запись в списке
                CrimeLab.get( getActivity() ).deleteCrime( mCrime );
                //завершаем текущую активити
                getActivity().finish();
            default:
                return super.onOptionsItemSelected( item );
        }
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
//определяем кнопку
        mReportButton = (Button) v.findViewById( R.id.crime_report );
        //Устанавливаем на кнопку слушателя
        mReportButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //записываем в интент переменную
                Intent i = new Intent( Intent.ACTION_SEND );
                //присваеваем тип переменной
                i.setType( "text/plain" );
                //записываем в переменную значение из интента и отчет
                i.putExtra( Intent.EXTRA_TEXT, getCrimeReport() );
                //записываем в переменную объект строка
                i.putExtra( Intent.EXTRA_SUBJECT, getString( R.string.crime_report_subject ) );
                //запускаем ативити передав ей в качестве параметра переменную
                i = Intent.createChooser( i, getString( R.string.send_report ) );
                startActivity( i );
            }
        } );


        //записываем в pickContact значение которое будем передавать в ActivityForResult
        //для поиска пирложений на устройстве которые могут выполнить действие  CONTENT_URI
        //Поиск контактов на телефоне
        final Intent pickContact = new Intent( Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI );


        //определяем кнопку
        mSuspectButton = (Button) v.findViewById( R.id.crime_suspect );
        //устанавливаем слушаетель на кнопку
        mSuspectButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Запускаем поиск активити с экшеном  pickContact
                startActivityForResult( pickContact, REQUEST_CONTACT );
            }
        } );
        //если значение Suspect(преступник) не пустое
        if (mCrime.getSuspect() != null) {
            //устанавливаем текст в кнопку взятый из mCrime.getSuspect()
            mSuspectButton.setText( mCrime.getSuspect() );
        }
//записываем в переменную типа PackageManager данные
        PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager.resolveActivity( pickContact,
                //флаг поиска приложения MATCH_DEFAULT_ONLY
                PackageManager.MATCH_DEFAULT_ONLY ) == null) {
            mSuspectButton.setEnabled( false );
        }

        final Intent pickContactCall = new Intent( Intent.ACTION_CALL,
                Uri.parse( ContactsContract.Contacts.LOOKUP_KEY ) );

        mCallButton = (Button) v.findViewById( R.id.crime_call );
        mCallButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Intent.ACTION_DIAL, Uri.parse( "tel:" + mCrime.getPhoneNumber() ) );
                startActivity( intent );


            }
        } );

        mPhotoButton = (ImageButton) v.findViewById( R.id.crime_camera );
        final Intent captureImage = new Intent
                ( MediaStore.ACTION_IMAGE_CAPTURE );
        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity( packageManager ) != null;
        mPhotoButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureImage,REQUEST_PHOTO  );
            }
        } );
        mPhotoView = (ImageView) v.findViewById( R.id.crime_photo );

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
        } else if (requestCode == REQUEST_CONTACT && data != null) {
            Uri contactUri = data.getData();
            //Определение полей, значение которыйх
            //должно быть возвращены запросам
            String[] queryFields = new String[]{
                    ContactsContract.Contacts.DISPLAY_NAME
            };
            //Выполнение запроса - contactUri здесь выполняет функции
            //условия "where" хде
            Cursor c = getActivity().getContentResolver()
                    .query( contactUri, queryFields, null, null, null );
            try {
                //проверка полученных результатов
                c.moveToFirst();
                String suspect = c.getString( 0 );
                mCrime.setSuspect( suspect );
                mSuspectButton.setText( suspect );
            } finally {
                c.close();
            }
        }
    }

    //Метод обновления даты в заданном формате
    private void updateDate() {
        //устанавливаем текст в кнопку
        //установить текст вызываем DateFormat.format для форматирования даты
        // вызвав getDate() у обьета Crime,mCrime, получаем текущую дату
        mDateButton.setText( DateFormat.format( "EEEE, MMM dd, yyyy", mCrime.getDate() ) );
    }

    //модот для создание отчета из 4 строк объедененных в одну
    private String getCrimeReport() {
        //создаем пустую строку solvedString
        String solvedString = null;
        //если установлена галочка тогда
        if (mCrime.isSolved()) {
            //брем значение crime_report_solved из файла string
            solvedString = getString( R.string.crime_report_solved );
        }
        //елси галочка отсутсвует тода берем значение crime_report_unsolved
        //из файла string
        else {
            solvedString = getString( R.string.crime_report_unsolved );
        }
        //Создаем строковую переменную dateFormat
        String dateFormat = "EEE, MMM, dd";
        //записываем в нее дату в формате "EEE, MMM, dd"
        String dateString = DateFormat.format( dateFormat, mCrime.getDate() ).toString();
//создаме строковую переменную для записи в нее преступника
        String suspect = mCrime.getSuspect();
        //если поле преступник пустое
        if (suspect == null) {
            //в переменную записываем занчине crime_report_no_suspect
            //из файла string
            suspect = getString( R.string.crime_report_no_suspect );
        }
        //если в поле есть значение, записываем щанчение crime_report_suspect
        //из файла string + suspect(имя преступника)
        else {
            suspect = getString( R.string.crime_report_suspect, suspect );
        }

        //Создаем строковую переменную для создание отчета
        //в нее пишим значение crime_report из файла string
        //так же шапку отчета, дату , раскрыто или нет, преступник
        String report = getString( R.string.crime_report, mCrime.getTitle(), dateString, solvedString, suspect );
        //возвращаем отчет (строковое значение)
        return report;
    }

    //Метод обновления времени
    private void updateTime() {
        //устанавливаем формат выводы даты
        SimpleDateFormat tf = new SimpleDateFormat( "kk:mm" );
        //предаем установленный формат в текст кнопки
        mTimeButton.setText( tf.format( mCrime.getDate() ) );

    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable( null );
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity()
            );
            mPhotoView.setImageBitmap( bitmap );
        }
    }


}
