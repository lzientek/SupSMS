package com.supinfo.supsms.app.task;

import android.os.AsyncTask;
import android.util.Log;
import com.supinfo.supsms.app.callback.LoginCallback;
import com.supinfo.supsms.app.helpers.PostClient;
import com.supinfo.supsms.app.models.User;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Annaud on 21/01/2015.
 */
public class LoginTask extends AsyncTask<Void, Void, Void> {

    private LoginCallback loginCallback;
    private String user;
    private String password;
    private String result;
    private User _User = null;

    public LoginTask(String user, String password, LoginCallback loginCallback) {
        this.loginCallback = loginCallback;
        this.user = user;
        this.password = password;
    }


    @Override
    protected Void doInBackground(Void... params) {

        LoginPostRequest();

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        loginCallback.callback(_User);
    }


    public void LoginPostRequest() {
        try {
            URI uri = new URI("http://91.121.105.200/API/");

            List<BasicNameValuePair> lListOfInformations = new ArrayList<BasicNameValuePair>(3);
            lListOfInformations.add(new BasicNameValuePair("action", "login"));
            lListOfInformations.add(new BasicNameValuePair("login", user));
            lListOfInformations.add(new BasicNameValuePair("password", password));

            //create a post client
            PostClient postClient = new PostClient(uri, lListOfInformations);

            //get request result as JsonObject
            JSONObject lJson = postClient.getResultAsJsonObject();
            if (lJson.getBoolean("success")) {
                JSONObject lJson2 = new JSONObject(lJson.getString("user"));
                _User = User.convertToUser(lJson2);
            }


        } catch (Exception e) {
            Log.d("Tag", e.getMessage());
        }
    }


}
