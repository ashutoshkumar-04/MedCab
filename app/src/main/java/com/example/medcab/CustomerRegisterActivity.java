package com.example.medcab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CustomerRegisterActivity extends AppCompatActivity {

    private Button customerLoginButton;
    private Button customerRegisterButton;
    private TextView customerRegisterLink;
    private TextView customerStatus;
    private EditText customerEmail;
    private EditText customerPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        mAuth = FirebaseAuth.getInstance();

        customerLoginButton = (Button) findViewById(R.id.customerLoginButton);
        customerRegisterButton = (Button) findViewById(R.id.customer_register_btn);
        customerRegisterLink = (TextView) findViewById(R.id.register_customer_link);
        customerStatus = (TextView) findViewById(R.id.customerLoginStatus);
        customerEmail = (EditText) findViewById(R.id.customerLoginEmail);
        customerPassword = (EditText) findViewById(R.id.customerLoginPassword);
        loadingBar = new ProgressDialog(this);


        customerRegisterButton.setVisibility(View.INVISIBLE);
        customerRegisterButton.setEnabled(false);

        customerRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerLoginButton.setVisibility(View.INVISIBLE);
                customerRegisterLink.setVisibility(View.INVISIBLE);
                customerStatus.setText("Register Customer");

                customerRegisterButton.setVisibility(View.VISIBLE);
                customerRegisterButton.setEnabled(true);
            }
        });

        customerRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = customerEmail.getText().toString();
                String password = customerPassword.getText().toString();

                RegisterCustomer(email, password);
            }
        });

        customerLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = customerEmail.getText().toString();
                String password = customerPassword.getText().toString();

                SignInCustomer(email,password);
            }
        });

    }

    private void SignInCustomer(String email, String password) {

        if (TextUtils.isEmpty(email)){
            Toast.makeText(CustomerRegisterActivity.this, "Please Enter Your Email !", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(CustomerRegisterActivity.this, "Please Enter Your Password !", Toast.LENGTH_SHORT).show();
        }

        else{

            loadingBar.setTitle("Customer Login");
            loadingBar.setMessage("Please Wait");
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(CustomerRegisterActivity.this, "Customer Logged-In Successfully ! ", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                            else{
                                Toast.makeText(CustomerRegisterActivity.this, "Customer Login Unsuccessful, Please try again", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                        }

                    });
        }

    }

    private void RegisterCustomer(String email, String password) {

        if (TextUtils.isEmpty(email)){
            Toast.makeText(CustomerRegisterActivity.this, "Please Enter Your Email !", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(CustomerRegisterActivity.this, "Please Enter Your Password !", Toast.LENGTH_SHORT).show();
        }

        else{

            loadingBar.setTitle("Customer Registration");
            loadingBar.setMessage("Please Wait");
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(CustomerRegisterActivity.this, "Customer Registered Successful ! ", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                            else{
                                Toast.makeText(CustomerRegisterActivity.this, "Customer Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                        }
                    });
        }
    }
}