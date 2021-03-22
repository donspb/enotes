package ru.gb.androidone.donspb.enote;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.gb.androidone.donspb.enote.EnoteData;

public class EnotesFragment extends Fragment {

    private static final String ARG_INDEX_NAME = "enote";
    public static final String CURRENT_NOTE = "CurrentNote";
    private EnoteData edata;
    private boolean isLandscape;

    public EnotesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            edata = savedInstanceState.getParcelable(CURRENT_NOTE);
            showContent(edata);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_enotes, container, false);
        loadData(view);
        return view;
    }

    private void loadData(ViewGroup view) {
        String[] titles = getResources().getStringArray(R.array.notes_titles);
        String[] descr = getResources().getStringArray(R.array.notes_descriptions);
        String[] date = getResources().getStringArray(R.array.notes_dates);

        for (int i = 0; i < titles.length; i++) {
            TextView tv = new TextView(getContext());
            tv.setText(titles[i]);
            tv.setTextSize(30);
            view.addView(tv);
            int ti = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edata = new EnoteData(titles[ti], descr[ti], date[ti]);
                    showContent(edata);
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, edata);
        super.onSaveInstanceState(outState);
    }

    private void showContent(EnoteData edata) {

        OneNoteFragment onEnote = OneNoteFragment.newInstance(edata);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, onEnote);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
}