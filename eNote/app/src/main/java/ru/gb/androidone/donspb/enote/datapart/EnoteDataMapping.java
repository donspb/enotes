package ru.gb.androidone.donspb.enote.datapart;

import com.google.firebase.Timestamp;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EnoteDataMapping {
    public static class Fields {
        public final static String DATE = "date";
        public final static String TITLE = "title";
        public final static String DESCRIPTION = "description";
    }

    public static EnoteData toEnoteData(String id, Map<String,Object> doc) {
        Timestamp ts = (Timestamp) doc.get(Fields.DATE);
        EnoteData result = new EnoteData((String) doc.get(Fields.TITLE),
                (String) doc.get(Fields.DESCRIPTION), ts.toDate());
        result.setId(id);
        return result;
    }

    public static Map<String, Object> toDocument(EnoteData enoteData) {
        Map<String, Object> result = new HashMap<>();
        result.put(Fields.TITLE, enoteData.getNoteTitle());
        result.put(Fields.DESCRIPTION, enoteData.getNoteDescription());
        result.put(Fields.DATE, enoteData.getDateTime());
        return result;
    }
}
