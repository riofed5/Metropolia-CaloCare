package NonActivityClasses;

import android.widget.EditText;

//This class is for storing Prefs and global functions
public class AppControl {

    public static final String USER_PREF = "userData";

    public static final String FOOD_PREF = "foodData";

    public static String getText(EditText input) {
        return input.getText().toString().trim();
    }
}
