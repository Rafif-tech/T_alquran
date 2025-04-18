package com.example.al_quranapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listViewSurah;
    ArrayList<String> surahList;
    ArrayAdapter<String> adapter;

    String url = "https://api.alquran.cloud/v1/surah"; // endpoint API

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewSurah = findViewById(R.id.listViewSurah);
        surahList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, surahList);
        listViewSurah.setAdapter(adapter);

        getSurahFromAPI();
    }

    private void getSurahFromAPI() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray dataArray = response.getJSONArray("data");

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject surahObj = dataArray.getJSONObject(i);
                            int number = surahObj.getInt("number");
                            String englishName = surahObj.getString("englishName");
                            String name = surahObj.getString("name");

                            surahList.add(number + ". " + name + " (" + englishName + ")");
                        }

                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        Toast.makeText(this, "Gagal parsing data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }
}
