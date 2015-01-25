package com.supinfo.supsms.app.task;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import com.supinfo.supsms.app.callback.ContactCallback;
import com.supinfo.supsms.app.models.Contact;

import java.util.ArrayList;

/**
 * BackupSmsTask class
 */
public class GetContactTask extends AsyncTask<Void, Void, Void> {

    private ContactCallback contactCallback;
    private ArrayList<Contact> contactList;

    ContentResolver cr;

    public GetContactTask(ContentResolver cr, ContactCallback contactCallback) {
        this.contactCallback = contactCallback;
        contactList = new ArrayList<Contact>();
        this.cr = cr;
    }


    @Override
    protected Void doInBackground(Void... params) {

        getPhoneContacts();

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        contactCallback.callback(contactList);
    }


    /**
     * get a list from contacts from the phone
     *
     * @return ArrayList of Contact
     */
    private void getPhoneContacts() {


        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {

                //get contact infos
                Contact contact = new Contact();
                contact.set_id(Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID))));
                contact.setDisplayName(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));

                //get contact phone number
                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID))}, null);
                    while (pCur.moveToNext()) {
                        contact.setPhone(pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    }
                    pCur.close();
                }

                //add the contact to the list
                contactList.add(contact);

            }
        }

    }

}
