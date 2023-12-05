package com.example.techspark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    String mobile;
    ImageView success;
    ProgressBar progressBar;
    Intent i;
    View view;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         i=getIntent();
         mobile=i.getStringExtra("mobile");
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout =findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new home_fragment()).commit();
            navigationView.setCheckedItem(R.id.home_nav);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home_nav){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new home_fragment()).commit();
        }
        if (item.getItemId()==R.id.profile_nav){
            Bundle b = new Bundle();
            b.putString("mobile", mobile);

            profile_fragment fragment = new profile_fragment();
            fragment.setArguments(b);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

            //Toast.makeText(this, ""+mobile, Toast.LENGTH_SHORT).show();

        }
         if (item.getItemId()==R.id.about_nav){

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new about_fragment()).commit();
        }
         if(item.getItemId()==R.id.logout_nav) {
             finish();

             FirebaseAuth.getInstance().signOut();
             Intent i1=new Intent(this,login_activity.class);
             startActivity(i1);
         }


        drawerLayout.closeDrawer(GravityCompat.START);
        return  true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }


}