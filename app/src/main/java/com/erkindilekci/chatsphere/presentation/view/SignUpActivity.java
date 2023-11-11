package com.erkindilekci.chatsphere.presentation.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.erkindilekci.chatsphere.databinding.ActivitySignUpBinding;
import com.erkindilekci.chatsphere.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    @Inject
    public FirebaseAuth mAuth;
    @Inject
    public DatabaseReference mDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btSignUp.setOnClickListener(v -> {
            String name = binding.etName.getText().toString().strip();
            String email = binding.etEmail.getText().toString().strip();
            String password = binding.etPassword.getText().toString().strip();

            if (name.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Field(s) can't be empty!", Toast.LENGTH_SHORT).show();
            } else {
                setViewsInvisible();

                signUp(name, email, password);
            }
        });
    }

    private void signUp(String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        addUserToDatabase(name, email, mAuth.getCurrentUser().getUid());

                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(binding.etPassword.getWindowToken(), 0);

                        binding.progressBar.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(
                                SignUpActivity.this,
                                "Error: " + task.getException().getLocalizedMessage(),
                                Toast.LENGTH_SHORT
                        ).show();

                        setViewsVisible();
                    }
                });
    }

    private void addUserToDatabase(String name, String email, String uid) {
        mDbRef.child("user").child(uid).setValue(new User(name, email, uid));
    }

    private void setViewsVisible() {
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.etEmail.setVisibility(View.VISIBLE);
        binding.etPassword.setVisibility(View.VISIBLE);
        binding.btSignUp.setVisibility(View.VISIBLE);
    }

    private void setViewsInvisible() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.etName.setVisibility(View.INVISIBLE);
        binding.etEmail.setVisibility(View.INVISIBLE);
        binding.etPassword.setVisibility(View.INVISIBLE);
        binding.btSignUp.setVisibility(View.INVISIBLE);
    }
}