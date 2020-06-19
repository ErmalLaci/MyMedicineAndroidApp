package com.example.mymedicine.NHSServices;

import com.example.mymedicine.NHSClasses.Treatment;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface TreatmentAPIService {

    //Header of API search, this is the API Key
    @Headers({
            "subscription-key: *ENTER YOUR API KEY HERE*"
    })

    //This is the condition search value
    @GET("conditions/{condition}")
    Single<Treatment> getPersonData(@Path("condition") String treatmentInput);
}
