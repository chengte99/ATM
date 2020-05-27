package com.chengte99.atm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class TransActivity extends AppCompatActivity {

    private static final String TAG = TransActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);

        new TransTask().execute("https://atm201605.appspot.com/h");
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
