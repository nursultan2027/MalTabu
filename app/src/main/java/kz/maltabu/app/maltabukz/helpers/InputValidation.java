package kz.maltabu.app.maltabukz.helpers;

import android.app.Activity;
import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public String validateContent(String content){
        Pattern pattern = Pattern.compile("[?.!]");
        ArrayList<Character> charArr = new ArrayList<>();
        Matcher m = pattern.matcher(content);
        while (m.find()) {
            int start=m.start();
            charArr.add(content.charAt(start));
        }
        String result = "";
        if(content!=null && !content.isEmpty()) {
            String[] sentences = content.split("[?!.]");
            if(sentences.length>charArr.size()){
                charArr.add('.');
            }
            for (int i=0; i<sentences.length;i++) {
                result += validateSentences(sentences[i])+charArr.get(i)+" ";
            }
            result = result.trim().replaceAll(" +", " ");
        }
        return result;
    }

    private String validateSentences(String sentence){
        sentence = sentence.toLowerCase();
        while(sentence.substring(0,1).equals(" ")){
            sentence = sentence.replace(sentence.substring(0,1),"");
        }
        sentence = sentence.substring(0, 1).toUpperCase() + sentence.substring(1);
        return sentence;
    }

    public String getDate(String s){
        String [] ss = s.split("T");
        String [] ss2 = ss[0].split("-");
        if (ss2[1].equals("01"))
        {
            ss2[1] = "Января,қаңтар";
        } else {
            if (ss2[1].equals("02"))
            {
                ss2[1] = "Февраля,ақпан";
            }
            else {
                if (ss2[1].equals("03"))
                {
                    ss2[1] = "Марта,наурыз";
                }
                else {
                    if (ss2[1].equals("04"))
                    {
                        ss2[1] = "Апреля,сәуiр";
                    } else {
                        if (ss2[1].equals("05"))
                        {
                            ss2[1] = "Мая,мамыр";
                        } else {
                            if (ss2[1].equals("06"))
                            {
                                ss2[1] = "Июня,маусым";
                            }
                            else {
                                if (ss2[1].equals("07"))
                                {
                                    ss2[1] = "Июля,шiлде";
                                } else {
                                    if (ss2[1].equals("08"))
                                    {
                                        ss2[1] = "Августа,тамыз";
                                    }
                                    else {
                                        if (ss2[1].equals("09"))
                                        {
                                            ss2[1] = "Сентября,қыркүйек";
                                        }
                                        else {
                                            if (ss2[1].equals("10"))
                                            {
                                                ss2[1] = "Октября,қазан";
                                            }
                                            else {
                                                if (ss2[1].equals("11"))
                                                {
                                                    ss2[1] = "Ноября,қараша";
                                                }
                                                else {
                                                    if (ss2[1].equals("12"))
                                                    {
                                                        ss2[1] = "Декабря,желтоқсан";
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return ss2[2] +","+ss2[1];
    }
}
