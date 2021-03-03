package com.example.calocare;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import NonActivityClasses.AppControl;

public class ActiveLevel extends AppCompatActivity {
    private Button button4;
    private RadioGroup level;
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_level);

        button4 = findViewById(R.id.button4);

        pref = getSharedPreferences(AppControl.USER_PREF, Activity.MODE_PRIVATE);
        prefEditor = pref.edit();
        level = findViewById(R.id.level);

        //https://stackoverflow.com/questions/39715867/android-how-to-enable-a-button-if-a-radio-button-is-checked
        //https://developer.android.com/reference/android/widget/RadioGroup.OnCheckedChangeListener (where is -1 coming from
        level.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //If something is checked, change onChecked
                if (checkedId != -1) {
                    button4.setEnabled(true);
                }
            }
        });
    }


    public void nextActivity(View v) {
        Intent nextActivity = new Intent(this, Goal.class);
        startActivity(nextActivity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int checkId = pref.getInt("userActive", -1);

        // Avoid next button is enable while no radio button is checked since there is an onCheckedChangedListener
        if (checkId == -1) {
            level.clearCheck();
        } else {
            level.check(checkId);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        int selectedId = level.getCheckedRadioButtonId();
        double activeLevel = 0.0;

        // Set value which is used to calculate Maximum Calories consumed when radio button is selected
        if (selectedId != -1) {
            if (selectedId == R.id.notactive) {
                activeLevel = 1.2;
            }
            else if (selectedId == R.id.slightlyactive) {
                activeLevel = 1.55;
            }
            else if (selectedId == R.id.active) {
                activeLevel = 1.9;
            }
        }
        prefEditor.putInt("userActive", selectedId);
        prefEditor.putFloat("userActiveVal", (float) activeLevel);
        prefEditor.commit();
    }
}