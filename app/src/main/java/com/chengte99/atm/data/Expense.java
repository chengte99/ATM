package com.chengte99.atm.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Expense {
    @PrimaryKey(autoGenerate = true)
    int id;
    @NonNull
    @ColumnInfo(name = "onCreate")
    String date;
    @NonNull
    String info;
    @NonNull
    int amount;

    public Expense(@NonNull String date, @NonNull String info, int amount) {
        this.date = date;
        this.info = info;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    @NonNull
    public String getInfo() {
        return info;
    }

    public void setInfo(@NonNull String info) {
        this.info = info;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
