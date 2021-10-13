package com.itgenz.appcontentprovider.constant;

import android.net.Uri;

public class URIConstant {
    // URI Authority
    private static final String AUTHORITY = "com.itgenz.contentprovider";

    // create content URIs from the authority by appending path to database table
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/countries"); // Convert String to URI type
}
