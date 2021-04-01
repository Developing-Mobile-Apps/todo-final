package com.example.todofinal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todofinal.R;
import com.example.todofinal.model.Todo;
import com.example.todofinal.util.Utils;
import com.google.android.material.chip.Chip;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    // properties
    private final List<Todo> todoList;
    private final OnTodoClickListener todoClickListener;

    // constructor
    public TodoAdapter(List<Todo> todoList, OnTodoClickListener todoClickListener) {
        this.todoList = todoList;
        this.todoClickListener = todoClickListener;
    }

    @NonNull
    @Override
    public TodoAdapter.TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the todo_row layout and create a view holder
        View todoRow = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_row, parent, false);

        return new TodoViewHolder(todoRow);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.TodoViewHolder holder, int position) {
        // bind view and data
        Todo todo = todoList.get(position);
        holder.todo.setText(todo.getTodo());
        holder.radioBtn.setSelected(todo.isDone());
        holder.dueDateChip.setText(Utils.formatDate(todo.getDueDate()));
        holder.dueDateChip.setTextColor(Utils.priorityColor(todo));
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // properties
        public RadioButton radioBtn;
        public TextView todo;
        public Chip dueDateChip;

        // constructor
        public TodoViewHolder(View todoRow) {
            super(todoRow);
            radioBtn = todoRow.findViewById(R.id.todo_row_radio_btn);
            todo = todoRow.findViewById(R.id.todo_row_todo);
            dueDateChip = todoRow.findViewById(R.id.todo_row_chip);

            // set on click listener
            todoRow.setOnClickListener(this);
            radioBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            Todo currentTodo = todoList.get(getAdapterPosition());

            if (id == R.id.todo_row_layout) {
                todoClickListener.onTodoClick(currentTodo);
            } else if (id == R.id.todo_row_radio_btn) {
                todoClickListener.onTodoRadioBtnClick(currentTodo);
            }
        }
    }
}
