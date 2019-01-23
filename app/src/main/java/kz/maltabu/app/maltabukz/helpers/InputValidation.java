package kz.maltabu.app.maltabukz.helpers;

import android.app.Activity;
import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class InputValidation {
    private Context context;

    public InputValidation(Context context) {
        this.context = context;
    }

    public boolean isInputEditTextEmail(EditText EditText) {
        String value = EditText.getText().toString().trim();
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            hideKeyboardFrom(EditText);
            return false;
        }
        return true;
    }

    public boolean isInputEditTextFilled(EditText EditText) {
        String value = EditText.getText().toString().trim();
        if (value.isEmpty()) {
            hideKeyboardFrom(EditText);
            return false;
        }

        return true;
    }

    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public boolean validatePhoneNumber(EditText EditText) {
        String value = EditText.getText().toString().trim();
        if (value.isEmpty() || !Patterns.PHONE.matcher(value).matches() ||value.length()!=10) {
            return false;
        }
        return true;
    }
}
