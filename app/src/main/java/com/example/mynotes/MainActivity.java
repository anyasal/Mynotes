package com.example.mynotes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.mynotes.Adapter.NotesListAdapter;
import com.example.mynotes.Database.RoomDB;
import com.example.mynotes.Models.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    RecyclerView recyclerView;
    FloatingActionButton fab_add;
    NotesListAdapter notesListAdapter;
    RoomDB database;
    List<Notes> notes = new ArrayList<>();
    SearchView searchView_home;
    Notes selectedNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Проверка авторизации
        if (!isUserLoggedIn()) {
            redirectToLogin();
            return;
        }
        if (!isUserLoggedIn()) {
            redirectToLogin();
            return;
        }
        setContentView(R.layout.activity_main);
        initializeComponents();
        setupDatabase();
        setupRecyclerView();
        setupListeners();
    }
    private void initializeComponents() {
        recyclerView = findViewById(R.id.recycler_home);
        fab_add = findViewById(R.id.fad_add);
        searchView_home = findViewById(R.id.searchView_home);
    }
    private void setupDatabase() {
        database = RoomDB.getInstance(this);
        notes = database.mainDao().getAll();
    }
    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notesListAdapter = new NotesListAdapter(MainActivity.this, notes, notesClickListener);
        recyclerView.setAdapter(notesListAdapter);
    }
    private void setupListeners() {
        fab_add.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NotesTakenActivity.class);
            startActivityForResult(intent, 101);
        });
        searchView_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }
    private boolean isUserLoggedIn() {
        SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
        return prefs.getBoolean("isLoggedIn", false);
    }
    private void redirectToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
    // Остальные методы остаются без изменений
    private void filter(String newText) {
        List<Notes> filteredList = new ArrayList<>();
        for (Notes singleNote : notes) {
            if (singleNote.getTitle().toLowerCase().contains(newText.toLowerCase())
                    || singleNote.getNotes().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(singleNote);
            }
        }
        notesListAdapter.filterList(filteredList);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            Notes new_notes = (Notes) data.getSerializableExtra("note");
            database.mainDao().insert(new_notes);
            notes.clear();
            notes.addAll(database.mainDao().getAll());
            notesListAdapter.notifyDataSetChanged();
        } else if (requestCode == 102 && resultCode == Activity.RESULT_OK) {
            Notes new_notes = (Notes) data.getSerializableExtra("note");
            database.mainDao().update(new_notes.getId(), new_notes.getTitle(), new_notes.getNotes());
            notes.clear();
            notes.addAll(database.mainDao().getAll());
            notesListAdapter.notifyDataSetChanged();
        }
    }

    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onClick(Notes notes) {
            Intent intent = new Intent(MainActivity.this, NotesTakenActivity.class);
            intent.putExtra("old_note", notes);
            startActivityForResult(intent, 102);
        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {
            selectedNote = notes;
            showPopup(cardView);
        }
    };

    private void showPopup(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.pin) {
            database.mainDao().pin(selectedNote.getId(), !selectedNote.isPinned());
            Toast.makeText(this, selectedNote.isPinned() ? "Unpinned" : "Pinned", Toast.LENGTH_SHORT).show();
            updateNotesList();
            return true;
        } else if (item.getItemId() == R.id.delete) {
            database.mainDao().delete(selectedNote);
            notes.remove(selectedNote);
            notesListAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void updateNotesList() {
        notes.clear();
        notes.addAll(database.mainDao().getAll());
        notesListAdapter.notifyDataSetChanged();
    }

}