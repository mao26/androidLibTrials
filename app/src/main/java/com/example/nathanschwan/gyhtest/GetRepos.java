package com.example.nathanschwan.gyhtest;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by nathanschwan on 10/14/16.
 */

//USE FOR ASYNC 1 case in LogIn.java activity.

//public class GetRepos extends AsyncTask<Void, Void, List<GithubClient.Repo>> {
//
//    public interface asyncResult{
//        public void showAsyncResult(List<GithubClient.Repo> val);
//    }
//
//    asyncResult delegate;
//
//    public GetRepos(asyncResult delegate){
//        this.delegate =delegate;
//    }
//
//    @Override
//    protected List<GithubClient.Repo> doInBackground(Void... params){
//        GithubClient client = ServiceGenerator.createService(GithubClient.class);
//        Call<List<GithubClient.Repo>> call = client.listRepos("nschwan94");
//        List<GithubClient.Repo> repos = new ArrayList<>();
//        try{
//            repos = call.execute().body();
//        }
//        catch (IOException e){
//            System.out.print(e.getMessage());
//        }
//        return repos;
//    }
//
//    protected void onPostExecute(List<GithubClient.Repo> val){
//        delegate.showAsyncResult(val);
//    }
//
//}
