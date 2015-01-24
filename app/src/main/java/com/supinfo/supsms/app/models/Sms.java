package com.supinfo.supsms.app.models;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sms implements Serializable {
    private String body,box,address;
    private long date;
    private int _id;
    private int thread_id;

    public static String convertToJsonString (List<Sms> pListSms){

        List<JSONObject> lListOfJson = new ArrayList<JSONObject>();

        for (Sms lSms : pListSms)
        {
            lListOfJson.add(Sms.convertSmsToJson(lSms));
        }

        JSONObject lJson = new JSONObject();

        try {
            lJson.put("sms",lListOfJson);
            return lJson.getString("sms") ;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }


    public static JSONObject convertSmsToJson(Sms lSms){
        JSONObject lJson = new JSONObject();

        try {
            lJson.put("body",lSms.getBody());
            lJson.put("address",lSms.getAddress());
            lJson.put("box",lSms.getBox());
            lJson.put("id",lSms.get_id());
            lJson.put("thread_id",lSms.getThread_id());
            lJson.put("date",lSms.getDate());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return lJson;

    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getThread_id() {
        return thread_id;
    }

    public void setThread_id(int thread_id) {
        this.thread_id = thread_id;
    }
}
