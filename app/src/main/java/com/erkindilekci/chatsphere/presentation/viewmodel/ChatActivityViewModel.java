package com.erkindilekci.chatsphere.presentation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.erkindilekci.chatsphere.data.model.Message;
import com.erkindilekci.chatsphere.data.repository.ChatRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ChatActivityViewModel extends ViewModel {

    public MutableLiveData<List<Message>> messageList;

    private String senderRoom;
    private String receiverRoom;

    private ChatRepository repository;

    @Inject
    public ChatActivityViewModel(ChatRepository repository) {
        this.repository = repository;
//        getMessages();
        messageList = repository.getMessageList();
    }

    public void getMessages() {
        repository.getMessages(senderRoom);
    }

    public void setSenderRoom(String senderRoom) {
        this.senderRoom = senderRoom;
    }

    public void setReceiverRoom(String receiverRoom) {
        this.receiverRoom = receiverRoom;
    }

    public void sendMessage(Message messageObj) {
        repository.sendMessage(senderRoom, receiverRoom, messageObj);
    }
}
