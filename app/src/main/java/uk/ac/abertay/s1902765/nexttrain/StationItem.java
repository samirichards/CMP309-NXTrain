package uk.ac.abertay.s1902765.nexttrain;

import android.location.Address;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

@Fts4
@Entity(tableName = "stations")
public class StationItem {

    //Primary key must be int for the sake of rapid searching
    @PrimaryKey
    @ColumnInfo(name = "rowid")
    public int id;

    public String CrsCode;

    public String Name;
    public String SixteenCharacterName;

    //This will contain the longitude and latitude of the stations
    //Nvm this will need to be broken up into separate parts
    //Room doesn't support complex types
    @Nullable
    public Address LocationAddress;

    public String StationOperator;

    //0 is unstaffed
    //1 is part time staffed
    //2 is full time staffed
    public int StaffingLevel;

    public boolean ClosedCircuitTelevision;

    //Add more basic information as you see fit, however for now this should suffice
    //The rest of the information can be fetched on request
    //If more items are added to this class which are populated afterwards you can use @Ignore on them to prevent
    //saving to the database

}

