package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WeekActivity extends AppCompatActivity {
    String cityName = "";
    ImageView back;
    TextView nameCity;
    ListView lv;
    CustomAdapter customAdapter;
    ArrayList<Thoitiet> manThoitiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Anhxa();
        setContentView(R.layout.activity_week);
        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        Log.d("Ket qua", city);
        if (city.equals("")) {
            Get7DayData("Saigon");
        } else {
            Get7DayData(city);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void Anhxa() {
        back = findViewById(R.id.back);
        nameCity = findViewById(R.id.textViewNameCity);
        lv = findViewById(R.id.listView);
        manThoitiet = new ArrayList<Thoitiet>();
        customAdapter = new CustomAdapter(WeekActivity.this, manThoitiet);
        lv.setAdapter(customAdapter)    ;
    }

    private void Get7DayData(String city) {
        RequestQueue requestQueue = Volley.newRequestQueue(WeekActivity.this);
        String url = "http://api.weatherapi.com/v1/forecast.json?key=29931f8e9e1745d6b3882750241005&q=" + city + "&days=3";
//        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metrics&appid=725fec9a2b521c3ac949aacaae1aa7c6";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectcity = jsonObject.getJSONObject("city");
                            String name = jsonObjectcity.getString("name");
                            nameCity.setText(name);

                            JSONArray jsonArray = jsonObject.getJSONArray("list");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjecItem = jsonArray.getJSONObject(i);
                                String ngay = jsonObjecItem.getString("dt");

                                Long l = Long.valueOf(ngay);
                                Date date = new Date(1*1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-mm-dd hh:mm:ss");
                                String day = simpleDateFormat.format(date);

                                JSONObject jsonObjecTemp = jsonObjecItem.getJSONObject("temp");
                                String max = jsonObjecItem.getString("max");
                                String min = jsonObjecItem.getString("min");

                                Double a = Double.valueOf(max);
                                Double b = Double.valueOf(min);
                                String nhietdoMax = String.valueOf(a.intValue());
                                String nhietdoMin = String.valueOf(b.intValue());

                                JSONArray jsonArrayWeather = jsonObjecItem.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);

                                String status = jsonObjectWeather.getString("desription");
                                String icon = jsonObjectWeather.getString("icon");

                                manThoitiet.add(new Thoitiet(ngay, status,icon, nhietdoMax, nhietdoMin));
                            }
                            customAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Ket qua", error.toString());
                    }
                });
        requestQueue.add(stringRequest);
    }
}