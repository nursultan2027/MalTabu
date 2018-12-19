package com.proj.changelang.helpers;

import android.content.Context;

import com.google.gson.Gson;
import com.proj.changelang.models.Catalog;
import com.proj.changelang.models.Category;
import com.proj.changelang.models.City;
import com.proj.changelang.models.Region;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FileHelper {
    private Context context;
    public FileHelper(Context context) {
        this.context = context;
    }

    public String readDictionary(){return readFile("dictionary");}
    public void writeDictionary(String s) { writeFile(s, "dictionary");}
    public String readToken(){return readFile("token");}
    public void writeToken(String s) { writeFile(s, "token");}
    public String readDataFile(){return readFile("data");}
    public void writeDataFile(String s) { writeFile(s, "data");}
    public String readUserFile(){return readFile("user");}
    public void writeUserFile(String s) { writeFile(s, "user");}

    public String readFile(String str) {
        FileInputStream fin = null;
        String text = "";
        try {
            fin = context.openFileInput(str);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            text = new String (bytes);
            return text;
        }
        catch(IOException ex) {
        }
        finally{
            try{
                if(fin!=null)
                    fin.close();
            }
            catch(IOException ex){
            }
        }
        return text;
    }
    public void writeFile(String s, String fileName) {
        FileOutputStream fos = null;
        try {
            String text = s;
            fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
            fos.write(text.getBytes());
        }
        catch(IOException ex) {

        }
        finally{
            try{
                if(fos!=null)
                    fos.close();
            }
            catch(IOException ex){

            }
        }
    }


    public ArrayList<Region> getRegionsFromFile() throws JSONException {
        Gson googleJson = new Gson();
        ArrayList<Region> regionArrayList = new ArrayList<>();
        JSONArray regions = new JSONObject(readDataFile()).getJSONArray("regions");
        ArrayList jsonObjList = googleJson.fromJson(String.valueOf(regions), ArrayList.class);
        for (int i=0; i<jsonObjList.size(); i++)
        {
            JSONObject categoryObject = regions.getJSONObject(i);
            Region regionRegion = new Region(categoryObject.getString("_id"), categoryObject.getString("value"), categoryObject.getString("name"), categoryObject.getString("firstCase"));
            ArrayList<City> cityArrayList = new ArrayList<>();
            JSONArray cities = categoryObject.getJSONArray("cities");
            ArrayList jsonObjList2 = googleJson.fromJson(String.valueOf(cities), ArrayList.class);
            for(int j=0; j<jsonObjList2.size(); j++) {
                JSONObject cityObject = cities.getJSONObject(j);
                City city = new City(cityObject.getString("_id"), cityObject.getString("value"), cityObject.getString("name"), cityObject.getString("firstCase"));
                cityArrayList.add(city);
            }
            regionRegion.cities = cityArrayList;
            regionArrayList.add(regionRegion);
        }
        return regionArrayList;
    }
    public ArrayList<Category> getCategoriesFromFile() throws JSONException {
        Gson googleJson = new Gson();
        ArrayList<Category> categoryArrayList= new ArrayList<>();
        JSONArray categs = new JSONObject(readDataFile()).getJSONArray("categories");
        ArrayList categsList = googleJson.fromJson(String.valueOf(categs), ArrayList.class);
        for(int k=0; k<categsList.size();k++)
        {
            JSONObject regionObject = categs.getJSONObject(k);
            int count = regionObject.getJSONObject("stat").getInt("count");
            Category category = new Category(regionObject.getString("_id"), regionObject.getString("value"), regionObject.getString("name"), regionObject.getString("firstCase"),count);
            ArrayList<Catalog> catalogsArrayList = new ArrayList<>();
            JSONArray catalogs = regionObject.getJSONArray("catalogs");
            ArrayList jsonObjList3 = googleJson.fromJson(String.valueOf(catalogs), ArrayList.class);
            for (int l=0; l<jsonObjList3.size();l++)
            {
                JSONObject catalogObj = catalogs.getJSONObject(l);
                int count2 = catalogObj.getJSONObject("stat").getInt("count");
                Catalog catalog = new Catalog(catalogObj.getString("_id"), catalogObj.getString("value"), catalogObj.getString("name"), catalogObj.getString("firstCase"),count2);
                catalogsArrayList.add(catalog);
            }
            category.catalogs=catalogsArrayList;
            categoryArrayList.add(category);
        }
        return categoryArrayList;
    }
    public JSONObject diction() throws JSONException {
        return new JSONObject(readDictionary());
    }

}