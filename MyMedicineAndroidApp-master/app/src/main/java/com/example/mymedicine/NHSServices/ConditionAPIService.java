package com.example.mymedicine.NHSServices;

import com.example.mymedicine.NHSClasses.Condition;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ConditionAPIService {

    //Header of API search, this is the API Key
    @Headers({
            "subscription-key: *ENTER YOUR API KEY HERE*"
    })

    //This is the condition search value
    @GET("conditions/{condition}")
    Single<Condition> getPersonData(@Path("condition") String conditionSearchInput);
}
