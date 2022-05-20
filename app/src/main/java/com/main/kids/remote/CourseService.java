package com.main.kids.remote;

import com.main.kids.model.Course;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CourseService {
    @POST("/api/private/course")
    Call<ArrayList<Course>> getCourses(@Header("x-access-token") String x_access_token);
}
