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
//        private String login;
//        private String blog;
//        private int public_repos;
//
//        public String getLogin() {
//            return login;
//        }
//
//        public String getBlog() {
//            return blog;
//        }
//
//        public int getPublicRepos() {
//            return public_repos;
//        }
    }

    @GET("users/{user}/repos")
//    Call<List<Repo>> listRepos(@Path("user") String user);
    // Retrofit basic implementation
    Observable<List<Repo>> listRepos(@Path("user") String user);

}
