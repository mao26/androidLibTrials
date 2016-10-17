package com.example.nathanschwan.gyhtest;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
/**
 * Created by nathanschwan on 10/14/16.
 */

public class ServiceGenerator {
    public static final String API_BASE_URL = "https://api.github.com/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass){
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

//    public static void main(String... args){
//        GithubClient client = ServiceGenerator.createService(GithubClient.class);
//
//        Call<List<GithubClient.Repo>> call = client.listRepos("nschwan94");
//        List<GithubClient.Repo> repos = new ArrayList<>();
//        try{
//            repos = call.execute().body();
//        }
//        catch (IOException e){
//            System.out.print(e.getMessage());
//        }
//
//        for (GithubClient.Repo repo:repos) {
//            System.out.println(repo.name);
//        }
//    }

}
