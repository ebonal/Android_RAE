package com.rae.placetobe.data;

import android.provider.BaseColumns;

public interface ImageData extends BaseColumns
{
    String TABLE_NAME = "imagedata";

    String COLUMN_NAME_PATH    = "PATH";
    String COLUMN_NAME_COMMENT = "COMMENT";
    String COLUMN_NAME_DATE    = "DATE";
}
