package com.training.my_application1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditNoteActivity extends AppCompatActivity {

    EditText etTitle, etContent;
    Button btnSave, btnDel;
    NotesDatabaseHelper db;
    int noteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        db = new NotesDatabaseHelper(this);

        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        btnSave = findViewById(R.id.btnSave);
        btnDel = findViewById(R.id.btnDel);

        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            noteId = intent.getIntExtra("id", -1);
            etTitle.setText(intent.getStringExtra("title"));
            etContent.setText(intent.getStringExtra("content"));
            btnDel.setEnabled(true);
        } else {
            btnDel.setEnabled(false); // Hide delete if it's a new note
        }

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String content = etContent.getText().toString().trim();

            if (title.isEmpty()) {
                Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (noteId == -1) {
                db.addNote(title, content);
                Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show();
            } else {
                db.updateNote(noteId, title, content);
                Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
            }
            finish();
        });

        btnDel.setOnClickListener(v -> {
            if (noteId != -1) {
                db.deleteNote(noteId);
                Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
