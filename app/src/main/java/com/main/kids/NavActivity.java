package com.main.kids;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.main.kids.databinding.ActivityNavBinding;
import com.main.kids.model.Course;
import com.main.kids.remote.ApiUtils;
import com.main.kids.remote.CourseService;
import com.main.kids.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavBinding binding;
    CourseService courseService;
    public List<Course> courses = new ArrayList<Course>();
    ProgressDialog progressDialog;

    TextView txtUsername;
    TextView txtEmail;

    public List<Course> getCs(){
        return this.courses;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        courseService = ApiUtils.getCourseService();
        new StartAsyncTask().execute(getCs());

        SharedPreferences sp = NavActivity.this.getSharedPreferences("Token", Context.MODE_PRIVATE);
        String username = sp.getString("username", "");
        String email =  sp.getString("email", "");

        binding = ActivityNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id. toolbar ) ;
        setSupportActionBar(toolbar) ;

        FloatingActionButton fab = findViewById(R.id. fab ) ;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id. drawer_layout ) ;
        NavigationView navigationView = findViewById(R.id. nav_view );
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_nav);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        View headerView = navigationView.getHeaderView(0);
        txtUsername = (TextView) headerView.findViewById(R.id.username);
        txtEmail = (TextView) headerView.findViewById(R.id.email);

        txtUsername.setText(username);
        txtEmail.setText(email);

    }

    @Override
    public void onBackPressed () {
        DrawerLayout drawer = findViewById(R.id. drawer_layout ) ;
        if (drawer.isDrawerOpen(GravityCompat. START )) {
            drawer.closeDrawer(GravityCompat. START ) ;
        } else {
            super .onBackPressed() ;
        }
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_content_nav);

        // By calling onNavDestinationSelected(), you always get the right behavior
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
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

    protected class StartAsyncTask extends AsyncTask<List<Course>, Void,Void> {

        private List<Course> courses;

        public List<Course> getCourses(){
            return this.courses;
        }

        public StartAsyncTask(){
        }

        public StartAsyncTask(List<Course> cs){
            this.courses = cs;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experience
            progressDialog = new ProgressDialog(NavActivity.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(true);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }


        @Override
        public Void doInBackground(List<Course>... lists) {
            Log.e("startAsyncTask", "start");

            SharedPreferences sp = NavActivity.this.getSharedPreferences("Token", Context.MODE_PRIVATE);
            String x_access_token = sp.getString("x-access-token", "");

            Call<ArrayList<Course>> call = courseService.getCourses(x_access_token);

            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if(response.isSuccessful()){
                        ArrayList<Course> coursesFromApi = (ArrayList<Course>) response.body();
                        if(!coursesFromApi.isEmpty()){
                            //set courses
                            List<Course> theList = lists[0];
                            theList.addAll(coursesFromApi);
                            Log.e("Size of cs ", String.valueOf(lists[0].size()));
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
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            @SuppressLint("HandlerLeak") final Handler mHandler = new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {
                    super.handleMessage(msg);
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            };
            progressDialog.setMessage("Load courses ...");
            mHandler.sendMessageDelayed(new Message(), 3000);
        }
    }
}