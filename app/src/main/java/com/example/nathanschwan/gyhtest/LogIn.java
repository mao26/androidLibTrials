package com.example.nathanschwan.gyhtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.widget.ListView;

//imports for RxJava
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.util.Log;
import rx.Subscriber;

public class LogIn extends AppCompatActivity {



    @BindView(R.id.uname) EditText uname;
    @BindView(R.id.pass) EditText pass;
    @BindView(R.id.submit) Button submit;
    @BindView(R.id.unamelist) ListView unamelist;
    private List<String> usernames = new ArrayList<String>();
    private ArrayAdapter<String> myarray;
    @OnClick(R.id.submit)
    protected void submitClick(){
//        //SIMPLE 1
//        String enterUname = uname.getText().toString();
//        Toast.makeText(this, enterUname, Toast.LENGTH_LONG).show();
//        usernames.add(enterUname);
//        myarray.notifyDataSetChanged();
          // ASYNC1
//        GetRepos gettingRepos =  new GetRepos(new GetRepos.asyncResult() {
//            @Override
//            public void showAsyncResult(List<GithubClient.Repo> val) {
//                for (GithubClient.Repo repo:val) {
//                    usernames.add(repo.name);
//                }
//                myarray.notifyDataSetChanged();
//            }
//        });
//        gettingRepos.execute();
//        NON RXJAVA VERSION above
          // ASYNC2
//        with RXJAVA below
//        GithubClient client = ServiceGenerator.createService(GithubClient.class);
//        client.listRepos("nschwan94")
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<List<GithubClient.Repo>>() {
//                    @Override
//                    public final void onCompleted() {
//                        Toast.makeText(getApplicationContext(), "WE DID IT", Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public final void onError(Throwable e) {
//                        Log.e("GithubDemo", e.getMessage());
//                    }
//
//                    @Override
//                    public final void onNext(List<GithubClient.Repo> response) {
//                        for (GithubClient.Repo name: response){
//                            usernames.add(name.name);
//                        }
//                        myarray.notifyDataSetChanged();
//                    }
//                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);
//        lambda below versus butterknife annotation OnClick
//        submit.setOnClickListener(e->{
//            String enterUname = uname.getText().toString();
//            Toast.makeText(this, "Hello World", Toast.LENGTH_LONG).show();
//            usernames.add(enterUname);
//            myarray.notifyDataSetChanged();
//        });
        myarray = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usernames);
        unamelist.setAdapter(myarray);
    }
}