package com.example.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * NoteActivity: add or edit a note.
 */
public class NoteActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE_ID = "note_id";

    private EditText etTitle, etContent;
    private Button btnSave, btnDelete;
    private NotesDatabaseHelper dbHelper;
    private int editingId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        dbHelper = new NotesDatabaseHelper(this);

        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);
        btnSave = findViewById(R.id.btn_save);
        // btn_delete may be absent in the layout; resolve by name at runtime to avoid
        // IDE unresolved-id errors
        btnDelete = null;
        int deleteResId = getResources().getIdentifier("btn_delete", "id", getPackageName());
        if (deleteResId != 0) {
            btnDelete = findViewById(deleteResId);
        }

        if (getIntent() != null && getIntent().hasExtra(EXTRA_NOTE_ID)) {
            editingId = getIntent().getIntExtra(EXTRA_NOTE_ID, -1);
            Note n = dbHelper.getNoteById((int) editingId);
            if (n != null) {
                etTitle.setText(n.getTitle());
                etContent.setText(n.getContent());
            }
            if (btnDelete != null)
                btnDelete.setVisibility(View.VISIBLE);
        } else {
            if (btnDelete != null)
                btnDelete.setVisibility(View.GONE);
        }

        btnSave.setOnClickListener(v -> saveNote());

        if (btnDelete != null) {
            btnDelete.setOnClickListener(v -> new AlertDialog.Builder(this)
                    .setTitle("Delete")
                    .setMessage("Delete this note?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        if (editingId != -1) {
                            dbHelper.deleteNote((int) editingId);
                            Intent res = new Intent();
                            res.putExtra("action", "deleted");
                            setResult(RESULT_OK, res);
                            Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show());
        }
    }

    private void saveNote() {
        String title = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            etTitle.setError("Enter title");
            return;
        }
        if (editingId == -1) {
            long row = dbHelper.addNote(title, content, "");
            Intent res = new Intent();
            res.putExtra("action", "added");
            res.putExtra("row", row);
            setResult(RESULT_OK, res);
            Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.updateNote((int) editingId, title, content, "");
            Intent res = new Intent();
            res.putExtra("action", "updated");
            res.putExtra("id", (int) editingId);
            setResult(RESULT_OK, res);
            Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

}
