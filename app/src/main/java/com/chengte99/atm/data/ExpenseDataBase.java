package com.chengte99.atm.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Expense.class, Word.class}, version = 1)
public abstract class ExpenseDataBase extends RoomDatabase {
    public abstract ExpenseDAO expenseDAO();
}
