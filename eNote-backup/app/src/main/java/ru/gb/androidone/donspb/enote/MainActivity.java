package ru.gb.androidone.donspb.enote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

//    private EnoteData[] tempDataSet;
    private static final int DATA_SET_SIZE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        setDataTemp();
    }

//    private void setDataTemp() {
//        tempDataSet = new EnoteData[DATA_SET_SIZE];
//        for (int i = 0; i < DATA_SET_SIZE; i++) {
//            tempDataSet[i].setNoteTitle("Note " + i);
//            tempDataSet[i].setNoteDescription("This is the body (also known as Description) of Note " + i);
//            tempDataSet[i].setDateTime();
//        }
//    }
}