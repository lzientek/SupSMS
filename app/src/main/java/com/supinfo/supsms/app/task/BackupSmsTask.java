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
    private List<Sms> smsListInbox;
    private List<Sms> smsListSent;
    private Boolean isSuccess;
    private User user;

    public BackupSmsTask(User user, List<Sms> smsListInbox , List<Sms> smsListSent,RequestCallback requestCallback) {
        this.requestCallback = requestCallback;
        this.smsListInbox = smsListInbox;
        this.smsListSent = smsListSent;
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

        isSuccess =  InboxPostRequest(smsListInbox)&&SentPostRequest(smsListSent) ;

    }

    private boolean SentPostRequest(List<Sms> pListOfSms)
    {
        boolean success = false;

        try {
            URI uri = new URI("http://91.121.105.200/API/");

            List<BasicNameValuePair> lListOfInformations = new ArrayList<BasicNameValuePair>();
            lListOfInformations.add(new BasicNameValuePair("action", "backupsms"));
            lListOfInformations.add(new BasicNameValuePair("login", user.getUsername()));
            lListOfInformations.add(new BasicNameValuePair("password", user.getPassword()));
            lListOfInformations.add(new BasicNameValuePair("box", "sent"));

            String JsonSmsString = Sms.convertToJsonString(pListOfSms);
            lListOfInformations.add(new BasicNameValuePair("sms", JsonSmsString));

            //create a post client
            PostClient postClient = new PostClient(uri, lListOfInformations);

            //get request result as JsonObject
            JSONObject lJson = postClient.getResultAsJsonObject();
            success = lJson.getBoolean("success");

        } catch (Exception e) {
            Log.d("Tag", e.getMessage());
        }

        return success;
    }

    private boolean InboxPostRequest(List<Sms> pListOfSms)
    {
        boolean success = false;

        try {
            URI uri = new URI("http://91.121.105.200/API/");

            List<BasicNameValuePair> lListOfInformations = new ArrayList<BasicNameValuePair>();
            lListOfInformations.add(new BasicNameValuePair("action", "backupsms"));
            lListOfInformations.add(new BasicNameValuePair("login", user.getUsername()));
            lListOfInformations.add(new BasicNameValuePair("password", user.getPassword()));
            lListOfInformations.add(new BasicNameValuePair("box", "inbox"));
            String JsonSmsString = Sms.convertToJsonString(pListOfSms);
            lListOfInformations.add(new BasicNameValuePair("sms", JsonSmsString));

            //create a post client
            PostClient postClient = new PostClient(uri, lListOfInformations);

            //get request result as JsonObject
            JSONObject lJson = postClient.getResultAsJsonObject();
            success = lJson.getBoolean("success");

        } catch (Exception e) {
            Log.d("Tag", e.getMessage());
        }

        return success;
    }


}
