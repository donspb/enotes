package ru.gb.androidone.donspb.enote;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

import ru.gb.androidone.donspb.enote.datapart.EnoteData;
import ru.gb.androidone.donspb.enote.observe.Publisher;

import static android.content.ContentValues.TAG;

public class EnoteEditorFragment extends Fragment {

    private static final String ARG_PARAM_ENOTEDATA = "enoteDataP";

    private EnoteData mEnoteData;
    private Publisher mPublisher;

    private TextInputEditText title;
    private TextInputEditText description;
    private DatePicker datePicker;
    private Button btnSave;

    public EnoteEditorFragment() {
        // Required empty public constructor
    }

    public static EnoteEditorFragment newInstance(EnoteData enoteData) {
        EnoteEditorFragment fragment = new EnoteEditorFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM_ENOTEDATA, enoteData);
        fragment.setArguments(args);
        return fragment;
    }

    public static EnoteEditorFragment newInstance() {
        EnoteEditorFragment fragment = new EnoteEditorFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEnoteData = getArguments().getParcelable(ARG_PARAM_ENOTEDATA);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        mPublisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        mPublisher = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_enote_editor, container, false);
        initView(view);
        if (mEnoteData != null) {
            populateView();
        }
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        mEnoteData = collectEnoteData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPublisher.notifySingle(mEnoteData);
    }

    private EnoteData collectEnoteData() {
        String title = this.title.getText().toString();
        String description = this.description.getText().toString();
        Date date = getDateFromDatePicker();
        if (mEnoteData != null) {
            EnoteData result = new EnoteData(title, description, date);
            result.setId(mEnoteData.getId());
            return result;
        }
        else {
            return new EnoteData(title, description, date);
        }
    }

    private Date getDateFromDatePicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, this.datePicker.getYear());
        calendar.set(Calendar.MONTH, this.datePicker.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        return calendar.getTime();
    }

    private void initView(View view) {
        title = view.findViewById(R.id.input_title);
        description = view.findViewById(R.id.input_descr);
        datePicker = view.findViewById(R.id.input_date);
        btnSave = view.findViewById(R.id.save_button);

        btnSave.setOnClickListener((View v) -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private void populateView() {
        title.setText(mEnoteData.getNoteTitle());
        description.setText(mEnoteData.getNoteDescription());
        initDatePicker(mEnoteData.getDateTime());
    }

    private void initDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }
}