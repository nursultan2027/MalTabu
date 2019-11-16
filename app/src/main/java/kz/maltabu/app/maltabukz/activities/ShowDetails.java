package kz.maltabu.app.maltabukz.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.fragments.ImageFragment;
import kz.maltabu.app.maltabukz.fragments.YandexNativeAdFragment;
import kz.maltabu.app.maltabukz.helpers.CustomAnimator;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.InputValidation;
import kz.maltabu.app.maltabukz.helpers.LocaleHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.models.Comment;
import kz.maltabu.app.maltabukz.models.Image;
import kz.maltabu.app.maltabukz.models.Post;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShowDetails extends AppCompatActivity {
    private TextView callPhoneText, title, content, price, phone, location, date, photos, commen, visitorsText;
    private ImageView img;
    private AdView mAdView;
    private ConstraintLayout cs1, hot, top, comments;
    private Post post;
    private String currentNumber;
    private FileHelper fileHelper;
    private LinearLayout layout;
    private int PAGE_COUNT;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private InputValidation inputValidation;
    private Dialog epicDialog;
    private int selectedImg, phonecount=1, COMMENT_REQUEST_CODE=105;
    private Intent imagesIntent;
    private static final int PERMISSION_REQUEST_CODE = 123;
    private GestureDetector tapGestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileHelper = new FileHelper(this);
        epicDialog = new Dialog(this);
        inputValidation = new InputValidation(this);
        imagesIntent = new Intent(this, ShowDetailsImages.class);
        sDialog();
        getPost(getIntent().getStringExtra("postNumb"));

    }

    protected void sDialog() {
        epicDialog.setContentView(R.layout.progress_dialog);
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }
    public String uiPrice(String value){
        int length = value.length();
        String result = value;
        if(length>3){
            if(length==4){
                result = value.substring(0,1)+" "+value.substring(1);
            } else {
                if(length==5){
                    result = value.substring(0,2)+" "+value.substring(2);
                } else {
                    if(length==6){
                        result = value.substring(0,3)+" "+value.substring(3);
                    } else {
                        if(length==7){
                            result = value.substring(0,1)+" "+value.substring(1,4)+" "+value.substring(4);
                        } else {
                            if(length==8){
                                result = value.substring(0,2)+" "+value.substring(2,5)+" "+value.substring(5);
                            } else {
                                if(length==9){
                                    result = value.substring(0,3)+" "+value.substring(3,6)+" "+value.substring(6);
                                } else {
                                    if(length==10){
                                        result = value.substring(0,1)+" "+value.substring(1,4)+" "+value.substring(4,7) + " "+value.substring(7);
                                    } else {
                                        if(length==11){
                                            result = value.substring(0,2)+" "+value.substring(2,5)+" "+value.substring(5,8) + " "+value.substring(8);
                                        } else {
                                            if(length==12){
                                                result = value.substring(0,3)+" "+value.substring(3,6)+" "+value.substring(6,9) + " "+value.substring(9);
                                            } else {
                                                if(length==13){
                                                    result = value.substring(0,1)+" "+value.substring(1,4)+" "+value.substring(4,7) + " "+value.substring(7,10)+" "+value.substring(10);
                                                } else {
                                                    if(length==14){
                                                        result = value.substring(0,2)+" "+value.substring(2,5)+" "+value.substring(5,8) + " "+value.substring(8,11)+" "+value.substring(11);
                                                    } else {
                                                        if(length==15){
                                                            result = value.substring(0,3)+" "+value.substring(3,6)+" "+value.substring(6,9) + " "+value.substring(9,12)+" "+value.substring(12);
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
        }

        return result;
    }

    private void getPost(String numb) {
        final OkHttpClient client = new OkHttpClient();
        final Request request2 = new Request.Builder()
                .url("https://maltabu.kz/v1/api/clients/posts/"+numb)
                .get()
                .addHeader("isAuthorized", "false")
                .build();
        AsyncTask<Void, Void, String> asyncTask1 = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response2 = client.newCall(request2).execute();
                    if (!response2.isSuccessful()) {
                        return null;
                    }
                    return response2.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s1) {
                super.onPostExecute(s1);
                Gson googleJson = new Gson();
                if (s1 != null) {
                    try {
                        JSONObject postObject = new JSONObject(s1);
                        JSONArray arr;
                        if(postObject.has("img")) {
                            JSONObject imgWebObject = postObject.getJSONObject("img");
                            arr = imgWebObject.getJSONArray("web");
                        } else {
                            arr = postObject.getJSONArray("images");
                        }
                        ArrayList<Image> imagesArrayList = new ArrayList<>();
                        ArrayList imgObjList = googleJson.fromJson(String.valueOf(arr), ArrayList.class);
                        Image image = null;
                        Comment com = null;
                        for (int j = 0; j < imgObjList.size(); j++) {
                            JSONObject imgJson = arr.getJSONObject(j);
                            image = new Image(
                                    imgJson.getString("extra_small"),
                                    imgJson.getString("small"),
                                    imgJson.getString("medium"),
                                    imgJson.getString("big"));
                            imagesArrayList.add(image);
                        }
                        JSONArray commentsArr = postObject.getJSONArray("comments");
                        ArrayList<Comment> commentsArrayList = new ArrayList<>();
                        ArrayList commObjList = googleJson.fromJson(String.valueOf(commentsArr), ArrayList.class);
                        for (int k = 0; k < commObjList.size(); k++) {
                            JSONObject imgJson = commentsArr.getJSONObject(k);
                            com = new Comment(
                                    imgJson.getString("content"),
                                    imgJson.getString("createdAt"),
                                    imgJson.getString("name"),
                                    imgJson.getString("mail"));
                            commentsArrayList.add(com);
                        }
                        String phones = "";
                        JSONArray arr2 = postObject.getJSONArray("phones");
                        ArrayList ObjList = googleJson.fromJson(String.valueOf(arr2), ArrayList.class);
                        for (int k=0; k<ObjList.size();k++){
                            phones+=arr2.getString(k)+";";
                        }
                        int visitors = postObject.getJSONObject("stat").getInt("visitors");
                        String createdAt = postObject.getString("createdAt");
                        String cityID = postObject.getJSONObject("cityID").getString("name");
                        int number = postObject.getInt("number");
                        String title = postObject.getString("title");
                        String price = postObject.getJSONObject("price").getString("kind");
                        if (price.equals("value")) {
                            price = uiPrice(String.valueOf(postObject.getJSONObject("price").getInt("value")))+" ₸";
                        } else {
                            if (price.equals("trade")) {
                                if (Maltabu.lang.toLowerCase().equals("ru")) {
                                    price = "Договорная цена";
                                } else {
                                    String kazName = fileHelper.diction().getString("Договорная цена");
                                    price = kazName;
                                }
                            } else {
                                if (price.equals("free")) {
                                    if (Maltabu.lang.toLowerCase().equals("ru")) {
                                        price = "Отдам даром";
                                    } else {
                                        String kazName = fileHelper.diction().getString("Отдам даром");
                                        price = kazName;
                                    }
                                }
                            }
                        }
                        if (postObject.getBoolean("hasContent")) {
                            String content = postObject.getString("content");
                            Post post2 = new Post(visitors, inputValidation.getDate(createdAt), title, content, cityID, price, String.valueOf(number), imagesArrayList,commentsArrayList);
                            post2.setPhones(phones);
                            post = post2;
                            setContentView(R.layout.post_details);
                            initViews();
                            setInfo();
                            if(!ShowDetails.this.isFinishing() && epicDialog!=null) {
                                epicDialog.dismiss();
                            }
                        } else {
                            Post post2 = new Post(visitors, inputValidation.getDate(createdAt), title, cityID, price, String.valueOf(number), imagesArrayList,commentsArrayList);
                            post2.setPhones(phones);
                            post = post2;
                            setContentView(R.layout.post_details);
                            initViews();
                            setInfo();
                            if(epicDialog.isShowing()) {
                                epicDialog.dismiss();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        };
        asyncTask1.execute();
    }

    private void setInfo() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        title.setText(post.getTitle());
        if (post.getContent()!=null) {
            content.setText(post.getContent());
        }
        price.setText(post.getPrice());
        if(Maltabu.lang.equals("ru")) {
            switch (post.getVisitors()){
                case "1": visitorsText.setText(post.getVisitors()+" просмотр");
                case "2": visitorsText.setText(post.getVisitors()+" просмотра");
                case "3": visitorsText.setText(post.getVisitors()+" просмотра");
                case "4": visitorsText.setText(post.getVisitors()+" просмотра");
                default: visitorsText.setText(post.getVisitors()+" "+getResoursesByLang().getString(R.string.visitorsCount));
            }
        } else {
            visitorsText.setText(post.getVisitors()+" "+getResoursesByLang().getString(R.string.visitorsCount));
        }
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.ic_action_eye);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.MaltabuBlue));
        findViewById(R.id.visitors_icon).setBackgroundDrawable(wrappedDrawable);
        location.setText(post.getCityID());
        String dates [] = post.getCreatedAt().split(",");
        if (Maltabu.lang.equals("ru"))
            date.setText(dates[0]+ " "+dates[1]);
        else
            date.setText(dates[0]+ " "+dates[2]);
        PAGE_COUNT = post.getImages().size();
        if(post.getImages().size()>0)
            photos.setText(String.valueOf("1/"+post.getImages().size()));
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomAnimator().animateViewBound(img);
                finish();
            }
        });
        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), getImageFragments());
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(0);
        phonePlaceholder();
        cs1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall();
            }
        });
        tapGestureDetector = new GestureDetector(this, new TapGestureListener());
        pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tapGestureDetector.onTouchEvent(event);
                return false;
            }
        });
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                selectedImg = position;
                if(post.getImages().size()>0) {
                    photos.setText(String.valueOf(position + 1 + "/" + post.getImages().size()));
                }
                if(position==post.getImages().size()){
                    photos.setVisibility(View.GONE);
                    img.setVisibility(View.GONE);
                } else {
                    photos.setVisibility(View.VISIBLE);
                    img.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String [] gg = phonesArr(post.getPhones());
                currentNumber = gg[0];
                showPhoneNumber();
            }
        });

        hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomAnimator().animateHotViewLinear(hot);
                if(Maltabu.isAuth.equals("false")){
                    Toast.makeText(ShowDetails.this, getResoursesByLang().getString(R.string.productionNoAuth),Toast.LENGTH_SHORT).show();
                } else {
                    getPosting();
                }
            }
        });

        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomAnimator().animateHotViewLinear(top);
                if(Maltabu.isAuth.equals("false")){
                    Toast.makeText(ShowDetails.this, getResoursesByLang().getString(R.string.productionNoAuth),Toast.LENGTH_SHORT).show();
                } else {
                    getPosting();
                }
            }
        });

        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomAnimator().animateHotViewLinear(comments);
                Intent comIntent = new Intent(ShowDetails.this, CommentsActivity.class);
                comIntent.putExtra("post", post);
                startActivityForResult(comIntent, COMMENT_REQUEST_CODE);
            }
        });
    }

    private void showPhoneNumber(){
        final String [] gg = phonesArr(post.getPhones());
        phone.setText("+7("+gg[0].substring(0,3)+")"+gg[0].substring(3,gg[0].length()));
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNumber = gg[0];
                makeCall();
            }
        });
        if(phonecount<gg.length) {
            if (gg.length > 1) {
                for (int j = 1; j < gg.length; j++) {
                    TextView phone2 = new TextView(ShowDetails.this);
                    phone2.setPadding(5,5,5,5);
                    phone2.setText("+7(" + gg[j].substring(0, 3) + ")" + gg[j].substring(3, gg[j].length()));
                    final int finalJ = j;
                    phone2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            currentNumber = gg[finalJ];
                            makeCall();
                        }
                    });
                    phonecount++;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        phone2.setTextColor(getColor(R.color.MaltabuBlue));
                    }
                    layout.addView(phone2);
                }
            }
            showPhone(post.getNumber());
        }
    }

    private void initViews() {
        mAdView = findViewById(R.id.adView);
        cs1 = (ConstraintLayout) findViewById(R.id.callPhone);
        callPhoneText = (TextView) findViewById(R.id.textView32);
        commen = (TextView) findViewById(R.id.commentxt);
        img = (ImageView) findViewById(R.id.finish);
        comments = (ConstraintLayout) findViewById(R.id.constraintLayout19);
        layout = (LinearLayout) findViewById(R.id.lin);
        title = (TextView) findViewById(R.id.textView2);
        content = (TextView) findViewById(R.id.textView777);
        price = (TextView) findViewById(R.id.textView6);
        phone = (TextView) findViewById(R.id.phoneTxt);
        location = (TextView) findViewById(R.id.locationTxt);
        date = (TextView) findViewById(R.id.dateTxt);
        photos = (TextView) findViewById(R.id.photos);
        hot = (ConstraintLayout)findViewById(R.id.constraintLayout21);
        top = (ConstraintLayout)findViewById(R.id.constraintLayout30);
        visitorsText = (TextView) findViewById(R.id.visitors_text);
    }

    public List<Fragment> getImageFragments(){
        List<Fragment> fragments = new ArrayList<>();
        if(post.getImages().size()>0) {
            for (int i = 0; i < post.getImages().size(); i++) {
                fragments.add(ImageFragment.newInstance(i, post.getImages().get(i).getMedium()));
            }
            fragments.add(new YandexNativeAdFragment());
        }
        return fragments;
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList;

        public MyPagerAdapter(FragmentManager fragmentManager, List<Fragment> mFragmentList) {
            super(fragmentManager);
            this.mFragmentList = mFragmentList;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

    class TapGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            imagesIntent.putExtra("post", post);
            imagesIntent.putExtra("pageCount", PAGE_COUNT);
            imagesIntent.putExtra("select", selectedImg);
            startActivity(imagesIntent);
            return false;
        }
    }

    public void showPhone(String numb){
        final OkHttpClient client = new OkHttpClient();
        final Request request2 = new Request.Builder()
                .url("https://maltabu.kz/v1/api/clients/posts/"+numb+"/phone")
                .get()
                .addHeader("isAuthorized", "false")
                .build();
        AsyncTask<Void, Void, String> asyncTask1 = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response2 = client.newCall(request2).execute();
                    if (!response2.isSuccessful()) {
                        return null;
                    }
                    return response2.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s1) {
                super.onPostExecute(s1);
                if (s1 != null) {
                }
            }
        };
        asyncTask1.execute();
    }

    public String [] phonesArr(String s){
        String [] spl = s.split(";");
        for(int i=0; i<spl.length;i++){
            spl[i].replace(";","");
        }
        return spl;
    }

    public void phonePlaceholder(){
        String [] gg = phonesArr(post.getPhones());
        currentNumber = gg[0];
        String placeHplder="+7("+gg[0].substring(0,3);
        Context context = LocaleHelper.setLocale(this, Maltabu.lang);
        Resources resources = context.getResources();
        callPhoneText.setText(resources.getString(R.string.CallPhone));
        if(post.getComments().size()>0)
            commen.setText(resources.getString(R.string.showComments)+"("+String.valueOf(post.getComments().size())+")");
        else
            commen.setText(resources.getString(R.string.comments2));
        phone.setText(placeHplder+") "+resources.getString(R.string.showPhone));
    }

    public Resources getResoursesByLang(){
        Context context = LocaleHelper.setLocale(this, Maltabu.lang);
        return context.getResources();
    }
    public void makeCall(){
        if (hasPermissions())
        {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:+7"+currentNumber));
            showPhoneNumber();
            startActivity(callIntent);
        }
        else {
            requestPermissionWithRationale();
        }
    }

    private boolean hasPermissions(){
        int res = 0;
        String[] permissions = new String[]{Manifest.permission.CALL_PHONE};

        for (String perms : permissions){
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)){
                return false;
            }
        }
        return true;
    }

    public void requestPermissionWithRationale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            final String message = "Storage permission is needed to show files count";
            Snackbar.make(ShowDetails.this.findViewById(R.id.seelected), message, Snackbar.LENGTH_LONG)
                    .setAction("GRANT", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestPerms();
                        }
                    })
                    .show();
        } else {
            requestPerms();
        }
    }

    private void requestPerms(){
        String[] permissions = new String[]{Manifest.permission.CALL_PHONE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions,PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:

                for (int res : grantResults) {
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }

                break;
            default:
                allowed = false;
                break;
        }

        if (allowed) {
            String [] gg= phonesArr(post.getPhones());
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:+7"+gg[0]));
            startActivity(callIntent);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                    Toast.makeText(this, "Storage Permissions denied.",
                            Toast.LENGTH_SHORT).show();

                } else {
                    showNoStoragePermissionSnackbar();
                }
            }
        }
    }

    public void showNoStoragePermissionSnackbar() {
        Snackbar.make(ShowDetails.this.findViewById(R.id.seelected),
                "Storage permission isn't granted" , Snackbar.LENGTH_LONG)
                .setAction("SETTINGS", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openApplicationSettings();

                        Toast.makeText(getApplicationContext(),
                                "Open Permissions and grant the Storage permission",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .show();
    }

    public void openApplicationSettings() {
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(appSettingsIntent, PERMISSION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            String [] gg= phonesArr(post.getPhones());
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:+7"+gg[0]));
            startActivity(callIntent);
            return;
        } else {
            if(requestCode == COMMENT_REQUEST_CODE){
                if(data!=null) {
                    String commentsCount = data.getData().toString();
                    try {
                        String username = new JSONObject(fileHelper.readUserFile()).getString("name");
                        String email = new JSONObject(fileHelper.readUserFile()).getString("mail");
                        if (!commentsCount.equals("")) {
                            post.getComments().add(new Comment(commentsCount, "", username, email));
                            commen.setText(getString(R.string.showComments) + "(" + String.valueOf(post.getComments().size()) + ")");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void getPosting(){
        final OkHttpClient client = new OkHttpClient();
        final Request request2 = new Request.Builder()
                .url("https://maltabu.kz/v1/api/clients/cabinet/posting")
                .get()
                .addHeader("isAuthorized", Maltabu.isAuth)
                .addHeader("token", Maltabu.token)
                .build();
        AsyncTask<Void, Void, String> asyncTask1 = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response2 = client.newCall(request2).execute();
                    if (!response2.isSuccessful()) {
                        Maltabu.token = null;
                        Maltabu.isAuth = "false";
                        fileHelper.writeUserFile("");
                        fileHelper.writeToken("");
                        startActivity(new Intent(ShowDetails.this, AuthAvtivity.class));
                        finish();
                        return null;
                    }
                    return response2.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s1) {
                super.onPostExecute(s1);
                if (s1 != null) {
                    fileHelper.writePostingFile(s1);
                    getUser();
                }
            }
        };
        asyncTask1.execute();
    }

    public void getUser(){
        final OkHttpClient client = new OkHttpClient();
        final Request request2 = new Request.Builder()
                .url("https://maltabu.kz/v1/api/login/clients")
                .get()
                .addHeader("isAuthorized", Maltabu.isAuth)
                .addHeader("token", Maltabu.token)
                .build();
        AsyncTask<Void, Void, String> asyncTask1 = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response2 = client.newCall(request2).execute();
                    if (!response2.isSuccessful()) {
                        return null;
                    }
                    return response2.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            @Override
            protected void onPostExecute(String s1) {
                super.onPostExecute(s1);
                if (s1 != null) {
                    Intent hotIntent = new Intent(ShowDetails.this, TopHotActivity.class);
                    hotIntent.putExtra("number", post.getNumber());
                    hotIntent.putExtra("rrr", post.getTitle());
                    startActivity(hotIntent);
                }
            }
        };
        asyncTask1.execute();
    }

}
