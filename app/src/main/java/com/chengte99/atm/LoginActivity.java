package com.chengte99.atm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chengte99.atm.databinding.ActivityLoginBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int REQUEST_CAM = 5;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            toCapture();
        }else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAM);
        }

        binding.cbRemUserid.setChecked(
                getSharedPreferences("atm", MODE_PRIVATE).getBoolean("REMEMBER_USERID", false)
        );

        binding.cbRemUserid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getSharedPreferences("atm", MODE_PRIVATE).edit()
                        .putBoolean("REMEMBER_USERID", b)
                        .commit();
            }
        });

        String acc_storage = getSharedPreferences("atm", MODE_PRIVATE).getString("ACC", "");
        Log.d(TAG, "onCreate: " + acc_storage);
        binding.account.setText(acc_storage);
    }

    private void toCapture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAM) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                toCapture();
            }
        }
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

                            boolean isRem = getSharedPreferences("atm", MODE_PRIVATE).getBoolean("REMEMBER_USERID", false);
                            if (isRem) {
                                getSharedPreferences("atm", MODE_PRIVATE).edit()
                                        .putString("ACC", account)
                                        .apply();
                            }

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
