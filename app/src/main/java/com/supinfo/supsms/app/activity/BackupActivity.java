package com.supinfo.supsms.app.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import com.supinfo.supsms.app.R;
import com.supinfo.supsms.app.callback.RequestCallback;
import com.supinfo.supsms.app.models.Contact;
import com.supinfo.supsms.app.models.Sms;
import com.supinfo.supsms.app.models.User;
import com.supinfo.supsms.app.task.BackupContactTask;
import com.supinfo.supsms.app.task.BackupSmsTask;

import java.security.Timestamp;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class BackupActivity extends Activity implements View.OnClickListener {

    private Button btn_backup_contact, btn_backup_sms, btn_logout;
    private Context context;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);

        //find elements
        btn_backup_contact = (Button) findViewById(R.id.btn_backup_contact);
        btn_backup_sms = (Button) findViewById(R.id.btn_backup_sms);
        btn_logout = (Button) findViewById(R.id.btn_logout);

        //set the click listener
        btn_backup_contact.setOnClickListener(this);
        btn_backup_sms.setOnClickListener(this);
        btn_logout.setOnClickListener(this);

        context = this;
        user = (User) getIntent().getSerializableExtra("user");
    }


    @Override
    public void onClick(View view) {
        if (view == btn_backup_contact) {
            //todo: BackupContactTask call
            ArrayList<Contact> contacts = new ArrayList<Contact>();

            ContentResolver cr = getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {

                    Contact lContact = new Contact();

                    lContact.set_id(Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID))));
                    lContact.setFirstName(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)));
                    lContact.setLastName(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));

                    if (Integer.parseInt(cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                                new String[]{cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID))}, null);
                        while (pCur.moveToNext()) {

                            lContact.setPhone(pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                        }
                        pCur.close();
                    }

                    contacts.add(lContact);

                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            new BackupContactTask(user, contacts, new RequestCallback() {
                @Override
                public void callback(Boolean isSuccess) {
                    Boolean is = isSuccess;
                }
            }).execute();
        } else if (view == btn_backup_sms) {
            //todo: BackupSmsTask call
            ArrayList<Sms> lListOfSmsInbox = new ArrayList<Sms>();
            ArrayList<Sms> lListOfSmsSent = new ArrayList<Sms>();

            // partie message envoyer

            Cursor cursor = getContentResolver().query(Uri.parse("content://sms/sent"), null, null, null, null);
            cursor.moveToFirst();

            try {
                do{
                    Sms lSms = new Sms();
                    try {

                        lSms.set_id( Integer.parseInt(cursor.getString(0)));
                        lSms.setThread_id(Integer.parseInt(cursor.getString(1)));
                        lSms.setAddress(cursor.getString(2));
                        lSms.setDate(Long.parseLong(cursor.getString(4)));
                        lSms.setBody(cursor.getString(12));
                        lSms.setBox("sent");
                    }catch (Exception ex)
                    {

                    }
                    //pour test
//                String msgData ="";
//                for(int idx=0;idx<cursor.getColumnCount();idx++)
//                {
//                    msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx) + "  :: id="+idx;
//                }
                    lListOfSmsSent.add(lSms);
                }while(cursor.moveToNext());
            }catch (Exception e)
            {

            }

            // partie message recus

            cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
            cursor.moveToFirst();

            try {

            }catch (Exception e)
            {
                do{
                    Sms lSms = new Sms();
                    try {

                        lSms.set_id( Integer.parseInt(cursor.getString(0)));
                        lSms.setThread_id(Integer.parseInt(cursor.getString(1)));
                        lSms.setAddress(cursor.getString(2));
                        lSms.setDate(Long.parseLong(cursor.getString(4)));
                        lSms.setBody(cursor.getString(12));
                        lSms.setBox("inbox");
                    }catch (Exception ex)
                    {

                    }
                    //pour test
//                String msgData ="";
//                for(int idx=0;idx<cursor.getColumnCount();idx++)
//                {
//                    msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx) + "  :: id="+idx;
//                }
                    lListOfSmsInbox.add(lSms);
                }while(cursor.moveToNext());
            }

            new BackupSmsTask(user,lListOfSmsInbox,lListOfSmsSent, new RequestCallback() {
                @Override
                public void callback(Boolean isSuccess) {
                      Boolean is = isSuccess;
                }
            }).execute();
        } else if (view == btn_logout) {
            onLogout();
        }
    }

    private void onLogout() {
        //remove user from the shared pref
        SharedPreferences lSharedPreferences = getPreferences(context.MODE_PRIVATE);
        SharedPreferences.Editor editor = lSharedPreferences.edit();
        editor.remove("user");
        editor.commit();

        //go to login page
        finish();
    }
}
