package uni.miskolc.ips.ilona.measurement.model.measurement.wifi;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uni.miskolc.ips.ilona.measurement.model.measurement.WiFiRSSI;
import uni.miskolc.ips.ilona.measurement.model.measurement.WiFiRSSIDistanceCalculator;

public class VectorIntersectionWiFiRSSIDistance implements
		WiFiRSSIDistanceCalculator {

	public static final double UNKNOWN_DISTANCE = -1;
	private static final Logger LOG = LogManager
			.getLogger(VectorIntersectionWiFiRSSIDistance.class);

	@Override
	public double distance(WiFiRSSI rssA, WiFiRSSI rssB) {
		double result = UNKNOWN_DISTANCE;
		Set<String> intersection = new HashSet<String>(rssA.getRssiValues()
				.keySet());
		intersection.retainAll(rssB.getRssiValues().keySet());
		if (intersection.isEmpty()) {
			return result;
		}
		result = 1 - Math.abs(correlation(rssA, rssB, intersection));
		LOG.info(String.format("Distance between %s and %s is %f",
				rssA.toString(), rssB.toString(), result));
		return result;

	}

	protected double correlation(WiFiRSSI rssA, WiFiRSSI rssB,
			Set<String> intersection) {
		double result = 0;

		double avgRSSA = this.avg(rssA, intersection);
		double avgRSSB = this.avg(rssB, intersection);
		double devRSSA = deviance(rssA, intersection);
		double devRSSB = deviance(rssB, intersection);
		double nominator = 0.0;

		for (String each : intersection) {
			nominator += (rssA.getRSSI(each) - avgRSSA)
					* (rssB.getRSSI(each) - avgRSSB);
		}
		if (intersection.size() > 1) {
			result = nominator
					/ ((intersection.size() - 1) * (devRSSA * devRSSB));
			return result;
		}
		return 0;
	}

	protected double deviance(WiFiRSSI wifi, Set<String> intersection) {
		double result = 0.0;
		final double avg = this.avg(wifi, intersection);
		for (String each : intersection) {
			result += Math.pow(wifi.getRSSI(each) - avg, 2.0);
		}
		result = result / ((double) intersection.size() - 1);
		return Math.sqrt(result);

	}

	protected double avg(WiFiRSSI wifi, Set<String> intersection) {
		double result = 0.0;

		for (String each : intersection) {
			result += wifi.getRSSI(each);
		}

		return result / (double) intersection.size();
	}
}
