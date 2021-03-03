package com.example.calocare;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import NonActivityClasses.AppControl;
import NonActivityClasses.InputFilterMinMax;

public class BasicInfo extends AppCompatActivity {
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;

    private EditText nameTxt, ageTxt;
    private Button nextBtn;
    private RadioGroup gender;
    private boolean onChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_info);
        this.setTitle(R.string.basic_info_title);

        pref = getSharedPreferences(AppControl.USER_PREF, Activity.MODE_PRIVATE);
        prefEditor = pref.edit();

        nameTxt = findViewById(R.id.name);
        ageTxt = findViewById(R.id.age);
        nextBtn = findViewById(R.id.next);
        nextBtn.setEnabled(false);
        gender = findViewById(R.id.gender);

        nameTxt.addTextChangedListener(watcher);
        ageTxt.addTextChangedListener(watcher);

        // set min, max input value for age
        ageTxt.setFilters(new InputFilter[]{ new InputFilterMinMax(1, 150, this) });
        ageTxt.setTransformationMethod(new NumericKeyBoardTransformationMethod());

        //https://stackoverflow.com/questions/39715867/android-how-to-enable-a-button-if-a-radio-button-is-checked
        //https://developer.android.com/reference/android/widget/RadioGroup.OnCheckedChangeListener (where is -1 coming from
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //If something is checked, change onChecked
                if (checkedId != -1) {
                    onChecked = true;
                }
                validateNext();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int age = pref.getInt("userAge", 0);
        int checkId = pref.getInt("userGender", -1);

        nameTxt.setText(pref.getString("userName", ""));

        //If no saved key, instead of showing 0, show empty
        if (age <= 0) {
            ageTxt.setText("");
        } else {
            ageTxt.setText("" + age);
        }

        // Avoid next button is enable while no radio button is checked since there is an onCheckedChangedListener
        if (checkId == -1) {
            gender.clearCheck();
        } else {
            gender.check(checkId);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        int selectedId = gender.getCheckedRadioButtonId();
        int defaultAge = 0;

        if (!TextUtils.isEmpty(AppControl.getText(nameTxt))) {
            prefEditor.putString("userName", AppControl.getText(nameTxt));
        }
        if (!TextUtils.isEmpty(AppControl.getText(ageTxt))) {
            defaultAge = Integer.parseInt(AppControl.getText(ageTxt));
            prefEditor.putInt("userAge", defaultAge);
        }
        if (selectedId != -1) {
            RadioButton selectedGender = findViewById(selectedId);

            prefEditor.putString("userGenderText", selectedGender.getText().toString());
        }
        prefEditor.putInt("userGender", selectedId);
        prefEditor.commit();
    }

    //In order to only allow the user to use numbers only, input type number password is needed
    //So we need to convert the * back to what the user types.
    private class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return source;
        }
    }

    //https://stackoverflow.com/questions/26196770/enable-button-once-all-edittext-fields-are-not-empty
    private final TextWatcher watcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        //Whenever text is changed, check if all is filled
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            validateNext();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private void validateNext() {
        nextBtn.setEnabled(hasFilled());
    }

    public void nextActivity(View v) {
        Intent nextActivity = new Intent(this, BMRInFo.class);
        startActivity(nextActivity);
    }

    private boolean hasFilled() {
        //If neither the text fields are empty and onChecked is true, enable the next button;
        return !TextUtils.isEmpty(AppControl.getText(nameTxt))
                && !TextUtils.isEmpty(AppControl.getText(ageTxt))
                && onChecked != false;
    }
}
