package com.erkindilekci.chatsphere.presentation.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.erkindilekci.chatsphere.data.model.Message;
import com.erkindilekci.chatsphere.databinding.ActivityChatBinding;
import com.erkindilekci.chatsphere.presentation.adapter.MessageAdapter;
import com.erkindilekci.chatsphere.presentation.viewmodel.ChatActivityViewModel;
import com.google.firebase.auth.FirebaseAuth;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;

    String receiverRoom;
    String senderRoom;

    private ChatActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        String name = getIntent().getStringExtra("name");
        String receiverUid = getIntent().getStringExtra("uid");

        String senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        senderRoom = receiverUid + senderUid;
        receiverRoom = senderUid + receiverUid;

        viewModel = new ViewModelProvider(this).get(ChatActivityViewModel.class);

        viewModel.setSenderRoom(senderRoom);
        viewModel.setReceiverRoom(receiverRoom);

        viewModel.getMessages();

        binding.chatTopAppBar.setTitle(name);
        setSupportActionBar(binding.chatTopAppBar);

        binding.chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.messageList.observe(this, messages -> binding.chatRecyclerView.setAdapter(new MessageAdapter(this, messages)));

        binding.sentButton.setOnClickListener(v -> {
            String message = binding.messageBox.getText().toString();

            viewModel.sendMessage(new Message(message, senderUid));

            binding.messageBox.setText("");
        });
    }
}