package com.supinfo.supsms.app.task;

import android.os.AsyncTask;

import android.util.Log;
import com.supinfo.supsms.app.callback.LoginCallback;

import com.supinfo.supsms.app.models.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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

    public LoginTask(String user,String password,LoginCallback loginCallback) {
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
        loginCallback.callback();
    }


    public void LoginPostRequest()
    {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost();

            URI uri = new URI("http://91.121.105.200/API/");

            post.setURI(uri);

            List<BasicNameValuePair> lListOfInformations = new ArrayList<BasicNameValuePair>(3);

            lListOfInformations.add(new BasicNameValuePair("action", "login"));
            lListOfInformations.add(new BasicNameValuePair("login", user));
            lListOfInformations.add(new BasicNameValuePair("password", password));
            post.setEntity(new UrlEncodedFormEntity(lListOfInformations));

            HttpResponse reponse = httpClient.execute(post) ;
            result = EntityUtils.toString( reponse.getEntity());

            JSONObject lJson = new JSONObject(result);
            JSONObject lJson2 = new JSONObject(lJson.getString("user"));

            User lUser = User.convertToUser(lJson2);

            Thread.sleep(1000);


        }catch (Exception e)
        {
            Log.d("Tag",e.getMessage());
        }
    }




}
