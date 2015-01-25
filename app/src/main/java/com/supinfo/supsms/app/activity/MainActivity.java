package com.supinfo.supsms.app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.supinfo.supsms.app.R;
import com.supinfo.supsms.app.callback.LoginCallback;
import com.supinfo.supsms.app.models.User;
import com.supinfo.supsms.app.task.LoginTask;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity implements View.OnClickListener {

    public static final String TAG = "SupSMS/MainActivity:";
    private EditText et_login;
    private EditText et_password;
    private Button btn_login;
    private Animation animFadeIn;
    private TextView tv_title;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_login = (EditText) findViewById(R.id.et_login);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_title = (TextView) findViewById(R.id.tv_title);
        context = this;

        btn_login.setOnClickListener(this);
        startFadeInAnimation();

        SharedPreferences lSharedPreferences = getSharedPreferences("supsms", context.MODE_PRIVATE);
        String userJsonStr = lSharedPreferences.getString("user", null);
        if (userJsonStr != null) {

            User actualUser;
            try {
                actualUser = User.convertToUser(new JSONObject(userJsonStr));
                GoToBackupPage(actualUser);
            } catch (JSONException e) {
                Log.d(MainActivity.TAG, e.getMessage());
            }
        }


    }

    private void startFadeInAnimation() {
        animFadeIn = AnimationUtils.loadAnimation(this, R.anim.anim_fade_in);

        et_login.startAnimation(animFadeIn);
        et_password.startAnimation(animFadeIn);
        btn_login.startAnimation(animFadeIn);
        tv_title.startAnimation(animFadeIn);
    }

    @Override
    public void onClick(View v) {
        btn_login.setEnabled(false);
        new LoginTask(et_login.getText().toString(), et_password.getText().toString(), new LoginCallback() {
            @Override
            public void callback(User pUser) {
                btn_login.setEnabled(true);
                if (pUser != null) {
                    SharedPreferences lSharedPreferences = getSharedPreferences("supsms", context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = lSharedPreferences.edit();
                    editor.putString("user", User.convertToJson(pUser).toString());
                    editor.commit();

                    GoToBackupPage(pUser);

                } else {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MainActivity.this);
                    dlgAlert.setTitle(getString(R.string.login_error_text));
                    dlgAlert.setMessage(getString(R.string.check_connection_credentials_error_text));
                    dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            finish();
                        }
                    });
                    dlgAlert.setCancelable(false);
                    dlgAlert.create().show();
                }
            }
        }).execute();

    }

    private void GoToBackupPage(User user) {
        Intent intent = new Intent(this, BackupActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}
