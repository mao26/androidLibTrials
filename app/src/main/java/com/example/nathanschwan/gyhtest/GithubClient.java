package com.example.nathanschwan.gyhtest; /**
 * Created by nathanschwan on 10/14/16.
 */

import retrofit2.http.*;
import java.util.List;
import retrofit2.Call;
import rx.Observable;


public interface GithubClient {

    static class Repo {
        String name;
        //Original retrofit implementation
    }

    @GET("users/{user}/repos")
//    Call<List<Repo>> listRepos(@Path("user") String user);
    // Retrofit basic implementation
//    Call<List<Repo>> listRepos(@Path("user") String user);
    //For ASYNC 2 with RxJava
    Observable<List<Repo>> listRepos(@Path("user") String user);

}
