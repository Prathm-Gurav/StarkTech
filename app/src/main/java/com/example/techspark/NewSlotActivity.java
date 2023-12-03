package com.example.techspark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class NewSlotActivity extends AppCompatActivity {

    RecyclerView r1;
    List<String> list ;
    lifting_chart_adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_slot);

        r1=findViewById(R.id.recycler_view_id);

        list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        r1.setLayoutManager(layoutManager);
        adapter = new lifting_chart_adapter(this, list);
        r1.setAdapter(adapter);

    }

    public void temproary_add_data(View view) {
        list.add("4");
        adapter.notifyItemInserted(list.size() - 1);
    }
}