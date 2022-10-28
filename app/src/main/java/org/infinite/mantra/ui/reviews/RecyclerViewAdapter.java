package org.infinite.mantra.ui.reviews;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.infinite.mantra.R;
import org.infinite.mantra.database.model.PetographModel;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private final List<PetographModel> data;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView dataDate;
        public TextView dataTime;
        public TextView diastolic;
        public TextView range;
        public ImageView statusButton;
        public TextView systolic;

        public MyViewHolder(View itemView) {
            super(itemView);
            range = itemView.findViewById(R.id.rangeTextView);
            systolic = itemView.findViewById(R.id.systolicTextView);
            diastolic = itemView.findViewById(R.id.diastolicTextView);
            dataTime = itemView.findViewById(R.id.timeTextView);
            dataDate = itemView.findViewById(R.id.dateTextView);
            statusButton = itemView.findViewById(R.id.statusImage);
        }
    }

    public RecyclerViewAdapter(List<PetographModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_card_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name = data.get(position).getName();
        char results;
        switch (name) {
            case "Normal":
                results = 1;
                break;
            case "High":
                results = 2;
                break;
            default:
                results = 0;
                break;
        }
        switch (results) {
            case 1:
                holder.range.setText(R.string.normalBp);
                holder.range.setTextColor(Color.parseColor("#00FF00"));
                holder.systolic.setTextColor(Color.parseColor("#00FF00"));
                holder.diastolic.setTextColor(Color.parseColor("#00FF00"));
                holder.dataTime.setTextColor(Color.parseColor("#00FF00"));
                holder.dataDate.setTextColor(Color.parseColor("#00FF00"));
//                holder.statusButton.setImageResource(17301520);
                break;
            case 2:
                holder.range.setText(R.string.highBp);
                holder.range.setTextColor(Color.parseColor("#FF0000"));
                holder.systolic.setTextColor(Color.parseColor("#FF0000"));
                holder.diastolic.setTextColor(Color.parseColor("#FF0000"));
                holder.dataTime.setTextColor(Color.parseColor("#FF0000"));
                holder.dataDate.setTextColor(Color.parseColor("#FF0000"));
//                holder.statusButton.setImageResource(R.drawable.ic_trending_up_red_24dp);
                break;
            default:
                holder.range.setText("Nil");
                break;

        }
        holder.systolic.setText(data.get(position).getSystolic());
        holder.diastolic.setText(data.get(position).getDiastolic());
        holder.dataTime.setText(data.get(position).getTimeMeasured());
        holder.dataDate.setText(data.get(position).getDateMeasured());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(PetographModel item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }

    public List<PetographModel> getData() {
        return data;
    }
}
