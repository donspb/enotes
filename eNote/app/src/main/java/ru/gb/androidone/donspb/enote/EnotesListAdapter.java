package ru.gb.androidone.donspb.enote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EnotesListAdapter extends RecyclerView.Adapter<EnotesListAdapter.ViewHolder> {

    private EnoteDataSource dataSource;
    private OnItemClickListener itemClickListener;

    public EnotesListAdapter(EnoteDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public EnotesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.enotes_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EnotesListAdapter.ViewHolder holder, int position) {
        holder.setData(dataSource.getEnoteData(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView descr;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            View.OnClickListener onItemClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            };

            title = itemView.findViewById(R.id.list_title_field);
            descr = itemView.findViewById(R.id.list_descr_field);
            date = itemView.findViewById(R.id.list_date_field);

            title.setOnClickListener(onItemClickListener);
            descr.setOnClickListener(onItemClickListener);
            date.setOnClickListener(onItemClickListener);
        }

        public void setData(EnoteData enoteData) {
            title.setText(enoteData.getNoteTitle());
            descr.setText(enoteData.getNoteDescription());
            date.setText(enoteData.getDateTime());
        }
    }
}
