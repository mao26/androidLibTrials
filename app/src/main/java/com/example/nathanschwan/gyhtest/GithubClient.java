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
        Integer id;
        //Original retrofit implementation

        public Repo(String name, Integer id){
            this.name = name;
            this.id = id;
        }

        public String getName(){ return name; }
        public Integer getId(){ return id; }
    }

    @GET("users/{user}/repos")
//    Call<List<Repo>> listRepos(@Path("user") String user);
    // Retrofit basic implementation
    //For ASYNC 2 with RxJava
    Observable<List<Repo>> listRepos(@Path("user") String user);

}
