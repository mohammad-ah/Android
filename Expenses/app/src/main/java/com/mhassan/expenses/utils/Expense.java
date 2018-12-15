package com.mhassan.expenses.utils;

import java.util.Objects;

public class Expense {
    private String id;
    private String category;
    private double amount;
    private String currency;
    private String date;
    private String description;

    public Expense(String id, String category, double amount, String currency, String date, String description) {
        this.id = id;
        this.category = category;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expense)) return false;
        Expense expense = (Expense) o;
        return Double.compare(expense.getAmount(), getAmount()) == 0 &&
                Objects.equals(getId(), expense.getId()) &&
                Objects.equals(getCategory(), expense.getCategory()) &&
                Objects.equals(getCurrency(), expense.getCurrency()) &&
                Objects.equals(getDate(), expense.getDate()) &&
                Objects.equals(getDescription(), expense.getDescription());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getCategory(), getAmount(), getCurrency(), getDate(), getDescription());
    }
}
