package com.example.my_module_wallet.features.user.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.my_module_wallet.MainActivity;
import com.example.my_module_wallet.R;
import com.example.my_module_wallet.features.user.model.User;
import com.example.my_module_wallet.features.user.viewmodel.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {
    private UserViewModel viewModel;
    EditText txtUsername, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);


        Button button = findViewById(R.id.btnCreateAccount);
        Button btnLogin = findViewById(R.id.btnLogin);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login(txtUsername.getText().toString(), txtPassword.getText().toString());
            }
        });
    }

    private void Login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            Snackbar.make(txtPassword, "Please enter login credentials", Snackbar.LENGTH_LONG).show();
        } else {
            viewModel.userLogin(username, password);
            viewModel.userMutableLiveData.observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if (user != null) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        int userId = user.getId();
                        intent.putExtra("USER_ID", userId);
                        startActivity(intent);
                    }
                }
            });
        }
    }
}