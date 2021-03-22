package ru.gb.androidone.donspb.enote;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EnotesListFragment extends Fragment {

    private static final String ARG_INDEX_NAME = "enote";
    public static final String CURRENT_NOTE = "CurrentNote";
    private EnoteData edata;
    private boolean isLandscape;

    public static EnotesListFragment newInstance() {
        return new EnotesListFragment();
    }

//    public EnotesListFragment() {
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.enotes_list_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_lines);
        EnoteDataSource data = new EnoteDataSourceImpl(getResources()).init();
        initRecycleView(recyclerView, data);
//        loadData(view);
        return view;
    }

    private void initRecycleView(RecyclerView recyclerView, EnoteDataSource data) {
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        EnotesListAdapter adapter = new EnotesListAdapter(data);
        recyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener(new EnotesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(),
                        String.format("Позиция - %d", position), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
//
//        if (savedInstanceState != null) {
//            edata = savedInstanceState.getParcelable(CURRENT_NOTE);
//            showContent(edata);
//        }
//    }
//
//
//
//    private void loadData(View view) {
//        LinearLayout layoutView = (LinearLayout) view;
//
//        String[] titles = getResources().getStringArray(R.array.notes_titles);
//        String[] descr = getResources().getStringArray(R.array.notes_descriptions);
//        String[] date = getResources().getStringArray(R.array.notes_dates);
//
//        LayoutInflater ltInflater = getLayoutInflater();
//
//        for (int i = 0; i < titles.length; i++) {
//            View item = ltInflater.inflate(R.layout.enotes_list_item, layoutView, false);
//            TextView tv = item.findViewById(R.id.list_item_tv);
//            tv.setText(titles[i]);
//            layoutView.addView(item);
//            int ti = i;
//            tv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    edata = new EnoteData(titles[ti], descr[ti], date[ti]);
//                    showContent(edata);
//                }
//            });
//        }
//    }
//
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        outState.putParcelable(CURRENT_NOTE, edata);
//        super.onSaveInstanceState(outState);
//    }
//
//    private void showContent(EnoteData edata) {
//
//        OneNoteFragment onEnote = OneNoteFragment.newInstance(edata);
//
//        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container, onEnote);
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        fragmentTransaction.commit();
//    }
}