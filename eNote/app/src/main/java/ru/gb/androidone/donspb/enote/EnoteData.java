package ru.gb.androidone.donspb.enote;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EnoteData implements Parcelable {

    private String mNoteTitle;
    private String mNoteDescription;
    private String mDateTime;

    public EnoteData(String noteTitle, String noteDescription, String noteDate) {
        this.mNoteTitle = noteTitle;
        this.mNoteDescription = noteDescription;
        this.mDateTime = noteDate;
    }

    protected EnoteData (Parcel in) {
        mNoteTitle = in.readString();
        mNoteDescription = in.readString();
        mDateTime = in.readString();
    }


    public static final Creator<EnoteData> CREATOR = new Creator<EnoteData>() {
        @Override
        public EnoteData createFromParcel(Parcel in) {
            return new EnoteData(in);
        }

        @Override
        public EnoteData[] newArray(int size) {
            return new EnoteData[size];
        }
    };

    public String getNoteTitle() {
        return mNoteTitle;
    }

    public String getNoteDescription() {
        return mNoteDescription;
    }

    public String getDateTime() {
        return mDateTime;
    }

//    private void setDateTime() {
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date();
//        mDateTime = df.format(mDateTime);
//    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mNoteTitle);
        dest.writeString(mNoteDescription);
        dest.writeString(mDateTime);
    }
}
