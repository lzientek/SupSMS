package com.supinfo.supsms.app.models;

import com.supinfo.supsms.app.helpers.EncodingHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class Sms implements Serializable {
    private String body, box, address;
    private long date;
    private int _id;
    private int thread_id;

    public static String convertToJsonString(List<Sms> pListSms) {

        JSONArray lListOfJson = new JSONArray();

        for (Sms lSms : pListSms) {
            lListOfJson.put(Sms.convertSmsToJson(lSms));
        }

        JSONObject lJson = new JSONObject();

        try {
            lJson.put("SMS", lListOfJson);
            return lJson.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }


    public static JSONObject convertSmsToJson(Sms lSms) {
        JSONObject lJson = new JSONObject();

        try {
            String newBody = EncodingHelper.ToBase64(lSms.getBody());
            lJson.put("body", newBody);
            lJson.put("address", lSms.getAddress());
            lJson.put("box", lSms.getBox());
            lJson.put("_id", lSms.get_id());
            lJson.put("thread_id", lSms.getThread_id());
            lJson.put("date", lSms.getDate());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
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
