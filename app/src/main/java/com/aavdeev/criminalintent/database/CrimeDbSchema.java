package com.aavdeev.criminalintent.database;

//Создаем класс для базы данных
public class CrimeDbSchema {
    //создаем внутрений класс для создания имя базы данных
    public static final class CrimeTable {
        public static final String NAME = "crime";

        //внутренний класс для созданиея полей БД
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT = "suspect";

        }

    }
}

