package com.example.biddutkumar.firebaselogin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailsignIn,passwordsignIn;
    private Button signinBtn;
    private TextView signupTV;
    private ProgressBar progressbarsignInPB;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Sign In Activity");

        mAuth = FirebaseAuth.getInstance();

        emailsignIn=findViewById(R.id.emailsigninET);
        passwordsignIn=findViewById(R.id.passwordsignindET);
        signinBtn=findViewById(R.id.signinBtn1);
        signupTV=findViewById(R.id.signupTV1);
        progressbarsignInPB=findViewById(R.id.progressbarPB);

        signinBtn.setOnClickListener(this);
        signupTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id;
        id=v.getId();

        switch (id)
        {
            case R.id.signinBtn1:
                userLogin();
                break;

            case R.id.signupTV1:
                Intent intent=new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
                break;

        }
    }

    private void userLogin() {

        String email=emailsignIn.getText().toString().trim();
        String password=passwordsignIn.getText().toString().trim();

        //checking the validity of the email
        if(email.isEmpty())
        {
            emailsignIn.setError("Enter an email address");
            emailsignIn.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailsignIn.setError("Enter a valid email address");
            emailsignIn.requestFocus();
            return;
        }

        //checking the validity of the password
        if(password.isEmpty())
        {
            passwordsignIn.setError("Enter a password");
            passwordsignIn.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            passwordsignIn.setError("Minimun length of a password should be 6");
            passwordsignIn.requestFocus();
            return;
        }

        progressbarsignInPB.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressbarsignInPB.setVisibility(View.GONE);
                        if (task.isSuccessful()) {

                            finish();
                            Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
                            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        } else {

                            Toast.makeText(getApplicationContext(), "Loging Unsuccessful", Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }
}
