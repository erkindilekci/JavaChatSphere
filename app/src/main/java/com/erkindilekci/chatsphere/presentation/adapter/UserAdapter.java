package com.erkindilekci.chatsphere.presentation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.erkindilekci.chatsphere.databinding.RecyclerviewUserRowBinding;
import com.erkindilekci.chatsphere.data.model.User;
import com.erkindilekci.chatsphere.presentation.view.ChatActivity;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context mContext;
    private List<User> users;

    public UserAdapter(Context mContext, List<User> users) {
        this.mContext = mContext;
        this.users = users;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        RecyclerviewUserRowBinding binding;

        public UserViewHolder(RecyclerviewUserRowBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(RecyclerviewUserRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User currentUser = users.get(position);

        holder.binding.txtName.setText(currentUser.getName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ChatActivity.class);
            intent.putExtra("name", currentUser.getName());
            intent.putExtra("uid", currentUser.getUid());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
