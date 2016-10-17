package com.example.nathanschwan.gyhtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUp extends AppCompatActivity {

    @BindView(R.id.uname) EditText uname;
    @BindView(R.id.pass) EditText pass;
    @BindView(R.id.passconfirm) EditText passconfirm;

    @OnClick(R.id.submit)
    protected void onClick(){
        String urname = uname.getText().toString();
        String pass1 = pass.getText().toString();
        String pass2 = passconfirm.getText().toString();
        if (pass1.equals(pass2)) {
            Toast.makeText(this, "Validated", Toast.LENGTH_LONG).show();
            Intent result = new Intent();
            result.putExtra("uname", urname);
            result.putExtra("pass", pass1);
            setResult(RESULT_OK, result);
            finish();

        }
        else{
            Toast.makeText(this, "Invalid data; Try again!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

    }
}
