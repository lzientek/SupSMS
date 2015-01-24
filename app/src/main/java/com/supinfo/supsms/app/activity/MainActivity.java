package com.supinfo.supsms.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import com.supinfo.supsms.app.R;
import com.supinfo.supsms.app.callback.LoginCallback;
import com.supinfo.supsms.app.models.User;
import com.supinfo.supsms.app.task.LoginTask;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText et_login;
    private EditText et_password;
    private Button btn_login;
    private Animation animFadeIn;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_login = (EditText) findViewById(R.id.et_login);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        context = this;

        btn_login.setOnClickListener(this);

        animFadeIn = AnimationUtils.loadAnimation(this, R.anim.anim_fade_in);

        et_login.startAnimation(animFadeIn);
        et_password.startAnimation(animFadeIn);
        btn_login.startAnimation(animFadeIn);

        SharedPreferences lSharedPreferences = getPreferences(context.MODE_PRIVATE);
        String userJsonStr = lSharedPreferences.getString("user", null);
        if (userJsonStr != null) {

            User actualUser;
            try {
                actualUser = User.convertToUser(new JSONObject(userJsonStr));
                GoToBackupPage(actualUser);
            } catch (JSONException e) {
                Log.d("Tag", e.getMessage());
            }
        }


    }

    @Override
    public void onClick(View v) {

        new LoginTask(et_login.getText().toString(), et_password.getText().toString(), new LoginCallback() {
            @Override
            public void callback(User pUser) {

                if (pUser != null) {
                    SharedPreferences lSharedPreferences = getPreferences(context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = lSharedPreferences.edit();
                    editor.putString("user", User.convertToJson(pUser).toString());
                    editor.commit();

                    GoToBackupPage(pUser);
                }

            }
        }).execute();

    }

    private void GoToBackupPage(User user) {
        Intent intent = new Intent(this, BackupActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
