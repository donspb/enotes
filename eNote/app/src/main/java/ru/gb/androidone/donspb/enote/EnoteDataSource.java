package ru.gb.androidone.donspb.enote;

public interface EnoteDataSource {
    EnoteData getEnoteData(int position);
    int size();
}
