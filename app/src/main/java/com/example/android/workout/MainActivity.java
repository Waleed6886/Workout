package com.example.android.workout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button1,button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == button1.getId()){
            Intent i = new Intent(MainActivity.this,Day.class);
            i.putExtra("day",1);
            startActivity(i);
        }
        else if(id == button2.getId()){
            Intent i = new Intent(MainActivity.this,Day.class);
            i.putExtra("day",2);
            startActivity(i);
        }
    }
}
