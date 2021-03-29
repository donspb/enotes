package ru.gb.androidone.donspb.enote.navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

import ru.gb.androidone.donspb.enote.R;
import ru.gb.androidone.donspb.enote.datapart.EnoteData;
import ru.gb.androidone.donspb.enote.datapart.EnoteDataSource;

public class EnotesListAdapter extends RecyclerView.Adapter<EnotesListAdapter.ViewHolder> {

    private EnoteDataSource dataSource;
    private OnItemClickListener itemClickListener;
    private final Fragment fragment;
    private int menuPosition;


    public EnotesListAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setDataSource(EnoteDataSource enoteDataSource) {
        this.dataSource = enoteDataSource;
        notifyDataSetChanged();
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

    public int getMenuPosition() {
        return menuPosition;
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

            View.OnLongClickListener onItemLongClickListener = new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {menuPosition = getLayoutPosition();
                    itemView.showContextMenu(10,10);
                    return true;
                }
            };

            title = itemView.findViewById(R.id.list_title_field);
            descr = itemView.findViewById(R.id.list_descr_field);
            date = itemView.findViewById(R.id.list_date_field);

            title.setOnClickListener(onItemClickListener);
            descr.setOnClickListener(onItemClickListener);
            date.setOnClickListener(onItemClickListener);
            title.setOnLongClickListener(onItemLongClickListener);
            descr.setOnLongClickListener(onItemLongClickListener);
            date.setOnLongClickListener(onItemLongClickListener);

            registerContextMenu(itemView);
        }

        private void registerContextMenu(@NonNull View itemView) {
            if (fragment != null) {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        menuPosition = getLayoutPosition();
                        return false;
                    }
                });
            }
            fragment.registerForContextMenu(itemView);
        }

        public void setData(EnoteData enoteData) {
            title.setText(enoteData.getNoteTitle());
            descr.setText(enoteData.getNoteDescription());
            date.setText(new SimpleDateFormat("dd-MM-yyyy").format(enoteData.getDateTime()));
        }
    }
}
