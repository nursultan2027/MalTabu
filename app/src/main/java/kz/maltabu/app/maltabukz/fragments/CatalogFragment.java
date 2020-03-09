package kz.maltabu.app.maltabukz.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import org.json.JSONException;
import org.json.JSONObject;
import kz.maltabu.app.maltabukz.R;
import kz.maltabu.app.maltabukz.activities.ActivityFragment;
import kz.maltabu.app.maltabukz.adapters.ViewPagerAdapter;
import kz.maltabu.app.maltabukz.helpers.CustomAnimator;
import kz.maltabu.app.maltabukz.helpers.FileHelper;
import kz.maltabu.app.maltabukz.helpers.LocaleHelper;
import kz.maltabu.app.maltabukz.helpers.Maltabu;
import kz.maltabu.app.maltabukz.models.Category;
import kz.maltabu.app.maltabukz.redesign.ui.fragment.CatalogFragmentRedesign;

public class CatalogFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View headerView;
    private FileHelper fileHelper;
    private Category category;
    private JSONObject object;
    private Dialog sortDialog;
    private TextView sort;
    private ActivityFragment af;

    public CatalogFragment(){}
    public CatalogFragment(ActivityFragment af){
        this.af=af;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        headerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.tab_item, null, false);
        View view = inflater.inflate(R.layout.show_catalog, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        sort = view.findViewById(R.id.sort);
        sortDialog = new Dialog(getActivity());
        tabLayout.setupWithViewPager(viewPager);
        printWhiteBoxes();
        final Resources resources = getResources();
        final int [] textRes = new int[]{R.id.text, R.id.text1,R.id.text2,R.id.text3,R.id.text4,R.id.text5,R.id.text6,R.id.text7};
        final int [] imgRes = new int[]{R.id.imageView9,R.id.imageView10,R.id.imageView11,R.id.imageView12,R.id.imageView13,R.id.imageView14,R.id.imageView15,R.id.imageView16};
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView txt = (TextView) tab.getCustomView().findViewById(textRes[tab.getPosition()]);
                txt.setAlpha((float) 1.0);
                txt.setTypeface(null, Typeface.BOLD);
                ImageView img = (ImageView) tab.getCustomView().findViewById(imgRes[tab.getPosition()]);
                img.setAlpha((float) 1.0);
                Maltabu.selectedFragment = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView txt = (TextView) tab.getCustomView().findViewById(textRes[tab.getPosition()]);
                txt.setAlpha((float) 0.5);
                txt.setTypeface(null, Typeface.NORMAL);
                ImageView img = (ImageView) tab.getCustomView().findViewById(imgRes[tab.getPosition()]);
                img.setAlpha((float) 0.5);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        int bl = resources.getColor(R.color.Black);
        if(Maltabu.byTime&&Maltabu.increment) {
            sort.setText(resources.getString(R.string.sort1));
        } else {
            if(Maltabu.byTime&&!Maltabu.increment){
                sort.setText(resources.getString(R.string.sort2));
            } else {
                if (!Maltabu.byTime && !Maltabu.increment){
                    sort.setText(resources.getString(R.string.sort3));
                } else {
                    sort.setText(resources.getString(R.string.sort4));
                }
            }
        }
        for (int l=0; l<tabLayout.getTabCount();l++) {
            if (l==0){
                TextView txt = (TextView) tabLayout.getTabAt(l).getCustomView().findViewById(textRes[l]);
                txt.setTextColor(bl);
                txt.setTypeface(null, Typeface.BOLD);
                txt.setAlpha((float) 1.0);
                ImageView img = (ImageView) tabLayout.getTabAt(l).getCustomView().findViewById(imgRes[l]);
                img.setAlpha((float) 1.0);
            } else {
                TextView txt = (TextView) tabLayout.getTabAt(l).getCustomView().findViewById(textRes[l]);
                txt.setTextColor(bl);
                txt.setAlpha((float) 0.5);
                txt.setTypeface(null, Typeface.NORMAL);
                ImageView img = (ImageView) tabLayout.getTabAt(l).getCustomView().findViewById(imgRes[l]);
                img.setAlpha((float) 0.5);
            }
        }
        viewPager.setCurrentItem(Maltabu.selectedFragment);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        Bundle bundle = this.getArguments();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        String [] catalogStr = bundle.getStringArray("str");
        category = bundle.getParcelable("categ");
        fileHelper = new FileHelper(getActivity());
        try {
            object = fileHelper.diction();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Bundle bundle1 = new Bundle();
        bundle1.putBoolean("isCatalog", false);
        bundle1.putString("catalog", category.getId());
        TextView txtv = getActivity().findViewById(R.id.hottitle);
        CatalogFragmentRedesign fragobj1=CatalogFragmentRedesign.newInstance();
        fragobj1.setArguments(bundle1);
        if(Maltabu.lang.equals("ru")) {
            adapter.addFragment(fragobj1, "Все");
            txtv.setText(category.getName());
            if(category.getId().equals("5ab672c9559d5e049c25a62b")) {
                txtv.setText("Сельхоз животные");
            }
        } else {
            try {
                adapter.addFragment(fragobj1, "Барлығы");
                String kazName;
                if(object!=null)
                    kazName = object.getString(category.getName());
                else
                    kazName=category.getName();
                if(kazName.toString().toLowerCase().equals("жем")){
                    kazName = "Жем-шөп";
                }
                txtv.setText(kazName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for (int i=0; i<category.catalogs.size();i++){
            Bundle bundle2 = new Bundle();
            bundle2.putBoolean("isCatalog", true);
            bundle2.putString("catalog",catalogStr[i]);
            CatalogFragmentRedesign fragobj2 =CatalogFragmentRedesign.newInstance();
            fragobj2.setArguments(bundle2);
            if(Maltabu.lang.toLowerCase().equals("ru")) {
                adapter.addFragment(fragobj2, category.catalogs.get(i).getName());
            } else {
                try {
                    String kazName = object.getString(category.catalogs.get(i).getName());
                    adapter.addFragment(fragobj2, kazName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        viewPager.setAdapter(adapter);

    }

    private void printWhiteBoxes() {
        int [] imgRes = new int[]{R.id.imageView10,R.id.imageView11,R.id.imageView12,R.id.imageView13,R.id.imageView14,R.id.imageView15,R.id.imageView16};
        int [] conRes = new int[]{R.id.con1,R.id.con2,R.id.con3,R.id.con4,R.id.con5,R.id.con6,R.id.con7};
        int [] textRes = new int[]{R.id.text1,R.id.text2,R.id.text3,R.id.text4,R.id.text5,R.id.text6,R.id.text7};
        int [] draw1 = new int[] {};
        ImageView img = (ImageView) headerView.findViewById(R.id.imageView9);
        if(category.getId().equals("5ab672c9559d5e049c25a62b")){
            draw1 = new int[] {R.drawable.cammel,R.drawable.horse,R.drawable.cow,R.drawable.sheep,R.drawable.chicken,R.drawable.goat,R.drawable.rabbit };
            img.setImageResource(R.drawable.animals);
        }
        if(category.getId().equals("5ab672c9559d5e049c25a644")){
            draw1 = new int[] {R.drawable.dog,R.drawable.cat,R.drawable.parrot,R.drawable.hamster,R.drawable.goldfish,R.drawable.turtle};
            img.setImageResource(R.drawable.pets);
        }
        if(category.getId().equals("5ab672c9559d5e049c25a633")){
            draw1 = new int[] {R.drawable.kombikorm,R.drawable.hay,R.drawable.grain,R.drawable.dobavki,R.drawable.otherfeed};
            img.setImageResource(R.drawable.feed);
        }
        if(category.getId().equals("5ab672c9559d5e049c25a639")){
            draw1 = new int[] {R.drawable.wholesale,R.drawable.wholesale2};
            img.setImageResource(R.drawable.wholesale);
        }
        if(category.getId().equals("5ab672c9559d5e049c25a63e")){
            draw1 = new int[] {R.drawable.transportation,R.drawable.shaver,R.drawable.training,R.drawable.medother,R.drawable.otherservice,R.drawable.tractor,R.drawable.equipment};
            img.setImageResource(R.drawable.services);
        }
        if(category.getId().equals("5ab672c9559d5e049c25a64b")){
            draw1 = new int[] {R.drawable.meat,R.drawable.milk,R.drawable.vegetables,R.drawable.fruits,R.drawable.otherproducts};
            img.setImageResource(R.drawable.products);
        }
        if(category.getId().equals("5afeb741d151e32d5cc245c3")){
            draw1 = new int[] {R.drawable.work2,R.drawable.job};
            img.setImageResource(R.drawable.work);
        }
        for(int i=0; i< tabLayout.getTabCount()-1; i++){
            ImageView imgs = (ImageView) headerView.findViewById(imgRes[i]);
            imgs.setImageResource(draw1[i]);
            TextView txt = (TextView) headerView.findViewById(textRes[i]);
            if(Maltabu.lang.toLowerCase().equals("ru")) {
                String tabName = category.catalogs.get(i).getName();
                if(tabName.toLowerCase().equals("бараны")){
                    tabName = "Овцы";
                }
                txt.setText(tabName);
            } else {
                try {
                    String kame = object.getString(category.catalogs.get(i).getName());
                    txt.setText(kame);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            ConstraintLayout con = (ConstraintLayout) headerView.findViewById(conRes[i]);
            tabLayout.getTabAt(i+1).setCustomView(con);
        }
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAnimator.animateHotViewLinear(sort);
                soDialog();
            }
        });

        TextView txt = (TextView) headerView.findViewById(R.id.text);
        if(Maltabu.lang.equals("ru")) {
            txt.setText("Все");
        } else {
            txt.setText("Барлығы");
        }
        ConstraintLayout con = (ConstraintLayout) headerView.findViewById(R.id.con);
        tabLayout.getTabAt(0).setCustomView(con);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    protected void soDialog() {
        sortDialog.setContentView(R.layout.sort_dialog);
        final Resources resources = LocaleHelper.setLocale(getActivity(), Maltabu.lang).getResources();
        final TextView txt1 = (TextView) sortDialog.findViewById(R.id.textView57);
        final TextView txt2 = (TextView) sortDialog.findViewById(R.id.textView59);
        final TextView txt3 = (TextView) sortDialog.findViewById(R.id.textView60);
        final TextView txt4 = (TextView) sortDialog.findViewById(R.id.textView61);
        txt1.setText(resources.getString(R.string.sort1));
        txt2.setText(resources.getString(R.string.sort2));
        txt3.setText(resources.getString(R.string.sort3));
        txt4.setText(resources.getString(R.string.sort4));
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Maltabu.byTime = true;
                Maltabu.increment = true;
                if (sortDialog != null && sortDialog.isShowing()) {
                    sortDialog.dismiss();
                }
                sort.setText(resources.getString(R.string.sort1));
                af.openCurrentFragment();
            }
        });
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Maltabu.byTime = true;
                Maltabu.increment = false;
                if (sortDialog != null && sortDialog.isShowing()) {
                    sortDialog.dismiss();
                }
                sort.setText(resources.getString(R.string.sort2));
                af.openCurrentFragment();
            }
        });
        txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Maltabu.byTime = false;
                Maltabu.increment = false;
                if (sortDialog != null && sortDialog.isShowing()) {
                    sortDialog.dismiss();
                }
                sort.setText(resources.getString(R.string.sort3));
                af.openCurrentFragment();
            }
        });
        txt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Maltabu.byTime = false;
                Maltabu.increment = true;
                if (sortDialog != null && sortDialog.isShowing()) {
                    sortDialog.dismiss();
                }
                sort.setText(resources.getString(R.string.sort4));
                af.openCurrentFragment();
            }
        });

        sortDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sortDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Maltabu.filterModel=null;
    }
}
