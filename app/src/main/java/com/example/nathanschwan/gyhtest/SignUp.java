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
import com.amazonaws.models.nosql.UserDO;



public class SignUp extends AppCompatActivity {

    private final static String LOG_TAG = Application.class.getSimpleName();

    @BindView(R.id.uname)
    EditText uname;
    @BindView(R.id.pass)
    EditText pass;
    @BindView(R.id.passconfirm)
    EditText passconfirm;

    @OnClick(R.id.submit)
    protected void onClick() {
//        String urname = uname.getText().toString();
//        String pass1 = pass.getText().toString();
//        String pass2 = passconfirm.getText().toString();
//        if (pass1.equals(pass2)) {
//            Toast.makeText(this, "Validated", Toast.LENGTH_LONG).show();
//            Intent result = new Intent();
//            result.putExtra("uname", urname);
//            result.putExtra("pass", pass1);
//            setResult(RESULT_OK, result);
//            finish();
//
//        }
//        else{
//            Toast.makeText(this, "Invalid data; Try again!", Toast.LENGTH_SHORT).show();
//        }
        Thread setData = new Thread(){
                public void run(){
                    insertData();
                }
        };
        setData.start();
        Toast.makeText(this, "Sent Data to DB", Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        while( setData.isAlive()){}
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

    }

    public void insertData() {
        // Fetch the default configured DynamoDB ObjectMapper
        final DynamoDBMapper dynamoDBMapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();
        final FitnessLevelsDO note = new FitnessLevelsDO(); // Initialize the Notes Object


        note.setPushup((double) 20);
        note.setSitups((double) 25);
        note.setMile(24.2);
        note.setUserId("nathan");


        AmazonClientException lastException = null;

        try {
            dynamoDBMapper.save(note);
        } catch (final AmazonClientException ex) {
            Log.e(LOG_TAG, "Failed saving item : " + ex.getMessage(), ex);
            lastException = ex;
        }


    }
}