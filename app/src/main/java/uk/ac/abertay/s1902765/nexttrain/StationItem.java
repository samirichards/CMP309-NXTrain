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
    public String CrsCode;

    public String Name;
    public String SixteenCharacterName;

    @Nullable
    public String Postcode;
    @Nullable
    public String AddressLine1;
    @Nullable
    public String AddressLine2;
    @Nullable
    public String AddressLine3;
    @Nullable
    public String AddressLine4;
    @Nullable
    public String AddressLine5;

    public double Longitude;
    public double Latitude;

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

