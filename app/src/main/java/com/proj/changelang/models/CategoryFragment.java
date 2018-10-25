package com.proj.changelang.models;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.proj.changelang.R;
import com.proj.changelang.adapters.PostAdapter;

import java.util.ArrayList;


public class CategoryFragment extends Fragment {
    private RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_fragment, container, false);
        Bundle bundle = this.getArguments();
        if (this.getArguments() != null && getArguments().containsKey("posts")) {
            ArrayList<Post> posts = bundle.getParcelableArrayList("posts");
            ListView lst = (ListView) view.findViewById(R.id.prodss);
            PostAdapter adapter = new PostAdapter(this.getContext(),R.layout.item_item, posts);
            lst.setAdapter(adapter);
        }
        return view;
    }

}