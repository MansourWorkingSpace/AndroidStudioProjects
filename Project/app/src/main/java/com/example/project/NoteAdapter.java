package com.example.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private final Context context;
    private List<Note> notes = new ArrayList<>();
    private final NotesDatabaseHelper dbHelper;

    public NoteAdapter(Context ctx, List<Note> notes, NotesDatabaseHelper dbHelper) {
        this.context = ctx;
        this.notes = notes;
        this.dbHelper = dbHelper;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note n = notes.get(position);
        holder.title.setText(n.getTitle());
        holder.date.setText(n.getDate());

        // Click to edit
        holder.card.setOnClickListener(v -> {
            Intent i = new Intent(context, NoteActivity.class);
            i.putExtra(NoteActivity.EXTRA_NOTE_ID, n.getId());
            if (context instanceof Activity) {
                ((Activity) context).startActivityForResult(i, MainActivity.REQ_EDIT_NOTE);
            } else {
                context.startActivity(i);
            }
        });

        // Long click to confirm delete
        holder.card.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete")
                    .setMessage("Delete this note?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dbHelper.deleteNote(n.getId());
                        notes.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView title, date;

        ViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            title = itemView.findViewById(R.id.tv_title);
            date = itemView.findViewById(R.id.tv_date);
        }
    }
}
