package ru.gb.androidone.donspb.enote;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class EnoteDataSourceImpl implements EnoteDataSource {

    private List<EnoteData> dataSource;
    private Resources resources;

    public EnoteDataSourceImpl(Resources resources) {
        dataSource = new ArrayList<>();
        this.resources = resources;
    }

    public EnoteDataSourceImpl init() {
        String[] titles = resources.getStringArray(R.array.notes_titles);
        String[] descr = resources.getStringArray(R.array.notes_descriptions);
        String[] date = resources.getStringArray(R.array.notes_dates);

        for (int i = 0; i < titles.length; i++) {
            dataSource.add(new EnoteData(titles[i], descr[i], date[i]));
        }
        return this;
    }

    @Override
    public EnoteData getEnoteData(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size() {
        return dataSource.size();
    }
}
