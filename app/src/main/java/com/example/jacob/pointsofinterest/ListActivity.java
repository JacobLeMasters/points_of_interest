package com.example.jacob.pointsofinterest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ListActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    Button mButton;
    String buttonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_list);

        linearLayout = (LinearLayout) this.findViewById(R.id.list);

        readFileInEditor();
    }

    public void readFileInEditor()
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
                    latitude = Double.parseDouble(reader.readLine());
                    longitude = Double.parseDouble(reader.readLine());
                    final Button button = new Button(this);
                    button.setText(str);
                    button.setHint(latitude + " " + longitude);
                    setColorOnPreference(button);
                    linearLayout.addView(button);

                    button.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                    public void onClick(View v){
                            startMapsActivity(button);
                        }
                    });
                }
            }
        }

        catch (Throwable t) {

            Toast

                    .makeText(this, "Exception: "+t.toString(), Toast.LENGTH_LONG)

                    .show();

        }


    }

    public void setColorOnPreference(Button button)
    {
        SharedPreferences myPrefs2 = this.getSharedPreferences(button.getText().toString(), Context.MODE_PRIVATE);
        Boolean visited = myPrefs2.getBoolean("visited", false);
        if(!visited)
            button.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
        else
            button.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);
        button.invalidate();
    }

    public void startMapsActivity(Button button)
    {
        String str = button.getHint().toString();
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("cameraStart", str);
        startActivity(intent);
    }
}
