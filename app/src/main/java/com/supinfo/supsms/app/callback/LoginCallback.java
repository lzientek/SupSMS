package com.supinfo.supsms.app.callback;

import com.supinfo.supsms.app.models.User;

/**
 * Created by Annaud on 21/01/2015.
 */
public interface LoginCallback {

    void callback(User pUser);
}
