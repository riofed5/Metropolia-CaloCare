package com.example.calocare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import NonActivityClasses.Food;
import NonActivityClasses.FoodList;

public class FoodChoice extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_choice);
        listView = findViewById(R.id.list_food);

        FoodList.getInstance().add();
        listView.setAdapter(new ArrayAdapter<Food>(this, R.layout.listview_food, FoodList.getInstance().getFoodList()));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent nextActivity = new Intent(FoodChoice.this, FoodInfo.class);
                nextActivity.putExtra("foodIndex", i);
                startActivity(nextActivity);
            }
        });
    }
}
