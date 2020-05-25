package com.chengte99.atm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chengte99.atm.databinding.ActivityLoginBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String acc_storage = getSharedPreferences("atm", MODE_PRIVATE).getString("ACC", "");
        Log.d(TAG, "onCreate: " + acc_storage);
        binding.account.setText(acc_storage);
    }

    public void login(View view) {
        final String account = binding.account.getText().toString();
        final String password = binding.password.getText().toString();

        FirebaseDatabase.getInstance().getReference("users")
                .child(account).child("password")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String pw = (String) dataSnapshot.getValue();
                        if (pw.equals(password)) {

                            getSharedPreferences("atm", MODE_PRIVATE).edit()
                                    .putString("ACC", account)
                                    .apply();

                            setResult(RESULT_OK);
                            finish();
                        }else {
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("Error")
                                    .setMessage("Login Failed")
                                    .setPositiveButton("OK", null)
                                    .show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

//        if ("jack".equals(account) && "1234".equals(password)) {
//            setResult(RESULT_OK);
//            finish();
//        }
    }

    public void quit(View view) {

    }
}
