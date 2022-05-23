package com.main.kids.remote;

import com.main.kids.model.Course;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CourseService {
    @GET("api/private/course")
    Call<ArrayList<Course>> getCourses(@Header("x-access-token") String x_access_token);
}
