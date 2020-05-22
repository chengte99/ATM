package com.chengte99.atm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.chengte99.atm.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void login(View view) {
        String acc = binding.account.getText().toString();
        String pw = binding.password.getText().toString();
        if ("jack".equals(acc) && "1234".equals(pw)) {
            finish();
        }
    }

    public void quit(View view) {

    }
}
