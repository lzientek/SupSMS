package com.supinfo.supsms.app.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class Contact implements Serializable {
    private String lastName,firstName,phone;
    private int _id;

    public static JSONObject convertToJson(List<Contact> pListContact){

        JSONObject lJson = new JSONObject();

        try {
            lJson.put("contact", pListContact);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lJson;

    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
