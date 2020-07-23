package com.country.countries;



import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface api {

    @GET("rest/v2/region/asia")//endpoint

    Call<ResponseBody> countries( //response we shall get



    );


}
