package uk.ac.abertay.s1902765.nexttrain;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface StationsDao {
    //TODO Make this asynchronous when it comes time to optimise everything
    //Link to page for this:
    //https://developer.android.com/training/data-storage/room/async-queries#java

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertStations(StationItem... stationItems);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertStation(StationItem stationItem);

    @Update
    public long updateStation(StationItem stationItem);

    @Delete
    public int deleteStations(StationItem ... stationItems);

    @Delete
    public void deleteStation(StationItem stationItem);

    @Query("SELECT * FROM stations")
    public StationItem[] getAllStations();

    //All purpose search function which makes use of not just the station name
    @Query("SELECT * FROM stations WHERE stations.Name LIKE :searchTerm " +
    "OR stations.CrsCode LIKE :searchTerm " +
    "OR stations.SixteenCharacterName LIKE :searchTerm")
    public StationItem[] searchForStations(String searchTerm);

    @Query("SELECT * FROM stations WHERE stations.StationOperator = :operatorCode")
    public StationItem[] getAllStationsFromOperator(String operatorCode);
}
