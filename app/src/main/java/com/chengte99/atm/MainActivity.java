package com.chengte99.atm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 100;
    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean logon = false;
    String[] functions = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!logon) {
            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
            startActivityForResult(intent, REQUEST_CODE);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Adapter
        FunctionAdapter adapter = new FunctionAdapter(this);
        recyclerView.setAdapter(adapter);

//        Log.d(TAG, "onCreate: " + functions[0]);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                finish();
            }
        }
    }
}
