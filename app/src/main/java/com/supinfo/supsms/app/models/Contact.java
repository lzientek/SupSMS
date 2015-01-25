package com.supinfo.supsms.app.models;

import com.supinfo.supsms.app.helpers.EncodingHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class Contact implements Serializable {
    private String displayName, phone;
    private int _id;

    public static String convertToJsonString(List<Contact> contacts) {

        JSONArray lListOfJson = new JSONArray();

        for (Contact contact : contacts) {
            lListOfJson.put(Contact.convertContactToJson(contact));
        }

        JSONObject lJson = new JSONObject();

        try {
            lJson.put("contact", lListOfJson);
            return lJson.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static JSONObject convertContactToJson(Contact contact) {
        JSONObject lJson = new JSONObject();

        try {

            lJson.put("_ID", contact.get_id());
            lJson.put("PNUM", contact.getPhone());
            lJson.put("DNAME", EncodingHelper.ToBase64(contact.getDisplayName()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lJson;

    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
