package com.example.nidonnaedon;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nisonnaeson.R;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private final List<ExpenseItem> expenseList;
    private final OnItemClickListener listener;
    private final Context context;

    public ExpenseAdapter(List<ExpenseItem> expenseList, OnItemClickListener listener, Context context) {
        this.expenseList = expenseList;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item_account layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        // Get the current expense item
        ExpenseItem expense = expenseList.get(position);
        // Bind the data to the TextViews
        holder.accountNameTextView.setText(expense.getName());
        holder.accountDateTextView.setText(expense.getDate());

        // Set click listener for the item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onItemClick(adapterPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    // Interface for handling item clicks
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // ViewHolder class for the adapter
    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        public TextView accountNameTextView;
        public TextView accountDateTextView;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            // Find the TextViews in the item_account layout
            accountNameTextView = itemView.findViewById(R.id.account_name);
            accountDateTextView = itemView.findViewById(R.id.account_date);
        }
    }
}
