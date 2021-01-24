package com.country.countries;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRepo {

    private final MutableLiveData<ArrayList<Countries>> allCountries;
    private final ArrayList<Countries> countryList;


    public MyRepo(Application application) { //application is subclass of context

         //cant call abstract func but since instance is there we can do this
        countryList = new ArrayList<>();
        allCountries = new MutableLiveData<>();


    }

    public MutableLiveData<ArrayList<Countries>> callAPI(){


        Call<ResponseBody> call = RetrofitClient.getInstance().getapi().countries();
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.code() == 200) {

                    try {
                        assert response.body() != null;

                        JSONArray dataArray = new JSONArray(response.body().string());

                        for (int i = 0; i < dataArray.length(); i++) {

                            Countries modelRecycler = new Countries();
                            JSONObject dataobj = dataArray.getJSONObject(i);

                            modelRecycler.setName(dataobj.getString("name"));
                            modelRecycler.setRegion(dataobj.getString("region"));
                            modelRecycler.setCapital(dataobj.getString("capital"));
                            modelRecycler.setFlag(dataobj.getString("flag"));

                            modelRecycler.setSubregion(dataobj.getString("subregion"));
                            modelRecycler.setPopulation(dataobj.getLong("population"));
                            modelRecycler.setBorders(dataobj.getJSONArray("borders"));

                            modelRecycler.setLanguages(dataobj.getJSONArray("languages"));

                         //   System.out.println("dataobj.getString(\"flag\") = " + dataobj.getJSONArray("languages"));

                            //  productCode.add(dataobj.getString("productCode"));



                            countryList.add(modelRecycler);

                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                        System.out.println("e.getMessage() = " + e.getMessage());

                    }
                    allCountries.setValue(countryList);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //failed
                allCountries.postValue(null);
                System.out.println("t.getMessage() = " + t.getMessage());

            }
        });
        return allCountries;

    }

}
