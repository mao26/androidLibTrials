package com.example.nathanschwan.gyhtest;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.ListView;
import android.content.Intent;

//imports for RxJava
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.user.IdentityManager;
import com.amazonaws.regions.Regions;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import rx.Subscriber;

public class LogIn extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener{

    //google sign in services
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;


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
    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    private ProgressDialog mProgressDialog;

    //amazon signin cognito services.
    public CognitoCachingCredentialsProvider credentialsProvider;


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
//    //FOR SIGNUP activity testing
//    @Override
//    protected void onActivityResult(int reqcode, int rescode, Intent data){
//        switch(reqcode){
//            case(1):
//                Toast.makeText(this, data.getStringExtra("pass"), Toast.LENGTH_SHORT).show();
//                uname.setText(data.getStringExtra("uname"));
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            mStatusTextView.setText("Signed in as: " + acct.getDisplayName());
            updateUI(true);

            String idToken = acct.getIdToken();
            Map<String, String> logins = new HashMap<String, String>();
            logins.put("accounts.google.com", idToken);
            credentialsProvider.setLogins(logins);


        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("LOADING");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText("SIGNED OUT");

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);



        findViewById(R.id.sign_in_button).setOnClickListener(this);

        // Views
        mStatusTextView = (TextView) findViewById(R.id.status);

        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.disconnect_button).setOnClickListener(this);
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
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.

        credentialsProvider = ((MyApplication) this.getApplication()).getProvider();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1068196854265-qi23mhnfeuh3as2elgs5nmlcvcp4e4e4.apps.googleusercontent.com")
                .requestEmail()
                .build();
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.disconnect_button:
                revokeAccess();
                break;
        }
    }
    // [START signIn]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signIn]

    // [START signOut]
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END signOut]
    // [START revokeAccess]
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]
}