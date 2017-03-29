package com.example.android.workout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import java.util.Map;
import java.util.Set;

import static com.example.android.workout.R.id.number;
import static java.lang.Integer.parseInt;

public class Day extends AppCompatActivity {
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        //to use the full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // to make the screen unscrollable
        RecyclerView r = (RecyclerView) findViewById(R.id.list);
        r.setNestedScrollingEnabled(false);
        r.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        Intent past = getIntent();
        day = past.getIntExtra("day", -1);
        if (day == 1)
            r.setAdapter(new Adapter(getResources().getStringArray(R.array.day1)));
        else if (day == 2)
            r.setAdapter(new Adapter(getResources().getStringArray(R.array.day2)));
    }

    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private String[] arr;
        private SharedPreferences sh;
        private SharedPreferences.Editor edit;
        private Point size;

        public Adapter(String[] arr) {
            this.arr = arr;
            sh = getSharedPreferences("works", MODE_PRIVATE);
            edit = sh.edit();
            Display display = getWindowManager().getDefaultDisplay();
            size = new Point();
            display.getSize(size);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(Day.this).inflate(R.layout.row, parent, false);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, size.y / arr.length);
            v.setLayoutParams(p);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.name.setText(arr[position]);
            holder.number.setText(sh.getFloat(holder.name.getText().toString(), 0) + "");
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float number = Float.parseFloat(holder.number.getText().toString()) + 0.5f;
                    edit.putFloat(holder.name.getText().toString(),number);
                    edit.commit();
                    holder.number.setText(number + "");
                }
            });

            holder.min.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float number = Float.parseFloat(holder.number.getText().toString()) - 0.5f;
                    if (number < 0)
                        return;
                    edit.putFloat(holder.name.getText().toString(), number);
                    holder.number.setText(number + "");
                    edit.commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            return arr.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView name, number;
            ImageButton add, min;

            public ViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                number = (TextView) itemView.findViewById(R.id.number);
                add = (ImageButton) itemView.findViewById(R.id.add);
                min = (ImageButton) itemView.findViewById(R.id.min);
                if (day == 2) {
                    add.setBackgroundResource(R.color.red);
                    min.setBackgroundResource(R.color.redDark);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        number.setTextColor(getResources().getColor(R.color.redDark, null));
                    }
                }
            }
        }
    }
}
