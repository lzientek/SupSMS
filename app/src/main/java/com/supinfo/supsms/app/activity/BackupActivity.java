package com.supinfo.supsms.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.supinfo.supsms.app.R;
import com.supinfo.supsms.app.callback.ContactCallback;
import com.supinfo.supsms.app.callback.RequestCallback;
import com.supinfo.supsms.app.callback.SmsCallback;
import com.supinfo.supsms.app.models.Contact;
import com.supinfo.supsms.app.models.Sms;
import com.supinfo.supsms.app.models.User;
import com.supinfo.supsms.app.task.BackupContactTask;
import com.supinfo.supsms.app.task.BackupSmsTask;
import com.supinfo.supsms.app.task.GetContactTask;
import com.supinfo.supsms.app.task.GetSmsTask;

import java.util.ArrayList;

public class BackupActivity extends Activity implements View.OnClickListener {

    private Button btn_backup_contact, btn_backup_sms, btn_logout;
    private Context context;
    private User user;
    private ProgressBar pb_advancement;
    private TextView tv_advancement;
    private ImageView iv_error, iv_success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);

        //find elements
        btn_backup_contact = (Button) findViewById(R.id.btn_backup_contact);
        btn_backup_sms = (Button) findViewById(R.id.btn_backup_sms);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        pb_advancement = (ProgressBar) findViewById(R.id.pb_advancement);
        tv_advancement = (TextView) findViewById(R.id.tv_advancement);
        iv_error = (ImageView) findViewById(R.id.iv_error);
        iv_success = (ImageView) findViewById(R.id.iv_success);

        //set the click listener
        btn_backup_contact.setOnClickListener(this);
        btn_backup_sms.setOnClickListener(this);
        btn_logout.setOnClickListener(this);

        context = this;
        user = (User) getIntent().getSerializableExtra("user");
    }


    @Override
    public void onClick(View view) {
        if (view == btn_backup_contact) {
            SendContact();

        } else if (view == btn_backup_sms) {
            SendSms();

        } else if (view == btn_logout) {
            onLogout();
        }
    }


    private void SendContact() {
        showProgressBar();
        tv_advancement.setText(getString(R.string.loading_contacts_text));

        new GetContactTask(getContentResolver(), new ContactCallback() {
            @Override
            public void callback(ArrayList<Contact> contacts) {
                //no contact  found
                if (contacts.size() <= 0) {
                    showError();
                    tv_advancement.setText(getString(R.string.no_contact_text));

                } else {
                    //send to the server
                    tv_advancement.setText(getString(R.string.sending_contact_server_text));
                    new BackupContactTask(user, contacts, new RequestCallback() {
                        @Override
                        public void callback(Boolean isSuccess) {

                            if (isSuccess) {
                                tv_advancement.setText(getString(R.string.sent_contact_text));
                                showSuccess();
                            } else {
                                tv_advancement.setText(getString(R.string.error_sending_text));
                                showError();
                            }


                        }
                    }).execute();
                }
            }
        }).execute();
    }

    private void SendSms() {
        //send sms to the server
        showProgressBar();
        tv_advancement.setText(getString(R.string.loading_sms_text));

        new GetSmsTask(getContentResolver(), new SmsCallback() {
            @Override
            public void callback(ArrayList<Sms> inboxSms, ArrayList<Sms> sentSms) {
                //no sms found
                if ((inboxSms.size() + sentSms.size()) <= 0) {
                    tv_advancement.setText(getString(R.string.no_sms_text));
                    showError();
                } else {
                    tv_advancement.setText(getString(R.string.sending_sms_server_text));

                    //sending sms to the server
                    new BackupSmsTask(user, inboxSms, sentSms, new RequestCallback() {
                        @Override
                        public void callback(Boolean isSuccess) {
                            if (isSuccess) {
                                tv_advancement.setText(getString(R.string.sent_sms_text));
                                showSuccess();
                            } else {
                                tv_advancement.setText(getString(R.string.error_sending_text));
                                showError();
                            }
                        }
                    }).execute();
                }
            }
        }).execute();
    }

    private void showError() {
        pb_advancement.setVisibility(View.GONE);
        iv_error.setVisibility(View.VISIBLE);
        iv_success.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        pb_advancement.setVisibility(View.VISIBLE);
        iv_error.setVisibility(View.GONE);
        iv_success.setVisibility(View.GONE);
    }

    private void showSuccess() {
        pb_advancement.setVisibility(View.GONE);
        iv_error.setVisibility(View.GONE);
        iv_success.setVisibility(View.VISIBLE);
    }

    /**
     * Logout the actual user and send it back to the login page
     */
    private void onLogout() {
        //remove user from the shared pref
        SharedPreferences lSharedPreferences = getSharedPreferences("supsms", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = lSharedPreferences.edit();
        editor.clear();
        editor.commit();

        //go to login page
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
