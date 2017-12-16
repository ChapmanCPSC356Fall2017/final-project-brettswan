package com.example.brettswan.stockpredictor;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class API {
    private static final String STOCK_API_URL = "http://dev.markitondemand.com/MODApis/Api/v2/Quote/json?symbol=";
    private static final String PYTHON_SERVER_ADDRESS = "http://23.241.239.34:3500/predictions";

    private static JsonResult stockData;

    public static void webLookup(String stock, final Callback callback){
        OkHttpClient client = new OkHttpClient();
        stockData = JsonResult.getData();

        String url = STOCK_API_URL + stock;


        Request req = new Request.Builder()
                .url(url)
                .build();

        client.newCall(req).enqueue(new com.squareup.okhttp.Callback()
        {
            @Override
            public void onFailure(Request request, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String json = response.body().string();
                Log.i("JSON",json);
                Gson gson = new Gson();

                JsonResult jsonResults = gson.fromJson(json, JsonResult.class);

                callback.onResult(jsonResults);

            }
        });
    }

    public static void homeLookup(String stock, String date, final Callback callback){
        OkHttpClient client = new OkHttpClient();
        stockData = JsonResult.getData();

        String url = PYTHON_SERVER_ADDRESS + "/"+ stock + "/" + date;


        Request req = new Request.Builder()
                .url(url)
                .build();

        client.newCall(req).enqueue(new com.squareup.okhttp.Callback()
        {
            @Override
            public void onFailure(Request request, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String json = response.body().string();
                Log.i("JSON",json);
                Gson gson = new Gson();

                HomeResult jsonResults = gson.fromJson(json, HomeResult.class);

                callback.onHomeResult(jsonResults);

            }
        });
    }

    public interface Callback
    {
        void onFailure(Exception e);
        void onResult(JsonResult results);
        void onHomeResult(HomeResult results);
    }

}
