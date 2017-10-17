package com.aavdeev.criminalintent;


import java.util.Date;
import java.util.UUID;

public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private String mSuspect;//переменная для хранения имени подозреваемого
    private Date time;
    private String mContact;
    private String mPhoneNumber;

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getContact() {
        return mContact;
    }

    public Crime() {
        this(UUID.randomUUID());
    }

    public Crime(UUID id) {
        //Генерируеем уникальный индефикатор
        mId = id;
        mDate = new Date();
    }

    //пполучить имя подозреваемого
    public String getSuspect() {
        return mSuspect;
    }

    //установить имя подозреваемого
    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    public void setContact(String contact) {
        mContact = contact;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
