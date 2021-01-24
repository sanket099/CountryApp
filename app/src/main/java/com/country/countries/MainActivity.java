package com.country.countries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Adapter.OnNoteList {

    Adapter adapter;
    RecyclerView recyclerView;
    ArrayList<Countries> modelRecyclerArrayList;
    MyViewModel myViewModel;
    EditText et_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_search = findViewById(R.id.et_search);
        modelRecyclerArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        initRecycler();


        myViewModel.loadData().observe(this, new Observer<ArrayList<Countries>>() {
                    @Override
                    public void onChanged(ArrayList<Countries> countries) {
                        if(countries!=null){
                            modelRecyclerArrayList = countries;
                            adapter.updateList(modelRecyclerArrayList);

                        }
                        //adapter.notifyDataSetChanged();

                    }
                });

        search();




    }

    private void initRecycler() {
        adapter = new Adapter(this,this,modelRecyclerArrayList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

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