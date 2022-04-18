package uk.ac.abertay.s1902765.nexttrain;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

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
    public int updateStation(StationItem stationItem);

    @Delete
    public int deleteStations(StationItem ... stationItems);

    @Delete
    public void deleteStation(StationItem stationItem);

    @Query("SELECT * FROM stations")
    public List<StationItem> getAllStations();

    @Query("Select * from stations where 'this' = 'is a work around'")
    public List<StationItem> getNoStations();

    @Query("SELECT Name FROM stations")
    public List<String> getAllStationNames();

    @Query("SELECT * FROM stations")
    public LiveData<List<StationItem>> getAllStationsLive();

    //All purpose search function which makes use of not just the station name
    @Query("SELECT * FROM stations WHERE stations.Name LIKE '%' || :searchTerm || '%'"
    + "OR stations.CrsCode LIKE '%' || :searchTerm || '%' " +
    "OR stations.SixteenCharacterName LIKE '%' || :searchTerm || '%' ORDER BY stations.Name ASC"
    )
    public List<StationItem> searchForStations(String searchTerm);


    @Query("SELECT * FROM stations WHERE stations.Name MATCH :searchTerm " +
            "OR stations.CrsCode MATCH :searchTerm " +
            "OR stations.SixteenCharacterName MATCH :searchTerm ORDER BY stations.Name ASC")
    public LiveData<List<StationItem>> searchForStationsLive(String searchTerm);

    @Query("SELECT * FROM stations WHERE stations.StationOperator = :operatorCode")
    public List<StationItem> getAllStationsFromOperator(String operatorCode);
}
