package com.erkindilekci.chatsphere.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.erkindilekci.chatsphere.data.model.Message;
import com.erkindilekci.chatsphere.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class ChatRepository {

    private MutableLiveData<List<User>> userList;
    private MutableLiveData<List<Message>> messageList;

    private FirebaseAuth mAuth;
    private DatabaseReference mDbRef;

    @Inject
    public ChatRepository(FirebaseAuth mAuth, DatabaseReference mDbRef) {
        userList = new MutableLiveData<>();
        messageList = new MutableLiveData<>();
        this.mAuth = mAuth;
        this.mDbRef = mDbRef;
    }

    public MutableLiveData<List<User>> getUserList() {
        return userList;
    }

    public MutableLiveData<List<Message>> getMessageList() {
        return messageList;
    }

    public void getUsers() {
        mDbRef.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<User> users = new ArrayList<>();

                for (DataSnapshot data : snapshot.getChildren()) {
                    User user = data.getValue(User.class);
                    if (!Objects.equals(mAuth.getCurrentUser().getEmail(), user.getEmail())) {
                        users.add(user);
                    }
                }

                userList.setValue(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void getMessages(String senderRoom) {
        mDbRef.child("chats").child(senderRoom).child("messages")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<Message> messages = new ArrayList<>();

                        for (DataSnapshot data : snapshot.getChildren()) {
                            Message message = data.getValue(Message.class);
                            messages.add(message);
                        }

                        messageList.setValue(messages);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    public void sendMessage(String senderRoom, String receiverRoom, Message messageObj) {
        mDbRef.child("chats").child(senderRoom).child("messages").push()
                .setValue(messageObj)
                .addOnSuccessListener(task -> {
                    mDbRef.child("chats").child(receiverRoom).child("messages").push()
                            .setValue(messageObj);
                });
    }

    public void signOut() {
        mAuth.signOut();
    }
}
