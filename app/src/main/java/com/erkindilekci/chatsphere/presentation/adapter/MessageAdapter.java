package com.erkindilekci.chatsphere.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.erkindilekci.chatsphere.data.model.Message;
import com.erkindilekci.chatsphere.databinding.RecyclerviewReceivedMessageRowBinding;
import com.erkindilekci.chatsphere.databinding.RecyclerviewSentMessageRowBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Message> messages;

    private static final int ITEM_RECEIVED = 1;
    private static final int ITEM_SENT = 2;

    public MessageAdapter(Context mContext, List<Message> messages) {
        this.mContext = mContext;
        this.messages = messages;
    }

    public class SentViewHolder extends RecyclerView.ViewHolder {
        RecyclerviewSentMessageRowBinding binding;

        public SentViewHolder(RecyclerviewSentMessageRowBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }

    public class ReceivedViewHolder extends RecyclerView.ViewHolder {
        RecyclerviewReceivedMessageRowBinding binding;

        public ReceivedViewHolder(RecyclerviewReceivedMessageRowBinding b) {
            super(b.getRoot());
            this.binding = b;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new ReceivedViewHolder(RecyclerviewReceivedMessageRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        } else {
            return new SentViewHolder(RecyclerviewSentMessageRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message currentMessage = messages.get(position);

        if (holder instanceof SentViewHolder) {
            ((SentViewHolder) holder).binding.tvSentMessage.setText(currentMessage.getMessage());
        } else {
            ((ReceivedViewHolder) holder).binding.tvReceiveMessage.setText(currentMessage.getMessage());
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message currentMessage = messages.get(position);

        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(currentMessage.getSenderId())) {
            return ITEM_SENT;
        } else {
            return ITEM_RECEIVED;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
