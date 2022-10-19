package com.example.parkit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminLoginActivity extends AppCompatActivity {

    EditText password, email;
    Button login;

    ProgressDialog progessDialog;

    // Fire base Authentication
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        login = findViewById(R.id.login);

        progessDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }
        });

    }

    // Function to perform login
    private void performLogin() {
        String Email = email.getText().toString();
        String Password = password.getText().toString();

        if ( Email.isEmpty() || Password.isEmpty() ){
            password.setError("Enter Proper Password / Email");
        }
        else{
            progessDialog.setMessage("Wait while login in done ...");
            progessDialog.setTitle("Login");
            progessDialog.show();

            // To check the credentials
            mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if ( task.isSuccessful() ){
                        progessDialog.dismiss();
                        sendAdminToNextActivity();
                        Toast.makeText(AdminLoginActivity.this, "Login Successful !!!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progessDialog.dismiss();
                        Toast.makeText(AdminLoginActivity.this, "Incorrect Email / Password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendAdminToNextActivity() {
        Intent intent = new Intent(AdminLoginActivity.this, AdminMainActivity.class);
        startActivity(intent);
    }
}