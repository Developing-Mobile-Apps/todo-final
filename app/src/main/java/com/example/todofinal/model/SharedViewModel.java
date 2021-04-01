package com.example.todofinal.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Todo> selectedItem = new MutableLiveData<>();
    private boolean isEdit;

    public LiveData<Todo> getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Todo todo) {
        selectedItem.setValue(todo);
    }

    public boolean getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }
}
