package com.example.jacob.pointsofinterest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private String input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void startMapsActivity(View v){
        TextView textView = (TextView) findViewById(R.id.search_input);
        input = textView.getText().toString();
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("searchInput", input);
        startActivity(intent);
    }
}
