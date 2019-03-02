package kz.maltabu.app.maltabukz.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.LocaleHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.fragments.ImageFragment;
import kz.maltabu.app.maltabukz.models.Post;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShowDetails extends AppCompatActivity {
    private TextView title, content, price, phone, location, date, photos, commen;
    private ImageView img;
    private ConstraintLayout cs1, hot, top, comments;
    private Post post;
    private FileHelper fileHelper;
    private LinearLayout layout;
    private int PAGE_COUNT;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private int selectedImg;
    private Intent imagesIntent;
    private static final int PERMISSION_REQUEST_CODE = 123;
    private GestureDetector tapGestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_details);
        fileHelper = new FileHelper(this);
        imagesIntent = new Intent(this, ShowDetailsImages.class);
        initViews();
        setInfo();
    }

    private void setInfo() {
        title.setText(post.getTitle());
        if (post.getContent()!=null) {
            content.setText(post.getContent());
        }
        commen.setText(commen.getText().toString()+"("+String.valueOf(post.getComments().size())+")");
        price.setText(post.getPrice());
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
                finish();
            }
        });
        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
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
                if(post.getImages().size()>0)
                    photos.setText(String.valueOf(position+1+"/"+post.getImages().size()));
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
                phone.setText("+7("+gg[0].substring(0,3)+")"+gg[0].substring(3,gg[0].length()));
                if(gg.length>1){
                    for (int j=1; j<gg.length; j++){
                        TextView phone2 = new TextView(ShowDetails.this);
                        phone2.setText("+7("+gg[j].substring(0,3)+")"+gg[j].substring(3,gg[j].length()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            phone2.setTextColor(getColor(R.color.MaltabuBlue));
                        }
                        layout.addView(phone2);
                    }
                }
                showPhone(post.getNumber());
            }
        });

        hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Maltabu.isAuth.equals("false")){
                    startActivity(new Intent(ShowDetails.this, AuthAvtivity.class));
                } else {
                    getPosting();
                }
            }
        });

        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Maltabu.isAuth.equals("false")){
                    startActivity(new Intent(ShowDetails.this, AuthAvtivity.class));
                } else {
                    getPosting();
                }
            }
        });

        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent comIntent = new Intent(ShowDetails.this, CommentsActivity.class);
                comIntent.putExtra("post", post);
                startActivity(comIntent);
                finish();
            }
        });
    }

    private void initViews() {
        post = getIntent().getParcelableExtra("post");
        cs1 = findViewById(R.id.callPhone);
        commen = findViewById(R.id.commentxt);
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
    }


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ImageFragment.newInstance(position, post.getImages().get(position).getMedium());
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
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
                .url("http://maltabu.kz/v1/api/clients/posts/"+numb+"/phone")
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
        String placeHplder="+7("+gg[0].substring(0,3);
        Context context = LocaleHelper.setLocale(this, Maltabu.lang);
        Resources resources = context.getResources();
        phone.setText(placeHplder+") "+resources.getString(R.string.showPhone));
    }

    public void makeCall(){
        String [] gg= phonesArr(post.getPhones());
        if (hasPermissions())
        {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:+7"+gg[0]));
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
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void getPosting(){
        final OkHttpClient client = new OkHttpClient();
        final Request request2 = new Request.Builder()
                .url("http://maltabu.kz/v1/api/clients/cabinet/posting")
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
                .url("http://maltabu.kz/v1/api/login/clients")
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
