package ru.gb.androidone.donspb.enote.datapart;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class EnoteDataFirebaseSource implements EnoteDataSource {

    private static final String ENOTES_COLLECTION = "eNotes";

    private FirebaseFirestore store = FirebaseFirestore.getInstance();

    private CollectionReference collection = store.collection(ENOTES_COLLECTION);

    private List<EnoteData> enotesData = new ArrayList<EnoteData>();

    @Override
    public EnoteDataSource init(EnoteDataSourceResp enoteDataSourceResp) {
        collection.orderBy(EnoteDataMapping.Fields.DATE, Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            enotesData = new ArrayList<EnoteData>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> doc = document.getData();
                                String id = document.getId();
                                EnoteData enoteData = EnoteDataMapping.toEnoteData(id, doc);
                                enotesData.add(enoteData);
                            }

                            enoteDataSourceResp.initialized(EnoteDataFirebaseSource.this);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: ", e);
            }
        });
        return this;
    }

    @Override
    public EnoteData getEnoteData(int position) {
        return enotesData.get(position);
    }

    @Override
    public int size() {
        if (enotesData == null) {
            return 0;
        }
        return enotesData.size();
    }

    @Override
    public void deleteEnote(int position) {
        collection.document(enotesData.get(position).getId()).delete();
        enotesData.remove(position);
    }

    @Override
    public void editEnote(int position, EnoteData enoteData) {
        String id = enoteData.getId();
        collection.document(id).set(EnoteDataMapping.toDocument(enoteData));
    }

    @Override
    public void addEnote(final EnoteData enoteData) {
        collection.add(EnoteDataMapping.toDocument(enoteData)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                enoteData.setId(documentReference.getId());
            }
        });
    }

    @Override
    public void clearEnotes() {
        for (EnoteData enoteData : enotesData) {
            collection.document(enoteData.getId()).delete();
        }
        enotesData = new ArrayList<EnoteData>();
    }
}
