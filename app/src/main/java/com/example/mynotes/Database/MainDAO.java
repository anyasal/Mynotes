package com.example.mynotes.Database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.mynotes.Models.Notes;

import java.util.List;

@Dao
public interface MainDAO {

    @Insert(onConflict = REPLACE)
    void insert(Notes notes);

    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Notes> getAll();

    @Query("UPDATE notes SET title = :title, notes = :notes WHERE id = :id")
    void update(int id, String title, String notes);

    @Delete
    void delete(Notes notes);

    @Query("UPDATE notes SET pinned = :pin WHERE id = :id")
    void  pin (int id, boolean pin);

}

//
//
//package com.example.mynotes.Database;
//
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Update;
//
//import com.example.mynotes.Models.Notes;
//
//import java.util.List;
//
//@Dao
//public interface MainDAO {
//   @Insert
//   void insert(Notes note);
//
//   @Update
//   void update(Notes note);
//
//   @Delete
//   void delete(Notes note);
//
//   @Query("SELECT * FROM notes ORDER BY ID DESC")
//   List<Notes> getAll(); // Используем getAll
//}
//@Dao
//public interface MainDAO {
//   @Insert
//   void insert(Notes note);
//
//   @Update
//   void update(Notes note);
//
//   @Delete
//   void delete(Notes note);
//
//   @Query("SELECT * FROM notes ORDER BY id DESC") // Теперь столбец "id" должен быть виден
//   List<Notes> getAll();
//
//   @Query("UPDATE notes SET title = :title, notes = :notes WHERE id = :id")
//   void update(int id, String title, String notes);

