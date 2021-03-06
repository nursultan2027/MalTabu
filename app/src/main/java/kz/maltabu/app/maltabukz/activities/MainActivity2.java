package kz.maltabu.app.maltabukz.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import io.paperdb.Paper;
import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.Redesign.ui.activity.AddPostRedesign;
import kz.maltabu.app.maltabukz.fragments.CatalogFragment;
import kz.maltabu.app.maltabukz.fragments.HotFragment;
import kz.maltabu.app.maltabukz.fragments.SearchFragment;
import kz.maltabu.app.maltabukz.helpers.ConnectionHelper;
import kz.maltabu.app.maltabukz.helpers.CustomAnimator;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.LocaleHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ActivityFragment {

    private TextView filterText, menu1, menu2,menu3,menu4,menu6,menu5,menu7,menu8, menu82, cab, lange, hottitle;
    private ConstraintLayout cl1, m1, m2, m3, m4, m5, m6, m7,cab1, cLnag, search;
    private ImageView filter, flag, menuLogo;
    private JSONObject object;
    private FileHelper fileHelper;
    private ConnectionHelper connectionHelper;
    private DrawerLayout drawer;
    private Dialog epicDialog, sortDialog;
    private Intent filterIntent;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        Log.d("token1", Paper.book().read("firebaseToken",""));
        fileHelper = new FileHelper(this);
        SetActivityView();
        epicDialog = new Dialog(this);
        sortDialog = new Dialog(this);
        connectionHelper = new ConnectionHelper(this);
        opentCurrentFragment(Maltabu.fragmentNumb);
        drawer.openDrawer(Gravity.LEFT);
    }

    private void SetActivityView(){
        setContentView(R.layout.activity_main);
        Resources res = this.getResources();
        filter = (ImageView) findViewById(R.id.filter);
        filterText = findViewById(R.id.filter_text);
        filterIntent = new Intent(this, FilterActivity.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppBarLayout appBar = (AppBarLayout) findViewById(R.id.appBarLayout2);
        setSupportActionBar(toolbar);
        appBar.setOutlineProvider(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        Paper.init(this);
        String lgg = Paper.book().read("language");
        if(lgg==null){
            Paper.book().write("language", "kk");
        } else {
            Maltabu.lang = lgg;
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_toggle);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View view = navigationView.getHeaderView(0);
        cab  = (TextView) view.findViewById(R.id.cabinet);
        menu1  = (TextView) view.findViewById(R.id.menu1);
        menu2  = (TextView) view.findViewById(R.id.menu2);
        menu3  = (TextView) view.findViewById(R.id.menu3);
        menu4  = (TextView) view.findViewById(R.id.menu4);
        menu5  = (TextView) view.findViewById(R.id.menu5);
        menu6  = (TextView) view.findViewById(R.id.menu6);
        menu7  = (TextView) view.findViewById(R.id.menu7);
        menuLogo = (ImageView) view.findViewById(R.id.imageView37);
        hottitle = (TextView) findViewById(R.id.hottitle);
        lange = (TextView) view.findViewById(R.id.langText);
        cLnag = (ConstraintLayout) view.findViewById(R.id.constraintLayout29);
        flag = (ImageView) view.findViewById(R.id.imageView30);
        cab1  = (ConstraintLayout) view.findViewById(R.id.constraintLayout);
        m1  = (ConstraintLayout) view.findViewById(R.id.constraintLayout2);
        m2  = (ConstraintLayout) view.findViewById(R.id.constraintLayout3);
        m3  = (ConstraintLayout) view.findViewById(R.id.constraintLayout4);
        m4  = (ConstraintLayout) view.findViewById(R.id.constraintLayout5);
        m5  = (ConstraintLayout) view.findViewById(R.id.constraintLayout6);
        m6  = (ConstraintLayout) view.findViewById(R.id.constraintLayout9);
        m7  = (ConstraintLayout) view.findViewById(R.id.constraintLayout10);
        search  = (ConstraintLayout) view.findViewById(R.id.constraintLayout228);
        menu8  = (TextView) findViewById(R.id.menu8);
        menu82  = (TextView) findViewById(R.id.menu82);
        cl1 = (ConstraintLayout) findViewById(R.id.constraintLayout7);

        initListeners();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        try {
            object = new JSONObject(fileHelper.readUserFile());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        updateView((String) Paper.book().read("language"));
        if(Maltabu.filterModel!=null)
            findViewById(R.id.filter_badge).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.filter_badge).setVisibility(View.GONE);
    }
    private void initListeners() {
        cLnag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Maltabu.lang.equals("kk")){
                    Paper.book().write("language", "ru");
                    Maltabu.lang = "ru";
                    flag.setImageResource(R.drawable.ru);
                    updateView((String)Paper.book().read("language"));
                        opentCurrentFragment(Maltabu.fragmentNumb);
                } else {
                    Paper.book().write("language", "kk");
                    Maltabu.lang = "kk";
                    flag.setImageResource(R.drawable.kz);
                    updateView((String)Paper.book().read("language"));
                    opentCurrentFragment(Maltabu.fragmentNumb);
                }
            }
        });
        menuLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentMain();
                Maltabu.selectedFragment = 0;
            }
        });
        cab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connectionHelper.isConnected()) {
                    if(Maltabu.isAuth.equals("false")) {
                        startActivity(new Intent(MainActivity2.this, AuthAvtivity.class));
                        setTransition();
                        finish();
                    } else {
                        sDialog();
                        getPosting();
                    }
                } else {
                    startActivity(new Intent(MainActivity2.this, NoConnection.class));
                }
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connectionHelper.isConnected()) {
                    fragmentSearch();
                    Maltabu.selectedFragment = 0;
                } else {
                    startActivity(new Intent(MainActivity2.this, NoConnection.class));
                }
            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connectionHelper.isConnected()) {
                    sDialog();
                    startActivity(filterIntent);
                } else {
                    startActivity(new Intent(MainActivity2.this, NoConnection.class));
                }
            }
        });
        filterText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connectionHelper.isConnected()) {
                    sDialog();
                    startActivity(filterIntent);
                } else {
                    startActivity(new Intent(MainActivity2.this, NoConnection.class));
                }
            }
        });
        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connectionHelper.isConnected()) {
                    try {
                        fragment1();
                        Maltabu.selectedFragment = 0;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    startActivity(new Intent(MainActivity2.this, NoConnection.class));
                }
            }
        });
        m2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connectionHelper.isConnected()) {
                    try {
                        fragment2();
                        Maltabu.selectedFragment = 0;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    startActivity(new Intent(MainActivity2.this, NoConnection.class));
                }
            }
        });
        m3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connectionHelper.isConnected()) {
                    try {
                        fragment3();
                        Maltabu.selectedFragment = 0;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    startActivity(new Intent(MainActivity2.this, NoConnection.class));
                }
            }
        });
        m4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connectionHelper.isConnected()) {
                    try {
                        fragment4();
                        Maltabu.selectedFragment = 0;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    startActivity(new Intent(MainActivity2.this, NoConnection.class));
                }
            }
        });
        m5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connectionHelper.isConnected()) {
                    try {
                        fragment5();
                        Maltabu.selectedFragment = 0;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    startActivity(new Intent(MainActivity2.this, NoConnection.class));
                }
            }
        });
        m6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fragment6();
                    Maltabu.selectedFragment = 0;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        m7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connectionHelper.isConnected()) {
                    try {
                        fragment7();
                        Maltabu.selectedFragment = 0;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    startActivity(new Intent(MainActivity2.this, NoConnection.class));
                }
            }
        });
        cl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connectionHelper.isConnected()) {
                    CustomAnimator.animateViewBound(cl1.findViewById(R.id.imageView));
                    startActivity(new Intent(MainActivity2.this, AddPostActivity.class));
                    setTransition();
                    finish();
                } else {
                    startActivity(new Intent(MainActivity2.this, NoConnection.class));
                }
            }
        });
    }

    private void setTransition(){
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_up);
    }

    private void updateView(String lang) {
        Context context = LocaleHelper.setLocale(this, lang);
        Maltabu.lang = lang;
        if (lang.equals("ru"))
            flag.setImageResource(R.drawable.ru);
        else
            flag.setImageResource(R.drawable.kz);
        Resources resources = context.getResources();
        setTitle("");
        if(Maltabu.isAuth.equals("false")) {
            cab.setText(resources.getString(R.string.Cabinet));
        } else {
            try {
                if(object!=null)
                    cab.setText(object.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        menu1.setText(resources.getString(R.string.menu1));
        menu2.setText(resources.getString(R.string.menu2));
        menu3.setText(resources.getString(R.string.menu3));
        menu4.setText(resources.getString(R.string.menu4));
        menu5.setText(resources.getString(R.string.menu5));
        menu6.setText(resources.getString(R.string.menu6));
        menu7.setText(resources.getString(R.string.menu7));
        menu8.setText(resources.getString(R.string.menu8));
        menu82.setText(resources.getString(R.string.menu82));
        lange.setText(resources.getString(R.string.other_lang));
        filterText.setText(resources.getString(R.string.filter));
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(Maltabu.fragmentNumb==0) {
                if (back_pressed + 2000 > System.currentTimeMillis()) {
                    super.onBackPressed();
                } else {
                    if(Maltabu.lang.equals("ru"))
                        Toast.makeText(getBaseContext(), "Нажмите еще раз для выхода", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getBaseContext(), "Шығу үшін тағы басыңыз", Toast.LENGTH_SHORT).show();

                }
                back_pressed = System.currentTimeMillis();
            } else {
                Maltabu.filterModel = null;
                Maltabu.s1= null;
                Maltabu.s3= null;
                Maltabu.s5= null;
                Maltabu.s6= null;
                Maltabu.s4= null;
                Maltabu.s2= null;
                fragmentMain();
            }
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase,"ru"));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
//        displaySelectedScreen(id);
        return true;
    }

    private void fragmentMain(){
        filter.setVisibility(View.GONE);
        filterText.setVisibility(View.GONE);
        findViewById(R.id.filter_badge).setVisibility(View.GONE);
        Context context = LocaleHelper.setLocale(this, Maltabu.lang);
        hottitle.setText(context.getResources().getString(R.string.hotTitle));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HotFragment fragment = new HotFragment();
        fragmentTransaction.replace(R.id.main, fragment);
        fragmentTransaction.commit();
        removeAllFragments(fragmentManager, fragment);
        drawer.closeDrawer(GravityCompat.START);
        Maltabu.fragmentNumb = 0;
        Maltabu.byTime = true;
        Maltabu.increment = true;
        Maltabu.s1= null;
        Maltabu.s3= null;
        Maltabu.s5= null;
        Maltabu.s6= null;
        Maltabu.s4= null;
        Maltabu.s2= null;
        Maltabu.text = null;
    }
    private void fragment1() throws JSONException {
        filter.setVisibility(View.VISIBLE);
        filterText.setVisibility(View.VISIBLE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CatalogFragment fragment = new CatalogFragment(this);
        Bundle bundle1 = new Bundle();
        bundle1.putParcelable("categ", fileHelper.getCategoriesFromFile().get(1));
        String [] asd = {"5ab672c9559d5e049c25a62c",
                "5ab672c9559d5e049c25a62d","5ab672c9559d5e049c25a62e",
                "5ab672c9559d5e049c25a62f","5ab672c9559d5e049c25a630",
                "5ab672c9559d5e049c25a631", "5ab672c9559d5e049c25a632"};
        bundle1.putStringArray("str", asd);
        bundle1.putString("categId", "5ab672c9559d5e049c25a62b");
        fragment.setArguments(bundle1);
        fragmentTransaction.replace(R.id.main, fragment);
        fragmentTransaction.commit();
        removeAllFragments(fragmentManager, fragment);
        drawer.closeDrawer(GravityCompat.START);
        Maltabu.fragmentNumb = 1;
        Maltabu.s1= null;
        Maltabu.s3= null;
        Maltabu.s5= null;
        Maltabu.s4= null;
        Maltabu.s2= null;
        Maltabu.text = null;
        Maltabu.s6= null;
    }
    private void fragment2() throws JSONException { FragmentManager fragmentManager = getSupportFragmentManager();
        filter.setVisibility(View.VISIBLE);
        filterText.setVisibility(View.VISIBLE);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CatalogFragment fragment = new CatalogFragment(this);
        Bundle bundle1 = new Bundle();
        bundle1.putParcelable("categ", fileHelper.getCategoriesFromFile().get(4));
        String [] asd = {"5ab672c9559d5e049c25a645","5ab672c9559d5e049c25a646",
                "5ab672c9559d5e049c25a647","5ab672c9559d5e049c25a648",
                "5ab672c9559d5e049c25a649","5ab672c9559d5e049c25a64a"};
        bundle1.putStringArray("str", asd);
        bundle1.putString("categId", "5ab672c9559d5e049c25a644");
        fragment.setArguments(bundle1);
        fragmentTransaction.replace(R.id.main, fragment);
        fragmentTransaction.commit();
        removeAllFragments(fragmentManager, fragment);
        drawer.closeDrawer(GravityCompat.START);
        Maltabu.fragmentNumb = 2;
        Maltabu.s1= null;
        Maltabu.text = null;
        Maltabu.s4= null;
        Maltabu.s2= null;
        Maltabu.s3= null;
        Maltabu.s5= null;
        Maltabu.s6= null;
    }
    private void fragment3() throws JSONException { FragmentManager fragmentManager = getSupportFragmentManager();
        filter.setVisibility(View.VISIBLE);
        filterText.setVisibility(View.VISIBLE);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CatalogFragment fragment = new CatalogFragment(this);
        Bundle bundle1 = new Bundle();
        bundle1.putParcelable("categ", fileHelper.getCategoriesFromFile().get(2));
        String [] asd = {"5ab672c9559d5e049c25a634","5ab672c9559d5e049c25a635",
                "5ab672c9559d5e049c25a636","5ab672c9559d5e049c25a637","5ab672c9559d5e049c25a638"};
        bundle1.putStringArray("str", asd);
        bundle1.putString("categId", "5ab672c9559d5e049c25a633");
        fragment.setArguments(bundle1);
        fragmentTransaction.replace(R.id.main, fragment);
        fragmentTransaction.commit();
        removeAllFragments(fragmentManager, fragment);
        drawer.closeDrawer(GravityCompat.START);
        Maltabu.fragmentNumb = 3;
        Maltabu.s1= null;
        Maltabu.text = null;
        Maltabu.s4= null;
        Maltabu.s2= null;
        Maltabu.s3= null;
        Maltabu.s5= null;
        Maltabu.s6= null;
    }
    private void fragment4() throws JSONException {  FragmentManager fragmentManager = getSupportFragmentManager();
        filter.setVisibility(View.VISIBLE);
        filterText.setVisibility(View.VISIBLE);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CatalogFragment fragment = new CatalogFragment(this);
        Bundle bundle1 = new Bundle();
        bundle1.putParcelable("categ", fileHelper.getCategoriesFromFile().get(0));
        String [] asd = {"5ab672c9559d5e049c25a63a", "5ab672c9559d5e049c25a63b"};
        bundle1.putStringArray("str", asd);
        bundle1.putString("categId", "5ab672c9559d5e049c25a639");
        fragment.setArguments(bundle1);
        fragmentTransaction.replace(R.id.main, fragment);
        fragmentTransaction.commit();
        removeAllFragments(fragmentManager, fragment);
        drawer.closeDrawer(GravityCompat.START);
        Maltabu.fragmentNumb = 4;
        Maltabu.text = null;
        Maltabu.s1= null;
        Maltabu.s4= null;
        Maltabu.s2= null;
        Maltabu.s3= null;
        Maltabu.s5= null;
        Maltabu.s6= null;
        }
    private void fragment5() throws JSONException {
        FragmentManager fragmentManager = getSupportFragmentManager();
        filter.setVisibility(View.VISIBLE);
        filterText.setVisibility(View.VISIBLE);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CatalogFragment fragment = new CatalogFragment(this);
        Bundle bundle1 = new Bundle();
        bundle1.putParcelable("categ", fileHelper.getCategoriesFromFile().get(3));
        String [] asd = {"5ab672c9559d5e049c25a63f","5ab672c9559d5e049c25a640",
                "5ab672c9559d5e049c25a641","5ab672c9559d5e049c25a642",
                "5ab672c9559d5e049c25a643","5b0bffe2530c6256285a1933","5b0bffe2530c6256285a19b1"
        };
        bundle1.putStringArray("str", asd);
        bundle1.putString("categId", "5ab672c9559d5e049c25a63e");
        fragment.setArguments(bundle1);
        fragmentTransaction.replace(R.id.main, fragment);
        fragmentTransaction.commit();
        removeAllFragments(fragmentManager, fragment);
        Maltabu.text = null;
        drawer.closeDrawer(GravityCompat.START);
        Maltabu.fragmentNumb = 5;
        Maltabu.s4= null;
        Maltabu.s2= null;
        Maltabu.s1= null;
        Maltabu.s3= null;
        Maltabu.s5= null;
        Maltabu.s6= null;
    }
    private void fragment6() throws JSONException {
        filter.setVisibility(View.VISIBLE);
        filterText.setVisibility(View.VISIBLE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CatalogFragment fragment = new CatalogFragment(this);
        Bundle bundle1 = new Bundle();

        bundle1.putParcelable("categ", fileHelper.getCategoriesFromFile().get(5));
        String [] asd =  {"5ab672c9559d5e049c25a64c","5ab672c9559d5e049c25a64d",
                "5ab672c9559d5e049c25a64e","5ab672c9559d5e049c25a64f",
                "5ab672c9559d5e049c25a650"};
        bundle1.putStringArray("str", asd);
        bundle1.putString("categId", "5ab672c9559d5e049c25a64b");
        fragment.setArguments(bundle1);
        fragmentTransaction.replace(R.id.main, fragment);
        fragmentTransaction.commit();
        removeAllFragments(fragmentManager, fragment);
        drawer.closeDrawer(GravityCompat.START);
        Maltabu.fragmentNumb = 6;
        Maltabu.s1= null;
        Maltabu.text = null;
        Maltabu.s4= null;
        Maltabu.s2= null;
        Maltabu.s3= null;
        Maltabu.s5= null;
        Maltabu.s6= null;
    }
    private void fragment7() throws JSONException {
        filter.setVisibility(View.VISIBLE);
        filterText.setVisibility(View.VISIBLE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CatalogFragment fragment = new CatalogFragment(this);
        Bundle bundle1 = new Bundle();
        bundle1.putParcelable("categ", fileHelper.getCategoriesFromFile().get(6));
        String [] asd = {"5afeb741d151e32d5cc245c4","5afeb741d151e32d5cc245c5"};
        bundle1.putStringArray("str", asd);
        bundle1.putString("categId", "5afeb741d151e32d5cc245c3");
        fragment.setArguments(bundle1);
        fragmentTransaction.replace(R.id.main, fragment);
        fragmentTransaction.commit();
        removeAllFragments(fragmentManager, fragment);
        drawer.closeDrawer(GravityCompat.START);
        Maltabu.fragmentNumb = 7;
        Maltabu.s1= null;
        Maltabu.text = null;
        Maltabu.s3= null;
        Maltabu.s4= null;
        Maltabu.s2= null;
        Maltabu.s5= null;
        Maltabu.s6= null;
    }
    private void fragmentSearch() {
        filter.setVisibility(View.GONE);
        filterText.setVisibility(View.GONE);
        findViewById(R.id.filter_badge).setVisibility(View.GONE);
        Context context = LocaleHelper.setLocale(this, Maltabu.lang);
        hottitle.setText(context.getResources().getString(R.string.SearchText));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SearchFragment fragment = new SearchFragment();
        fragmentTransaction.replace(R.id.main, fragment);
        fragmentTransaction.commit();
        removeAllFragments(fragmentManager, fragment);
        drawer.closeDrawer(GravityCompat.START);
        Maltabu.fragmentNumb = 8;
        Maltabu.byTime = true;
        Maltabu.increment = true;
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
                        startActivity(new Intent(MainActivity2.this, AuthAvtivity.class));
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
                    fileHelper.writeUserFile(s1);
                    if (epicDialog != null && epicDialog.isShowing()) {
                        epicDialog.dismiss();
                    }
                    startActivity(new Intent(MainActivity2.this, CabinetActivity.class));
                    setTransition();
                    finish();
                }
            }
        };
        asyncTask1.execute();
    }

    protected void sDialog() {
        epicDialog.setContentView(R.layout.progress_dialog);
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

    public void opentCurrentFragment(int numb){
        switch (numb) {
            case 0:
                fragmentMain();
                break;
            case 1:
                try {
                    fragment1();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    fragment2();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    fragment3();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                try {
                    fragment4();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 5:
                try {
                    fragment5();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 6:
                try {
                    fragment6();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 7:
                try {
                    fragment7();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 8:
                fragmentSearch();
                break;
        }
    }

    public void removeAllFragments(FragmentManager fragmentManager, HotFragment fragment){
            for (int i=0; i<fragmentManager.getFragments().size(); i++){
                if(fragmentManager.getFragments().get(i)!=fragment){
                    fragmentManager.beginTransaction().remove(fragmentManager.getFragments().get(i));
                }
            }
    }

    public void removeAllFragments(FragmentManager fragmentManager, CatalogFragment fragment){
        for (int i=0; i<fragmentManager.getFragments().size(); i++){
            if(fragmentManager.getFragments().get(i)!=fragment){
                fragmentManager.beginTransaction().remove(fragmentManager.getFragments().get(i));
            }
        }
    }

    public void removeAllFragments(FragmentManager fragmentManager, SearchFragment fragment){
        for (int i=0; i<fragmentManager.getFragments().size(); i++){
            if(fragmentManager.getFragments().get(i)!=fragment){
                fragmentManager.beginTransaction().remove(fragmentManager.getFragments().get(i));
            }
        }
    }

    @Override
    public void openCurrentFragment() {
        opentCurrentFragment(Maltabu.fragmentNumb);
    }
}
