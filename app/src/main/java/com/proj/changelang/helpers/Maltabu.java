package com.proj.changelang.helpers;

import com.proj.changelang.adapters.PostRecycleAdapter;
import com.proj.changelang.models.Category;
import com.proj.changelang.models.FilterModel;
import com.proj.changelang.models.Post;
import com.proj.changelang.models.Region;

import org.json.JSONObject;

import java.util.ArrayList;

public class Maltabu {
    public static String s1 = null;
    public static String s2 = null;
    public static String s3 = null;
    public static String s4 = null;
    public static String s5 = null;
    public static String s6 = null;
    public static String text = null;
    public static ArrayList<Post> posts;
    public static PostRecycleAdapter adapter;
    public static boolean byTime = true;
    public static boolean increment = true;
    public static String lang = null;
    public static String token=null;
    public static String isAuth="false";
    public static int fragmentNumb = 0;
    public static int selectedFragment = 0;
    public static FilterModel filterModel=null;
}