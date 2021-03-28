package ru.gb.androidone.donspb.enote;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import ru.gb.androidone.donspb.enote.datapart.EnoteData;

public class OneNoteFragment extends Fragment {

    private static final String ARG_INDEX_NAME = "enote";
    private EnoteData endata;

    public OneNoteFragment() {
        // Required empty public constructor
    }

    public static OneNoteFragment newInstance(EnoteData param1) {
        OneNoteFragment fragment = new OneNoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_INDEX_NAME, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            endata = getArguments().getParcelable(ARG_INDEX_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_one_note, container, false);

        TextView tv = view.findViewById(R.id.one_note_title);
        tv.setText(endata.getNoteTitle());

        tv = view.findViewById(R.id.one_note_descr);
        tv.setText(endata.getNoteDescription());

        tv = view.findViewById(R.id.one_note_date);
        tv.setText(new SimpleDateFormat("dd-MM-yyyy").format(endata.getDateTime()));


//        TextView tv = new TextView(getContext());
//        tv.setText(endata.getNoteTitle());
//        tv.setTextSize(30);
//        view.addView(tv);
//
//        tv = new TextView(getContext());
//        tv.setText(endata.getNoteDescription());
//        tv.setTextSize(20);
//        view.addView(tv);
//
//        tv = new TextView(getContext());
//        tv.setText(endata.getDateTime());
//        tv.setTextSize(10);
//        view.addView(tv);

        return view;
    }

}