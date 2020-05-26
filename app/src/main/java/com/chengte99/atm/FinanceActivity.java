package com.chengte99.atm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.chengte99.atm.data.Expense;
import com.chengte99.atm.data.ExpenseDataBase;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FinanceActivity extends AppCompatActivity {

    private static final String TAG = FinanceActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);

//        final ExpenseDataBase expenseDataBase = ExpenseDataBase.getInstance(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ExpenseDataBase.getInstance(FinanceActivity.this)
                        .expenseDAO()
                        .insert(new Expense("2020-05-26", "dinner", 210));
            }
        }).start();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<Expense> expenses = ExpenseDataBase.getInstance(FinanceActivity.this)
                        .expenseDAO()
                        .getAll();
                for (Expense expense : expenses) {
                    Log.d(TAG, "expenses: " +
                            expense.getDate() + "/" +
                            expense.getInfo() + "/" +
                            expense.getAmount());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_finance, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.upload_expense_to_firebase) {
            final String userid = getSharedPreferences("atm", MODE_PRIVATE).getString("ACC", null);
            if (userid != null) {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<Expense> expenses = ExpenseDataBase.getInstance(FinanceActivity.this)
                                .expenseDAO()
                                .getAll();

                        FirebaseDatabase.getInstance()
                                .getReference("users")
                                .child(userid)
                                .child("expense")
                                .setValue(expenses);
                    }
                });
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
