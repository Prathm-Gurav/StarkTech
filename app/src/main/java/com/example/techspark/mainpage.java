package com.example.techspark;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class mainpage extends AppCompatActivity {

    int lot_count =1;
    RecyclerView r1;
    List<String> list ;
    lifting_chart_adapter adapter;
    Button editButton,nextBatchButton,totalBirdCountButton,birdWeightButton,okButton;
    CardView totalBirdCountMinusButton,totalBirdCountAddButton;
    CardView birdWeightMinusButton,birdWeightAddButton;
    LinearLayout totalBirdCountLinearLayout,birdWeightLinearLayout;
    EditText editTotalBirdCountEditText,editBirdWeightEditText,edittextInitialBirdCount;
    EditText totalBirdCountEditText,birdWeightEditText,totalBirdWeightEditText;
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

        birdWeightAddButton=findViewById(R.id.button_add_bird_weight);
        birdWeightMinusButton=findViewById(R.id.button_minus_bird_weight);

        totalBirdCountLinearLayout=findViewById(R.id.linear_total_bird_count);
        birdWeightLinearLayout=findViewById(R.id.bird_weight);

        editTotalBirdCountEditText=findViewById(R.id.edittext_total_bird_count_edit);
        editBirdWeightEditText=findViewById(R.id.edittext_bird_weight_edit);

        totalBirdCountEditText=findViewById(R.id.edittext_total_bird_count);
        totalBirdWeightEditText=findViewById(R.id.edittext_totalbw);
        birdWeightEditText=findViewById(R.id.edittext_bw);

        totalBirdCountButton=findViewById(R.id.button_total_bird_count);
        birdWeightButton=findViewById(R.id.button_bird_weight);
        okButton=findViewById(R.id.button_ok);
        r1=findViewById(R.id.recycler_view_id);
        list = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        r1.setLayoutManager(layoutManager);
        adapter = new lifting_chart_adapter(this, list);
        r1.setAdapter(adapter);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editButton.setVisibility(View.GONE);
                nextBatchButton.setVisibility(View.GONE);
                totalBirdCountButton.setVisibility(View.VISIBLE);
                birdWeightButton.setVisibility(View.VISIBLE);
            }
        });

        totalBirdCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalBirdCountLinearLayout.setVisibility(View.VISIBLE);
                okButton.setVisibility(View.VISIBLE);
                totalBirdCountButton.setVisibility(View.GONE);
                birdWeightButton.setVisibility(View.GONE);
            }
        });

        birdWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                birdWeightLinearLayout.setVisibility(View.VISIBLE);
                okButton.setVisibility(View.VISIBLE);
                birdWeightButton.setVisibility(View.GONE);
                totalBirdCountButton.setVisibility(View.GONE);
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalBirdCountLinearLayout.setVisibility(View.GONE);
                birdWeightLinearLayout.setVisibility(View.GONE);
                editButton.setVisibility(View.VISIBLE);
                nextBatchButton.setVisibility(View.VISIBLE);
                totalBirdCountButton.setVisibility(View.GONE);
                birdWeightButton.setVisibility(View.GONE);
                okButton.setVisibility(View.GONE);
            }
        });



        totalBirdCountMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=Integer.parseInt(edittextInitialBirdCount.getText().toString())-1;
                editTotalBirdCountEditText.setText(String.valueOf(num));
                edittextInitialBirdCount.setText(String.valueOf(num));
            }
        });

        totalBirdCountAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=Integer.parseInt(edittextInitialBirdCount.getText().toString())+1;
                editTotalBirdCountEditText.setText(String.valueOf(num));
                edittextInitialBirdCount.setText(String.valueOf(num));
            }
        });

        birdWeightMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=Integer.parseInt(editBirdWeightEditText.getText().toString())-1;
                birdWeightEditText.setText(String.valueOf(num));
                editBirdWeightEditText.setText(String.valueOf(num));
            }
        });

        birdWeightAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=Integer.parseInt(editBirdWeightEditText.getText().toString())+1;
                birdWeightEditText.setText(String.valueOf(num));
                editBirdWeightEditText.setText(String.valueOf(num));
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

        edittextInitialBirdCount.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                int data=Integer.parseInt(edittextInitialBirdCount.getText().toString());
                if(data%25==0) {
                    list.add(""+(lot_count++));
                    adapter.notifyItemInserted(list.size() - 1);
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
                if(task.isSuccessful()){
                }
                edittextInitialBirdCount.setText("0");
                birdWeightEditText.setText("0");
                editTotalBirdCountEditText.setText("0");
                editBirdWeightEditText.setText("0");
            }
        });
    }
    public  void gotoHomePage(View view){
        Intent i  = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}