package com.example.biddutkumar.firebaselogin;

import android.app.assist.AssistStructure;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailSignUp,passwordSignUp;
    private Button signupBtn2;
    private TextView signinTV2;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.setTitle("Sign Up Activity");

        mAuth = FirebaseAuth.getInstance();

        emailSignUp=findViewById(R.id.emailSignUpET);
        passwordSignUp=findViewById(R.id.passwordSignUpET);
        signupBtn2=findViewById(R.id.signupBtn2);
        signinTV2=findViewById(R.id.signinTV2);
        progressBar=findViewById(R.id.progressbarSignUpPB);

        signupBtn2.setOnClickListener(this);
        signinTV2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id;
        id=v.getId();
        switch (id)
        {
            case R.id.signupBtn2:
                userRegister();
                break;

            case R.id.signinTV2:
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void userRegister() {

        String email=emailSignUp.getText().toString().trim();
        String password=passwordSignUp.getText().toString().trim();

        //checking the validity of the email
        if(email.isEmpty())
        {
            emailSignUp.setError("Enter an email address");
            emailSignUp.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailSignUp.setError("Enter a valid email address");
            emailSignUp.requestFocus();
            return;
        }

        //checking the validity of the password
        if(password.isEmpty())
        {
            passwordSignUp.setError("Enter a password");
            passwordSignUp.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            passwordSignUp.setError("Minimun length of a password should be 6");
            passwordSignUp.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful())
                        {
                            finish();
                            Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
                            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else
                        {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(getApplicationContext(), "User is already Register", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Error : "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }

                    }
                });

    }
}
