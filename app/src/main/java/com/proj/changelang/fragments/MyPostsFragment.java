package com.proj.changelang.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.proj.changelang.R;
import com.proj.changelang.activities.FilterActivity;
import com.proj.changelang.adapters.PostRecycleAdapter;
import com.proj.changelang.helpers.EndlessListener;
import com.proj.changelang.helpers.FileHelper;
import com.proj.changelang.helpers.Maltabu;
import com.proj.changelang.models.FilterModel;
import com.proj.changelang.models.Image;
import com.proj.changelang.models.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class MyPostsFragment extends Fragment {
    private View view;
    private Spinner spinner;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.my_posts_fragment, container, false);
        spinner = (Spinner) view.findViewById(R.id.spinner1);
        ArrayList<String> arr = new ArrayList<>();
        arr.add("Активные объявления");
        arr.add("Объявления на модерации");
        arr.add("Объявления в архиве");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, arr);
        spinner.setAdapter(adapter);
        return view;
    }
}