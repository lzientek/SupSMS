package com.supinfo.supsms.task;

import android.os.AsyncTask;

import com.supinfo.supsms.callback.LoginCallback;

/**
 * Created by Annaud on 21/01/2015.
 */
public class LoginTask extends AsyncTask<Void, Void, Void> {

    private LoginCallback loginCallback;
    private String user;
    private String password;

    public LoginTask(String user,String password,LoginCallback loginCallback) {
        this.loginCallback = loginCallback;
        this.user = user;
        this.password = password;
    }


    @Override
    protected Void doInBackground(Void... params) {




        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        loginCallback.callback();
    }
}
