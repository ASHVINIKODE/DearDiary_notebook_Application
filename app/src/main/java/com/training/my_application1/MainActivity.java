package com.training.my_application1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnAddNote;
    NotesAdapter adapter;
    NotesDatabaseHelper db;
    ArrayList<Note> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new NotesDatabaseHelper(this);
        notesList = new ArrayList<>();

        recyclerView = findViewById(R.id.notesRecyclerView);
        btnAddNote = findViewById(R.id.btnAddNote);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAddNote.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddEditNoteActivity.class))
        );

        loadNotes();
    }

    private void loadNotes() {
        notesList.clear();
        Cursor cursor = db.getAllNotes();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No notes found", Toast.LENGTH_SHORT).show();
            return;
        }

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            notesList.add(new Note(id, title, content));
        }

        adapter = new NotesAdapter(this, notesList, db);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }
}
