package com.example.myapplication;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Using string-array resource for items; no Map/List imports required

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        String[] countryNames = getResources().getStringArray(R.array.country_list);

        // Map country names to drawable resource names (must match files in
        // res/drawable)
        String[] flagNames = new String[] {
                "flag_bresil",
                "flag_ksa",
                "flag_espagne",
                "flag_argentine",
                "flag_australie",
                "flag_canada",
                "flag_allemagne",
                "flag_chine",
                "flag_usa"
        };

        List<Country> countryList = new ArrayList<>();
        for (int i = 0; i < countryNames.length; i++) {
            String name = countryNames[i];
            String flag = (i < flagNames.length) ? flagNames[i] : "";
            countryList.add(new Country(name, flag));
        }

        MyCustomListAdapter customAdapter = new MyCustomListAdapter(countryList, this);
        listView.setAdapter(customAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}