package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DialogActivity extends AppCompatActivity {

    private static final int DIALOG_ALERT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        // regular click behavior (optional)
        Button quitBtn = findViewById(R.id.B3);
        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog();
            }
        });

        // long click behavior: show dialog identified by DIALOG_ALERT
        quitBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDialog(DIALOG_ALERT);
                return true; // indicate the event was handled
            }
        });
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Exit");
        builder.setMessage("Are you sure you want to quit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // finish the activity (quit)
                finish();
            }
        });
        builder.setNegativeButton("No", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected android.app.Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_ALERT:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Did you want to close the app??");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new YesOnClickListener());
                builder.setNegativeButton("No", new NoOnClickListener());
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }

        return super.onCreateDialog(id);
    }

    // Listener classes used by onCreateDialog
    private class YesOnClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // close the activity / app
            DialogActivity.this.finish();
        }
    }

    private class NoOnClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // show a toast and keep the app running
            android.widget.Toast.makeText(getApplicationContext(), "App still running", android.widget.Toast.LENGTH_SHORT).show();
        }
    }
}
