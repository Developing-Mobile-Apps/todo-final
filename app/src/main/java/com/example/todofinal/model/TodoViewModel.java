package com.example.todofinal.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todofinal.data.TodoRepository;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    // properties
    public static TodoRepository todoRepository;
    public final LiveData<List<Todo>> allTodo;

    // constructor
    public TodoViewModel(Application application) {
        super(application);
        todoRepository = new TodoRepository(application);
        allTodo = todoRepository.getAllTodo();
    }

    public static void insert(Todo todo) {
        todoRepository.insert(todo);
    }

    public static void update(Todo todo) {
        todoRepository.update(todo);
    }

    public static void delete(Todo todo) {
        todoRepository.delete(todo);
    }

    public static void deleteAll() {
        todoRepository.deleteAll();
    }

    // these methods are used by the application view to perform crud operations
    public LiveData<List<Todo>> getAllTodo() {
        return allTodo;
    }

    public LiveData<Todo> get(long id) {
        return todoRepository.get(id);
    }
}
