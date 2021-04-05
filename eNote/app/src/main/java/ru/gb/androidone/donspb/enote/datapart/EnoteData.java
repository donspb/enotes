package ru.gb.androidone.donspb.enote.datapart;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class EnoteData implements Parcelable {

    private String id;
    private String mNoteTitle;
    private String mNoteDescription;
    private Date mDateTime;

    public EnoteData(String noteTitle, String noteDescription, Date noteDate) {
        this.mNoteTitle = noteTitle;
        this.mNoteDescription = noteDescription;
        this.mDateTime = noteDate;
    }

    protected EnoteData (Parcel in) {
        mNoteTitle = in.readString();
        mNoteDescription = in.readString();
        mDateTime = new Date(in.readLong());
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

    public Date getDateTime() {
        return mDateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        dest.writeLong(mDateTime.getTime());
    }
}
