package com.mhassan.expenses.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mhassan.expenses.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class ExpensesListAdapter extends RecyclerView.Adapter<ExpensesListAdapter.ViewHolder> {
    private JSONArray mExpenses;

    interface OnActionPerform {
        void onItemSelected(JSONObject expense);
    }

    private OnActionPerform onActionPerform;

    void setOnActionPerform(OnActionPerform onActionPerform) {
        this.onActionPerform = onActionPerform;
    }

    ExpensesListAdapter(JSONArray expenses) {
        this.mExpenses = expenses;
    }

    void updateList(JSONArray expenses) {
        this.mExpenses = expenses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_expenses, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        try {
            String category = mExpenses.getJSONObject(i).getString("category");
            String amount = mExpenses.getJSONObject(i).getString("amount");
            String currency = mExpenses.getJSONObject(i).getString("currency");

            viewHolder.textViewCategory.setText(category);
            viewHolder.textViewAmount.setText(String.format("%1$s%2$s", amount, currency));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mExpenses.length();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView layoutRoot;
        AppCompatTextView textViewCategory;
        AppCompatTextView textViewAmount;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutRoot = itemView.findViewById(R.id.layoutRoot);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);

            layoutRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onActionPerform != null) {
                        try {
                            onActionPerform.onItemSelected(mExpenses.getJSONObject(getAdapterPosition()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
}
