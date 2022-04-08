package uk.ac.abertay.s1902765.nexttrain;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {StationItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract StationsDao stationsDao();
}
