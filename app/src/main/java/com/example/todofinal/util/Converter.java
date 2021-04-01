package com.example.todofinal.util;

import androidx.room.TypeConverter;

import com.example.todofinal.model.Priority;

import java.util.Date;

public class Converter {
    @TypeConverter
    public static Date fromTimestampToDate(Long timestamp) {
        return (timestamp == null) ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long fromDateToTimestamp(Date date) {
        return (date == null) ? null : date.getTime();
    }

    @TypeConverter
    public static String fromPriority(Priority priority) {
        return (priority == null) ? null : priority.name();
    }

    @TypeConverter
    public static Priority toPriority(String priority) {
        return (priority == null) ? null : Priority.valueOf(priority);
    }
}
