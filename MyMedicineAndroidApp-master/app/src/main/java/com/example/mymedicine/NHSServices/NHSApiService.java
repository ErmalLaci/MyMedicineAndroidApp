package com.example.mymedicine.NHSServices;

import com.example.mymedicine.NHSClasses.Conditions;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NHSApiService {

    //Header of API search, this is the API Key
    @Headers({
            "subscription-key: *ENTER YOUR API KEY HERE*"
    })

    //This is the condition search value
    @GET("conditions/{condition}")
    Single<Conditions> getPersonData(@Path("condition") String conditionSearchInput,
                                     @Query("category") String categoryChosen,
                                     @Query("genre") String condString);
}
