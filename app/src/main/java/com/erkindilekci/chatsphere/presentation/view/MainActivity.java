package com.erkindilekci.chatsphere.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.erkindilekci.chatsphere.R;
import com.erkindilekci.chatsphere.databinding.ActivityMainBinding;
import com.erkindilekci.chatsphere.presentation.adapter.UserAdapter;
import com.erkindilekci.chatsphere.presentation.viewmodel.MainActivityViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.progressBar.setVisibility(View.VISIBLE);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        binding.userRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.userList.observe(this, users -> {
            binding.userRecyclerView.setAdapter(new UserAdapter(this, users));
            binding.progressBar.setVisibility(View.GONE);
        });

        setSupportActionBar(binding.topAppBar);

        binding.topAppBar.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.logout) {
                viewModel.signOut();

                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}