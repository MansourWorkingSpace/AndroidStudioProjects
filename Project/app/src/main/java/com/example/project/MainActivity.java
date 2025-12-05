package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * MainActivity: displays a list of notes using RecyclerView.
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private NotesDatabaseHelper dbHelper;
    private TextView emptyView;
    public static final int REQ_EDIT_NOTE = 1001;
    private boolean sortByDate = false;
    private boolean sortAsc = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new NotesDatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        emptyView = findViewById(R.id.empty_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, NoteActivity.class);
            startActivityForResult(i, REQ_EDIT_NOTE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_EDIT_NOTE && resultCode == RESULT_OK && data != null) {
            String action = data.getStringExtra("action");
            if (action != null) {
                switch (action) {
                    case "added":
                        Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();
                        break;
                    case "updated":
                        Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
                        break;
                    case "deleted":
                        Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            loadNotes();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sort_date_asc) {
            sortByDate = true;
            sortAsc = true;
            loadNotes();
            return true;
        } else if (id == R.id.action_sort_date_desc) {
            sortByDate = true;
            sortAsc = false;
            loadNotes();
            return true;
        } else if (id == R.id.action_clear_all) {
            new AlertDialog.Builder(this)
                    .setTitle("Clear all")
                    .setMessage("Delete all notes? This cannot be undone.")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dbHelper.deleteAllNotes();
                        Toast.makeText(this, "All notes cleared", Toast.LENGTH_SHORT).show();
                        loadNotes();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadNotes() {
        List<Note> notes;
        if (sortByDate) {
            notes = dbHelper.getAllNotesSortedByDate(sortAsc);
        } else {
            notes = dbHelper.getAllNotes();
        }
        if (notes.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            if (adapter == null) {
                adapter = new NoteAdapter(this, notes, dbHelper);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.setNotes(notes);
                adapter.notifyDataSetChanged();
            }
        }
    }

}