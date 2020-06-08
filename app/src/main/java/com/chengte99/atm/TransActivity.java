package com.chengte99.atm;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chengte99.atm.model.RequestParameter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TransActivity extends AppCompatActivity {

    private static final String TAG = TransActivity.class.getSimpleName();
    private List<Transtion> transtions;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        new TransTask().execute("https://atm201605.appspot.com/h");

        okhttpGet();
//        okhttpPOST();
    }

    private void okhttpPOST() {
        List<RequestParameter> parameters = new ArrayList<>();
        parameters.add(new RequestParameter("CrlMode", "appctl"));
        parameters.add(new RequestParameter("App", "gbb"));
        parameters.add(new RequestParameter("IsDev", "1"));

        FormBody.Builder builder = new FormBody.Builder();
        if (parameters != null && parameters.size() > 0) {
            for (RequestParameter parameter : parameters) {
                builder.add(parameter.getName(), parameter.getValue());
            }
        }
        RequestBody formBody = builder.build();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://ctl.5282288.net/appmenu_api/listApm.php")
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String resStr = response.body().string();
                Log.d(TAG, "onResponse: " + resStr);
            }
        });
    }

    private void okhttpGet() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://atm201605.appspot.com/h")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String string = response.body().string();
                Log.d(TAG, "onResponse: " + string);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        parseJSON(string);
                        parseGSON(string);
                    }
                });
            }
        });
    }

    private void parseGSON(String json) {
        Gson gson = new Gson();
        transtions = gson.fromJson(json, new TypeToken<ArrayList<Transtion>>(){}.getType());

        TransAdapter adapter = new TransAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void parseJSON(String json) {
        transtions = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Transtion trans = new Transtion(object);
                transtions.add(trans);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TransAdapter adapter = new TransAdapter();
        recyclerView.setAdapter(adapter);
    }

    public class TransAdapter extends RecyclerView.Adapter<TransAdapter.TransViewHolder> {

        @NonNull
        @Override
        public TransViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_transtion, parent, false);
            return new TransViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TransViewHolder holder, int position) {
            Transtion trans = transtions.get(position);
            holder.bindTo(trans);
        }

        @Override
        public int getItemCount() {
            return transtions.size();
        }

        public class TransViewHolder extends RecyclerView.ViewHolder {
            TextView dateText;
            TextView amountText;
            TextView typeText;
            public TransViewHolder(@NonNull View itemView) {
                super(itemView);
                dateText = itemView.findViewById(R.id.item_date);
                amountText = itemView.findViewById(R.id.item_amount);
                typeText = itemView.findViewById(R.id.item_type);
            }

            public void bindTo(Transtion trans) {
                dateText.setText(trans.getDate());
                amountText.setText(String.valueOf(trans.getAmount()));
                typeText.setText(trans.getType() + "");
            }
        }
    }

    public class TransTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute: " + s);
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder sb = null;
            try {
                URL url = new URL(strings[0]);
                InputStream is = url.openStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = br.readLine();
//                StringBuffer sbf = new StringBuffer();
                sb = new StringBuilder();
                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                }
                Log.d(TAG, "doInBackground: " + sb.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }
    }
}
