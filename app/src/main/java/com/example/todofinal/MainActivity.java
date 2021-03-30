package com.example.todofinal;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todofinal.adapter.OnTodoClickListener;
import com.example.todofinal.adapter.TodoAdapter;
import com.example.todofinal.data.TodoRepository;
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // delete allTodo
        if (id == R.id.action_delete_all_todo) {
            TodoViewModel.deleteAll();
        }

        return super.onOptionsItemSelected(item);
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