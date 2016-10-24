package com.example.nathanschwan.gyhtest;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.com.google.gson.annotations.SerializedName;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;


import com.amazonaws.mobileconnectors.apigateway.ApiRequest;
import com.amazonaws.mobileconnectors.apigateway.ApiResponse;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
import com.amazonaws.models.nosql.UsersTESTDO;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.gson.Gson;


// import the CognitoCredentialsProvider object from the auth package


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import rx.Subscriber;





public class SignUp extends AppCompatActivity {


    private final static String LOG_TAG = MyApplication.class.getSimpleName();

    @BindView(R.id.uname)
    EditText uname;
    @BindView(R.id.pass)
    EditText pass;
    @BindView(R.id.passconfirm)
    EditText passconfirm;
    @BindView(R.id.usersList)
    ListView usersList;

    private LambdaMicroserviceClient client;
    private CognitoCachingCredentialsProvider credentialsProvider;
    private AmazonDynamoDBClient ddbClient;
    private DynamoDBMapper mapper;
    public ApiClientFactory factory;

    private ArrayList<MyUsers> usersArray;
    private ArrayAdapter<MyUsers> customAdapter;


    @OnClick(R.id.showusers)
    protected void onShowUsersClick(){
        Observable<Boolean> showtask = Observable.fromCallable(()->showData());
        Thread setData = new Thread(){
                int attempts = 0;
                public void run(){
                    boolean answer = showData();
                    attempts += 1;
                    if (!answer && attempts < 5){
                        run();
                    }
                    Log.e(LOG_TAG, "INSERTED DATA SUCCESSFULLY");
                    return;

                }
        };
        setData.start();


    }

    @OnClick(R.id.submit)
    protected void onClick() {
        String urname = uname.getText().toString();
        String pass1 = pass.getText().toString();
        String pass2 = passconfirm.getText().toString();
//        if (pass1.equals(pass2)) {
//            Toast.makeText(this, "Validated", Toast.LENGTH_LONG).show();
//            Intent result = new Intent();
//            result.putExtra("uname", urname);
//            result.putExtra("pass", pass1);
//
//            //save in DB.
//            Observable<Boolean> savetask = Observable.fromCallable(()->insertData(urname, pass1));
//
//            savetask.subscribeOn(Schedulers.newThread())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Subscriber<Boolean>() {
//                                   @Override
//                                   public final void onCompleted() {
//                                       Log.e(LOG_TAG, "INSERTED DATA SUCCESSFULLY with OBSERVABLE");
//                                   }
//
//                                   @Override
//                                   public final void onError(Throwable e) {
//                                       Log.e("GithubDemo", e.getMessage());
//                                   }
//
//                                   @Override
//                                   public final void onNext(Boolean response) {
//                                   }
//                               });
//            setResult(RESULT_OK, result);
//            finish();
//
//        }
//        else{
//            Toast.makeText(this, "Invalid data; Try again!", Toast.LENGTH_SHORT).show();
//        }
        ////above code is for validating uname/pass input text.
        //BELOW code is initial dynamo mapper threading to save data.
        Thread setData = new Thread(){
                int attempts = 0;
                public void run(){
                    boolean answer = insertData(urname, pass1);
                    attempts += 1;
                    if (!answer && attempts < 5){
                        run();
                    }
                    Log.e(LOG_TAG, "INSERTED DATA SUCCESSFULLY");
                    return;

                }
        };
        setData.start();
        while (setData.isAlive()){}
        customAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Sent Data to DB", Toast.LENGTH_LONG).show();
//        while( setData.isAlive()){}
        //finish();
    }

    class usersListAdapter extends ArrayAdapter<MyUsers> {
        private Context context;
        private List<MyUsers> usersList;

        public usersListAdapter(Context context, int resource, ArrayList<MyUsers> object) {
            super(context, resource, object);
            this.context = context;
            this.usersList = object;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            MyUsers thisuser = usersList.get(position);

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

            name.setText(thisuser.getUser());
            id.setText(thisuser.getPass());

            return view;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        credentialsProvider = ((MyApplication) this.getApplication()).getProvider();
        factory = ((MyApplication) this.getApplication()).getFactory();
        ddbClient = new AmazonDynamoDBClient(credentialsProvider);
        mapper = new DynamoDBMapper(ddbClient);

        // Create an instance of your SDK.
        client = factory.build(LambdaMicroserviceClient.class);

        usersArray = new ArrayList<MyUsers>();
        customAdapter = new usersListAdapter(this, 0, usersArray);
        //setting up list adapter
        usersList.setAdapter(customAdapter);




    }


    //practice deserializing with GSON
    public class UsersTestTable {
        public MyUsers[] Items;

        public int Count;

        public UsersTestTable(){
            this.Items = Items;
        }
    }

    public class MyUsers{
        private String password;
        private String name;
        private String userId;

        public MyUsers(){
            this.password = password;
            this.name = name;
            this.userId = userId;
        }

        public String getUser(){
            return this.name;
        }

        public String getPass(){
            return this.password;
        }
    }

    public boolean insertData(String name, String password) {
//        ApiRequest request = new ApiRequest().withPath("/hello-world").withHttpMethod(HttpMethodName.GET);
        ApiRequest request = new ApiRequest()
                .withPath("/helloDBWorld")
                .withHttpMethod(HttpMethodName.GET)
                .withParameter("TableName", "gyhtest-mobilehub-903612549-UsersTEST");

        Log.e(LOG_TAG, credentialsProvider.getIdentityId());

        Log.d(LOG_TAG, request.getPath());

        ApiResponse response = null;
        try{
            response = client.execute(request);
            Log.i(LOG_TAG, response.getStatusText());
        }
        catch(Exception e){
            Log.d("EXCEPTION", e.getMessage());
        }

        //to read response with InputStream
        StringBuffer buffer = new StringBuffer();
        InputStreamReader isr = null;
        InputStream is = null;
        UsersTestTable resulttable = new UsersTestTable();
        try {
            is = response.getContent();
            isr = new InputStreamReader(is);
            Gson mygson = new Gson();
            // using gson to map json response to an object.
            resulttable = mygson.fromJson(isr, UsersTestTable.class);
//            BufferedReader br = new BufferedReader(isr);
//            String line = null;
//
//            while ((line = br.readLine()) != null) {
//                buffer.append(line + "\n");
//            }
        }
        catch(Exception e){
                Log.e(LOG_TAG, e.getMessage());
            }
        finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (isr != null) {
                    isr.close();
                }
            }
            catch(Exception e){}
        }
        for (MyUsers x : resulttable.Items){
            usersArray.add(x);
        }
//        Log.w(LOG_TAG, buffer.toString());

//        Log.v(LOG_TAG, resulttable.Items[0].name);


        return true;

        //        final UsersTESTDO note = new UsersTESTDO(); // Initialize the Notes Object
//
//        note.setName(name);
//        note.setPassword(password);
//        note.setUserId("NOTUUID102016");
////
////        note.setPushup((double) 20);
////        note.setSitups((double) 25);
////        note.setMile(24.2);
////        note.setUserId("nathan");
////
//
//        AmazonClientException lastException = null;
//        Log.e(LOG_TAG, "INSERTING DATA");
//
//
//        try {
//            mapper.save(note);
//        } catch (final AmazonClientException ex) {
//            Log.e(LOG_TAG, "Failed saving item : " + ex.getMessage(), ex);
//            lastException = ex;
//            return false;
//        }
//        return true;
    }

    protected boolean showData(){

        Runnable runnable = new Runnable() {
            public void run() {
                //DynamoDB calls go here
                try {
                    DynamoDBScanExpression expression = new DynamoDBScanExpression();
                    PaginatedScanList<UsersTESTDO> list = mapper.scan(UsersTESTDO.class, expression);
                    for (UsersTESTDO item: list) {
                        Log.e(LOG_TAG, item.getName() + item.getPassword());
                    }
                    //customAdapter.notifyDataSetChanged();
                }
                catch(Exception e){
                    Log.e(LOG_TAG, e.getMessage());
                    e.printStackTrace();
                }
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
        return true;
    }
}