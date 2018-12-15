package com.mhassan.expenses.ui;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.mhassan.expenses.R;
import com.mhassan.expenses.utils.Expense;
import com.mhassan.expenses.utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class AddExpensesActivity extends AppCompatActivity {

    private JSONArray mExpenses;
    private String mId;

    private Spinner spinnerCategories;
    private AppCompatEditText editTextAmount;
    private Spinner spinnerCurrency;
    private AppCompatEditText editTextDate;
    private AppCompatEditText editTextDescription;
    private AppCompatButton buttonAdd;

    // https://www.journaldev.com/9976/android-date-time-picker-dialog
    private int mYear, mMonth, mDay;

    private boolean isView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        String expenses = SharedPref.get(this, SharedPref.EXPENSES, "");

        if (expenses == null || expenses.isEmpty())
            mExpenses = new JSONArray();
        else {
            try {
                mExpenses = new JSONArray(expenses);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        buttonAdd = findViewById(R.id.buttonAdd);

        spinnerCategories = findViewById(R.id.spinnerCategories);
        editTextAmount = findViewById(R.id.editTextAmount);
        spinnerCurrency = findViewById(R.id.spinnerCurrency);
        editTextDate = findViewById(R.id.editTextDate);
        editTextDescription = findViewById(R.id.editTextDescription);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isView) {
                    updateView();
                } else {
                    addNewExpense();
                }
            }
        });

        editTextDate.setText(String.format("%1$s-%2$s-%3$s", mYear, mMonth + 1, mDay));
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddExpensesActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                mYear = year;
                                mMonth = monthOfYear;
                                mDay = dayOfMonth;
                                editTextDate.setText(String.format("%1$s-%2$s-%3$s", mYear, mMonth + 1, mDay));
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        if (getIntent().hasExtra("type") && getIntent().getStringExtra("type").equals("add")) {
            isView = false;
            buttonAdd.setText(getResources().getString(R.string.add));
        } else {
            isView = true;
            buttonAdd.setText(getResources().getString(R.string.edit));
            fillFormData();
        }
    }

    private void updateView() {
        for (int i = 0; i < mExpenses.length(); i++) {
            if (mExpenses.optJSONObject(i).optString("id").equals(mId)) {
                String category = spinnerCategories.getSelectedItem().toString();
                String amount = Objects.requireNonNull(editTextAmount.getText()).toString();
                String currency = spinnerCurrency.getSelectedItem().toString();
                String date = Objects.requireNonNull(editTextDate.getText()).toString().trim();
                String description = Objects.requireNonNull(editTextDescription.getText()).toString().trim();

                try {
                    mExpenses.optJSONObject(i).put("category", category);
                    mExpenses.optJSONObject(i).put("currency", currency);
                    mExpenses.optJSONObject(i).put("amount", amount);
                    mExpenses.optJSONObject(i).put("date", date);
                    mExpenses.optJSONObject(i).put("description", description);

                    SharedPref.save(this, SharedPref.EXPENSES, mExpenses.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        finish();
    }

    private void addNewExpense() {
        String id = String.valueOf(Calendar.getInstance().getTimeInMillis());
        String category = spinnerCategories.getSelectedItem().toString();
        String amount = Objects.requireNonNull(editTextAmount.getText()).toString();
        String currency = spinnerCurrency.getSelectedItem().toString();
        String date = Objects.requireNonNull(editTextDate.getText()).toString().trim();
        String description = Objects.requireNonNull(editTextDescription.getText()).toString().trim();

        Expense expense = new Expense(id, category, Double.parseDouble(amount), currency, date, description);
        Gson gson = new Gson();
        String expenseJson = gson.toJson(expense);
        try {
            JSONObject expenseObject = new JSONObject(expenseJson);
            mExpenses.put(expenseObject);

            SharedPref.save(this, SharedPref.EXPENSES, mExpenses.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finish();
        }
    }

    private void fillFormData() {
        mId = getIntent().getStringExtra("id");
        String category = getIntent().getStringExtra("category");
        String currency = getIntent().getStringExtra("currency");
        double amount = getIntent().getDoubleExtra("amount", 0);
        String date = getIntent().getStringExtra("date");
        String description = getIntent().getStringExtra("description");

        editTextDate.setText(date);
        try {
            String[] dateTokens = date.split("-");
            mYear = Integer.parseInt(dateTokens[0]);
            mMonth = Integer.parseInt(dateTokens[1]) - 1;
            mDay = Integer.parseInt(dateTokens[2]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        editTextAmount.setText(String.valueOf(amount));
        editTextDescription.setText(description);

        List<String> categoriesList = Arrays.asList(getResources().getStringArray(R.array.categories));
        spinnerCategories.setSelection(categoriesList.indexOf(category));

        List<String> currencyList = Arrays.asList(getResources().getStringArray(R.array.currency));
        spinnerCurrency.setSelection(currencyList.indexOf(currency));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
