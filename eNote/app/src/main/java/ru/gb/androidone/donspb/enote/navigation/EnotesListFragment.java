package ru.gb.androidone.donspb.enote.navigation;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

import ru.gb.androidone.donspb.enote.EnoteEditorFragment;
import ru.gb.androidone.donspb.enote.MainActivity;
import ru.gb.androidone.donspb.enote.OneNoteFragment;
import ru.gb.androidone.donspb.enote.R;
import ru.gb.androidone.donspb.enote.datapart.EnoteData;
import ru.gb.androidone.donspb.enote.datapart.EnoteDataFirebaseSource;
import ru.gb.androidone.donspb.enote.datapart.EnoteDataSource;
import ru.gb.androidone.donspb.enote.datapart.EnoteDataSourceResp;
import ru.gb.androidone.donspb.enote.observe.Observer;
import ru.gb.androidone.donspb.enote.observe.Publisher;

public class EnotesListFragment extends Fragment {

    private static final int ANIM_TIME_SETTING = 100;
    private static final String ARG_INDEX_NAME = "enote";
    private static final String TAG = "enote";
    public static final String CURRENT_NOTE = "CurrentNote";
    private EnoteData edata;
    private EnoteDataSource data;
    private EnotesListAdapter adapter;
    private RecyclerView recyclerView;
    private MainNavigation navigation;
    private Publisher publisher;
    private boolean moveToFirstPosition;


    public static EnotesListFragment newInstance() {
        return new EnotesListFragment();
    }

    public EnotesListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.enotes_list_fragment, container, false);
        initView(view);
        setHasOptionsMenu(true);
        data = new EnoteDataFirebaseSource().init(new EnoteDataSourceResp() {
            @Override
            public void initialized(EnoteDataSource enoteData) {
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setDataSource(data);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onOptionsItemSelected(item);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_lines);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new EnotesListAdapter(this);
        recyclerView.setAdapter(adapter);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(ANIM_TIME_SETTING);
        animator.setRemoveDuration(ANIM_TIME_SETTING);
        recyclerView.setItemAnimator(animator);

        if (moveToFirstPosition && data.size() > 0) {
            recyclerView.scrollToPosition(0);
            moveToFirstPosition = false;
        }

        adapter.SetOnItemClickListener(new EnotesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                String[] titles = getResources().getStringArray(R.array.notes_titles);
//                String[] descr = getResources().getStringArray(R.array.notes_descriptions);

                edata = data.getEnoteData(position);
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
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.button_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onContextItemSelected(item);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    private boolean onItemSelected(int menuItemId) {
        switch (menuItemId) {
            case  R.id.toolbar_add:
                navigation.addFragment(EnoteEditorFragment.newInstance(),true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateEnoteData(EnoteData enoteData) {
                        data.addEnote(enoteData);
                        adapter.notifyItemInserted(data.size() - 1);
                        moveToFirstPosition = true;
                    }
                });
                return true;
            case R.id.action_edit:
                final int updatePosition = adapter.getMenuPosition();
                navigation.addFragment(EnoteEditorFragment.newInstance(data.getEnoteData(updatePosition)), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateEnoteData(EnoteData enoteData) {
                        data.editEnote(updatePosition, enoteData);
                        adapter.notifyItemChanged(updatePosition);
                    }
                });
                return true;
            case R.id.action_delete:
                int deletePosition = adapter.getMenuPosition();
                data.deleteEnote(deletePosition);
                adapter.notifyItemRemoved(deletePosition);
                return true;
            case R.id.toolbar_clear:
                data.clearEnotes();
                adapter.notifyDataSetChanged();
                return true;
        }
        return false;
    }
}
