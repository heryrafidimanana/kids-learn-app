package com.main.kids;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.main.kids.databinding.ActivityNavBinding;
import com.main.kids.model.Course;
import com.main.kids.remote.ApiUtils;
import com.main.kids.remote.CourseService;

import java.util.ArrayList;

public class NavActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavBinding binding;
    CourseService courseService;

    private ArrayList<Course> courses;

    private ArrayList<Course> getCourses(){

        SharedPreferences sp = getApplicationContext().getSharedPreferences("Token", Context.MODE_PRIVATE);
        String x_access_token = sp.getString("x-access-token", "");

        Call<ArrayList<Course>> call = courseService.getCourses(x_access_token);
        ArrayList<Course> courses = new ArrayList<Course>();

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){
                    ArrayList<Course> courses = (ArrayList<Course>) response.body();
                    if(!courses.isEmpty()){
                        //set courses
//                        this.courses = courses ;
                    }
                    else{
                        Toast.makeText(NavActivity.this, "There is nothing!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(NavActivity.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(NavActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return courses;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        courseService = ApiUtils.getCourseService();

        SharedPreferences sp = getApplicationContext().getSharedPreferences("Token", Context.MODE_PRIVATE);
        String x_access_token = sp.getString("x-access-token", "");

        binding = ActivityNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNav.toolbar);
        binding.appBarNav.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_nav);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_nav);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}