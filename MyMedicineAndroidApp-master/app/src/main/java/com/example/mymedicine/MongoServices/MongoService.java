package com.example.mymedicine.MongoServices;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import io.reactivex.Observable;

public interface MongoService {

    @POST("sendTreatment")
    @FormUrlEncoded
    Observable<String> sendTreatment(@Field("conditionNames") String conditionNames,
                                     @Field("treatmentNumbers") String treatmentNumbers);

    @POST("sendTreatmentReview")
    @FormUrlEncoded
    Observable<String> sendTreatmentReview(@Field("id") String id,
                                     @Field("conditionNames") String conditionNames,
                                     @Field("treatmentNumbers") String treatmentNumbers,
                                     @Field("treatmentReviews") String treatmentReviews);

    @POST("createId")
    @FormUrlEncoded
    Observable<String> createId(@Field("age") int age,
                                @Field("gender") int gender,
                                @Field("activity") int activity,
                                @Field("stress") int stress);

    @POST("updateUser")
    @FormUrlEncoded
    Observable<String> updateUser(@Field("id") String id,
                                  @Field("age") int age,
                                  @Field("gender") int gender,
                                  @Field("activity") int activity,
                                  @Field("stress") int stress);

}

