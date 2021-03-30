package com.example.todofinal.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "todo_table")
public class Todo {
    // properties of column for todo_table
    @ColumnInfo(name = "todo_id")
    @PrimaryKey(autoGenerate = true)
    public long todoId;
    public String todo;
    public Priority priority;

    @ColumnInfo(name = "due_date")
    public Date dueDate;

    @ColumnInfo(name = "date_created")
    public Date dateCreated;

    @ColumnInfo(name = "is_done")
    public boolean isDone;

    // constructor
    public Todo(String todo, Priority priority, Date dueDate, Date dateCreated, boolean isDone) {
        this.todo = todo;
        this.priority = priority;
        this.dueDate = dueDate;
        this.dateCreated = dateCreated;
        this.isDone = isDone;
    }

    // getters and setters
    public long getTodoId() {
        return todoId;
    }

    public void setTodoId(long todoId) {
        this.todoId = todoId;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
