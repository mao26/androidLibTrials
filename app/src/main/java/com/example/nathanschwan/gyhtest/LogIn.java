package com.example.nathanschwan.gyhtest;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
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
import android.content.Intent;

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
    //for Custom Adapter
    private ArrayList<GithubClient.Repo> repos = new ArrayList<GithubClient.Repo>();
    private ArrayAdapter<String> myarray;
    //custom adapter
    private ArrayAdapter<GithubClient.Repo> customAdapter;


    //custom adapter implementation
    class repoArrayAdapter extends ArrayAdapter<GithubClient.Repo>{
        private Context context;
        private List<GithubClient.Repo> repoList;

        //constructor
        public repoArrayAdapter(Context context, int resource, ArrayList<GithubClient.Repo> objects){
            super(context, resource, objects);

            this.context = context;
            this.repoList = objects;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            GithubClient.Repo thisrepo = repoList.get(position);

            //get inflator
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.gitrepo, null);

            //set onclick listener for rows
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView name = (TextView) v.findViewById(R.id.name);
                    String name1 = name.getText().toString();
                    Toast.makeText(context, name1, Toast.LENGTH_SHORT).show();
                }
            });


            TextView name = (TextView) view.findViewById(R.id.name);
            TextView id = (TextView) view.findViewById(R.id.id);

            name.setText(thisrepo.getName());
            id.setText(Integer.toString(thisrepo.getId()));

            return view;

        }
    }

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
        //with RXJAVA below
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
//                        for (GithubClient.Repo name: response) {
//                            usernames.add(name.name);
//                            repos.add(name);
//                        }
//                        myarray.notifyDataSetChanged();
//                        customAdapter.notifyDataSetChanged();
//                    }
//                });
        // TRIGGER SIGNUP
        Intent signup = new Intent(this, SignUp.class);
//        startActivityForResult(signup, 1);
        startActivity(signup);
    }

    @Override
    protected void onActivityResult(int reqcode, int rescode, Intent data){
        switch(reqcode){
            case(1):
                Toast.makeText(this, data.getStringExtra("pass"), Toast.LENGTH_SHORT).show();
                uname.setText(data.getStringExtra("uname"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);
//        lambda below versus butterknife annotation OnClick above
//        submit.setOnClickListener(e->{
//            String enterUname = uname.getText().toString();
//            Toast.makeText(this, "Hello World", Toast.LENGTH_LONG).show();
//            usernames.add(enterUname);
//            myarray.notifyDataSetChanged();
//        });
        myarray = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usernames);
        customAdapter = new repoArrayAdapter(this, 0, repos);
        //choose between regular adapter or custom adapter.
        unamelist.setAdapter(customAdapter);
    }
}