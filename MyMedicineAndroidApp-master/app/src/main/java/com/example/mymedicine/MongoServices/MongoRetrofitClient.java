package com.example.mymedicine.MongoServices;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MongoRetrofitClient {
    public static Retrofit instance;

    public static Retrofit getInstance(){
        if (instance == null){
            //For this code to work the base URL must connect to phone/emulator where the app is running to the device
            //the node client is running off, the node client must then be connected to the device running the MongoDB server
            instance = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return instance;

    }
}
