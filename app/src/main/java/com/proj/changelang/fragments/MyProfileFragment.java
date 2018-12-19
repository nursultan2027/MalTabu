package com.proj.changelang.fragments;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.proj.changelang.R;
import com.proj.changelang.helpers.FileHelper;

import org.json.JSONException;
import org.json.JSONObject;


public class MyProfileFragment extends Fragment {
    private View view;
    private ConstraintLayout constraintLayout32;
    private FileHelper fileHelper;
    private TextView email, number, balance;
    private JSONObject object;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.my_profile_fragment, container, false);
        fileHelper = new FileHelper(getActivity());
        email = (TextView) view.findViewById(R.id.textView35);
        number = (TextView) view.findViewById(R.id.number);
        constraintLayout32 = view.findViewById(R.id.constraintLayout32);
        balance = (TextView) view.findViewById(R.id.textView37);
        try {
            object = new JSONObject(fileHelper.readUserFile());
            email.setText(object.getString("name"));
            number.setText(String.valueOf(object.getInt("number")));
            balance.setText(String.valueOf(object.getInt("balance"))+" ед.");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        constraintLayout32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager host = (ViewPager) getActivity().findViewById(R.id.pager);
                host.setCurrentItem(0);
            }
        });
        return view;
    }
}