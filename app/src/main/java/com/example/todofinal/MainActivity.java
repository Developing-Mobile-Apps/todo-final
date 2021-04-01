package com.example.todofinal;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todofinal.adapter.OnTodoClickListener;
import com.example.todofinal.adapter.TodoAdapter;
import com.example.todofinal.model.SharedViewModel;
import com.example.todofinal.model.Todo;
import com.example.todofinal.model.TodoViewModel;
import com.example.todofinal.util.Utils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements OnTodoClickListener {
    private static final String CHANNEL_ID = "com.example.todofinal.MainActivity.CHANNEL_ID";
    private static final int LAUNCH_ACTIVITY_REQUEST = 1001;
    private final int UPCOMING_TODO = 9999;
    private final Date CURRENT_DATE = new Date();

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
        createNotificationChannel();

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
            int upcomingTodoCount = 0;

            for (Todo todo : allTodo) {
                if (Utils.areDateSame(todo.getDueDate(), CURRENT_DATE)) {
                    ++upcomingTodoCount;
                }
            }

            if (upcomingTodoCount > 0) {
                // create notification
                NotificationCompat.Builder builder = new NotificationCompat
                        .Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                        .setContentTitle("Upcoming Todo")
                        .setContentText("You have " + upcomingTodoCount + " Todo due today")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                // display notification if needed
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(UPCOMING_TODO, builder.build());
            }

            todoAdapter = new TodoAdapter(allTodo, this);
            recyclerView.setAdapter(todoAdapter);
        });

        // set fab
        FloatingActionButton addTodoFab = findViewById(R.id.fab);
        addTodoFab.setOnClickListener(view -> showBottomSheetDialog());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
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
       if (id == R.id.action_about) {
            // go to about page
            Intent intent = AboutActivity.makeIntent(this);
            startActivityForResult(intent, LAUNCH_ACTIVITY_REQUEST);
        } else if (id == R.id.action_delete_all_todo) {
            // delete allTodo
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