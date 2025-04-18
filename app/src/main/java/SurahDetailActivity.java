import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.al_quranapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SurahDetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AyahAdapter adapter;
    List<AyahModel> ayahList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surah_detail);

        recyclerView = findViewById(R.id.recyclerViewAyah);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ayahList = new ArrayList<>();
        adapter = new AyahAdapter(ayahList);
        recyclerView.setAdapter(adapter);

        int surahNumber = getIntent().getIntExtra("surah_number", 1);
        fetchAyahs(surahNumber);
    }

    private void fetchAyahs(int number) {
        String url = "https://api.alquran.cloud/v1/surah/" + number;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject data = response.getJSONObject("data");
                        JSONArray ayahs = data.getJSONArray("ayahs");

                        for (int i = 0; i < ayahs.length(); i++) {
                            JSONObject ayah = ayahs.getJSONObject(i);
                            int numberInSurah = ayah.getInt("numberInSurah");
                            String text = ayah.getString("text");

                            ayahList.add(new AyahModel(numberInSurah, text));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Toast.makeText(this, "Gagal parsing data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
