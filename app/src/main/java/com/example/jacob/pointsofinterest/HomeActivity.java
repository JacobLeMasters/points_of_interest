package com.example.jacob.pointsofinterest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HomeActivity extends AppCompatActivity {

    private String input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void startListActivity(View v){
        TextView textView = (TextView) findViewById(R.id.search_input);
        input = textView.getText().toString();
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("searchInput", input);
        startActivity(intent);
    }

    public void clearSharedPreferences(View v)
    {
        try {
            InputStream in = this.getResources().openRawResource(R.raw.points_of_interest);
            if (in != null){
                InputStreamReader tmp = new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(tmp);
                String str;
                double latitude;
                double longitude;
                while (reader.readLine() != null){
                    str = reader.readLine();
                    reader.readLine();
                    reader.readLine();
                    this.getSharedPreferences(str, 0).edit().clear().apply();
                }
            }
        }

        catch (Throwable t) {

            Toast

                    .makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG)

                    .show();

        }
    }
}
