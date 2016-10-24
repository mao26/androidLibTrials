package com.example.nathanschwan.gyhtest;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by nathanschwan on 10/20/16.
 */

public interface LambdaInterface {

    @POST("helloDBWorld")
//    Call<List<Repo>> listRepos(@Path("user") String user);
        // Retrofit basic implementation
        //For ASYNC 2 with RxJava
    Observable<List<GithubClient.Repo>> listRepos(@Path("user") String user);
}
