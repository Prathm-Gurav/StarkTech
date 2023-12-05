package com.example.techspark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Random;

public class changepassword extends AppCompatActivity {

    EditText t1, t2, t3, t4;
    final Random random = new Random();
    String otp;
    FirebaseFirestore db;
    int flag;
    String mob;
    String newPass, conPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        t1 = findViewById(R.id.mobileno);
        t2 = findViewById(R.id.otpno);
        t3 = findViewById(R.id.password);
        t4 = findViewById(R.id.conpassword);
        t3.setEnabled(false);
        t4.setEnabled(false);
        flag = 0;
        db = FirebaseFirestore.getInstance();
    }

    public void get_otp_cng_pass(View view) {
        String mobile = t1.getText().toString().trim();
        if (!mobile.equals("")) {
            if (mobile.length() == 10) {
                db.collection("users").whereEqualTo("mobile", mobile).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean mobileFound = false;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String mobileDb = document.getString("mobile");
                                if (mobileDb.equals(mobile)) {
                                    mobileFound = true;
                                    break;
                                }
                            }

                            if (mobileFound) {
                                if (ContextCompat.checkSelfPermission(changepassword.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                                    sendOTP();
                                    Toast.makeText(changepassword.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    ActivityCompat.requestPermissions(changepassword.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                                }
                            } else {
                                Toast.makeText(changepassword.this, "Mobile number not found. Please Sign Up", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please provide a valid mobile number", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please provide a mobile number", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendOTP() {
        otp = String.valueOf(random.nextInt(100000));
        mob = t1.getText().toString().trim();
        String msg = "Hii, Welcome to BHM.\nTo change your account password please verify with the Otp.\nYour otp is : " + otp;
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

    public void verify_otp_cng_pass(View view) {
        String otpvrf = t2.getText().toString().trim();
        if (!otpvrf.equals("")) {
            if (otpvrf.equals(otp)) {
                Toast.makeText(this, "Verified successfully", Toast.LENGTH_SHORT).show();
                flag = 1;
                t3.setEnabled(true);
                t4.setEnabled(true);
            }
        } else {
            Toast.makeText(this, "Please provide OTP", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveDetails_cng_pass(View view) {
        newPass = t3.getText().toString().trim();
        conPass = t4.getText().toString().trim();
        mob = t1.getText().toString().trim();
        final String emailaddress = mob + "@gmail.com";
        Toast.makeText(this, ""+emailaddress, Toast.LENGTH_SHORT).show();

        if (flag == 1) {
            if (!newPass.equals("") && !conPass.equals("")) {
                if (newPass.equals(conPass)) {
                    FirebaseAuth.getInstance().fetchSignInMethodsForEmail(emailaddress)
                            .addOnCompleteListener(new OnCompleteListener<com.google.firebase.auth.SignInMethodQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<com.google.firebase.auth.SignInMethodQueryResult> task) {
                                    if (task.isSuccessful()) {
                                        com.google.firebase.auth.SignInMethodQueryResult result = task.getResult();
                                        if (result.getSignInMethods().size() > 0) {
                                            // User with the provided email address exists
                                            // Get the user and delete it
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            if (user != null) {
                                                user.delete()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(getApplicationContext(), "User deleted successfully", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    Toast.makeText(getApplicationContext(), "Failed to delete user", Toast.LENGTH_SHORT).show();

                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                }
                            });
                } else {
                    Toast.makeText(this, "New password and Confirm Password should be the same", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please provide a password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "OTP verification isn't completed! Try Again", Toast.LENGTH_SHORT).show();
        }
    }
}
