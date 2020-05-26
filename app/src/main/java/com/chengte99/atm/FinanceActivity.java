package com.chengte99.atm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import com.chengte99.atm.data.Expense;
import com.chengte99.atm.data.ExpenseDataBase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FinanceActivity extends AppCompatActivity {

    private static final String TAG = FinanceActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);

        final ExpenseDataBase expenseDataBase = Room.databaseBuilder(this,
                ExpenseDataBase.class, "expense.db").build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                expenseDataBase.expenseDAO().insert(new Expense("2020-05-26", "parking", 50));
            }
        }).start();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<Expense> expenses = expenseDataBase.expenseDAO().getAll();
                for (Expense expense : expenses) {
                    Log.d(TAG, "expenses: " +
                            expense.getDate() + "/" +
                            expense.getInfo() + "/" +
                            expense.getAmount());
                }
            }
        });

    }
}
