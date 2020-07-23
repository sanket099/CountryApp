package com.country.countries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Adapter.OnNoteList {

    Adapter adapter;
    RecyclerView recyclerView;
    ArrayList<Countries> modelRecyclerArrayList;
    EditText et_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_search = findViewById(R.id.et_search);
        recyclerView = findViewById(R.id.recycler_view);

        Json();


    }

    private void Json() {

        Call<ResponseBody> call = RetrofitClient.getInstance().getapi().countries();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    assert response.body() != null;
                    writeRecycler(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: Try Again", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void writeRecycler(String response) {

        try {
            //getting the whole json object from the response
          //  JSONObject obj = new JSONObject(response);

            JSONArray dataArray = new JSONArray(response);


            modelRecyclerArrayList = new ArrayList<>();
            //JSONArray dataArray  = obj.getJSONArray("products");

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

                System.out.println("dataobj.getString(\"flag\") = " + dataobj.getJSONArray("languages"));

                //  productCode.add(dataobj.getString("productCode"));



                modelRecyclerArrayList.add(modelRecycler);

            }

            adapter = new Adapter(this,this,modelRecyclerArrayList,this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

          /*  if(getIntent().getStringArrayListExtra("array")!=null){
                etsearch.setVisibility(View.VISIBLE);
                // filter.setVisibility(View.VISIBLE);

                getfilter();
            }
            else {
                search2();
            }*/

          search();


        } catch (JSONException e) {
            e.printStackTrace();
            //"error" + e.getMessage());
        }




    }

    public void search() {

        et_search.setVisibility(View.VISIBLE);

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });


    }
    private void filter(String s){
        ArrayList<Countries> filteredlist = new ArrayList<>();
        for(Countries countries : modelRecyclerArrayList){
            if(countries.getName().toLowerCase().contains(s.toLowerCase())){
                filteredlist.add(countries);
            }

        }

        adapter.filteredlist(filteredlist);
    }


    @Override
    public void OnnoteClick(Countries userClass) {

        Intent intent = new Intent(MainActivity.this,Details.class);
        intent.putExtra("name",userClass.getName());
        intent.putExtra("capital",userClass.getCapital());
        intent.putExtra("flag",userClass.getFlag());
        intent.putExtra("population",userClass.getPopulation());
        intent.putExtra("region",userClass.getRegion());
        intent.putExtra("subregion",userClass.getSubregion());
        intent.putExtra("languages",userClass.getLanguages().toString());
        intent.putExtra("borders",userClass.getBorders().toString());


        startActivity(intent);


    }
}