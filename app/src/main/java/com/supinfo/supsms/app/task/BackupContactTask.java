package com.supinfo.supsms.app.task;

import android.os.AsyncTask;
import android.util.Log;
import com.supinfo.supsms.app.callback.RequestCallback;
import com.supinfo.supsms.app.helpers.PostClient;
import com.supinfo.supsms.app.models.Contact;
import com.supinfo.supsms.app.models.User;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * BackupContactTask class
 */
public class BackupContactTask extends AsyncTask<Void, Void, Void> {

    private RequestCallback requestCallback;
    private Boolean isSuccess;
    private User user;
    private List<Contact> contacts;

    public BackupContactTask(User user,List<Contact> contacts, RequestCallback requestCallback) {
        this.requestCallback = requestCallback;
        this.user = user;
        this.contacts = contacts;
        isSuccess = false;
    }


    @Override
    protected Void doInBackground(Void... params) {

        ContactPostRequest();

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        requestCallback.callback(isSuccess);
    }


    public void ContactPostRequest() {
        try {
            URI uri = new URI("http://91.121.105.200/API/");

            List<BasicNameValuePair> lListOfInformations = new ArrayList<BasicNameValuePair>(3);
            lListOfInformations.add(new BasicNameValuePair("action", "backupcontacts"));
            lListOfInformations.add(new BasicNameValuePair("login", user.getUsername()));
            lListOfInformations.add(new BasicNameValuePair("password", user.getPassword()));


            String contactJsonStr =  Contact.convertToJsonString(contacts);
            lListOfInformations.add(new BasicNameValuePair("contacts", contactJsonStr));

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
