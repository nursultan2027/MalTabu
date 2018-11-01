package com.proj.changelang.models;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.proj.changelang.R;
import com.proj.changelang.adapters.PostAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;


public class ImageFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    public static ImageFragment newInstance(int page, String url) {
        ImageFragment imgFragment = new ImageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        arguments.putString(ARGUMENT_PAGE_NUMBER, url);
        imgFragment.setArguments(arguments);
        return imgFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_item,null);
        Bundle bundle = this.getArguments();
        if (this.getArguments() != null && getArguments().containsKey(ARGUMENT_PAGE_NUMBER)) {
            String url = bundle.getString(ARGUMENT_PAGE_NUMBER);
            ImageView card = (ImageView) view.findViewById(R.id.imgPage);
            Picasso.with(getContext()).load("http://maltabu.kz/"
                    +url).into(card);
        }
        return view;
    }

}