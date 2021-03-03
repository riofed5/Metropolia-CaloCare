package com.example.calocare;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import NonActivityClasses.AppControl;
import NonActivityClasses.Calories;
import NonActivityClasses.Food;
import NonActivityClasses.FoodList;

public class FoodInfo extends AppCompatActivity {
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;

    private Spinner spin;
    private String nutriUnit = "mg";
    private Food selectedFood;
    private int numOfServ = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);
        this.setTitle(R.string.food_info_title);

        pref = getSharedPreferences(AppControl.FOOD_PREF, Activity.MODE_PRIVATE);
        prefEditor = pref.edit();

        Bundle b = getIntent().getExtras();
        int index = b.getInt("foodIndex", 0);
        selectedFood = FoodList.getInstance().getFood(index);

        //https://stackoverflow.com/questions/13377361/how-to-create-a-drop-down-list
        // Take object spinner
        spin = findViewById(R.id.numOfServ);

        // Create a data source for spinner
        List<String> list = new ArrayList<>();
        for (int x = 1; x <= 100; x++) {
            list.add(String.valueOf(x));
        }

        // Assign data source to Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);

        //Call to display list for spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                numOfServ = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                selectedFood.setNumOfServ(numOfServ);
                updateFoodAdded();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }

    public void addFood(View v) {
        Calories.getInstance().calcServingCalo(selectedFood);
        prefEditor.putInt("foodAdded", Calories.getInstance().getAddedCalo());
        prefEditor.putInt("foodRemain", Calories.getInstance().calcRemain());
        prefEditor.commit();

        Intent nextActivity = new Intent(this, GiaoDienChinh.class);
        startActivity(nextActivity);
    }

    //Multiplied depending on number of servings
    private void updateFoodAdded() {
        ((TextView)findViewById(R.id.tv_foodName)).setText(selectedFood.toString());
        ((TextView)findViewById(R.id.tv_servSize)).setText(selectedFood.getServingSize());
        ((TextView)findViewById(R.id.tv_calories)).setText(selectedFood.getCalories() * numOfServ + " kcal");
        ((TextView)findViewById(R.id.tv_carbs)).setText(selectedFood.getCarbs() * numOfServ + nutriUnit);
        ((TextView)findViewById(R.id.tv_protein)).setText(selectedFood.getProtein() * numOfServ + nutriUnit);
        ((TextView)findViewById(R.id.tv_fat)).setText(selectedFood.getFat() * numOfServ + nutriUnit);
        ((TextView)findViewById(R.id.tv_fiber)).setText(selectedFood.getFiber() * numOfServ + nutriUnit);
        ((TextView)findViewById(R.id.tv_cholesterol)).setText(selectedFood.getCholesterol() * numOfServ + nutriUnit);
        ((TextView)findViewById(R.id.tv_calcium)).setText(selectedFood.getCalcium() * numOfServ + nutriUnit);
    }
}
