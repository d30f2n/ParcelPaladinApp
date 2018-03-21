package com.parcelpaladin.d30f2n.parcelpaladinfirebase;

import com.google.firebase.auth.UserInfo;

/**
 * Created by d30f2n on 3/19/2018.
 */

public class UserInformation {

    public String name;

    public UserInformation()
    {

    }

    public UserInformation(String name) {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
