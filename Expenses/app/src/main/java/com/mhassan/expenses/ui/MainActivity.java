package com.mhassan.expenses.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mhassan.expenses.R;
import com.mhassan.expenses.utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements ExpensesListAdapter.OnActionPerform {
    private static final String ADD = "add";
    private static final String VIEW = "view";

    private ExpensesListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewExpenses = findViewById(R.id.recyclerViewExpenses);

        recyclerViewExpenses.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewExpenses.setLayoutManager(manager);

        mAdapter = new ExpensesListAdapter(new JSONArray());

        mAdapter.setOnActionPerform(this);
        recyclerViewExpenses.setAdapter(mAdapter);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        String expensesListString = SharedPref.get(this, SharedPref.EXPENSES, "");
        try {
            JSONArray expenses = new JSONArray(expensesListString);
            mAdapter.updateList(expenses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_expenses:
                Intent intent = new Intent(this, AddExpensesActivity.class);
                intent.putExtra("type", ADD);
                startActivity(intent);
                return true;
        }
        return false;
    }

    @Override
    public void onItemSelected(JSONObject expense) {
        Intent intent = new Intent(this, AddExpensesActivity.class);
        intent.putExtra("type", VIEW);
        intent.putExtra("id", expense.optString("id"));
        intent.putExtra("category", expense.optString("category"));
        intent.putExtra("currency", expense.optString("currency"));
        intent.putExtra("amount", expense.optDouble("amount"));
        intent.putExtra("date", expense.optString("date"));
        intent.putExtra("description", expense.optString("description"));
        startActivity(intent);
    }
}
