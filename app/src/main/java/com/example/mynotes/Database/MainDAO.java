package com.example.mynotes.Database;
import static androidx.room.OnConflictStrategy.REPLACE;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.mynotes.Models.Notes;
import com.example.mynotes.Models.User;
import java.util.List;

@Dao
public interface MainDAO {

    // Методы для работы с заметками
    @Insert(onConflict = REPLACE)
    void insert(Notes notes);

    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Notes> getAll();

    @Query("UPDATE notes SET title = :title, notes = :notes WHERE id = :id")
    void update(int id, String title, String notes);

    @Delete
    void delete(Notes notes);

    @Query("UPDATE notes SET pinned = :pin WHERE id = :id")
    void  pin(int id, boolean pin);

    // Методы для работы с пользователями
    @Insert(onConflict = REPLACE)
    void insertUser(User user);

    @Query("SELECT * FROM users WHERE login = :login LIMIT 1")
    User getUserByLogin(String login);

    @Query("SELECT COUNT(*) FROM users WHERE login = :login")
    int checkLoginExists(String login);

}