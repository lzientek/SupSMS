package com.supinfo.supsms.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.supinfo.supsms.callback.LoginCallback;
import com.supinfo.supsms.task.LoginTask;

import fr.lucas.android.supsms.supsms.R;

/**
 * Created by Annaud on 21/01/2015.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    EditText et_login ;
    EditText et_password ;
    Button btn_login ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_login = (EditText) findViewById(R.id.et_login);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        new LoginTask(et_login.getText().toString(),et_password.getText().toString(),new LoginCallback() {
            @Override
            public void callback() {

            }
        }).execute();

    }


}


