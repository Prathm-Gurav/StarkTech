package com.example.techspark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class lifting_chart_adapter extends RecyclerView.Adapter<lifting_chart_adapter.ViewHolder> {

    private List<String> mData; // Example data list
    private LayoutInflater mInflater;

    // Constructor to initialize the adapter with data
    public lifting_chart_adapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.lifting_data_design, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = mData.get(position);
        holder.bind(item);
    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // ViewHolder class to hold the views for each item in the RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTextView;

        ViewHolder(View itemView) {
            super(itemView);
            itemTextView = itemView.findViewById(R.id.textView2); // Replace with your TextView ID
        }

        void bind(String item) {
            itemTextView.setText(item);
            // You can set other views here if needed
        }
    }
}