package com.example.todofinal.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todofinal.model.Todo;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    void insertTodo(Todo todo);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);

    @Query("SELECT * FROM todo_table WHERE todo_id == :id")
    LiveData<Todo> get(long id);

    @Query("SELECT * FROM todo_table ORDER BY due_date")
    LiveData<List<Todo>> getAll();

    @Query("DELETE FROM todo_table")
    void deleteAll();
}
