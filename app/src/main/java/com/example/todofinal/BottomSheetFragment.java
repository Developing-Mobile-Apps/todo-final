package com.example.todofinal;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.todofinal.model.Priority;
import com.example.todofinal.model.SharedViewModel;
import com.example.todofinal.model.Todo;
import com.example.todofinal.model.TodoViewModel;
import com.example.todofinal.util.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    // properties
    Calendar calendar = Calendar.getInstance();
    private EditText enterTodo;
    private ImageButton calendarButton;
    private ImageButton priorityButton;
    private RadioGroup priorityRadioGroup;
    private RadioButton selectedRadioButton;
    private int selectedButtonId;
    private ImageButton saveButton;
    private CalendarView calendarView;
    private Group calendarGroup;
    private Date dueDate;
    private SharedViewModel sharedViewModel;
    private boolean isEdit;
    private Priority priority;

    // constructor
    public BottomSheetFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

        // initialize fragment views
        calendarGroup = view.findViewById(R.id.calendar_group);
        calendarView = view.findViewById(R.id.calendar_view);
        calendarButton = view.findViewById(R.id.today_calendar_button);
        enterTodo = view.findViewById(R.id.enter_todo_et);
        saveButton = view.findViewById(R.id.save_todo_button);
        priorityButton = view.findViewById(R.id.priority_todo_button);
        priorityRadioGroup = view.findViewById(R.id.radioGroup_priority);

        Chip todayChip = view.findViewById(R.id.today_chip);
        Chip tomorrowChip = view.findViewById(R.id.tomorrow_chip);
        Chip nextWeekChip = view.findViewById(R.id.next_week_chip);

        todayChip.setOnClickListener(this);
        tomorrowChip.setOnClickListener(this);
        nextWeekChip.setOnClickListener(this);

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // save the visibility state of calendar group
        if (calendarGroup.getVisibility() == View.VISIBLE) {
            outState.putBoolean("is_calendar_group_visible", true);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        if (savedInstanceState != null) {
            boolean isVisible = savedInstanceState.getBoolean("is_calendar_group_visible");
            if (isVisible) {
                calendarGroup.setVisibility(View.VISIBLE);
            }
        }

        calendarButton.setOnClickListener(calendarBtnView -> {
            calendarGroup.setVisibility(
                    calendarGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE
            );
            Utils.hideSoftKeyboard(calendarBtnView);
        });

        calendarView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            calendar.clear();
            calendar.set(year, month, dayOfMonth);
            dueDate = calendar.getTime();
        });

        priorityButton.setOnClickListener(priorityBtnView -> {
            Utils.hideSoftKeyboard(priorityBtnView);
            priorityRadioGroup.setVisibility(
                    priorityRadioGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE
            );
            priorityRadioGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
                if (priorityRadioGroup.getVisibility() == View.VISIBLE) {
                    selectedButtonId = checkedId;
                    selectedRadioButton = view.findViewById(selectedButtonId);

                    if (selectedRadioButton.getId() == R.id.radioButton_high) {
                        priority = Priority.HIGH;
                    } else if (selectedRadioButton.getId() == R.id.radioButton_med) {
                        priority = Priority.MEDIUM;
                    } else if (selectedRadioButton.getId() == R.id.radioButton_low) {
                        priority = Priority.LOW;
                    } else {
                        priority = Priority.LOW;
                    }
                } else {
                    priority = Priority.LOW;
                }
            });
        });

        // save todo
        saveButton.setOnClickListener(saveBtnView -> {
            String todo = enterTodo.getText().toString().trim();

            if (!TextUtils.isEmpty(todo) && dueDate != null && priority != null) {
                Todo myTodo = new Todo(todo,
                        priority,
                        dueDate,
                        Calendar.getInstance().getTime(),
                        false);

                if (isEdit) {
                    Todo updateTodo = sharedViewModel.getSelectedItem().getValue();
                    updateTodo.setTodo(todo);
                    updateTodo.setDateCreated(Calendar.getInstance().getTime());
                    updateTodo.setPriority(priority);
                    updateTodo.setDueDate(dueDate);
                    TodoViewModel.update(updateTodo);
                    sharedViewModel.setIsEdit(false);
                } else {
                    TodoViewModel.insert(myTodo);
                }
                enterTodo.setText("");

                if (this.isVisible()) {
                    this.dismiss();
                }

            } else {
                Snackbar.make(saveButton, R.string.empty_field, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (sharedViewModel.getSelectedItem().getValue() != null) {
            isEdit = sharedViewModel.getIsEdit();
            Todo todo = sharedViewModel.getSelectedItem().getValue();
            enterTodo.setText(todo.getTodo());
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        // reset the calendar to current date
        calendar = Calendar.getInstance();
        if (id == R.id.today_chip) {
            calendar.add(Calendar.DAY_OF_YEAR, 0);
        } else if (id == R.id.tomorrow_chip) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        } else if (id == R.id.next_week_chip) {
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }

        dueDate = calendar.getTime();
    }
}