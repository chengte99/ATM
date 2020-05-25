package com.chengte99.atm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 100;
    private boolean logon = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!logon) {
            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
            startActivityForResult(intent, REQUEST_CODE);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
