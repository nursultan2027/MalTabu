package com.proj.changelang.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.proj.changelang.R;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Catalog;
import com.proj.changelang.models.Category;
import com.proj.changelang.models.City;
import com.proj.changelang.models.Region;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class AddPostActivity2 extends AppCompatActivity{
    private Button addPhoneNumber, addImg;
    private CheckBox checkBox;
    private LinearLayout linearLayout;
    private RadioButton rb1, rb2, rb3;
    private EditText edPhone, title, PriceRB;
    private TextView checkTitle, Rb3, RbFree;
    private EditText [] phones = new EditText[4];
    private int phoneNimb = 0;
    private Spinner spinnerRegion, spinnerCity;
    private int PICK_IMAGE_REQUEST = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post2);
        title = (EditText) findViewById(R.id.editText);
        checkBox = (CheckBox)findViewById(R.id.checkBox4);
        checkTitle = (TextView) findViewById(R.id.checkTitle);
        Rb3 = (TextView) findViewById(R.id.textView17);
        RbFree = (TextView) findViewById(R.id.textView77);
        PriceRB = (EditText) findViewById(R.id.editText3);
        addImg = (Button) findViewById(R.id.button2);
        addPhoneNumber = (Button)findViewById(R.id.addPhone);
        rb1 = (RadioButton) findViewById(R.id.priceRB);
        rb3 = (RadioButton) findViewById(R.id.RB3);
        rb2 = (RadioButton) findViewById(R.id.freeRD);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        edPhone = (EditText) findViewById(R.id.phoneEditText);
        spinnerRegion = (Spinner) findViewById(R.id.spinner);
        spinnerCity = (Spinner) findViewById(R.id.spinner2);
        ArrayList<String> arr = new ArrayList<>();
        arr.add(getResources().getString(R.string.chooseRegion));
        for (int i=0; i<Maltabu.regions.size();i++) {
            Region region = Maltabu.regions.get(i);
            arr.add(region.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arr);
        spinnerRegion.setAdapter(adapter);
        spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (title.hasFocus()) {
                    title.clearFocus();
                }
                ArrayList<String> arr = new ArrayList<>();
                arr.add(getResources().getString(R.string.chooseCity));
                for (int i=0; i<Maltabu.regions.get(position).cities.size();i++) {
                    City city = Maltabu.regions.get(position).cities.get(i);
                    arr.add(city.getName());
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(AddPostActivity2.this, android.R.layout.simple_spinner_dropdown_item, arr);
                spinnerCity.setAdapter(adapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RbFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PriceRB.hasFocus())
                PriceRB.clearFocus();
                rb2.toggle();
            }
        });
        Rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PriceRB.hasFocus())
                PriceRB.clearFocus();
                rb3.toggle();
            }
        });
        PriceRB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    rb1.toggle();
            }
        });

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetImages();
            }
        });

        addPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneNimb<4) {
                    EditText newPhone = new EditText(AddPostActivity2.this);
                    phones[phoneNimb] = newPhone;
                    phoneNimb++;
                    newPhone.setInputType(InputType.TYPE_CLASS_PHONE);
                    if (title.hasFocus()) {
                        title.clearFocus();
                    }
                    linearLayout.addView(newPhone);
                }
            }
        });
        checkTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkBox.isChecked()){
                    checkBox.setChecked(true);
                }
                else {
                    checkBox.setChecked(false);
                }
            }
        });
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity2.class));finish();
    }

    public void GetImages(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
