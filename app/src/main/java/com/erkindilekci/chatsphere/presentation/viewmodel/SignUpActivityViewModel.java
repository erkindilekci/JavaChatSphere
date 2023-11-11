package com.erkindilekci.chatsphere.presentation.viewmodel;

import androidx.lifecycle.ViewModel;

import com.erkindilekci.chatsphere.data.repository.ChatRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SignUpActivityViewModel extends ViewModel {

    private ChatRepository repository;

    @Inject
    public SignUpActivityViewModel(ChatRepository repository) {
        this.repository = repository;
    }

    public void signUp(String name, String email, String password) {
        repository.signUp(name, email, password);
    }
}
