package com.example.notes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDiscription;
    private Spinner spinnerDaysOfWeek;
    private RadioGroup radioGroupPreority;

    private NotesDBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        dbHelper = new NotesDBHelper(this);
        database = dbHelper.getWritableDatabase();
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDiscription = findViewById(R.id.editTextDescription);
        spinnerDaysOfWeek = findViewById(R.id.spinnerDaysOfWeek);
        radioGroupPreority = findViewById(R.id.radioGroupPriority);

    }

    public void onCkickSaveNote(View view) {
        String title = editTextTitle.getText().toString().trim();
        int daysOfWeek = spinnerDaysOfWeek.getSelectedItemPosition();
        String descriptions = editTextDiscription.getText().toString().trim();
        int radioButtonId = radioGroupPreority.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonId);
        int priority = Integer.parseInt(radioButton.getText().toString());
        if (isFilled(title, descriptions)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, title);
            contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, descriptions);
            contentValues.put(NotesContract.NotesEntry.COLUMN_DAYSOFWEEK, daysOfWeek+1);
            contentValues.put(NotesContract.NotesEntry.COLUMN_PRIORITY, priority);
            database.insert(NotesContract.NotesEntry.TABLE_NAME, null, contentValues);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.warning_fill_filds, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isFilled(String title, String description) {
        return !title.isEmpty() && !description.isEmpty();
    }
}
