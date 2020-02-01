package kz.maltabu.app.maltabukz.helpers;

import kz.maltabu.app.maltabukz.adapters.PostRecycleAdapterNew;
import kz.maltabu.app.maltabukz.models.FilterModel;
import kz.maltabu.app.maltabukz.models.Post;

import java.util.ArrayList;

public class Maltabu {
    public static final String API_key = "16364ff9-f4b0-45ac-ad8f-820d663f50a2";
    public static String s1 = null;
    public static String s2 = null;
    public static String s3 = null;
    public static String s4 = null;
    public static String s5 = null;
    public static String s6 = null;
    public static String text = null;
    public static int searchPage = 1;
    public static ArrayList<Post> posts;
    public static PostRecycleAdapterNew adapter;
    public static boolean byTime = true;
    public static boolean increment = true;
    public static String lang = null;
    public static String token=null;
    public static String version = "1.0.53";
    public static String isAuth="false";
    public static String topPrice="150";
    public static String hotPrice="250";
    public static int fragmentNumb = 0;
    public static int selectedFragment = 0;
    public static FilterModel filterModel=null;
}