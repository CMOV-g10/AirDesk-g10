package pt.ulisboa.tecnico.cmov.airdesk_g10;

import android.provider.BaseColumns;

/**
 * Created by luis on 4/7/15.
 */
public final class AirDeskContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public AirDeskContract() {}

    /* Inner class that defines the table contents */
    public static abstract class UserEntry implements BaseColumns {
        /** Implement database columns here **/
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_USER_ID = "userid";
        public static final String COLUMN_USER_NAME = "name";
        public static final String COLUMN_USER_PASSWORD = "password";

    }
    public static abstract class WorkspaceEntry implements BaseColumns {
        /** Implement database columns here **/
        public static final String TABLE_NAME = "workspace";
        public static final String COLUMN_WORKSPACE_ID = "workspaceid";
        public static final String COLUMN_WORKSPACE_NAME = "name";
        public static final String COLUMN_WORKSPACE_QUOTA = "quota";
        public static final String COLUMN_WORKSPACE_PUBLIC = "public";
        public static final String COLUMN_WORKSPACE_TAGS = "tags";
    }
    public static abstract class FileEntry implements BaseColumns {
        /** Implement database columns here **/
        public static final String TABLE_NAME = "file";
        public static final String COLUMN_FILE_ID = "fileid";
        public static final String COLUMN_FILE_TITLE = "title";
        public static final String COLUMN_FILE_CONTENT = "content";

    }
}