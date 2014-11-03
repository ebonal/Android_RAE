package com.rae.placetobe.data;

import android.provider.BaseColumns;

public interface Users extends BaseColumns
{
    String TABLE_NAME = "users";

    String COLUMN_NAME_USER_ID = "userid";
    String COLUMN_NAME_NAME    = "name";
    String COLUMN_NAME_EMAIL   = "email";
}
