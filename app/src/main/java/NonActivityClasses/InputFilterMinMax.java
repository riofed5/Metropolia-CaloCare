package NonActivityClasses;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class InputFilterMinMax implements InputFilter {
    private int min, max;
    private Context context;

    public InputFilterMinMax(int min, int max, Context newContext) {
        this.min = min;
        this.max = max;
        this.context = newContext;
    }

    //https://stackoverflow.com/questions/14212518/is-there-a-way-to-define-a-min-and-max-value-for-edittext-in-android
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        //Try block is used to enclose the code
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(min, max, input))
                return null;
            else if (!isInRange(min, max, input)) {
                //https://stackoverflow.com/questions/31175601/how-can-i-change-default-toast-message-color-and-background-color-in-android
                //Create a toast message, black backround, white text, and center of screen.
                Toast newToast = Toast.makeText(context, "Invalid, limit: " + min + " - " + max, Toast.LENGTH_SHORT);
                View v = newToast.getView();

                v.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);

                TextView text = v.findViewById(android.R.id.message);
                text.setTextColor(Color.RED);

                newToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

                newToast.show();
            }
        }
        //If an exception occurs at the particular statement of try block, the rest of the block code will not execute
        //NumberFormatException is when the input is not a number
        catch (NumberFormatException nfe) { }
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return (b > a) ? (c >= a && c <= b) : (c >= b);
    }
}