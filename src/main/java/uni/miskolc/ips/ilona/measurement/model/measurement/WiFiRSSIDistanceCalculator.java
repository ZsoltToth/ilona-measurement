package uni.miskolc.ips.ilona.measurement.model.measurement;

public interface WiFiRSSIDistanceCalculator {
  double UNKOWN_DISTANCE = -1.0;

  double distance(WiFiRSSI rssA, WiFiRSSI rssB);
}
