package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button quitButton ;
    Button openButton ;
    public void Display(View v) {
        Toast.makeText(this, "Quit is clicked!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        quitButton = findViewById(R.id.B1);
        openButton = findViewById(R.id.B2);
        quitButton.setOnClickListener(this);
        openButton.setOnClickListener(this);

        /*Button quitButton = findViewById(R.id.B1);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Quit is clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        Button openButton = findViewById(R.id.B2);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Open is clicked!", Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    @Override
    public void onClick(View v) {
        if(v == quitButton){
            Toast.makeText(MainActivity.this, "Quit is clicked!", Toast.LENGTH_SHORT).show();
        } else if (v == openButton) {
            // start DialogActivity which uses a dialog-style theme
            android.content.Intent intent = new android.content.Intent(MainActivity.this, DialogActivity.class);
            startActivity(intent);
        }
    }
}