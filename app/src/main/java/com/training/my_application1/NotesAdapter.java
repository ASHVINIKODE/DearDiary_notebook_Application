package com.training.my_application1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private ArrayList<Note> notesList;
    private Context context;
    private NotesDatabaseHelper db;

    public NotesAdapter(Context ctx, ArrayList<Note> notes, NotesDatabaseHelper dbHelper) {
        this.context = ctx;
        this.notesList = notes;
        this.db = dbHelper;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note note = notesList.get(position);
        holder.title.setText(note.getTitle());
        holder.content.setText(note.getContent());

        // Click to edit
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddEditNoteActivity.class);
            intent.putExtra("id", note.getId());
            intent.putExtra("title", note.getTitle());
            intent.putExtra("content", note.getContent());
            context.startActivity(intent);
        });

        // Long press to delete
        holder.itemView.setOnLongClickListener(v -> {
            db.deleteNote(note.getId());
            notesList.remove(position);
            notifyItemRemoved(position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView title, content;

        public NoteViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);     // <-- Match your XML ID
            content = itemView.findViewById(R.id.tvContent); // <-- Match your XML ID
        }
    }
}
