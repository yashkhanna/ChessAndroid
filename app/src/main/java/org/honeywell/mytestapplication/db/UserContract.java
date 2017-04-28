package org.honeywell.mytestapplication.db;

import android.provider.BaseColumns;

/**
 * Created by yash.khanna on 4/28/2017.
 */

public final class UserContract {
    private UserContract() {}

    public static class UserEntry {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_PASSWORD = "password";
    }
}
