package uk.ac.abertay.s1902765.nexttrain.RttApi;
import java.util.List;

public class ServiceSearchResult {
    public String serviceUid;
    public String runDate;
    public String serviceType;
    public boolean isPassenger;
    public String trainIdentity;
    public String powerType;
    public String trainClass;
    public String atocCode;
    public String atocName;
    public boolean performanceMonitored;
    public List<Origin> origin;
    public List<Destination> destination;
    public List<Location_Service> locations;
    public boolean realtimeActivated;
    public String runningIdentity;
}


