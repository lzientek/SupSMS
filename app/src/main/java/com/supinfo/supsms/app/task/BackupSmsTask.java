package com.supinfo.supsms.app.task;

import android.os.AsyncTask;
import android.provider.Telephony;
import android.util.Log;
import com.supinfo.supsms.app.callback.RequestCallback;
import com.supinfo.supsms.app.models.Sms;
import com.supinfo.supsms.app.models.User;
import com.supinfo.supsms.app.requestHelper.PostClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * BackupSmsTask class
 */
public class BackupSmsTask extends AsyncTask<Void, Void, Void> {

    private RequestCallback requestCallback;
    private List<Sms> smsList;
    private Boolean isSuccess;
    private User user;

    public BackupSmsTask(User user, List<Sms> smsList,RequestCallback requestCallback) {
        this.requestCallback = requestCallback;
        this.smsList = smsList;
        isSuccess = false;
        this.user = user;
    }


    @Override
    protected Void doInBackground(Void... params) {

        SmsPostRequest();

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        requestCallback.callback(isSuccess);
    }


    public void SmsPostRequest() {
        try {
            URI uri = new URI("http://91.121.105.200/API/");

            List<BasicNameValuePair> lListOfInformations = new ArrayList<BasicNameValuePair>(3);
            lListOfInformations.add(new BasicNameValuePair("action", "backupsms"));
            lListOfInformations.add(new BasicNameValuePair("login", user.getUsername()));
            lListOfInformations.add(new BasicNameValuePair("password", user.getPassword()));

            //todo: voire comment gérer ca
            lListOfInformations.add(new BasicNameValuePair("box", "inbox"));
            lListOfInformations.add(new BasicNameValuePair("box", "sent"));
            //ajouter la list de sms
             //todo : convertir les sms en Json
            //create a post client
            PostClient postClient = new PostClient(uri, lListOfInformations);

            //get request result as JsonObject
            JSONObject lJson = postClient.getResultAsJsonObject();
            isSuccess = lJson.getBoolean("success");

        } catch (Exception e) {
            Log.d("Tag", e.getMessage());
        }
    }


}
