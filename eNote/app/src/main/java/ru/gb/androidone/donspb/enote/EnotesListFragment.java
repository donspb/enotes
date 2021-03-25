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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EnotesListFragment extends Fragment {

    private static final String ARG_INDEX_NAME = "enote";
    private static final String TAG = "enote";
    public static final String CURRENT_NOTE = "CurrentNote";
    private EnoteData edata;
    private boolean isLandscape;

    public static EnotesListFragment newInstance() {

        return new EnotesListFragment();
    }

    public EnotesListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.enotes_list_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_lines);
        EnoteDataSource data = new EnoteDataSourceImpl(getResources()).init();
        initRecycleView(recyclerView, data);
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
                String[] titles = getResources().getStringArray(R.array.notes_titles);
                String[] descr = getResources().getStringArray(R.array.notes_descriptions);
                String[] date = getResources().getStringArray(R.array.notes_dates);

                edata = new
                        EnoteData(titles[position], descr[position], date[position]);
                showContent(edata);
            }
        });
    }

    private void showContent(EnoteData edata) {

        OneNoteFragment onEnote = OneNoteFragment.newInstance(edata);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            fragmentTransaction.replace(R.id.landscape_side_container, onEnote);
        else fragmentTransaction.replace(R.id.fragment_container, onEnote);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, edata);
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState: instance saved ");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            edata = savedInstanceState.getParcelable(CURRENT_NOTE);
            if (edata != null) showContent(edata);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
