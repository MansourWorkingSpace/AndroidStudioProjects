package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String[] countryNames = getResources().getStringArray(R.array.country_list);
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

        List<Country> list = new ArrayList<>();
        for (int i = 0; i < countryNames.length; i++) {
            String name = countryNames[i];
            String flag = (i < flagNames.length) ? flagNames[i] : "";
            list.add(new Country(name, flag));
        }

        MyCustomAdapter adapter = new MyCustomAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }
}
