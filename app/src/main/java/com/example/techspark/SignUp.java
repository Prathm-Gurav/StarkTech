package com.example.techspark;

import android.Manifest.permission;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SignUp extends AppCompatActivity {
    EditText t1, t2, t3, t4, t5, t6;
    final Random random = new Random();
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    FirebaseUser mUser;
    FirebaseDatabase db;
    String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        t1 = findViewById(R.id.editTextText);
        t2 = findViewById(R.id.editTextText2);
        t3 = findViewById(R.id.editTextPhone2);
        t4 = findViewById(R.id.editTextTextPassword2);
        t5 = findViewById(R.id.editTextTextPassword3);
        t6 = findViewById(R.id.editTextNumber);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db= FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(this);
    }

    public void register(View view) {
        String username = t1.getText().toString().trim();
        String address = t2.getText().toString().trim();
        String mobile = t3.getText().toString();
        String password = t4.getText().toString().trim();
        String conPassword = t5.getText().toString().trim();
        if (!username.equals("")) {
            if (!address.equals("")) {
                if (!mobile.equals("") && mobile.length() == 10) {
                    if (!password.equals("") && password.equals(conPassword) && password.length()>6) {
//                        FirebaseFirestore.getInstance().collection("users").whereEqualTo("mobile", mobile).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    if (task.getResult() != null && !task.getResult().isEmpty()) {
//                                        Toast.makeText(SignUp.this, "User already exists", Toast.LENGTH_SHORT).show();
//                                    } else {
                        if (ContextCompat.checkSelfPermission(SignUp.this, permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                            sendOTP();
                        } else {
                            ActivityCompat.requestPermissions(SignUp.this, new String[]{permission.SEND_SMS}, 100);
                        }
//                                    }
//                                }
//                            }
//                        });
                    } else {
                        Toast.makeText(this, "Please provide valid password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Please provide valid mobile number", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please provide valid address", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please provide valid username", Toast.LENGTH_SHORT).show();
        }
    }


    public void sendOTP() {
        otp = String.valueOf(random.nextInt(100000));
        String mob = t3.getText().toString().trim();
        String username = t1.getText().toString().trim();

        String msg = "Hii, " + username + " Welcome to my application.\nTo open your account please verify with the Otp.\nYour otp is : " + otp;

        if (!mob.equals("") && !msg.equals("")) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(mob, null, msg, null, null);
            Toast.makeText(this, "OTP sent on registered mobile number " + mob, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendOTP();
        } else {
            Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    public void verify(View view) {
        String otpn = t6.getText().toString();
        String username = t1.getText().toString().trim();
        String address = t2.getText().toString().trim();
        String mobile = t3.getText().toString();
        String emailaddress = t3.getText().toString();
        String password = t4.getText().toString().trim();
        String conpassword = t5.getText().toString().trim();
        emailaddress += "@gmail.com";
        if (!otpn.equals("")) {
            if (otp.equals(otpn)) {
                progressDialog.setMessage("Please wait while Registration Complete...");
                progressDialog.setTitle("Registration");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                Map<String, String> v = new HashMap<>();
                v.put("username", username);
                v.put("address", address);
                v.put("mobile", mobile);
                mAuth.createUserWithEmailAndPassword(emailaddress, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            String id = task.getResult().getUser().getUid().toString();
                            db.getReference().child("users").child(id).setValue(v);
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(), login_activity.class);
                            i.putExtra("flag",1);
                            startActivity(i);
                        } else {
                            progressDialog.dismiss();
                            t1.setText("");
                            t2.setText("");
                            t3.setText("");
                            t4.setText("");
                            t5.setText("");
                            t6.setText("");
                            Toast.makeText(getApplicationContext(),"User Already Exist!!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
            else {
                Toast.makeText(this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please provide OTP", Toast.LENGTH_SHORT).show();
        }
    }
}