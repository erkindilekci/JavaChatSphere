package com.erkindilekci.chatsphere.presentation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.erkindilekci.chatsphere.data.model.User;
import com.erkindilekci.chatsphere.data.repository.ChatRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainActivityViewModel extends ViewModel {

    public MutableLiveData<List<User>> userList;

    private ChatRepository repository;

    @Inject
    public MainActivityViewModel(ChatRepository repository) {
        this.repository = repository;
        getUsers();
        userList = repository.getUserList();
    }

    private void getUsers() {
        repository.getUsers();
    }

    public void signOut() {
        repository.signOut();
    }
}
