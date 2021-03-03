package com.example.calocare;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

import NonActivityClasses.AlarmReceiver;
import NonActivityClasses.AppControl;
import NonActivityClasses.Calories;
import NonActivityClasses.UserInfo;

public class GiaoDienChinh extends AppCompatActivity {
    private SharedPreferences userPref, foodPref;
    private SharedPreferences.Editor userPrefEditor, foodPrefEditor;

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    private TextView caloGoal;
    private TextView caloAdded;
    private TextView caloRemain;


    private UserInfo user = UserInfo.getInstance();

    private static final int code1 = 1;
    private static final int code2 = 2;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giao_dien_chinh);

        userPref = getSharedPreferences(AppControl.USER_PREF, Activity.MODE_PRIVATE);
        foodPref = getSharedPreferences(AppControl.FOOD_PREF, Activity.MODE_PRIVATE);
        userPrefEditor = userPref.edit();
        foodPrefEditor = foodPref.edit();

        caloGoal = findViewById(R.id.tv_goal);
        caloAdded = findViewById(R.id.tv_food);
        caloRemain = findViewById(R.id.tv_remain);


        if(userPref.getInt("userGoalVal", 0) == 0) {
            toBasicInfo();
        }
        else {
            //If there are PendingIntents that match code, context, and intent, return null, otherwise return the pending intent
            //then check if the return is not equal to null
            //https://stackoverflow.com/questions/4556670/how-to-check-if-alarmmanager-already-has-an-alarm-set/9575569#9575569
            boolean alarmUp1 = (PendingIntent.getBroadcast(this, code1,
                    new Intent(this, AlarmReceiver.class),
                    PendingIntent.FLAG_NO_CREATE) != null);
            boolean alarmUp2 = (PendingIntent.getBroadcast(this, code2,
                    new Intent(this, AlarmReceiver.class),
                    PendingIntent.FLAG_NO_CREATE) != null);
            //Check whether alarm is existed or not
            if (!(alarmUp1 || alarmUp2)) {
                setAlarm(true);     //Set the alarm for midnight reset
                setAlarm(false);    //Set the alarm for 10p.m. notification
            }
            setUserValue();
            print();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Integer.parseInt(caloGoal.getText().toString()) != 0) {
            foodPrefEditor.putInt("foodGoal", Integer.parseInt(caloGoal.getText().toString()));
            foodPrefEditor.putInt("foodAdded", Integer.parseInt(caloAdded.getText().toString()));
            foodPrefEditor.putInt("foodRemain", Integer.parseInt(caloRemain.getText().toString()));
            foodPrefEditor.commit();
        }
    }
    
    public void addFood(View v) {
        Intent nextActivity = new Intent(this, FoodChoice.class);
        startActivity(nextActivity);
    }

    public void editInfo(View v) {
        toBasicInfo();
    }

    public void print(){
        int foodAdded = foodPref.getInt("foodAdded", 0);
        Calories.getInstance().setAddedCalo(foodAdded);

        caloGoal.setText(String.valueOf(Calories.getInstance().maxCalo()));
        caloAdded.setText(String.valueOf(foodAdded));
        caloRemain.setText(String.valueOf(Calories.getInstance().calcRemain()));
    }

    private void setUserValue() {
        //Everytime opeing the app, set all the userinfo to run functions.
        user.setAge(userPref.getInt("userAge", 0));
        user.setGender(userPref.getString("userGenderText", ""));
        user.setHeight(userPref.getInt("userHeight", 0));
        user.setWeight(userPref.getInt("userWeight", 0));
        user.setActiveStatus(userPref.getFloat("userActiveVal", 0));
        user.setGoalStatus(userPref.getInt("userGoalVal", 0));
    }

    private void toBasicInfo() {
        Intent nextActivity = new Intent(this, BasicInfo.class);
        startActivity(nextActivity);
    }

    //https://developer.android.com/training/scheduling/alarms.html
    //https://developer.android.com/reference/kotlin/android/app/PendingIntent?hl=en
    public void setAlarm( boolean isMidnight ) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("isMidnight", isMidnight);
        alarmIntent = PendingIntent.getBroadcast(this, isMidnight ? code1 : code2, intent, PendingIntent.FLAG_UPDATE_CURRENT);  //Need to use separate code because if the same code, it replaces the old PendingIntent

        Calendar calendar = Calendar.getInstance();
        //Set specific time for alarm
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, isMidnight ? 1 : 0);    //since 12a.m. is for the next day, we need to add 1 to the date, but none for 10p.m.
        calendar.set(Calendar.HOUR_OF_DAY, isMidnight ? 0 : 22);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);


    }
}
