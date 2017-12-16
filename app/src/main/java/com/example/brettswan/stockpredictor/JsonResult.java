package com.example.brettswan.stockpredictor;

import com.google.gson.annotations.SerializedName;

public class JsonResult {

    private static JsonResult instance;

    static {
        instance = new JsonResult();
    }

    public static JsonResult getData(){
        if (instance == null)
        {
            instance = new JsonResult();
        }
        return instance;
    }

    @SerializedName("Symbol")
    public String symbol;

    @SerializedName("Name")
    public String  name;

    @SerializedName("LastPrice")
    public Double currentPrice;

    @SerializedName("ChangePercent")
    public Double percentChange;

    @SerializedName("Volume")
    public long volume;

    @SerializedName("MarketCap")
    public long marketCap;
}
