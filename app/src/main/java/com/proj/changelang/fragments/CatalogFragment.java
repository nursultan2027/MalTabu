package com.proj.changelang.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.proj.changelang.R;
import com.proj.changelang.adapters.ViewPagerAdapter;
import com.proj.changelang.helpers.FileHelper;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.Category;
import com.proj.changelang.models.Image;

import org.json.JSONException;

public class CatalogFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View headerView;
    private FileHelper fileHelper;
    private Category category;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        headerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.tab_item, null, false);
        View view = inflater.inflate(R.layout.show_catalog, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
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
                ImageView img = (ImageView) tab.getCustomView().findViewById(imgRes[tab.getPosition()]);
                img.setAlpha((float) 1.0);
                Maltabu.selectedFragment = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView txt = (TextView) tab.getCustomView().findViewById(textRes[tab.getPosition()]);
                txt.setAlpha((float) 0.3);
                ImageView img = (ImageView) tab.getCustomView().findViewById(imgRes[tab.getPosition()]);
                img.setAlpha((float) 0.3);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for (int l=0; l<tabLayout.getTabCount();l++)
        {
            if (l==0){
                TextView txt = (TextView) tabLayout.getTabAt(l).getCustomView().findViewById(textRes[l]);
                txt.setTextColor(resources.getColor(R.color.Black));
                txt.setAlpha((float) 1.0);
                ImageView img = (ImageView) tabLayout.getTabAt(l).getCustomView().findViewById(imgRes[l]);
                img.setAlpha((float) 1.0);
            } else {
                TextView txt = (TextView) tabLayout.getTabAt(l).getCustomView().findViewById(textRes[l]);
                txt.setTextColor(resources.getColor(R.color.Black));
                txt.setAlpha((float) 0.3);
                ImageView img = (ImageView) tabLayout.getTabAt(l).getCustomView().findViewById(imgRes[l]);
                img.setAlpha((float) 0.3);
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
        Bundle bundle1 = new Bundle();
        bundle1.putBoolean("isCatalog", false);
        bundle1.putString("catalog", category.getId());
        CategoryFragment fragobj1=new CategoryFragment();
        fragobj1.setArguments(bundle1);
        if(Maltabu.lang.equals("ru")) {
            adapter.addFragment(fragobj1, "все");
        } else {
            adapter.addFragment(fragobj1, "барлық");
        }
        for (int i=0; i<category.catalogs.size();i++){
            Bundle bundle2 = new Bundle();
            bundle2.putBoolean("isCatalog", true);
            bundle2.putString("catalog",catalogStr[i]);
            CategoryFragment fragobj2 =new CategoryFragment();
            fragobj2.setArguments(bundle2);
            if(Maltabu.lang.toLowerCase().equals("ru")) {
                adapter.addFragment(fragobj2, category.catalogs.get(i).getName());
            } else {
                try {
                    String kazName = fileHelper.diction().getString(category.catalogs.get(i).getName());
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
            draw1 = new int[] {R.drawable.cammel,R.drawable.horse,R.drawable.cow,R.drawable.sheep,R.drawable.goat,R.drawable.chicken,R.drawable.rabbit };
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
            draw1 = new int[] {R.drawable.transportation,R.drawable.shaver,R.drawable.training,R.drawable.medother,R.drawable.equipment,R.drawable.tractor,R.drawable.otherservice};
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
                txt.setText(category.catalogs.get(i).getName());
            } else {
                try {
                    String kame = fileHelper.diction().getString(category.catalogs.get(i).getName());
                    txt.setText(kame);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            ConstraintLayout con = (ConstraintLayout) headerView.findViewById(conRes[i]);
            tabLayout.getTabAt(i+1).setCustomView(con);
        }

        TextView txt = (TextView) headerView.findViewById(R.id.text);
        if(Maltabu.lang.equals("ru")) {
            txt.setText("все");
        } else {
            txt.setText("барлық");
        }
        ConstraintLayout con = (ConstraintLayout) headerView.findViewById(R.id.con);
        tabLayout.getTabAt(0).setCustomView(con);
    }
}