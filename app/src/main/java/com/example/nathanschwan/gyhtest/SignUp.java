package com.example.nathanschwan.gyhtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


import com.amazonaws.AmazonClientException;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.models.nosql.FitnessLevelsDO;
import com.amazonaws.models.nosql.UsersTESTDO;

import com.amazonaws.models.nosql.UserDO;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.util.Log;
import rx.Subscriber;




public class SignUp extends AppCompatActivity {

    private final static String LOG_TAG = Application.class.getSimpleName();

    @BindView(R.id.uname)
    EditText uname;
    @BindView(R.id.pass)
    EditText pass;
    @BindView(R.id.passconfirm)
    EditText passconfirm;


    @OnClick(R.id.showusers)


    @OnClick(R.id.submit)
    protected void onClick() {
        String urname = uname.getText().toString();
        String pass1 = pass.getText().toString();
        String pass2 = passconfirm.getText().toString();
        if (pass1.equals(pass2)) {
            Toast.makeText(this, "Validated", Toast.LENGTH_LONG).show();
            Intent result = new Intent();
            result.putExtra("uname", urname);
            result.putExtra("pass", pass1);

            //save in DB.
            Observable<Boolean> savetask = Observable.fromCallable(()->insertData(urname, pass1));

            savetask.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Boolean>() {
                                   @Override
                                   public final void onCompleted() {
                                       Log.e(LOG_TAG, "INSERTED DATA SUCCESSFULLY with OBSERVABLE");
                                   }

                                   @Override
                                   public final void onError(Throwable e) {
                                       Log.e("GithubDemo", e.getMessage());
                                   }

                                   @Override
                                   public final void onNext(Boolean response) {
                                   }
                               });
            setResult(RESULT_OK, result);
            finish();

        }
        else{
            Toast.makeText(this, "Invalid data; Try again!", Toast.LENGTH_SHORT).show();
        }
        ////above code is for validating uname/pass input text.
        ////BELOW code is initial dynamo mapper threading to save data.
//        Thread setData = new Thread(){
//                int attempts = 0;
//                public void run(){
//                    boolean answer = insertData();
//                    attempts += 1;
//                    if (!answer && attempts < 5){
//                        run();
//                    }
//                    Log.e(LOG_TAG, "INSERTED DATA SUCCESSFULLY");
//                    return;
//
//                }
//        };
//        setData.start();

        Toast.makeText(this, "Sent Data to DB", Toast.LENGTH_LONG).show();
//        while( setData.isAlive()){}
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

    }

    public boolean insertData(String name, String password) {
        // Fetch the default configured DynamoDB ObjectMapper
        final DynamoDBMapper dynamoDBMapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();
        final UsersTESTDO note = new UsersTESTDO(); // Initialize the Notes Object

        note.setName(name);
        note.setPassword(password);
        note.setUserId("NOTUUID");
//
//        note.setPushup((double) 20);
//        note.setSitups((double) 25);
//        note.setMile(24.2);
//        note.setUserId("nathan");
//

        AmazonClientException lastException = null;
        Log.e(LOG_TAG, "INSERTING DATA");


        try {
            dynamoDBMapper.save(note);
        } catch (final AmazonClientException ex) {
            Log.e(LOG_TAG, "Failed saving item : " + ex.getMessage(), ex);
            lastException = ex;
            return false;
        }
        return true;


    }
}