package com.example.todofinal.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.todofinal.model.Todo;
import com.example.todofinal.util.TodoRoomDatabase;

import java.util.List;

public class TodoRepository {
    // properties
    private final TodoDao todoDao;
    private final LiveData<List<Todo>> allTodo;

    // constructor
    public TodoRepository(Application application) {
        TodoRoomDatabase todoRoomDatabase = TodoRoomDatabase.getDatabase(application);
        todoDao = todoRoomDatabase.todoDao();
        allTodo = todoDao.getAll();
    }

    // methods used to run crud operations
    public LiveData<List<Todo>> getAllTodo() {
        return allTodo;
    }

    public LiveData<Todo> get(long id) {
        return todoDao.get(id);
    }

    // these methods execute on threads in the background
    public void insert(Todo todo) {
        TodoRoomDatabase.databaseWriteExecutor.execute(() -> todoDao.insertTodo(todo));
    }

    public void update(Todo todo) {
        TodoRoomDatabase.databaseWriteExecutor.execute(() -> todoDao.update(todo));
    }

    public void delete(Todo todo) {
        TodoRoomDatabase.databaseWriteExecutor.execute(() -> todoDao.delete(todo));
    }

    public void deleteAllTask() {
        TodoRoomDatabase.databaseWriteExecutor.execute(todoDao::deleteAll);
    }
}
