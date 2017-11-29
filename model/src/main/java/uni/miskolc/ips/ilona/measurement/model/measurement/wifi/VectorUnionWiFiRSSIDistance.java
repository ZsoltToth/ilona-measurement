package uni.miskolc.ips.ilona.measurement.model.measurement.wifi;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uni.miskolc.ips.ilona.measurement.model.measurement.WiFiRSSI;
import uni.miskolc.ips.ilona.measurement.model.measurement.WiFiRSSIDistanceCalculator;

public class VectorUnionWiFiRSSIDistance implements WiFiRSSIDistanceCalculator {

	private final double UNKNOWN_VALUE;
	private static final Logger LOG = LogManager
			.getLogger(VectorUnionWiFiRSSIDistance.class);;

	public VectorUnionWiFiRSSIDistance(double unknownValue) {
		super();
		this.UNKNOWN_VALUE = unknownValue;
	}

	@Override
	public double distance(WiFiRSSI rssA, WiFiRSSI rssB) {
		double result = 0.0;
		Set<String> union = new HashSet<String>();
		union.addAll(rssA.getRssiValues().keySet());
		union.addAll(rssB.getRssiValues().keySet());
		if (union.isEmpty()) {
			return WiFiRSSIDistanceCalculator.UNKOWN_DISTANCE;
		}
		if (rssA.getRssiValues().isEmpty() || rssB.getRssiValues().isEmpty()) {
			return 1.0;
		}

		for (String each : union) {
			double valA = rssA.getRssiValues().containsKey(each) ? rssA
					.getRssiValues().get(each) : UNKNOWN_VALUE;
			double valB = rssB.getRssiValues().containsKey(each) ? rssB
					.getRssiValues().get(each) : UNKNOWN_VALUE;
			result += Math.pow(valA - valB, 2.0);
		}
		result = Math.sqrt(result);
		LOG.info(String.format("Distance between %s and %s is %f",
				rssA.toString(), rssB.toString(), result));
		return result;
	}

}
