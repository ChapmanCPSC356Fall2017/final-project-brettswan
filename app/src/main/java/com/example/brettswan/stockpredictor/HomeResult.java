package com.example.brettswan.stockpredictor;

import com.google.gson.annotations.SerializedName;

/**
 * Created by brettswan on 12/15/17.
 */

public class HomeResult {

    private static HomeResult instance;

    static {
        instance = new HomeResult();
    }

    public static HomeResult getData(){
        if (instance == null)
        {
            instance = new HomeResult();
        }
        return instance;
    }

    @SerializedName("symbol")
    public String symbol;

    @SerializedName("Average Volume")
    public Double  average_volume;

    @SerializedName("Average price spread")
    public Double price_spread;

}