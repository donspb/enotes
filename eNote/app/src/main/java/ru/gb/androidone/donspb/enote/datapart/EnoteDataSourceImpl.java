package ru.gb.androidone.donspb.enote.datapart;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.gb.androidone.donspb.enote.R;

public class EnoteDataSourceImpl implements EnoteDataSource {

    private List<EnoteData> dataSource;
    private Resources resources;

    public EnoteDataSourceImpl(Resources resources) {
        dataSource = new ArrayList<>();
        this.resources = resources;
    }

    public EnoteDataSourceImpl init(EnoteDataSourceResp enoteDataSourceResp) {
        String[] titles = resources.getStringArray(R.array.notes_titles);
        String[] descr = resources.getStringArray(R.array.notes_descriptions);

        for (int i = 0; i < titles.length; i++) {
            dataSource.add(new EnoteData(titles[i], descr[i], Calendar.getInstance().getTime()));
        }

        if (enoteDataSourceResp != null) {
            enoteDataSourceResp.initialized(this);
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

    @Override
    public void deleteEnote(int position) {
        dataSource.remove(position);
    }

    @Override
    public void editEnote(int position, EnoteData enoteData) {
        dataSource.set(position, enoteData);
    }

    @Override
    public void addEnote(EnoteData enoteData) {
        dataSource.add(enoteData);
    }

    @Override
    public void clearEnotes() {
        dataSource.clear();
    }
}
