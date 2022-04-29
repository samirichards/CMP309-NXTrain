package uk.ac.abertay.s1902765.nexttrain.RttApi;

import java.util.ArrayList;
import java.util.List;

public class LocationDetail {
    public boolean realtimeActivated;
    public String tiploc;
    public String crs;
    public String description;
    public String gbttBookedArrival;
    public String gbttBookedDeparture;
    public List<Origin> origin;
    public List<Destination> destination;
    public boolean isCall;
    public boolean isPublicCall;
    public String realtimeArrival;
    public boolean realtimeArrivalActual;
    public String realtimeDeparture;
    public boolean realtimeDepartureActual;
    public String platform;
    public boolean platformConfirmed;
    public boolean platformChanged;
    public String displayAs;
}
