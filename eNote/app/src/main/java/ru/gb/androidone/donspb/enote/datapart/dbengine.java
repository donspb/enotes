package ru.gb.androidone.donspb.enote.datapart;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class dbengine {

    private static final String DATABASE_FILENAME = "enotebase";
    private static final String DELIMITER = "|+|+|";

    protected void writeData(EnoteData enoteData, Context context) {
        String stringToWrite = enoteData.getNoteTitle() + DELIMITER + enoteData.getNoteDescription() +
            DELIMITER + enoteData.getDateTime().toString();
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(context.openFileOutput(DATABASE_FILENAME, Context.MODE_APPEND)));
            bufferedWriter.write(stringToWrite);
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected EnoteData readData(int position, Context context) {
        EnoteData enoteData;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    context.openFileInput(DATABASE_FILENAME)));
            String str = "";
            int i = 0;
            boolean eof = false;
            while (((str = bufferedReader.readLine()) != null)) {
                String[] dataarray = str.split(DELIMITER);
                if (i == position) {
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = format.parse(dataarray[2]);
                    return new EnoteData(dataarray[0], dataarray[1], date);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void changeData(int position, EnoteData enoteData) {

    }
}
