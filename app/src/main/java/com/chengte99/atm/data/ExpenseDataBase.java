package com.chengte99.atm.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Expense.class, Word.class}, version = 1)
public abstract class ExpenseDataBase extends RoomDatabase {
    public abstract ExpenseDAO expenseDAO();
    private static ExpenseDataBase instance = null;

    public static ExpenseDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, ExpenseDataBase.class,
                    "expense.db").build();
        }
        return instance;
    }
}
