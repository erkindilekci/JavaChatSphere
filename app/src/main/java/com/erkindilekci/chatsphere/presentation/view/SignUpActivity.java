package com.erkindilekci.chatsphere.presentation.view;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.erkindilekci.chatsphere.databinding.ActivitySignUpBinding;
import com.erkindilekci.chatsphere.presentation.viewmodel.SignUpActivityViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    @Inject
    public FirebaseAuth mAuth;
    @Inject
    public DatabaseReference mDbRef;

    private SignUpActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(SignUpActivityViewModel.class);

        binding.btSignUp.setOnClickListener(v -> handeSignUpButtonClick());
    }

    private void handeSignUpButtonClick() {
        String name = binding.etName.getText().toString().strip();
        String email = binding.etEmail.getText().toString().strip();
        String password = binding.etPassword.getText().toString().strip();

        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Field(s) can't be empty!", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.signUp(name, email, password);

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(binding.etPassword.getWindowToken(), 0);
        }
    }
}