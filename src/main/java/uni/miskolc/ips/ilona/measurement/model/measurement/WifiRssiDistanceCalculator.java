package uni.miskolc.ips.ilona.measurement.model.measurement;

public interface WifiRssiDistanceCalculator {
    double UNKOWN_DISTANCE = -1.0;

    double distance(WifiRssi rssA, WifiRssi rssB);
}
