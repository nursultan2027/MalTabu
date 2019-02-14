package kz.maltabu.app.maltabukz.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.InputValidation;
import kz.maltabu.app.maltabukz.helpers.LocaleHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.helpers.PostBodyHelper;
import kz.maltabu.app.maltabukz.models.Catalog;
import kz.maltabu.app.maltabukz.models.Region;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import io.paperdb.Paper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddPostActivity2 extends AppCompatActivity{
    private Button addPhoneNumber, addImg, addPost;
    private CheckBox checkBox;
    private FileHelper fileHelper;
    private ArrayList<Region> regions = new ArrayList();
    private ArrayList<ArrayList<String>> citiesArr = new ArrayList<>();
    private LinearLayout linearLayout;
    private Bitmap bitmap;
    private RadioButton rb1, rb2, rb3;
    private EditText title, PriceRB, content, email, address;
    private EditText [] editTexts = new EditText[5];
    private InputValidation inputValidation;
    private TextView checkTitle, Rb3, RbFree, ctlg, pdf;
    private boolean ok = false;
    private ConstraintLayout [] phones = new ConstraintLayout[5];
    private int phoneNimb = 0;
    private TextView [] updts = new TextView[17];
    private boolean [] checked = new boolean[8];
    private boolean [] check = new boolean[5];
    private String RegionID=null, CityID=null;
    private int imgNumb = 0;
    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    private File photoFile;
    private Catalog catalog;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private Dialog imgDialog, epicDialog;
    private ImageView back;
    private ImageView [] imageViews= new ImageView[8];
    private ConstraintLayout[] cls= new ConstraintLayout[5];
    private ConstraintLayout[] climgs= new ConstraintLayout[8];
    private ConstraintLayout[] closes= new ConstraintLayout[8];
    private Spinner spinnerRegion, spinnerCity;
    private int PICK_IMAGE_REQUEST = 8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post2);
        fileHelper = new FileHelper(this);
        getCities();
        imgDialog = new Dialog(this);
        epicDialog = new Dialog(this);
        inputValidation = new InputValidation(this);
        catalog = getIntent().getParcelableExtra("catalog");
        initViews();
        ClearFocus();
        if(Maltabu.lang.equals("ru"))
            ctlg.setText(catalog.getName());
        else {
            try {
                ctlg.setText(fileHelper.diction().getString(catalog.getName()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        setListeners();
        ArrayList<String> arr = new ArrayList<>();
        arr.add(LocaleHelper.setLocale(this, Maltabu.lang).getResources().getString(R.string.chooseRegion));
        for (int i=0; i<regions.size();i++) {
            Region region = regions.get(i);
            arr.add(region.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arr);
        spinnerRegion.setAdapter(adapter);
        updateViews((String) Paper.book().read("language"));
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    private void updateViews(String lang) {
        Context context = LocaleHelper.setLocale(this, lang);
        Maltabu.lang = lang;
        Resources resources = context.getResources();
        int [] res = {R.string.addPost, R.string.catalog, R.string.title, R.string.desc,
                R.string.price, R.string.radioB1, R.string.radioB2, R.string.barter,
                R.string.barter2, R.string.photo, R.string.region, R.string.city,
                R.string.phone, R.string.mail, R.string.address, R.string.rules};
        for(int i=0; i<16; i++){
            updts[i].setText(resources.getString(res[i]));
        }
        addPhoneNumber.setText(resources.getString(R.string.phonePlus));
        addImg.setText(resources.getString(R.string.photoPlus));
        addPost.setText(resources.getString(R.string.addPost));
    }

    private void setListeners() {
        if(Maltabu.isAuth.equals("true")) {
            try {
                JSONObject user = new JSONObject(fileHelper.readUserFile());
                String mail = user.getString("mail");
                email.setText(mail);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddPostActivity2.this, PdfActivity.class));
            }
        });
        if(Maltabu.isAuth.equals("true")){
//            email.setText(fileHelper.readUserFile());
        }
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearFocus();
                newPost();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddPostActivity2.this, AddPostActivity.class));
                finish();
            }
        });
        spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                ClearFocus();
                if(position>0) {
                    RegionID = regions.get(position-1).getId();
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(AddPostActivity2.this, android.R.layout.simple_spinner_dropdown_item, citiesArr.get(position-1));
                    spinnerCity.setAdapter(adapter2);
                }
                else {
                    ArrayList<String> asd = new ArrayList<>();
                    asd.add(LocaleHelper.setLocale(AddPostActivity2.this, Maltabu.lang).getResources().getString(R.string.chooseCity));
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(AddPostActivity2.this, android.R.layout.simple_spinner_dropdown_item,asd);
                    spinnerCity.setAdapter(adapter2);
                }

                spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        if (position>0){
                            if(pos>0){
                                CityID = regions.get(position-1).cities.get(pos-1).getId();
                                Toast.makeText(AddPostActivity2.this,
                                        regions.get(position-1).cities.get(pos-1).getName(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RbFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearFocus();
                rb2.toggle();
            }
        });
        Rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearFocus();
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
        PriceRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb1.toggle();
            }
        });

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearFocus();
                if(imgNumb<8)
                    GetImages();
            }
        });

        addPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearFocus();
                if (phoneNimb<4) {
                    phoneNimb++;
                    if(getPhoneNumb()!=5) {
                        phones[getPhoneNumb()].setVisibility(View.VISIBLE);
                        editTexts[getPhoneNumb()].setVisibility(View.VISIBLE);
                        check[getPhoneNumb()] = true;
                    }
                    ConstraintLayout cs = (ConstraintLayout) findViewById(R.id.constraintLayout16);
                    if(phoneNimb>0)
                    {
                        cs.setVisibility(View.VISIBLE);
                    }
                    else {
                        cs.setVisibility(View.GONE);
                    }
                }
            }
        });
        checkTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearFocus();
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
                ClearFocus();
                if(phones[4].getVisibility()==View.VISIBLE)
                {
                    phones[4].setVisibility(View.GONE);
                    editTexts[4].setVisibility(View.GONE);
                }
                else {
                    if(phones[3].getVisibility()==View.VISIBLE)
                    {
                        phones[3].setVisibility(View.GONE);
                        editTexts[3].setVisibility(View.GONE);
                    }
                    else {
                        if(phones[2].getVisibility()==View.VISIBLE)
                        {
                            phones[2].setVisibility(View.GONE);
                            editTexts[2].setVisibility(View.GONE);
                        }
                        else {
                            editTexts[1].setText("");
                            phones[1].setVisibility(View.GONE);
                            editTexts[1].setVisibility(View.GONE);
                            check[1]=false;
                        }
                    }
                }
                if(phoneNimb>0)
                    phoneNimb--;
                if(phoneNimb==0) {
                    cls[0].setVisibility(View.GONE);
                }
            }
        });
        cls[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearFocus();
                if(phoneNimb>0) {
                    phoneNimb--;
                }
                if(phoneNimb==0) {
                    cls[0].setVisibility(View.GONE);
                }
                editTexts[1].setText("");
                phones[1].setVisibility(View.GONE);
                editTexts[1].setVisibility(View.GONE);
                check[1]=false;
            }
        });
        cls[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.hasFocus())
                    ClearFocus();
                if(phoneNimb>0) {
                    phoneNimb--;
                }
                if(phoneNimb==0) {
                    cls[0].setVisibility(View.GONE);
                }
                editTexts[2].setText("");
                editTexts[2].setVisibility(View.GONE);
                phones[2].setVisibility(View.GONE);
                check[2]=false;
            }
        });
        cls[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearFocus();
                if(phoneNimb>0) {
                    phoneNimb--;
                }
                if(phoneNimb==0) {
                    cls[0].setVisibility(View.GONE);
                }
                editTexts[3].setText("");
                editTexts[3].setVisibility(View.GONE);
                phones[3].setVisibility(View.GONE);
                check[3]=false;
            }
        });
        cls[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearFocus();
                if(phoneNimb>0) {
                    phoneNimb--;
                }
                if(phoneNimb==0) {
                    cls[0].setVisibility(View.GONE);
                }
                editTexts[4].setText("");
                editTexts[4].setVisibility(View.GONE);
                phones[4].setVisibility(View.GONE);
                check[4]=false;
            }
        });

        for (int i=0; i<8; i++){
            final int finalI = i;
            closes[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClickImgX(finalI);
                }
            });
        }

    }

    private void ClearFocus() {
        if (address.hasFocus())
            address.clearFocus();
        if (addPost.hasFocus())
            addPost.clearFocus();
        if (addImg.hasFocus())
            addImg.clearFocus();
        if (addPhoneNumber.hasFocus())
            addPhoneNumber.clearFocus();
        if (title.hasFocus())
            title.clearFocus();
        if(PriceRB.hasFocus())
            PriceRB.clearFocus();
        if(rb1.hasFocus())
            rb1.clearFocus();
        if(rb2.hasFocus())
            rb2.clearFocus();
        if(checkBox.hasFocus())
            checkBox.clearFocus();
        if(rb3.hasFocus())
            rb3.clearFocus();
        if (spinnerRegion.hasFocus())
            spinnerRegion.clearFocus();
        if (spinnerCity.hasFocus())
            spinnerCity.clearFocus();
        if(email.hasFocus())
            email.clearFocus();
        for(int i=0; i<5;i++){
            if(editTexts[i].hasFocus()) {
                editTexts[i].clearFocus();
            }
        }
    }

    private void initViews() {
        title = (EditText) findViewById(R.id.editText);
        addPost = (Button)findViewById(R.id.addPost);
        content = (EditText) findViewById(R.id.editText2);
        ctlg = (TextView) findViewById(R.id.selectedCatalog);
        email = (EditText) findViewById(R.id.editText5);
        address = (EditText) findViewById(R.id.editText6);
        editTexts[0] = (EditText) findViewById(R.id.editText4);
        editTexts[1] = (EditText) findViewById(R.id.editTex);
        editTexts[2] = (EditText) findViewById(R.id.editTe);
        editTexts[3] = (EditText) findViewById(R.id.editT);
        editTexts[4] = (EditText) findViewById(R.id.edit);
        editTexts[1].setVisibility(View.GONE);
        editTexts[2].setVisibility(View.GONE);
        editTexts[3].setVisibility(View.GONE);
        editTexts[4].setVisibility(View.GONE);
        back = (ImageView) findViewById(R.id.arrr);
        pdf = (TextView) findViewById(R.id.textView28);
        updts[0] = (TextView)findViewById(R.id.textView);
        updts[1] = (TextView)findViewById(R.id.textView20);
        updts[2] = (TextView)findViewById(R.id.textView23);
        updts[3] = (TextView)findViewById(R.id.textView15);
        updts[4] = (TextView)findViewById(R.id.textView16);
        updts[5] = (TextView)findViewById(R.id.textView17);
        updts[6] = (TextView)findViewById(R.id.textView77);
        updts[7] = (TextView)findViewById(R.id.textView18);
        updts[8] = (TextView)findViewById(R.id.checkTitle);
        updts[9] = (TextView)findViewById(R.id.textView19);
        updts[10] = (TextView)findViewById(R.id.textView21);
        updts[11] = (TextView)findViewById(R.id.textView22);
        updts[12] = (TextView)findViewById(R.id.txtphone);
        updts[13] = (TextView)findViewById(R.id.textView26);
        updts[14] = (TextView)findViewById(R.id.textView27);
        updts[15] = (TextView)findViewById(R.id.textView28);
        checked[0] = true;
        checked[1] = false;
        checked[2] = false;
        checked[3] = false;
        checked[4] = false;
        checked[5] = false;
        checked[6] = false;
        checked[7] = false;
        check[0] = false;
        check[1] = false;
        check[2] = false;
        check[3] = false;
        check[4] = false;
        climgs[0] = (ConstraintLayout) findViewById(R.id.imageView);
        climgs[1] = (ConstraintLayout) findViewById(R.id.imageView2);
        climgs[2] = (ConstraintLayout) findViewById(R.id.imageView3);
        climgs[3] = (ConstraintLayout) findViewById(R.id.imageView4);
        climgs[4] = (ConstraintLayout) findViewById(R.id.imageView5);
        climgs[5] = (ConstraintLayout) findViewById(R.id.imageView6);
        climgs[6] = (ConstraintLayout) findViewById(R.id.imageView7);
        climgs[7] = (ConstraintLayout) findViewById(R.id.imageView8);
        imageViews[0] = (ImageView) findViewById(R.id.imgg1);
        imageViews[1] = (ImageView) findViewById(R.id.imgg2);
        imageViews[2] = (ImageView) findViewById(R.id.imgg3);
        imageViews[3] = (ImageView) findViewById(R.id.imgg4);
        imageViews[4] = (ImageView) findViewById(R.id.imgg5);
        imageViews[5] = (ImageView) findViewById(R.id.imgg6);
        imageViews[6] = (ImageView) findViewById(R.id.imgg7);
        imageViews[7] = (ImageView) findViewById(R.id.imgg8);
        imageViews[0].setVisibility(View.GONE);
        imageViews[1].setVisibility(View.GONE);
        imageViews[2].setVisibility(View.GONE);
        imageViews[3].setVisibility(View.GONE);
        imageViews[4].setVisibility(View.GONE);
        imageViews[5].setVisibility(View.GONE);
        imageViews[6].setVisibility(View.GONE);
        imageViews[7].setVisibility(View.GONE);
        closes[0] = (ConstraintLayout) findViewById(R.id.close1);
        closes[1] = (ConstraintLayout) findViewById(R.id.close2);
        closes[2] = (ConstraintLayout) findViewById(R.id.close3);
        closes[3] = (ConstraintLayout) findViewById(R.id.close4);
        closes[4] = (ConstraintLayout) findViewById(R.id.close5);
        closes[5] = (ConstraintLayout) findViewById(R.id.close6);
        closes[6] = (ConstraintLayout) findViewById(R.id.close7);
        closes[7] = (ConstraintLayout) findViewById(R.id.close8);
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
    }

    public void GetImages(){
        imgDialog();
    }

    @Override
    public void onBackPressed() {
        if(!ok)
            startActivity(new Intent(this, AddPostActivity.class));
        else
            startActivity(new Intent(this, MainActivity2.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                bitmap = decodeSampledBitmapFromResource(photoFile.getAbsolutePath(),180,100);
                if(getImgNumb()<8) {
                    climgs[getImgNumb()].setVisibility(View.VISIBLE);
                    imageViews[getImgNumb()].setVisibility(View.VISIBLE);
                    imageViews[getImgNumb()].setImageBitmap(rotateImag(bitmap, photoFile.getAbsolutePath()));
                    checked[getImgNumb()] = true;
                }
            }
        }
        else {
            if (data != null) {
                if(data.getClipData()!=null){
                    ClipData mClipData = data.getClipData();
                    ClipData.Item item = null;
                    Bitmap scaled = null;
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        item = mClipData.getItemAt(i);
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), item.getUri());
                            if (getImgNumb() < 8) {
                                int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
                                scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                                imageViews[getImgNumb()].setImageBitmap(scaled);
                                imageViews[getImgNumb()].setVisibility(View.VISIBLE);
                                climgs[getImgNumb()].setVisibility(View.VISIBLE);
                                checked[getImgNumb()] = true;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                        Uri uri = data.getData();
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            if (getImgNumb() < 8) {
                                int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
                                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                                imageViews[getImgNumb()].setImageBitmap(scaled);
                                imageViews[getImgNumb()].setVisibility(View.VISIBLE);
                                climgs[getImgNumb()].setVisibility(View.VISIBLE);
                                checked[getImgNumb()] = true;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight &&
                    (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public Bitmap rotateImag(Bitmap bitmap, String kk){
        ExifInterface exifInterface = null;
        try{
            exifInterface = new ExifInterface(kk);
        } catch (IOException e){
            e.printStackTrace();
        }
        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        Matrix matrix = new Matrix();
        switch (orientation){
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            default:
        }
        return Bitmap.createBitmap(bitmap, 0,0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
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
                if (imgDialog != null && imgDialog.isShowing()) {
                    imgDialog.dismiss();
                }
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
                        Uri fileProvider = FileProvider.getUriForFile(AddPostActivity2.this, "kz.maltabu.app.maltabukz.fileprovider", photoFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

                        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(cameraIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                            if (imgDialog != null && imgDialog.isShowing()) {
                                imgDialog.dismiss();
                            }
                        }
                    }
                }
            }
        });
        imgDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        imgDialog.show();
    }

    public File getPhotoFileUri(String fileName) {
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return file;
    }
    public void ClickImgX(final int number){
        if(climgs[number].getVisibility()==View.VISIBLE)
        {
            climgs[number].setVisibility(View.GONE);
            checked[number]=false;
        }
    }

    public int getImgNumb(){
        for (int i=0; i<8; i++){
            if (checked[i]==false)
                return i;
        }
        return 8;
    }
    public int getPhoneNumb(){
        for (int i=1; i<5; i++){
            if (check[i]==false)
                return i;
        }
        return 5;
    }
    public boolean CheckPost(){
        Resources res = LocaleHelper.setLocale(this, Maltabu.lang).getResources();
        if(rb1.isChecked() && PriceRB.getText().toString().isEmpty()){
            Toast.makeText(this, res.getString(R.string.priceValid), Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            if (!rb1.isChecked()&&!rb2.isChecked()&&!rb3.isChecked())
            {
                Toast.makeText(this, res.getString(R.string.priceValid), Toast.LENGTH_LONG).show();
                return false;
            }
            else {
                if (!inputValidation.isInputEditTextEmail(email)) {
                    Toast.makeText(this, res.getString(R.string.emailValid), Toast.LENGTH_LONG).show();
                    return false;
                }
                else {
                    if(RegionID==null){
                        Toast.makeText(this, res.getString(R.string.chooseRegion), Toast.LENGTH_LONG).show();
                        return false;
                    }
                    else {
                        if(CityID==null){
                            Toast.makeText(this, res.getString(R.string.chooseCity), Toast.LENGTH_LONG).show();
                            return false;
                        }
                        else {
                            if (!inputValidation.validatePhoneNumber(editTexts[0])) {
                                Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_LONG).show();
                                return false;
                            } else {
                                if (title.getText().toString().isEmpty()) {
                                    Toast.makeText(this, res.getString(R.string.titleValid), Toast.LENGTH_LONG).show();
                                    return false;
                                } else
                                    return true;
                            }
                        }
                    }
                }
            }
        }
    }
    public ArrayList<String> getPhones(){
        ArrayList<String>  aa = new ArrayList<>();
        for (int i=0;i<5;i++){
            if(editTexts[i].getVisibility()==View.VISIBLE){
                if (inputValidation.validatePhoneNumber(editTexts[i])){
                    aa.add(editTexts[i].getText().toString());
                }
            }
        }
        return aa;
    }
    public void newPost(){
        if(CheckPost()){
            sDialog();
            postAds();
        }
    }
    public void postAds(){
        final OkHttpClient client = new OkHttpClient();
        ArrayList<File> files = getImages();
        ArrayList<String> phones = getPhones();

        String exchange = "false";
        if(checkBox.isChecked()){
            exchange ="true";
        }
        String price = "";
        String value="";
        if(rb1.isChecked()){
            price = "value";
            value = PriceRB.getText().toString();
        } else {
            if(rb3.isChecked()){
                price = "trade";
                value = "";
            }
            else {
                if(rb2.isChecked()){
                    price="free";
                    value="";
                }
            }
        }
        String HasImages = "false";
        if(files.size()>0)
            HasImages = "true";
        PostBodyHelper postBodyHelper = new PostBodyHelper();
        postBodyHelper.setTitle(title.getText().toString());
        postBodyHelper.setContent(content.getText().toString());
        postBodyHelper.setCatalogID(catalog.getId());
        postBodyHelper.setCityID(CityID);
        postBodyHelper.setRegionID(RegionID);
        postBodyHelper.setPrice(price);
        postBodyHelper.setValue(value);
        postBodyHelper.setExchange(exchange);
        postBodyHelper.setFiles(files);
        postBodyHelper.setPhones(phones);
        postBodyHelper.setAddress(address.getText().toString());
        postBodyHelper.setEmail(email.getText().toString());
        postBodyHelper.setHasImages(HasImages);

        RequestBody body = postBodyHelper.getBody();

        Request request = null;

        if(Maltabu.isAuth.equals("false")) {
            request = new Request.Builder()
                    .url("http://maltabu.kz/v1/api/clients/posting")
                    .addHeader("Content-Type", "multipart/form-data")
                    .addHeader("isAuthorized", "false")
                    .post(body)
                    .build();
        }
        else {
            request = new Request.Builder()
                    .url("http://maltabu.kz/v1/api/clients/posting")
                    .addHeader("Content-Type", "multipart/form-data")
                    .addHeader("isAuthorized", Maltabu.isAuth)
                    .addHeader("token", Maltabu.token)
                    .post(body)
                    .build();
        }
        final Request finalRequest = request;
        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response = client.newCall(finalRequest).execute();
                    if (!response.isSuccessful()) {
                        if (epicDialog != null && epicDialog.isShowing()) {
                            epicDialog.dismiss();
                        }
                        return null;
                    }
                    return response.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    if (epicDialog != null && epicDialog.isShowing()) {
                        epicDialog.dismiss();
                    }
                    ok = true;
                    setContentView(R.layout.add_post_success);
                    back = (ImageView) findViewById(R.id.arr);
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(AddPostActivity2.this, MainActivity2.class));
                            finish();
                        }
                    });
                }
            }
        };
        asyncTask.execute();
    }
    public void getCities(){
        try {
            for (int i=0; i<fileHelper.getRegionsFromFile().size();i++) {
                Region region = fileHelper.getRegionsFromFile().get(i);
                regions.add(region);
                ArrayList<String> cityarr = new ArrayList<>();
                cityarr.add(LocaleHelper.setLocale(this, Maltabu.lang).getResources().getString(R.string.chooseCity));
                for (int j=0; j<region.cities.size(); j++){
                    cityarr.add(region.cities.get(j).getName());
                }
                citiesArr.add(cityarr);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<File> getImages(){
        ArrayList<File> array2 = new ArrayList<>();
        Bitmap bitmap2 = null;
        byte[] b = new byte[]{};
        FileOutputStream fos = null;
        File f = null;
        for (int i=0;i<8;i++){
            if(imageViews[i].getVisibility()==View.VISIBLE){
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                f = new File(this.getCacheDir(), "filephotos"+String.valueOf(i)+".jpg");
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bitmap2 = ((BitmapDrawable) imageViews[i].getDrawable()).getBitmap();
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                b = stream.toByteArray();
                try {
                    fos = new FileOutputStream(f);
                    try {
                        fos.write(b);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                array2.add(f);
            }
        }
        return array2;
    }

    protected void sDialog() {
        epicDialog.setContentView(R.layout.progress_dialog);
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }
}