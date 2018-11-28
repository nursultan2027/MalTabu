package com.proj.changelang.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.text.InputType;
import android.util.Log;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.proj.changelang.R;
import com.proj.changelang.helpers.InputValidation;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Catalog;
import com.proj.changelang.models.Category;
import com.proj.changelang.models.City;
import com.proj.changelang.models.Region;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddPostActivity2 extends AppCompatActivity{
    private Button addPhoneNumber, addImg;
    private CheckBox checkBox;
    private LinearLayout linearLayout;
    private RadioButton rb1, rb2, rb3;
    private EditText title, PriceRB;
    private InputValidation inputValidation;
    private TextView checkTitle, Rb3, RbFree, ctlg;
    private ConstraintLayout [] phones = new ConstraintLayout[5];
    private int phoneNimb = 0, reqCam =1, selectFile=0;
    private int imgNumb = 0;
    private ImageView imageView;

    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    private File photoFile;


    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private Dialog imgDialog;
    private ConstraintLayout invis;
    private ImageView [] imageViews= new ImageView[10];
    private ConstraintLayout[] cls= new ConstraintLayout[5];
    private Spinner spinnerRegion, spinnerCity;
    private int PICK_IMAGE_REQUEST = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post2);
        imgDialog = new Dialog(this);
        inputValidation = new InputValidation(this);
        Catalog catalog = getIntent().getParcelableExtra("catalog");
        title = (EditText) findViewById(R.id.editText);
        ctlg = (TextView) findViewById(R.id.selectedCatalog);
        ctlg.setText(catalog.getName());
        imageViews[0] = (ImageView) findViewById(R.id.imageView);
        imageViews[1] = (ImageView) findViewById(R.id.imageView2);
        imageViews[2] = (ImageView) findViewById(R.id.imageView3);
        imageViews[3] = (ImageView) findViewById(R.id.imageView4);
        imageViews[4] = (ImageView) findViewById(R.id.imageView5);
        imageViews[5] = (ImageView) findViewById(R.id.imageView6);
        imageViews[6] = (ImageView) findViewById(R.id.imageView7);
        imageViews[7] = (ImageView) findViewById(R.id.imageView8);
        imageViews[8] = (ImageView) findViewById(R.id.imageView9);
        imageViews[9] = (ImageView) findViewById(R.id.imageView10);

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
        phones[0] = (ConstraintLayout) findViewById(R.id.phoneEditText);
        phones[1] = (ConstraintLayout) findViewById(R.id.phoneEditText2);
        phones[2] = (ConstraintLayout) findViewById(R.id.phoneEditText3);
        phones[3] = (ConstraintLayout) findViewById(R.id.phoneEditText4);
        phones[4] = (ConstraintLayout) findViewById(R.id.phoneEditText5);
        cls[0] = (ConstraintLayout)findViewById(R.id.constraintLayout16);
        cls[1] = (ConstraintLayout)findViewById(R.id.constraintLayout17);
        cls[2] = (ConstraintLayout)findViewById(R.id.constraintLayout18);
        cls[3] = (ConstraintLayout)findViewById(R.id.constraintLayout19);
        cls[4] = (ConstraintLayout)findViewById(R.id.constraintLayout20);
        spinnerRegion = (Spinner) findViewById(R.id.spinner);
        spinnerCity = (Spinner) findViewById(R.id.spinner2);
        ArrayList<String> arr = new ArrayList<>();
        for (int i=0; i<Maltabu.regions.size();i++) {
            Region region = Maltabu.regions.get(i);
            arr.add(region.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arr);
        spinnerRegion.setAdapter(adapter);
        spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (title.hasFocus())
                    title.clearFocus();
                if(PriceRB.hasFocus())
                    PriceRB.clearFocus();
                ArrayList<String> arr = new ArrayList<>();
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
                if(imgNumb<10)
                    GetImages();
            }
        });

        addPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.hasFocus())
                    title.clearFocus();
                if (phoneNimb<4) {
                    phoneNimb++;
                    phones[phoneNimb].setVisibility(View.VISIBLE);
                    if(phoneNimb==1)
                    {
                        ConstraintLayout cs = (ConstraintLayout) findViewById(R.id.constraintLayout16);
                        cs.setVisibility(View.VISIBLE);
                    }
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

        cls[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneNimb>0)
                    phoneNimb--;
                if(phones[4].getVisibility()==View.VISIBLE)
                {
                    phones[4].setVisibility(View.GONE);
                }
                else {
                    if(phones[3].getVisibility()==View.VISIBLE)
                    {
                        phones[3].setVisibility(View.GONE);
                    }
                    else {
                        if(phones[2].getVisibility()==View.VISIBLE)
                        {
                            phones[2].setVisibility(View.GONE);
                        }
                        else {
                            phones[1].setVisibility(View.GONE);
                        }
                    }
                }
                if(phoneNimb==0) {
                    cls[0].setVisibility(View.GONE);
                }
            }
        });
        cls[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneNimb>0) {
                    phoneNimb--;
                }
                if(phoneNimb==0) {
                    cls[0].setVisibility(View.GONE);
                }
                phones[1].setVisibility(View.GONE);
            }
        });
        cls[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneNimb>0) {
                    phoneNimb--;
                }
                if(phoneNimb==0) {
                    cls[0].setVisibility(View.GONE);
                }
                phones[2].setVisibility(View.GONE);
            }
        });
        cls[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneNimb>0) {
                    phoneNimb--;
                }
                if(phoneNimb==0) {
                    cls[0].setVisibility(View.GONE);
                }
                phones[3].setVisibility(View.GONE);
            }
        });
        cls[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneNimb>0) {
                    phoneNimb--;
                }
                if(phoneNimb==0) {
                    cls[0].setVisibility(View.GONE);
                }
                Toast.makeText(AddPostActivity2.this, String.valueOf(phoneNimb),Toast.LENGTH_SHORT).show();
                phones[4].setVisibility(View.GONE);
            }
        });
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, AddPostActivity.class));finish();
    }

    public void GetImages(){
        imgDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(data.getClipData()!=null){
//            ClipData mClipData = data.getClipData();
//            for(int i = 0; i < mClipData.getItemCount(); i++)
//            {
//                ClipData.Item item = mClipData.getItemAt(i);
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), item.getUri());
//                    imageViews[i].setImageBitmap(bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
//                try {
                    Uri uri = data.getData();
                    imageViews[imgNumb].setVisibility(View.VISIBLE);
                    Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    imageViews[imgNumb].setImageBitmap(takenImage);
                    imgNumb++;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                }
            }
        else {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

                Uri uri = data.getData();

                try {
                    imageViews[imgNumb].setVisibility(View.VISIBLE);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imageViews[imgNumb].setImageBitmap(bitmap);
                    imgNumb++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void imgDialog(){
        imgDialog.setContentView(R.layout.choose_file_dialog);
        final ConstraintLayout asd = (ConstraintLayout) imgDialog.findViewById(R.id.constraintLayout22);
        final ConstraintLayout asd2 = (ConstraintLayout) imgDialog.findViewById(R.id.constraintLayout23);
        asd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                imgDialog.dismiss();
            }
        });
        asd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA},
                                    MY_CAMERA_PERMISSION_CODE);
                        }
                    } else {
                        photoFile = getPhotoFileUri(photoFileName);
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        Uri fileProvider = FileProvider.getUriForFile(AddPostActivity2.this, "com.proj.changelang.fileprovider", photoFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

                        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(cameraIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                            imgDialog.dismiss();
                        }
                    }
                }
            }
        });
        imgDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        imgDialog.show();
    }

    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }


    public boolean CheckPost(){
//        if()
        return false;
    }
}
