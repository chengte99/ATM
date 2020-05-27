package com.chengte99.atm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 100;
    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean logon = false;
    private ArrayList<Function> functions;
    //    String[] functions = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!logon) {
            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
            startActivityForResult(intent, REQUEST_CODE);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupFunction();

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        //Adapter
//        FunctionAdapter adapter = new FunctionAdapter(this);
        IconAdapter adapter = new IconAdapter();
        recyclerView.setAdapter(adapter);

//        Log.d(TAG, "onCreate: " + functions[0]);
    }

    public class IconAdapter extends RecyclerView.Adapter<IconAdapter.IconViewHolder> {

        @NonNull
        @Override
        public IconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_icon, parent, false);
            return new IconViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull IconViewHolder holder, int position) {
            final Function function = functions.get(position);
            holder.nameText.setText(function.getName());
            holder.iconImage.setImageResource(function.getIcon());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClick(function);
                }
            });
        }

        @Override
        public int getItemCount() {
            return functions.size();
        }

        public class IconViewHolder extends RecyclerView.ViewHolder {
            ImageView iconImage;
            TextView nameText;
            public IconViewHolder(@NonNull View itemView) {
                super(itemView);
                iconImage = itemView.findViewById(R.id.item_icon);
                nameText = itemView.findViewById(R.id.item_name);
            }
        }
    }

    private void itemClick(Function function) {
        Log.d(TAG, "onClick: " + function.getName());

        switch (function.getIcon()) {
            case R.drawable.f_01:
                startActivity(new Intent(this, TransActivity.class));
                break;
            case R.drawable.f_02:
                break;
            case R.drawable.f_03:
                startActivity(new Intent(this, FinanceActivity.class));
                break;
            case R.drawable.f_04:
                Intent intent = new Intent(this, ContactActivity.class);
                startActivity(intent);
                break;
            case R.drawable.f_05:
                finish();
                break;
        }
    }

    private void setupFunction() {
        functions = new ArrayList<>();
        String[] funcs = getResources().getStringArray(R.array.functions);
        functions.add(new Function(funcs[0], R.drawable.f_01));
        functions.add(new Function(funcs[1], R.drawable.f_02));
        functions.add(new Function(funcs[2], R.drawable.f_03));
        functions.add(new Function(funcs[3], R.drawable.f_04));
        functions.add(new Function(funcs[4], R.drawable.f_05));
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
