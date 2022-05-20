package com.main.kids.remote;

import com.main.kids.model.ResObj;
import com.main.kids.model.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("api/auth/signin")
    Call<ResObj> login(@Body UserModel userModel);
}
