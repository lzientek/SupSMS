package com.supinfo.supsms.app.callback;

import com.supinfo.supsms.app.models.Sms;

import java.util.ArrayList;

/**
 * Created by lucas on 25/01/2015.
 */
public interface SmsCallback {
    public void callback(ArrayList<Sms> inboxSms, ArrayList<Sms> sentSms);
}
