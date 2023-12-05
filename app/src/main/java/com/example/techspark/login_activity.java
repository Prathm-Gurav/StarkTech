package com.example.techspark;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class login_activity extends AppCompatActivity {

    TextInputEditText t1,t2;
    FirebaseAuth mAuth;
    TextView forgotpass;
    ProgressDialog progressDialog;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        db=FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        t1=findViewById(R.id.editTextPhone);
        forgotpass=findViewById(R.id.textView4);
        t2=findViewById(R.id.editTextPassword);

    }

    @Override
    protected void onStart() {
        super.onStart();

        int flag=getIntent().getIntExtra("flag",0);
        if(flag==1) {
            return ;
        }


        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        if (firebaseUser!=null){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }

    public void get_login(View view) {
        String mobile = t1.getText().toString().trim();
        String password = t2.getText().toString().trim();
        String emailaddress = mobile + "@gmail.com"; // Ensure a valid email format

        if (!mobile.equals("")) {
            if (mobile.length() == 10) {
                if (!password.equals("")) {
                    progressDialog.setMessage("Please wait while Login Complete...");
                    progressDialog.setTitle("Login");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(emailaddress, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        finish();
                                        progressDialog.dismiss();
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(login_activity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                        Bundle bundle = new Bundle();
                                        i.putExtra("mobile",mobile);
                                        //Toast.makeText(login_activity.this, ""+mobile, Toast.LENGTH_SHORT).show();
                                        startActivity(i);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        progressDialog.dismiss();
                                        Toast.makeText(login_activity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                } else {
                    Toast.makeText(this, "Please provide Password", Toast.LENGTH_SHORT).show();
                }
            } else {

                Toast.makeText(this, "Please provide valid mobile number", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please provide mobile number", Toast.LENGTH_SHORT).show();
        }
    }


    public void create_acc_fun(View view) {
        Intent i = new Intent(this,SignUp.class);
        startActivity(i);
    }
    public void forgotPassword(View view) {
        Toast.makeText(this, "cliecked", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this,changepassword.class);
        startActivity(i);
    }
}