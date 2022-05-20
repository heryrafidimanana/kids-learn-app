package com.main.kids.remote;

public class ApiUtils {
    public static final String BASE_URL = "https://kids-app-api.herokuapp.com/";

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    public static CourseService getCourseService(){
        return RetrofitCourse.getCourses(BASE_URL).create(CourseService.class);
    }
}
