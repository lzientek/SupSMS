package com.supinfo.supsms.app.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Annaud on 22/01/2015.
 */

public class User implements Serializable {

    private int id;
    private String username;
    private String password;
    private String phone;
    private String lastname;
    private String firstname;
    private String postalCode;
    private String address;
    private String email;

    public static User convertToUser(JSONObject object){

        User lUser = new User();
        try {
            lUser.setId(object.getInt("id"));
            lUser.setUsername(object.getString("username"));
            lUser.setPassword(object.getString("password"));
            lUser.setPhone(object.getString("phone"));
            lUser.setLastname(object.getString("lastname"));
            lUser.setFirstname(object.getString("firstname"));
            lUser.setPostalCode(object.getString("postalCode"));
            lUser.setAddress(object.getString("address"));
            lUser.setEmail(object.getString("email"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lUser;

    }


    public static JSONObject convertToJson(User pUser){

        JSONObject lJson = new JSONObject();

        try {
            lJson.put("id",pUser.getId());
            lJson.put("username",pUser.getUsername());
            lJson.put("password",pUser.getPassword());
            lJson.put("phone",pUser.getPhone());
            lJson.put("lastname",pUser.getLastname());
            lJson.put("firstname",pUser.getFirstname());
            lJson.put("postalCode",pUser.getPostalCode());
            lJson.put("address",pUser.getAddress());
            lJson.put("email",pUser.getEmail());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lJson;

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
