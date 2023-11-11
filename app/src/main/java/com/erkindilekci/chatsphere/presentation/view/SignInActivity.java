package com.erkindilekci.chatsphere.presentation.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.erkindilekci.chatsphere.databinding.ActivitySignInBinding;
import com.erkindilekci.chatsphere.presentation.viewmodel.SignInActivityViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;

    private SignInActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(SignInActivityViewModel.class);

        if (viewModel.checkCurrentUser()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        binding.btSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });

        binding.btLogin.setOnClickListener(v -> handeSignInButtonClick());
    }

    private void handeSignInButtonClick() {
        String email = binding.etEmail.getText().toString().strip();
        String password = binding.etPassword.getText().toString().strip();

        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Field(s) can't be empty!", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.signIn(email, password);

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(binding.etPassword.getWindowToken(), 0);
        }
    }
}