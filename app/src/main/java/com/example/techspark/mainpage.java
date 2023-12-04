package com.example.techspark;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class mainpage extends AppCompatActivity {

    Button editButton,nextBatchButton,totalBirdCountButton,averageButton,cancelButton,okButton;
    CardView totalBirdCountMinusButton,totalBirdCountAddButton;
    CardView averageMinusButton,averageAddButton;
    LinearLayout totalBirdCountLinearLayout,averageLinearLayout;
    EditText editTotalBirdCountEditText,editAverageEditText,edittextInitialBirdCount;
    EditText totalBirdCountEditText,averageEditText,totalBirdWeightEditText;
    FirebaseFirestore db;
    DocumentReference documentReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        editButton=findViewById(R.id.button_edit);
        nextBatchButton=findViewById(R.id.button_next_batch);
        edittextInitialBirdCount  =findViewById(R.id.edittext_bc);

        totalBirdCountAddButton=findViewById(R.id.button_add_total_bird_count);
        totalBirdCountMinusButton=findViewById(R.id.button_minus_total_bird_count);

        averageAddButton=findViewById(R.id.button_add_average);
        averageMinusButton=findViewById(R.id.button_minus_average);

        totalBirdCountLinearLayout=findViewById(R.id.linear_total_bird_count);
        averageLinearLayout=findViewById(R.id.linear_average);

        editTotalBirdCountEditText=findViewById(R.id.edittext_total_bird_count_edit);
        editAverageEditText=findViewById(R.id.edittext_average_edit);

        totalBirdCountEditText=findViewById(R.id.edittext_total_bird_count);
        totalBirdWeightEditText=findViewById(R.id.edittext_totalbw);
        averageEditText=findViewById(R.id.edittext_average);

        totalBirdCountButton=findViewById(R.id.button_total_bird_count);
        averageButton=findViewById(R.id.button_average);
        cancelButton=findViewById(R.id.button_cancel);
        okButton=findViewById(R.id.button_ok);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editButton.setVisibility(View.GONE);
                nextBatchButton.setVisibility(View.GONE);
                totalBirdCountButton.setVisibility(View.VISIBLE);
                averageButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
            }
        });

        totalBirdCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalBirdCountLinearLayout.setVisibility(View.VISIBLE);
                okButton.setVisibility(View.VISIBLE);
                totalBirdCountButton.setVisibility(View.GONE);
                averageButton.setVisibility(View.GONE);
            }
        });

        averageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                averageLinearLayout.setVisibility(View.VISIBLE);
                okButton.setVisibility(View.VISIBLE);
                averageButton.setVisibility(View.GONE);
                totalBirdCountButton.setVisibility(View.GONE);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editButton.setVisibility(View.VISIBLE);
                nextBatchButton.setVisibility(View.VISIBLE);
                totalBirdCountButton.setVisibility(View.GONE);
                averageButton.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalBirdCountLinearLayout.setVisibility(View.GONE);
                averageLinearLayout.setVisibility(View.GONE);
                editButton.setVisibility(View.VISIBLE);
                nextBatchButton.setVisibility(View.VISIBLE);
                totalBirdCountButton.setVisibility(View.GONE);
                averageButton.setVisibility(View.GONE);
                cancelButton.setVisibility(View.GONE);
                okButton.setVisibility(View.GONE);
            }
        });



        totalBirdCountMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=Integer.parseInt(totalBirdCountEditText.getText().toString())-1;
                editTotalBirdCountEditText.setText(String.valueOf(num));
                totalBirdCountEditText.setText(String.valueOf(num));
            }
        });

        totalBirdCountAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=Integer.parseInt(totalBirdCountEditText.getText().toString())+1;
                editTotalBirdCountEditText.setText(String.valueOf(num));
                totalBirdCountEditText.setText(String.valueOf(num));
            }
        });

        averageMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=Integer.parseInt(totalBirdWeightEditText.getText().toString())-1;
                totalBirdWeightEditText.setText(String.valueOf(num));
                totalBirdWeightEditText.setText(String.valueOf(num));
            }
        });

        averageAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=Integer.parseInt(totalBirdWeightEditText.getText().toString())+1;
                totalBirdWeightEditText.setText(String.valueOf(num));
            }
        });


        //fetching bird count from firebase firestore

        db = FirebaseFirestore.getInstance();
        documentReference = db.collection("hen_data").document("count_data");
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(mainpage.this, "listen failed", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    long totalBirdCount = documentSnapshot.getLong("total_count");
                    edittextInitialBirdCount.setText(String.valueOf(totalBirdCount));
                } else {
                    Toast.makeText(mainpage.this, "current data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void gotoNextBatch(View view){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("total_count",0);

        db.collection("hen_data").document("count_data").set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                edittextInitialBirdCount.setText("0");
            }
        });
    }


}