package com.example.techspark;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class home_fragment extends Fragment {



    ImageView success;
    ProgressBar progressBar;

    Button establishConnection,nextbutton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_fragment, container, false);
        success = view.findViewById(R.id.imageview1);
        progressBar = view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);

        establishConnection = view.findViewById(R.id.establishconn);
        nextbutton = view.findViewById(R.id.nxtbtn);
        establishConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                establishConnection(view);
            }

            private void establishConnection(View view) {
                int delayMillis = 3000;
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Hide the ProgressBar after the delay
                        progressBar.setVisibility(View.GONE);
                        success.setImageResource(R.drawable.tickmark);
                        Toast.makeText(view.getContext(), "Connection Established", Toast.LENGTH_SHORT).show();
                    }
                }, delayMillis);
            }
        });

        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(), tokengeneration.class);
                startActivity(i);
            }
        });
        return view;
    }
}