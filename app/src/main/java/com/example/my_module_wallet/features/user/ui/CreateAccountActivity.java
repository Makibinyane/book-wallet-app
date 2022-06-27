package com.example.my_module_wallet.features.user.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.my_module_wallet.R;
import com.example.my_module_wallet.features.user.model.User;
import com.example.my_module_wallet.features.user.viewmodel.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

public class CreateAccountActivity extends AppCompatActivity {
    private UserViewModel viewModel;
    private EditText txtUsername, txtPassword, txtFirstname, txtLastname, txtConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button btnSubmit = findViewById(R.id.btnCreateAccountSubmit);

         txtUsername = findViewById(R.id.txtCreateUsername);
         txtPassword = findViewById(R.id.txtCreatePassword);
         txtFirstname = findViewById(R.id.txtCreateFirstname);
         txtLastname = findViewById(R.id.txtCreateLastname);
         txtConfirmPassword = findViewById(R.id.txtConfirmPassword);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(0, txtFirstname.getText().toString(), txtLastname.getText().toString(), txtUsername.getText().toString(),
                        txtPassword.getText().toString());
                createUser(user, txtConfirmPassword.getText().toString());
            }
        });
    }

    private void createUser(User user, String confirmPassword) {
        if (isValid(user, confirmPassword)) {
            viewModel.addUser(user);
            showAddUserDialog();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddUserDialog() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Account has been successfully..")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Account created");
        alert.show();
    }

    private boolean isValid(User user, String confirmPassword) {
        boolean isDataValid = true;
        if (user.getFirstName().isEmpty()) {
            isDataValid = false;
            txtFirstname.setError("Please enter first name");
        }

        if (user.getFirstLastname().isEmpty()) {
            isDataValid = false;
            txtLastname.setError("Please enter last name");
        }

        if (user.getUsername().isEmpty()) {
            isDataValid = false;
            txtUsername.setError("Please enter username");
        }

        if (user.getPassword().isEmpty()) {
            isDataValid = false;
            txtPassword.setError("Please enter password");
        }

        if (confirmPassword.isEmpty()) {
            isDataValid = false;
            txtConfirmPassword.setError("Please enter confirm password");
        }

        if (!user.getPassword().isEmpty() && !confirmPassword.isEmpty()) {
            if (!user.getPassword().equals(confirmPassword)) {
                isDataValid = false;
                Snackbar.make(txtConfirmPassword, "Please ensure passwords are the same", Snackbar.LENGTH_LONG).show();
            }
        }

        return isDataValid;
    }
}