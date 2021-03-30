package com.example.todofinal;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todofinal.adapter.OnTodoClickListener;
import com.example.todofinal.adapter.TodoAdapter;
import com.example.todofinal.model.SharedViewModel;
import com.example.todofinal.model.Todo;
import com.example.todofinal.model.TodoViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements OnTodoClickListener {
    // properties
    private RecyclerView recyclerView;
    private TodoAdapter todoAdapter;
    private BottomSheetFragment bottomSheetFragment;
    private SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize fragment
        bottomSheetFragment = new BottomSheetFragment();
        ConstraintLayout bottomSheetLayout = findViewById(R.id.bottomSheet);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior
                .from(bottomSheetLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);

        // initialize recycler view
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // initialize view model
        TodoViewModel todoViewModel = new ViewModelProvider.AndroidViewModelFactory(
                MainActivity.this.getApplication())
                .create(TodoViewModel.class);

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        // set the observer to observe any change in data from LiveData
        todoViewModel.getAllTodo().observe(this, allTodo -> {
            todoAdapter = new TodoAdapter(allTodo, this);
            recyclerView.setAdapter(todoAdapter);
        });

        FloatingActionButton addTodoFab = findViewById(R.id.fab);
        addTodoFab.setOnClickListener(view -> showBottomSheetDialog());
    }

    @Override
    public void onTodoClick(Todo todo) {
        sharedViewModel.setSelectedItem(todo);
        sharedViewModel.setIsEdit(true);
        showBottomSheetDialog();
    }

    @Override
    public void onTodoRadioBtnClick(Todo todo) {
        TodoViewModel.delete(todo);
    }

    private void showBottomSheetDialog() {
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }
}