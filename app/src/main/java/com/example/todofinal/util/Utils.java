package com.example.todofinal.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.todofinal.model.Priority;
import com.example.todofinal.model.Todo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    // static methods to perform commonly repeated task
    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("EEE, MMM, d");

        return simpleDateFormat.format(date);
    }

    public static void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static int priorityColor(Todo todo) {
        int color;
        if (todo.getPriority() == Priority.HIGH) {
            color = Color.rgb(255, 0, 0);
        } else if (todo.getPriority() == Priority.MEDIUM) {
            color = Color.rgb(255, 178, 102);
        } else {
            color = Color.rgb(0, 128, 0);
        }

        return color;
    }
}
