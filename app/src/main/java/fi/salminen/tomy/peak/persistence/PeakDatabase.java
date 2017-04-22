package fi.salminen.tomy.peak.persistence;

import com.raizlabs.android.dbflow.annotation.Database;


@Database(name = PeakDatabase.NAME, version = PeakDatabase.VERSION)
public class PeakDatabase {
    public static final String NAME = "PeakDatabase";

    public static final int VERSION = 1;
}