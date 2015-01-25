package com.supinfo.supsms.app.task;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import com.supinfo.supsms.app.callback.SmsCallback;
import com.supinfo.supsms.app.models.Sms;

import java.util.ArrayList;

/**
 * BackupSmsTask class
 */
public class GetSmsTask extends AsyncTask<Void, Void, Void> {

    private SmsCallback smsCallback;
    private Boolean isSuccess;
    private ContentResolver cr;
    private ArrayList<Sms> lListOfSmsInbox;
    private ArrayList<Sms> lListOfSmsSent;

    public GetSmsTask(ContentResolver cr, SmsCallback smsCallback) {
        this.smsCallback = smsCallback;
        this.cr = cr;
        isSuccess = false;

    }


    @Override
    protected Void doInBackground(Void... params) {

        getInboxSms();
        getSentSms();

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        smsCallback.callback(lListOfSmsInbox, lListOfSmsSent);
    }


    /**
     * get a list of sms from the inbox
     *
     * @return ArrayList of Sms
     */
    private ArrayList<Sms> getInboxSms() {
        lListOfSmsInbox = new ArrayList<Sms>();

        Cursor cursor = cr.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        cursor.moveToFirst();

        do {
            Sms sms = new Sms();
            try {

                sms.set_id(Integer.parseInt(cursor.getString(0)));
                sms.setThread_id(Integer.parseInt(cursor.getString(1)));
                sms.setAddress(cursor.getString(2));
                sms.setDate(Long.parseLong(cursor.getString(4)));
                sms.setBody(cursor.getString(12));
                sms.setBox("inbox");
            } catch (Exception ex) {

            }
            lListOfSmsInbox.add(sms);
        } while (cursor.moveToNext());

        return lListOfSmsInbox;
    }


    /**
     * get a list of sent sms
     *
     * @return Array list of sms
     */
    private ArrayList<Sms> getSentSms() {
        lListOfSmsSent = new ArrayList<Sms>();

        Cursor cursor = cr.query(Uri.parse("content://sms/sent"), null, null, null, null);
        cursor.moveToFirst();

        do {
            Sms sms = new Sms();
            try {

                sms.set_id(Integer.parseInt(cursor.getString(0)));
                sms.setThread_id(Integer.parseInt(cursor.getString(1)));
                sms.setAddress(cursor.getString(2));
                sms.setDate(Long.parseLong(cursor.getString(4)));
                sms.setBody(cursor.getString(12));
                sms.setBox("sent");
            } catch (Exception ex) {

            }
            lListOfSmsSent.add(sms);
        } while (cursor.moveToNext());

        return lListOfSmsSent;
    }


}
