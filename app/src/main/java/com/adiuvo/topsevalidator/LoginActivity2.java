package com.adiuvo.topsevalidator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import soup.neumorphism.NeumorphButton;

public class LoginActivity2 extends AppCompatActivity {
    NeumorphButton neumorphButton;
    EditText email,pass;
    String Email,Pass;
    String TAG="LoginActivity2.java";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        neumorphButton=findViewById(R.id.loginButton);
        email=findViewById(R.id.mailId);
        pass=findViewById(R.id.Password);
        neumorphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(LoginActivity2.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                startActivity(new Intent(LoginActivity2.this,HomeActivity.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity2.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }

            }
        });
    }

    private boolean validate() {
        int c=0;
        if(!email.getText().toString().equals("")){
            email.setError(null);
            Email=email.getText().toString().trim();

        }else{
            c=1;
            email.setError("Please Enter an Email Address");
        }
        if(!pass.getText().toString().equals("")){
            pass.setError(null);
            Pass=pass.getText().toString().trim();
        }else{
            c=1;
            pass.setError("Please Enter the Password");
        }
        return c==0;
    }
}