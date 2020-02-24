package com.example.noteme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;
import java.util.List;

public class AddNote extends AppCompatActivity {
    Toolbar toolbar;
    EditText noteTitle, noteDetails;
    Calendar c;
    String todaysDate;
    String currentTime;
    Note editingNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        editingNote = (Note)intent.getSerializableExtra("note");

        noteTitle = findViewById(R.id.noteTitle);
        noteDetails = findViewById(R.id.noteDetails);

        if(editingNote != null){
            String editingTitle = editingNote.getTitle();
            noteTitle.setText(editingTitle);
            String editingContent = editingNote.getContent();
            noteDetails.setText(editingContent);
            Log.d("Show:", "ID =>" + editingNote.getID());
        }

        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    getSupportActionBar().setTitle(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // get current date and time
        c = Calendar.getInstance();
        todaysDate = c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH);
        currentTime = pad(c.get(Calendar.HOUR)) + ":" + pad(c.get(Calendar.MINUTE));

        Log.d("calendar", "Date and Time:" + todaysDate + " and " + currentTime );
    }
    private String pad(int i){
        if (i<10)
            return "0"+i;
        return String.valueOf(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete){
            Toast.makeText(this,"Note discarded",Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.save){
            if(editingNote!= null){
                editingNote.setTitle(noteTitle.getText().toString());
                editingNote.setContent(noteDetails.getText().toString());
                NoteDatabase db = new NoteDatabase(this);
                long id = db.saveNote(editingNote);
                if(id == editingNote.getID())
                    Toast.makeText(this,"Note updated", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(this,"error!!!", Toast.LENGTH_SHORT).show();
                }
                goToMain();
            }
            else {
                Note note = new Note(noteTitle.getText().toString(), noteDetails.getText().toString(), todaysDate, currentTime);
                NoteDatabase db = new NoteDatabase(this);
                long id = db.addNote(note);
                Toast.makeText(this, "Save btn", Toast.LENGTH_SHORT).show();
                Log.d("After:", "ID =>" + id);
                note.setID(id);
                Log.d("AfterSet:", "ID =>" + note.getID());
                goToMain();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
