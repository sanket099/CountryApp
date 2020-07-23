package com.country.countries;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmadrosid.svgloader.SvgLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Details extends AppCompatActivity {

    TextView name, capital, languages, region , subregion, population, borders;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);



        name = findViewById(R.id.tv_name);
        capital = findViewById(R.id.tv_type);
        languages = findViewById(R.id.tv_date_2);
        region = findViewById(R.id.tv_price_show);
        subregion = findViewById(R.id.buyer_name);
        population = findViewById(R.id.etemail);
        borders = findViewById(R.id.quant_required);
        iv = findViewById(R.id.iv_product_seller);


        setTitle("Country Details");

        Intent intent = getIntent();

        if(intent == null){
            System.out.println("error here");
        }
        else{
            Fill(intent);
        }


    }

    private void Fill(Intent intent) {

        String lang = "";
        String border = "";
        String iso1 ="";
        String iso2 = "";
        String lan_name = "";
        String native_name = "";



        name.setText(intent.getStringExtra("name"));
        capital.setText(intent.getStringExtra("capital"));
        region.setText(intent.getStringExtra("region"));
        try {
            String bo = (intent.getStringExtra("borders"));
            System.out.println("bo = " + bo);
            JSONArray jsonArray = new JSONArray(bo);
            System.out.println("jsonArray = " + jsonArray);
            for (int i = 0; i < jsonArray.length(); i++) {
                String s = null;
                try {
                    s = (String) jsonArray.get(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                border = border + " " + String.valueOf(s);

            }

            borders.setText(border);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        population.setText(String.valueOf(intent.getLongExtra("population",0)));
        subregion.setText(intent.getStringExtra("subregion"));

        String stringExtra = intent.getStringExtra("languages");
        JSONArray array = null;
        try {
            array = new JSONArray(stringExtra);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = null;
            try {
                jsonObject = array.getJSONObject(i);
                iso1 = jsonObject.getString("iso639_1");
                iso2 = jsonObject.getString("iso639_2");
                lan_name = jsonObject.getString("name");
                native_name = jsonObject.getString("nativeName");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            iso1 = "Iso639_1 : " + iso1;
            iso2 = "Iso639_2 : " + iso2;
            lan_name = "Name : " + lan_name;
            native_name = "Native Name : " + native_name;

            lang = lang + "\n" + iso1 + "\n" + iso2+ "\n" + lan_name + "\n" + native_name+"\n\n" ;



        }

        languages.setText(lang);


        SvgLoader.pluck()
                .with(this)
                .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                .load(intent.getStringExtra("flag"), iv);






    }
}