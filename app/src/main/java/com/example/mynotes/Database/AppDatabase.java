package com.example.mynotes.Database;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.mynotes.Models.Notes;
import com.example.mynotes.Models.User;

@Database(entities = {Notes.class, User.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MainDAO mainDAO();
    private static AppDatabase instance;
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "notes_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}