package kz.maltabu.app.maltabukz.helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyFarebaseHelper {
    private Context context;
    public MyFarebaseHelper(Context context){
        this.context=context;
    }
    public void sendNotificationToken(String clientToken, String firebaseToken){
        final OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("clientToken", clientToken)
                .add("firebaseToken", firebaseToken)
                .build();
        final Request request2 = new Request.Builder()
                .url("https://maltabu.kz/v1/api/clients/self/setNotificationToken")
                .post(formBody)
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
                    try {
                        JSONObject jsonResponse = new JSONObject(s1);
                        if(jsonResponse.has("message")){
                            if(jsonResponse.getString("message").equals("Updated")){
                                Log.d("senTag", "OK");
                            } else {
                                if(jsonResponse.has("status")){
                                    if(jsonResponse.getInt("status")==404){
                                        Log.d("senTag", "invalid client token");
                                    } else {
                                        Log.d("senTag", "invalid body");
                                    }
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        asyncTask1.execute();
    }
}
