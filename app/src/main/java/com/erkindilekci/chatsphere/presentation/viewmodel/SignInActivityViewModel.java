package com.erkindilekci.chatsphere.presentation.viewmodel;

import androidx.lifecycle.ViewModel;

import com.erkindilekci.chatsphere.data.repository.ChatRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SignInActivityViewModel extends ViewModel {

    private ChatRepository repository;

    @Inject
    public SignInActivityViewModel(ChatRepository repository) {
        this.repository = repository;
    }

    public void signIn(String email, String password) {
        repository.signIn(email, password);
    }

    public boolean checkCurrentUser() {
        return repository.checkCurrentUser();
    }
}
