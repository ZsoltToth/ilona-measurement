package uni.miskolc.ips.ilona.measurement.model.measurement;

public interface WiFiRSSIDistanceCalculator {
	
	public static final double UNKOWN_DISTANCE = -1.0;
	
	public double distance(WiFiRSSI rssA,WiFiRSSI rssB);

}
