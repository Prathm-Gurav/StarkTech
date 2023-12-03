package com.example.techspark;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class profile_fragment extends Fragment {
    String mob;
    FirebaseFirestore db;
    EditText t1,t2,t3;
    View view;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db=FirebaseFirestore.getInstance();

        view=inflater.inflate(R.layout.fragment_profile_fragment, container, false);
        fetch_details();
        t1=view.findViewById(R.id.name);
        t2=view.findViewById(R.id.address);
        t3=view.findViewById(R.id.phone);
        return view;
    }

    private void fetch_details() {
        Bundle b= getArguments();
        if(b!=null){
            mob=b.getString("mobile");
        }
        db.collection("users").whereEqualTo("mobile",mob).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String usernameDb = document.getString("username");
                        String addressDb = document.getString("address");
                        t1.setText(usernameDb);
                        t2.setText(addressDb);
                        t3.setText(mob);
                    }
                }
            }
        });
    }

    public void logout(View view) {
        Intent i = new Intent(view.getContext(),login_activity.class);
        startActivity(i);
    }
}