package com.supinfo.supsms.app.callback;

import com.supinfo.supsms.app.models.Contact;

import java.util.ArrayList;

/**
 * Created by lucas on 24/01/2015.
 */
public interface ContactCallback {
    void callback(ArrayList<Contact> contacts);
}
