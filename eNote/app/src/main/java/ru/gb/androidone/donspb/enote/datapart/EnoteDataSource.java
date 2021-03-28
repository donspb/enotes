package ru.gb.androidone.donspb.enote.datapart;

public interface EnoteDataSource {
    EnoteData getEnoteData(int position);
    int size();
    void deleteEnote(int position);
    void editEnote(int position, EnoteData enoteData);
    void addEnote(EnoteData enoteData);
    void clearEnotes();
}
