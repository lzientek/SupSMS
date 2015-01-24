package com.supinfo.supsms.app.requestHelper;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;

public class PostClient {
    HttpClient httpClient = new DefaultHttpClient();
    HttpPost post = new HttpPost();
    List<BasicNameValuePair> lListOfInformations;
    URI uri;

    public PostClient(URI uri, List<BasicNameValuePair> lListOfInformations) throws UnsupportedEncodingException {
        this.uri = uri;
        post.setURI(uri);
        this.lListOfInformations = lListOfInformations;
        post.setEntity(new UrlEncodedFormEntity(lListOfInformations));
    }

    public String getResultAsString() throws IOException {
        HttpResponse reponse = httpClient.execute(post);
        String result = EntityUtils.toString(reponse.getEntity());
        return result;
    }

    public JSONObject getResultAsJsonObject() throws IOException, JSONException {
        JSONObject lJson = new JSONObject(getResultAsString());

        return lJson;
    }
}