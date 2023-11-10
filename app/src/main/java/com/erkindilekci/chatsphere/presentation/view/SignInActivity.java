package com.erkindilekci.chatsphere.presentation.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.erkindilekci.chatsphere.databinding.ActivitySignInBinding;
import com.google.firebase.auth.FirebaseAuth;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        binding.btSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });

        binding.btLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().strip();
            String password = binding.etPassword.getText().toString().strip();

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Field(s) can't be empty!", Toast.LENGTH_SHORT).show();
            } else {
                makeViewsInvisible();

                signIn(email, password);
            }
        });
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(binding.etPassword.getWindowToken(), 0);
                    } else {
                        Toast.makeText(
                                SignInActivity.this,
                                "Error: " + task.getException().getLocalizedMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                        makeViewsVisible();
                    }
                });
    }

    private void makeViewsInvisible() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.etEmail.setVisibility(View.INVISIBLE);
        binding.etPassword.setVisibility(View.INVISIBLE);
        binding.btLogin.setVisibility(View.INVISIBLE);
        binding.btSignUp.setVisibility(View.INVISIBLE);
    }

    private void makeViewsVisible() {
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.etEmail.setVisibility(View.VISIBLE);
        binding.etPassword.setVisibility(View.VISIBLE);
        binding.btLogin.setVisibility(View.VISIBLE);
        binding.btSignUp.setVisibility(View.VISIBLE);
    }
}