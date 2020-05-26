package com.chengte99.atm.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExpenseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Expense expense);

    @Query("select * from Expense")
    public List<Expense> getAll();
}
